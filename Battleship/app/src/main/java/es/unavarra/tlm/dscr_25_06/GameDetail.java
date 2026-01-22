package es.unavarra.tlm.dscr_25_06;

import java.util.List;

/** Contenedor para GET /v2/game/{game_id} */
public class GameDetail {
    public GameInfo game;     // obligatorio
    public List<Ship> ships;  // tus barcos colocados
    public Gunfire gunfire;   // disparos hechos/recibidos
}
