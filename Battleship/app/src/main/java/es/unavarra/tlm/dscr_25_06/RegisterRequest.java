package es.unavarra.tlm.dscr_25_06;

/**
 * Cuerpo JSON que enviamos a PUT /v2/user para registrar.
 */
public class RegisterRequest {
    String username;
    String password;

    public RegisterRequest(String u, String p) {
        this.username = u;
        this.password = p;
    }
}
