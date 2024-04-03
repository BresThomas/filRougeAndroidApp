package edu.bres.filrouge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * @author [Bitoun, Bres, Wallner] - March 2024
 */

public class ShoppingActivity extends AppCompatActivity {

    private final String TAG = "bres, bitoun, wallner " + getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoping);
    }
}