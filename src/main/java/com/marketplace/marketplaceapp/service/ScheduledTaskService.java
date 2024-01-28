package com.marketplace.marketplaceapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {

    @Autowired
    private JobService jobService;

    // Run the task every day at a specific cron expression -seconds, minutes, hours, day of month, month, and day of week.
    //here it is  11:20:00 -everyday at 11 : 20 am
    @Scheduled(cron = "0 20 11 * * ?")
    public void handleExpiredJobs() {
        jobService.handleExpiredJobs();
    }
}

