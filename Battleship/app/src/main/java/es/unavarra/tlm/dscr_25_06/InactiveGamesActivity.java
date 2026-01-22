package es.unavarra.tlm.dscr_25_06;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class InactiveGamesActivity extends AppCompatActivity {

    private static final int POLL_INTERVAL = 5000; // 5 segundos

    private ProgressBar pb;
    private TextView tvEmpty;
    private ListView lv;
    private Button btnRefresh;
    private EditText etSearch;
    private GameListAdapter adapter;

    // Lista completa
    private List<GameInfo> allGames = new ArrayList<>();

    // Polling automático
    private final Handler handler = new Handler();
    private final Runnable poll = () -> {
        cargarSilencioso();
        programarPolling();
    };

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_games_list);
        setTitle(getString(R.string.title_games_inactive));

        pb = findViewById(R.id.pb);
        tvEmpty = findViewById(R.id.tvEmpty);
        lv = findViewById(R.id.lvGames);
        btnRefresh = findViewById(R.id.btnRefresh);
        etSearch = findViewById(R.id.etSearch);

        adapter = new GameListAdapter(this, new GameListAdapter.Actions() {
            @Override public void onAccept(GameInfo g) { /* no-op en inactivas */ }
            @Override public void onReject(GameInfo g) { /* no-op en inactivas */ }
            @Override public void onCancel(GameInfo g) { /* no-op en inactivas */ }
        });
        lv.setAdapter(adapter);

        // Click en una partida finalizada -> ver el detalle
        lv.setOnItemClickListener((parent, view, position, id) -> {
            GameInfo g = (GameInfo) parent.getItemAtPosition(position);
            if (g != null) {
                Intent i = new Intent(this, GameDetailActivity.class);
                i.putExtra("game_id", (long) g.id);
                startActivity(i);
            }
        });

        btnRefresh.setOnClickListener(v -> cargar());

        // Filtro de búsqueda
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

        cargar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargar();
        programarPolling();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(poll);
    }

    private void programarPolling() {
        handler.removeCallbacks(poll);
        handler.postDelayed(poll, POLL_INTERVAL);
    }

    /** Carga con spinner visible (para carga inicial o manual) */
    private void cargar() {
        String token = SessionManager.getToken(this);
        if (token == null) {
            Toast.makeText(this, getString(R.string.err_sesion_caducada), Toast.LENGTH_LONG).show();
            return;
        }

        pb.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);

        realizarCarga(true);
    }

    /** Carga silenciosa sin spinner (para polling automático) */
    private void cargarSilencioso() {
        String token = SessionManager.getToken(this);
        if (token == null) {
            return;
        }

        realizarCarga(false);
    }

    private void realizarCarga(boolean mostrarSpinner) {
        String token = SessionManager.getToken(this);
        if (token == null) return;

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Authentication", token);

        String url = new ListarInactivosRequest().buildUrl(getString(R.string.api_game_inactive));
        client.get(url, new ListarInactivosResponseHandler(this, games -> {
            if (mostrarSpinner) pb.setVisibility(View.GONE);

            allGames.clear();
            if (games != null) {
                allGames.addAll(games);
            }

            // Aplicar filtro de búsqueda actual
            filtrar(etSearch.getText().toString());
        }) {
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
                if (mostrarSpinner) {
                    pb.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                    tvEmpty.setText(getString(R.string.list_error_load, statusCode));
                }
            }
        });
    }

    private void filtrar(String query) {
        List<GameInfo> filtered = new ArrayList<>();
        String q = query.trim().toLowerCase();

        for (GameInfo g : allGames) {
            if (q.isEmpty()) {
                filtered.add(g);
            } else {
                // Buscar por nombre de enemigo
                String enemyName = (g.getEnemy() != null && g.getEnemy().getUsername() != null)
                        ? g.getEnemy().getUsername().toLowerCase()
                        : "";
                if (enemyName.contains(q)) {
                    filtered.add(g);
                }
            }
        }

        adapter.setItems(filtered);

        if (filtered.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText(q.isEmpty()
                    ? getString(R.string.list_empty_inactive)
                    : getString(R.string.list_empty_search));
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }
}
