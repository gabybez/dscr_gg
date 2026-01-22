package es.unavarra.tlm.ordinario2025solo;

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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // URL base de la API
    private static final String BASE_URL = "https://api.battleship.tatai.es/v2/exam-2025-2";

    // URL para obtener todos los partidos
    private static final String GAMES_URL = BASE_URL + "/games";
    private ListView listView;
    private AsyncHttpClient client;
    private Gson gson;

    // Guardamos la lista para poder acceder desde el click listener
    private List<Game> gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar
        client = new AsyncHttpClient();
        gson = new Gson();
        listView = findViewById(R.id.list_games);

        // Configurar click en items de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el runner clickeado
                Game game = gamesList.get(position);

                // Abrir DetailActivity pasando los datos
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                //Id del partido (para construir la url del Put)
                intent.putExtra("gameId", game.getId());
                //Detalles del equipo visitante
                intent.putExtra("awayId", game.getAway().getId());
                intent.putExtra("awayName",game.getAway().getName());
                intent.putExtra("awayScore",game.getAway().getScore());
                //Detalles del equipo local
                intent.putExtra("homeId", game.getHome().getId());
                intent.putExtra("homeName",game.getHome().getName());
                intent.putExtra("homeScore",game.getHome().getScore());

                //Iniciar la activity de detalle
                startActivity(intent);
            }
        });

        // Hacer petición GET al servidor
        fetchGames();
    }

    /*
     * ========================================================================
     * onResume() - Se ejecuta cada vez que la Activity vuelve a primer plano
     * ========================================================================
     *
     * Recargamos los partidos cuando volvemos de GameDetailActivity
     * para mostrar las puntuaciones actualizadas. Clave
     */
    @Override
    protected void onResume() {
        super.onResume();
        fetchGames();
    }


    /*
     * Petición GET al servidor para obtener los corredores.
     * Nota: GET no necesita body ni StringEntity.
     */
    private void fetchGames() {
        // GET es más simple que POST - no necesita entity ni content-type
        client.get(GAMES_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Parsear JSON
                String json = new String(responseBody);
                GamesResponse response = gson.fromJson(json, GamesResponse.class);

                // Guardar la lista para usarla en el click listener
                gamesList = response.getGames();

                // Crear adapter y asignarlo al ListView
                ListAdapter adapter = new ListAdapter(MainActivity.this, gamesList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

}