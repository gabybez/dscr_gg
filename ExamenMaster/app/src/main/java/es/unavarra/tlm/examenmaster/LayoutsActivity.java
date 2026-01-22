package es.unavarra.tlm.examenmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LayoutsActivity extends AppCompatActivity {

    private EditText etMensaje;
    private CheckBox chkTerminos;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts); // Vincula el XML

        // 1. Inicialización de vistas
        etMensaje = findViewById(R.id.et_Mensaje);
        chkTerminos = findViewById(R.id.chk_Terminos);
        btnEnviar = findViewById(R.id.btn_EnviarParametros);

        // 2. Evento Click para enviar datos
        btnEnviar.setOnClickListener(v -> {
            // Extraemos los valores de los widgets
            String mensaje = etMensaje.getText().toString();
            boolean acepto = chkTerminos.isChecked();

            // 3. CREACIÓN DEL INTENT
            // De esta pantalla (this) a la de resultados
            Intent intent = new Intent(this, LayoutsResultActivity.class);

            // 4. PASO DE PARÁMETROS (EXTRAS)
            // Usamos una "clave" (String) y el "valor" (dato)
            intent.putExtra("EXTRA_MSG", mensaje);
            intent.putExtra("EXTRA_CHK", acepto);

            // 5. LANZAR ACTIVIDAD
            startActivity(intent);
        });
    }
}