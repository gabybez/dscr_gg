package es.unavarra.tlm.examen_2023_claude;

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

    private static final String URL = "https://api.battleship.tatai.es/v2/exam-2023-1";

    private ListView listView;
    private AsyncHttpClient client;
    private Gson gson;

    // Guardamos la lista para poder acceder desde el click listener
    private List<RunnerResult> runnerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar
        client = new AsyncHttpClient();
        gson = new Gson();
        listView = findViewById(R.id.list_runners);

        // Configurar click en items de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el runner clickeado
                RunnerResult runner = runnerList.get(position);

                // Abrir DetailActivity pasando los datos
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("position", runner.getPosition());
                intent.putExtra("categoryPosition", runner.getCategoryPosition());
                intent.putExtra("name", runner.getRunner().getFullName());
                intent.putExtra("dorsal", runner.getNumber());
                intent.putExtra("time", runner.getFormattedTime());
                startActivity(intent);
            }
        });

        // Hacer petición GET al servidor
        fetchRunners();
    }

    /**
     * Petición GET al servidor para obtener los corredores.
     * Nota: GET no necesita body ni StringEntity.
     */
    private void fetchRunners() {
        // GET es más simple que POST - no necesita entity ni content-type
        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // Parsear JSON
                String json = new String(responseBody);
                RaceResponse response = gson.fromJson(json, RaceResponse.class);

                // Procesar y mostrar datos
                processAndDisplayRunners(response.getRunners());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Procesa los datos: ordena por tiempo y calcula posiciones.
     */
    private void processAndDisplayRunners(List<RunnerResult> runners) {
        // ====================================================================
        // PASO 1: Ordenar por tiempo ASCENDENTE (menor tiempo = más rápido = primero)
        // ====================================================================
        Collections.sort(runners, new Comparator<RunnerResult>() {
            @Override
            public int compare(RunnerResult r1, RunnerResult r2) {
                return r1.getTime() - r2.getTime();
            }
        });

        // ====================================================================
        // PASO 2: Calcular posiciones
        // ====================================================================
        int maleCount = 0;   // Contador para hombres
        int femaleCount = 0; // Contador para mujeres

        for (int i = 0; i < runners.size(); i++) {
            RunnerResult runner = runners.get(i);

            // Posición general (1, 2, 3...)
            runner.setPosition(i + 1);

            // Posición por categoría (género)
            if (runner.getGender().equals("M")) {
                maleCount++;
                runner.setCategoryPosition(maleCount);
            } else {
                femaleCount++;
                runner.setCategoryPosition(femaleCount);
            }
        }

        // Guardar referencia para el click listener
        runnerList = runners;

        // ====================================================================
        // PASO 3: Crear adapter y asignarlo al ListView
        // ====================================================================
        RunnerAdapter adapter = new RunnerAdapter(this, runners);
        listView.setAdapter(adapter);
    }
}