package edu.bres.filrouge;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements PostExecuteActivity<Panier>, Clickable {
    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();
    private ProduitAdapter adapter;
    private final List<ProduitInterface> produitInterfaces = new ArrayList<>(); //complete list
    private final List<ProduitInterface> displayedProduit = new ArrayList<>(); //displayed list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://edu.info06.net/onepiece/characters.json";
        new HttpAsyncGet<>(url, Panier.class, this, new ProgressDialog(MainActivity.this) );

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
                                if (name != null && url_image != null && description != null) {
                                    Produit produit = new Produit(name, description, url_image);
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

        // Assuming ProduitActivity is already defined
        Intent intent = new Intent(this, ProduitActivity.class);
        intent.putExtra("character", produitInterfaces.get(itemIndex));
        startActivity(intent);
    }

    /**
     * Return index in the complete list matching with the ProduitInterface
     * found at index parameter position in the current displayed list
     *
     * @param index in the displayed list
     * @return index in the complete list matching with the item to find
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

    @Override
    public void onRatingBarChange(int itemIndex, float value) {
        produitInterfaces.get(findIndexInList(itemIndex)).setValue(value);
        displayedProduit.get(itemIndex).setValue(value);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPostExecute(List<Panier> itemList) {
        Log.d(TAG, itemList.toString());
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
