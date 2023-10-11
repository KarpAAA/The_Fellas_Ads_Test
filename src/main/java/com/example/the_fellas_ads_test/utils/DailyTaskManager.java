package com.example.the_fellas_ads_test.utils;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DailyTaskManager {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0/1 * * * ?" )
    public void scheduledTask(){
        System.out.println("Checking application working state in : " + dateFormat.format(new Date()));
    }
}
