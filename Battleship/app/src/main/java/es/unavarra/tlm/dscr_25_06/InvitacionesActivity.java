package es.unavarra.tlm.dscr_25_06;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Lista /v2/game/active y muestra:
 *  - waiting & your_turn==true  -> "Te han invitado" (puedes ACEPTAR/RECHAZAR)
 *  - waiting & your_turn==false -> "Enviada por ti" (puedes cancelar con DELETE)
 */
public class InvitacionesActivity extends AppCompatActivity {

    private RecyclerView rv;
    private ProgressBar pb;
    private TextView empty;
    private JuegosAdapter adapter;

    private String URL_GAME_BASE, URL_ACTIVE;

    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_invitaciones);

        setTitle(getString(R.string.title_invitaciones));

        URL_GAME_BASE = getString(R.string.api_game_base);
        URL_ACTIVE = getString(R.string.api_game_active);

        rv = findViewById(R.id.rv_invitaciones);
        pb = findViewById(R.id.pb_cargando);
        empty = findViewById(R.id.tv_empty);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JuegosAdapter(new ArrayList<>(),
                this::aceptar, this::rechazar);
        rv.setAdapter(adapter);

        cargar();
    }

    private void cargar() {
        String token = SessionManager.getToken(this);
        if (token == null) {
            Toast.makeText(this, getString(R.string.session_expired), Toast.LENGTH_LONG).show();
            return;
        }

        pb.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Authentication", token);

        client.get(URL_ACTIVE, new ListarActivosResponseHandler(this, all -> {
            pb.setVisibility(View.GONE);

            // filtra solo 'waiting'
            List<GameInfo> waiting = new java.util.ArrayList<>();
            for (GameInfo g : all) if (g.isWaiting()) waiting.add(g);

            adapter.setItems(waiting);
            empty.setVisibility(waiting.isEmpty() ? View.VISIBLE : View.GONE);
            if (waiting.isEmpty()) empty.setText(getString(R.string.msg_sin_pendientes));
        }));
    }

    private void aceptar(GameInfo g) {
        String token = SessionManager.getToken(this);
        if (token == null) { Toast.makeText(this, getString(R.string.session_expired), Toast.LENGTH_LONG).show(); return; }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Authentication", token);

        String url = new AceptarInvitacionRequest(g.id).buildUrl(URL_GAME_BASE);
        // POST /v2/game/{id}
        client.post(this, url, null, "application/json",
                new AceptarInvitacionResponseHandler(this, game -> {
                    // tras aceptar, recarga lista (desaparecerá de 'waiting')
                    cargar();
                }));
    }

    private void rechazar(GameInfo g) {
        String token = SessionManager.getToken(this);
        if (token == null) { Toast.makeText(this, getString(R.string.session_expired), Toast.LENGTH_LONG).show(); return; }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Authentication", token);

        String url = new RechazarInvitacionRequest(g.id).buildUrl(URL_GAME_BASE);
        client.delete(this, url, null,
                new RechazarInvitacionResponseHandler(this, game -> {
                    // tras rechazar/cancelar, recarga
                    cargar();
                }));
    }

    // ---------------- Adapter----------------
    static class JuegosAdapter extends RecyclerView.Adapter<JuegosAdapter.VH> {
        interface OnAceptar { void run(GameInfo g); }
        interface OnRechazar { void run(GameInfo g); }

        private List<GameInfo> data;
        private final OnAceptar onAceptar; private final OnRechazar onRechazar;

        JuegosAdapter(List<GameInfo> d, OnAceptar a, OnRechazar r) { data=d; onAceptar=a; onRechazar=r; }

        void setItems(List<GameInfo> d){ data=d; notifyDataSetChanged(); }

        @Override public VH onCreateViewHolder(android.view.ViewGroup p, int vt) {
            android.view.View v = android.view.LayoutInflater.from(p.getContext())
                    .inflate(R.layout.item_invitacion, p, false);
            return new VH(v);
        }
        @Override public void onBindViewHolder(VH h, int pos) {
            GameInfo g = data.get(pos);
            h.tvTitulo.setText(
                    (g.enemy != null && g.enemy.getUsername() != null)
                            ? g.enemy.getUsername()
                            : h.itemView.getContext().getString(R.string.game_title_fallback, g.id)
            );

            if (g.isPendingMyDecision()) {
                h.tvEstado.setText(h.itemView.getContext().getString(R.string.label_debes_responder));
                h.btnAceptar.setVisibility(View.VISIBLE);
                h.btnRechazar.setVisibility(View.VISIBLE);
            } else if (g.isPendingTheirDecision()) {
                h.tvEstado.setText(h.itemView.getContext().getString(R.string.label_esperando_otro));
                h.btnAceptar.setVisibility(View.GONE);
                h.btnRechazar.setVisibility(View.GONE); // cancelar si quieres
            } else {
                h.tvEstado.setText(g.state);
                h.btnAceptar.setVisibility(View.GONE);
                h.btnRechazar.setVisibility(View.GONE);
            }

            h.btnAceptar.setOnClickListener(v -> onAceptar.run(g));
            h.btnRechazar.setOnClickListener(v -> onRechazar.run(g));
        }
        @Override public int getItemCount(){ return data==null?0:data.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView tvTitulo, tvEstado; Button btnAceptar, btnRechazar;
            VH(android.view.View v){ super(v);
                tvTitulo   = v.findViewById(R.id.tv_titulo);
                tvEstado   = v.findViewById(R.id.tv_estado);
                btnAceptar = v.findViewById(R.id.btn_aceptar);
                btnRechazar= v.findViewById(R.id.btn_rechazar);
            }
        }
    }
}
