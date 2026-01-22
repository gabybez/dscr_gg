package es.unavarra.tlm.examenclaude1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

//Detalle y actualización de las mesas

public class ReserveActivity extends AppCompatActivity {

    //URL base de la API
    private static final String BASE_URL = "https://api.restaurant.example.com/v1/tables";

    //Widgets de la pantalla
    private TextView txtTableNumber;
    private TextView txtCapacity;
    private EditText editName;
    private EditText editGuests;
    private Button btnReserve;

    //HTTP y JSON
    private AsyncHttpClient httpClient;
    private Gson gson;

    //Datos de la mesa (recibidos del intent)
    private int tableId;
    private int capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        //Paso 1: Inicializar HTTP y GSON

        httpClient = new AsyncHttpClient();
        gson = new Gson();

        //Paso 2: Recibir los datos del intent

        Bundle extras = getIntent().getExtras();
        tableId = extras.getInt("tableId");
        capacity = extras.getInt("capacity");

        //Paso 3: Obtener referencia de los widgets
        txtTableNumber = findViewById(R.id.txt_table_number);
        txtCapacity = findViewById(R.id.txt_capacity);
        editName = findViewById(R.id.edit_name);
        editGuests = findViewById(R.id.edit_guests);
        btnReserve = findViewById(R.id.btn_reserve);

        //Paso 4: Mostrar los datos iniciales
        txtTableNumber.setText(String.valueOf(tableId));
        txtCapacity.setText(String.valueOf(capacity));

        //Paso 5 configurar los listeners del botón
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReserveClicked();
            }
        });
    }
    //onReserveClicked() - Para validar y enviar la reserva
    private void onReserveClicked(){
        //primera validacion(campo nombre no vacio)
        String name = editName.getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(this, R.string.error_guests_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        // ====================================================================
        // VALIDACIÓN 2: Comensales no vacío
        // ====================================================================
        String guestsStr = editGuests.getText().toString().trim();

        if (guestsStr.isEmpty()) {
            Toast.makeText(this, R.string.error_guests_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        // ====================================================================
        // VALIDACIÓN 3: Comensales es un número válido
        // ====================================================================
        int guests;
        try {
            guests = Integer.parseInt(guestsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.error_guests_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        // ====================================================================
        // VALIDACIÓN 4: Comensales no supera la capacidad
        // ====================================================================
        if (guests > capacity) {
            Toast.makeText(this, R.string.error_guests_exceeded, Toast.LENGTH_SHORT).show();
            return;
        }

        // ====================================================================
        // VALIDACIÓN 5: Al menos 1 comensal
        // ====================================================================
        if (guests < 1) {
            Toast.makeText(this, R.string.error_guests_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        // ====================================================================
        // Todo OK - Enviar petición POST
        // ====================================================================
        sendReservation(name, guests);
    }
    /*
     * ========================================================================
     * sendReservation() - Petición POST para crear la reserva
     * ========================================================================
     *
     * URL: POST /tables/{tableId}/reserve
     * Body: { "name": "...", "guests": N }
     */
    private void sendReservation(String name, int guests){
        //Primero construimos la URL
        String url = BASE_URL + "/" + tableId + "/reserve";

        //Segundo creamos el objeto de la peticion y lo convertimos a json
        ReserveRequest request = new ReserveRequest(name,guests);
        String jsonBody = gson.toJson(request);
        //crear StringEntity
        StringEntity entity = new StringEntity(jsonBody, "UTF-8");
        //Enviar la peticion POST
        httpClient.post(
                this,
                url,
                entity,
                "application/json",
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        //Parsear la respuesta
                        String json = new String(responseBody);
                        ReserveResponse response = gson.fromJson(json, ReserveResponse.class);
                        //Mostrar el toast de exito
                        String message = getString(R.string.reservation_success,
                                response.getReservation().getName());
                        Toast.makeText(ReserveActivity.this, message, Toast.LENGTH_SHORT).show();

                        //Volver al listado de mesas
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        // Manejar error HTTP 400
                        if (statusCode == 400 && responseBody != null) {
                            String json = new String(responseBody);
                            ErrorResponse errorResponse = gson.fromJson(json, ErrorResponse.class);
                            String errorMessage = getErrorMessage(errorResponse.getCode());
                            Toast.makeText(ReserveActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Otros errores
                        Toast.makeText(ReserveActivity.this,
                                R.string.error_network, Toast.LENGTH_SHORT).show();

                    }
                }
        );

    }

    /*
     * ========================================================================
     * getErrorMessage() - Convertir código de error a mensaje legible
     * ========================================================================
     */
    private String getErrorMessage(String code) {
        switch (code) {
            case "NAME_REQUIRED":
                return getString(R.string.error_name_required);

            case "GUESTS_REQUIRED":
                return getString(R.string.error_guests_required);

            case "GUESTS_EXCEEDED":
                return getString(R.string.error_guests_exceeded_server);

            case "TABLE_NOT_AVAILABLE":
                return getString(R.string.error_table_not_available_server);

            default:
                return getString(R.string.error_network);
        }
    }
}