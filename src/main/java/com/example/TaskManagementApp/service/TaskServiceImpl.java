package com.example.TaskManagementApp.service;

import com.example.TaskManagementApp.model.Task;
import com.example.TaskManagementApp.model.User;
import com.example.TaskManagementApp.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repo;

    public TaskServiceImpl(TaskRepository repo) {
        this.repo = repo;
    }

    @Override
    public Task saveTask(Task task) {
        return repo.save(task);
    }

    @Override
    public List<Task> findTasksForUser(User user) {
        return repo.findByUserOrderByDueDateAsc(user);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public List<Task> findAll() {
        return repo.findAll();
    }
}
