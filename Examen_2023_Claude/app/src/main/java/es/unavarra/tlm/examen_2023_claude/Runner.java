package es.unavarra.tlm.examen_2023_claude;

/**
 * Modelo para el objeto anidado "runner" dentro de cada elemento.
 * JSON: { "name": "Paco", "lastname": "Gómez" }
 */
public class Runner {
    private String name;
    private String lastname;

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    // Método útil para obtener nombre completo
    public String getFullName() {
        return name + " " + lastname;
    }
}