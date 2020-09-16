package com.example.hope;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class first_yoga extends AppCompatActivity {

    ImageView  yogasliderImageView;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_first_yoga );

        yogasliderImageView = findViewById( R.id.YogaSliderimageView);

        animationDrawable = (AnimationDrawable) yogasliderImageView.getBackground();
        animationDrawable.setEnterFadeDuration( 1000 );
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();

    }
}
