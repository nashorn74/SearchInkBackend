package org.nashorn.searchink;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
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
			
			sendTaskList();
		}
		
		List<Task> dueTasks = taskRepository.findFirstForDue();
		if (dueTasks.size() == 0) {
			List<Task> performTasks = taskRepository.findFirstForPerform();
			if (performTasks.size() > 0) {
				Task task = performTasks.get(0);
				Date dueDate = DateUtils.addMinutes(new Date(), 2);
				task.setUpdatedat(new Date());
				task.setDuedate(dueDate);
				task.setResolvedat(null);
				task.setStatus(8888);
				taskRepository.save(task);
				System.out.println("new perform task:"+task.getId());
				
				sendTaskList();
			}
		} else {
			List<Task> overDueTasks = taskRepository.findFirstOverDue(new Date());
			if (overDueTasks.size() > 0) {
				Task task = overDueTasks.get(0);
				task.setUpdatedat(new Date());
				task.setResolvedat(new Date());
				task.setStatus(9999);
				taskRepository.save(task);
				System.out.println("complete task:"+task.getId());
				
				sendTaskList();
			}
		}
		
	}
	
	@Scheduled(initialDelay = 60000, fixedDelay = 60000) 
	public void addTask() { 
		System.out.println("@addTask");
		taskRepository.save(new Task(new Date(), null, null, null, 
        		"auto-task", "auto-task description", 1, 1));
		
		sendTaskList();
	}
	
	public void sendTaskList() {
		List<Task> tasks = taskRepository.findAllExceptPostpone();
		webSocket.convertAndSend("/topic/greetings", 
				tasks.stream().map(task -> modelMapper.map(task, TaskDto.Task.class))
		          .collect(Collectors.toList()));
	}
}
