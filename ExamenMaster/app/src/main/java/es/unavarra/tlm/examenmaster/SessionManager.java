package es.unavarra.tlm.examenmaster;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "ExamenPrefs";
    private static final String KEY_TOKEN = "token";

    // Metodo para recuperar el token
    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_TOKEN, null); // Retorna null si no existe
    }

    // Metodo para guardar el token
    public static void setToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit(); // Los apuntes recalcan el uso de commit()
    }

    // Metodo para borrar_todo (Logout)
    public static void clear(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
    }
}