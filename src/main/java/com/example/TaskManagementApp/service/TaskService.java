package com.example.TaskManagementApp.service;

import com.example.TaskManagementApp.model.Task;
import com.example.TaskManagementApp.model.User;
import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task saveTask(Task task);
    List<Task> findTasksForUser(User user);
    Optional<Task> findById(Long id);
    void deleteById(Long id);

    //REST API
    List<Task> findAll();
}
