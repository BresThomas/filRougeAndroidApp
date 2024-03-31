package edu.bres.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Clickable {

    private final String TAG = "wallner " + getClass().getSimpleName();
    private ProduitAdapter adapter;
    private final List<ProduitInterface> produitInterfaces = new ArrayList<>(); //complete list
    private final List<ProduitInterface> displayedProduit = new ArrayList<>(); //displayed list
    //private ImageView loadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loadingAnimation = findViewById(R.id.loadingAnimation); // Initialize the loading animation ImageView

        if (displayedProduit.isEmpty()) {
            // Load products from Firebase Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("product")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("titre");
                                String url = document.getString("url_image");
                                String description = document.getString("description");
                                if (name != null && url != null && description != null) {
                                    Produit produit = new Produit(name, description, url);
                                    produitInterfaces.add(produit);
                                    displayedProduit.add(produit); // Assuming all products are initially displayed
                                }
                            }
                            initGridView(); // Initialize GridView after loading products
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    });
        } else {
            initGridView(); // Initialize GridView if products are already loaded
        }
    }

    private void initGridView() {
        GridView gridView = findViewById(R.id.gridView);

        adapter = new ProduitAdapter(displayedProduit, this, this);
        gridView.setAdapter(adapter);
    }

    void afficherLoadingAvecAnimation() {
        setContentView(R.layout.activity_loading);

        ImageView imageViewAnimation = findViewById(R.id.imageloadinganimation);
        imageViewAnimation.setBackgroundResource(R.drawable.frame_animation_list);

        // Obtenir l'AnimationDrawable à partir de l'ImageView
        AnimationDrawable animationDrawable = (AnimationDrawable) imageViewAnimation.getBackground();

        // Démarrer l'animation
        animationDrawable.start();

        // Créer un ObjectAnimator pour la rotation continue de l'ImageView
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(
                imageViewAnimation,
                "rotation",
                0f,
                360f
        );
        rotationAnimator.setDuration(3000);
        rotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        rotationAnimator.start();

        // Revenir à MainActivity après 3 secondes
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


    @Override
    public void onClicItem(int index) {
        int itemIndex = findIndexInList(index);
        Log.d(TAG, "clicked on = " + produitInterfaces.get(itemIndex).getName());
        Intent intent = new Intent(this, ProduitActivity.class);
        intent.putExtra("character", produitInterfaces.get(itemIndex));
        startActivity(intent);
    }

    private int findIndexInList(int index) {
        ProduitInterface itemToFind = displayedProduit.get(index);
        for (int i = 0; i < produitInterfaces.size(); i++) {
            if (produitInterfaces.get(i).equals(itemToFind)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onRatingBarChange(int itemIndex, float value) {
        produitInterfaces.get(findIndexInList(itemIndex)).setValue(value);
        displayedProduit.get(itemIndex).setValue(value);
        adapter.notifyDataSetChanged();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
