package edu.bres.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.app.ProgressDialog;

import java.util.Locale;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements PostExecuteActivity<ProduitPanier>, Clickable {
    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();
    private ProduitAdapter adapter;
    private float note = 0;
    private TextView displayNote;
    private final List<ProduitInterface> produitInterfaces = new ArrayList<>(); //complete list
    private final List<ProduitInterface> displayedProduit = new ArrayList<>(); //displayed list
    //private ImageView loadingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = "https://bresthomas.github.io/jsonHosting/produitPanier.json";
        new HttpAsyncGet<>(url, ProduitPanier.class, this, new ProgressDialog(MainActivity.this) );

        final SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(10);
        displayNote = findViewById(R.id.value);

        if (displayedProduit.isEmpty()) {
            // Load products from Firebase Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("product")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("titre");
                                String url_image = document.getString("url_image");
                                String description = document.getString("description");
                                float value = document.getLong("note");
                                if (name != null && url_image != null && description != null) {
                                    Produit produit = new Produit(name, description, url_image, value);
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
            
        
        // Vérifier si l'intention spéciale pour afficher l'animation est présente
        if (getIntent().hasExtra("showAnimation")) {
            afficherLoadingAvecAnimation();
        } else {
            // Votre logique normale ici
            setContentView(R.layout.activity_main);
        } 

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                note = progress / 2f; // Progression de 0.5 en 0.5
                displayNote.setText(String.format("%.1f/5", note));

                updateDisplayedProducts();
            }

            private void updateDisplayedProducts() {
                displayedProduit.clear(); // Clear the displayed products list

                // Filter products based on the current note
                for (ProduitInterface produit : produitInterfaces) {
                    if (produit.getValue() >= note) {
                        displayedProduit.add(produit);
                    }
                }

                adapter.notifyDataSetChanged(); // Notify adapter of the data change
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Réglez la progression initiale et affichez la note
        seekBar.setProgress((int) (note * 2)); // Convertissez la note en progression de la SeekBar
        displayNote.setText(String.format("%.1f/5", note));
    }

    private void initGridView() {
        GridView gridView = findViewById(R.id.meilleursProduits);

        adapter = new ProduitAdapter(displayedProduit, this, this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> onClicItemForLoading(position));
    }

    private void afficherLoadingAvecAnimation() {
        setContentView(R.layout.activity_loading);

        ImageView imageViewAnimation = findViewById(R.id.imageloadinganimation);
        imageViewAnimation.setBackgroundResource(R.drawable.loading_animation);

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

    private void onClicItemForLoading(int index) {
        // Code pour afficher LoadingActivity
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra("showAnimation", true); // Ajoutez cette ligne pour indiquer à LoadingActivity d'afficher l'animation
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
    public void onPostExecute(List<ProduitPanier> itemList) {
        Log.d(TAG, itemList.toString());

        for(ProduitPanier produit : itemList) {
            Log.d(TAG, produit.getName());
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
