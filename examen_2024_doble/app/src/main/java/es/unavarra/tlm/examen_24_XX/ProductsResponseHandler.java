package es.unavarra.tlm.examen_24_XX;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/*
 * ============================================================================
 * HANDLER: ProductsResponseHandler.java - PRIMERA PETICIÓN
 * ============================================================================
 * 
 * Este handler se ejecuta cuando llega la respuesta de /products.
 * 
 * FLUJO:
 * 1. MainActivity hace GET /products
 * 2. onSuccess() de este handler recibe la lista de productos
 * 3. DENTRO de onSuccess(), hacemos la SEGUNDA petición GET /components
 * 4. Pasamos la lista de productos al siguiente handler
 * 
 * PATRÓN DE PETICIONES ANIDADAS:
 * - Este handler guarda el Context y ListView para pasarlos
 * - En onSuccess(), lanza la segunda petición
 * - La segunda petición recibe también la lista de productos
 */
public class ProductsResponseHandler extends AsyncHttpResponseHandler {
    
    // Guardamos el contexto y ListView para pasarlos al siguiente handler
    private Context context;
    private ListView listView;
    
    // URL de la segunda petición
    private static final String COMPONENTS_URL = "https://api.electronics.example.com/v1/components";
    
    /*
     * Constructor: recibe el contexto y el ListView desde MainActivity
     */
    public ProductsResponseHandler(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }
    
    /*
     * ========================================================================
     * onSuccess() - Respuesta exitosa de /products
     * ========================================================================
     * 
     * 1. Parseamos la lista de productos
     * 2. Lanzamos la segunda petición (componentes)
     * 3. Pasamos la lista de productos al segundo handler
     */
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        
        // Parsear JSON a objeto Java
        String json = new String(responseBody);
        Gson gson = new Gson();
        ProductsResponse response = gson.fromJson(json, ProductsResponse.class);
        
        // Obtener la lista de productos
        List<Product> productsList = response.getProducts();
        
        // ====================================================================
        // PETICIÓN ANIDADA: Ahora pedimos los componentes
        // ====================================================================
        /*
         * Creamos un nuevo AsyncHttpClient para la segunda petición.
         * Pasamos:
         * - context: para inflar layouts y mostrar Toasts
         * - listView: para asignar el adapter
         * - productsList: la lista que acabamos de recibir
         */
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(COMPONENTS_URL, new ComponentsResponseHandler(context, listView, productsList));
    }
    
    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Toast.makeText(context, R.string.error_network, Toast.LENGTH_SHORT).show();
    }
}
