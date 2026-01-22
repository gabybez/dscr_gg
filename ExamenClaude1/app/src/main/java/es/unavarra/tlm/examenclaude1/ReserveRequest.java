package es.unavarra.tlm.examenclaude1;
//Lo que mandamos al servidor para hacer la reserva
public class ReserveRequest {
    private String name;
    private int guests;

    /*
     * Constructor para crear la petición fácilmente.
     * Ejemplo: new ScoreRequest("uuid-del-equipo", 2)
     */
    public ReserveRequest(String name, int guests){
        this.name = name;
        this.guests = guests;
    }
    //Getters (supuestamente necesarios por Gson, aunque creo que realmente no lo son...)
    public String getName() {
        return name;
    }

    public int getGuests() {
        return guests;
    }
}
