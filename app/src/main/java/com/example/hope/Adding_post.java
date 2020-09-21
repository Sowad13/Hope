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
import com.example.hope.Model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Adding_post extends AppCompatActivity {

    private static final int REQUESCODE = 1;
    private static final int PReqCode = 1;

    ImageView popupUserImage;
    ImageView addpic,addpost;
    TextView popupDescription;
    TextView feelingView;
    Spinner popupTitleSpinner;
    ProgressBar progressBar;
    String titletext,UserId;

    private Uri pickedImgUri = null;


    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase mData;
    DatabaseReference mreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_adding_post );



        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mData = FirebaseDatabase.getInstance();
        mreference = mData.getReference();
         UserId = user.getUid();


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


        popupDescription = findViewById(R.id.addDescription);
        addpost = findViewById(R.id.postAdd);
        progressBar = findViewById(R.id.progress);
        feelingView = findViewById( R.id.feelingTextView );
        //popupUserImage = findViewById(R.id.dp);
        addpic = findViewById(R.id.picAdd);
        popupTitleSpinner = findViewById(R.id.addTitlespinner);

        ArrayAdapter<CharSequence> spinarrayadapter = ArrayAdapter.createFromResource( this,R.array.feelings,android.R.layout.simple_spinner_item );
        spinarrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        popupTitleSpinner.setAdapter( spinarrayadapter );
        popupTitleSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                titletext = popupTitleSpinner.getSelectedItem().toString();

                 feelingView.setText( titletext );

                    // Toast.makeText( parent.getContext(),titletext,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );




      //  Glide.with(Adding_post.this).load(user.getPhotoUrl()).into(popupUserImage);

        // Add post click Listener

        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addpost.setVisibility(View.INVISIBLE);
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
                                    detailpost.setUserid(UserId);
                                    detailpost.setImgUpload(imageDownlaodLink);
                                    detailpost.setType( 1 );

                                    postAddedtoFirebase(detailpost);

                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    showMessage(e.getMessage());
                                    progressBar.setVisibility(View.INVISIBLE);
                                    addpost.setVisibility(View.VISIBLE);

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
                                    detailpost.setType( 1 );
                                    detailpost.setUserid(UserId);

                                    postAddedtoFirebase(detailpost);

                                }
                            } ).addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    showMessage(e.getMessage());
                                    progressBar.setVisibility(View.INVISIBLE);
                                    addpost.setVisibility(View.VISIBLE);

                                }
                            } );
                        }
                    } );


                }
                else if(!feelingView.toString().isEmpty() && !popupDescription.getText().toString().isEmpty() && pickedImgUri == null)
                {
                    detailpost detailpost = new detailpost( );
                    detailpost.setTitle( feelingView.getText().toString() );
                    detailpost.setDescription( popupDescription.getText().toString() );
                    detailpost.setType( 0);
                    detailpost.setUserid(UserId);

                    postAddedtoFirebase(detailpost);

                }

                else{
                    showMessage("Please verify all input fields or choose Post Image") ;
                    addpost.setVisibility(View.VISIBLE);
                    progressBar.setVisibility( View.INVISIBLE);

                }

            }


        });

    }

    private void postAddedtoFirebase(final detailpost detailpost) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Posts");
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

        String key = databaseReference.push().getKey();
        detailpost.setPostKey(key);
        /*Users users = new Users(  );
        String uid = users.getId();
        if (UserId == uid){

            String username = users.getUsername();
            detailpost.setUsernam( username ) ;
        }*/

      //  String username = "Nushrattttttttt";
        //detailpost.setUsernam( username ) ;

        databaseReference.child( key ).setValue( detailpost ).addOnSuccessListener( new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                showMessage("Post Added successfully");
                progressBar.setVisibility(View.INVISIBLE);
                addpost.setVisibility(View.VISIBLE);
                finish();
                return;

            }
        } );

    }


    private void showMessage(String message) {

        Toast.makeText(Adding_post.this,message, Toast.LENGTH_LONG).show();
    }


}

