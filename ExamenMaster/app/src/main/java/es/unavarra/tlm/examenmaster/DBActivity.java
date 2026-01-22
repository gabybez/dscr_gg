package es.unavarra.tlm.examenmaster;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.greenrobot.greendao.database.Database;
import java.util.Date;
import java.util.List;

public class DBActivity extends AppCompatActivity {

    private AccessDao accessDao; // Clase generada por GreenDAO
    private TextView tvResumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbactivity);

        tvResumen = findViewById(R.id.tv_ResumenDB);

        // 1. INICIALIZACIÓN DE LA DB
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "access-db");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();

        // Obtenemos el DAO (Data Access Object)
        accessDao = daoSession.getAccessDao();

        // 2. BOTÓN INSERTAR
        findViewById(R.id.btn_InsertarTest).setOnClickListener(v -> {
            insertarDatoDePrueba();
            actualizarInterfaz();
        });

        // 3. BOTÓN BORRAR
        findViewById(R.id.btn_BorrarDB).setOnClickListener(v -> {
            accessDao.deleteAll();
            actualizarInterfaz();
        });

        actualizarInterfaz();
    }

    private void insertarDatoDePrueba() {
        // Abstracción: tabla -> objeto
        Access nuevo = new Access();
        nuevo.setUsername("Prueba_" + System.currentTimeMillis());
        nuevo.setValid(Math.random() > 0.5); // Aleatorio true/false
        nuevo.setCreated_at(new Date());

        accessDao.insert(nuevo); // Guarda en la DB
    }

    private void actualizarInterfaz() {
        // CONSULTAS USANDO QueryBuilder
        // Contamos válidos
        long validos = accessDao.queryBuilder()
                .where(AccessDao.Properties.Valid.eq(true))
                .count();

        // Contamos inválidos
        long invalidos = accessDao.queryBuilder()
                .where(AccessDao.Properties.Valid.eq(false))
                .count();

        // Mostramos usando el string con formato
        String resumen = getString(R.string.lbl_conteo, (int)validos, (int)invalidos);
        tvResumen.setText(resumen);
    }
}