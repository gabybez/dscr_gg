package es.unavarra.tlm.dscr_25_06;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Button btnNewGame, btnPartidasOn, btnPartidasOff, btnSalir;
    private Button btnInvitaciones, btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SessionManager.getToken(this) == null) {
            goToWelcome();
            return;
        }

        btnNewGame      = findViewById(R.id.btn_New_game);
        btnPartidasOn   = findViewById(R.id.btn_Partidas_on);
        btnPartidasOff  = findViewById(R.id.btn_Partidas_off);
        btnSalir        = findViewById(R.id.Btn_salir);
        btnInvitaciones = findViewById(R.id.btn_Invitaciones);
        btnPerfil       = findViewById(R.id.btn_Perfil);

        btnNewGame.setOnClickListener(v ->
                startActivity(new Intent(this, CrearPartidaActivity.class)));

        btnPartidasOn.setOnClickListener(v ->
                startActivity(new Intent(this, ActiveGamesActivity.class)));

        if (btnInvitaciones != null) {
            btnInvitaciones.setOnClickListener(v ->
                    startActivity(new Intent(this, InvitacionesActivity.class)));
        }

        btnPartidasOff.setOnClickListener(v ->
                startActivity(new Intent(this, InactiveGamesActivity.class)));

        btnPerfil.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));

        btnSalir.setOnClickListener(v -> doLogout());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SessionManager.getToken(this) == null) {
            goToWelcome();
        }
    }

    private void doLogout() {
        String token = SessionManager.getToken(this);
        if (token == null) {
            goToWelcome();
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Authentication", token);

        client.delete(this, getString(R.string.api_login_session), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                SessionManager.clear(MainActivity.this);
                goToWelcome();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                SessionManager.clear(MainActivity.this);
                Toast.makeText(MainActivity.this, getString(R.string.err_servidor), Toast.LENGTH_SHORT).show();
                goToWelcome();
            }
        });
    }

    private void goToWelcome() {
        Intent i = new Intent(this, WelcomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}