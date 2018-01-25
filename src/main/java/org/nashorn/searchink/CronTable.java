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
	
	@Scheduled(initialDelay = 60000, fixedDelay = 60000) 
	public void otherJob() { 
		System.out.println("@Scheduled");
		taskRepository.save(new Task(new Date(), null, null, null, 
        		"auto-task", "auto-task description", 1, 1));
		
		List<Task> tasks = taskRepository.findAll();
		webSocket.convertAndSend("/topic/greetings", 
				tasks.stream().map(task -> modelMapper.map(task, TaskDto.Task.class))
		          .collect(Collectors.toList()));
	}
}
