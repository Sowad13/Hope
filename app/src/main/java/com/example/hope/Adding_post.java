package com.example.hope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Adding_post extends AppCompatActivity {

    private static final int REQUESCODE = 1;
    private static final int PReqCode = 1;

    // Dialog storywriting;

    ImageView popupUserImage;
    ImageView addpic,addpost;
    TextView popupDescription;
    Spinner popupTitle;
    ProgressBar progressBar;

    private Uri pickedImgUri = null;


    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_adding_post );



        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        iniPopup();
        setupPopupImageClick();

      /*  fab = findViewById(R.id.fabbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storywriting.show();

            }
        });*/

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


        //popupUserImage = findViewById(R.id.dp);
        addpic = findViewById(R.id.picAdd);
        popupTitle = findViewById(R.id.addTitle);
        ArrayAdapter<CharSequence> spinarrayadapter = ArrayAdapter.createFromResource( this,R.array.feelings,android.R.layout.simple_spinner_item );
        spinarrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        popupTitle.setAdapter( spinarrayadapter );
        popupTitle.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String titletext = parent.getItemAtPosition( position ).toString();
               // Toast.makeText( parent.getContext(),titletext,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        popupDescription = findViewById(R.id.addDescription);
        addpost = findViewById(R.id.postAdd);
        progressBar = findViewById(R.id.progress);


        //Glide.with(Adding_post.this).load(user.getPhotoUrl()).into(popupUserImage);

        // Add post click Listener

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addpost.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);


                if(!popupTitle.toString().isEmpty() && !popupDescription.getText().toString().isEmpty() && pickedImgUri != null) {


                    //created post add to firebase




                }

                else{
                    showMessage("Please verify all input fields and choose Post Image") ;
                    addpost.setVisibility(View.VISIBLE);
                    progressBar.setVisibility( View.INVISIBLE);

                }

            }

        });

    }







    private void showMessage(String message) {

        Toast.makeText(Adding_post.this,message, Toast.LENGTH_LONG).show();
    }


}
