package es.unavarra.tlm.examen_2025_claude;

/*
 * ============================================================================
 * MODELO: ScoreRequest.java
 * ============================================================================
 *
 * Representa el cuerpo (body) de la petición PUT para actualizar puntuación.
 *
 * JSON que enviaremos:
 * {
 *   "teamId": "c9fae4d5-6e7f-4ca0-bf4d-3e5f60781901",
 *   "score": 2
 * }
 *
 * IMPORTANTE:
 * - teamId: UUID del equipo al que sumar puntos
 * - score: Cantidad de puntos a sumar (1, 2 o 3)
 */
public class ScoreRequest {

    // UUID del equipo
    private String teamId;

    // Puntos a sumar (1, 2 o 3)
    private int score;

    /*
     * Constructor para crear la petición fácilmente.
     * Ejemplo: new ScoreRequest("uuid-del-equipo", 2)
     */
    public ScoreRequest(String teamId, int score) {
        this.teamId = teamId;
        this.score = score;
    }

    // Getters (Gson los necesita para serializar)

    public String getTeamId() {
        return teamId;
    }

    public int getScore() {
        return score;
    }
}
