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

public class ShoppingActivity extends AppCompatActivity {

    private final String TAG = "ShoppingActivity";
    private ProgressDialog progressDialog;
    private GridView gridView;
    private ShoppingAdapter adapter;
    private List<ProductShopping> itemList = new ArrayList<>();
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

    private void fetchItemsFromFirestore() {
        db.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Convertir le document Firestore en objet ProductShopping
                            ProductShopping product = document.toObject(ProductShopping.class);
                            itemList.add(product);
                        }
                        // Mettre à jour l'adaptateur avec les données récupérées
                        updateAdapter();
                    } else {
                        Log.e(TAG, "Erreur lors de la récupération des données: ", task.getException());
                    }
                });
    }

    private void updateAdapter() {
        // Masquer la boîte de dialogue de chargement
        progressDialog.dismiss();

        // Initialiser l'adaptateur et le définir sur la GridView
        adapter = new ShoppingAdapter(itemList, this);
        gridView.setAdapter(adapter);
    }
}
