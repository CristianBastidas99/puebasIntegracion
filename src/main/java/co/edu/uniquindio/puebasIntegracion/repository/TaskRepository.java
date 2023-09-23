package co.edu.uniquindio.puebasIntegracion.repository;

import co.edu.uniquindio.puebasIntegracion.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
