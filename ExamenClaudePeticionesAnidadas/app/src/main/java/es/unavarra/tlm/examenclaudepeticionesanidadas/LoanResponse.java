package es.unavarra.tlm.examenclaudepeticionesanidadas;
/*
 * Respuesta del préstamo exitoso
 * JSON: { "loan": { "id": "...", "bookId": "...", "date": "..." } }
 */
public class LoanResponse {
    private Loan loan;

    public Loan getLoan() {
        return loan;
    }
}
