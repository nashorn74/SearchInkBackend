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
	
	// 애플리케이션 시작 후 60초 후에 첫 실행, 그 후 매 60초마다 주기적으로 실행한다. 
	@Scheduled(initialDelay = 6000, fixedDelay = 6000) 
	public void otherJob() { 
		System.out.println("@Scheduled");
		taskRepository.save(new Task(new Date(), null, null, null, 
        		"auto-task", "auto-task description", 1, 1));
		webSocket.convertAndSend("/topic/greetings", taskRepository.findAll());
	}
}
