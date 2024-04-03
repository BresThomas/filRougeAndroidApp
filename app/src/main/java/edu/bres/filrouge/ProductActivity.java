package edu.bres.filrouge;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

/**
 * Activité responsable de l'affichage des détails d'un produit.
 * Cette activité reçoit un objet Produit en tant qu'intention, puis affiche ses détails tels que le nom, l'image, la description et le prix.
 * Les utilisateurs peuvent également acheter le produit en appuyant sur un bouton.
 *
 * @author [Bitoun, Bres, Wallner] - March 2024
 *
 */
public class ProductActivity extends AppCompatActivity {

    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();
    private HttpAsyncGet productRef;
    private Product product;
    private float baseRating; // Note de base du produit


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
        TextView productPrice = findViewById(R.id.productPrice);
        Button productBuyButton = findViewById(R.id.productButtonAddBasket);
        RatingBar productRatingBar = findViewById(R.id.productRatingBar);

        // Affichage des informations du produit
        productTitle.setText("Détailles du produit");
        productName.setText(product.getName());
        Picasso.get().load(product.getPicture()).into(productPicture);
        productDescription.setText(product.getDescription());
        productPrice.setText(String.valueOf(product.getPrice()));

        baseRating = product.getRating();

        // Gestion du changement de note de la RatingBar
        productRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // Calcul de la nouvelle note globale
                float newRating = (baseRating + rating) / 2;
                product.setRating(newRating);
            }
        });


        // Gestion du clic sur le bouton "Acheter"
        productBuyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Démarrer l'activité d'animation
                startActivity(new Intent(ProductActivity.this, LoadingBasketActivity.class));
            }
        });

        // Animation pour tous les éléments de la vue XML
        animateViews(productTitle, productName, productPicture,productRatingBar, productDescription, productPrice, productBuyButton);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Intent intent = null;
                int id = item.getItemId();
                if (id == R.id.home) {
                    // Vous pouvez laisser cette partie vide si vous voulez rester dans MainActivity
                    // Ou vous pouvez démarrer MainActivity à nouveau si nécessaire
                    intent = new Intent(ProductActivity.this, MainActivity.class);
                } else if (id == R.id.panier) {
                    // Remplacez PanierActivity.class par le nom de votre activité pour le panier
                    intent = new Intent(ProductActivity.this, PanierActivity.class);
                }
                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            };

    private void animateViews(View... views) {
        for (View view : views) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -1000f, 0f);
            animator.setDuration(1000);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.start();
        }
    }


}
