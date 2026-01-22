package es.unavarra.tlm.examenmaster;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// IMPORTS CLAVE
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class APIActivity extends AppCompatActivity {

    private TextView tvResultado;
    private final String URL_API = "http://api.battleship.tatai.es/v2/welcome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        tvResultado = findViewById(R.id.tv_ApiResult);

        findViewById(R.id.btn_PostTest).setOnClickListener(v -> ejecutarPostServidor());
    }

    /**
     * ANEXO 1: Ejemplo de petición POST con JSON
     */
    private void ejecutarPostServidor() {
        // 1. Instanciar herramientas
        AsyncHttpClient client = new AsyncHttpClient();
        Gson gson = new Gson();

        // 2. Preparar el objeto de envío (Request)
        WelcomeRequest requestObj = new WelcomeRequest("AlumnoExamen");

        try {
            // 3. Convertir objeto a JSON String y meterlo en una "Entidad"
            String jsonBody = gson.toJson(requestObj);
            StringEntity entity = new StringEntity(jsonBody);

            tvResultado.setText(getString(R.string.msg_conectando));

            // 4. Realizar la petición POST
            // Contexto, URL, Entidad, Tipo de contenido, Handler
            client.post(this, URL_API, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // Convertimos los bytes recibidos a String
                    String jsonResponse = new String(responseBody);

                    // Deserializamos el JSON a nuestro objeto Java
                    WelcomeResponse resp = gson.fromJson(jsonResponse, WelcomeResponse.class);

                    tvResultado.setText("Servidor dice: " + resp.getMessage());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(APIActivity.this, getString(R.string.err_servidor), Toast.LENGTH_SHORT).show();
                    tvResultado.setText("Código error: " + statusCode);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}