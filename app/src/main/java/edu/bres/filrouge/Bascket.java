package edu.bres.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Activité pour afficher une liste de produits dans une GridView.
 * Elle récupère les données à partir de Firebase Firestore.
 *
 * @author [Bitoun, Bres, Wallner] - March 2024
 */
public class Bascket extends AppCompatActivity {

    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();
    private ProgressDialog progressDialog;
    private GridView gridView;
    private BascketAdapter adapter;
    private List<ProductBascket> itemList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        // Initialiser Firestore
        db = FirebaseFirestore.getInstance();

        // Initialiser la GridView
        gridView = findViewById(R.id.shoppingGridview);

        // Afficher la boîte de dialogue de chargement
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Chargement...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Récupérer les données du JSON depuis Firebase Firestore
        fetchItemsFromFirestore();
    }

    /**
     * Récupère les données des produits à partir de Firebase Firestore.
     */
    private void fetchItemsFromFirestore() {
        db.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convertir le document Firestore en objet ProductShopping
                            ProductBascket product = document.toObject(ProductBascket.class);
                            itemList.add(product);
                        }
                        // Mettre à jour l'adaptateur avec les données récupérées
                        updateAdapter();
                    } else {
                        Log.e(TAG, "Erreur lors de la récupération des données: ", task.getException());
                    }
                });
    }

    /**
     * Met à jour l'adaptateur avec les données récupérées et l'attache à la GridView.
     */
    private void updateAdapter() {
        // Masquer la boîte de dialogue de chargement
        progressDialog.dismiss();

        // Initialiser l'adaptateur et le définir sur la GridView
        adapter = new BascketAdapter(itemList, this);
        gridView.setAdapter(adapter);
    }
}
