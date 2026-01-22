package es.unavarra.tlm.examenclaudepeticionesanidadas;
/*
 * ============================================================================
 * MODELO: BookDetail.java (detalle completo del libro)
 * ============================================================================
 *
 * JSON:
 * {
 *   "id": "book-001",
 *   "title": "El Quijote",
 *   "year": 1605,
 *   "pages": 863,
 *   "available": true,
 *   "authorId": "author-001"
 * }
 *
 * IMPORTANTE: authorId se usa para hacer la SEGUNDA petición (anidada)
 */

public class BookDetail {
    private String id;
    private String title;
    private int year;
    private int pages;
    private boolean available;
    private String authorId;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getPages() {
        return pages;
    }

    public boolean getAvailable() {
        return available;
    }

    public String getAuthorId() {
        return authorId;
    }

}
