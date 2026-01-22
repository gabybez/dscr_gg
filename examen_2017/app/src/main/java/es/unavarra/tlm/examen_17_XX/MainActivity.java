package es.unavarra.tlm.examen_17_XX;

import android.os.Bundle;
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

/*
 * ============================================================================
 * MainActivity - ACTIVIDAD PRINCIPAL
 * ============================================================================
 * 
 * FLUJO DE LA APLICACIÓN:
 * 1. Se abre la app
 * 2. En onCreate() hacemos petición GET al servidor
 * 3. Recibimos JSON con lista de usuarios
 * 4. Ordenamos por número de mensajes (mayor a menor)
 * 5. Creamos el Adapter y lo asignamos al ListView
 * 6. Se muestra la lista
 */
public class MainActivity extends AppCompatActivity {
    
    // URL del servidor (del enunciado)
    private static final String URL = "https://api.messenger.tatai.es/v2/exam/turn-1";
    
    // Widgets y objetos
    private ListView listView;
    private AsyncHttpClient httpClient;
    private Gson gson;
    
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
        listView = findViewById(R.id.list_users);
        
        // ====================================================================
        // PASO 2: Hacer petición GET al servidor
        // ====================================================================
        fetchUsers();
    }
    
    /*
     * ========================================================================
     * fetchUsers() - Petición GET al servidor
     * ========================================================================
     * 
     * DIFERENCIA ENTRE GET Y POST:
     * - GET: Solo obtener datos. No enviamos body.
     * - POST: Enviar datos. Necesita body (StringEntity).
     * 
     * En este examen es GET, así que NO necesitamos StringEntity.
     */
    private void fetchUsers() {
        
        // Petición GET asíncrona
        // Parámetros: URL, ResponseHandler
        httpClient.get(URL, new AsyncHttpResponseHandler() {
            
            /*
             * onSuccess() - Respuesta exitosa (código 200-299)
             * 
             * Parámetros:
             * - statusCode: código HTTP (200, 201, etc.)
             * - headers: cabeceras de la respuesta
             * - responseBody: cuerpo de la respuesta como bytes
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                
                // Convertir bytes a String
                String json = new String(responseBody);
                
                // Parsear JSON a objeto Java usando Gson
                // gson.fromJson(jsonString, ClaseDestino.class)
                StatsResponse response = gson.fromJson(json, StatsResponse.class);
                
                // Obtener la lista de usuarios
                List<User> users = response.getStats();
                
                // Ordenar y mostrar
                sortAndDisplayUsers(users);
            }
            
            /*
             * onFailure() - Error (código 400-599 o error de red)
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, 
                                 byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, 
                    R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    /*
     * ========================================================================
     * sortAndDisplayUsers() - Ordenar y mostrar la lista
     * ========================================================================
     * 
     * REQUISITO: Ordenar de MAYOR a MENOR número de mensajes.
     * 
     * Usamos Collections.sort() con un Comparator personalizado.
     */
    private void sortAndDisplayUsers(List<User> users) {
        
        // ====================================================================
        // ORDENAR LA LISTA
        // ====================================================================
        /*
         * Collections.sort() ordena una lista.
         * Necesita un Comparator que indique cómo comparar dos elementos.
         * 
         * El método compare(a, b) debe retornar:
         * - Negativo si a < b
         * - Cero si a == b
         * - Positivo si a > b
         * 
         * Para ordenar de MAYOR a MENOR:
         * - Retornamos b - a (inverso del normal)
         * - Si b tiene más mensajes que a, retorna positivo -> b va primero
         */
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                // MAYOR a MENOR: restamos u2 - u1 (al revés)
                return u2.getMessages() - u1.getMessages();
            }
        });
        
        // ====================================================================
        // CREAR ADAPTER Y ASIGNAR AL LISTVIEW
        // ====================================================================
        /*
         * Creamos el adapter pasando:
         * - this: el contexto (la Activity)
         * - users: la lista de datos
         */
        UserAdapter adapter = new UserAdapter(this, users);
        
        /*
         * Asignamos el adapter al ListView.
         * A partir de este momento, el ListView llamará a los métodos
         * del adapter (getCount, getView, etc.) para mostrar la lista.
         */
        listView.setAdapter(adapter);
    }
}
