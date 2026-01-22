package es.unavarra.tlm.ordinario2025solo;

import java.util.List;

//Representa lo que nos devuelve el servidor al hacer el get. Al hacer el get nos devuelve una lista de games
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
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

}
