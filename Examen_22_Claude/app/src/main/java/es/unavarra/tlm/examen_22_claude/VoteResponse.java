package es.unavarra.tlm.examen_22_claude;

/*
 * ============================================================================
 * MODELO: VoteResponse.java
 * ============================================================================
 * Esta clase representa el JSON que recibimos del servidor cuando el
 * voto es exitoso.
 *
 * JSON que recibimos:
 * {"selection": 9}
 *
 * "selection" contiene el valor que votamos, confirmado por el servidor.
 * ============================================================================
 */
public class VoteResponse {

    /*
     * El voto registrado por el servidor.
     * Es el mismo valor que enviamos en VoteRequest.
     */
    private int selection;

    /*
     * Getter
     */
    public int getSelection() {
        return selection;
    }

    /*
     * Setter
     */
    public void setSelection(int selection) {
        this.selection = selection;
    }
}