package es.unavarra.tlm.examenclaudepeticionesanidadas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/*
 * ============================================================================
 * BookDetailActivity - DETALLE DEL LIBRO CON PETICIÓN ANIDADA
 * ============================================================================
 *
 * FLUJO DE PETICIONES ANIDADAS:
 *
 * 1. onCreate() llama a fetchBookDetail()
 * 2. fetchBookDetail() hace GET /books/{bookId}
 * 3. En el onSuccess de fetchBookDetail():
 *    - Mostramos la info del libro
 *    - Obtenemos el authorId de la respuesta
 *    - Llamamos a fetchAuthor(authorId)  <-- PETICIÓN ANIDADA
 * 4. fetchAuthor() hace GET /authors/{authorId}
 * 5. En el onSuccess de fetchAuthor():
 *    - Mostramos la info del autor
 *
 * IMPORTANTE:
 * La segunda petición (autor) se hace DENTRO del onSuccess de la primera (libro)
 * porque necesitamos el authorId que viene en la respuesta del libro.
 */
public class BookDetailActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.library.example.com/v1";

    // Widgets
    private TextView txtTitle;
    private TextView txtYear;
    private TextView txtPages;
    private TextView txtStatus;
    private TextView txtAuthorName;
    private TextView txtAuthorInfo;
    private Button btnBorrow;

    // HTTP y JSON
    private AsyncHttpClient httpClient;
    private Gson gson;

    // Datos
    private String bookId;
    private BookDetail currentBook;  // Guardamos para el préstamo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Inicializar
        httpClient = new AsyncHttpClient();
        gson = new Gson();

        // Obtener bookId del Intent
        bookId = getIntent().getExtras().getString("bookId");

        // Referencias a widgets
        txtTitle = findViewById(R.id.txt_title);
        txtYear = findViewById(R.id.txt_year);
        txtPages = findViewById(R.id.txt_pages);
        txtStatus = findViewById(R.id.txt_status);
        txtAuthorName = findViewById(R.id.txt_author_name);
        txtAuthorInfo = findViewById(R.id.txt_author_info);
        btnBorrow = findViewById(R.id.btn_borrow);

        // Listener del botón préstamo
        btnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestBorrow();
            }
        });

        // ====================================================================
        // PRIMERA PETICIÓN: Obtener detalle del libro
        // ====================================================================
        fetchBookDetail();
    }

    /*
     * ========================================================================
     * PRIMERA PETICIÓN: GET /books/{bookId}
     * ========================================================================
     *
     * Obtiene los detalles del libro.
     * En el onSuccess, DESPUÉS de mostrar los datos, llamamos a fetchAuthor()
     */
    private void fetchBookDetail() {

        String url = BASE_URL + "/books/" + bookId;

        httpClient.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                // Parsear respuesta
                String json = new String(responseBody);
                BookDetailResponse response = gson.fromJson(json, BookDetailResponse.class);
                currentBook = response.getBook();

                // ============================================================
                // MOSTRAR INFORMACIÓN DEL LIBRO
                // ============================================================
                txtTitle.setText(currentBook.getTitle());
                txtYear.setText(getString(R.string.year_label, currentBook.getYear()));
                txtPages.setText(getString(R.string.pages_label, currentBook.getPages()));

                // Estado
                String statusText = currentBook.getAvailable() ?
                        getString(R.string.status_available) : getString(R.string.status_no_available);
                txtStatus.setText(getString(R.string.status_label, statusText));

                // Mostrar botón solo si está disponible
                if (currentBook.getAvailable()) {
                    btnBorrow.setVisibility(View.VISIBLE);
                } else {
                    btnBorrow.setVisibility(View.GONE);
                }

                // ============================================================
                // PETICIÓN ANIDADA: Obtener info del autor
                // ============================================================
                /*
                 * IMPORTANTE: Esta llamada está DENTRO del onSuccess
                 * porque necesitamos el authorId que acabamos de recibir.
                 *
                 * Esta es la clave de las peticiones anidadas:
                 * No podemos hacer las dos peticiones en paralelo porque
                 * la segunda depende de un dato de la primera.
                 */
                String authorId = currentBook.getAuthorId();
                fetchAuthor(authorId);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                Toast.makeText(BookDetailActivity.this,
                        R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
     * ========================================================================
     * SEGUNDA PETICIÓN (ANIDADA): GET /authors/{authorId}
     * ========================================================================
     *
     * Se llama desde el onSuccess de fetchBookDetail().
     * Obtiene la información del autor usando el authorId del libro.
     */
    private void fetchAuthor(String authorId) {

        String url = BASE_URL + "/authors/" + authorId;

        httpClient.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                // Parsear respuesta
                String json = new String(responseBody);
                AuthorResponse response = gson.fromJson(json, AuthorResponse.class);
                Author author = response.getAuthor();

                // ============================================================
                // MOSTRAR INFORMACIÓN DEL AUTOR
                // ============================================================
                txtAuthorName.setText(author.getName());

                // Formato: "España (1547-1616)"
                String lifespan = getString(R.string.author_lifespan,
                        author.getCountry(),
                        author.getBirthYear(),
                        author.getDeathYear());
                txtAuthorInfo.setText(lifespan);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                // Si falla la petición del autor, mostramos mensaje pero
                // no cerramos la pantalla (ya tenemos info del libro)
                txtAuthorName.setText("Autor desconocido");
                txtAuthorInfo.setText("");
            }
        });
    }

    /*
     * ========================================================================
     * PETICIÓN POST: Solicitar préstamo
     * ========================================================================
     */
    private void requestBorrow() {

        String url = BASE_URL + "/books/" + bookId + "/borrow";

        // POST sin body (el enunciado dice que no requiere body)
        // Enviamos un StringEntity vacío
        StringEntity entity = new StringEntity("{}", "UTF-8");

        httpClient.post(this, url, entity, "application/json",
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(BookDetailActivity.this,
                                R.string.loan_success, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers,
                                          byte[] responseBody, Throwable error) {

                        if (statusCode == 400 && responseBody != null) {
                            String json = new String(responseBody);
                            ErrorResponse errorResponse = gson.fromJson(json, ErrorResponse.class);

                            if ("BOOK_NOT_AVAILABLE".equals(errorResponse.getCode())) {
                                Toast.makeText(BookDetailActivity.this,
                                        R.string.error_not_available, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        Toast.makeText(BookDetailActivity.this,
                                R.string.error_network, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
