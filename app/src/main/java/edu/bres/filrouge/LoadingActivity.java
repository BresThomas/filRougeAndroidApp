package edu.bres.filrouge;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ImageView imageViewAnimation = findViewById(R.id.imageloadinganimation);
        imageViewAnimation.setBackgroundResource(R.drawable.loading_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageViewAnimation.getBackground();
        animationDrawable.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoadingActivity.this, "Article ajouté dans le panier", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, 4000);
    }
}
