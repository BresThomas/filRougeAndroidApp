package edu.bres.filrouge;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Cette activité affiche une animation de chargement pendant quelques secondes
 * puis affiche un message toast indiquant qu'un article a été ajouté dans le panier.
 * Une fois le message affiché, l'activité se termine.
 * 
 * <p>La classe LoadingActivity est une activité qui affiche une animation de chargement
 * 
 * @author [Bitoun, Bres, Wallner] - March 2024
 */
public class LoadingActivity extends AppCompatActivity {

    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Récupération de l'ImageView pour l'animation
        ImageView imageViewAnimation = findViewById(R.id.pictureloadinganimation);

        // Configuration de l'animation à partir du drawable
        imageViewAnimation.setBackgroundResource(R.drawable.loading_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageViewAnimation.getBackground();
        animationDrawable.start();

        // Affichage d'un message toast après un délai de 4 secondes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoadingActivity.this, "Article ajouté dans le panier", Toast.LENGTH_SHORT).show();
                finish(); // Fin de l'activité après l'affichage du toast
            }
        }, 4000); // Délai de 4 secondes
    }
}
