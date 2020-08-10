package com.example.hope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Adding_post extends AppCompatActivity {



    private static final int REQUESCODE = 1;
    private static final int PReqCode = 1;


    ImageView popupUserImage;
    ImageView addpic,addpost;
    TextView popupTitle,popupDescription;
    ProgressBar progressBar;

    private Uri pickedImgUri = null;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_post);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        iniPopup();
        setupPopupImageClick();

    }


    private void setupPopupImageClick() {

        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAndRequestForPermission();
            }
        });
    }


    private void checkAndRequestForPermission() {


        if (ContextCompat.checkSelfPermission(Adding_post.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Adding_post.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Adding_post.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Adding_post.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }

        }
        else
            openGallery();

    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            pickedImgUri = data.getData() ;
           addpic.setImageURI(pickedImgUri);


        }


    }


    private void iniPopup() {


     /*   popAddPost = new Dialog(this);
        popAddPost.setContentView(R.layout.activity_adding_post);
        popAddPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popAddPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popAddPost.getWindow().getAttributes().gravity = Gravity.TOP;*/


        popupUserImage = findViewById(R.id.dp);
        addpic = findViewById(R.id.picAdd);
        popupTitle = findViewById(R.id.addTitle);
        popupDescription = findViewById(R.id.addDescription);
        addpost = findViewById(R.id.postAdd);
        progressBar = findViewById(R.id.progress);


        Glide.with(Adding_post.this).load(user.getPhotoUrl()).into(popupUserImage);

        // Add post click Listener

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addpost.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);


                if(!popupTitle.getText().toString().isEmpty() && !popupDescription.getText().toString().isEmpty() && pickedImgUri != null) {


                    //create post and add to firebase




                }

                else{
                    showMessage("Please verify all input fields and choose Post Image") ;
                    addpost.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                }

            }

        });


    }


    private void showMessage(String message) {

        Toast.makeText(Adding_post.this,message,Toast.LENGTH_LONG).show();
    }



}
