package es.unavarra.tlm.carlos_examen_2025ord;

/*
 * ============================================================================
 * MODELO: Team.java
 * ============================================================================
 *
 * Representa un equipo de baloncesto.
 *
 * JSON de ejemplo:
 * {
 *   "id": "b8f9e3c4-5d6e-4b9f-af3c-2d4e5f607890",
 *   "name": "Phoenix Suns",
 *   "score": 6
 * }
 *
 * IMPORTANTE sobre UUID:
 * - En JSON se trata como String (texto)
 * - En Java lo guardamos como String también
 * - Si necesitáramos operaciones UUID, usaríamos java.util.UUID
 */
public class Team {

    // Identificador único del equipo (UUID como String)
    private String id;

    // Nombre del equipo
    private String name;

    // Puntuación actual
    private int score;

    // ========================================================================
    // GETTERS
    // ========================================================================

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    // ========================================================================
    // SETTER para actualizar puntuación localmente
    // ========================================================================

    public void setScore(int score) {
        this.score = score;
    }
}
