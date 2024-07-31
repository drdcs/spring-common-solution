package dev.ddash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
@EnableScheduling
public class CommonPaginatedApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonPaginatedApplication.class, args);
    }
}