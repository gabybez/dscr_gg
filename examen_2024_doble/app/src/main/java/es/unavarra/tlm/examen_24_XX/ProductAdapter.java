package es.unavarra.tlm.examen_24_XX;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/*
 * ============================================================================
 * ADAPTER: ProductAdapter.java - COMBINA DOS LISTAS
 * ============================================================================
 * 
 * Este Adapter es especial porque recibe DOS listas:
 * 1. Lista de productos (con componentIds)
 * 2. Lista de componentes (con id, name, price)
 * 
 * En getView(), para cada producto:
 * 1. Recorremos sus componentIds
 * 2. Buscamos cada componente en la lista de componentes
 * 3. Sumamos los precios
 * 4. Concatenamos los nombres
 * 
 * Es el MISMO patrón que el examen 2024 con products/materials.
 */
public class ProductAdapter extends BaseAdapter {
    
    private Context context;
    private List<Product> productsList;       // Lista de productos
    private List<Component> componentsList;   // Lista de componentes (para buscar)
    
    /*
     * ========================================================================
     * CONSTRUCTOR - Recibe AMBAS listas
     * ========================================================================
     */
    public ProductAdapter(Context context, List<Product> productsList, List<Component> componentsList) {
        this.context = context;
        this.productsList = productsList;
        this.componentsList = componentsList;
    }
    
    @Override
    public int getCount() {
        return productsList.size();
    }
    
    @Override
    public Product getItem(int position) {
        return productsList.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    /*
     * ========================================================================
     * getView() - AQUÍ SE COMBINAN LOS DATOS
     * ========================================================================
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        // Crear o reutilizar vista
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_product, parent, false);
        }
        
        // Obtener el producto de esta posición
        Product product = getItem(position);
        
        // Referencias a widgets
        LinearLayout rowLayout = convertView.findViewById(R.id.row_layout);
        TextView txtName = convertView.findViewById(R.id.txt_name);
        TextView txtComponents = convertView.findViewById(R.id.txt_components);
        TextView txtPrice = convertView.findViewById(R.id.txt_price);
        
        // ====================================================================
        // MOSTRAR NOMBRE DEL PRODUCTO
        // ====================================================================
        txtName.setText(product.getName());
        
        // ====================================================================
        // CALCULAR PRECIO TOTAL Y OBTENER NOMBRES DE COMPONENTES
        // ====================================================================
        /*
         * Para cada producto, necesitamos:
         * 1. Sumar los precios de todos sus componentes
         * 2. Obtener los nombres de los componentes para mostrarlos
         * 
         * ALGORITMO:
         * - Recorrer los componentIds del producto
         * - Para cada componentId, buscar en la lista de componentes
         * - Si lo encontramos, sumar su precio y guardar su nombre
         */
        float totalPrice = 0;
        StringBuilder componentNames = new StringBuilder();
        
        // Recorrer los IDs de componentes del producto
        for (int i = 0; i < product.getComponentIds().size(); i++) {
            
            String componentId = product.getComponentIds().get(i);
            
            // Buscar este componente en la lista de componentes
            for (int j = 0; j < componentsList.size(); j++) {
                
                Component component = componentsList.get(j);
                
                // ¿Es este el componente que buscamos?
                if (component.getId().equals(componentId)) {
                    
                    // Sumar el precio
                    totalPrice = totalPrice + component.getPrice();
                    
                    // Añadir el nombre (con coma si no es el primero)
                    if (componentNames.length() > 0) {
                        componentNames.append(", ");
                    }
                    componentNames.append(component.getName());
                    
                    // Ya lo encontramos, salir del bucle interno
                    break;
                }
            }
        }
        
        // ====================================================================
        // MOSTRAR LISTA DE COMPONENTES
        // ====================================================================
        txtComponents.setText(componentNames.toString());
        
        // ====================================================================
        // MOSTRAR PRECIO CON FORMATO €XXX.XX
        // ====================================================================
        /*
         * String.format("€%.2f", totalPrice)
         * - %.2f = número decimal con 2 decimales
         * - Ejemplo: 459.99 -> "€459.99"
         */
        String priceFormatted = String.format("€%.2f", totalPrice);
        txtPrice.setText(priceFormatted);
        
        // ====================================================================
        // CEBREADO
        // ====================================================================
        if (position % 2 == 0) {
            rowLayout.setBackgroundColor(Color.WHITE);
        } else {
            rowLayout.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }
        
        return convertView;
    }
}
