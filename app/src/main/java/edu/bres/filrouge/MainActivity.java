package edu.bres.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.GridView;
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

    @Override
    public void onClicItem(int index) {
        int itemIndex = findIndexInList(index);
        Log.d(TAG, "clicked on = " + produitInterfaces.get(itemIndex).getName());

        // Afficher l'animation de chargement
        //loadingAnimation.setVisibility(View.VISIBLE);

        // Créer un ObjectAnimator pour déplacer l'animation de droite à centre
        //ObjectAnimator animator = ObjectAnimator.ofFloat(
                //loadingAnimation,
                //"translationX",
                //loadingAnimation.getTranslationX(),
                //(getResources().getDisplayMetrics().widthPixels / 2 - loadingAnimation.getWidth() / 2)
        //);

        //animator.setDuration(1000); // Durée de l'animation en millisecondes
        //animator.start(); // Démarrer l'animation

        // Assuming ProduitActivity is already defined
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
