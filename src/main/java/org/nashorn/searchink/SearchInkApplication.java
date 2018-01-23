package org.nashorn.searchink;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
public class SearchInkApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SearchInkApplication.class, args);
	}
	
	/*@Bean 
	public TaskScheduler taskScheduler() { 
		return new ConcurrentTaskScheduler(); 
	} */
	
	@Bean 
	public TaskScheduler taskScheduler() { 
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler(); 
		taskScheduler.setPoolSize(10); 
		return taskScheduler; 
	}
	
	@Bean
	public ModelMapper modelMapper() {
	  return new ModelMapper();
	}
	
}
