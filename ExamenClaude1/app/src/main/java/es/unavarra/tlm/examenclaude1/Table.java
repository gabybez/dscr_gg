package es.unavarra.tlm.examenclaude1;
//Representa una mesa del restaurante

public class Table {
    private int id;
    private int capacity;
    private Boolean available;

    //Getters
    public int getId(){
        return id;
    }
    public int getCapacity(){
        return capacity;
    }

    //Para boolean, el getter puede ser isAvailable() o getAvailable()
    public Boolean getAvailable() {
        return available;
    }
}
