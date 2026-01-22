package es.unavarra.tlm.examenclaude1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;
/*
 * ============================================================================
 * MainActivity - LISTADO DE MESAS
 * ============================================================================
 *
 * FLUJO:
 * 1. La app se abre
 * 2. En onCreate() hacemos petición GET para obtener las mesas
 * 3. Mostramos las mesas en el ListView con el TableAdapter
 * 4. Al hacer click en una mesa, abrimos ReserveActivity
 *
 */

public class MainActivity extends AppCompatActivity {
    //URL de la api
    private static final String URL = "https://api.restaurant.example.com/v1/tables";
    //Widgets
    private ListView listView;
    //HTTP y JSON
    private AsyncHttpClient httpClient;
    private Gson gson;
    //Lista de mesas(la guardamos para acceder al hacer click)
    private List<Table> tablesList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Paso 1 Inicializar objetos
        httpClient = new AsyncHttpClient();
        gson = new Gson();
        //obtener referencia al Listview
        listView = findViewById(R.id.list_tables);

        //Paso 2 Configurar el click en los items de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //obtener la mesa clicada
                Table table = tablesList.get(position);
                //Abrir la reserve activity pasando los datos necesarios
                //Solo en caso de que la mesa esté disponible
                if(table.getAvailable()){
                    Intent intent = new Intent(MainActivity.this, ReserveActivity.class);
                    //Id de la tabla (necesaria para la url del POST)
                    intent.putExtra("tableID", table.getId());
                    //Capacidad
                    intent.putExtra("capacity", table.getCapacity());
                    //Abrimos el intent
                    startActivity(intent);
                }else{//Si no está, ponemos un toast de que esa mesa está ocupada
                    Toast.makeText(MainActivity.this, R.string.not_available, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Paso 3 Cargar las mesas del servidor
        fetchTables();
    }
    //onResume()- se ejecuta cada vez que el activity vuelve al primer plano
    @Override
    protected void onResume(){
        super.onResume();
        fetchTables();
    }

    //fetchTables()- Peticion Get al servidor para obtener las mesas
    private void fetchTables(){
        //Peticion GET- no necesita body ni StringEntity
        httpClient.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //convertr bytes a string
                String json = new String(responseBody);
                //Parsear json a objeto java
                TablesResponse response = gson.fromJson(json, TablesResponse.class);
                //guardar la lista para usarla en el clickListener
                tablesList = response.getTables();
                //crear adapter y asignarlo al ListView
                TableAdapter adapter = new TableAdapter(MainActivity.this, tablesList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this,
                        R.string.error_network, Toast.LENGTH_SHORT).show();

            }
        });
    }
}