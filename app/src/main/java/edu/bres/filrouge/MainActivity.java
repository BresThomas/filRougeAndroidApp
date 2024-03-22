package edu.bres.filrouge;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Bres, Bitoun, Wallner";
    private List<ProduitInterface> listProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //String url = task.getResult().getDocuments().get(0).getString("url_image");
                        //String titre = task.getResult().getDocuments().get(0).getString("titre");
                        //Log.d(TAG, task.getResult().getDocuments().get(0).getString("titre"));

                        //ImageView imageView = findViewById(R.id.characterPicture);
                        //TextView textView = findViewById(R.id.titreProduit);

                        //textView.setText(titre);
                        //Picasso.get().load(url).into(imageView);

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String name = document.getString("titre");
                                String url = document.getString("url_image");
                                String description = document.getString("description");
                                if (name != null && url != null && description != null) {
                                    Produit produit = new Produit(name, description, url);
                                    listProducts.add(produit);
                                }
                            }

                            // maintenant liste complete

                            // recuperer listview
                            ListView listView = findViewById(R.id.listView);

                            //creer un adapter
                            ProduitAdapter adapter = new ProduitAdapter(listProducts, getApplicationContext());

                            listView.setAdapter(adapter);

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }
}
