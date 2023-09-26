package co.edu.uniquindio.puebasIntegracion.integration;

import co.edu.uniquindio.puebasIntegracion.entity.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @Autowired
    private WebClient.Builder webClientBuilder;
    @LocalServerPort
    private int port; // Spring Boot asignará automáticamente el puerto aleatorio

    @Test
    //@Sql({"classpath:data.sql"})
    public void testLoginWithValidCredentials() {
        // Crea un WebClient para realizar la solicitud HTTP
        WebClient webClient = webClientBuilder.baseUrl("http://localhost:" + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // Datos de inicio de sesión
        LoginRequest loginRequest = new LoginRequest("usuario@example.com", "12345");

        // Realiza una solicitud POST al endpoint de inicio de sesión
        String response = webClient.method(HttpMethod.POST)
                .uri("/login")
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Espera la respuesta síncronamente

        // Verifica que el inicio de sesión haya sido exitoso
        // Puedes agregar más verificaciones según sea necesario
        assertNotNull(response);
    }
}
