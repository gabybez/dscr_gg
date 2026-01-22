# 📊 GUÍA RÁPIDA DE ORDENACIÓN EN ANDROID

## 1. MÉTODO BÁSICO: Collections.sort()

```java
// Ordenar una lista con un Comparator
Collections.sort(miLista, new Comparator<MiObjeto>() {
    @Override
    public int compare(MiObjeto o1, MiObjeto o2) {
        // Retornar:
        // - Negativo si o1 va ANTES que o2
        // - Positivo si o1 va DESPUÉS que o2
        // - Cero si son iguales
    }
});

// DESPUÉS de ordenar, actualizar el ListView
adapter.notifyDataSetChanged();
```

---

## 2. ORDENAR NÚMEROS

### Ascendente (menor a mayor): 1, 2, 3, 4, 5
```java
Collections.sort(productos, new Comparator<Product>() {
    @Override
    public int compare(Product p1, Product p2) {
        return Float.compare(p1.getPrice(), p2.getPrice());
        // O también: return (int)(p1.getPrice() - p2.getPrice());
    }
});
```

### Descendente (mayor a menor): 5, 4, 3, 2, 1
```java
Collections.sort(productos, new Comparator<Product>() {
    @Override
    public int compare(Product p1, Product p2) {
        // INVERTIR: p2 primero
        return Float.compare(p2.getPrice(), p1.getPrice());
    }
});
```

### Comparadores para diferentes tipos numéricos:
```java
// int
return Integer.compare(p1.getCantidad(), p2.getCantidad());

// float
return Float.compare(p1.getPrecio(), p2.getPrecio());

// double
return Double.compare(p1.getValor(), p2.getValor());

// long
return Long.compare(p1.getId(), p2.getId());
```

---

## 3. ORDENAR STRINGS (TEXTOS)

### Alfabético A-Z (ascendente)
```java
Collections.sort(productos, new Comparator<Product>() {
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getName().compareTo(p2.getName());
    }
});
```

### Alfabético Z-A (descendente)
```java
Collections.sort(productos, new Comparator<Product>() {
    @Override
    public int compare(Product p1, Product p2) {
        // INVERTIR: p2 primero
        return p2.getName().compareTo(p1.getName());
    }
});
```

### Ignorando mayúsculas/minúsculas
```java
Collections.sort(productos, new Comparator<Product>() {
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getName().compareToIgnoreCase(p2.getName());
    }
});
```

---

## 4. ORDENAR FECHAS

### Más reciente primero (descendente)
```java
Collections.sort(tareas, new Comparator<Task>() {
    @Override
    public int compare(Task t1, Task t2) {
        // getTime() convierte Date a long (milisegundos)
        return Long.compare(t2.getCreatedAt().getTime(), t1.getCreatedAt().getTime());
    }
});
```

### Más antiguo primero (ascendente)
```java
Collections.sort(tareas, new Comparator<Task>() {
    @Override
    public int compare(Task t1, Task t2) {
        return Long.compare(t1.getCreatedAt().getTime(), t2.getCreatedAt().getTime());
    }
});
```

---

## 5. ORDENAR BOOLEANOS

### Primero los true (completados/activos)
```java
Collections.sort(tareas, new Comparator<Task>() {
    @Override
    public int compare(Task t1, Task t2) {
        // Boolean.compare: false < true
        // Invertimos para que true vaya primero
        return Boolean.compare(t2.isCompleted(), t1.isCompleted());
    }
});
```

### Primero los false (pendientes)
```java
Collections.sort(tareas, new Comparator<Task>() {
    @Override
    public int compare(Task t1, Task t2) {
        return Boolean.compare(t1.isCompleted(), t2.isCompleted());
    }
});
```

---

## 6. ORDENAR POR TAMAÑO DE LISTA

### Mayor número de elementos primero
```java
Collections.sort(productos, new Comparator<Product>() {
    @Override
    public int compare(Product p1, Product p2) {
        return Integer.compare(
            p2.getComponentIds().size(), 
            p1.getComponentIds().size()
        );
    }
});
```

---

## 7. ORDENAR POR VALOR CALCULADO

### Ejemplo: Ordenar por suma de precios de componentes
```java
Collections.sort(productsList, new Comparator<Product>() {
    @Override
    public int compare(Product p1, Product p2) {
        float price1 = calculateTotalPrice(p1);
        float price2 = calculateTotalPrice(p2);
        return Float.compare(price2, price1); // Mayor a menor
    }
});

// Método auxiliar
private float calculateTotalPrice(Product product) {
    float total = 0;
    for (String compId : product.getComponentIds()) {
        for (Component comp : componentsList) {
            if (comp.getId().equals(compId)) {
                total += comp.getPrice();
                break;
            }
        }
    }
    return total;
}
```

---

## 8. ORDENACIÓN MÚLTIPLE (por varios campos)

### Primero por estado, luego por fecha
```java
Collections.sort(tareas, new Comparator<Task>() {
    @Override
    public int compare(Task t1, Task t2) {
        // Primero comparar por completado (pendientes primero)
        int result = Boolean.compare(t1.isCompleted(), t2.isCompleted());
        
        // Si son iguales, comparar por fecha (más reciente primero)
        if (result == 0) {
            result = Long.compare(
                t2.getCreatedAt().getTime(), 
                t1.getCreatedAt().getTime()
            );
        }
        
        return result;
    }
});
```

---

## 9. SPINNER PARA ELEGIR ORDENACIÓN

### strings.xml
```xml
<string-array name="sort_options">
    <item>Precio: Mayor a menor</item>
    <item>Precio: Menor a mayor</item>
    <item>Nombre: A-Z</item>
    <item>Nombre: Z-A</item>
</string-array>
```

### Layout
```xml
<Spinner
    android:id="@+id/spinner_sort"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

### Java - Configurar Spinner
```java
Spinner spinner = findViewById(R.id.spinner_sort);

// Crear adapter desde string-array
ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
    this,
    R.array.sort_options,
    android.R.layout.simple_spinner_item
);
spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(spinnerAdapter);

// Listener cuando cambia la selección
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sortProducts(position);
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
});
```

### Java - Método de ordenación
```java
private void sortProducts(int option) {
    switch (option) {
        case 0: // Precio mayor a menor
            Collections.sort(lista, (p1, p2) -> 
                Float.compare(p2.getPrice(), p1.getPrice()));
            break;
        case 1: // Precio menor a mayor
            Collections.sort(lista, (p1, p2) -> 
                Float.compare(p1.getPrice(), p2.getPrice()));
            break;
        case 2: // Nombre A-Z
            Collections.sort(lista, (p1, p2) -> 
                p1.getName().compareTo(p2.getName()));
            break;
        case 3: // Nombre Z-A
            Collections.sort(lista, (p1, p2) -> 
                p2.getName().compareTo(p1.getName()));
            break;
    }
    
    // Actualizar ListView
    adapter.notifyDataSetChanged();
}
```

---

## 10. RESUMEN RÁPIDO

| Ordenación | Código |
|------------|--------|
| Números ASC | `Float.compare(p1.getX(), p2.getX())` |
| Números DESC | `Float.compare(p2.getX(), p1.getX())` |
| String A-Z | `p1.getName().compareTo(p2.getName())` |
| String Z-A | `p2.getName().compareTo(p1.getName())` |
| Fecha reciente | `Long.compare(p2.getDate().getTime(), p1.getDate().getTime())` |
| Boolean true primero | `Boolean.compare(p2.isX(), p1.isX())` |

### REGLA FÁCIL:
- **ASC (menor a mayor)**: `compare(p1, p2)` → p1 primero
- **DESC (mayor a menor)**: `compare(p2, p1)` → p2 primero (INVERTIR)

### SIEMPRE DESPUÉS:
```java
adapter.notifyDataSetChanged();
```
