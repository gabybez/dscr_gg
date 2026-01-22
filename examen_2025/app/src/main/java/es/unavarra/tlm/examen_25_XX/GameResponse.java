package es.unavarra.tlm.examen_25_XX;

/*
 * ============================================================================
 * MODELO: GameResponse.java
 * ============================================================================
 * 
 * Representa la respuesta del servidor al pedir UN partido específico
 * o al actualizar la puntuación.
 * 
 * JSON de ejemplo:
 * {
 *   "game": {
 *     "id": "a7f8e2b3-4d5c-4a8f-9e2b-1c3d4e5f6789",
 *     "away": { "id": "...", "name": "Phoenix Suns", "score": 6 },
 *     "home": { "id": "...", "name": "Milwaukee Bucks", "score": 49 }
 *   }
 * }
 * 
 * DIFERENCIA CON GamesResponse:
 * - GamesResponse tiene "games" (array de partidos)
 * - GameResponse tiene "game" (un solo partido)
 */
public class GameResponse {
    
    // Un solo partido
    private Game game;
    
    public Game getGame() {
        return game;
    }
}
