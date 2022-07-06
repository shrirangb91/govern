/**
 * 
 */
package com.govern.LiquibaseUtility.config;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author Shrirang.Brahmapurikar
 *
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {
	
	private static Logger LOGGER = LoggerFactory.getLogger(AsyncConfiguration.class);
	
	@Qualifier(value = "taskExecutor")
	public Executor taskExecutor() {
		LOGGER.debug("Creating Async Task executor");
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setQueueCapacity(100);
		executor.setMaxPoolSize(4);
		executor.setThreadNamePrefix("LiquibaseUtility-");
		executor.initialize();
		return executor;
		
	}
}
