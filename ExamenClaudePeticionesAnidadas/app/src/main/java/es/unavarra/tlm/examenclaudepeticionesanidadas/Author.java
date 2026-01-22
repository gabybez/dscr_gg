package es.unavarra.tlm.examenclaudepeticionesanidadas;
/*
 * ============================================================================
 * MODELO: Author.java
 * ============================================================================
 *
 * JSON:
 * {
 *   "id": "author-001",
 *   "name": "Miguel de Cervantes",
 *   "country": "España",
 *   "birthYear": 1547,
 *   "deathYear": 1616
 * }
 */
public class Author {
    private String id;
    private String name;
    private String country;
    private int birthYear;
    private int deatchYear;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeathYear() {
        return deatchYear;
    }
}
