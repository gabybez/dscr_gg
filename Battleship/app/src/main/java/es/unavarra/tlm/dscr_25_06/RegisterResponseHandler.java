package es.unavarra.tlm.dscr_25_06;

import android.content.Intent;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

/**
 * Gestiona la respuesta del registro (PUT /v2/user):
 * - onSuccess: te deja ya logueado (session viene en la respuesta) y entra a Main.
 * - onFailure: mapea códigos 400 del backend a strings.
 */
public class RegisterResponseHandler extends BaseHandler {
    public RegisterResponseHandler(android.content.Context ctx) { super(ctx); }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        try {
            AuthBundle resp = gson.fromJson(bodyToString(responseBody), AuthBundle.class);
            if (resp != null && resp.getSession() != null && resp.getSession().getToken() != null) {
                SessionManager.save(ctx, resp);
                Toast.makeText(ctx, ctx.getString(R.string.msg_registro_ok), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ctx, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ctx.startActivity(i);
            } else {
                Toast.makeText(ctx, ctx.getString(R.string.err_resp_registro_invalida), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(ctx, ctx.getString(R.string.err_parseando_registro), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        try {
            ApiError err = gson.fromJson(bodyToString(responseBody), ApiError.class);
            String msg;
            if (err != null && err.code != null) {
                switch (err.code) {
                    case "INVALID_USERNAME":       msg = ctx.getString(R.string.err_invalid_username); break;
                    case "INVALID_PASSWORD":       msg = ctx.getString(R.string.err_invalid_password); break;
                    case "USERNAME_NOT_AVAILABLE": msg = ctx.getString(R.string.err_username_not_available); break;
                    default:                       msg = ctx.getString(R.string.err_registro_fallido); break;
                }
            } else {
                msg = ctx.getString(R.string.err_registro_fallido);
            }
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(ctx, ctx.getString(R.string.err_registro_fallido), Toast.LENGTH_LONG).show();
        }
    }
}
