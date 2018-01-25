package org.nashorn.searchink;

import java.util.Date;
import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	public Task findOneById(String id);
	
	@Query("select a from Task a where a.resolvedat = NULL")
	public List<Task> findAllExceptPostpone();
	
	@Query("select a from Task a where a.duedate = NULL and a.resolvedat < ?1")
	public List<Task> findAllOverPostpone(Date now);
	
	@Query("select a from Task a where a.duedate <> NULL and a.resolvedat = NULL")
	public List<Task> findFirstForDue();
	
	@Query("select a from Task a where a.duedate < ?1 and a.resolvedat = NULL")
	public List<Task> findFirstOverDue(Date now);
	
	@Query("select a from Task a where a.duedate = NULL and a.resolvedat = NULL ORDER BY a.createdat ASC")
	public List<Task> findFirstForPerform();
}
