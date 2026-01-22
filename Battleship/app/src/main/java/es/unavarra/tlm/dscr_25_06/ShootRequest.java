// ShootRequest.java
package es.unavarra.tlm.dscr_25_06;

import com.google.gson.annotations.SerializedName;

/** Body para POST /v2/game/{id}/shoot { position: Position } */
public class ShootRequest {
    @SerializedName("position")
    public Position position;

    public ShootRequest(Position position) { this.position = position; }

    public String buildUrl(String baseGameUrl, long gameId) {
        String base = baseGameUrl.endsWith("/") ? baseGameUrl : baseGameUrl + "/";
        return base + gameId + "/shoot";
    }
}
