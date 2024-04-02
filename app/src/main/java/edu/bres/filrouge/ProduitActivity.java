package edu.bres.filrouge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProduitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        // Initialisation des éléments de l'interface utilisateur
        Produit character = getIntent().getParcelableExtra("character");
        TextView titleTextView = findViewById(R.id.title);
        TextView characterNameText = findViewById(R.id.produitName);
        ImageView characterPictureImage = findViewById(R.id.produitPicture);
        TextView characterDescriptionText = findViewById(R.id.produitDescription);

        // Affichage des informations du produit
        titleTextView.setText("Product page");
        characterNameText.setText(character.getName());
        Picasso.get().load(product.getPicture()).into(picture);
        characterDescriptionText.setText(character.getDescription());

        // Gestion du clic sur le bouton "Acheter"
        Button acheterButton = findViewById(R.id.buttonAcheter);
        acheterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité d'animation
                startActivity(new Intent(ProduitActivity.this, LoadingActivity.class));
            }
        });
    }
}
