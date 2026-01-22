# 📐 GUÍA COMPLETA DE LAYOUTS XML PARA ANDROID

## ÍNDICE
1. [LinearLayout - Distribuciones](#1-linearlayout)
2. [Formularios](#2-formularios)
3. [Listas (ListView)](#3-listas-listview)
4. [Filas de ListView (row.xml)](#4-filas-de-listview)
5. [Pantallas de Detalle](#5-pantallas-de-detalle)
6. [Combinaciones Comunes](#6-combinaciones-comunes)
7. [Widgets y Atributos](#7-widgets-y-atributos)
8. [Trucos y Patrones](#8-trucos-y-patrones)

---

# 1. LINEARLAYOUT

## 1.1 Vertical básico
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView android:id="@+id/txt1" ... />
    <TextView android:id="@+id/txt2" ... />
    <Button android:id="@+id/btn1" ... />

</LinearLayout>
```

## 1.2 Horizontal básico
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <Button android:id="@+id/btn1" ... />
    <Button android:id="@+id/btn2" ... />
    <Button android:id="@+id/btn3" ... />

</LinearLayout>
```

## 1.3 Horizontal con peso (distribuir espacio)
```xml
<!-- Tres botones que ocupan el mismo espacio -->
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <Button
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Btn 1"/>
    
    <Button
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Btn 2"/>
    
    <Button
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Btn 3"/>

</LinearLayout>
```

## 1.4 Elemento que ocupa todo el espacio restante
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <!-- Este TextView ocupa todo el espacio restante -->
    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Título largo..."/>
    
    <!-- Este botón solo ocupa lo necesario -->
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="OK"/>

</LinearLayout>
```

## 1.5 Centrar contenido
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center">

    <!-- Todo el contenido estará centrado -->
    <TextView ... />
    <Button ... />

</LinearLayout>
```

## 1.6 Diferentes alineaciones con gravity
```xml
<!-- gravity="center" -> centra horizontal y verticalmente -->
<!-- gravity="center_horizontal" -> centra solo horizontalmente -->
<!-- gravity="center_vertical" -> centra solo verticalmente -->
<!-- gravity="start" o "left" -> alinea a la izquierda -->
<!-- gravity="end" o "right" -> alinea a la derecha -->
<!-- gravity="top" -> arriba -->
<!-- gravity="bottom" -> abajo -->

<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center_horizontal|bottom">
    
    <!-- Contenido centrado horizontalmente y abajo -->

</LinearLayout>
```

---

# 2. FORMULARIOS

## 2.1 Formulario simple (vertical)
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp">

    <EditText
        android:id="@+id/edit_nombre"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Nombre"
        android:inputType="textPersonName"/>

    <EditText
        android:id="@+id/edit_email"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Email"
        android:inputType="textEmailAddress"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/edit_password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Contraseña"
        android:inputType="textPassword"
        android:layout_marginTop="16dp"/>

    <Button
        android:id="@+id/btn_submit"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="ENVIAR"
        android:layout_marginTop="24dp"/>

</LinearLayout>
```

## 2.2 Formulario con labels
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Nombre:"
        android:textStyle="bold"/>

    <EditText
        android:id="@+id/edit_nombre"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textPersonName"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Edad:"
        android:textStyle="bold"
        android:layout_marginTop="16dp"/>

    <EditText
        android:id="@+id/edit_edad"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="number"/>

    <Button
        android:id="@+id/btn_guardar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="GUARDAR"
        android:layout_marginTop="24dp"/>

</LinearLayout>
```

## 2.3 Formulario con campo y botón en línea
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!-- Barra superior: campo + botón -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="16dp"
        android:background="#E0E0E0">

        <EditText
            android:id="@+id/edit_buscar"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:hint="Buscar..."
            android:inputType="text"/>

        <Button
            android:id="@+id/btn_buscar"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:text="BUSCAR"/>

    </LinearLayout>

    <!-- Resto del contenido -->
    <ListView
        android:id="@+id/list_resultados"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

## 2.4 Formulario con dos botones (Cancelar / Aceptar)
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp">

    <!-- Campos del formulario -->
    <EditText
        android:id="@+id/edit_campo1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Campo 1"/>

    <EditText
        android:id="@+id/edit_campo2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Campo 2"
        android:layout_marginTop="16dp"/>

    <!-- Espacio flexible para empujar botones abajo -->
    <View
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>

    <!-- Botones en línea -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <Button
            android:id="@+id/btn_cancelar"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="CANCELAR"/>

        <Button
            android:id="@+id/btn_aceptar"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:layout_marginStart="8dp"
            android:text="ACEPTAR"/>

    </LinearLayout>

</LinearLayout>
```

## 2.5 Formulario con mensaje de error
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp">

    <EditText
        android:id="@+id/edit_usuario"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Usuario"
        android:inputType="text"/>

    <!-- Mensaje de error (oculto por defecto) -->
    <TextView
        android:id="@+id/txt_error"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Usuario no válido"
        android:textColor="#F44336"
        android:visibility="gone"/>

    <Button
        android:id="@+id/btn_validar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="VALIDAR"
        android:layout_marginTop="16dp"/>

</LinearLayout>
```

---

# 3. LISTAS (LISTVIEW)

## 3.1 Solo ListView
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <ListView
        android:id="@+id/list_items"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

## 3.2 Título + ListView
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Mis Elementos"
        android:textSize="24sp"
        android:textStyle="bold"
        android:padding="16dp"
        android:background="#E0E0E0"/>

    <ListView
        android:id="@+id/list_items"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

## 3.3 Campo búsqueda + ListView
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="16dp">

        <EditText
            android:id="@+id/edit_buscar"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:hint="Buscar..."/>

        <ImageButton
            android:id="@+id/btn_buscar"
            android:layout_width="48dp"
            android:layout_height="48dp"
            android:src="@android:drawable/ic_menu_search"
            android:background="?attr/selectableItemBackground"/>

    </LinearLayout>

    <ListView
        android:id="@+id/list_resultados"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

## 3.4 ListView + Botón flotante abajo
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <ListView
        android:id="@+id/list_items"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>

    <Button
        android:id="@+id/btn_añadir"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="AÑADIR NUEVO"
        android:layout_margin="16dp"/>

</LinearLayout>
```

## 3.5 Formulario arriba + ListView abajo
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <!-- Formulario -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="16dp"
        android:background="#EEEEEE">

        <EditText
            android:id="@+id/edit_nuevo"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:hint="Nueva tarea..."/>

        <Button
            android:id="@+id/btn_añadir"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:text="AÑADIR"/>

    </LinearLayout>

    <!-- Lista -->
    <ListView
        android:id="@+id/list_tareas"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</LinearLayout>
```

---

# 4. FILAS DE LISTVIEW (row.xml)

## 4.1 Fila simple (solo texto)
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp">

    <TextView
        android:id="@+id/txt_titulo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"/>

</LinearLayout>
```

## 4.2 Fila con título y subtítulo
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/row_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/txt_titulo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:textStyle="bold"/>

    <TextView
        android:id="@+id/txt_subtitulo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="14sp"
        android:textColor="#666666"
        android:layout_marginTop="4dp"/>

</LinearLayout>
```

## 4.3 Fila con icono a la izquierda
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/row_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp"
    android:gravity="center_vertical">

    <ImageView
        android:id="@+id/img_icono"
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:layout_marginEnd="16dp"/>

    <TextView
        android:id="@+id/txt_titulo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"/>

</LinearLayout>
```

## 4.4 Fila con icono y texto a la derecha
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/row_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp"
    android:gravity="center_vertical">

    <ImageView
        android:id="@+id/img_icono"
        android:layout_width="24dp"
        android:layout_height="24dp"
        android:layout_marginEnd="16dp"/>

    <!-- Título ocupa espacio restante -->
    <TextView
        android:id="@+id/txt_titulo"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:textSize="16sp"/>

    <!-- Valor a la derecha -->
    <TextView
        android:id="@+id/txt_valor"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="14sp"
        android:textColor="#666666"/>

</LinearLayout>
```

## 4.5 Fila tipo "partido" (puntos - equipos - puntos)
```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- Ejemplo: 76 Boston Celtics @ Denver Nuggets 52 -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/row_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp"
    android:gravity="center_vertical">

    <!-- Puntos equipo 1 -->
    <TextView
        android:id="@+id/txt_score1"
        android:layout_width="40dp"
        android:layout_height="wrap_content"
        android:gravity="end"
        android:textStyle="bold"
        android:textSize="18sp"/>

    <!-- Nombre equipo 1 -->
    <TextView
        android:id="@+id/txt_team1"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:gravity="end"
        android:paddingStart="8dp"
        android:paddingEnd="8dp"/>

    <!-- Separador @ -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@"
        android:paddingStart="8dp"
        android:paddingEnd="8dp"/>

    <!-- Nombre equipo 2 -->
    <TextView
        android:id="@+id/txt_team2"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:gravity="start"
        android:paddingStart="8dp"
        android:paddingEnd="8dp"/>

    <!-- Puntos equipo 2 -->
    <TextView
        android:id="@+id/txt_score2"
        android:layout_width="40dp"
        android:layout_height="wrap_content"
        android:gravity="start"
        android:textStyle="bold"
        android:textSize="18sp"/>

</LinearLayout>
```

## 4.6 Fila con múltiples columnas (tabla)
```xml
<?xml version="1.0" encoding="utf-8"?>
<!-- Ejemplo: Pos | Nombre | Dorsal | Tiempo -->
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/row_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="12dp"
    android:gravity="center_vertical">

    <!-- Columna 1: Posición (ancho fijo) -->
    <TextView
        android:id="@+id/txt_posicion"
        android:layout_width="40dp"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:textStyle="bold"/>

    <!-- Columna 2: Nombre (flexible) -->
    <TextView
        android:id="@+id/txt_nombre"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:gravity="start"
        android:paddingStart="8dp"/>

    <!-- Columna 3: Dorsal (ancho fijo) -->
    <TextView
        android:id="@+id/txt_dorsal"
        android:layout_width="50dp"
        android:layout_height="wrap_content"
        android:gravity="center"/>

    <!-- Columna 4: Tiempo (ancho fijo) -->
    <TextView
        android:id="@+id/txt_tiempo"
        android:layout_width="80dp"
        android:layout_height="wrap_content"
        android:gravity="end"/>

</LinearLayout>
```

## 4.7 Fila con icono condicional (disponible/ocupado)
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/row_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp"
    android:gravity="center_vertical">

    <!-- Título -->
    <TextView
        android:id="@+id/txt_titulo"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:textSize="16sp"/>

    <!-- Icono de estado -->
    <ImageView
        android:id="@+id/img_status"
        android:layout_width="24dp"
        android:layout_height="24dp"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"/>

    <!-- Texto de estado -->
    <TextView
        android:id="@+id/txt_status"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="14sp"/>

</LinearLayout>
```

## 4.8 Fila con checkbox (tarea completable)
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/row_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp"
    android:gravity="center_vertical">

    <!-- Icono check/uncheck -->
    <ImageView
        android:id="@+id/img_check"
        android:layout_width="24dp"
        android:layout_height="24dp"
        android:layout_marginEnd="16dp"/>

    <!-- Título (ocupa espacio) -->
    <TextView
        android:id="@+id/txt_titulo"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:textSize="16sp"/>

    <!-- Fecha -->
    <TextView
        android:id="@+id/txt_fecha"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="12sp"
        android:textColor="#666666"/>

</LinearLayout>
```

## 4.9 Fila con imagen premium condicional
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/row_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp"
    android:gravity="center_vertical">

    <!-- Nombre -->
    <TextView
        android:id="@+id/txt_nombre"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:textSize="16sp"/>

    <!-- Icono premium (GONE por defecto, se muestra en Adapter si es premium) -->
    <ImageView
        android:id="@+id/img_premium"
        android:layout_width="24dp"
        android:layout_height="24dp"
        android:layout_marginEnd="8dp"
        android:src="@drawable/ic_premium"
        android:visibility="gone"/>

    <!-- Valor numérico -->
    <TextView
        android:id="@+id/txt_valor"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="14sp"/>

</LinearLayout>
```

---

# 5. PANTALLAS DE DETALLE

## 5.1 Detalle simple con labels
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp">

    <!-- Título grande -->
    <TextView
        android:id="@+id/txt_titulo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="24sp"
        android:textStyle="bold"
        android:layout_marginBottom="24dp"/>

    <!-- Campo 1 -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Campo 1:"
        android:textColor="#666666"/>

    <TextView
        android:id="@+id/txt_campo1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:layout_marginBottom="16dp"/>

    <!-- Campo 2 -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Campo 2:"
        android:textColor="#666666"/>

    <TextView
        android:id="@+id/txt_campo2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:layout_marginBottom="16dp"/>

</LinearLayout>
```

## 5.2 Detalle con secciones
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp">

    <!-- Título -->
    <TextView
        android:id="@+id/txt_titulo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="24sp"
        android:textStyle="bold"
        android:layout_marginBottom="24dp"/>

    <!-- SECCIÓN 1 -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="INFORMACIÓN"
        android:textStyle="bold"
        android:textColor="#666666"
        android:textSize="12sp"
        android:layout_marginBottom="8dp"/>

    <TextView
        android:id="@+id/txt_info1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"/>

    <TextView
        android:id="@+id/txt_info2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:layout_marginBottom="24dp"/>

    <!-- SECCIÓN 2 -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="DETALLES"
        android:textStyle="bold"
        android:textColor="#666666"
        android:textSize="12sp"
        android:layout_marginBottom="8dp"/>

    <TextView
        android:id="@+id/txt_detalle1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"/>

</LinearLayout>
```

## 5.3 Detalle con marcador grande (tipo partido)
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp">

    <!-- Título: "Equipo1 @ Equipo2" -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="center"
        android:layout_marginBottom="24dp">

        <TextView
            android:id="@+id/txt_team1_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="18sp"
            android:textStyle="bold"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text=" @ "
            android:textSize="18sp"/>

        <TextView
            android:id="@+id/txt_team2_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="18sp"
            android:textStyle="bold"/>

    </LinearLayout>

    <!-- Marcador grande: "10 - 47" -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:gravity="center"
        android:layout_marginBottom="32dp">

        <TextView
            android:id="@+id/txt_score1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="48sp"
            android:textStyle="bold"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text=" - "
            android:textSize="48sp"
            android:paddingStart="24dp"
            android:paddingEnd="24dp"/>

        <TextView
            android:id="@+id/txt_score2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="48sp"
            android:textStyle="bold"/>

    </LinearLayout>

    <!-- Botones +1, +2, +3 para cada equipo -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <!-- Botones equipo 1 -->
        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="horizontal"
            android:gravity="center">

            <Button
                android:id="@+id/btn_team1_1"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="+1"/>

            <Button
                android:id="@+id/btn_team1_2"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="+2"/>

            <Button
                android:id="@+id/btn_team1_3"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="+3"/>

        </LinearLayout>

        <!-- Botones equipo 2 -->
        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="horizontal"
            android:gravity="center">

            <Button
                android:id="@+id/btn_team2_1"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="+1"/>

            <Button
                android:id="@+id/btn_team2_2"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="+2"/>

            <Button
                android:id="@+id/btn_team2_3"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="+3"/>

        </LinearLayout>

    </LinearLayout>

</LinearLayout>
```

## 5.4 Detalle con botón condicional
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp">

    <TextView
        android:id="@+id/txt_titulo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="24sp"
        android:textStyle="bold"
        android:layout_marginBottom="16dp"/>

    <TextView
        android:id="@+id/txt_info"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:layout_marginBottom="24dp"/>

    <!-- Botón que se muestra/oculta desde el código -->
    <!-- visibility="gone" por defecto, se cambia a "visible" si corresponde -->
    <Button
        android:id="@+id/btn_accion"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="REALIZAR ACCIÓN"
        android:visibility="gone"/>

</LinearLayout>
```

---

# 6. COMBINACIONES COMUNES

## 6.1 Login (campos + botón + link)
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="32dp"
    android:gravity="center">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Iniciar Sesión"
        android:textSize="28sp"
        android:textStyle="bold"
        android:layout_marginBottom="32dp"/>

    <EditText
        android:id="@+id/edit_usuario"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Usuario"
        android:inputType="text"/>

    <EditText
        android:id="@+id/edit_password"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Contraseña"
        android:inputType="textPassword"
        android:layout_marginTop="16dp"/>

    <Button
        android:id="@+id/btn_login"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="ENTRAR"
        android:layout_marginTop="24dp"/>

    <TextView
        android:id="@+id/txt_registrar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="¿No tienes cuenta? Regístrate"
        android:textColor="#1976D2"
        android:layout_marginTop="16dp"/>

</LinearLayout>
```

## 6.2 Registro (múltiples campos)
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="24dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Crear Cuenta"
            android:textSize="24sp"
            android:textStyle="bold"
            android:layout_marginBottom="24dp"/>

        <EditText
            android:id="@+id/edit_nombre"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Nombre completo"
            android:inputType="textPersonName"/>

        <EditText
            android:id="@+id/edit_email"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Email"
            android:inputType="textEmailAddress"
            android:layout_marginTop="16dp"/>

        <EditText
            android:id="@+id/edit_telefono"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Teléfono"
            android:inputType="phone"
            android:layout_marginTop="16dp"/>

        <EditText
            android:id="@+id/edit_password"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Contraseña"
            android:inputType="textPassword"
            android:layout_marginTop="16dp"/>

        <EditText
            android:id="@+id/edit_password2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Repetir contraseña"
            android:inputType="textPassword"
            android:layout_marginTop="16dp"/>

        <Button
            android:id="@+id/btn_registrar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="REGISTRARSE"
            android:layout_marginTop="32dp"/>

    </LinearLayout>

</ScrollView>
```

## 6.3 Pantalla de estado (logueado/no logueado)
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="24dp">

    <!-- Sección cuando NO está logueado -->
    <LinearLayout
        android:id="@+id/layout_no_logueado"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:visibility="visible">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="No has iniciado sesión"
            android:textSize="18sp"
            android:layout_marginBottom="16dp"/>

        <Button
            android:id="@+id/btn_ir_login"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="INICIAR SESIÓN"/>

    </LinearLayout>

    <!-- Sección cuando SÍ está logueado -->
    <LinearLayout
        android:id="@+id/layout_logueado"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:visibility="gone">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Bienvenido"
            android:textSize="24sp"
            android:textStyle="bold"/>

        <TextView
            android:id="@+id/txt_nombre_usuario"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:textSize="18sp"
            android:layout_marginTop="8dp"
            android:layout_marginBottom="24dp"/>

        <Button
            android:id="@+id/btn_cerrar_sesion"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="CERRAR SESIÓN"/>

    </LinearLayout>

</LinearLayout>
```

---

# 7. WIDGETS Y ATRIBUTOS

## 7.1 TextView - Atributos comunes
```xml
<TextView
    android:id="@+id/txt_ejemplo"
    android:layout_width="match_parent"      <!-- o wrap_content o 0dp -->
    android:layout_height="wrap_content"
    android:text="Texto estático"            <!-- o @string/nombre -->
    android:textSize="16sp"                  <!-- tamaño (usar sp) -->
    android:textStyle="bold"                 <!-- normal, bold, italic -->
    android:textColor="#FF0000"              <!-- color en hex o @color/nombre -->
    android:gravity="center"                 <!-- alineación del texto dentro -->
    android:padding="16dp"                   <!-- espacio interno -->
    android:layout_margin="8dp"              <!-- espacio externo -->
    android:maxLines="2"                     <!-- máximo de líneas -->
    android:ellipsize="end"                  <!-- ... al final si no cabe -->
    android:visibility="visible"/>           <!-- visible, invisible, gone -->
```

## 7.2 EditText - Tipos de input
```xml
<!-- Texto normal -->
<EditText android:inputType="text" />

<!-- Texto multilínea -->
<EditText android:inputType="textMultiLine" />

<!-- Nombre de persona -->
<EditText android:inputType="textPersonName" />

<!-- Email -->
<EditText android:inputType="textEmailAddress" />

<!-- Contraseña (oculta caracteres) -->
<EditText android:inputType="textPassword" />

<!-- Número entero -->
<EditText android:inputType="number" />

<!-- Número decimal -->
<EditText android:inputType="numberDecimal" />

<!-- Teléfono -->
<EditText android:inputType="phone" />

<!-- URL -->
<EditText android:inputType="textUri" />
```

## 7.3 Button - Variantes
```xml
<!-- Botón normal -->
<Button
    android:id="@+id/btn_normal"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="PULSAR"/>

<!-- Botón que ocupa todo el ancho -->
<Button
    android:id="@+id/btn_ancho"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="ENVIAR"/>

<!-- Botón con icono -->
<Button
    android:id="@+id/btn_icono"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Guardar"
    android:drawableStart="@android:drawable/ic_menu_save"
    android:drawablePadding="8dp"/>
```

## 7.4 ImageView
```xml
<!-- Imagen desde drawable -->
<ImageView
    android:id="@+id/img_icono"
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:src="@drawable/mi_imagen"/>

<!-- Imagen escalada -->
<ImageView
    android:id="@+id/img_foto"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    android:scaleType="centerCrop"/>

<!-- Tipos de scaleType:
     - centerCrop: Recorta para llenar manteniendo proporción
     - centerInside: Escala para que quepa completa
     - fitXY: Estira para llenar (distorsiona)
     - center: Centra sin escalar
-->
```

## 7.5 ImageButton
```xml
<ImageButton
    android:id="@+id/btn_buscar"
    android:layout_width="48dp"
    android:layout_height="48dp"
    android:src="@android:drawable/ic_menu_search"
    android:background="?attr/selectableItemBackground"
    android:contentDescription="Buscar"/>
```

---

# 8. TRUCOS Y PATRONES

## 8.1 Visibility (mostrar/ocultar desde Java)
```xml
<!-- En XML: oculto por defecto -->
<TextView
    android:id="@+id/txt_error"
    android:visibility="gone" />

<Button
    android:id="@+id/btn_accion"
    android:visibility="gone" />
```

```java
// En Java: mostrar cuando corresponda
txtError.setVisibility(View.VISIBLE);
txtError.setVisibility(View.GONE);      // Oculto, no ocupa espacio
txtError.setVisibility(View.INVISIBLE); // Oculto, SÍ ocupa espacio
```

## 8.2 Espacio flexible (empujar elementos)
```xml
<!-- Empujar botón hacia abajo -->
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView ... />
    
    <!-- View vacío que ocupa todo el espacio restante -->
    <View
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>
    
    <!-- Este botón queda abajo del todo -->
    <Button ... />

</LinearLayout>
```

## 8.3 Distribuir elementos equitativamente
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <!-- Todos con width="0dp" y weight="1" -->
    <Button android:layout_width="0dp" android:layout_weight="1" ... />
    <Button android:layout_width="0dp" android:layout_weight="1" ... />
    <Button android:layout_width="0dp" android:layout_weight="1" ... />

</LinearLayout>
```

## 8.4 Un elemento fijo, otro flexible
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <!-- Este ocupa el espacio restante -->
    <EditText
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"/>

    <!-- Este solo ocupa lo que necesita -->
    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="OK"/>

</LinearLayout>
```

## 8.5 ScrollView para contenido largo
```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!-- ScrollView solo puede tener UN hijo directo -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <!-- Aquí todo el contenido -->
        <TextView ... />
        <EditText ... />
        <Button ... />
        <!-- etc -->

    </LinearLayout>

</ScrollView>
```

## 8.6 Padding vs Margin
```xml
<!--
    padding: Espacio INTERNO (entre el borde y el contenido)
    margin: Espacio EXTERNO (entre este elemento y los demás)
-->
<Button
    android:padding="16dp"
    android:layout_margin="8dp"
    android:layout_marginTop="16dp"
    android:layout_marginBottom="16dp"
    android:layout_marginStart="8dp"
    android:layout_marginEnd="8dp"/>
```

## 8.7 Colores comunes
```xml
<!-- Colores en hexadecimal -->
android:textColor="#000000"     <!-- Negro -->
android:textColor="#FFFFFF"     <!-- Blanco -->
android:textColor="#666666"     <!-- Gris -->
android:textColor="#F44336"     <!-- Rojo -->
android:textColor="#4CAF50"     <!-- Verde -->
android:textColor="#2196F3"     <!-- Azul -->
android:textColor="#FF9800"     <!-- Naranja -->

android:background="#E0E0E0"    <!-- Gris claro (fondo) -->
android:background="#F5F5F5"    <!-- Gris muy claro -->
android:background="#EEEEEE"    <!-- Gris suave -->
```

## 8.8 Tamaños recomendados
```xml
<!-- Textos -->
android:textSize="12sp"   <!-- Pequeño (fechas, subtítulos) -->
android:textSize="14sp"   <!-- Normal (cuerpo) -->
android:textSize="16sp"   <!-- Medio (títulos de lista) -->
android:textSize="18sp"   <!-- Grande -->
android:textSize="24sp"   <!-- Título de pantalla -->
android:textSize="32sp"   <!-- Muy grande -->
android:textSize="48sp"   <!-- Enorme (marcadores) -->

<!-- Espaciados -->
android:padding="8dp"     <!-- Poco -->
android:padding="16dp"    <!-- Normal -->
android:padding="24dp"    <!-- Amplio -->
android:padding="32dp"    <!-- Muy amplio -->

<!-- Iconos -->
android:layout_width="16dp"   <!-- Muy pequeño -->
android:layout_width="24dp"   <!-- Normal -->
android:layout_width="40dp"   <!-- Grande -->
android:layout_width="48dp"   <!-- Muy grande -->
```

---

# RESUMEN RÁPIDO

## Layout principal
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <!-- CONTENIDO -->

</LinearLayout>
```

## Fila de ListView
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/row_layout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:padding="16dp"
    android:gravity="center_vertical">

    <!-- CONTENIDO -->

</LinearLayout>
```

## Fórmula para distribuir espacio
```
android:layout_width="0dp"
android:layout_weight="1"
```

## Visibilidad
```
android:visibility="visible"   <!-- Se ve -->
android:visibility="gone"      <!-- No se ve, no ocupa espacio -->
android:visibility="invisible" <!-- No se ve, SÍ ocupa espacio -->
```
