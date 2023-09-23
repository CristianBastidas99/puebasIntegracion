package co.edu.uniquindio.puebasIntegracion.integration;

import co.edu.uniquindio.puebasIntegracion.controller.*;
import co.edu.uniquindio.puebasIntegracion.entity.*;
import co.edu.uniquindio.puebasIntegracion.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskIntegrationTest {

    @Autowired
    private ProjectController projectController;

    @Autowired
    private UserController userController;

    @Autowired
    private TaskController taskController;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTitle("Nueva tarea");
        task.setCompleted(false);

        Task savedTask = taskRepository.save(task);

        assertNotNull(savedTask.getId());
        assertEquals("Nueva tarea", savedTask.getTitle());
        assertFalse(savedTask.isCompleted());
    }

    @Test
    public void testCreateAndRetrieveTask() {
        // Crear una nueva tarea utilizando el controlador
        Task task = new Task();
        task.setTitle("Nueva tarea");
        task.setCompleted(false);

        Task savedTask = taskController.createTask(task);

        // Obtener la tarea de la base de datos utilizando el repositorio
        Task retrievedTask = taskRepository.findById(savedTask.getId()).orElse(null);

        // Asegurarse de que la tarea creada y la tarea recuperada sean las mismas
        assertNotNull(retrievedTask);
        assertEquals("Nueva tarea", retrievedTask.getTitle());
        assertFalse(retrievedTask.isCompleted());
    }

    @Test
    public void testCreateAndAssignTaskToUser() {
        // Crear dos usuarios
        User user1 = new User();
        user1.setUsername("usuario1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("usuario2");
        userRepository.save(user2);

        // Crear una tarea
        Task task = new Task();
        task.setTitle("Nueva tarea");
        task.setCompleted(false);
        taskRepository.save(task);

        // Asignar la tarea al primer usuario
        ResponseEntity<Task> response1 = taskController.assignTaskToUser(task.getId(), user1.getId());

        // Reasignar la tarea al segundo usuario
        ResponseEntity<Task> response2 = taskController.assignTaskToUser(task.getId(), user2.getId());

        // Verificar que la reasignación al segundo usuario se haya realizado correctamente
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        Task assignedTask2 = response2.getBody();
        assertNotNull(assignedTask2.getAssignedUser());
        assertEquals(user2.getId(), assignedTask2.getAssignedUser().getId());
    }

    @Test
    @Transactional
    public void testCreateProjectAndAssignUserAndTask() {
        // Crear un usuario
        User user = new User();
        user.setUsername("Ximena");
        userRepository.save(user);

        // Crear un proyecto
        Project project = new Project();
        project.setName("Proyecto Software");
        projectRepository.save(project);

        // Asignar el usuario al proyecto
        ResponseEntity<Project> response1 = projectController.assignUserToProject(project.getId(), user.getId());

        // Verificar que la asignación del usuario al proyecto se haya realizado correctamente
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        Project assignedProject = response1.getBody();
        assertNotNull(assignedProject);
        assertTrue(assignedProject.getUsers().contains(user));

        // Crear una tarea
        Task task = new Task();
        task.setTitle("Automatizar la prueba y mostrar funcionamiento");
        task.setCompleted(false);
        taskRepository.save(task);

        // Asignar la tarea al usuario
        ResponseEntity<Task> response2 = taskController.assignTaskToUser(task.getId(), user.getId());

        // Verificar que la asignación de la tarea al usuario se haya realizado correctamente
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        Task assignedTaskToUser = response2.getBody();
        assertNotNull(assignedTaskToUser);
        assertEquals(user.getId(), assignedTaskToUser.getAssignedUser().getId());

        // Asignar la tarea al proyecto
        ResponseEntity<Task> response3 = taskController.assignTaskToProject(task.getId(), project.getId());

        // Verificar que la asignación de la tarea al proyecto se haya realizado correctamente
        assertEquals(HttpStatus.OK, response3.getStatusCode());
        Task assignedTaskToProject = response3.getBody();
        assertNotNull(assignedTaskToProject);
        assertEquals(project.getId(), assignedTaskToProject.getProject().getId());
    }

}
