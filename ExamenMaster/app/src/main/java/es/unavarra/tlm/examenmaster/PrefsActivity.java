package es.unavarra.tlm.examenmaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PrefsActivity extends AppCompatActivity {

    private TextView tvNombreActual;
    private EditText etNuevoNombre;
    private Button btnGuardar;

    // Nombre del archivo XML donde se guardan los datos
    private final String PREFS_FILE = "ExamenPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        tvNombreActual = findViewById(R.id.tv_NombreActual);
        etNuevoNombre = findViewById(R.id.et_NuevoNombre);
        btnGuardar = findViewById(R.id.btn_GuardarPrefs);

        // 1. CARGAR DATOS AL INICIAR
        cargarPreferencias();

        // 2. EVENTO PARA GUARDAR
        btnGuardar.setOnClickListener(v -> {
            String nuevoNombre = etNuevoNombre.getText().toString();
            if (!nuevoNombre.isEmpty()) {
                guardarPreferencia(nuevoNombre);
                cargarPreferencias(); // Refrescamos el TextView
            }
        });
    }

    /**
     * TEMA 3.3: Cómo recuperar datos de SharedPreferences
     */
    private void cargarPreferencias() {
        // Obtenemos el objeto SharedPreferences (Archivo, Modo)
        // Modo 0 es Context.MODE_PRIVATE
        SharedPreferences settings = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        // Leemos el valor (Clave, Valor por defecto si no existe)
        String nombre = settings.getString("usuario_nombre", "Sin nombre");

        tvNombreActual.setText(getString(R.string.lbl_username_actual) + " " + nombre);
    }

    /**
     * TEMA 3.3: Cómo almacenar datos en SharedPreferences
     */
    private void guardarPreferencia(String nombre) {
        // 1. Obtener SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        // 2. Crear un Editor para poder escribir
        SharedPreferences.Editor editor = settings.edit();

        // 3. Meter los pares Clave-Valor
        editor.putString("usuario_nombre", nombre);

        // 4. ¡IMPORTANTÍSIMO! Guardar los cambios con commit()
        editor.commit();

        Toast.makeText(this, getString(R.string.msg_prefs_guardadas), Toast.LENGTH_SHORT).show();
    }
}