package es.unavarra.tlm.examenclaude1;
//Representa una reserva confirmada
public class Reservation {
    private String id;
    private int table;
    private String name;
    private int guests;

    //Getters

    public String getId() {
        return id;
    }
    public int getTable() {
        return table;
    }
    public String getName() {
        return name;
    }
    public int getGuests() {
        return guests;
    }
}
