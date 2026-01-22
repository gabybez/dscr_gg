package es.unavarra.tlm.examen_24_XX;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/*
 * ============================================================================
 * HANDLER: ComponentsResponseHandler.java - SEGUNDA PETICIÓN (ANIDADA)
 * ============================================================================
 * 
 * Este handler se ejecuta cuando llega la respuesta de /components.
 * 
 * IMPORTANTE: Este handler recibe TRES cosas en el constructor:
 * 1. context: para crear el adapter
 * 2. listView: para asignarle el adapter
 * 3. productsList: la lista de productos de la PRIMERA petición
 * 
 * FLUJO:
 * 1. onSuccess() recibe la lista de componentes
 * 2. Ya tenemos las DOS listas (productos + componentes)
 * 3. Creamos el adapter pasándole AMBAS listas
 * 4. Asignamos el adapter al ListView
 * 
 * Este es el MISMO patrón que el examen 2024:
 * - MaterialsResponseHandler recibía ProductsList
 * - Aquí recibimos productsList
 */
public class ComponentsResponseHandler extends AsyncHttpResponseHandler {
    
    private Context context;
    private ListView listView;
    private List<Product> productsList;  // De la PRIMERA petición
    
    /*
     * Constructor: recibe todo lo necesario para crear el adapter
     */
    public ComponentsResponseHandler(Context context, ListView listView, List<Product> productsList) {
        this.context = context;
        this.listView = listView;
        this.productsList = productsList;
    }
    
    /*
     * ========================================================================
     * onSuccess() - Respuesta exitosa de /components
     * ========================================================================
     * 
     * Aquí tenemos TODO lo necesario:
     * - productsList (de la primera petición)
     * - componentsList (de esta petición)
     * 
     * Podemos crear el adapter y mostrarlo.
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        
        // Parsear JSON a objeto Java
        String json = new String(responseBody);
        Gson gson = new Gson();
        ComponentsResponse response = gson.fromJson(json, ComponentsResponse.class);
        
        // Obtener la lista de componentes
        List<Component> componentsList = response.getComponents();
        
        // ====================================================================
        // CREAR ADAPTER CON AMBAS LISTAS
        // ====================================================================
        /*
         * El adapter recibe:
         * - context: para inflar layouts
         * - productsList: de la PRIMERA petición
         * - componentsList: de esta petición
         * 
         * El adapter se encarga de combinar los datos en getView()
         */
        ProductAdapter adapter = new ProductAdapter(context, productsList, componentsList);
        
        // Asignar el adapter al ListView
        listView.setAdapter(adapter);
    }
    
    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Toast.makeText(context, R.string.error_network, Toast.LENGTH_SHORT).show();
    }
}
