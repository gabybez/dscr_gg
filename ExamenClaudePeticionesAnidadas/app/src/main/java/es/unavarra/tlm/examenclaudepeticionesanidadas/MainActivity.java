package es.unavarra.tlm.examenclaudepeticionesanidadas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/*
 * ============================================================================
 * MainActivity - LISTADO DE LIBROS
 * ============================================================================
 */
public class MainActivity extends AppCompatActivity {

    //URL de la API

    private static final String BOOKS_URL = "https://api.library.example.com/v1/books";
    //Widgets
    private ListView listView;
    //HTTP y JSON
    private AsyncHttpClient httpClient;
    private Gson gson;
    //Lista de libros, los guardamos para acceder con el click
    private List<Book> booksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httpClient = new AsyncHttpClient();
        gson = new Gson();
        listView = findViewById(R.id.list_books);

        // Click en libro -> abrir detalle
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Obtener el libro clickado
                Book book = booksList.get(position);
                //Abrir la DetailActivity pasandole los datos necesarios (el id del libro, necesario para el get posterior)
                Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
                intent.putExtra("bookId", book.getId());
                startActivity(intent);
            }
        });

        fetchBooks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchBooks();
    }

    private void fetchBooks() {
        httpClient.get(BOOKS_URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String json = new String(responseBody);
                BooksResponse response = gson.fromJson(json, BooksResponse.class);
                booksList = response.getBooks();

                BookAdapter adapter = new BookAdapter(MainActivity.this, booksList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this,
                        R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
