package co.edu.uniquindio.puebasIntegracion.repository;

import co.edu.uniquindio.puebasIntegracion.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
