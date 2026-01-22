package es.unavarra.tlm.examen_22_claude;

/*
 * ============================================================================
 * MODELO: RoomResponse.java
 * ============================================================================
 * Esta clase representa el JSON que recibimos del servidor cuando la
 * petición de sala es exitosa.
 *
 * JSON que recibimos:
 * {
 *   "room": 123,
 *   "buttons": {
 *     "one": 4,
 *     "two": 7,
 *     "three": 9,
 *     "four": 12,
 *     "five": 16
 *   }
 * }
 *
 * CÓMO FUNCIONA CON GSON (deserialización - JSON a objeto):
 * 1. Gson lee el JSON y busca las claves
 * 2. Por cada clave, busca un atributo con el mismo nombre
 * 3. Asigna el valor del JSON al atributo Java
 *
 * OBJETOS ANIDADOS:
 * El JSON tiene un objeto anidado "buttons".
 * Para representarlo, creamos una clase interna "Buttons".
 *
 * Ejemplo de uso:
 * String json = "{\"room\":123,\"buttons\":{\"one\":4...}}";
 * RoomResponse response = gson.fromJson(json, RoomResponse.class);
 * int roomNum = response.getRoom();           // 123
 * int btnOne = response.getButtons().getOne(); // 4
 * ============================================================================
 */
public class RoomResponse {

    /*
     * Número de sala (el mismo que enviamos)
     */
    private int room;

    /*
     * Objeto anidado con los valores de los botones.
     * En el JSON es: "buttons": { ... }
     * En Java es un objeto de tipo Buttons.
     */
    private Buttons buttons;

    // ========================================================================
    // GETTERS
    // ========================================================================

    public int getRoom() {
        return room;
    }

    public Buttons getButtons() {
        return buttons;
    }

    // ========================================================================
    // SETTERS
    // ========================================================================

    public void setRoom(int room) {
        this.room = room;
    }

    public void setButtons(Buttons buttons) {
        this.buttons = buttons;
    }

    /*
     * ========================================================================
     * CLASE INTERNA: Buttons
     * ========================================================================
     * Representa el objeto JSON anidado "buttons".
     *
     * JSON:
     * "buttons": {
     *   "one": 4,
     *   "two": 7,
     *   "three": 9,
     *   "four": 12,
     *   "five": 16
     * }
     *
     * Los nombres de los atributos (one, two, three, four, five) deben
     * coincidir EXACTAMENTE con las claves del JSON.
     */
    public static class Buttons {
        private int one;
        private int two;
        private int three;
        private int four;
        private int five;

        // Getters
        public int getOne() {
            return one;
        }

        public int getTwo() {
            return two;
        }

        public int getThree() {
            return three;
        }

        public int getFour() {
            return four;
        }

        public int getFive() {
            return five;
        }

        // Setters
        public void setOne(int one) {
            this.one = one;
        }

        public void setTwo(int two) {
            this.two = two;
        }

        public void setThree(int three) {
            this.three = three;
        }

        public void setFour(int four) {
            this.four = four;
        }

        public void setFive(int five) {
            this.five = five;
        }
    }
}