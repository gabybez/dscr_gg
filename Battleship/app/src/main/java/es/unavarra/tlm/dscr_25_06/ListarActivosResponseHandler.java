package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Handler para GET /v2/game/active
 * El spec dice que devuelve: { "games": [GameInfo, ...] }
 */
public class ListarActivosResponseHandler extends BaseHandler {

    public interface OnGames { void onResult(List<GameInfo> games); }

    private static final String TAG = "API_ACTIVE";
    private final OnGames cb;

    public ListarActivosResponseHandler(Context ctx, OnGames cb) {
        super(ctx);
        this.cb = cb;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String raw = bodyToString(responseBody);
        Log.d(TAG, "status=" + statusCode + " body=" + raw);
        try {
            JsonObject obj = JsonParser.parseString(raw).getAsJsonObject();
            Type t = new TypeToken<List<GameInfo>>(){}.getType();
            List<GameInfo> all = gson.fromJson(obj.get("games"), t);
            // Nunca null hacia arriba:
            cb.onResult(all != null ? all : new ArrayList<>());
        } catch (Exception e) {
            Toast.makeText(ctx, ctx.getString(R.string.err_listar_invitaciones), Toast.LENGTH_LONG).show();
            cb.onResult(new ArrayList<>());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, "fail " + statusCode + " body=" + bodyToString(responseBody), error);
        Toast.makeText(ctx, ctx.getString(R.string.err_listar_invitaciones) + " ("+statusCode+")", Toast.LENGTH_LONG).show();
        cb.onResult(new ArrayList<>());
    }
}
