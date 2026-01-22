package es.unavarra.tlm.examen_22_claude;

/*
 * ============================================================================
 * MODELO: RoomRequest.java
 * ============================================================================
 * Esta clase representa el JSON que enviamos al servidor para seleccionar sala.
 *
 * JSON que queremos generar:
 * {"room": 123}
 *
 * CÓMO FUNCIONA CON GSON:
 * 1. Gson mira los atributos de la clase
 * 2. El nombre del atributo se convierte en la clave JSON
 * 3. El valor del atributo se convierte en el valor JSON
 *
 * Ejemplo:
 * RoomRequest request = new RoomRequest(123);
 * String json = gson.toJson(request);
 * // json = {"room": 123}
 *
 * IMPORTANTE:
 * - El nombre del atributo DEBE coincidir con la clave JSON esperada por el servidor
 * - Si el servidor espera "room", el atributo debe llamarse "room"
 * - Para nombres diferentes, se puede usar @SerializedName("nombre_en_json")
 * ============================================================================
 */
public class RoomRequest {

    /*
     * Atributo que representa el número de sala.
     * Al ser "room", en el JSON aparecerá como "room": valor
     */
    private int room;

    /*
     * Constructor que recibe el número de sala.
     * Se usa antes de convertir a JSON.
     */
    public RoomRequest(int room) {
        this.room = room;
    }

    /*
     * Getter - Obtener el valor de room.
     * Los getters son buena práctica aunque Gson no los necesite.
     */
    public int getRoom() {
        return room;
    }

    /*
     * Setter - Establecer el valor de room.
     * Los setters son buena práctica para encapsulamiento.
     */
    public void setRoom(int room) {
        this.room = room;
    }
}