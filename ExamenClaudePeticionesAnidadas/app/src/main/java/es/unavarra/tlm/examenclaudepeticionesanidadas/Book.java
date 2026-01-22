package es.unavarra.tlm.examenclaudepeticionesanidadas;
/*
 * ============================================================================
 * MODELO: Book.java (para el listado)
 * ============================================================================
 *
 * JSON:
 * {
 *   "id": "book-001",
 *   "title": "El Quijote",
 *   "available": true
 * }
 */

public class Book {
    private String id;
    private String title;
    private Boolean available;

    public String getId() {
        return id;
    }
    public String getTitle(){
        return title;
    }

    public Boolean getAvailable() {
        return available;
    }
}
