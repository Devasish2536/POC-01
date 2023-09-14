package com.example.demo.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	private Boolean isBatchStatusComplete = false;
	private String loggerMsg = "";

	@Scheduled(cron = "0/20 * * * * *")
	public String executeJob() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParameters parameters = new JobParametersBuilder().addLong("Batch Process :", System.currentTimeMillis())
				.toJobParameters();
		if (!isBatchStatusComplete) {
			JobExecution jobExecution = jobLauncher.run(job, parameters);
			if (jobExecution.getStatus().isUnsuccessful()) {
				loggerMsg = "Batch Process Stopped, Something Went Wrong!!";
			} else {
				loggerMsg = "Batch Process Completed";
				isBatchStatusComplete = true;
			}
		}

		return loggerMsg;
	}
}
