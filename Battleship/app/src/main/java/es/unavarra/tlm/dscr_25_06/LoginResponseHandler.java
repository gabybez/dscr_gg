package es.unavarra.tlm.dscr_25_06;

import android.content.Intent;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

/**
 * Gestiona la respuesta del login (PUT /v2/session):
 * - onSuccess: parsea {user, session}, guarda sesión y navega a Main.
 * - onFailure: mapea el code de error del backend a un texto de strings.xml.
 */
public class LoginResponseHandler extends BaseHandler {
    public LoginResponseHandler(android.content.Context ctx) { super(ctx); }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        try {
            AuthBundle resp = gson.fromJson(bodyToString(responseBody), AuthBundle.class);
            if (resp != null && resp.getSession() != null && resp.getSession().getToken() != null) {
                SessionManager.save(ctx, resp);
                // Entrar a Main y limpiar back stack (no volver a Login con "atrás")
                Intent i = new Intent(ctx, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ctx.startActivity(i);
            } else {
                Toast.makeText(ctx, ctx.getString(R.string.err_resp_login_invalida), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(ctx, ctx.getString(R.string.err_parseando_login), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        try {
            ApiError err = gson.fromJson(bodyToString(responseBody), ApiError.class);
            String msg;
            if (err != null && err.code != null) {
                switch (err.code) {
                    case "INVALID_USERNAME":    msg = ctx.getString(R.string.err_invalid_username); break;
                    case "INVALID_PASSWORD":    msg = ctx.getString(R.string.err_invalid_password); break;
                    case "INVALID_CREDENTIALS": msg = ctx.getString(R.string.err_invalid_credentials); break;
                    default:                    msg = ctx.getString(R.string.err_login_fallido); break;
                }
            } else {
                msg = ctx.getString(R.string.err_login_fallido);
            }
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(ctx, ctx.getString(R.string.err_login_fallido), Toast.LENGTH_LONG).show();
        }
    }
}
