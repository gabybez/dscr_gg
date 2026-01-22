package es.unavarra.tlm.dscr_25_06;

/**
 * Construye la URL para el endpoint:
 *   GET /v2/game/{game_id}
 *
 * Uso:
 *   String url = new GetGameDetailRequest(gameId).buildUrl(getString(R.string.api_game_base));
 * donde api_game_base es "https://api.battleship.tatai.es/v2/game/".
 */
public class GetGameDetailRequest {

    private final long gameId;

    public GetGameDetailRequest(long gameId) {
        this.gameId = gameId;
    }

    /** Devuelve la URL final tolerante a base con/sin barra final. */
    public String buildUrl(String baseGameUrl) {
        String base = baseGameUrl.endsWith("/") ? baseGameUrl : baseGameUrl + "/";
        return base + gameId;
    }
}
