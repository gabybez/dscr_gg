package es.unavarra.tlm.examen_24_XX;

import java.util.List;

/*
 * ============================================================================
 * MODELO: ProductsResponse.java
 * ============================================================================
 * 
 * Respuesta del servidor al pedir los productos.
 * 
 * JSON:
 * {
 *   "products": [
 *     { "id": "...", "name": "...", "componentIds": [...] },
 *     ...
 *   ]
 * }
 */
public class ProductsResponse {
    
    private List<Product> products;
    
    public List<Product> getProducts() {
        return products;
    }
}
