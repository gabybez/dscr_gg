package es.unavarra.tlm.examen2017xx;

import java.util.List;

/**
 * Modelo para la respuesta completa del servidor.
 * JSON: { stats: [ ... ] }
 */
public class StatsResponse {
    private List<StatsResult> stats;

    public List<StatsResult> getStats() {
        return stats;
    }
}