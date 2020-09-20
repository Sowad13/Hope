package com.example.hope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Post_edit extends AppCompatActivity {

    private static final int REQUESCODE = 1;
    private static final int PReqCode = 1;

    ImageView addpic,postupdate;
    TextView popupDescription;
    TextView feelingView;
    Spinner popupTitleSpinner;
    ProgressBar progressBar;
    String titletext,UserId;

    private String Title,Image;
    private  String Description,pkey;

    private Uri pickedImgUri = null;


    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase mData;
    DatabaseReference mreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_post_edit );

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance();
        mreference = mData.getReference();
        UserId = user.getUid();

        Title = getIntent().getStringExtra( "title" );
        Description = getIntent().getStringExtra( "description" );
        pkey = getIntent().getStringExtra( "key" );
        Image = getIntent().getStringExtra( "image" );


        popupDescription = findViewById(R.id.addDescription);
        popupDescription.setText( Description );
        postupdate= findViewById(R.id.postupdate);
        progressBar = findViewById(R.id.progress);
        feelingView = findViewById( R.id.feelingTextView );
        addpic = findViewById(R.id.picAdd);
        Glide.with( this ).load( Image ).into( addpic );
        popupTitleSpinner = findViewById(R.id.addTitlespinner);
        setupPopupImageClick();

        ArrayAdapter<CharSequence> spinarrayadapter = ArrayAdapter.createFromResource( this,R.array.feelings,android.R.layout.simple_spinner_item );
        spinarrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        popupTitleSpinner.setAdapter( spinarrayadapter );
        popupTitleSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                titletext = popupTitleSpinner.getSelectedItem().toString();

                feelingView.setText( titletext );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        postupdate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postupdate.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);


                if(!feelingView.toString().isEmpty() && !popupDescription.getText().toString().isEmpty() && pickedImgUri != null) {


                    //created post add to firebase
                    StorageReference storageReference = FirebaseStorage.getInstance( ).getReference().child( "post_images" );
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile( pickedImgUri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String imageDownlaodLink = uri.toString();

                                    detailpost detailpost = new detailpost( );
                                    detailpost.setTitle( feelingView.getText().toString() );
                                    detailpost.setDescription( popupDescription.getText().toString() );
                                    detailpost.setImgUpload(imageDownlaodLink);
                                    detailpost.setUserid(UserId);
                                    detailpost.setType( 1 );

                                    postUpdatedtoFirebase(detailpost);

                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    showMessage(e.getMessage());
                                    progressBar.setVisibility(View.INVISIBLE);
                                    postupdate.setVisibility(View.VISIBLE);

                                }
                            } );
                        }
                    } );


                }
                else if(!feelingView.toString().isEmpty() &&  popupDescription.getText().toString().isEmpty() && pickedImgUri != null) {


                    //created post add to firebase
                    StorageReference storageReference = FirebaseStorage.getInstance( ).getReference().child( "post_images" );
                    final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());
                    imageFilePath.putFile( pickedImgUri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            imageFilePath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String imageDownlaodLink = uri.toString();

                                    detailpost detailpost = new detailpost( );
                                    detailpost.setTitle( feelingView.getText().toString() );
                                    detailpost.setImgUpload(imageDownlaodLink);
                                    detailpost.setUserid(UserId);
                                    detailpost.setType( 1 );

                                postUpdatedtoFirebase(detailpost);

                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    showMessage(e.getMessage());
                                    progressBar.setVisibility(View.INVISIBLE);
                                    postupdate.setVisibility(View.VISIBLE);

                                }
                            } );
                        }
                    } );


                }
                else if(!feelingView.toString().isEmpty() && !popupDescription.toString().isEmpty() && pickedImgUri == null)
                {
                    detailpost detailpost = new detailpost( );
                    detailpost.setTitle( feelingView.getText().toString() );
                    detailpost.setDescription( popupDescription.getText().toString() );
                    detailpost.setUserid(UserId);
                    detailpost.setType( 0);

                    postUpdatedtoFirebase(detailpost);

                }

                else{
                    showMessage("Please verify all input fields or choose Post Image") ;
                   postupdate.setVisibility(View.VISIBLE);
                    progressBar.setVisibility( View.INVISIBLE);

                }

            }


        });

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
        if (ContextCompat.checkSelfPermission(Post_edit.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Post_edit.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(Post_edit.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(Post_edit.this,
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

    private void postUpdatedtoFirebase(detailpost detailpost) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Posts");
        String key = pkey;
        detailpost.setPostKey( pkey );

        databaseReference.child( key ).setValue( detailpost ).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showMessage("Post Updated successfully");
                progressBar.setVisibility(View.INVISIBLE);
                postupdate.setVisibility(View.VISIBLE);
                finish();
                return;
            }
        } );

    }

    private void showMessage(String message) {

        Toast.makeText(Post_edit.this,message, Toast.LENGTH_LONG).show();
    }


}
