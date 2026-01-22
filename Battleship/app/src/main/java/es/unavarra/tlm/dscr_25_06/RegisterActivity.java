package es.unavarra.tlm.dscr_25_06;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.entity.StringEntity;
import java.io.UnsupportedEncodingException;

/**
 *  - Valida campos, envía PUT /v2/user con JSON.
 *  - En éxito, te deja logueado y entra a Main.
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText etUsuario, etContrasena;
    private final Gson gson = new Gson();
    private String URL; // strings.xml -> api_register_user

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_register);

        URL = getString(R.string.api_register_user);

        etUsuario = findViewById(R.id.Hint_usuario);
        etContrasena = findViewById(R.id.Hint_contraseña);
        Button btnCrear = findViewById(R.id.Btn_crearCuenta);

        btnCrear.setOnClickListener(v -> doRegister());
    }

    /** Construye JSON y hace PUT /v2/user. */
    private void doRegister() {
        String u = etUsuario.getText().toString().trim();
        String p = etContrasena.getText().toString();

        if (u.isEmpty() || p.isEmpty()) {
            Toast.makeText(this, getString(R.string.err_rellena_ambos), Toast.LENGTH_SHORT).show();
            return;
        }
        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity body = new StringEntity(gson.toJson(new RegisterRequest(u, p)), "UTF-8");
        client.put(this, URL, body, "application/json", new RegisterResponseHandler(this));
    }
}
