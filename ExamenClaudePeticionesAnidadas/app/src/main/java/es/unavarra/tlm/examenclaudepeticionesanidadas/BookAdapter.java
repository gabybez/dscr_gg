package es.unavarra.tlm.examenclaudepeticionesanidadas;

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
 * ADAPTER: BookAdapter.java
 * ============================================================================
 *
 * El Adapter es el "puente" entre los DATOS (List<Book>) y la VISTA (ListView).
 *
 * Por cada libro en la lista, el Adapter crea una fila visual.
 *
 * DEBE extender BaseAdapter e implementar 4 MÉTODOS OBLIGATORIOS:
 * 1. getCount()    - ¿Cuántos elementos hay en la lista?
 * 2. getItem()     - Dame el elemento en la posición X
 * 3. getItemId()   - Dame el ID del elemento en la posición X
 * 4. getView()     - Crea/configura la vista para la posición X (EL MÁS IMPORTANTE)
 *
 * CEBREADO (colores alternos):
 * - Filas en posición par (0, 2, 4...): color blanco
 * - Filas en posición impar (1, 3, 5...): color gris
 */

public class BookAdapter extends BaseAdapter {

    //Lista de libros (los datos)
    private List<Book> books;
    //Contexto de la Activity (necesario para inflar los layouts)
    private Context context;
    //Constructor (lo llamamos una vez al crear el Adapter)
    public BookAdapter(Context context, List<Book> books){
        this.context = context;
        this.books = books;
    }
    //getCount() - Para saber cuantas filas tiene la lista
    @Override
    public int getCount(){
        return books.size();
    }
    //getItem()- Para obtener la mesa de una posicion del listado
    @Override
    public Book getItem(int position){
        return books.get(position);
    }
    //getItemId()- ID unico del elemento (usamos la posicion aunque siendo numerico... revisar)
    @Override
    public long getItemId(int position){
        return position;
    }
    //getView()- Es el más importante pues construye la vista
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Paso 1 Crear o reutilizar la vista
        if (convertView == null) {
            // LayoutInflater convierte XML -> View
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflamos row_table.xml
            // Parámetros: (layout, parent, attachToRoot=false)
            convertView = inflater.inflate(R.layout.row_book, parent, false);
        }
        //Paso 2 Obtener la mesa de esta posicion
        Book book = getItem(position);
        //Paso 3 Obtener referencias de los widget de la fila
        LinearLayout rowLayout = convertView.findViewById(R.id.row_layout);
        TextView txtBookName = convertView.findViewById(R.id.txt_book_name);
        ImageView imgStatus = convertView.findViewById(R.id.img_status);
        TextView txtStatus = convertView.findViewById(R.id.txt_status);

        //Paso 4 Configurar los valores de cada Widget
        txtBookName.setText(String.valueOf(book.getTitle()));
        //Paso 5 Parte de la imagen (si esta ocupado pondrá untexto y una imagen distintos a si está disponible)

        if(book.getAvailable()){ //si esta disponible
            imgStatus.setImageResource(R.drawable.green_icon);
            txtStatus.setText(R.string.status_available);
        }else{
            imgStatus.setImageResource(R.drawable.red_icon);
            txtStatus.setText(R.string.status_no_available);
        }
        //Paso 6 Parte del cebreado
        if (position % 2 == 0) {
            // Posiciones pares (0, 2, 4...) -> Blanco
            rowLayout.setBackgroundColor(Color.WHITE);
        } else {
            // Posiciones impares (1, 3, 5...) -> Gris
            rowLayout.setBackgroundColor(Color.LTGRAY);
        }
        //Paso 7 Retornar la vista
        return convertView;

    }


}
