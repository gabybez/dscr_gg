package es.unavarra.tlm.carlos_examen_2025ord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/*
 * ============================================================================
 * MainActivity - LISTADO DE PARTIDOS
 * ============================================================================
 *
 * FLUJO:
 * 1. La app se abre
 * 2. En onCreate() hacemos petición GET para obtener los partidos
 * 3. Mostramos los partidos en el ListView con el GameAdapter
 * 4. Al hacer click en un partido, abrimos GameDetailActivity
 *
 * PUNTUACIÓN:
 * - Mostrar listado de partidos (petición y alineaciones): 2 puntos
 * - Cebreado en el listado: 1 punto
 */
public class MainActivity extends AppCompatActivity {

    // URL base de la API
    private static final String BASE_URL = "https://api.battleship.tatai.es/v2/exam-2025-2";

    // URL para obtener todos los partidos
    private static final String GAMES_URL = BASE_URL + "/games";

    // Widgets
    private ListView listView;

    // HTTP y JSON
    private AsyncHttpClient httpClient;
    private Gson gson;

    // Lista de partidos (la guardamos para acceder al hacer click)
    private List<Game> gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ====================================================================
        // PASO 1: Inicializar objetos
        // ====================================================================
        httpClient = new AsyncHttpClient();
        gson = new Gson();

        // Obtener referencia al ListView
        listView = findViewById(R.id.list_games);

        // ====================================================================
        // PASO 2: Configurar el click en los items de la lista
        // ====================================================================
        /*
         * setOnItemClickListener se ejecuta cuando el usuario pulsa una fila.
         *
         * Parámetros del callback onItemClick:
         * - parent: El AdapterView (ListView)
         * - view: La vista de la fila pulsada
         * - position: Índice de la fila (0, 1, 2...)
         * - id: ID del elemento (lo que retorna getItemId)
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Obtener el partido clickeado
                Game game = gamesList.get(position);

                // Abrir la pantalla de detalle pasando los datos necesarios
                // Pasamos el ID del partido y los IDs y nombres de los equipos
                Intent intent = new Intent(MainActivity.this, GameDetailActivity.class);

                // ID del partido (necesario para la URL de actualización)
                intent.putExtra("gameId", game.getId());

                // Datos del equipo visitante (away)
                intent.putExtra("awayId", game.getAway().getId());
                intent.putExtra("awayName", game.getAway().getName());
                intent.putExtra("awayScore", game.getAway().getScore());

                // Datos del equipo local (home)
                intent.putExtra("homeId", game.getHome().getId());
                intent.putExtra("homeName", game.getHome().getName());
                intent.putExtra("homeScore", game.getHome().getScore());

                // Iniciar la Activity de detalle
                startActivity(intent);
            }
        });

        // ====================================================================
        // PASO 3: Cargar los partidos del servidor
        // ====================================================================
        fetchGames();
    }

    /*
     * ========================================================================
     * onResume() - Se ejecuta cada vez que la Activity vuelve a primer plano
     * ========================================================================
     *
     * Recargamos los partidos cuando volvemos de GameDetailActivity
     * para mostrar las puntuaciones actualizadas.
     */
    @Override
    protected void onResume() {
        super.onResume();
        fetchGames();
    }

    /*
     * ========================================================================
     * fetchGames() - Petición GET para obtener todos los partidos
     * ========================================================================
     *
     * TIPO: GET (solo obtener datos, no enviamos nada)
     * URL: https://api.battleship.tatai.es/v2/exam-2025-2/games
     *
     * RESPUESTA EXITOSA (HTTP 200):
     * {
     *   "games": [
     *     { "id": "...", "away": {...}, "home": {...} },
     *     ...
     *   ]
     * }
     */
    private void fetchGames() {

        // Petición GET - No necesita body ni StringEntity
        httpClient.get(GAMES_URL, new AsyncHttpResponseHandler() {

            /*
             * onSuccess() - Respuesta exitosa (HTTP 200-299)
             *
             * Parámetros:
             * - statusCode: código HTTP (200, 201, etc.)
             * - headers: cabeceras de la respuesta
             * - responseBody: cuerpo como bytes
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                // Convertir bytes a String
                String json = new String(responseBody);

                // Parsear JSON a objeto Java
                // gson.fromJson(jsonString, ClaseDestino.class)
                GamesResponse response = gson.fromJson(json, GamesResponse.class);

                // Guardar la lista para usarla en el click listener
                gamesList = response.getGames();

                // Crear adapter y asignarlo al ListView
                GameAdapter adapter = new GameAdapter(MainActivity.this, gamesList);
                listView.setAdapter(adapter);
            }

            /*
             * onFailure() - Error (HTTP 400-599 o error de red)
             */
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this,
                        R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
