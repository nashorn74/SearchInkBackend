package org.nashorn.searchink;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	public Task findOneById(String id);
	
	@Query("select a from Task a where a.duedate = NULL and a.resolvedat = NULL")
	public List<Task> findAllExceptPostpone();
	
	@Query("select a from Task a where a.duedate = NULL and a.resolvedat < ?1")
	public List<Task> findAllOverPostpone(Date now);
}
