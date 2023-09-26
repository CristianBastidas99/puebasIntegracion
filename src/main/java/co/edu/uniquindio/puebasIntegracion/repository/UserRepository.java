package co.edu.uniquindio.puebasIntegracion.repository;

import co.edu.uniquindio.puebasIntegracion.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

}
