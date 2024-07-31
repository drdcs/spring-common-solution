package dev.ddash.taskscheduler.dto;

import dev.ddash.taskscheduler.service.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// POST:
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequest {
    private String task;
    private String cron;
}
