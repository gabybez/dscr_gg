package es.unavarra.tlm.examenmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.greenrobot.greendao.database.Database;
import java.util.List;

public class ListasActivity extends AppCompatActivity {

    private ListView listView;
    private AccessDao accessDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        listView = findViewById(R.id.lv_Accesos);

        // 1. INICIALIZAR DAO (Igual que en Login/DBActivity)
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "access-db");
        Database db = helper.getWritableDb();
        accessDao = new DaoMaster(db).newSession().getAccessDao();

        // 2. CONSULTAR DATOS ORDENADOS DESCENDENTE
        List<Access> lista = accessDao.queryBuilder()
                .orderDesc(AccessDao.Properties.Created_at)
                .list();

        // 3. CONFIGURAR ADAPTADOR
        AccessAdapter adapter = new AccessAdapter(this, lista);
        listView.setAdapter(adapter);

        // 4. EVENTO CLICK EN LA FILA
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // El 'id' que recibimos es el que devolvió getItemId() en el adaptador
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("ACCESS_ID", id); // Pasamos solo el ID
            startActivity(intent);
        });
    }
}