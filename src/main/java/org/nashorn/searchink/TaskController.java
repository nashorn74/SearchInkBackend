package org.nashorn.searchink;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {
	@Autowired
    private TaskRepository taskRepository;
	
	@Autowired
	private ModelMapper modelMapper;
		
	@RequestMapping(method = RequestMethod.POST)
    public Task put(@RequestParam(value = "title") String title, 
    		@RequestParam(value = "description") String description,
    		@RequestParam(value = "priority") Integer priority,
    		@RequestParam(value = "status") Integer status) {
        return taskRepository.save(new Task(new Date(), null, null, null, 
        		title, description, priority, status));
    }
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Task findOne(@PathVariable(value = "id") String id) {
        return taskRepository.findOneById(id);
    }
		
	@GetMapping
    public List<TaskDto.Task> getExams() {
        List<Task> tasks = taskRepository.findAllExceptPostpone();
        return tasks.stream().map(task -> modelMapper.map(task, TaskDto.Task.class))
          .collect(Collectors.toList());
    }
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Task update(@PathVariable(value = "id") String id, 
    		@RequestParam(value = "title") String title, 
    		@RequestParam(value = "description") String description,
    		@RequestParam(value = "priority") Integer priority,
    		@RequestParam(value = "status") Integer status) {
		Task task = taskRepository.findOneById(id);
		task.setUpdatedat(new Date());
		task.setTitle(title);
		task.setDescription(description);
		task.setPriority(priority);
		task.setStatus(status);
        return taskRepository.save(task);
    }
	
	@RequestMapping(value = "postpone/{id}", method = RequestMethod.PUT)
    public Task postpone(@PathVariable(value = "id") String id) {
		Task task = taskRepository.findOneById(id);
		Date postponeDate = DateUtils.addMinutes(new Date(), 1);
		task.setUpdatedat(new Date());
		task.setDuedate(null);
		task.setResolvedat(postponeDate);
        return taskRepository.save(task);
    }
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") String id) {
		Task task = taskRepository.findOneById(id);
		taskRepository.delete(task);
    }
	
	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
	public List<Task> broadcasting() throws Exception {
		return taskRepository.findAll();
	}
}