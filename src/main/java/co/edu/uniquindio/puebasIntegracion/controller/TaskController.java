package co.edu.uniquindio.puebasIntegracion.controller;

import co.edu.uniquindio.puebasIntegracion.entity.*;
import co.edu.uniquindio.puebasIntegracion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PostMapping("/assign/{taskId}/{userId}")
    public ResponseEntity<Task> assignTaskToUser(
            @PathVariable Long taskId, @PathVariable Long userId) {

        Task task = taskRepository.findById(taskId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (task != null && user != null) {
            task.setAssignedUser(user);
            taskRepository.save(task);
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
