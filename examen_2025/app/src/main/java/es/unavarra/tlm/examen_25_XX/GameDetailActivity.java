package es.unavarra.tlm.examen_25_XX;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/*
 * ============================================================================
 * GameDetailActivity - DETALLE Y ACTUALIZACIÓN DE UN PARTIDO
 * ============================================================================
 * 
 * FUNCIONALIDADES:
 * 1. Mostrar el marcador actual del partido
 * 2. Botones +1, +2, +3 para cada equipo
 * 3. Al pulsar un botón, enviar petición PUT al servidor
 * 4. Actualizar el marcador en pantalla con la respuesta
 * 5. Mostrar Toast con mensaje de éxito o error
 * 
 * PUNTUACIÓN:
 * - Mostrar detalle de un partido: 1 punto
 * - Actualizar puntuación (petición PUT): 2 puntos
 * - Actualizar resultado en pantalla: 1 punto
 * - Mostrar mensajes de error HTTP 400: 1 punto
 */
public class GameDetailActivity extends AppCompatActivity {
    
    // URL base de la API
    private static final String BASE_URL = "https://api.battleship.tatai.es/v2/exam-2025-2";
    
    // Widgets de la pantalla
    private TextView txtAwayName;
    private TextView txtHomeName;
    private TextView txtAwayScore;
    private TextView txtHomeScore;
    
    // HTTP y JSON
    private AsyncHttpClient httpClient;
    private Gson gson;
    
    // Datos del partido (recibidos del Intent)
    private String gameId;
    private String awayId;
    private String awayName;
    private String homeId;
    private String homeName;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        
        // ====================================================================
        // PASO 1: Inicializar HTTP y Gson
        // ====================================================================
        httpClient = new AsyncHttpClient();
        gson = new Gson();
        
        // ====================================================================
        // PASO 2: Obtener datos del Intent
        // ====================================================================
        /*
         * Los datos vienen del MainActivity cuando pulsamos un partido.
         * getIntent().getExtras() retorna un Bundle con los extras.
         */
        Bundle extras = getIntent().getExtras();
        
        gameId = extras.getString("gameId");
        awayId = extras.getString("awayId");
        awayName = extras.getString("awayName");
        int awayScore = extras.getInt("awayScore");
        homeId = extras.getString("homeId");
        homeName = extras.getString("homeName");
        int homeScore = extras.getInt("homeScore");
        
        // ====================================================================
        // PASO 3: Obtener referencias a los widgets
        // ====================================================================
        txtAwayName = findViewById(R.id.txt_away_name);
        txtHomeName = findViewById(R.id.txt_home_name);
        txtAwayScore = findViewById(R.id.txt_away_score);
        txtHomeScore = findViewById(R.id.txt_home_score);
        
        // Botones equipo visitante (away)
        Button btnAway1 = findViewById(R.id.btn_away_1);
        Button btnAway2 = findViewById(R.id.btn_away_2);
        Button btnAway3 = findViewById(R.id.btn_away_3);
        
        // Botones equipo local (home)
        Button btnHome1 = findViewById(R.id.btn_home_1);
        Button btnHome2 = findViewById(R.id.btn_home_2);
        Button btnHome3 = findViewById(R.id.btn_home_3);
        
        // ====================================================================
        // PASO 4: Mostrar datos iniciales
        // ====================================================================
        txtAwayName.setText(awayName);
        txtHomeName.setText(homeName);
        txtAwayScore.setText(String.valueOf(awayScore));
        txtHomeScore.setText(String.valueOf(homeScore));
        
        // ====================================================================
        // PASO 5: Configurar listeners de los botones
        // ====================================================================
        /*
         * Cada botón llama a addScore() con:
         * - teamId: UUID del equipo
         * - teamName: Nombre del equipo (para el Toast)
         * - points: Puntos a sumar (1, 2 o 3)
         */
        
        // Botones equipo visitante
        btnAway1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScore(awayId, awayName, 1);
            }
        });
        
        btnAway2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScore(awayId, awayName, 2);
            }
        });
        
        btnAway3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScore(awayId, awayName, 3);
            }
        });
        
        // Botones equipo local
        btnHome1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScore(homeId, homeName, 1);
            }
        });
        
        btnHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScore(homeId, homeName, 2);
            }
        });
        
        btnHome3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScore(homeId, homeName, 3);
            }
        });
    }
    
    /*
     * ========================================================================
     * addScore() - Enviar petición PUT para sumar puntos
     * ========================================================================
     * 
     * TIPO: PUT
     * URL: https://api.battleship.tatai.es/v2/exam-2025-2/games/{gameId}/score
     * 
     * BODY (JSON):
     * {
     *   "teamId": "uuid-del-equipo",
     *   "score": 2
     * }
     * 
     * RESPUESTA EXITOSA (HTTP 200):
     * {
     *   "game": { ... partido actualizado ... }
     * }
     * 
     * ERRORES (HTTP 400):
     * { "code": "TEAM_ID_REQUIRED" }
     * { "code": "INVALID_TEAM_ID" }
     * { "code": "SCORE_REQUIRED" }
     * { "code": "SCORE_INVALID" }
     * 
     * ERROR (HTTP 404): Partido no encontrado
     * 
     * @param teamId   - UUID del equipo
     * @param teamName - Nombre del equipo (para el Toast)
     * @param points   - Puntos a sumar (1, 2 o 3)
     */
    private void addScore(String teamId, String teamName, int points) {
        
        // Construir la URL con el ID del partido
        String url = BASE_URL + "/games/" + gameId + "/score";
        
        // ====================================================================
        // Crear el objeto de petición y convertirlo a JSON
        // ====================================================================
        ScoreRequest request = new ScoreRequest(teamId, points);
        String jsonBody = gson.toJson(request);
        
        // ====================================================================
        // Crear StringEntity para el body de la petición
        // ====================================================================
        /*
         * StringEntity envuelve el JSON para enviarlo en el body.
         * Importante: especificar charset UTF-8 para caracteres especiales.
         */
        StringEntity entity;
        try {
            entity = new StringEntity(jsonBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, R.string.error_network, Toast.LENGTH_SHORT).show();
            return;
        }
        
        // ====================================================================
        // Enviar petición PUT
        // ====================================================================
        /*
         * client.put() para peticiones PUT
         * 
         * Parámetros:
         * - context: this (la Activity)
         * - url: la URL completa
         * - entity: el body de la petición
         * - contentType: "application/json"
         * - handler: callback para manejar la respuesta
         */
        httpClient.put(
            this,                      // Context
            url,                       // URL
            entity,                    // Body
            "application/json",        // Content-Type
            new ScoreResponseHandler(teamName, points)  // Handler
        );
    }
    
    /*
     * ========================================================================
     * ScoreResponseHandler - Manejador de respuesta para la petición PUT
     * ========================================================================
     * 
     * Clase interna que extiende AsyncHttpResponseHandler.
     * Guardamos teamName y points para usarlos en el Toast.
     */
    private class ScoreResponseHandler extends AsyncHttpResponseHandler {
        
        private String teamName;
        private int points;
        
        public ScoreResponseHandler(String teamName, int points) {
            this.teamName = teamName;
            this.points = points;
        }
        
        /*
         * onSuccess() - Respuesta exitosa (HTTP 200)
         * 
         * 1. Parsear la respuesta para obtener el partido actualizado
         * 2. Actualizar los marcadores en pantalla
         * 3. Mostrar Toast de éxito
         */
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            
            // Parsear respuesta
            String json = new String(responseBody);
            GameResponse response = gson.fromJson(json, GameResponse.class);
            Game game = response.getGame();
            
            // ================================================================
            // Actualizar marcadores en pantalla
            // ================================================================
            /*
             * REQUISITO: "Actualizar el resultado del partido sin realizar
             * una nueva petición, lo haremos usando la misma respuesta"
             */
            txtAwayScore.setText(String.valueOf(game.getAway().getScore()));
            txtHomeScore.setText(String.valueOf(game.getHome().getScore()));
            
            // ================================================================
            // Mostrar Toast de éxito
            // ================================================================
            /*
             * REQUISITO: "X punto(s) más para [equipo]"
             * - "1 punto más para Memphis Grizzlies"
             * - "2 puntos más para Memphis Grizzlies"
             * - "3 puntos más para Memphis Grizzlies"
             */
            String message;
            if (points == 1) {
                // Singular: "1 punto más para..."
                message = getString(R.string.points_added_singular, points, teamName);
            } else {
                // Plural: "2 puntos más para..." o "3 puntos más para..."
                message = getString(R.string.points_added_plural, points, teamName);
            }
            
            Toast.makeText(GameDetailActivity.this, message, Toast.LENGTH_SHORT).show();
        }
        
        /*
         * onFailure() - Error (HTTP 400, 404 o error de red)
         * 
         * REQUISITO: "Mostrar mensajes para todos los posibles errores HTTP 400"
         */
        @Override
        public void onFailure(int statusCode, Header[] headers, 
                             byte[] responseBody, Throwable error) {
            
            // ================================================================
            // HTTP 404 - Partido no encontrado
            // ================================================================
            if (statusCode == 404) {
                Toast.makeText(GameDetailActivity.this, 
                    R.string.error_game_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            
            // ================================================================
            // HTTP 400 - Error de validación
            // ================================================================
            if (statusCode == 400 && responseBody != null) {
                String json = new String(responseBody);
                ErrorResponse errorResponse = gson.fromJson(json, ErrorResponse.class);
                
                // Mostrar mensaje según el código de error
                String errorMessage = getErrorMessage(errorResponse.getCode());
                Toast.makeText(GameDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                return;
            }
            
            // ================================================================
            // Otros errores (red, servidor, etc.)
            // ================================================================
            Toast.makeText(GameDetailActivity.this, 
                R.string.error_network, Toast.LENGTH_SHORT).show();
        }
    }
    
    /*
     * ========================================================================
     * getErrorMessage() - Convertir código de error a mensaje legible
     * ========================================================================
     * 
     * CÓDIGOS DE ERROR (del enunciado):
     * - TEAM_ID_REQUIRED: El identificador de equipo es obligatorio
     * - INVALID_TEAM_ID: El equipo no juega este partido
     * - SCORE_REQUIRED: Los puntos anotados son obligatorios
     * - SCORE_INVALID: Cantidad de puntos no válida
     */
    private String getErrorMessage(String code) {
        switch (code) {
            case "TEAM_ID_REQUIRED":
                return getString(R.string.error_team_id_required);
                
            case "INVALID_TEAM_ID":
                return getString(R.string.error_invalid_team_id);
                
            case "SCORE_REQUIRED":
                return getString(R.string.error_score_required);
                
            case "SCORE_INVALID":
                return getString(R.string.error_score_invalid);
                
            default:
                return getString(R.string.error_network);
        }
    }
}
