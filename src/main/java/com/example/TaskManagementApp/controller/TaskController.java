package com.example.TaskManagementApp.controller;

import com.example.TaskManagementApp.model.Task;
import com.example.TaskManagementApp.model.User;
import com.example.TaskManagementApp.service.TaskService;
import com.example.TaskManagementApp.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private static final String SESSION_USER_ID = "userId";

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model,
                          @RequestParam(value="editId", required=false) Long editId) {
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);
        if (userId == null) return "redirect:/login";

        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            session.invalidate();
            return "redirect:/login";
        }

        model.addAttribute("username", user.getUsername());
        model.addAttribute("tasks", taskService.findTasksForUser(user));
        model.addAttribute("taskForm", new Task());
        model.addAttribute("today", java.time.LocalDate.now());

        Task taskForm;
        if (editId != null) {
            taskForm = taskService.findById(editId).orElse(new Task());
        } else {
            taskForm = new Task();
            taskForm.setStatus("Pending");
        }
        model.addAttribute("taskForm", taskForm);
        return "welcome";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@Valid @ModelAttribute("taskForm") Task task,
                           BindingResult result,
                           HttpSession session,
                           Model model) {
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);
        if (userId == null) return "redirect:/login";
        User user = userService.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";

        if (result.hasErrors()) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("tasks", taskService.findTasksForUser(user));
            return "welcome";
        }

        if (task.getId() != null) {
            Task existing = taskService.findById(task.getId()).orElse(null);
            if (existing != null) {
                existing.setTitle(task.getTitle());
                existing.setDescription(task.getDescription());
                existing.setDueDate(task.getDueDate());
                existing.setStatus(task.getStatus());
                existing.setUser(user);
                taskService.saveTask(existing);
            }
        } else {
            // new
            task.setUser(user);
            taskService.saveTask(task);
        }
        return "redirect:/welcome";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);
        if (userId == null) return "redirect:/login";
        taskService.findById(id).ifPresent(task -> {
            if (task.getUser() != null && task.getUser().getId().equals(userId)) {
                taskService.deleteById(id);
            }
        });
        return "redirect:/welcome";
    }

    @GetMapping("/tasks/edit/{id}")
    public String editTask(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);
        if (userId == null) return "redirect:/login";
        return "redirect:/welcome?editId=" + id;
    }
}


//REST API endpoints for Task Service

@RestController
@RequestMapping("/api/tasks")
class TaskRestController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskRestController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.findAll();  // <-- you’ll add this in TaskService + Impl
    }
@GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        return taskService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        if (task.getUser() == null || task.getUser().getId() == null) {
            return ResponseEntity.badRequest().build(); // must include user ID
        }

        User user = userService.findById(task.getUser().getId()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().build(); // invalid user
        }

        task.setUser(user);
        Task saved = taskService.saveTask(task);
        return ResponseEntity.created(URI.create("/api/tasks/" + saved.getId())).body(saved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.findById(id)
                .map(existing -> {
                    existing.setTitle(task.getTitle());
                    existing.setDescription(task.getDescription());
                    existing.setDueDate(task.getDueDate());
                    existing.setStatus(task.getStatus());
                    existing.setUser(task.getUser());
                    Task updated = taskService.saveTask(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        return taskService.findById(id)
                .map(t -> {
                    taskService.deleteById(id);
                    return ResponseEntity.ok("Task deleted successfully!");
                })
                .orElse(ResponseEntity.notFound().build());
    }

}