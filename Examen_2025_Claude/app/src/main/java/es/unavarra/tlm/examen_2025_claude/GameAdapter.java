package es.unavarra.tlm.examen_2025_claude;

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
 * ADAPTER: GameAdapter.java
 * ============================================================================
 *
 * El Adapter es el "puente" entre los DATOS (List<Game>) y la VISTA (ListView).
 *
 * Por cada partido en la lista, el Adapter crea una fila visual.
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
public class GameAdapter extends BaseAdapter {

    // Lista de partidos (los datos)
    private List<Game> games;

    // Contexto (la Activity) - necesario para inflar layouts
    private Context context;

    /*
     * ========================================================================
     * CONSTRUCTOR
     * ========================================================================
     * Se llama UNA VEZ cuando creamos el adapter.
     *
     * @param context - La Activity (this)
     * @param games   - Lista de partidos a mostrar
     */
    public GameAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    /*
     * ========================================================================
     * getCount() - ¿Cuántas filas tiene la lista?
     * ========================================================================
     * El ListView llama a este método para saber cuántas filas crear.
     * Retornamos el tamaño de nuestra lista de partidos.
     */
    @Override
    public int getCount() {
        return games.size();
    }

    /*
     * ========================================================================
     * getItem() - Obtener el partido en una posición
     * ========================================================================
     * Retorna el Game en la posición indicada.
     * Muy útil cuando hacemos click en una fila para saber qué partido es.
     *
     * NOTA: Podemos cambiar el tipo de retorno de Object a Game.
     */
    @Override
    public Game getItem(int position) {
        return games.get(position);
    }

    /*
     * ========================================================================
     * getItemId() - ID único del elemento
     * ========================================================================
     * Si nuestros objetos tuvieran un ID numérico, lo retornaríamos.
     * Como Game tiene UUID (String), retornamos la posición.
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
     *
     * Aquí es donde:
     * 1. Creamos (o reutilizamos) la vista de la fila
     * 2. Obtenemos el partido de esta posición
     * 3. Configuramos los widgets con los datos del partido
     * 4. Aplicamos el color de fondo (cebreado)
     *
     * PARÁMETROS:
     * @param position    - Índice del elemento (0, 1, 2, 3...)
     * @param convertView - Vista reciclada (puede ser null la primera vez)
     * @param parent      - El ListView padre
     * @return            - La vista configurada para esta fila
     *
     * OPTIMIZACIÓN (reciclaje de vistas):
     * ListView recicla vistas para ahorrar memoria.
     * Si convertView != null, podemos reutilizarla en vez de crear una nueva.
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
         * Si convertView NO es null, es una vista reciclada que reutilizamos.
         */
        if (convertView == null) {
            // LayoutInflater convierte XML -> View
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflamos row_game.xml
            // Parámetros: (layout, parent, attachToRoot=false)
            convertView = inflater.inflate(R.layout.row_game, parent, false);
        }

        // ====================================================================
        // PASO 2: Obtener el partido de esta posición
        // ====================================================================
        Game game = getItem(position);

        // ====================================================================
        // PASO 3: Obtener referencias a los widgets DENTRO de la fila
        // ====================================================================
        /*
         * IMPORTANTE: Usamos convertView.findViewById()
         * porque buscamos DENTRO de la fila, no en toda la Activity.
         */
        LinearLayout rowLayout = convertView.findViewById(R.id.row_layout);
        TextView txtAwayScore = convertView.findViewById(R.id.txt_away_score);
        TextView txtAwayName = convertView.findViewById(R.id.txt_away_name);
        TextView txtHomeName = convertView.findViewById(R.id.txt_home_name);
        TextView txtHomeScore = convertView.findViewById(R.id.txt_home_score);

        // ====================================================================
        // PASO 4: Configurar los valores de cada widget
        // ====================================================================

        // Equipo visitante (away)
        txtAwayScore.setText(String.valueOf(game.getAway().getScore()));
        txtAwayName.setText(game.getAway().getName());

        // Equipo local (home)
        txtHomeName.setText(game.getHome().getName());
        txtHomeScore.setText(String.valueOf(game.getHome().getScore()));

        // ====================================================================
        // PASO 5: CEBREADO - Colores alternos según posición
        // ====================================================================
        /*
         * REQUISITO DEL EXAMEN: "cebreado" = colores alternos
         *
         * Usamos el operador módulo (%) para saber si es par o impar:
         * - position % 2 == 0 -> par (posiciones 0, 2, 4...) -> BLANCO
         * - position % 2 != 0 -> impar (posiciones 1, 3, 5...) -> GRIS
         *
         * Color.WHITE = Blanco (#FFFFFF)
         * Color.LTGRAY = Gris claro (#CCCCCC)
         *
         * También podrías usar Color.parseColor("#F0F0F0") para un gris personalizado
         */
        if (position % 2 == 0) {
            // Posiciones pares (0, 2, 4...) -> Blanco
            rowLayout.setBackgroundColor(Color.WHITE);
        } else {
            // Posiciones impares (1, 3, 5...) -> Gris
            rowLayout.setBackgroundColor(Color.LTGRAY);
        }

        // ====================================================================
        // PASO 6: Retornar la vista configurada
        // ====================================================================
        return convertView;
    }
}
