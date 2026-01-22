package es.unavarra.tlm.examen_25_XX;

/*
 * ============================================================================
 * MODELO: Game.java
 * ============================================================================
 * 
 * Representa un partido de baloncesto.
 * 
 * JSON de ejemplo:
 * {
 *   "id": "a7f8e2b3-4d5c-4a8f-9e2b-1c3d4e5f6789",
 *   "away": {
 *     "id": "b8f9e3c4-5d6e-4b9f-af3c-2d4e5f607890",
 *     "name": "Phoenix Suns",
 *     "score": 6
 *   },
 *   "home": {
 *     "id": "c9fae4d5-6e7f-4ca0-bf4d-3e5f60781901",
 *     "name": "Milwaukee Bucks",
 *     "score": 47
 *   }
 * }
 * 
 * Contiene:
 * - id: UUID del partido
 * - away: Equipo visitante (objeto Team)
 * - home: Equipo local (objeto Team)
 */
public class Game {
    
    // Identificador único del partido (UUID como String)
    private String id;
    
    // Equipo visitante
    private Team away;
    
    // Equipo local (juega en casa)
    private Team home;
    
    // ========================================================================
    // GETTERS
    // ========================================================================
    
    public String getId() {
        return id;
    }
    
    public Team getAway() {
        return away;
    }
    
    public Team getHome() {
        return home;
    }
}
