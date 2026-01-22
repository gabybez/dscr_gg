package es.unavarra.tlm.examen_24_XX;

import java.util.List;

/*
 * ============================================================================
 * MODELO: ComponentsResponse.java
 * ============================================================================
 * 
 * Respuesta del servidor al pedir los componentes.
 * 
 * JSON:
 * {
 *   "components": [
 *     { "id": "...", "name": "...", "price": ... },
 *     ...
 *   ]
 * }
 */
public class ComponentsResponse {
    
    private List<Component> components;
    
    public List<Component> getComponents() {
        return components;
    }
}
