package es.unavarra.tlm.examen_25_XX;

/*
 * ============================================================================
 * MODELO: ErrorResponse.java
 * ============================================================================
 * 
 * Representa la respuesta de error del servidor (HTTP 400).
 * 
 * JSON de ejemplo:
 * {
 *   "code": "TEAM_ID_REQUIRED"
 * }
 * 
 * CÓDIGOS DE ERROR POSIBLES:
 * - TEAM_ID_REQUIRED: El identificador de equipo es obligatorio
 * - INVALID_TEAM_ID: El equipo no juega este partido
 * - SCORE_REQUIRED: Los puntos anotados son obligatorios
 * - SCORE_INVALID: Cantidad de puntos no válida
 */
public class ErrorResponse {
    
    // Código de error
    private String code;
    
    public String getCode() {
        return code;
    }
}
