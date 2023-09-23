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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskController taskController;

    @Autowired
    private UserRepository userRepository;

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

        // Verificar que la asignación al primer usuario se haya realizado correctamente
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        Task assignedTask1 = response1.getBody();
        assertNotNull(assignedTask1.getAssignedUser());
        assertEquals(user1.getId(), assignedTask1.getAssignedUser().getId());

        // Asignar la tarea al segundo usuario
        ResponseEntity<Task> response2 = taskController.assignTaskToUser(task.getId(), user2.getId());

        // Verificar que la reasignación al segundo usuario se haya realizado correctamente
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        Task assignedTask2 = response2.getBody();
        assertNotNull(assignedTask2.getAssignedUser());
        assertEquals(user2.getId(), assignedTask2.getAssignedUser().getId());
    }

}
