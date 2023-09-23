package co.edu.uniquindio.puebasIntegracion.integration;

import co.edu.uniquindio.puebasIntegracion.controller.TaskController;
import co.edu.uniquindio.puebasIntegracion.entity.Task;
import co.edu.uniquindio.puebasIntegracion.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskController taskController;

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

}
