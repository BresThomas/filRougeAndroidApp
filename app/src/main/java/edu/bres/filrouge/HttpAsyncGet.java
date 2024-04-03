package edu.bres.filrouge;

import android.app.ProgressDialog;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Cette classe représente une tâche asynchrone pour récupérer des données JSON à partir d'une URL donnée
 * et construire un objet de type T à partir de ces données.
 * 
 * @param <T> Le type d'objet à construire à partir des données JSON.
 * 
 * @author [Bitoun, Bres, Wallner] - March 2024
 */
public class HttpAsyncGet<T>{
    private static final String TAG = "Bres, Bitoun, Wallner " + HttpAsyncGet.class.getSimpleName();    // Pour affichage en cas d'erreur
    private final Class<T> clazz;
    private List<T> itemList;
    private final HttpHandler webService;

    /**
     * Constructeur de la classe HttpAsyncGet.
     * 
     * @param url L'URL à partir de laquelle récupérer les données JSON.
     * @param clazz Le type de classe à construire à partir des données JSON.
     * @param activity L'activité qui va traiter les résultats après leur récupération.
     * @param progressDialog La boîte de dialogue de progression affichée pendant le chargement des données.
     */
    public HttpAsyncGet(String url, Class<T> clazz, PostExecuteActivity activity, ProgressDialog progressDialog) {
        super();
        webService = new HttpHandler();
        this.clazz = clazz;
        if(progressDialog != null) onPreExecute( progressDialog );
        Runnable runnable = ()->{
            doInBackGround(url);
            activity.runOnUiThread( ()-> {
                if(progressDialog != null) progressDialog.dismiss();
                activity.onPostExecute(getItemResult());
            } );
        };
        Executors.newSingleThreadExecutor().execute( runnable );
    }

    /**
     * Méthode exécutée en arrière-plan pour récupérer les données JSON à partir de l'URL spécifiée.
     * 
     * @param urlAddress L'adresse URL à partir de laquelle récupérer les données JSON.
     */
    public void doInBackGround(String urlAddress){
        // get the jsonStr to parse
        String jsonStr = webService.makeServiceCall(urlAddress);
        ObjectMapper mapper = new ObjectMapper();
        try {
            //todo:  itemList = mapper.readValue(jsonStr, new TypeReference<List<T>>(){});   was not possible
            //       the previous line provided List<Object> instead of List<T>
            //       because "l'argument List<T> dans new TypeReference<List<T>>(){} est un type générique non résolu".
            itemList = mapper.readValue(jsonStr, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Renvoie la liste des objets récupérés à partir des données JSON.
     * 
     * @return La liste d'objets construits à partir des données JSON.
     */
    public List<T> getItemResult() {
        return itemList;
    }

    /**
     * Affiche la boîte de dialogue de progression avant de commencer la récupération des données.
     * 
     * @param progressDialog La boîte de dialogue de progression à afficher.
     */
    public void onPreExecute( ProgressDialog progressDialog ) {
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * Cette classe interne gère les requêtes HTTP pour récupérer les données JSON.
     */
    static class HttpHandler { //innerClass

        /**
         * Effectue une requête HTTP GET pour récupérer les données JSON à partir de l'URL spécifiée.
         * 
         * @param reqUrl L'URL à partir de laquelle récupérer les données JSON.
         * @return La réponse sous forme de chaîne de caractères.
         */
        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl).openConnection();
                connection.setRequestMethod("GET");
                // lecture du fichier
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = convertStreamToString(inputStream);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        /**
         * Convertit un flux d'entrée en une chaîne de caractères.
         * 
         * @param inputStream Le flux d'entrée à convertir.
         * @return La chaîne de caractères résultante.
         */
        private String convertStreamToString(InputStream inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                    Log.e(TAG,line);
                }
            }
            catch (IOException e) {  e.printStackTrace();   }
            finally {
                try { inputStream.close(); } catch (IOException e) { e.printStackTrace();  }
            }
            return stringBuilder.toString();
        }
    }
}
