package edu.bres.filrouge;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProduitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        Produit character = getIntent().getParcelableExtra("character");

        TextView titleTextView = findViewById(R.id.title);
        //Button button = findViewById(R.id.button);
        TextView characterNameText = findViewById(R.id.characterName);
        ImageView characterPictureImage = findViewById(R.id.characterPicture);
        TextView characterDescriptionText = findViewById(R.id.produitDescription);

        titleTextView.setText("OnePiece characters");
        characterNameText.setText(character.getName());
        //characterPictureImage.setImageResource(character.getPicture());
        characterDescriptionText.setText(character.getDescription());


    }
}

