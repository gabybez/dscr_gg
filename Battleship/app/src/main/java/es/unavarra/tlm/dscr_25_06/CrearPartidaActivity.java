package es.unavarra.tlm.dscr_25_06;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import cz.msebera.android.httpclient.entity.StringEntity;

public class CrearPartidaActivity extends AppCompatActivity {

    private EditText etOpponent;
    private Button btnInvitar;
    private final Gson gson = new Gson();
    private String URL_CHALLENGE; // strings.xml -> api_challenge

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_crear_partida);

        URL_CHALLENGE = getString(R.string.api_challenge);

        etOpponent = findViewById(R.id.et_buscar_usuario);
        btnInvitar = findViewById(R.id.btn_invitar);

        btnInvitar.setOnClickListener(v -> invitar());
    }

    private void invitar() {
        String opponent = etOpponent.getText().toString().trim();
        if (opponent.isEmpty()) {
            Toast.makeText(
                    this,
                    getString(R.string.invite_user_prompt),
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String token = SessionManager.getToken(this);
        if (token == null) {
            Toast.makeText(this, getString(R.string.session_expired), Toast.LENGTH_LONG).show();
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Authentication", token);

        StringEntity body = new StringEntity(
                gson.toJson(new InvitarPartidaRequest(opponent)),
                "UTF-8"
        );

        // PUT /v2/challenge
        client.put(
                this,
                URL_CHALLENGE,
                body,
                "application/json",
                new InvitarPartidaResponseHandler(this, () -> {
                    Toast.makeText(
                            this,
                            getString(R.string.invite_sent, opponent),
                            Toast.LENGTH_SHORT
                    ).show();
                    finish();
                })
        );
    }
}
