package dev.ddash.taskscheduler.service.impl;

import dev.ddash.taskscheduler.service.Task;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class MyTask2 implements Task {
    @Override
    public void performTask() throws Exception {
        log.info("MyTask2 execute");
        Thread.sleep(4000);
    }
}
