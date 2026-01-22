package es.unavarra.tlm.examen_24_XX;

import java.util.List;

/*
 * ============================================================================
 * MODELO: Product.java
 * ============================================================================
 * 
 * Representa un producto de la tienda.
 * 
 * JSON:
 * {
 *   "id": "prod-001",
 *   "name": "Smartphone X1",
 *   "componentIds": ["comp-cpu-1", "comp-ram-1", "comp-screen-1"]
 * }
 * 
 * IMPORTANTE:
 * - componentIds es una lista de IDs (Strings)
 * - Para obtener el precio y nombre de cada componente, 
 *   necesitamos buscarlos en la lista de Components
 */
public class Product {
    
    private String id;
    private String name;
    private List<String> componentIds;  // Lista de IDs de componentes
    
    // Getters
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public List<String> getComponentIds() {
        return componentIds;
    }
}
