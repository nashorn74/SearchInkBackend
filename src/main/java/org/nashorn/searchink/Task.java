package org.nashorn.searchink;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Task {
	@Id
	private String id;	
	private Date createdat;
	private Date updatedat;
	private Date duedate;
	private Date resolvedat;
	private String title;
	private String description;
	private Integer priority;
	private Integer status;
	
	protected Task() {}
	
	public Task(Date createdat, Date updatedat, Date duedate, Date resolvedat, String title, 
			String description, Integer priority, Integer status) {
		this.id = UUID.randomUUID().toString();
		this.createdat = createdat;
		this.updatedat = updatedat;
		this.duedate = duedate;
		this.resolvedat = resolvedat;
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.status = status;
	}
	
	public Task(String id, Date createdat, Date updatedat, Date duedate, Date resolvedat, String title, 
			String description, Integer priority, Integer status) {
		this.id = id;
		this.createdat = createdat;
		this.updatedat = updatedat;
		this.duedate = duedate;
		this.resolvedat = resolvedat;
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.status = status;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	public Date getUpdatedat() {
		return updatedat;
	}

	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

	public Date getDuedate() {
		return duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public Date getResolvedat() {
		return resolvedat;
	}

	public void setResolvedat(Date resolvedat) {
		this.resolvedat = resolvedat;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
