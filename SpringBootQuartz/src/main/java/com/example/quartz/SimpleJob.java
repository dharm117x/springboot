package com.example.quartz;

import java.text.MessageFormat;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

public class SimpleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
    	 JobDataMap dataMap = context.getMergedJobDataMap();
         System.out.println(MessageFormat.format("Job: {0}, Data: {1}", getClass(), dataMap.getString("param")));
    }

}