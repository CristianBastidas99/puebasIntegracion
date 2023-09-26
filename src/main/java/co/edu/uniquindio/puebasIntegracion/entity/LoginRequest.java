package co.edu.uniquindio.puebasIntegracion.entity;

public class LoginRequest {
    private String email;
    private String password;

    public LoginRequest() {
        // Constructor vacío requerido por Jackson para la deserialización.
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
