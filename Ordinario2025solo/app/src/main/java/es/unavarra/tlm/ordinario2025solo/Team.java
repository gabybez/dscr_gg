package es.unavarra.tlm.ordinario2025solo;

//Es el primero que hago, es el más basico. Representa un equipo completo

/*
 * ============================================================================
 * MODELO: Team.java
 * ============================================================================
 *
 * Representa un equipo de baloncesto.
 *
 * JSON de ejemplo:
 * {
 *   "id": "b8f9e3c4-5d6e-4b9f-af3c-2d4e5f607890",
 *   "name": "Phoenix Suns",
 *   "score": 6
 * }
 *
 */

public class Team {
    private String id;
    private String name;
    private int score;

    //Getters
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getScore(){
        return score;
    }

    //Setter (para actualizar la puntuación en la parte de añadir puntos, más adelante)

    public void setScore(int score) {
        this.score = score;
    }
}
