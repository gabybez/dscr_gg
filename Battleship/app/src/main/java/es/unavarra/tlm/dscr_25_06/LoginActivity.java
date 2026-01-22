package es.unavarra.tlm.dscr_25_06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 *  - Si ya hay token guardado -> entrar a Main y terminar.
 *  - Si no, mostrar formulario, validar y llamar PUT /v2/session (JSON con Gson).
 *  - Handler guarda sesión y navega a Main limpiando el back stack.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText etUsuario, etContrasena;
    private View vistaNoId, vistaId;
    private final Gson gson = new Gson();
    private String URL; // strings.xml -> api_login_session

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        // URL del endpoint desde strings.xml (fácil de cambiar por entorno)
        URL = getString(R.string.api_login_session);

        // Vistas (usa tus IDs)
        vistaNoId = findViewById(R.id.usuarioNoIdentificado);
        vistaId   = findViewById(R.id.usuarioIdentificado);
        etUsuario = findViewById(R.id.Hint_usuarioLogin);
        etContrasena = findViewById(R.id.Hint_contraseñaLogin);
        Button btnLogin = findViewById(R.id.Btn_login);

        // Si ya hay sesión, entra directo a Main
        if (SessionManager.getToken(this) != null) { goMain(); return; }
        // Si no hay sesión, muestra el formulario
        toggle(false);

        // Click en "Iniciar sesión"
        btnLogin.setOnClickListener(v -> doLogin());
    }

    /** Construye el body JSON y hace la petición PUT /v2/session usando AsyncHttpClient. */
    private void doLogin() {
        String u = etUsuario.getText().toString().trim();
        String p = etContrasena.getText().toString();

        // Validación mínima en cliente
        if (u.isEmpty() || p.isEmpty()) {
            Toast.makeText(this, getString(R.string.err_rellena_ambos), Toast.LENGTH_SHORT).show();
            return;
        }
        // Cliente HTTP
        AsyncHttpClient client = new AsyncHttpClient();
        // Cuerpo JSON de la petición: Gson -> StringEntity UTF-8
        StringEntity body = new StringEntity(gson.toJson(new LoginRequest(u, p)), "UTF-8");
        // PUT con Content-Type application/json y handler dedicado
        client.put(this, URL, body, "application/json", new LoginResponseHandler(this));
    }

    /** Alterna las vistas de "no identificado" / "identificado" */
    private void toggle(boolean identificado) {
        vistaNoId.setVisibility(identificado ? View.GONE : View.VISIBLE);
        vistaId.setVisibility(identificado ? View.VISIBLE : View.GONE);
    }

    /** Entra en Main y limpia la pila para que atrás no vuelva a Login. */
    private void goMain() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
