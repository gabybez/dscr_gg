package es.unavarra.tlm.examen_2023_claude;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity que muestra el detalle de un corredor.
 * Recibe los datos por Intent extras.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Obtener datos del Intent
        Bundle extras = getIntent().getExtras();

        int position = extras.getInt("position");
        int categoryPosition = extras.getInt("categoryPosition");
        String name = extras.getString("name");
        int dorsal = extras.getInt("dorsal");
        String time = extras.getString("time");

        // Obtener referencias a TextViews
        TextView txtPosition = findViewById(R.id.txt_detail_position);
        TextView txtCategory = findViewById(R.id.txt_detail_category);
        TextView txtName = findViewById(R.id.txt_detail_name);
        TextView txtDorsal = findViewById(R.id.txt_detail_dorsal);
        TextView txtTime = findViewById(R.id.txt_detail_time);

        // Mostrar datos
        txtPosition.setText(String.valueOf(position));
        txtCategory.setText(String.valueOf(categoryPosition));
        txtName.setText(name);
        txtDorsal.setText(String.valueOf(dorsal));
        txtTime.setText(time);
    }
}