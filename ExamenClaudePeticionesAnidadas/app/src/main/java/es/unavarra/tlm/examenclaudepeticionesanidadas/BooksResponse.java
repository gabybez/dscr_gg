package es.unavarra.tlm.examenclaudepeticionesanidadas;

import java.util.List;
/*
 * Respuesta del listado de libros
 * JSON: { "books": [...] }
 */

public class BooksResponse {
    private List<Book> books;
    public List<Book> getBooks() {
        return books;
    }

}
