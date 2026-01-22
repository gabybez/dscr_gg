package es.unavarra.tlm.examen2017xx;

/**
 * Modelo para cada elemento del array "stats".
 * JSON de cada elemento:
 * {
 *   name: "Jose",
 *   messages: 101,
 *   premium: true,
 * }
 */
public class StatsResult {
    private String name;  // nombre
    private int messages;  // "cantidad de mensajes"
    private boolean premium;       // true or false



    // Getters
    public String getName() {
        return name;
    }

    public int getMessages() {
        return messages;
    }

    public boolean getPremium() {
        return premium;
    }
}