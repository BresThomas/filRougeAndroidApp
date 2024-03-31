package edu.bres.filrouge;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AutreActivity extends AppCompatActivity {
    MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_produit);

        Button boutonAfficherAutreVue = findViewById(R.id.buttonPannier);
        boutonAfficherAutreVue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.afficherLoadingAvecAnimation();
            }
        });
    }
}
