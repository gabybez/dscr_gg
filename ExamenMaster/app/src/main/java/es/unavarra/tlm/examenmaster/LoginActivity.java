package es.unavarra.tlm.examenmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View; // Necesario para los listeners
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// NUEVOS IMPORTS PARA LA PRÁCTICA 4 (Bases de Datos)
import org.greenrobot.greendao.database.Database;
import java.util.Date;

/**
 * TEMA 3.2: Una Activity representa una pantalla
 */
public class LoginActivity extends AppCompatActivity {

    // 1. DECLARACIÓN DE VARIABLES (Widgets)
    // Declaramos los elementos que vamos a manipular desde Java
    private EditText etUsuario, etPassword;
    private Button btnEntrar;

    // VARIABLE PARA EL DAO (Práctica 4 - GreenDAO)
    private AccessDao accessDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 2. VINCULAR LAYOUT
        // "Inflamos" la interfaz XML en esta clase Java
        setContentView(R.layout.activity_login);

        // 3. INICIALIZACIÓN DE VISTAS
        // Buscamos los elementos por su ID definido en el XML
        etUsuario = findViewById(R.id.et_Usuario);
        etPassword = findViewById(R.id.et_Password);
        btnEntrar = findViewById(R.id.btn_Entrar);

        // --- INICIALIZACIÓN DE LA BASE DE DATOS (Tema 3.4) ---
        // Preparamos la conexión según los apuntes (Pág. 10)
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "access-db");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        accessDao = daoSession.getAccessDao();
        // -----------------------------------------------------

        // 4. GESTIÓN DE EVENTOS (Listener)
        // Programamos qué pasa cuando el usuario pulsa el botón
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos el texto de los inputs y los pasamos a String
                String user = etUsuario.getText().toString();
                String pass = etPassword.getText().toString();

                // 5. VALIDACIÓN SIMPLE
                if (user.isEmpty() || pass.isEmpty()) {
                    // USO DE RECURSOS: getString() para no escribir texto a piñón
                    Toast.makeText(LoginActivity.this,
                            getString(R.string.err_campos_vacios), Toast.LENGTH_SHORT).show();
                } else {
                    ejecutarLogin(user, pass);
                }
            }
        });
    }

    private void ejecutarLogin(String user, String pass) {

        // --- LÓGICA DE LA PRÁCTICA 4 (Registro en DB) ---
        // Según la práctica, el acceso es válido si la contraseña es "dscr"
        boolean esValido = pass.equals("dscr");

        // Creamos el objeto entidad para guardar en la tabla
        Access intento = new Access();
        intento.setUsername(user);
        intento.setValid(esValido);
        intento.setCreated_at(new Date()); // Fecha y hora actual

        // Insertamos el registro en la base de datos
        accessDao.insert(intento);
        // -------------------------------------------------

        if (esValido) {

            // 6. PERSISTENCIA DE SESIÓN
            // Usamos nuestra clase SessionManager (que usa SharedPreferences)
            // para guardar que el usuario está "logueado"
            SessionManager.setToken(this, "TOKEN_DE_PRUEBA_123");

            Toast.makeText(this, getString(R.string.msg_bienvenida), Toast.LENGTH_SHORT).show();

            // 7. NAVEGACIÓN (Intent)
            // El Intent permite pasar de la pantalla de Login a la principal
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            // 8. CERRAR ACTIVITY ACTUAL
            // Usamos finish() para que si el usuario pulsa "atrás" en el móvil,
            // no vuelva al login estando ya dentro de la app.
            finish();

        } else {
            // Mantenemos el aviso de error usando recursos
            Toast.makeText(this, getString(R.string.err_login_incorrecto), Toast.LENGTH_SHORT).show();
        }
    }
}