package edu.bres.filrouge;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activité pour afficher les paramètres de l'application.
 * Cette activité est accessible depuis le menu de l'application.
 * Elle permet à l'utilisateur de modifier différents réglages de l'application.
 * Elle affiche un simple message lors de sa création.
 *
 * @author [Bitoun, Bres, Wallner] - March 2024
 */
public class SettingsActivity extends AppCompatActivity {

    // TAG utilisé pour les logs
    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Afficher un message par exemple
        Toast.makeText(this, "Paramètres", Toast.LENGTH_SHORT).show();
    }
}
