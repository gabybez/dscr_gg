package es.unavarra.tlm.dscr_25_06;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    private ProgressBar pb;
    private TextView tvUsername, tvCreatedAt;
    private TextView tvGames, tvWins, tvLosses, tvWinRate;

    private final Gson gson = new Gson();
    private long userId = -1;
    private String username = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userId = getIntent().getLongExtra("user_id", -1);
        username = getIntent().getStringExtra("username");

        if (userId <= 0) {
            userId = SessionManager.getUserId(this);
            username = SessionManager.getUsername(this);
        }

        if (username != null) {
            setTitle(getString(R.string.title_profile_user, username));
        } else {
            setTitle(getString(R.string.title_profile));
        }

        pb = findViewById(R.id.pb);
        tvUsername = findViewById(R.id.tvUsername);
        tvCreatedAt = findViewById(R.id.tvCreatedAt);
        tvGames = findViewById(R.id.tvGames);
        tvWins = findViewById(R.id.tvWins);
        tvLosses = findViewById(R.id.tvLosses);
        tvWinRate = findViewById(R.id.tvWinRate);

        cargarPerfil();
    }

    private void cargarPerfil() {
        String token = SessionManager.getToken(this);
        if (token == null) {
            Toast.makeText(this, getString(R.string.err_sesion_caducada), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (userId <= 0) {
            Toast.makeText(this, getString(R.string.err_profile_no_user), Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        pb.setVisibility(View.VISIBLE);

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Authentication", token);

        String url = getString(R.string.api_user_base) + userId;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pb.setVisibility(View.GONE);
                try {
                    String raw = new String(responseBody, "UTF-8");
                    UserProfile profile = gson.fromJson(raw, UserProfile.class);
                    mostrarPerfil(profile);
                } catch (Exception e) {
                    Toast.makeText(ProfileActivity.this, getString(R.string.err_profile_parse), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pb.setVisibility(View.GONE);
                Toast.makeText(ProfileActivity.this, getString(R.string.err_profile_load, statusCode), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarPerfil(UserProfile profile) {
        if (profile.user != null) {
            tvUsername.setText(profile.user.getUsername());
        }

        if (profile.account != null && profile.account.created_at != null) {
            String fecha = profile.account.created_at;
            if (fecha.contains("T")) {
                fecha = fecha.substring(0, 10);
            }
            tvCreatedAt.setText(getString(R.string.profile_created_at, fecha));
        }

        if (profile.stats != null) {
            UserStats s = profile.stats;

            tvGames.setText(getString(R.string.profile_games, s.games));
            tvWins.setText(getString(R.string.profile_wins, s.wins));
            tvLosses.setText(getString(R.string.profile_losses, s.losses));

            // Calcular win rate
            if (s.games > 0) {
                int winRate = (s.wins * 100) / s.games;
                tvWinRate.setText(getString(R.string.profile_win_rate, winRate));
            } else {
                tvWinRate.setText(getString(R.string.profile_win_rate, 0));
            }
        }
    }
}