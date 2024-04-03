package edu.bres.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Activité pour afficher le panier de l'utilisateur
 * Cette activité affiche les articles sélectionnés par l'utilisateur pour l'achat
 * Elle implémente également la navigation en bas de l'écran
 *
 * @author [Bitoun, Bres, Wallner] - March 2024
 */
public class PanierActivity extends AppCompatActivity {

    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

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

    /**
     * Gestionnaire d'événements pour la navigation inférieure.
     * Cet écouteur est déclenché lorsqu'un élément de la barre de navigation inférieure est sélectionné.
     * Il permet de naviguer entre les différentes activités de l'application.
     */
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
