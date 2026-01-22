package es.unavarra.tlm.examen_22_claude;

/*
 * ============================================================================
 * MODELO: ErrorResponse.java
 * ============================================================================
 * Esta clase representa el JSON de error que recibimos del servidor
 * cuando algo sale mal (código HTTP 400).
 *
 * JSON que recibimos:
 * {"error": 1}
 *
 * CÓDIGOS DE ERROR PARA SALA (POST):
 * - error: 1 -> El número de sala es obligatorio
 * - error: 2 -> Valor de sala no válida
 *
 * CÓDIGOS DE ERROR PARA VOTO (PUT):
 * - error: 1 -> El voto es obligatorio
 * - error: 2 -> Valor de voto no válido
 *
 * NOTA: Los códigos son los mismos pero significan cosas diferentes
 * según la petición que estemos haciendo.
 * ============================================================================
 */
public class ErrorResponse {

    /*
     * Código de error devuelto por el servidor.
     */
    private int error;

    /*
     * Getter
     */
    public int getError() {
        return error;
    }

    /*
     * Setter
     */
    public void setError(int error) {
        this.error = error;
    }
}