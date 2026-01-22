package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class GetGameDetailResponseHandler extends BaseHandler {

    public interface OnLoaded {
        void onLoaded(GameDetail detail);
    }

    private static final String TAG = "API_GAME_DETAIL";
    private final OnLoaded cb;

    public GetGameDetailResponseHandler(Context ctx, OnLoaded cb) {
        super(ctx);
        this.cb = cb;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String raw = bodyToString(responseBody);
        Log.d(TAG, "status=" + statusCode + " body=" + raw);
        try {
            JsonObject obj = JsonParser.parseString(raw).getAsJsonObject();

            GameDetail detail = new GameDetail();
            detail.game = gson.fromJson(obj.get("game"), GameInfo.class);

            Type shipsType = new TypeToken<List<Ship>>() {}.getType();
            detail.ships = gson.fromJson(obj.get("ships"), shipsType);

            detail.gunfire = gson.fromJson(obj.get("gunfire"), Gunfire.class);

            if (cb != null) cb.onLoaded(detail);

        } catch (Exception e) {
            Log.e(TAG, "Error parseando detalle", e);
            Toast.makeText(ctx, ctx.getString(R.string.list_error_parse), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        Log.e(TAG, "fail " + statusCode + " body=" + bodyToString(responseBody), error);
        Toast.makeText(ctx, ctx.getString(R.string.list_error_load, statusCode), Toast.LENGTH_LONG).show();
    }
}