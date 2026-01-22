package es.unavarra.tlm.carlos_examen_2025ord;

import java.util.List;

/*
 * ============================================================================
 * MODELO: GamesResponse.java
 * ============================================================================
 *
 * Representa la respuesta del servidor al pedir el listado de partidos.
 *
 * JSON de ejemplo:
 * {
 *   "games": [
 *     { "id": "...", "away": {...}, "home": {...} },
 *     { "id": "...", "away": {...}, "home": {...} }
 *   ]
 * }
 *
 * El atributo "games" es un array de partidos.
 */
public class GamesResponse {

    // Lista de partidos
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }
}
