package dev.ddash.taskscheduler.service.repository;

import dev.ddash.taskscheduler.dto.ScheduleMap;
import dev.ddash.taskscheduler.service.Task;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class TaskRepo {

    private static final HashMap<String, ScheduleMap> tasks = new HashMap<>();

    public Set<Map.Entry<String, ScheduleMap>> getTasks(){
        return tasks.entrySet();
    }
    public Map addRecord(String taskId, ScheduleMap scheduleMap) {
        tasks.put(taskId, scheduleMap);
        return tasks;
    }

    public ScheduleMap getRecord(String taskId) {
        return tasks.get(taskId);
    }

    public void removeRecord(String taskId) {
        tasks.remove(taskId);
    }
}
