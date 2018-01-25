package org.nashorn.searchink;

import java.util.Date;

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
	
	@Scheduled(initialDelay = 60000, fixedDelay = 60000) 
	public void otherJob() { 
		System.out.println("@Scheduled");
		taskRepository.save(new Task(new Date(), null, null, null, 
        		"auto-task", "auto-task description", 1, 1));
		webSocket.convertAndSend("/topic/greetings", taskRepository.findAll());
	}
}
