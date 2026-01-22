package es.unavarra.tlm.dscr_25_06;

/**
 * "Request" para las inactivas (cancelled/finished).
 * - GET /v2/game/inactive (sin body).
 */
public class ListarInactivosRequest {
    public String buildUrl(String baseInactiveUrl) {
        return baseInactiveUrl;
    }
}
