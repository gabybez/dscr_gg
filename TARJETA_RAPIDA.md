# 🚀 TARJETA DE REFERENCIA RÁPIDA - COPIAR Y PEGAR

## 1. AndroidManifest.xml - PERMISO INTERNET
```xml
<uses-permission android:name="android.permission.INTERNET" />

<application
    android:usesCleartextTraffic="true"
```

## 2. build.gradle - DEPENDENCIAS
```groovy
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'com.loopj.android:android-async-http:1.4.11'
    implementation 'com.google.code.gson:gson:2.10.1'
}
```

## 3. IMPORTS EN ACTIVITY
```java
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.google.gson.Gson;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import java.io.UnsupportedEncodingException;
```

## 4. LAYOUT - LinearLayout VERTICAL
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/txt_description"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/description"/>

    <EditText
        android:id="@+id/edit_input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/hint"
        android:inputType="number"/>

    <Button
        android:id="@+id/btn_action"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/button_text"/>

    <TextView
        android:id="@+id/txt_error"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#FF0000"
        android:visibility="gone"/>

</LinearLayout>
```

## 5. LAYOUT - LinearLayout HORIZONTAL (botones en línea)
```xml
<LinearLayout
    android:id="@+id/layout_buttons"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:visibility="gone">

    <Button
        android:id="@+id/btn_1"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="1"/>

    <Button
        android:id="@+id/btn_2"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="2"/>

</LinearLayout>
```

## 6. MODELO REQUEST
```java
public class MyRequest {
    private int value;
    
    public MyRequest(int value) {
        this.value = value;
    }
    
    public int getValue() { return value; }
}
```

## 7. MODELO RESPONSE
```java
public class MyResponse {
    private int result;
    private InnerObject data;
    
    public int getResult() { return result; }
    public InnerObject getData() { return data; }
    
    public static class InnerObject {
        private String name;
        public String getName() { return name; }
    }
}
```

## 8. MODELO ERROR
```java
public class ErrorResponse {
    private int error;
    public int getError() { return error; }
}
```

## 9. ACTIVITY COMPLETA
```java
public class MainActivity extends AppCompatActivity {
    
    private static final String URL = "https://api.ejemplo.com/endpoint";
    
    private EditText editInput;
    private Button btnAction;
    private TextView txtError;
    
    private AsyncHttpClient client;
    private Gson gson;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        client = new AsyncHttpClient();
        gson = new Gson();
        
        editInput = (EditText) findViewById(R.id.edit_input);
        btnAction = (Button) findViewById(R.id.btn_action);
        txtError = (TextView) findViewById(R.id.txt_error);
        
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionClick();
            }
        });
    }
    
    private void onActionClick() {
        txtError.setVisibility(View.GONE);
        
        String inputText = editInput.getText().toString().trim();
        
        if (inputText.isEmpty()) {
            showError(getString(R.string.error_empty));
            return;
        }
        
        int value;
        try {
            value = Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
            showError(getString(R.string.error_not_number));
            return;
        }
        
        sendRequest(value);
    }
    
    private void sendRequest(int value) {
        MyRequest request = new MyRequest(value);
        String jsonBody = gson.toJson(request);
        
        try {
            StringEntity entity = new StringEntity(jsonBody);
            client.post(this, URL, entity, "application/json", 
                new MyResponseHandler());
        } catch (UnsupportedEncodingException e) {
            showError("Encoding error");
        }
    }
    
    private void showError(String message) {
        txtError.setText(message);
        txtError.setVisibility(View.VISIBLE);
    }
    
    private class MyResponseHandler extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] body) {
            String json = new String(body);
            MyResponse response = gson.fromJson(json, MyResponse.class);
            // Procesar respuesta exitosa
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, 
                             byte[] body, Throwable error) {
            if (body == null) {
                showError(getString(R.string.error_network));
                return;
            }
            String json = new String(body);
            ErrorResponse errorResp = gson.fromJson(json, ErrorResponse.class);
            
            switch (errorResp.getError()) {
                case 1:
                    showError(getString(R.string.error_1));
                    break;
                case 2:
                    showError(getString(R.string.error_2));
                    break;
                default:
                    showError("Error desconocido");
            }
        }
    }
}
```

## 10. PETICIÓN PUT (en vez de POST)
```java
client.put(this, URL, entity, "application/json", new MyResponseHandler());
```

## 11. MOSTRAR/OCULTAR ELEMENTOS
```java
view.setVisibility(View.VISIBLE);  // Mostrar
view.setVisibility(View.GONE);     // Ocultar (no ocupa espacio)
```

## 12. CAMBIAR TEXTO DE BOTÓN
```java
button.setText(String.valueOf(42));  // int a String
button.setText("Texto");
```

## 13. LISTENER PARA MÚLTIPLES BOTONES
```java
private class MultiButtonListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Button clickedBtn = (Button) v;
        String value = clickedBtn.getText().toString();
        int numValue = Integer.parseInt(value);
        // Hacer algo con numValue
    }
}

// Asignar a todos los botones
btn1.setOnClickListener(new MultiButtonListener());
btn2.setOnClickListener(new MultiButtonListener());
btn3.setOnClickListener(new MultiButtonListener());
```

## 14. STRING CON FORMATO
```xml
<!-- strings.xml -->
<string name="result">You have chosen %d</string>
```
```java
// Java
String msg = String.format(getString(R.string.result), 42);
// Resultado: "You have chosen 42"
```

## 15. SHAREDPREFERENCES
```java
// GUARDAR
SharedPreferences prefs = getSharedPreferences("Config", 0);
SharedPreferences.Editor editor = prefs.edit();
editor.putString("key", "value");
editor.putInt("number", 42);
editor.commit();  // ¡NO OLVIDAR!

// LEER
SharedPreferences prefs = getSharedPreferences("Config", 0);
String valor = prefs.getString("key", "default");
int numero = prefs.getInt("number", 0);
```

## 16. GREENDAO - ENTITY
```java
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    
    @NotNull
    private String name;
    
    private int age;
}
// Después: Build > Make Project
```

## 17. GREENDAO - CONEXIÓN
```java
DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mydb");
Database db = helper.getWritableDb();
DaoSession session = new DaoMaster(db).newSession();
UserDao userDao = session.getUserDao();
```

## 18. GREENDAO - CRUD
```java
// Insertar
User user = new User();
user.setName("Juan");
userDao.insert(user);

// Leer todos
List<User> users = userDao.loadAll();

// Leer con condición
List<User> adults = userDao.queryBuilder()
    .where(UserDao.Properties.Age.ge(18))
    .list();

// Actualizar
user.setName("Jose");
userDao.update(user);

// Borrar
userDao.delete(user);
```

## 19. ADAPTER PARA LISTA
```java
public class UserAdapter extends BaseAdapter {
    private List<User> users;
    private Context context;
    
    public UserAdapter(Context ctx, List<User> users) {
        this.context = ctx;
        this.users = users;
    }
    
    public int getCount() { return users.size(); }
    public User getItem(int pos) { return users.get(pos); }
    public long getItemId(int pos) { return pos; }
    
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row, null);
        }
        User user = getItem(pos);
        TextView txt = convertView.findViewById(R.id.txt_name);
        txt.setText(user.getName());
        return convertView;
    }
}
```

## 20. USAR ADAPTER
```java
ListView listView = findViewById(R.id.my_list);
List<User> users = userDao.loadAll();
UserAdapter adapter = new UserAdapter(this, users);
listView.setAdapter(adapter);
```
