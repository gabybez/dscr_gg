package es.unavarra.tlm.dscr_25_06;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class GameDetailActivity extends AppCompatActivity {

    private static final int TOTAL_SHIPS = 7;
    private static final String PREFS_GAME_RESULTS = "game_results";

    private long gameId;

    private ProgressBar pb;
    private TextView tvTitle, tvState, tvTurn, tvBoardLabel;
    private View boxPlacing;
    private LinearLayout boxActions;
    private Spinner spShipType;
    private Button btnPlace, btnSwitchBoard, btnEnemyProfile, btnSurrender;
    private RecyclerView rv;
    private BoardAdapter boardAdapter;

    private final Gson gson = new Gson();
    private GameDetail detail;
    private List<BoardCell> cells;

    private boolean showingMyBoard = true;
    private boolean resultShown = false; // Para evitar mostrar el aviso múltiples veces

    private int carrierCount = 0;
    private int battleshipCount = 0;
    private int cruiserCount = 0;
    private int submarineCount = 0;
    private int destroyerCount = 0;

    private final Handler handler = new Handler();
    private final Runnable poll = () -> cargar(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        gameId = getIntent().getLongExtra("game_id", -1);
        if (gameId <= 0) {
            finish();
            return;
        }
        setTitle(getString(R.string.title_game_detail, gameId));

        pb = findViewById(R.id.pb);
        tvTitle = findViewById(R.id.tvTitle);
        tvState = findViewById(R.id.tvState);
        tvTurn = findViewById(R.id.tvTurn);
        tvBoardLabel = findViewById(R.id.tvBoardLabel);

        boxPlacing = findViewById(R.id.boxPlacing);
        boxActions = findViewById(R.id.boxActions);
        spShipType = findViewById(R.id.spShipType);
        btnPlace = findViewById(R.id.btnPlace);
        btnSwitchBoard = findViewById(R.id.btnSwitchBoard);
        btnEnemyProfile = findViewById(R.id.btnEnemyProfile);
        btnSurrender = findViewById(R.id.btnSurrender);

        rv = findViewById(R.id.rvBoard);
        rv.setLayoutManager(new GridLayoutManager(this, 10));

        btnPlace.setOnClickListener(v -> placeSelectedShip());
        btnSwitchBoard.setOnClickListener(v -> toggleBoard());
        btnEnemyProfile.setOnClickListener(v -> verPerfilEnemigo());
        btnSurrender.setOnClickListener(v -> confirmarRendicion());

        cargar(true);
    }

    private void verPerfilEnemigo() {
        if (detail == null || detail.game == null || detail.game.getEnemy() == null) {
            Toast.makeText(this, getString(R.string.err_no_enemy), Toast.LENGTH_SHORT).show();
            return;
        }

        User enemy = detail.game.getEnemy();
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("user_id", (long) enemy.getId());
        i.putExtra("username", enemy.getUsername());
        startActivity(i);
    }

    private void confirmarRendicion() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.surrender_title))
                .setMessage(getString(R.string.surrender_confirm))
                .setPositiveButton(getString(R.string.btn_surrender), (dialog, which) -> rendirse())
                .setNegativeButton(getString(R.string.btn_cancelar), null)
                .show();
    }

    private void rendirse() {
        String token = SessionManager.getToken(this);
        if (token == null) {
            Toast.makeText(this, getString(R.string.err_sesion_caducada), Toast.LENGTH_LONG).show();
            return;
        }

        pb.setVisibility(View.VISIBLE);

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Authentication", token);

        String url = getString(R.string.api_game_base) + gameId;
        client.delete(this, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pb.setVisibility(View.GONE);
                Toast.makeText(GameDetailActivity.this, getString(R.string.msg_surrendered), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pb.setVisibility(View.GONE);
                Toast.makeText(GameDetailActivity.this, getString(R.string.err_surrender, statusCode), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (detail != null) programarPolling();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(poll);
    }

    private void toggleBoard() {
        showingMyBoard = !showingMyBoard;
        montarTablero();
        updateBoardLabel();
    }

    private void updateBoardLabel() {
        if (showingMyBoard) {
            tvBoardLabel.setText(getString(R.string.label_my_board));
            btnSwitchBoard.setText(getString(R.string.btn_show_enemy_board));
        } else {
            tvBoardLabel.setText(getString(R.string.label_enemy_board));
            btnSwitchBoard.setText(getString(R.string.btn_show_my_board));
        }
    }

    private void programarPolling() {
        handler.removeCallbacks(poll);
        if (detail == null || detail.game == null) return;

        String state = detail.game.state;
        int barcosColocados = (detail.ships != null) ? detail.ships.size() : 0;

        boolean debeEsperar = false;

        if ("placing".equalsIgnoreCase(state)) {
            if (barcosColocados >= TOTAL_SHIPS) debeEsperar = true;
        } else if ("started".equalsIgnoreCase(state) && !detail.game.your_turn) {
            debeEsperar = true;
        }

        if (debeEsperar) handler.postDelayed(poll, 5000);
    }

    private void cargar(boolean showSpinner) {
        String token = SessionManager.getToken(this);
        if (token == null) {
            Toast.makeText(this, getString(R.string.err_sesion_caducada), Toast.LENGTH_LONG).show();
            return;
        }

        if (showSpinner) pb.setVisibility(View.VISIBLE);

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-Authentication", token);

        String url = new GetGameDetailRequest(gameId).buildUrl(getString(R.string.api_game_base));
        client.get(url, new GetGameDetailResponseHandler(this, d -> {
            if (showSpinner) pb.setVisibility(View.GONE);
            this.detail = d;

            GameInfo g = d.game;
            String enemy = (g.getEnemy() != null && g.getEnemy().getUsername() != null)
                    ? g.getEnemy().getUsername() : ("#" + g.id);
            tvTitle.setText(getString(R.string.detalle_rival, enemy));
            tvState.setText(getString(R.string.detalle_estado, g.state));

            contarBarcosColocados();
            updateUiForState();
            montarTablero();
            updateBoardLabel();
            programarPolling();

            // Comprobar si hay que mostrar aviso de resultado
            checkGameResult();
        }));
    }

    /**
     * Comprueba si la partida ha terminado y muestra aviso de victoria/derrota
     * solo la primera vez que se entra.
     */
    private void checkGameResult() {
        if (detail == null || detail.game == null) return;
        if (resultShown) return; // Ya se mostró en esta sesión

        GameInfo g = detail.game;
        if (!"finished".equalsIgnoreCase(g.state)) return;
        if (g.you_won == null) return;

        // Comprobar si ya se mostró el aviso para esta partida
        SharedPreferences prefs = getSharedPreferences(PREFS_GAME_RESULTS, MODE_PRIVATE);
        String key = "game_" + gameId + "_shown";
        boolean alreadyShown = prefs.getBoolean(key, false);

        if (!alreadyShown) {
            resultShown = true;

            // Guardar que ya se mostró
            prefs.edit().putBoolean(key, true).apply();

            // Mostrar diálogo de resultado
            if (g.you_won) {
                showResultDialog(
                        getString(R.string.dialog_win_title),
                        getString(R.string.dialog_win_message)
                );
            } else {
                showResultDialog(
                        getString(R.string.dialog_lose_title),
                        getString(R.string.dialog_lose_message)
                );
            }
        }
    }

    private void showResultDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.btn_ok), null)
                .setCancelable(true)
                .show();
    }

    private void contarBarcosColocados() {
        carrierCount = battleshipCount = cruiserCount = submarineCount = destroyerCount = 0;

        if (detail != null && detail.ships != null) {
            for (Ship s : detail.ships) {
                if (s.type == null) continue;
                switch (s.type) {
                    case carrier: carrierCount++; break;
                    case battleship: battleshipCount++; break;
                    case cruiser: cruiserCount++; break;
                    case submarine: submarineCount++; break;
                    case destroyer: destroyerCount++; break;
                }
            }
        }
    }

    private void updateUiForState() {
        if (detail == null || detail.game == null) return;
        GameInfo g = detail.game;

        // Mostrar botón de cambiar tablero en started o finished
        if ("started".equalsIgnoreCase(g.state) || "finished".equalsIgnoreCase(g.state)) {
            btnSwitchBoard.setVisibility(View.VISIBLE);
        } else {
            btnSwitchBoard.setVisibility(View.GONE);
            showingMyBoard = true;
        }

        // Mostrar botones de acción en placing, started (NO en finished)
        if ("placing".equalsIgnoreCase(g.state) || "started".equalsIgnoreCase(g.state)) {
            boxActions.setVisibility(View.VISIBLE);
            btnSurrender.setVisibility(View.VISIBLE);
            btnEnemyProfile.setVisibility(View.VISIBLE);
        } else if ("finished".equalsIgnoreCase(g.state)) {
            // En finished: solo ver perfil, no rendirse
            boxActions.setVisibility(View.VISIBLE);
            btnSurrender.setVisibility(View.GONE);
            btnEnemyProfile.setVisibility(View.VISIBLE);
        } else {
            boxActions.setVisibility(View.GONE);
        }

        if ("placing".equalsIgnoreCase(g.state)) {
            int barcosColocados = (detail.ships != null) ? detail.ships.size() : 0;

            if (barcosColocados >= TOTAL_SHIPS) {
                tvTurn.setText(getString(R.string.detalle_todos_colocados));
                boxPlacing.setVisibility(View.GONE);
                btnPlace.setVisibility(View.GONE);
            } else {
                tvTurn.setText(getString(R.string.detalle_coloca_barcos_count, barcosColocados, TOTAL_SHIPS));
                boxPlacing.setVisibility(View.VISIBLE);
                btnPlace.setVisibility(View.VISIBLE);

                List<String> available = new ArrayList<>();
                if (carrierCount < 1) available.add("carrier (5)");
                if (battleshipCount < 1) available.add("battleship (4)");
                if (cruiserCount < 1) available.add("cruiser (3)");
                if (submarineCount < 2) available.add("submarine (3) [" + submarineCount + "/2]");
                if (destroyerCount < 2) available.add("destroyer (2) [" + destroyerCount + "/2]");

                if (!available.isEmpty()) {
                    spShipType.setAdapter(new ArrayAdapter<>(
                            this, android.R.layout.simple_spinner_dropdown_item, available));
                }
            }

        } else if ("started".equalsIgnoreCase(g.state)) {
            tvTurn.setText(g.your_turn
                    ? getString(R.string.detalle_turno_tuyo)
                    : getString(R.string.detalle_turno_rival));
            boxPlacing.setVisibility(View.GONE);
            btnPlace.setVisibility(View.GONE);

        } else {
            String resultado = "";
            if (g.isFinished() && g.you_won != null) {
                resultado = g.you_won
                        ? " - " + getString(R.string.detalle_ganaste)
                        : " - " + getString(R.string.detalle_perdiste);
            }
            tvTurn.setText(getString(R.string.detalle_partida_finalizada) + resultado);
            boxPlacing.setVisibility(View.GONE);
            btnPlace.setVisibility(View.GONE);
        }
    }

    private void montarTablero() {
        if (cells == null) {
            cells = new ArrayList<>(100);
            for (int r = 0; r < 10; r++) {
                for (int c = 0; c < 10; c++) {
                    cells.add(new BoardCell(r, c));
                }
            }
            boardAdapter = new BoardAdapter(this, cells, (cell, position) -> onCellTap(cell));
            rv.setAdapter(boardAdapter);
        }

        for (BoardCell bc : cells) {
            bc.shotDone = bc.shotHit = bc.shotReceived = bc.receivedHit = false;
            bc.myShip = false;
            bc.selected = false;
        }

        if (showingMyBoard) {
            if (detail != null && detail.ships != null) {
                for (Ship s : detail.ships) {
                    if (s.position == null) continue;
                    for (Position p : s.position) {
                        int idx = posToIndex(p.row, p.column);
                        if (idx >= 0 && idx < cells.size()) cells.get(idx).myShip = true;
                    }
                }
            }

            if (detail != null && detail.gunfire != null && detail.gunfire.received != null) {
                for (Shot s : detail.gunfire.received) {
                    int idx = posToIndex(s.position.row, s.position.column);
                    if (idx >= 0 && idx < cells.size()) {
                        BoardCell bc = cells.get(idx);
                        bc.shotReceived = true;
                        bc.receivedHit = (s.result == ShotResult.hit || s.result == ShotResult.sunk);
                    }
                }
            }
        } else {
            if (detail != null && detail.gunfire != null && detail.gunfire.done != null) {
                for (Shot s : detail.gunfire.done) {
                    int idx = posToIndex(s.position.row, s.position.column);
                    if (idx >= 0 && idx < cells.size()) {
                        BoardCell bc = cells.get(idx);
                        bc.shotDone = true;
                        bc.shotHit = (s.result == ShotResult.hit || s.result == ShotResult.sunk);
                    }
                }
            }
        }

        boardAdapter.notifyDataSetChanged();
    }

    private int posToIndex(String row, int column) {
        int r = row.charAt(0) - 'A';
        int c = column - 1;
        return r * 10 + c;
    }

    private void onCellTap(BoardCell cell) {
        if (detail == null || detail.game == null) return;
        String state = detail.game.state;

        if ("finished".equalsIgnoreCase(state)) {
            return;
        }

        if ("placing".equalsIgnoreCase(state)) {
            int barcosColocados = (detail.ships != null) ? detail.ships.size() : 0;
            if (barcosColocados >= TOTAL_SHIPS) return;

            if (cell.myShip) {
                Toast.makeText(this, getString(R.string.err_cell_has_ship), Toast.LENGTH_SHORT).show();
                return;
            }
            cell.selected = !cell.selected;
            boardAdapter.notifyItemChanged(cell.rowIndex * 10 + cell.colIndex);
            return;
        }

        if ("started".equalsIgnoreCase(state)) {
            if (showingMyBoard) {
                Toast.makeText(this, getString(R.string.err_shoot_own_board), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!detail.game.your_turn) {
                Toast.makeText(this, getString(R.string.err_not_your_turn), Toast.LENGTH_SHORT).show();
                return;
            }
            if (cell.shotDone) {
                Toast.makeText(this, getString(R.string.err_cell_already_shot), Toast.LENGTH_SHORT).show();
                return;
            }
            shootAt(cell);
        }
    }

    private void placeSelectedShip() {
        String selectedItem = (String) spShipType.getSelectedItem();
        if (selectedItem == null) {
            Toast.makeText(this, getString(R.string.err_select_ship_type), Toast.LENGTH_SHORT).show();
            return;
        }

        ShipType ship = parseShipType(selectedItem);
        if (ship == null) {
            Toast.makeText(this, getString(R.string.err_select_ship_type), Toast.LENGTH_SHORT).show();
            return;
        }

        List<BoardCell> sel = getSelectedCells();
        if (sel.isEmpty()) {
            Toast.makeText(this, getString(R.string.err_no_selection), Toast.LENGTH_SHORT).show();
            return;
        }

        int size = shipSize(ship);
        if (sel.size() != size) {
            Toast.makeText(this,
                    getString(R.string.err_selection_length) + " " + getString(R.string.err_selection_length_detail, size),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Collections.sort(sel, Comparator
                .comparingInt((BoardCell bc) -> bc.rowIndex)
                .thenComparingInt(bc -> bc.colIndex));

        boolean sameRow = true, sameCol = true;
        for (BoardCell bc : sel) {
            if (bc.rowIndex != sel.get(0).rowIndex) sameRow = false;
            if (bc.colIndex != sel.get(0).colIndex) sameCol = false;
        }

        if (!sameRow && !sameCol) {
            Toast.makeText(this, getString(R.string.err_selection_shape), Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 1; i < sel.size(); i++) {
            BoardCell prev = sel.get(i - 1);
            BoardCell curr = sel.get(i);
            if (sameRow) {
                if (curr.colIndex != prev.colIndex + 1) {
                    Toast.makeText(this, getString(R.string.err_selection_shape), Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                if (curr.rowIndex != prev.rowIndex + 1) {
                    Toast.makeText(this, getString(R.string.err_selection_shape), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        List<Position> positions = new ArrayList<>();
        for (BoardCell bc : sel) {
            positions.add(new Position(bc.rowLetter(), bc.columnHuman()));
        }

        PlaceShipRequest req = new PlaceShipRequest(ship, positions);
        sendPlaceShip(req);
    }

    private ShipType parseShipType(String s) {
        if (s.startsWith("carrier")) return ShipType.carrier;
        if (s.startsWith("battleship")) return ShipType.battleship;
        if (s.startsWith("cruiser")) return ShipType.cruiser;
        if (s.startsWith("submarine")) return ShipType.submarine;
        if (s.startsWith("destroyer")) return ShipType.destroyer;
        return null;
    }

    private List<BoardCell> getSelectedCells() {
        List<BoardCell> out = new ArrayList<>();
        for (BoardCell bc : cells) {
            if (bc.selected) out.add(bc);
        }
        return out;
    }

    private int shipSize(ShipType t) {
        switch (t) {
            case carrier: return 5;
            case battleship: return 4;
            case cruiser: return 3;
            case submarine: return 3;
            case destroyer: return 2;
        }
        return 3;
    }

    private void sendPlaceShip(PlaceShipRequest bodyReq) {
        String token = SessionManager.getToken(this);
        if (token == null) {
            Toast.makeText(this, getString(R.string.err_sesion_caducada), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            String json = gson.toJson(bodyReq);
            StringEntity body = new StringEntity(json, "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("X-Authentication", token);

            String url = bodyReq.buildUrl(getString(R.string.api_game_base), gameId);
            client.post(this, url, body, "application/json",
                    new PlaceShipResponseHandler(this, (game, ships) -> {
                        for (BoardCell bc : cells) bc.selected = false;
                        cargar(false);
                    })
            );
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.err_preparando_peticion), Toast.LENGTH_LONG).show();
        }
    }

    private void shootAt(BoardCell cell) {
        String token = SessionManager.getToken(this);
        if (token == null) {
            Toast.makeText(this, getString(R.string.err_sesion_caducada), Toast.LENGTH_LONG).show();
            return;
        }

        ShootRequest req = new ShootRequest(new Position(cell.rowLetter(), cell.columnHuman()));
        try {
            String json = gson.toJson(req);
            StringEntity body = new StringEntity(json, "UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("X-Authentication", token);

            String url = req.buildUrl(getString(R.string.api_game_base), gameId);
            client.post(this, url, body, "application/json",
                    new ShootResponseHandler(this, (shot, gi, gun) -> {
                        cell.shotDone = true;
                        cell.shotHit = (shot.result == ShotResult.hit || shot.result == ShotResult.sunk);
                        boardAdapter.notifyItemChanged(cell.rowIndex * 10 + cell.colIndex);

                        // Comprobar si hemos ganado con este disparo
                        if (gi != null && "finished".equalsIgnoreCase(gi.state) && gi.you_won != null && gi.you_won) {
                            // ¡Victoria! Mostrar aviso inmediatamente
                            showResultDialog(
                                    getString(R.string.dialog_win_title),
                                    getString(R.string.dialog_win_message)
                            );
                            // Marcar como mostrado
                            SharedPreferences prefs = getSharedPreferences(PREFS_GAME_RESULTS, MODE_PRIVATE);
                            prefs.edit().putBoolean("game_" + gameId + "_shown", true).apply();
                            resultShown = true;
                        }

                        cargar(false);
                    })
            );
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.err_preparando_peticion), Toast.LENGTH_LONG).show();
        }
    }
}