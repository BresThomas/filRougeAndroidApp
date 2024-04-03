package edu.bres.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PanierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoping);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Gestion du clic sur le bouton "Acheter"
        Button acheterButton = findViewById(R.id.basketBuyButton);
        acheterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité d'animation
                startActivity(new Intent(PanierActivity.this, LoadingBuyActivity.class));
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.home) {
                    // Vous pouvez laisser cette partie vide si vous voulez rester dans MainActivity
                    // Ou vous pouvez démarrer MainActivity à nouveau si nécessaire
                    intent = new Intent(PanierActivity.this, MainActivity.class);
                } else if (id == R.id.panier) {
                    // Remplacez PanierActivity.class par le nom de votre activité pour le panier
                }
                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            };
}