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

/**
 * MainActivity est l'activité principale de l'application. Elle affiche une liste de produits
 * filtrés en fonction d'une note sélectionnée par l'utilisateur à l'aide d'une SeekBar.
 * Elle récupère les données des produits à partir d'une base de données Firestore.
 * L'utilisateur peut cliquer sur un produit pour afficher ses détails dans une autre activité.
 * Cette activité implémente les interfaces Clickable et PostExecuteActivity.
 * 
 * @author [Bitoun, Bre, Wallner] - March 2024
 * 
 */

public class MainActivity extends AppCompatActivity implements PostExecuteActivity<ProduitPanier>, Clickable {
    
    // Déclarations des variables de classe
    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();
    private ProduitAdapter adapter;
    private float note = 0;
    private TextView displayNote;
    private final List<ProduitInterface> produitInterfaces = new ArrayList<>(); //complete list
    private final List<ProduitInterface> displayedProduit = new ArrayList<>(); //displayed list

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les éléments de l'interface utilisateur et récupère les produits depuis Firestore.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Assurez-vous d'inflater le bon layout

        final SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(10);
        displayNote = findViewById(R.id.value);

        // Vérifie si la liste des produits affichés est vide
        if (displayedProduit.isEmpty()) {
            // Charge les produits depuis Firebase Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("product")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("titre");
                                String url_image = document.getString("url_image");
                                String description = document.getString("description");
                                float price = document.getLong("prix");
                                float value = document.getLong("note");
                                if (name != null && url_image != null && description != null)  {
                                    Produit produit = new Produit(name, description, url_image,value,price);
                                    produitInterfaces.add(produit);
                                    displayedProduit.add(produit); // Assuming all products are initially displayed
                                }
                            }
                            initGridView(); // Initialise la GridView après le chargement des produits
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    });
        } else {
            initGridView(); // Initialise la GridView si les produits sont déjà chargés
        }

        // Définit un écouteur pour la SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                note = progress / 2f; // Progression de 0.5 en 0.5
                displayNote.setText(String.format(Locale.getDefault(), "%.1f/5", note));

                updateDisplayedProducts();
            }

            private void updateDisplayedProducts() {
                displayedProduit.clear(); // Efface la liste des produits affichés

                // Filtre les produits en fonction de la note actuelle
                for (ProduitInterface produit : produitInterfaces) {
                    if (produit.getValue() >= note) {
                        displayedProduit.add(produit);
                    }
                }

                adapter.notifyDataSetChanged(); // Notifie l'adaptateur du changement de données
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Réglez la progression initiale et affichez la note
        seekBar.setProgress((int) (note * 2)); // Convertissez la note en progression de la SeekBar
        displayNote.setText(String.format(Locale.getDefault(), "%.1f/5", note));
    }


    /**
     * Initialise le GridView avec l'adapter et 
     * définit un écouteur d'événement pour les clics sur les éléments.
     */
    private void initGridView() {
        GridView gridView = findViewById(R.id.meilleursProduits);

        adapter = new ProduitAdapter(displayedProduit, this, this);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> onClicItemForLoading(position));
    }

     /**
     * Méthode appelée lorsqu'un élément de la liste est cliqué.
     * Lance l'activité ProduitActivity pour afficher les détails du produit.
     * @param index L'index de l'élément cliqué dans la liste
     */
    @Override
    public void onClicItem(int index) {
        int itemIndex = findIndexInList(index);
        Log.d(TAG, "clicked on = " + produitInterfaces.get(itemIndex).getName());
        Intent intent = new Intent(this, ProduitActivity.class);
        intent.putExtra("character", produitInterfaces.get(itemIndex));
        startActivity(intent);
    }

    /**
     * Méthode appelée lorsqu'un élément de la liste est cliqué pour afficher l'animation de chargement.
     * Lance l'activité LoadingActivity avec l'indication d'afficher l'animation.
     * @param index L'index de l'élément cliqué dans la liste
     */
    private void onClicItemForLoading(int index) {
        // Code pour afficher LoadingActivity
        Intent intent = new Intent(this, LoadingActivity.class);
        intent.putExtra("showAnimation", true); // Ajoutez cette ligne pour indiquer à LoadingActivity d'afficher l'animation
        startActivity(intent);
    }

    /**
     * Recherche l'index d'un produit dans la liste complète à partir de son index dans la liste affichée.
     * @param index L'index du produit dans la liste affichée
     * @return L'index du produit dans la liste complète, ou -1 s'il n'est pas trouvé
     */
    private int findIndexInList(int index) {
        ProduitInterface itemToFind = displayedProduit.get(index);
        for (int i = 0; i < produitInterfaces.size(); i++) {
            if (produitInterfaces.get(i).equals(itemToFind)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Appelée lorsqu'une note de produit est modifiée.
     * Met à jour la note du produit dans les listes produitInterfaces et displayedProduit,
     * puis notifie l'adapter pour refléter la modification.
     * @param itemIndex L'index du produit dans la liste
     * @param value La nouvelle valeur de la note du produit
     */
    @Override
    public void onRatingBarChange(int itemIndex, float value) {
        produitInterfaces.get(findIndexInList(itemIndex)).setValue(value);
        displayedProduit.get(itemIndex).setValue(value);
        adapter.notifyDataSetChanged();
    }

    /**
     * Appelée lorsque la tâche asynchrone de récupération des produits du panier est terminée.
     * Affiche les détails des produits récupérés dans les logs de débogage.
     * @param itemList La liste des produits du panier récupérés
     */
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
