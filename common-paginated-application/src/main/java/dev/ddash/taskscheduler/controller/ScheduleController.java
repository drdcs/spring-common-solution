package dev.ddash.taskscheduler.controller;

import dev.ddash.taskscheduler.dto.ScheduleMap;
import dev.ddash.taskscheduler.dto.ScheduleRequest;
import dev.ddash.taskscheduler.service.Task;
import dev.ddash.taskscheduler.service.TaskSchedulerService;
import dev.ddash.taskscheduler.service.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final TaskRepo repository;
    private final ApplicationContext applicationContext;

    public ScheduleController(TaskRepo repository, ApplicationContext applicationContext) {
        this.repository = repository;
        this.applicationContext = applicationContext;
    }

    @PostMapping
    public ResponseEntity<Void> scheduleTask(@RequestBody ScheduleRequest request) {
        Task task = (Task) applicationContext.getBean(request.getTask());
        repository.addRecord(request.getTask(), new ScheduleMap(request, task));
        return ResponseEntity.ok().build();
    }


}
