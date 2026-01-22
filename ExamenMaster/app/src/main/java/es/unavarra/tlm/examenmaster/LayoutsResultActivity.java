package es.unavarra.tlm.examenmaster;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LayoutsResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts_result);

        TextView tvMensaje = findViewById(R.id.tv_MensajeRecibido);
        TextView tvTerminos = findViewById(R.id.tv_TerminosRecibidos);
        Button btnVolver = findViewById(R.id.btn_Volver);

        // 1. RECUPERAR EL PAQUETE DE DATOS (BUNDLE)
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            // 2. EXTRAER LOS DATOS USANDO LAS MISMAS CLAVES
            String msg = extras.getString("EXTRA_MSG");
            boolean chk = extras.getBoolean("EXTRA_CHK");

            // 3. MOSTRAR LOS DATOS (usando strings con formato si quieres nota)
            tvMensaje.setText(getString(R.string.lbl_mensaje_recibido) + " " + msg);
            tvTerminos.setText(getString(R.string.lbl_terminos) + " " + (chk ? "SÍ" : "NO"));
        }

        // 4. BOTÓN VOLVER (Uso de finish)
        btnVolver.setOnClickListener(v -> finish()); // Cierra esta pantalla y vuelve a la anterior
    }
}