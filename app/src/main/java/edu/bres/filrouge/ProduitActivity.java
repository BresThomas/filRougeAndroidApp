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
        setContentView(R.layout.activity_character);
        Produit product = getIntent().getParcelableExtra("character");

        TextView titleTextView = findViewById(R.id.title);
        //Button button = findViewById(R.id.button);
        TextView characterNameText = findViewById(R.id.characterName);
        ImageView picture = findViewById(R.id.productPicture);
        TextView characterDescriptionText = findViewById(R.id.characterDescription);

        titleTextView.setText("Product page");
        characterNameText.setText(product.getName());
        Picasso.get().load(product.getPicture()).into(picture);
        characterDescriptionText.setText(product.getDescription());

    }
}

