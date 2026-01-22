package es.unavarra.tlm.examenclaude1;

/*
 * ============================================================================
 * MODELO: ReserveResponse.java (igual es un poco inutil, pero bueno)
 * ============================================================================
 *
 * Respuesta exitosa del servidor tras crear una reserva.
 *
 * JSON:
 * {
 *   "reservation": {
 *     "id": "abc123",
 *     "table": 3,
 *     "name": "Juan García",
 *     "guests": 3
 *   }
 * }
 */
public class ReserveResponse {

    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }
}
