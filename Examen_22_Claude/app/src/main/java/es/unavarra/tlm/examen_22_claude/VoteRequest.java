package es.unavarra.tlm.examen_22_claude;

/*
 * ============================================================================
 * MODELO: VoteRequest.java
 * ============================================================================
 * Esta clase representa el JSON que enviamos al servidor para votar.
 *
 * JSON que queremos generar:
 * {"vote": 9}
 *
 * Muy similar a RoomRequest, pero con diferente nombre de atributo.
 * ============================================================================
 */
public class VoteRequest {

    /*
     * El voto seleccionado por el usuario.
     * Es el valor del botón que pulsó (4, 7, 9, 12 o 16 en el ejemplo).
     */
    private int vote;

    /*
     * Constructor
     */
    public VoteRequest(int vote) {
        this.vote = vote;
    }

    /*
     * Getter
     */
    public int getVote() {
        return vote;
    }

    /*
     * Setter
     */
    public void setVote(int vote) {
        this.vote = vote;
    }
}