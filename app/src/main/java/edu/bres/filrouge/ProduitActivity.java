package edu.bres.filrouge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

/**
 * Activité responsable de l'affichage des détails d'un produit.
 * Cette activité reçoit un objet Produit en tant qu'intention, puis affiche ses détails tels que le nom, l'image, la description et le prix.
 * Les utilisateurs peuvent également acheter le produit en appuyant sur un bouton.
 */
public class ProduitActivity extends AppCompatActivity {

    /**
     * Méthode appelée lors de la création de l'activité.
     * Elle récupère l'objet Produit de l'intention, initialise les éléments de l'interface utilisateur et configure les actions des boutons.
     * @param savedInstanceState L'état de l'activité enregistré précédemment, le cas échéant.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // Récupération de l'objet Produit de l'intention
        Produit product = getIntent().getParcelableExtra("character");

        // Initialisation des éléments de l'interface utilisateur
        TextView titleTextView = findViewById(R.id.title);
        TextView characterNameText = findViewById(R.id.produitName);
        ImageView characterPictureImage = findViewById(R.id.produitPicture);
        TextView characterDescriptionText = findViewById(R.id.produitDescription);
        TextView productPrice = findViewById(R.id.produitPrice);

        // Affichage des informations du produit
        titleTextView.setText("Product page");
        characterNameText.setText(product.getName());
        Picasso.get().load(product.getPicture()).into(characterPictureImage);
        characterDescriptionText.setText(product.getDescription());
        productPrice.setText(String.valueOf(product.getPrice()));

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
