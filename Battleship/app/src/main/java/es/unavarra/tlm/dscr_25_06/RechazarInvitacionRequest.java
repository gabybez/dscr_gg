package es.unavarra.tlm.dscr_25_06;

/**
 * Request para rechazar/cancelar:
 *   DELETE /v2/game/{game_id}
 * No lleva body, solo construimos la URL.
 */
public class RechazarInvitacionRequest {
    private final long gameId;
    public RechazarInvitacionRequest(long gameId) { this.gameId = gameId; }

    public String buildUrl(String baseGameUrl) {
        return baseGameUrl.endsWith("/") ? baseGameUrl + gameId : baseGameUrl + "/" + gameId;
    }
}
