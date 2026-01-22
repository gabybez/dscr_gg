package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREFS_NAME = "battleship_prefs";
    private static final String KEY_TOKEN = "session_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";

    /** Guarda sesión completa desde AuthBundle (usado por LoginResponseHandler) */
    public static void save(Context ctx, AuthBundle bundle) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (bundle.getSession() != null) {
            editor.putString(KEY_TOKEN, bundle.getSession().getToken());
        }
        if (bundle.getUser() != null) {
            editor.putLong(KEY_USER_ID, bundle.getUser().getId());
            editor.putString(KEY_USERNAME, bundle.getUser().getUsername());
        }
        editor.apply();
    }

    /** Guarda sesión con datos separados */
    public static void saveSession(Context ctx, String token, long userId, String username) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString(KEY_TOKEN, token)
                .putLong(KEY_USER_ID, userId)
                .putString(KEY_USERNAME, username)
                .apply();
    }

    public static void saveToken(Context ctx, String token) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public static String getToken(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_TOKEN, null);
    }

    public static long getUserId(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getLong(KEY_USER_ID, -1);
    }

    public static String getUsername(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USERNAME, null);
    }

    public static void clear(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}