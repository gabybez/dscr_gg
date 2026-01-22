package es.unavarra.tlm.dscr_25_06;

/**
 * Request para aceptar una invitación:
 *   POST /v2/game/{game_id}
 * No lleva body, solo construimos la URL.
 */
public class AceptarInvitacionRequest {
    private final long gameId;
    public AceptarInvitacionRequest(long gameId) { this.gameId = gameId; }

    public String buildUrl(String baseGameUrl) {
        // Funciona con base que tenga o no barra final:
        return baseGameUrl.endsWith("/") ? baseGameUrl + gameId : baseGameUrl + "/" + gameId;
    }
}
