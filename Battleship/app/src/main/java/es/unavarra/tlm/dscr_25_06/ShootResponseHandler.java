// ShootResponseHandler.java
package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cz.msebera.android.httpclient.Header;

/**
 * Respuesta esperada (200):
 * { shot: Shot, game: GameInfo, ships: Ship[], gunfire: Gunfire }
 */
public class ShootResponseHandler extends BaseHandler {

    public interface OnShot { void onShot(Shot shot, GameInfo game, Gunfire gunfire); }

    private static final String TAG = "API_SHOOT";
    private final OnShot cb;

    public ShootResponseHandler(Context ctx, OnShot cb) {
        super(ctx);
        this.cb = cb;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String raw = bodyToString(responseBody);
        Log.d(TAG, "status=" + statusCode + " body=" + raw);
        try {
            JsonObject obj = JsonParser.parseString(raw).getAsJsonObject();
            Shot shot = gson.fromJson(obj.get("shot"), Shot.class);
            GameInfo game = gson.fromJson(obj.get("game"), GameInfo.class);
            Gunfire gun = gson.fromJson(obj.get("gunfire"), Gunfire.class);

            Toast.makeText(ctx, ctx.getString(R.string.msg_shot_ok, shot.result), Toast.LENGTH_SHORT).show();
            if (cb != null) cb.onShot(shot, game, gun);
        } catch (Exception e) {
            Toast.makeText(ctx, ctx.getString(R.string.err_parseando_shoot), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, "fail " + statusCode + " body=" + bodyToString(responseBody), error);
        Toast.makeText(ctx, ctx.getString(R.string.err_shoot, statusCode), Toast.LENGTH_LONG).show();
    }
}
