package es.unavarra.tlm.dscr_25_06;

/**
 * Representa la sesión activa devuelta por la API (token y fecha de caducidad).
 * Para autenticar peticiones futuras y para persistir el login.
 */
public class Session {
    // Token de autenticación (lo enviaremos como cabecera X-Authentication)
    private String token;
    private String valid_until;

    public String getToken() { return token; }
    public String getValid_until() { return valid_until; }
}
