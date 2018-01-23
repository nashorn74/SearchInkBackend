package org.nashorn.searchink;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Task> getExams() {
        return taskRepository.findAll();
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
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") String id) {
		Task task = taskRepository.findOneById(id);
		taskRepository.delete(task);
    }
}