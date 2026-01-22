package es.unavarra.tlm.dscr_25_06;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Pantalla de bienvenida
 * - Si ya hay token -> entra a Main directamente.
 * - Botón ENTRAR -> LoginActivity
 * - Botón REGISTRO -> RegisterActivity
 */
public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_welcome);

        // Autologin si ya estás identificado
        if (SessionManager.getToken(this) != null) {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            return;
        }

        Button btnEntrar   = findViewById(R.id.btn_Entrar);
        Button btnRegistro = findViewById(R.id.btn_Registro);

        btnEntrar.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));

        btnRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }
}
