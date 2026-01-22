package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.nio.charset.StandardCharsets;

/**
 * Clase base para evitar repetir utilidades en todos los handlers:
 * - Referencia a Context (para Toasts y navegación)
 * - Instancia Gson
 * - Conversión de byte[] → String (cómodo para parsear)
 */
public abstract class BaseHandler extends AsyncHttpResponseHandler {
    protected final Context ctx;
    protected final Gson gson = new Gson();

    public BaseHandler(Context ctx) { this.ctx = ctx; }

    protected String bodyToString(byte[] body) {
        return (body != null) ? new String(body, StandardCharsets.UTF_8) : "";
    }
}
