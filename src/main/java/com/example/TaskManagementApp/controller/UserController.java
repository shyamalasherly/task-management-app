package com.example.TaskManagementApp.controller;

import com.example.TaskManagementApp.model.User;
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
public class UserController {

    private final UserService userService;
    private static final String SESSION_USER_ID = "userId";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("usernameError", "Username already exists");
            return "register";
        }
        userService.saveUser(user);
        model.addAttribute("success", "Registration successful. Please login.");
        return "redirect:/login";
    }

    // Show login page
    @GetMapping({"/", "/login"})
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user,
                        Model model,
                        HttpSession session) {
        if (user.getUsername() == null || user.getPassword() == null) {
            model.addAttribute("error", "Please enter username and password");
            return "login";
        }
        return userService.findByUsernameAndPassword(user.getUsername(), user.getPassword())
                .map(u -> {
                    session.setAttribute(SESSION_USER_ID, u.getId());
                    return "redirect:/welcome";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Invalid credentials");
                    return "login";
                });
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}


//REST API
@RestController
@RequestMapping("/api/users")
class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build(); // username already exists
        }
        User saved = userService.saveUser(user);
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        return userService.findByUsernameAndPassword(user.getUsername(), user.getPassword())
                .map(u -> ResponseEntity.ok("Login successful for user: " + u.getUsername()))
                .orElse(ResponseEntity.status(401).body("Invalid username or password"));
    }
}