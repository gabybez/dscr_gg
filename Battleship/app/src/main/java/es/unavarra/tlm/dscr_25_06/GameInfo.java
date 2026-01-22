package es.unavarra.tlm.dscr_25_06;

/**
 * Campos clave:
 *  - id:        identificador único de la partida
 *  - state:     "waiting" | "placing" | "started" | "finished" | "cancelled"
 *  - you:       usuario actual (tú)
 *  - enemy:     rival
 *  - your_turn: boolean con significado según estado:
 *               * waiting: true => debes responder tú (aceptar/rechazar),
 *                          false => espera la respuesta del rival
 *               * placing: true => aún te faltan barcos por colocar,
 *                          false => ya terminaste de colocar
 *               * started: true => es tu turno de disparar,
 *                          false => turno del rival
 *  - you_won:   (solo en finished) true si ganaste; puede venir null si no aplica
 *  - *_at:      marcas temporales ISO 8601 (pueden ser null según estado)
 */
public class GameInfo {

    // --- Campos (dejados públicos para compatibilidad con tu código actual) ---
    public long id;
    public String state;        // waiting | placing | started | finished | cancelled
    public User you;            // tú
    public User enemy;          // el otro jugador
    public boolean your_turn;   // semántica descrita arriba
    public Boolean you_won;     // puede ser null si el juego no ha finalizado

    // Fechas (ISO 8601) opcionales
    public String updated_at;
    public String created_at;
    public String started_at;
    public String finished_at;

    // --- Getters  ---
    public long getId() { return id; }
    public String getState() { return state; }
    public User getYou() { return you; }
    public User getEnemy() { return enemy; }
    public boolean isYourTurn() { return your_turn; }
    public Boolean getYouWon() { return you_won; }
    public String getUpdatedAt() { return updated_at; }
    public String getCreatedAt() { return created_at; }
    public String getStartedAt() { return started_at; }
    public String getFinishedAt() { return finished_at; }

    // --- Atajos de estado ---
    public boolean isWaiting()   { return "waiting".equalsIgnoreCase(state); }
    public boolean isPlacing()   { return "placing".equalsIgnoreCase(state); }
    public boolean isStarted()   { return "started".equalsIgnoreCase(state); }
    public boolean isFinished()  { return "finished".equalsIgnoreCase(state); }
    public boolean isCancelled() { return "cancelled".equalsIgnoreCase(state); }

    /** En waiting: true si te toca responder a ti (aceptar/rechazar). */
    public boolean isPendingMyDecision() { return isWaiting() && your_turn; }

    /** En waiting: true si está pendiente de que el rival responda. */
    public boolean isPendingTheirDecision() { return isWaiting() && !your_turn; }

    @Override
    public String toString() {
        return "GameInfo{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", your_turn=" + your_turn +
                ", you_won=" + you_won +
                '}';
    }
}
