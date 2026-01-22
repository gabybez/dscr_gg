package es.unavarra.tlm.examenmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Importante: Si el examen no pide login, estas dos se pueden quitar
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // 1. Declaración de botones (siguiendo tu estructura)
    private Button btnLayouts, btnPrefs, btnDB, btnListas, btnAPI, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Control de sesión (si te piden que haya un login previo)
        // SessionManager es una clase que crearemos luego para SharedPreferences
        if (SessionManager.getToken(this) == null) {
            goToLogin();
            return;
        }

        // 3. Inicialización
        initViews();

        // 4. Listeners (Navegación a temas)
        btnLayouts.setOnClickListener(v ->
                startActivity(new Intent(this, LayoutsActivity.class)));

        btnPrefs.setOnClickListener(v ->
                startActivity(new Intent(this, PrefsActivity.class)));

        btnDB.setOnClickListener(v ->
                startActivity(new Intent(this, DBActivity.class)));

        btnListas.setOnClickListener(v ->
                startActivity(new Intent(this, ListasActivity.class)));

        btnAPI.setOnClickListener(v ->
                startActivity(new Intent(this, APIActivity.class)));

        btnSalir.setOnClickListener(v -> doLogout());
    }

    private void initViews() {
        btnLayouts = findViewById(R.id.btn_Layouts);
        btnPrefs   = findViewById(R.id.btn_Prefs);
        btnDB      = findViewById(R.id.btn_DB);
        btnListas  = findViewById(R.id.btn_Listas);
        btnAPI     = findViewById(R.id.btn_API);
        btnSalir   = findViewById(R.id.btn_Salir);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Verificamos sesión al volver a la pantalla principal
        if (SessionManager.getToken(this) == null) {
            goToLogin();
        }
    }

    private void doLogout() {
        // Borramos los datos locales y vamos al inicio
        SessionManager.clear(this);
        goToLogin();

        // OPCIONAL: Si te piden borrar la sesión también en el servidor (REST)
        /*
        AsyncHttpClient client = new AsyncHttpClient();
        client.delete(this, "URL_LOGOUT", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) { goToLogin(); }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable t) { goToLogin(); }
        });
        */
    }

    private void goToLogin() {
        // Estructura clave: Limpia la pila para que no se pueda volver atrás [Teoría 3.2]
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}