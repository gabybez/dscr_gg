package es.unavarra.tlm.examen_24_XX;

/*
 * ============================================================================
 * MODELO: Component.java
 * ============================================================================
 * 
 * Representa un componente electrónico.
 * 
 * JSON:
 * {
 *   "id": "comp-cpu-1",
 *   "name": "CPU",
 *   "price": 150.00
 * }
 * 
 * Este modelo se usa para:
 * 1. Buscar componentes por su ID
 * 2. Obtener el nombre para mostrarlo
 * 3. Obtener el precio para sumarlo al total
 */
public class Component {
    
    private String id;
    private String name;
    private float price;  // Usamos float para decimales (también podría ser double)
    
    // Getters
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public float getPrice() {
        return price;
    }
}
