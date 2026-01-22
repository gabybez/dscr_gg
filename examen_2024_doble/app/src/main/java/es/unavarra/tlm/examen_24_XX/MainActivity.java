package es.unavarra.tlm.examen_24_XX;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;

/*
 * ============================================================================
 * MainActivity - INICIA LA CADENA DE PETICIONES
 * ============================================================================
 * 
 * FLUJO COMPLETO DE PETICIONES ANIDADAS:
 * 
 * ┌─────────────────────────────────────────────────────────────────┐
 * │  1. MainActivity.onCreate()                                     │
 * │     └── client.get("/products", ProductsResponseHandler)        │
 * │                           ↓                                     │
 * │  2. ProductsResponseHandler.onSuccess()                         │
 * │     ├── Parsea lista de productos                               │
 * │     └── client.get("/components", ComponentsResponseHandler)    │
 * │                           ↓                                     │
 * │  3. ComponentsResponseHandler.onSuccess()                       │
 * │     ├── Parsea lista de componentes                             │
 * │     ├── Crea ProductAdapter(products, components)               │
 * │     └── listView.setAdapter(adapter)                            │
 * │                           ↓                                     │
 * │  4. ProductAdapter.getView()                                    │
 * │     ├── Por cada producto, busca sus componentes                │
 * │     ├── Suma precios                                            │
 * │     └── Concatena nombres                                       │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * Este es el MISMO patrón que el examen 2024 de products/materials.
 */
public class MainActivity extends AppCompatActivity {
    
    // URL de la primera petición
    private static final String PRODUCTS_URL = "https://api.electronics.example.com/v1/products";
    
    // Widgets
    private ListView listView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Obtener referencia al ListView
        listView = findViewById(R.id.list_products);
        
        // ====================================================================
        // Configurar click en los items
        // ====================================================================
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                // Obtener el producto del adapter
                ProductAdapter adapter = (ProductAdapter) parent.getAdapter();
                Product product = adapter.getItem(position);
                
                // Mostrar Toast con número de componentes
                // Formato: "El Smartphone X1 tiene 3 componentes"
                String message = getString(R.string.toast_components, 
                    product.getName(), 
                    product.getComponentIds().size());
                
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
        
        // ====================================================================
        // INICIAR LA CADENA DE PETICIONES
        // ====================================================================
        /*
         * Solo hacemos la PRIMERA petición aquí.
         * La segunda petición se hace en el onSuccess de ProductsResponseHandler.
         * 
         * Pasamos:
         * - this (Context): para que los handlers puedan crear adapters y Toasts
         * - listView: para que el último handler pueda asignar el adapter
         */
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(PRODUCTS_URL, new ProductsResponseHandler(this, listView));
    }
}
