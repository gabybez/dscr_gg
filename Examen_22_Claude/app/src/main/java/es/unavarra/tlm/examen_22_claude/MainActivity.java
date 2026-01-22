package es.unavarra.tlm.examen_22_claude;

/*
 * ============================================================================
 * ARCHIVO: MainActivity.java
 * ============================================================================
 * Esta es la Activity principal de la aplicación de votaciones.
 *
 * ESTRUCTURA DE UNA ACTIVITY:
 * 1. Hereda de AppCompatActivity (o Activity)
 * 2. Implementa onCreate() donde se inicializa todo
 * 3. Puede implementar otros métodos del ciclo de vida (onStart, onResume, etc.)
 *
 * CICLO DE VIDA:
 * onCreate() -> onStart() -> onResume() -> [RUNNING]
 *                                              |
 *                                        onPause() -> onStop() -> onDestroy()
 * ============================================================================
 */

// IMPORTS NECESARIOS
// Android básico
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Para peticiones HTTP asíncronas
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

// Para trabajar con JSON
import com.google.gson.Gson;

// Para enviar datos en el cuerpo de la petición
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

// Java estándar
import java.io.UnsupportedEncodingException;

/**
 * MainActivity - Pantalla principal de la aplicación
 *
 * FLUJO DE LA APLICACIÓN:
 * 1. Usuario introduce número de sala
 * 2. Pulsa CONTINUE
 * 3. Se valida que no esté vacío (y que sea número - EXTRA)
 * 4. Se envía POST al servidor con el número de sala
 * 5. Si OK (200): Se muestran 5 botones con los valores recibidos
 * 6. Si ERROR (400): Se muestra mensaje de error
 * 7. Usuario pulsa un botón de votación
 * 8. Se envía PUT al servidor con el voto
 * 9. Si OK: Se muestra "You have chosen X"
 * 10. Si ERROR: Se muestra mensaje de error
 */
public class MainActivity extends AppCompatActivity {

    /*
     * ========================================================================
     * CONSTANTES
     * ========================================================================
     * Buena práctica: URLs y valores fijos como constantes
     * Así si cambia la URL solo hay que modificar un lugar
     */
    private static final String BASE_URL = "https://api.battleship.tatai.es/v1/exam-2022-1";

    /*
     * ========================================================================
     * DECLARACIÓN DE WIDGETS
     * ========================================================================
     * Declaramos las referencias a los elementos del layout como variables
     * de instancia para poder acceder a ellos desde cualquier método.
     *
     * TIPOS COMUNES:
     * - TextView: Texto de solo lectura
     * - EditText: Campo de entrada de texto
     * - Button: Botón clickeable
     * - ImageView: Imagen
     * - LinearLayout: Contenedor de otros elementos
     */
    private EditText editRoomNumber;    // Campo para introducir número de sala
    private Button btnContinue;          // Botón continuar
    private TextView txtError;           // Texto para mostrar errores
    private TextView txtChoose;          // Texto "Choose one"
    private LinearLayout layoutButtons;  // Contenedor de botones de votación
    private Button btnOne, btnTwo, btnThree, btnFour, btnFive; // Botones de votación
    private TextView txtResult;          // Texto "You have chosen X"

    /*
     * ========================================================================
     * OBJETOS PARA HTTP Y JSON
     * ========================================================================
     */
    private AsyncHttpClient httpClient;  // Cliente para peticiones HTTP
    private Gson gson;                    // Para convertir objetos Java <-> JSON

    /*
     * ========================================================================
     * onCreate - MÉTODO PRINCIPAL DE INICIALIZACIÓN
     * ========================================================================
     * Se llama cuando la Activity se crea por primera vez.
     * Aquí se hace:
     * 1. Llamar a super.onCreate() (OBLIGATORIO)
     * 2. setContentView() para inflar el layout XML
     * 3. Obtener referencias a los widgets con findViewById()
     * 4. Configurar listeners para eventos
     * 5. Inicializar objetos necesarios
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // SIEMPRE llamar primero al método padre
        super.onCreate(savedInstanceState);

        // Inflar el layout XML y establecerlo como vista de esta Activity
        // R.layout.activity_main hace referencia a res/layout/activity_main.xml
        setContentView(R.layout.activity_main);

        // Inicializar cliente HTTP y Gson
        httpClient = new AsyncHttpClient();
        gson = new Gson();

        // ====================================================================
        // OBTENER REFERENCIAS A WIDGETS
        // ====================================================================
        // findViewById() busca un elemento por su ID en el layout inflado
        // El ID viene de android:id="@+id/nombre" en el XML
        // R.id.nombre es la referencia generada automáticamente

        editRoomNumber = (EditText) findViewById(R.id.edit_room_number);
        btnContinue = (Button) findViewById(R.id.btn_continue);
        txtError = (TextView) findViewById(R.id.txt_error);
        txtChoose = (TextView) findViewById(R.id.txt_choose);
        layoutButtons = (LinearLayout) findViewById(R.id.layout_buttons);
        txtResult = (TextView) findViewById(R.id.txt_result);

        // Botones de votación
        btnOne = (Button) findViewById(R.id.btn_one);
        btnTwo = (Button) findViewById(R.id.btn_two);
        btnThree = (Button) findViewById(R.id.btn_three);
        btnFour = (Button) findViewById(R.id.btn_four);
        btnFive = (Button) findViewById(R.id.btn_five);

        // ====================================================================
        // CONFIGURAR EVENTOS (LISTENERS)
        // ====================================================================
        // setOnClickListener() asigna una acción cuando se pulsa el botón
        // Usamos clase anónima que implementa View.OnClickListener

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamamos al método que procesa la selección de sala
                onContinueClicked();
            }
        });

        // Configurar listeners para cada botón de votación
        // Todos llaman al mismo método pero con diferente valor
        btnOne.setOnClickListener(new VoteClickListener());
        btnTwo.setOnClickListener(new VoteClickListener());
        btnThree.setOnClickListener(new VoteClickListener());
        btnFour.setOnClickListener(new VoteClickListener());
        btnFive.setOnClickListener(new VoteClickListener());
    }

    /*
     * ========================================================================
     * onContinueClicked - Procesar clic en botón CONTINUE
     * ========================================================================
     * Este método:
     * 1. Obtiene el texto del EditText
     * 2. Valida que no esté vacío
     * 3. Valida que sea un número (EXTRA 2)
     * 4. Envía la petición POST al servidor
     */
    private void onContinueClicked() {
        // Ocultar mensaje de error previo
        txtError.setVisibility(View.GONE);

        // ====================================================================
        // OBTENER VALOR DEL EDITTEXT
        // ====================================================================
        // getText() devuelve un Editable, necesitamos convertir a String
        String roomText = editRoomNumber.getText().toString();

        // ====================================================================
        // VALIDACIÓN 1: Campo vacío
        // ====================================================================
        // trim() elimina espacios al inicio y final
        // isEmpty() comprueba si la cadena está vacía
        if (roomText.trim().isEmpty()) {
            showError(getString(R.string.error_empty_room));
            return; // Salir del método, no continuar
        }

        // ====================================================================
        // VALIDACIÓN 2: Es un número válido (EXTRA 2 del examen)
        // ====================================================================
        // Intentamos parsear a int. Si falla, no es un número válido.
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomText.trim());
        } catch (NumberFormatException e) {
            showError(getString(R.string.error_not_number));
            return;
        }

        // ====================================================================
        // ENVIAR PETICIÓN POST AL SERVIDOR
        // ====================================================================
        sendRoomRequest(roomNumber);
    }

    /*
     * ========================================================================
     * sendRoomRequest - Enviar petición POST con número de sala
     * ========================================================================
     *
     * ANATOMÍA DE UNA PETICIÓN HTTP:
     * - URL: A dónde enviamos la petición
     * - Método: GET, POST, PUT, DELETE, etc.
     * - Headers: Metadatos (Content-Type, Authorization, etc.)
     * - Body: Datos que enviamos (en POST/PUT)
     *
     * En este caso:
     * - URL: https://api.battleship.tatai.es/v1/exam-2022-1
     * - Método: POST
     * - Content-Type: application/json
     * - Body: {"room": 123}
     */
    private void sendRoomRequest(int roomNumber) {
        // ====================================================================
        // PASO 1: Crear objeto Java con los datos a enviar
        // ====================================================================
        // Creamos un objeto que representa el JSON que queremos enviar
        RoomRequest request = new RoomRequest(roomNumber);

        // ====================================================================
        // PASO 2: Convertir objeto Java a JSON usando Gson
        // ====================================================================
        // gson.toJson() convierte el objeto a String JSON
        // RoomRequest{room: 123} -> {"room": 123}
        String jsonBody = gson.toJson(request);

        // ====================================================================
        // PASO 3: Crear StringEntity para el cuerpo de la petición
        // ====================================================================
        StringEntity entity;
        try {
            entity = new StringEntity(jsonBody);
        } catch (UnsupportedEncodingException e) {
            showError("Error encoding request");
            return;
        }

        // ====================================================================
        // PASO 4: Hacer la petición POST asíncrona
        // ====================================================================
        // Parámetros de post():
        // - this: Contexto (la Activity)
        // - URL: Dirección del servidor
        // - entity: Cuerpo de la petición
        // - "application/json": Content-Type header
        // - ResponseHandler: Clase que procesa la respuesta

        httpClient.post(
                this,
                BASE_URL,
                entity,
                "application/json",
                new RoomResponseHandler()  // Handler definido más abajo
        );
    }

    /*
     * ========================================================================
     * sendVoteRequest - Enviar petición PUT con el voto
     * ========================================================================
     * Similar a sendRoomRequest pero:
     * - Método PUT en lugar de POST
     * - Enviamos {"vote": X} en lugar de {"room": X}
     */
    private void sendVoteRequest(int voteValue) {
        // Crear objeto de petición
        VoteRequest request = new VoteRequest(voteValue);

        // Convertir a JSON
        String jsonBody = gson.toJson(request);

        // Crear entity
        StringEntity entity;
        try {
            entity = new StringEntity(jsonBody);
        } catch (UnsupportedEncodingException e) {
            showError("Error encoding request");
            return;
        }

        // ====================================================================
        // PETICIÓN PUT
        // ====================================================================
        // Usamos put() en lugar de post()
        // La única diferencia con POST es el método HTTP
        httpClient.put(
                this,
                BASE_URL,
                entity,
                "application/json",
                new VoteResponseHandler()
        );
    }

    /*
     * ========================================================================
     * showError - Mostrar mensaje de error
     * ========================================================================
     * Método auxiliar para mostrar errores de forma consistente.
     * Hace visible el TextView de error y establece el texto.
     */
    private void showError(String message) {
        txtError.setText(message);
        txtError.setVisibility(View.VISIBLE);
    }

    /*
     * ========================================================================
     * showVotingButtons - Mostrar botones de votación
     * ========================================================================
     * Se llama cuando recibimos respuesta exitosa del servidor.
     * Configura el texto de cada botón con los valores recibidos.
     */
    private void showVotingButtons(RoomResponse response) {
        // Obtener los valores de los botones desde la respuesta
        RoomResponse.Buttons buttons = response.getButtons();

        // Establecer el texto de cada botón
        // Convertimos int a String porque setText espera String
        btnOne.setText(String.valueOf(buttons.getOne()));
        btnTwo.setText(String.valueOf(buttons.getTwo()));
        btnThree.setText(String.valueOf(buttons.getThree()));
        btnFour.setText(String.valueOf(buttons.getFour()));
        btnFive.setText(String.valueOf(buttons.getFive()));

        // Hacer visibles los elementos de votación
        txtChoose.setVisibility(View.VISIBLE);
        layoutButtons.setVisibility(View.VISIBLE);
    }

    /*
     * ========================================================================
     * CLASE INTERNA: VoteClickListener
     * ========================================================================
     * Listener compartido por todos los botones de votación.
     * Obtiene el valor del botón pulsado y envía la petición.
     */
    private class VoteClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // Ocultar error previo
            txtError.setVisibility(View.GONE);

            // Obtener el botón pulsado
            Button clickedButton = (Button) v;

            // Obtener el texto del botón (que es el valor a enviar)
            String valueText = clickedButton.getText().toString();

            // Convertir a entero y enviar
            int voteValue = Integer.parseInt(valueText);
            sendVoteRequest(voteValue);
        }
    }

    /*
     * ========================================================================
     * CLASE INTERNA: RoomResponseHandler
     * ========================================================================
     * Procesa la respuesta de la petición POST de sala.
     *
     * AsyncHttpResponseHandler tiene dos métodos principales:
     * - onSuccess(): Se llama cuando el servidor responde con código 2XX o 3XX
     * - onFailure(): Se llama cuando el servidor responde con código 4XX o 5XX
     *                o hay error de red
     */
    private class RoomResponseHandler extends AsyncHttpResponseHandler {

        /*
         * onSuccess - Respuesta exitosa (HTTP 200-299)
         *
         * Parámetros:
         * - statusCode: Código HTTP (200, 201, etc.)
         * - headers: Headers de la respuesta
         * - responseBody: Cuerpo de la respuesta como bytes
         */
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            // ================================================================
            // PASO 1: Convertir bytes a String
            // ================================================================
            String responseJson = new String(responseBody);

            // ================================================================
            // PASO 2: Parsear JSON a objeto Java usando Gson
            // ================================================================
            // fromJson() convierte JSON String a objeto Java
            // Necesitamos especificar la clase destino (RoomResponse.class)
            RoomResponse response = gson.fromJson(responseJson, RoomResponse.class);

            // ================================================================
            // PASO 3: Mostrar los botones de votación
            // ================================================================
            showVotingButtons(response);
        }

        /*
         * onFailure - Respuesta de error (HTTP 400-599 o error de red)
         *
         * Parámetros adicionales:
         * - throwable: Excepción si hubo error de red
         */
        @Override
        public void onFailure(int statusCode, Header[] headers,
                              byte[] responseBody, Throwable throwable) {
            // Verificar si hay cuerpo de respuesta
            if (responseBody == null) {
                showError(getString(R.string.error_network));
                return;
            }

            // Parsear respuesta de error
            String responseJson = new String(responseBody);
            ErrorResponse error = gson.fromJson(responseJson, ErrorResponse.class);

            // ================================================================
            // MOSTRAR MENSAJE SEGÚN CÓDIGO DE ERROR
            // ================================================================
            // El servidor puede devolver:
            // error: 1 -> El número de sala es obligatorio
            // error: 2 -> Valor de sala no válida
            switch (error.getError()) {
                case 1:
                    showError(getString(R.string.error_room_required));
                    break;
                case 2:
                    showError(getString(R.string.error_invalid_room));
                    break;
                default:
                    showError("Unknown error: " + error.getError());
            }
        }
    }

    /*
     * ========================================================================
     * CLASE INTERNA: VoteResponseHandler
     * ========================================================================
     * Procesa la respuesta de la petición PUT de voto.
     */
    private class VoteResponseHandler extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            // Parsear respuesta
            String responseJson = new String(responseBody);
            VoteResponse response = gson.fromJson(responseJson, VoteResponse.class);

            // ================================================================
            // MOSTRAR MENSAJE DE RESULTADO
            // ================================================================
            // String.format() reemplaza %d por el número
            // "You have chosen %d" -> "You have chosen 9"
            String message = String.format(
                    getString(R.string.result_message),
                    response.getSelection()
            );

            txtResult.setText(message);
            txtResult.setVisibility(View.VISIBLE);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              byte[] responseBody, Throwable throwable) {
            if (responseBody == null) {
                showError(getString(R.string.error_network));
                return;
            }

            String responseJson = new String(responseBody);
            ErrorResponse error = gson.fromJson(responseJson, ErrorResponse.class);

            // Mensajes de error para votación:
            // error: 1 -> El voto es obligatorio
            // error: 2 -> Valor de voto no válido
            switch (error.getError()) {
                case 1:
                    showError(getString(R.string.error_vote_required));
                    break;
                case 2:
                    showError(getString(R.string.error_invalid_vote));
                    break;
                default:
                    showError("Unknown error: " + error.getError());
            }
        }
    }
}