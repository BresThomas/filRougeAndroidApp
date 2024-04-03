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
public class ProductActivity extends AppCompatActivity {

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
        Product product = getIntent().getParcelableExtra("character");

        // Initialisation des éléments de l'interface utilisateur
        TextView productTitle = findViewById(R.id.productTitle);
        TextView productName = findViewById(R.id.productName);
        ImageView productPicture = findViewById(R.id.productPicture);
        TextView productDescription = findViewById(R.id.productDescription);
        TextView productPrice = findViewById(R.id.itemPrice);

        // Affichage des informations du produit
        productTitle.setText("Product page");
        productName.setText(product.getName());
        Picasso.get().load(product.getPicture()).into(productPicture);
        productDescription.setText(product.getDescription());
        productPrice.setText(String.valueOf(product.getPrice()));

        // Gestion du clic sur le bouton "Acheter"
        Button acheterButton = findViewById(R.id.productButtonBuy);
        acheterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité d'animation
                startActivity(new Intent(ProductActivity.this, LoadingActivity.class));
            }
        });
    }
}
