package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cz.msebera.android.httpclient.Header;

/**
 * Respuesta para DELETE: 200 y { "game": GameInfo }
 */
public class RechazarInvitacionResponseHandler extends BaseHandler {

    public interface OnRejected { void onRejected(GameInfo game); }

    private static final String TAG = "API_REJECT";
    private final OnRejected cb;

    public RechazarInvitacionResponseHandler(Context ctx, OnRejected cb) {
        super(ctx);
        this.cb = cb;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String raw = bodyToString(responseBody);
        Log.d(TAG, "status=" + statusCode + " body=" + raw);
        try {
            JsonObject obj = JsonParser.parseString(raw).getAsJsonObject();
            GameInfo gi = gson.fromJson(obj.get("game"), GameInfo.class);
            Toast.makeText(ctx, ctx.getString(R.string.msg_rechazada), Toast.LENGTH_SHORT).show();
            if (cb != null) cb.onRejected(gi);
        } catch (Exception e) {
            Toast.makeText(ctx, ctx.getString(R.string.err_parseando_rechazar), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, "fail " + statusCode + " body=" + bodyToString(responseBody), error);
        Toast.makeText(ctx, ctx.getString(R.string.reject_failed, statusCode), Toast.LENGTH_LONG).show();
    }
}
