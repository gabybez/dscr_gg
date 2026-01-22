package es.unavarra.tlm.examen_2025_claude;

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
