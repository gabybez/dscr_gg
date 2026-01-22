package es.unavarra.tlm.examen_17_XX;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/*
 * ============================================================================
 * ADAPTER PERSONALIZADO - UserAdapter
 * ============================================================================
 * 
 * ¿QUÉ ES UN ADAPTER?
 * Es el "puente" entre los DATOS (List<User>) y la VISTA (ListView).
 * Por cada elemento de la lista, el Adapter crea una fila visual.
 * 
 * ¿POR QUÉ EXTENDER BaseAdapter?
 * Porque queremos personalizar completamente cómo se ve cada fila.
 * Otros adapters (ArrayAdapter, SimpleAdapter) son más limitados.
 * 
 * MÉTODOS OBLIGATORIOS (4):
 * 1. getCount()   - ¿Cuántos elementos hay?
 * 2. getItem()    - Dame el elemento en posición X
 * 3. getItemId()  - Dame el ID del elemento en posición X
 * 4. getView()    - Crea/configura la vista para la posición X
 */
public class UserAdapter extends BaseAdapter {
    
    // Lista de datos
    private List<User> users;
    
    // Contexto (necesario para inflar layouts)
    private Context context;
    
    /*
     * ========================================================================
     * CONSTRUCTOR
     * ========================================================================
     * Se llama una vez cuando creamos el Adapter.
     * Guardamos el contexto y la lista de datos.
     */
    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }
    
    /*
     * ========================================================================
     * getCount() - ¿Cuántas filas tiene la lista?
     * ========================================================================
     * El ListView llama a este método para saber cuántas filas crear.
     * Simplemente retornamos el tamaño de nuestra lista.
     */
    @Override
    public int getCount() {
        return users.size();
    }
    
    /*
     * ========================================================================
     * getItem() - Obtener el objeto en una posición
     * ========================================================================
     * Retorna el User en la posición indicada.
     * Útil cuando hacemos click en una fila y queremos saber qué objeto es.
     */
    @Override
    public User getItem(int position) {
        return users.get(position);
    }
    
    /*
     * ========================================================================
     * getItemId() - ID único del elemento
     * ========================================================================
     * Si nuestros objetos tuvieran un ID de base de datos, lo retornaríamos.
     * Como no lo tienen, retornamos la posición como ID.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    /*
     * ========================================================================
     * getView() - EL MÉTODO MÁS IMPORTANTE
     * ========================================================================
     * 
     * Se llama para CADA FILA visible en pantalla.
     * Aquí es donde:
     * 1. Creamos (o reutilizamos) la vista de la fila
     * 2. Obtenemos el objeto de datos
     * 3. Configuramos los widgets con los datos
     * 
     * PARÁMETROS:
     * - position: Índice del elemento (0, 1, 2, 3...)
     * - convertView: Vista reciclada (puede ser null)
     * - parent: El ListView padre
     * 
     * OPTIMIZACIÓN:
     * ListView recicla vistas para ahorrar memoria.
     * Si convertView != null, podemos reutilizarla.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        // ====================================================================
        // PASO 1: Crear o reutilizar la vista
        // ====================================================================
        /*
         * Si convertView es null, es la primera vez que se crea esta fila.
         * Debemos "inflar" el layout XML para convertirlo en un objeto View.
         * 
         * Si convertView no es null, es una vista reciclada que podemos reutilizar.
         */
        if (convertView == null) {
            // LayoutInflater convierte XML -> View
            LayoutInflater inflater = LayoutInflater.from(context);
            
            // Inflamos nuestro layout de fila (row_user.xml)
            // parent: el ListView padre
            // false: no adjuntar automáticamente al padre
            convertView = inflater.inflate(R.layout.row_user, parent, false);
        }
        
        // ====================================================================
        // PASO 2: Obtener el objeto de datos para esta posición
        // ====================================================================
        User user = getItem(position);
        
        // ====================================================================
        // PASO 3: Obtener referencias a los widgets DENTRO de la fila
        // ====================================================================
        /*
         * IMPORTANTE: Usamos convertView.findViewById()
         * porque buscamos DENTRO de la fila, no en toda la Activity.
         */
        LinearLayout rowLayout = convertView.findViewById(R.id.row_layout);
        TextView txtName = convertView.findViewById(R.id.txt_name);
        ImageView imgPremium = convertView.findViewById(R.id.img_premium);
        TextView txtMessages = convertView.findViewById(R.id.txt_messages);
        
        // ====================================================================
        // PASO 4: Configurar los valores
        // ====================================================================
        
        // Nombre del usuario
        txtName.setText(user.getName());
        
        // Número de mensajes (convertir int a String)
        txtMessages.setText(String.valueOf(user.getMessages()));
        
        // ====================================================================
        // PASO 5: Mostrar/ocultar icono premium
        // ====================================================================
        /*
         * Si el usuario es premium, mostramos el icono.
         * Si no es premium, lo ocultamos.
         * 
         * View.VISIBLE = se ve
         * View.GONE = no se ve y NO ocupa espacio
         * View.INVISIBLE = no se ve pero SÍ ocupa espacio
         */
        if (user.isPremium()) {
            imgPremium.setVisibility(View.VISIBLE);
        } else {
            imgPremium.setVisibility(View.GONE);
        }
        
        // ====================================================================
        // PASO 6: Alternar color de fondo (blanco/gris)
        // ====================================================================
        /*
         * REQUISITO DEL EXAMEN:
         * - Filas IMPARES (posición 0, 2, 4...) -> fondo BLANCO
         * - Filas PARES (posición 1, 3, 5...) -> fondo GRIS
         * 
         * Usamos el operador módulo (%) para saber si es par o impar:
         * - position % 2 == 0 -> par (índice 0, 2, 4...)
         * - position % 2 != 0 -> impar (índice 1, 3, 5...)
         * 
         * NOTA: El enunciado dice "filas impares blanco, pares gris"
         * pero en programación empezamos desde 0, así que:
         * - Posición 0 (primera fila, "impar" visualmente) -> blanco
         * - Posición 1 (segunda fila, "par" visualmente) -> gris
         */
        if (position % 2 == 0) {
            // Posiciones 0, 2, 4... -> Blanco
            rowLayout.setBackgroundColor(Color.WHITE);
        } else {
            // Posiciones 1, 3, 5... -> Gris claro
            rowLayout.setBackgroundColor(Color.LTGRAY);
        }
        
        // ====================================================================
        // PASO 7: Retornar la vista configurada
        // ====================================================================
        return convertView;
    }
}
