package edu.bres.filrouge;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Afficher un message par exemple
        Toast.makeText(this, "Paramètres", Toast.LENGTH_SHORT).show();
    }
}
