package es.unavarra.tlm.examen_17_XX;

/*
 * ============================================================================
 * MODELO: User.java
 * ============================================================================
 * Representa cada elemento del array "stats" en el JSON.
 * 
 * JSON de cada elemento:
 * { name: "José", messages: 101, premium: true }
 * 
 * IMPORTANTE: Los nombres de los atributos DEBEN coincidir con las claves del JSON.
 * - "name" en JSON -> private String name;
 * - "messages" en JSON -> private int messages;
 * - "premium" en JSON -> private boolean premium;
 */
public class User {
    
    // Nombre del usuario (String)
    private String name;
    
    // Cantidad de mensajes enviados (int)
    private int messages;
    
    // Si es usuario de pago (boolean)
    private boolean premium;
    
    // ========================================================================
    // GETTERS
    // ========================================================================
    
    public String getName() {
        return name;
    }
    
    public int getMessages() {
        return messages;
    }
    
    /*
     * NOTA: Para boolean, el getter puede ser isPremium() o getPremium()
     * Ambos funcionan con Gson.
     */
    public boolean isPremium() {
        return premium;
    }
}
