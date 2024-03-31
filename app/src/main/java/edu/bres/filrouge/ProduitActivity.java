package edu.bres.filrouge;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProduitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Produit product = getIntent().getParcelableExtra("character");

        TextView titleTextView = findViewById(R.id.title);
        //Button button = findViewById(R.id.button);
        TextView productNameText = findViewById(R.id.productName);
        ImageView picture = findViewById(R.id.productPicture);
        TextView productDescriptionText = findViewById(R.id.productDescription);

        titleTextView.setText("Product page");
        productNameText.setText(product.getName());
        Picasso.get().load(product.getPicture()).into(picture);
        productDescriptionText.setText(product.getDescription());

    }
}

