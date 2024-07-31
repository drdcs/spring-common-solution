package dev.ddash.taskscheduler.dto;

import dev.ddash.taskscheduler.service.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleMap {
    ScheduleRequest request;
    Task task;
}
