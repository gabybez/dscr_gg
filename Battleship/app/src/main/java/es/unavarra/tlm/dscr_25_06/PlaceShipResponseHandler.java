// PlaceShipResponseHandler.java
package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import cz.msebera.android.httpclient.Header;

/**
 * Respuesta esperada (200):
 * { game: GameInfo, ships: Ship[] }
 */
public class PlaceShipResponseHandler extends BaseHandler {

    public interface OnPlaced { void onPlaced(GameInfo game, List<Ship> ships); }

    private static final String TAG = "API_PLACE_SHIP";
    private final OnPlaced cb;

    public PlaceShipResponseHandler(Context ctx, OnPlaced cb) {
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

            Type listType = new TypeToken<List<Ship>>(){}.getType();
            List<Ship> ships = gson.fromJson(obj.get("ships"), listType);

            Toast.makeText(ctx, ctx.getString(R.string.msg_ship_colocado), Toast.LENGTH_SHORT).show();
            if (cb != null) cb.onPlaced(gi, ships);
        } catch (Exception e) {
            Toast.makeText(ctx, ctx.getString(R.string.err_parseando_place), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, "fail " + statusCode + " body=" + bodyToString(responseBody), error);
        Toast.makeText(ctx, ctx.getString(R.string.err_place_ship, statusCode), Toast.LENGTH_LONG).show();
    }
}
