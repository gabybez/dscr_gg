package es.unavarra.tlm.ordinario2025solo;
//Esta es para formar el body del put
public class ScoreRequest {

    private String teamId;
    private int score;

    /*
     * Constructor para crear la petición fácilmente.
     * Ejemplo: new ScoreRequest("uuid-del-equipo", 2)
     */

    public ScoreRequest(String teamId, int score){
        this.teamId = teamId;
        this.score = score;
    }

    //Getters necesarios para serializar (los necesita gson)

    public String getTeamId() {
        return teamId;
    }

    public int getScore() {
        return score;
    }
}
