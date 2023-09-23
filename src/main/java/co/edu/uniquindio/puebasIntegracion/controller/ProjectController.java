package co.edu.uniquindio.puebasIntegracion.controller;

import co.edu.uniquindio.puebasIntegracion.entity.*;
import co.edu.uniquindio.puebasIntegracion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long projectId) {
        return projectRepository.findById(projectId)
                .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project createdProject = projectRepository.save(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PostMapping("/assignUser/{projectId}/{userId}")
    public ResponseEntity<Project> assignUserToProject(
            @PathVariable Long projectId, @PathVariable Long userId) {

        Project project = projectRepository.findById(projectId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (project != null && user != null) {
            project.getUsers().add(user);
            user.getProjects().add(project);
            projectRepository.save(project);
            userRepository.save(user);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Otras operaciones como actualizar y eliminar proyectos

}

