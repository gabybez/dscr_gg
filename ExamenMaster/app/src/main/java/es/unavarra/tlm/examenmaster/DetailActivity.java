package es.unavarra.tlm.examenmaster;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.greenrobot.greendao.database.Database;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 1. RECUPERAR ID DEL INTENT
        long accessId = getIntent().getLongExtra("ACCESS_ID", -1);

        // 2. BUSCAR EN DB POR ESE ID
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "access-db");
        Database db = helper.getWritableDb();
        AccessDao accessDao = new DaoMaster(db).newSession().getAccessDao();
        Access access = accessDao.load(accessId);

        if (access != null) {
            // 3. MOSTRAR DATOS
            TextView tvInfo = findViewById(R.id.tv_DetailInfo);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            String texto = String.format("USUARIO: %s\nFECHA: %s\nVÁLIDO: %s",
                    access.getUsername(),
                    sdf.format(access.getCreated_at()),
                    access.getValid() ? "SÍ" : "NO");

            tvInfo.setText(texto);
        }
    }
}