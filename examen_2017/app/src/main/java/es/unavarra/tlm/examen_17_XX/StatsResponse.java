package es.unavarra.tlm.examen_17_XX;

import java.util.List;

/*
 * ============================================================================
 * MODELO: StatsResponse.java
 * ============================================================================
 * Representa la respuesta COMPLETA del servidor.
 * 
 * JSON completo:
 * {
 *   stats: [
 *     { name: "José", messages: 101, premium: true },
 *     { name: "Ramón", messages: 23, premium: false },
 *     ...
 *   ]
 * }
 * 
 * El atributo "stats" es un array, por eso usamos List<User>.
 */
public class StatsResponse {
    
    // Lista de usuarios (corresponde al array "stats" del JSON)
    private List<User> stats;
    
    public List<User> getStats() {
        return stats;
    }
}
