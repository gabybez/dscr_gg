package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter para ListView:
 *  - Pinta título/subtítulo con estado.
 *  - Muestra botones si state == waiting:
 *      * your_turn == true  -> ACEPTAR + RECHAZAR
 *      * your_turn == false -> CANCELAR
 *  - Notifica a la Activity mediante callbacks (Actions).
 */
public class GameListAdapter extends BaseAdapter {

    public interface Actions {
        void onAccept(GameInfo g);
        void onReject(GameInfo g);
        void onCancel(GameInfo g);
    }

    private final Context ctx;
    private final LayoutInflater inflater;
    private final List<GameInfo> data = new ArrayList<>();
    private final Actions actions;

    public GameListAdapter(Context ctx, Actions actions) {
        this.ctx = ctx;
        this.inflater = LayoutInflater.from(ctx);
        this.actions = actions;
    }

    public void setItems(List<GameInfo> items) {
        data.clear();
        if (items != null) data.addAll(items);
        notifyDataSetChanged();
    }

    @Override public int getCount() { return data.size(); }
    @Override public GameInfo getItem(int position) { return data.get(position); }
    @Override public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH h;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_game, parent, false);
            h = new VH(convertView);
            convertView.setTag(h);
        } else {
            h = (VH) convertView.getTag();
        }

        GameInfo g = getItem(position);

        // Título: username del rival (enemy) o fallback
        String title = (g.getEnemy() != null && g.getEnemy().getUsername() != null)
                ? g.getEnemy().getUsername()
                : ctx.getString(R.string.row_title_fallback, g.id);
        h.tvTitle.setText(title);

        // Subtítulo: estado + pista
        String state = g.state;
        String extra = "";
        if ("waiting".equalsIgnoreCase(state)) {
            extra = g.your_turn
                    ? ctx.getString(R.string.row_sub_waiting_mine)
                    : ctx.getString(R.string.row_sub_waiting_theirs);
            state = ctx.getString(R.string.state_waiting);
        } else if ("placing".equalsIgnoreCase(state)) {
            extra = g.your_turn
                    ? ctx.getString(R.string.row_sub_placing_mine)
                    : ctx.getString(R.string.row_sub_placing_theirs);
            state = ctx.getString(R.string.state_placing);
        } else if ("started".equalsIgnoreCase(state)) {
            extra = g.your_turn
                    ? ctx.getString(R.string.row_sub_started_mine)
                    : ctx.getString(R.string.row_sub_started_theirs);
            state = ctx.getString(R.string.state_started);
        } else if ("finished".equalsIgnoreCase(state)) {
            state = ctx.getString(R.string.state_finished);
        } else if ("cancelled".equalsIgnoreCase(state)) {
            state = ctx.getString(R.string.state_cancelled);
        }
        h.tvSubtitle.setText(extra.isEmpty() ? state : (state + " " + extra));

        // Acciones (solo waiting)
        if ("waiting".equalsIgnoreCase(g.state)) {
            h.actions.setVisibility(View.VISIBLE);

            if (g.your_turn) {
                // Te han invitado -> ACEPTAR y RECHAZAR
                h.btnPrimary.setText(R.string.btn_aceptar);
                h.btnSecondary.setText(R.string.btn_rechazar);
                h.btnSecondary.setVisibility(View.VISIBLE);

                h.btnPrimary.setOnClickListener(v -> actions.onAccept(g));
                h.btnSecondary.setOnClickListener(v -> actions.onReject(g));
            } else {
                // Invitación enviada por ti -> CANCELAR
                h.btnPrimary.setText(R.string.btn_cancelar);
                h.btnSecondary.setVisibility(View.GONE);

                h.btnPrimary.setOnClickListener(v -> actions.onCancel(g));
            }
        } else {
            h.actions.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class VH {
        final TextView tvTitle, tvSubtitle;
        final LinearLayout actions;
        final Button btnPrimary, btnSecondary;
        VH(View v) {
            tvTitle = v.findViewById(R.id.tvTitle);
            tvSubtitle = v.findViewById(R.id.tvSubtitle);
            actions = v.findViewById(R.id.actions);
            btnPrimary = v.findViewById(R.id.btnPrimary);
            btnSecondary = v.findViewById(R.id.btnSecondary);
        }
    }
}
