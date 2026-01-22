# CHULETA COMPLETA DE ANDROID - EXAMEN DSCR

## 📚 ÍNDICE
1. [3.1 Android: Introducción](#31-android-introducción)
2. [3.2 Android: Activities, Layouts & Resources](#32-android-activities-layouts--resources)
3. [3.3 Android: Gradle & SharedPreferences](#33-android-gradle--sharedpreferences)
4. [3.4 Android: Bases de Datos & ORMs (GreenDAO)](#34-android-bases-de-datos--orms-greendao)
5. [3.5 Android: Lists & Adapters](#35-android-lists--adapters)
6. [Anexo 1: Peticiones REST](#anexo-1-peticiones-rest)

---

## 3.1 Android: Introducción

### Historia Rápida
- **2003**: Android Inc. fundada (Andy Rubin, Rich Miner, Nick Sears, Chris White)
- **2005**: Comprada por Google
- **2008**: Lanzamiento HTC Dream (G1)
- **2017**: Kotlin anunciado como lenguaje oficial

### Lenguajes de Programación
- **Java** (original)
- **Kotlin** (oficial desde 2017)
- C/C++ (opcional, para código nativo)

### Arquitectura de Android (de arriba a abajo)
```
┌─────────────────────────────────────────┐
│           System Apps                    │ <- Dialer, Email, Calendar...
├─────────────────────────────────────────┤
│         Java API Framework               │ <- Content Providers, Managers
├─────────────────────────────────────────┤
│  Native C/C++ Libraries │ Android Runtime│ <- ART, Core Libraries
├─────────────────────────────────────────┤
│    Hardware Abstraction Layer (HAL)      │ <- Audio, Bluetooth, Camera
├─────────────────────────────────────────┤
│            Linux Kernel                  │ <- Drivers, Power Management
└─────────────────────────────────────────┘
```

### Dalvik Virtual Machine (DVM)
- Cada app se ejecuta en **su propio proceso**
- Compila bytecode de Java en archivos **.dex**
- Es una máquina virtual optimizada para móviles

### Compilación
```
Código fuente → Compilación → .apk → Firmado → Dispositivo
                    ↓
              Contiene:
              - Ficheros .dex
              - Recursos .arsc
              - AndroidManifest.xml
```

### Componentes de una App
| Componente | Descripción |
|------------|-------------|
| **Activity** | Pantalla de interfaz |
| **Service** | Tarea en segundo plano |
| **Content Provider** | Compartir datos entre apps |
| **Broadcast Receiver** | Escuchar eventos del sistema |
| **Intent** | Comunicación entre componentes |
| **Notification** | Avisos al usuario |

### Android Studio
- IDE oficial de Google
- Basado en **IntelliJ IDEA**
- Sistema de construcción: **Gradle**
- Descarga: https://developer.android.com/studio

### API Levels Importantes
| Versión | API | Nombre |
|---------|-----|--------|
| 11 | 30 | R |
| 12 | 31 | S |
| 13 | 33 | T (Tiramisu) |
| 14 | 34 | U |

---

## 3.2 Android: Activities, Layouts & Resources

### Activity
> "Una Activity es una molécula: un trozo discreto de funcionalidad"

- **Una pantalla** de la interfaz
- Independientes entre sí
- Tienen un **ciclo de vida**

### Ciclo de Vida de una Activity
```
           onCreate()
               ↓
           onStart() ←───── onRestart()
               ↓                 ↑
           onResume()            │
               ↓                 │
        [ACTIVITY RUNNING]       │
               ↓                 │
           onPause() ────────────┤
               ↓                 │
           onStop() ─────────────┘
               ↓
          onDestroy()
```

**Métodos principales:**
- `onCreate()`: Se crea la Activity (inicializar UI)
- `onStart()`: Se hace visible
- `onResume()`: Obtiene el foco
- `onPause()`: Pierde el foco
- `onStop()`: Ya no es visible
- `onDestroy()`: Se destruye

### AndroidManifest.xml
```xml
<!-- Permisos -->
<uses-permission android:name="android.permission.INTERNET" />

<!-- Activity principal (LAUNCHER) -->
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

### Layouts Principales

#### LinearLayout (vertical/horizontal)
```xml
<LinearLayout
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <!-- Elementos van uno tras otro -->
</LinearLayout>
```

#### RelativeLayout
```xml
<RelativeLayout>
    <TextView android:id="@+id/label"
        android:layout_alignParentTop="true"/>
    <EditText
        android:layout_below="@id/label"/>
</RelativeLayout>
```

#### FrameLayout
```xml
<FrameLayout>
    <!-- Elementos se apilan uno sobre otro -->
</FrameLayout>
```

### Widgets Principales

| Widget | Descripción |
|--------|-------------|
| `TextView` | Mostrar texto |
| `EditText` | Campo de entrada |
| `Button` | Botón clickeable |
| `ImageView` | Mostrar imagen |
| `CheckBox` | Casilla de verificación |
| `RadioButton` | Botón de opción |
| `Switch` | Interruptor |
| `ProgressBar` | Barra de progreso |

### Dimensiones
- `match_parent`: Ocupa todo el espacio del padre
- `wrap_content`: Solo el espacio necesario
- `dp`: Density-independent pixels (diseño)
- `sp`: Scale-independent pixels (texto)

### IDs en XML
```xml
<!-- Crear nuevo ID -->
android:id="@+id/mi_boton"

<!-- Referenciar ID existente -->
android:layout_below="@id/mi_boton"
```

### Obtener Widgets desde Java
```java
// En onCreate(), después de setContentView()
EditText editText = (EditText) findViewById(R.id.edit_text);
Button button = (Button) findViewById(R.id.my_button);

// Obtener/establecer valor
String valor = editText.getText().toString();
editText.setText("Nuevo valor");
```

### Strings (res/values/strings.xml)
```xml
<resources>
    <string name="app_name">Mi App</string>
    <string name="greeting">Hola %s</string>
</resources>
```

**Uso:**
```java
// En Java
String text = getResources().getString(R.string.app_name);
String formatted = String.format(getString(R.string.greeting), "Juan");

// En XML
android:text="@string/app_name"
```

### Eventos (Click Listener)
```java
// Opción 1: Clase anónima
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Acción al pulsar
    }
});

// Opción 2: Clase separada
public class MyClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        // Acción
    }
}
button.setOnClickListener(new MyClickListener());
```

### Intents (Navegar entre Activities)
```java
// Abrir nueva Activity
Intent intent = new Intent(this, SecondActivity.class);
startActivity(intent);

// Pasar datos
intent.putExtra("key", "value");
intent.putExtra("numero", 42);

// Recibir datos en destino
Bundle bundle = getIntent().getExtras();
String value = bundle.getString("key");
int numero = bundle.getInt("numero");

// Cerrar Activity actual
finish();
```

### Visibilidad de Elementos
```java
view.setVisibility(View.VISIBLE);   // Visible
view.setVisibility(View.INVISIBLE); // Oculto pero ocupa espacio
view.setVisibility(View.GONE);      // Oculto y no ocupa espacio
```

---

## 3.3 Android: Gradle & SharedPreferences

### Gradle
- Herramienta de **automatización**
- Usa **Groovy DSL**
- Archivo principal: `build.gradle`

### build.gradle (app)
```groovy
plugins {
    id 'com.android.application'
}

android {
    compileSdk 33
    
    defaultConfig {
        applicationId "es.unavarra.tlm.mi_app"
        minSdk 30
        targetSdk 33
        versionCode 1
        versionName "1.0"
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    // Añadir librerías aquí
}
```

### Añadir Dependencias
```groovy
dependencies {
    // HTTP Client
    implementation 'com.loopj.android:android-async-http:1.4.11'
    
    // Gson (JSON)
    implementation 'com.google.code.gson:gson:2.10.1'
    
    // GreenDAO (base de datos)
    implementation 'org.greenrobot:greendao:3.3.0'
}
```

### SharedPreferences
> Almacenamiento simple de pares clave-valor

**Obtener valores:**
```java
SharedPreferences settings = getSharedPreferences("Config", 0);

boolean alarma = settings.getBoolean("alarm_active", false);
float distancia = settings.getFloat("distance", 0.0f);
int max = settings.getInt("max_issues", 10);
long timestamp = settings.getLong("last_open", 0);
String username = settings.getString("username", "");
```

**Guardar valores:**
```java
SharedPreferences settings = getSharedPreferences("Config", 0);
SharedPreferences.Editor editor = settings.edit();

editor.putBoolean("alarm_active", true);
editor.putFloat("distance", 5.5f);
editor.putInt("max_issues", 20);
editor.putLong("last_open", System.currentTimeMillis());
editor.putString("username", "Juan");

// ¡IMPORTANTE! No olvidar commit()
editor.commit();
```

**Características:**
- Datos **privados** a la aplicación
- Archivo XML interno
- NO sirve para pasar datos entre Activities (usar Intent)
- Solo tipos simples (boolean, int, float, long, String)

---

## 3.4 Android: Bases de Datos & ORMs (GreenDAO)

### SQLite
- Base de datos **relacional** (RDBMS)
- Integrada en Android desde API 1
- Estándar SQL-92

### GreenDAO
- **ORM** (Object-Relational Mapper)
- Convierte tablas en objetos Java

### Configuración (build.gradle)
```groovy
// Antes de plugins (si Gradle >= 8)
buildscript {
    dependencies {
        classpath 'org.greenrobot:greendao-gradle-plugin:3.3.1'
    }
}

plugins {
    id 'com.android.application'
}

// Después de plugins
apply plugin: 'org.greenrobot.greendao'

android {
    // ...
}

// Configuración de GreenDAO
greendao {
    schemaVersion 1
}

dependencies {
    implementation 'org.greenrobot:greendao:3.3.0'
}
```

### Crear Entidad (Modelo)
```java
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    
    @NotNull
    private String name;
    
    private int age;
    
    // GreenDAO generará constructores y getters/setters
    // Hacer Build > Make Project después de crear
}
```

### Conectar a Base de Datos
```java
// Normalmente en onCreate de la Activity
DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mydb");
Database db = helper.getWritableDb();
DaoSession daoSession = new DaoMaster(db).newSession();

// Obtener el DAO para User
UserDao userDao = daoSession.getUserDao();
```

### Operaciones CRUD

**Create (Insertar):**
```java
User user = new User();
user.setName("Juan");
user.setAge(25);
userDao.insert(user);
```

**Read (Leer):**
```java
// Por ID
User user = userDao.load(1L);

// Todos
List<User> users = userDao.loadAll();

// Con condiciones (QueryBuilder)
List<User> adults = userDao.queryBuilder()
    .where(UserDao.Properties.Age.ge(18))
    .orderAsc(UserDao.Properties.Name)
    .list();
```

**Update (Actualizar):**
```java
user.setName("Juan Carlos");
userDao.update(user);
```

**Delete (Borrar):**
```java
userDao.delete(user);
// o
userDao.deleteByKey(1L);
```

### QueryBuilder
```java
List<User> results = userDao.queryBuilder()
    .where(UserDao.Properties.Name.like("J%"))      // Empieza por J
    .where(UserDao.Properties.Age.gt(18))           // Mayor que 18
    .orderDesc(UserDao.Properties.Age)              // Ordenar desc
    .limit(10)                                      // Máximo 10
    .list();
```

**Operadores:**
| Método | SQL |
|--------|-----|
| `eq(value)` | `= value` |
| `notEq(value)` | `!= value` |
| `gt(value)` | `> value` |
| `ge(value)` | `>= value` |
| `lt(value)` | `< value` |
| `le(value)` | `<= value` |
| `like(pattern)` | `LIKE pattern` |
| `isNull()` | `IS NULL` |
| `isNotNull()` | `IS NOT NULL` |

---

## 3.5 Android: Lists & Adapters

### ListView
- Listado vertical de celdas
- Cada celda es un layout independiente

### En Layout XML
```xml
<ListView
    android:id="@+id/my_list"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

### Layout de Celda (row.xml)
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp">
    
    <TextView
        android:id="@+id/txt_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
</LinearLayout>
```

### Adapter (extiende BaseAdapter)
```java
public class UserListAdapter extends BaseAdapter {
    private List<User> users;
    private Context context;
    
    public UserListAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }
    
    // Número de elementos
    @Override
    public int getCount() {
        return users.size();
    }
    
    // Elemento en posición
    @Override
    public User getItem(int position) {
        return users.get(position);
    }
    
    // ID del elemento
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    // Vista de cada celda
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reutilizar vista si existe
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row, null);
        }
        
        // Obtener datos
        User user = getItem(position);
        
        // Configurar vista
        TextView txtName = convertView.findViewById(R.id.txt_name);
        txtName.setText(user.getName());
        
        return convertView;
    }
}
```

### Usar Adapter
```java
// En onCreate
ListView listView = findViewById(R.id.my_list);
List<User> users = userDao.loadAll();
UserListAdapter adapter = new UserListAdapter(this, users);
listView.setAdapter(adapter);
```

### Click en Item
```java
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = (User) parent.getItemAtPosition(position);
        // Hacer algo con user
    }
});
```

### Long Click (pulsación larga)
```java
listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // Acción
        return true; // true = evento consumido
    }
});
```

---

## Anexo 1: Peticiones REST

### Dependencias Necesarias
```groovy
dependencies {
    // HTTP Client asíncrono
    implementation 'com.loopj.android:android-async-http:1.4.11'
    
    // Gson para JSON
    implementation 'com.google.code.gson:gson:2.10.1'
}
```

### Permiso en AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET" />

<application
    android:usesCleartextTraffic="true"  <!-- Si usas HTTP en vez de HTTPS -->
    ...>
```

### Modelo para Request (enviar JSON)
```java
// JSON: {"name": "Juan"}
public class WelcomeRequest {
    private String name;
    
    public WelcomeRequest(String name) {
        this.name = name;
    }
    
    public String getName() { return name; }
}
```

### Modelo para Response (recibir JSON)
```java
// JSON: {"message": "Hello Juan"}
public class WelcomeResponse {
    private String message;
    
    public String getMessage() { return message; }
}
```

### Modelo para Error
```java
// JSON: {"error": 1}
public class ErrorResponse {
    private int error;
    
    public int getError() { return error; }
}
```

### Response Handler
```java
public class MyResponseHandler extends AsyncHttpResponseHandler {
    
    private Gson gson = new Gson();
    
    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        // Código 2XX - Éxito
        String json = new String(responseBody);
        WelcomeResponse response = gson.fromJson(json, WelcomeResponse.class);
        // Usar response...
    }
    
    @Override
    public void onFailure(int statusCode, Header[] headers, 
                         byte[] responseBody, Throwable error) {
        // Código 4XX o 5XX - Error
        if (responseBody != null) {
            String json = new String(responseBody);
            ErrorResponse errorResp = gson.fromJson(json, ErrorResponse.class);
            // Mostrar error según código
        }
    }
}
```

### Hacer Petición POST
```java
// En la Activity
Gson gson = new Gson();
AsyncHttpClient client = new AsyncHttpClient();

// Crear objeto de request
WelcomeRequest request = new WelcomeRequest("Juan");

// Convertir a JSON
String jsonBody = gson.toJson(request);

// Crear entity
try {
    StringEntity entity = new StringEntity(jsonBody);
    
    // Hacer petición
    client.post(
        this,                              // Contexto
        "https://api.ejemplo.com/welcome", // URL
        entity,                            // Body
        "application/json",                // Content-Type
        new MyResponseHandler()            // Handler
    );
} catch (UnsupportedEncodingException e) {
    e.printStackTrace();
}
```

### Hacer Petición PUT
```java
client.put(
    this,
    "https://api.ejemplo.com/resource",
    entity,
    "application/json",
    new MyResponseHandler()
);
```

### Hacer Petición GET
```java
client.get(
    "https://api.ejemplo.com/resource",
    new MyResponseHandler()
);
```

### Códigos HTTP Importantes
| Código | Significado |
|--------|-------------|
| 200 | OK - Éxito |
| 201 | Created - Recurso creado |
| 400 | Bad Request - Error del cliente |
| 401 | Unauthorized - No autenticado |
| 404 | Not Found - No existe |
| 500 | Server Error - Error del servidor |

---

## 🎯 RESUMEN RÁPIDO PARA EL EXAMEN

### Estructura Básica de un Proyecto
```
app/
├── src/main/
│   ├── java/es/unavarra/tlm/app/
│   │   ├── MainActivity.java
│   │   ├── RequestModels.java
│   │   └── ResponseModels.java
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml
│   │   └── values/
│   │       └── strings.xml
│   └── AndroidManifest.xml
└── build.gradle
```

### Checklist del Examen
- [ ] Crear layout con todos los elementos pedidos
- [ ] Todos los textos en strings.xml
- [ ] Permiso INTERNET en AndroidManifest
- [ ] Crear modelos para Request y Response
- [ ] Implementar ResponseHandler con onSuccess y onFailure
- [ ] Validar campos vacíos antes de enviar
- [ ] Mostrar errores según código de respuesta
- [ ] inputType="number" para teclado numérico (extra)
- [ ] Validar que es número antes de enviar (extra)

### Plantilla de Activity
```java
public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView txtError;
    
    private AsyncHttpClient client;
    private Gson gson;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Inicializar
        client = new AsyncHttpClient();
        gson = new Gson();
        
        // Obtener widgets
        editText = findViewById(R.id.edit_text);
        button = findViewById(R.id.button);
        txtError = findViewById(R.id.txt_error);
        
        // Configurar listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });
    }
    
    private void onButtonClick() {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            showError("Campo requerido");
            return;
        }
        sendRequest(value);
    }
    
    private void showError(String msg) {
        txtError.setText(msg);
        txtError.setVisibility(View.VISIBLE);
    }
}
```
