package es.unavarra.tlm.dscr_25_06;

import java.util.List;

/** POST /v2/game/{game_id}/ship  body: { ship_type, positions } */
public class PlaceShipRequest {
    public ShipType ship_type;        // el nombre exacto del spec
    public List<Position> positions;  // posiciones consecutivas

    public PlaceShipRequest(ShipType shipType, List<Position> positions) {
        this.ship_type = shipType;
        this.positions = positions;
    }

    public String buildUrl(String baseGameUrl, long gameId) {
        String base = baseGameUrl.endsWith("/") ? baseGameUrl : baseGameUrl + "/";
        return base + gameId + "/ship";
    }
}
