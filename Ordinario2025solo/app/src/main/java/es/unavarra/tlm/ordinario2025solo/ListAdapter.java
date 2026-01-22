package es.unavarra.tlm.ordinario2025solo;

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
 * ADAPTER PERSONALIZADO PARA LISTVIEW
 * ============================================================================
 *
 * El Adapter es el "puente" entre los DATOS y la VISTA (ListView).
 * - Recibe una lista de objetos (datos)
 * - Por cada objeto, crea/configura una vista (fila)
 *
 * DEBE extender BaseAdapter e implementar 4 métodos obligatorios:
 * 1. getCount()    -> Cuántos elementos hay
 * 2. getItem()     -> Obtener elemento en posición X
 * 3. getItemId()   -> ID del elemento (normalmente la posición)
 * 4. getView()     -> Crear/configurar la vista de cada fila
 */
public class ListAdapter extends BaseAdapter {

    // Lista de datos que mostraremos
    private List<Game> games;

    // Contexto necesario para inflar layouts
    private Context context;

    /*
     * Constructor del Adapter.
     * Se llama una vez al crear el adapter.
     */
    public ListAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    /*
     * getCount() - ¿Cuántas filas tiene la lista?
     * ListView llama a este método para saber cuántas filas crear.
     */
    @Override
    public int getCount() {
        return games.size();
    }

    /*
     * getItem() - Obtener el objeto de datos en una posición.
     * Podemos cambiar el tipo de retorno de Object a RunnerResult.
     */
    @Override
    public Game getItem(int position) {
        return games.get(position);
    }

    /*
     * getItemId() - ID único del elemento.
     * Normalmente retornamos la posición si no tenemos ID específico.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     * ============================================================================
     * getView() - EL MÉTODO MÁS IMPORTANTE
     * ============================================================================
     *
     * Se llama para CADA FILA visible en pantalla.
     *
     * @param position    - Índice del elemento (0, 1, 2...)
     * @param convertView - Vista reciclada (puede ser null la primera vez)
     * @param parent      - El ListView padre
     * @return            - La vista configurada para esta fila
     *
     * OPTIMIZACIÓN: ListView recicla vistas. Si convertView no es null,
     * podemos reutilizarla en vez de crear una nueva (más eficiente).
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // ====================================================================
        // PASO 1: Crear o reutilizar la vista
        // ====================================================================
        if (convertView == null) {
            // Primera vez: hay que "inflar" el layout XML
            // LayoutInflater convierte XML -> objeto View
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_games, parent, false);
        }

        // ====================================================================
        // PASO 2: Obtener el objeto de datos para esta posición
        // ====================================================================
        Game game = getItem(position);

        // ====================================================================
        // PASO 3: Obtener referencias a los widgets de la fila
        // ====================================================================
        // Usamos convertView.findViewById() porque buscamos DENTRO de la fila

        LinearLayout rowLayout = convertView.findViewById(R.id.rowLayout);
        TextView txtAwayScore = convertView.findViewById(R.id.txt_away_score);
        TextView txtAwayName = convertView.findViewById(R.id.txt_away_name);

        TextView txtHomeName = convertView.findViewById(R.id.txt_home_name);
        TextView txtHomeScore = convertView.findViewById(R.id.txt_home_score);

        // ====================================================================
        // PASO 4: Configurar los valores de cada widget
        // ====================================================================

        // AwayScore
        txtAwayScore.setText(String.valueOf(game.getAway().getScore()));

        // AwayName
        txtAwayName.setText(String.valueOf(game.getAway().getName()));

        // HomeName
        txtHomeName.setText(String.valueOf(game.getHome().getName()));

        txtHomeScore.setText(String.valueOf(game.getHome().getScore()));

        //Cebreado

        if (position % 2 == 0) {
            // Posiciones pares (0, 2, 4...) -> Blanco
            rowLayout.setBackgroundColor(Color.WHITE);
        } else {
            // Posiciones impares (1, 3, 5...) -> Gris
            rowLayout.setBackgroundColor(Color.LTGRAY);
        }


        // ====================================================================
        // PASO 5: Retornar la vista configurada
        // ====================================================================
        return convertView;
    }
}