package dev.ddash.taskscheduler.service.impl;

import dev.ddash.taskscheduler.service.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * MyTask1 -> SCHEDULE
 */

@Service
@Log4j2
public class MyTask1 implements Task {
    @Override
    public void performTask() throws Exception {
        log.info("MyTask1 executing ..");
        Thread.sleep(2000);
    }
}
