package com.example.TaskManagementApp.repository;

import com.example.TaskManagementApp.model.Task;
import com.example.TaskManagementApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserOrderByDueDateAsc(User user);
}
