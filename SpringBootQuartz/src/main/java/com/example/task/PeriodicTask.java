package com.example.task;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PeriodicTask {

   // @Scheduled(cron = "${cron-string}")
    public void everyFiveSeconds() {
        System.out.println("Periodic task: " + new Date());
    }

}
