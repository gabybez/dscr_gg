package es.unavarra.tlm.examen_2023_claude;

/**
 * Modelo para cada elemento del array "runners".
 *
 * JSON de cada elemento:
 * {
 *   runner: { name: "Paco", lastname: "Gómez" },
 *   gender: "M",
 *   number: 1,
 *   time: 104
 * }
 */
public class RunnerResult {
    private Runner runner;  // Objeto anidado
    private String gender;  // "M" o "F"
    private int number;     // Dorsal
    private int time;       // Tiempo en segundos

    // Campos adicionales que calcularemos nosotros (no vienen del JSON)
    private int position;         // Puesto general
    private int categoryPosition; // Puesto en su categoría

    // Getters
    public Runner getRunner() {
        return runner;
    }

    public String getGender() {
        return gender;
    }

    public int getNumber() {
        return number;
    }

    public int getTime() {
        return time;
    }

    public int getPosition() {
        return position;
    }

    public int getCategoryPosition() {
        return categoryPosition;
    }

    // Setters para los campos calculados
    public void setPosition(int position) {
        this.position = position;
    }

    public void setCategoryPosition(int categoryPosition) {
        this.categoryPosition = categoryPosition;
    }

    /**
     * Formatea el tiempo de segundos a "X m Y s"
     * Ejemplo: 109 segundos -> "1 m 49 s"
     */
    public String getFormattedTime() {
        int minutes = time / 60;
        int seconds = time % 60;
        return minutes + " m " + seconds + " s";
    }
}