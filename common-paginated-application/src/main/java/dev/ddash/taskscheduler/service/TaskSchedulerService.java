package dev.ddash.taskscheduler.service;

import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import dev.ddash.taskscheduler.service.repository.TaskRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.concurrent.*;


@Service
@Log4j2
public class TaskSchedulerService {

    private final ConcurrentHashMap<String, CronSchedule> taskSchedulerMap = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    private final TaskRepo taskRepository;

    public TaskSchedulerService(TaskRepo taskRepository) {
        this.taskRepository = taskRepository;
    }


    // should poll every 1 second.
    @Scheduled(fixedDelay = 1000)
    public void init() {
//        log.info("Starting task scheduler and polling");
        // check for new task and insert into schedule.
        var entries = taskRepository.getTasks();
        for (var entry : entries) {
            String key = entry.getKey();
            if (!taskSchedulerMap.containsKey(key)) {
                CronSchedule cronSchedule = getCronSchedule(key);
                if (cronSchedule.nextRunTime != null && taskSchedulerMap.get(key) == null){
                    taskSchedulerMap.put(key, cronSchedule);
                }
            }
        }
        scheduler.scheduleAtFixedRate(this::pollTask, 0, 1, TimeUnit.SECONDS);
    }

    private CronSchedule getCronSchedule(String key) {
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX));
        Cron cron = parser.parse(taskRepository.getRecord(key).getRequest().getCron());
        ExecutionTime executionTime = ExecutionTime.forCron(cron);
        ZonedDateTime currentTime = ZonedDateTime.now();
        var nextRuntime = executionTime.nextExecution(currentTime).orElse(null);
        CronSchedule cronSchedule = new CronSchedule(cron, nextRuntime);
        return cronSchedule;
    }

    private void pollTask(){
        ZonedDateTime currentTime = ZonedDateTime.now();
        taskSchedulerMap.forEach((taskId, cronSchedule) -> {
            if (currentTime.isAfter(cronSchedule.nextRunTime)){
                executorService.submit(() -> {
                    var record = taskRepository.getRecord(taskId);
                    try {
                        record.getTask().performTask();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                taskSchedulerMap.remove(taskId);
                if ( taskSchedulerMap.containsKey(taskId) && taskSchedulerMap.get(taskId) == null){
                    getCronSchedule(taskId).nextRunTime = currentTime;
                }
            }
        });
    }

    private static class CronSchedule {
        Cron cron;
        ZonedDateTime nextRunTime;

        CronSchedule(Cron cron, ZonedDateTime nextRunTime) {
            this.cron = cron;
            this.nextRunTime = nextRunTime;
        }
    }
}
