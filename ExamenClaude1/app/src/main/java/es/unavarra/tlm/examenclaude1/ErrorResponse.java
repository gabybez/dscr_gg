package es.unavarra.tlm.examenclaude1;
//Representa la respuesta que nos da el servidor cuando hay un error
//NAME_REQUIRED GUESTS_REQUIRED GUESTS_EXCEEDED TABLE_NOT_AVAILABLE
public class ErrorResponse {
    private String code;

    public String getCode() {
        return code;
    }
}
