package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import cz.msebera.android.httpclient.Header;

/**
 * Handler para la respuesta de "enviar invitación".
 * Si la API devuelve un objeto con más info (p.ej., gameId),
 * aquí parseamos.
 */
public class InvitarPartidaResponseHandler extends BaseHandler {

    public interface OnOk { void done(); }

    private static final String TAG = "API_INVITE";
    private final OnOk cb;

    public InvitarPartidaResponseHandler(Context ctx, OnOk cb) {
        super(ctx);
        this.cb = cb;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String raw = bodyToString(responseBody);
        Log.d(TAG, "status=" + statusCode + " body=" + raw);
        Toast.makeText(ctx, ctx.getString(R.string.invite_sent_short), Toast.LENGTH_SHORT).show();
        if (cb != null) cb.done();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, "fail " + statusCode + " body=" + bodyToString(responseBody), error);
        Toast.makeText(ctx, ctx.getString(R.string.invite_failed, statusCode), Toast.LENGTH_LONG).show();
    }
}
