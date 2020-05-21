package com.example.hope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static int SPLASH_SCREEN = 4000;
    Animation topanim,botanim,leftanim,rightanim;
    ImageView image;
    TextView H,O,P,E;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        topanim = AnimationUtils.loadAnimation(this,R.anim.top_anim);
        botanim = AnimationUtils.loadAnimation(this,R.anim.bot_anim);
        leftanim = AnimationUtils.loadAnimation(this,R.anim.left_anim);
        rightanim = AnimationUtils.loadAnimation(this,R.anim.right_anim);

        mAuth = FirebaseAuth.getInstance();


        H = findViewById(R.id.textView);
        O = findViewById(R.id.textView3);
        P = findViewById(R.id.textView4);
        E = findViewById(R.id.textView5);
        image = findViewById(R.id.imageView4);


        H.setAnimation(rightanim);
        O.setAnimation(topanim);
        P.setAnimation(botanim);
        E.setAnimation(leftanim);
        image.setAnimation(topanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // if user don't logout then user will sent to dashbord every time user opens the app
                if(mAuth.getCurrentUser()!=null)
                {
                    Intent intent= new Intent(MainActivity.this,Dashboard.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Intent intent = new Intent(MainActivity.this, secondpage.class);
                    startActivity(intent);
                    finish();
                }
            }
        },SPLASH_SCREEN);





}

    @Override
    public void onClick(View v) {

    }
}