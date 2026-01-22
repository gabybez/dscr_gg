package es.unavarra.tlm.dscr_25_06;

/**
 * Cuerpo JSON que enviamos a PUT /v2/session para hacer login.
 * Gson convertirá este objeto en un JSON {"username":"...","password":"..."}.
 */
public class LoginRequest {
    String username;
    String password;

    public LoginRequest(String u, String p) {
        this.username = u;
        this.password = p;
    }
}
