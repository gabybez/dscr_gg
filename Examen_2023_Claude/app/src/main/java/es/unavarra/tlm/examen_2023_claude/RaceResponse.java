package es.unavarra.tlm.examen_2023_claude;

import java.util.List;

/**
 * Modelo para la respuesta completa del servidor.
 * JSON: { runners: [ ... ] }
 */
public class RaceResponse {
    private List<RunnerResult> runners;

    public List<RunnerResult> getRunners() {
        return runners;
    }
}