package es.unavarra.tlm.examenclaude1;

import java.util.List;

//Respuesta del servidor al pedir el listado de mesas
public class TablesResponse {
    private List<Table> tables;
    public List<Table> getTables() {
        return tables;
    }
}
