# 🔧 CHULETA DE PRÁCTICAS - CONCEPTOS PARA EL EXAMEN

## Práctica 1: Sistemas de Comunicación (netcat y cURL)

### netcat (nc) - Crear conexiones TCP/UDP

```bash
# SERVIDOR - escuchar en puerto 1234
nc -l 1234

# SERVIDOR con -k (mantiene la conexión abierta después de que el cliente desconecte)
nc -l -k 1234

# CLIENTE - conectar a servidor
nc 127.0.0.1 1234

# 127.0.0.1 = localhost (tu propia máquina)
```

### cURL - Peticiones HTTP

```bash
# GET simple
curl https://www.ejemplo.com

# GET con cabeceras visibles (-v = verbose)
curl -v https://www.ejemplo.com

# POST con JSON
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Juan"}' \
  https://api.ejemplo.com/endpoint

# Con API Key en cabecera
curl -H "api_key: TU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  "https://opendata.aemet.es/opendata/api/..."
```

**Códigos HTTP importantes:**
- 2XX: Éxito
- 4XX: Error del cliente
- 5XX: Error del servidor

---

## Práctica 2: Git

### Comandos básicos

```bash
# Inicializar repositorio
git init

# Añadir remote
git remote add origin <url_repositorio>

# Ver estado
git status

# Añadir archivos al stage
git add archivo.txt
git add .  # todos los archivos

# Hacer commit
git commit -m "Mensaje del commit"

# Subir cambios
git push origin master
git push origin main

# Bajar cambios
git pull origin master
```

### Tags (etiquetas)

```bash
# Crear tag
git tag "practica-3"

# Subir tag a remote
git push origin "practica-3"

# Ver tags
git tag
```

### Branches (ramas)

```bash
# Crear branch
git branch nombre-rama

# Cambiar a branch
git checkout nombre-rama

# Crear y cambiar en un solo comando
git checkout -b nombre-rama

# Volver a master
git checkout master

# Merge (desde master)
git merge nombre-rama

# Merge sin fast-forward (crea commit de merge)
git merge --no-ff nombre-rama
```

### .gitignore

```
# Archivo .gitignore - archivos que git ignorará
secret.txt
*.log
node_modules/
.env
```

### SSH Keys

```bash
# Generar par de claves
ssh-keygen

# Archivos generados:
# ~/.ssh/id_rsa      (clave PRIVADA - NUNCA compartir)
# ~/.ssh/id_rsa.pub  (clave PÚBLICA - añadir a Bitbucket)
```

---

## Práctica 3: Android Primeros Pasos & SharedPreferences

### Estructura de 3 Activities

```
MainActivity (Pantalla inicial)
    ├── "Entrar" → LoginActivity
    └── "Registro" → RegisterActivity
```

### Toast (mensajes emergentes)

```java
// Toast simple
Toast.makeText(this, "Mensaje", Toast.LENGTH_SHORT).show();

// Toast largo
Toast.makeText(this, "Mensaje largo", Toast.LENGTH_LONG).show();

// Con string de resources
Toast.makeText(this, getString(R.string.mensaje), Toast.LENGTH_SHORT).show();

// Con formato
String msg = String.format("Bienvenido %s", username);
Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
```

### Campo de contraseña

```xml
<EditText
    android:id="@+id/edit_password"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="@string/password_hint"
    android:inputType="textPassword"/>
```

### Validar campos vacíos

```java
String username = editUsername.getText().toString().trim();
String password = editPassword.getText().toString().trim();

if (username.isEmpty()) {
    Toast.makeText(this, "Usuario requerido", Toast.LENGTH_SHORT).show();
    return;
}

if (password.isEmpty()) {
    Toast.makeText(this, "Contraseña requerida", Toast.LENGTH_SHORT).show();
    return;
}
```

### Validar contraseña

```java
// Contraseña fija "dscr"
if (!password.equals("dscr")) {
    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
    return;
}
```

### SharedPreferences COMPLETO

```java
// ========== GUARDAR ==========
SharedPreferences prefs = getSharedPreferences("MiConfig", MODE_PRIVATE);
SharedPreferences.Editor editor = prefs.edit();
editor.putString("username", username);
editor.commit();  // ¡OBLIGATORIO!

// ========== LEER ==========
SharedPreferences prefs = getSharedPreferences("MiConfig", MODE_PRIVATE);
String username = prefs.getString("username", "");  // "" es valor por defecto

// Comprobar si existe
if (!username.isEmpty()) {
    // Usuario guardado existe
}

// ========== BORRAR ==========
SharedPreferences prefs = getSharedPreferences("MiConfig", MODE_PRIVATE);
SharedPreferences.Editor editor = prefs.edit();
editor.remove("username");  // Borrar una clave
// o
editor.clear();  // Borrar todo
editor.commit();
```

### Cambiar visibilidad según estado

```java
// En onCreate de LoginActivity
SharedPreferences prefs = getSharedPreferences("MiConfig", MODE_PRIVATE);
String username = prefs.getString("username", "");

if (!username.isEmpty()) {
    // Usuario YA identificado - mostrar "Hola usuario" y botón "Salir"
    txtHola.setText("Hola " + username);
    txtHola.setVisibility(View.VISIBLE);
    btnSalir.setVisibility(View.VISIBLE);
    
    // Ocultar formulario de login
    editUsername.setVisibility(View.GONE);
    editPassword.setVisibility(View.GONE);
    btnLogin.setVisibility(View.GONE);
} else {
    // Usuario NO identificado - mostrar formulario normal
    txtHola.setVisibility(View.GONE);
    btnSalir.setVisibility(View.GONE);
    
    editUsername.setVisibility(View.VISIBLE);
    editPassword.setVisibility(View.VISIBLE);
    btnLogin.setVisibility(View.VISIBLE);
}
```

### Navegar entre Activities

```java
// Abrir nueva Activity
Intent intent = new Intent(this, LoginActivity.class);
startActivity(intent);

// Cerrar Activity actual y volver a la anterior
finish();
```

---

## Práctica 4: Bases de Datos & ORMs (GreenDAO)

### Configuración build.gradle

```groovy
// Antes de plugins
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

// En android {}
greendao {
    schemaVersion 1
}

// En dependencies
dependencies {
    implementation 'org.greenrobot:greendao:3.3.0'
}
```

### Entidad Access (ejemplo de la práctica)

```java
@Entity
public class Access {
    @Id(autoincrement = true)
    private Long id;
    
    @NotNull
    private String username;
    
    @NotNull
    private boolean valid;
    
    @NotNull
    private java.util.Date created_at;
    
    // Después de crear: Build > Make Project
    // GreenDAO genera constructores, getters y setters automáticamente
}
```

### Conexión a Base de Datos

```java
// En la Activity (normalmente en onCreate o en Application)
DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "accesos-db");
Database db = helper.getWritableDb();
DaoSession daoSession = new DaoMaster(db).newSession();

// Obtener el DAO
AccessDao accessDao = daoSession.getAccessDao();
```

### Insertar registro de acceso

```java
// Crear nuevo acceso
Access access = new Access();
access.setUsername(username);
access.setValid(password.equals("dscr"));  // true si contraseña correcta
access.setCreated_at(new Date());  // Fecha y hora actual

// Insertar en BD
accessDao.insert(access);
```

### Contar registros (para el semáforo)

```java
// Contar accesos válidos
long validCount = accessDao.queryBuilder()
    .where(AccessDao.Properties.Valid.eq(true))
    .count();

// Contar accesos inválidos
long invalidCount = accessDao.queryBuilder()
    .where(AccessDao.Properties.Valid.eq(false))
    .count();

// Determinar color del semáforo
ImageView imgStatus = findViewById(R.id.img_status);

if (validCount > invalidCount) {
    imgStatus.setImageResource(R.drawable.bullet_green);
} else if (invalidCount > validCount) {
    imgStatus.setImageResource(R.drawable.bullet_red);
} else {
    imgStatus.setImageResource(R.drawable.bullet_grey);
}
```

### Alternativa: contar manualmente

```java
List<Access> allAccess = accessDao.loadAll();
int valid = 0;
int invalid = 0;

for (Access a : allAccess) {
    if (a.getValid()) {
        valid++;
    } else {
        invalid++;
    }
}
```

---

## Práctica 5: Lists & Adapters

### ListView en Layout

```xml
<ListView
    android:id="@+id/list_accesos"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

### Layout de cada fila (row_access.xml)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp">

    <!-- Icono verde/rojo -->
    <ImageView
        android:id="@+id/img_valid"
        android:layout_width="24dp"
        android:layout_height="24dp"
        android:layout_marginEnd="16dp"/>

    <LinearLayout
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:orientation="vertical">

        <!-- Username -->
        <TextView
            android:id="@+id/txt_username"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textStyle="bold"/>

        <!-- Fecha -->
        <TextView
            android:id="@+id/txt_date"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="12sp"/>

    </LinearLayout>
</LinearLayout>
```

### Adapter personalizado COMPLETO

```java
public class AccessAdapter extends BaseAdapter {
    
    private List<Access> accesos;
    private Context context;
    
    // Constructor
    public AccessAdapter(Context context, List<Access> accesos) {
        this.context = context;
        this.accesos = accesos;
    }
    
    @Override
    public int getCount() {
        return accesos.size();
    }
    
    @Override
    public Access getItem(int position) {
        return accesos.get(position);
    }
    
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reutilizar vista si existe
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.row_access, parent, false);
        }
        
        // Obtener el acceso de esta posición
        Access access = getItem(position);
        
        // Obtener referencias a los widgets
        ImageView imgValid = convertView.findViewById(R.id.img_valid);
        TextView txtUsername = convertView.findViewById(R.id.txt_username);
        TextView txtDate = convertView.findViewById(R.id.txt_date);
        
        // Configurar valores
        txtUsername.setText(access.getUsername());
        
        // Formatear fecha "YYYY-MM-DD HH:MM:SS"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        txtDate.setText(sdf.format(access.getCreated_at()));
        
        // Icono según validez
        if (access.getValid()) {
            imgValid.setImageResource(R.drawable.bullet_green);
        } else {
            imgValid.setImageResource(R.drawable.bullet_red);
        }
        
        return convertView;
    }
}
```

### Usar el Adapter en Activity

```java
public class AccessListActivity extends AppCompatActivity {
    
    private ListView listView;
    private AccessDao accessDao;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_list);
        
        // Inicializar BD
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "accesos-db");
        Database db = helper.getWritableDb();
        DaoSession session = new DaoMaster(db).newSession();
        accessDao = session.getAccessDao();
        
        // Obtener ListView
        listView = findViewById(R.id.list_accesos);
        
        // Obtener datos ordenados por fecha DESCENDENTE (más nuevos primero)
        List<Access> accesos = accessDao.queryBuilder()
            .orderDesc(AccessDao.Properties.Created_at)
            .list();
        
        // Crear y asignar adapter
        AccessAdapter adapter = new AccessAdapter(this, accesos);
        listView.setAdapter(adapter);
        
        // Click en item de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el item clickeado
                Access access = (Access) parent.getItemAtPosition(position);
                
                // Abrir detalle pasando el ID
                Intent intent = new Intent(AccessListActivity.this, AccessDetailActivity.class);
                intent.putExtra("access_id", access.getId());
                startActivity(intent);
            }
        });
    }
}
```

### Activity de Detalle

```java
public class AccessDetailActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_detail);
        
        // Obtener ID del intent
        Bundle extras = getIntent().getExtras();
        long accessId = extras.getLong("access_id");
        
        // Conectar a BD
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "accesos-db");
        Database db = helper.getWritableDb();
        DaoSession session = new DaoMaster(db).newSession();
        AccessDao accessDao = session.getAccessDao();
        
        // Buscar el registro por ID
        Access access = accessDao.load(accessId);
        
        // Mostrar datos
        TextView txtUsername = findViewById(R.id.txt_detail_username);
        TextView txtDate = findViewById(R.id.txt_detail_date);
        TextView txtValid = findViewById(R.id.txt_detail_valid);
        
        txtUsername.setText(access.getUsername());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        txtDate.setText(sdf.format(access.getCreated_at()));
        
        txtValid.setText(access.getValid() ? "Sí" : "No");
    }
}
```

### Formatear fechas

```java
import java.text.SimpleDateFormat;
import java.util.Date;

// Crear formateador
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

// Date a String
Date fecha = new Date();
String fechaStr = sdf.format(fecha);  // "2025-01-08 14:30:45"

// String a Date (si lo necesitas)
Date fecha2 = sdf.parse("2025-01-08 14:30:45");
```

---

## 📋 RESUMEN DE PATRONES COMUNES

### Patrón: Validar → Procesar → Mostrar resultado

```java
private void onButtonClick() {
    // 1. Obtener valores
    String valor = editText.getText().toString().trim();
    
    // 2. Validar
    if (valor.isEmpty()) {
        Toast.makeText(this, "Campo requerido", Toast.LENGTH_SHORT).show();
        return;
    }
    
    // 3. Procesar (guardar en BD, enviar petición, etc.)
    // ...
    
    // 4. Mostrar resultado
    Toast.makeText(this, "Operación exitosa", Toast.LENGTH_SHORT).show();
    
    // 5. Opcional: cerrar activity
    finish();
}
```

### Patrón: Cargar datos y mostrar diferente UI

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.mi_activity);
    
    // Obtener widgets
    // ...
    
    // Comprobar estado/datos
    if (hayDatosGuardados()) {
        mostrarVistaConDatos();
    } else {
        mostrarVistaVacia();
    }
}

private void mostrarVistaConDatos() {
    layoutConDatos.setVisibility(View.VISIBLE);
    layoutVacio.setVisibility(View.GONE);
}

private void mostrarVistaVacia() {
    layoutConDatos.setVisibility(View.GONE);
    layoutVacio.setVisibility(View.VISIBLE);
}
```

### Patrón: Pasar datos entre Activities

```java
// ORIGEN: Enviar datos
Intent intent = new Intent(this, DetalleActivity.class);
intent.putExtra("id", 123L);           // Long
intent.putExtra("nombre", "Juan");     // String
intent.putExtra("activo", true);       // boolean
startActivity(intent);

// DESTINO: Recibir datos
Bundle extras = getIntent().getExtras();
if (extras != null) {
    long id = extras.getLong("id");
    String nombre = extras.getString("nombre");
    boolean activo = extras.getBoolean("activo");
}
```

---

## ⚠️ ERRORES COMUNES A EVITAR

1. **Olvidar `editor.commit()`** después de SharedPreferences
2. **No hacer `Build > Make Project`** después de crear una entidad GreenDAO
3. **Usar `==` en vez de `.equals()`** para comparar Strings
4. **Olvidar `trim()`** al obtener texto de EditText
5. **No declarar Activity** en AndroidManifest.xml
6. **Olvidar permiso INTERNET** para peticiones HTTP
7. **Textos hardcoded** en vez de usar strings.xml
8. **No manejar null** en extras de Intent
