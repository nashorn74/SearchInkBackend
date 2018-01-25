package org.nashorn.searchink;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled; 
import org.springframework.stereotype.Component; 

@Component
public class CronTable {
	@Autowired
    private TaskRepository taskRepository;
	
	@Autowired
	private SimpMessagingTemplate webSocket;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Scheduled(initialDelay = 1000, fixedDelay = 1000) 
	public void checkPostpone() { 
		System.out.println("@checkPostpone");
		List<Task> overTasks = taskRepository.findAllOverPostpone(new Date());
		System.out.println("over postpone task count:"+overTasks.size());
		if (overTasks.size() > 0) {
			for (int i = 0; i < overTasks.size(); i++) {
				Task task = overTasks.get(i);
				task.setUpdatedat(new Date());
				task.setDuedate(null);
				task.setResolvedat(null);
		        taskRepository.save(task);
			}
			
			List<Task> tasks = taskRepository.findAllExceptPostpone();
			webSocket.convertAndSend("/topic/greetings", 
					tasks.stream().map(task -> modelMapper.map(task, TaskDto.Task.class))
			          .collect(Collectors.toList()));
		}
	}
	
	@Scheduled(initialDelay = 60000, fixedDelay = 60000) 
	public void addTask() { 
		System.out.println("@addTask");
		taskRepository.save(new Task(new Date(), null, null, null, 
        		"auto-task", "auto-task description", 1, 1));
		
		List<Task> tasks = taskRepository.findAllExceptPostpone();
		webSocket.convertAndSend("/topic/greetings", 
				tasks.stream().map(task -> modelMapper.map(task, TaskDto.Task.class))
		          .collect(Collectors.toList()));
	}
}
