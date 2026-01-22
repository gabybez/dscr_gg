package es.unavarra.tlm.examen2017xx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
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
public class StatsAdapter extends BaseAdapter {

    // Lista de datos que mostraremos
    private List<StatsResult> stats;

    // Contexto necesario para inflar layouts
    private Context context;

    /**
     * Constructor del Adapter.
     * Se llama una vez al crear el adapter.
     */
    public StatsAdapter(Context context, List<StatsResult> stats) {
        this.context = context;
        this.stats = stats;
    }

    /**
     * getCount() - ¿Cuántas filas tiene la lista?
     * ListView llama a este método para saber cuántas filas crear.
     */
    @Override
    public int getCount() {
        return stats.size();
    }

    /**
     * getItem() - Obtener el objeto de datos en una posición.
     * Podemos cambiar el tipo de retorno de Object a RunnerResult.
     */
    @Override
    public StatsResult getItem(int position) {
        return stats.get(position);
    }

    /**
     * getItemId() - ID único del elemento.
     * Normalmente retornamos la posición si no tenemos ID específico.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
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
            convertView = inflater.inflate(R.layout.row_stats, parent, false);
        }

        // ====================================================================
        // PASO 2: Obtener el objeto de datos para esta posición
        // ====================================================================
        StatsResult stats = getItem(position);

        // ====================================================================
        // PASO 3: Obtener referencias a los widgets de la fila
        // ====================================================================
        // Usamos convertView.findViewById() porque buscamos DENTRO de la fila
        
        LinearLayout rowLayout = convertView.findViewById(R.id.row_layout);
        TextView txtName = convertView.findViewById(R.id.txt_name);
        ImageView imgPremium = convertView.findViewById(R.id.img_premium);
        TextView txtMessages = convertView.findViewById(R.id.txt_messages);


        // ====================================================================
        // PASO 4: Configurar los valores de cada widget
        // ====================================================================

        // Puesto general
        txtPosition.setText(String.valueOf(runner.getPosition()));

        // Puesto en categoría
        txtCategoryPosition.setText(String.valueOf(runner.getCategoryPosition()));

        // Nombre completo (nombre + apellido)
        txtName.setText(runner.getRunner().getFullName());

        // Dorsal
        txtDorsal.setText(String.valueOf(runner.getNumber()));

        // Tiempo formateado (ej: "1 m 49 s")
        txtTime.setText(runner.getFormattedTime());

        // ====================================================================
        // PASO 5: Retornar la vista configurada
        // ====================================================================
        return convertView;
    }
}