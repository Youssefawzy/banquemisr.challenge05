package com.banquemisr.challenge05.repository;

import com.banquemisr.challenge05.enums.TaskStatus;
import com.banquemisr.challenge05.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByDueDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsByTitle(String title);

    List<Task> findByTitleContainingIgnoreCase(String title);

    List<Task> findByDescriptionContainingIgnoreCase(String description);

    List<Task> findByStatus(TaskStatus status);

    @Query(value = "SELECT t FROM Task t WHERE t.dueDate <= :dueDate")
    List<Task> findTasksDueOnOrBefore(@Param("dueDate") LocalDate dueDate);


}
