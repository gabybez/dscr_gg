package es.unavarra.tlm.dscr_25_06;

/**
 * Cuerpo JSON para invitar a una partida.
 * Ejemplo de JSON enviado: { "opponent": "pepe" }
 */

import com.google.gson.annotations.SerializedName;

/** Body para /v2/challenge */
public class InvitarPartidaRequest {

    // Si el backend quiere "username", garantízalo con SerializedName
    @SerializedName("username")
    private final String username;

    public InvitarPartidaRequest(String username) {
        this.username = username;
    }
}
