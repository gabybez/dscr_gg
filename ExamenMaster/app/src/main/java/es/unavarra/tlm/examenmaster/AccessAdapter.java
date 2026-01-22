package es.unavarra.tlm.examenmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AccessAdapter extends BaseAdapter {
    private Context context;
    private List<Access> accessList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public AccessAdapter(Context context, List<Access> accessList) {
        this.context = context;
        this.accessList = accessList;
    }

    @Override
    public int getCount() { return accessList.size(); } // Cuántos elementos hay

    @Override
    public Access getItem(int position) { return accessList.get(position); }

    @Override
    public long getItemId(int position) { return accessList.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. INFLAR EL LAYOUT si es nulo
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_access, parent, false);
        }

        // 2. OBTENER EL DATO ACTUAL
        Access currentAccess = getItem(position);

        // 3. VINCULAR WIDGETS
        TextView tvUser = convertView.findViewById(R.id.tv_RowUser);
        TextView tvDate = convertView.findViewById(R.id.tv_RowDate);
        ImageView imgStatus = convertView.findViewById(R.id.img_Status);

        // 4. ASIGNAR VALORES
        tvUser.setText(currentAccess.getUsername());
        tvDate.setText(dateFormat.format(currentAccess.getCreated_at()));

        // Icono según validez (Práctica 5)
        if (currentAccess.getValid()) {
            imgStatus.setImageResource(R.drawable.green_icon);
        } else {
            imgStatus.setImageResource(R.drawable.red_icon);
        }

        return convertView;
    }
}