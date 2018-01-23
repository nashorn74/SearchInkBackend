package org.nashorn.searchink;

import java.util.Date;
import lombok.Data;

public class TaskDto {
	@Data
	public static class Task{
		private String id;
		private Date createdat;
		private Date updatedat;
		private Date duedate;
		private Date resolvedat;
		private String title;
		private String description;
		private Integer priority;
		private Integer status;
		
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
}
