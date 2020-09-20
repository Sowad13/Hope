package com.example.hope;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUESCODE = 1;
    private static final int PReqCode = 1;

    private Uri pickedImgUri = null;

    EditText userName,email, password,confirmPassword;
    ImageView profileImage;
    Button registerButton;
    FirebaseAuth mAuth;
    CustomLoadingBar loadingBar;
   DatabaseReference UserRef;
   ImageView visibility,visibility2;
   FirebaseUser firebaseUser;
   //Context context;


   StorageReference storageReference;
   /*private static final int IMAGE_REQUEST=1;
   private Uri imageUri;*/
   private StorageTask uploadTask;


   int visible = 0,visible2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.registerusername);
        profileImage = findViewById(R.id.registerimage);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerpassword);
        registerButton = findViewById(R.id.registerbutton);
        UserRef = FirebaseDatabase.getInstance().getReference();
        confirmPassword = findViewById(R.id.register_confirm_password);
        visibility = findViewById(R.id.visibility);
        visibility2 = findViewById(R.id.visibility_2);

        setupPopupImageClick();

        //Glide.with(RegisterActivity.this).load(firebaseUser.getPhotoUrl()).into(profileImage);
        loadingBar = new CustomLoadingBar(RegisterActivity.this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingBar.StartLoadingDialog();




                RegisterNewUser();

            }
        });


       /* profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });*/


        visibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visible==0) {
                    visibility.setImageResource(R.drawable.ic_visibility_black_24dp);
                    confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visible =1;
                }
               else if(visible == 1)
                {
                    visibility.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visible =0;
                }
            }
        });


        visibility2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(visible2==0) {
                    visibility2.setImageResource(R.drawable.ic_visibility_black_24dp);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visible2=1;
                }
                else if(visible2 == 1)
                {
                    visibility2.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visible2=0;
                }
            }
        });




    }

    /*private void openImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

        }
    private void uploadImage(){
        final ProgressDialog pd = new ProgressDialog(getBaseContext());
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                           throw task.getException();
                        }

                        return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()){
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();

                        UserRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", imageUri);
                        UserRef.updateChildren(map);

                        pd.dismiss();
                    }else {
                        Toast.makeText(getBaseContext(), "Failed",Toast.LENGTH_SHORT).show();

                        pd.dismiss();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),Toast.LENGTH_SHORT).show();

                    pd.dismiss();
                }
            });
        }else{
            Toast.makeText(getBaseContext(),"No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && requestCode == RESULT_OK && data !=null && data.getData() != null){

            imageUri = data.getData();

            if (uploadTask !=null && uploadTask.isInProgress()){
                Toast.makeText(getBaseContext(), "Uplaoding . . .", Toast.LENGTH_SHORT).show();
            }else {
                uploadImage();
            }

        }
    }*/

    private void setupPopupImageClick() {

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAndRequestForPermission();
            }
        });

    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(RegisterActivity.this,"Please accept for required permission",Toast.LENGTH_SHORT).show();

            }

            else
            {
                ActivityCompat.requestPermissions(RegisterActivity.this,
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

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESCODE && data != null ) {

            pickedImgUri = data.getData() ;
            profileImage.setImageURI(pickedImgUri);

            //uploadImage();


        }
    }


    private void RegisterNewUser() {


        final String username = userName.getText().toString();
        String userPassword = password.getText().toString();
        String userEmail = email.getText().toString();
        String confirm = confirmPassword.getText().toString();

        if (userEmail.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.setError("Enter Valid Email");
            loadingBar.DismissLoadingDialog();
            // Toast.makeText(RegisterActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
        }
        if (userPassword.length() < 6) {
            password.setError("Password length should be more than 6");
            loadingBar.DismissLoadingDialog();
            //Toast.makeText(RegisterActivity.this, "Enter password more than 6 word  ", Toast.LENGTH_SHORT).show();
        }
        if(confirm.isEmpty()||!confirm.equals(userPassword))
        {
            confirmPassword.setError("Password does not match");
            loadingBar.DismissLoadingDialog();
        }
        if(username.isEmpty())
        {
            userName.setError("Enter your name");
            loadingBar.DismissLoadingDialog();
        }
       else {

           // creating new user
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        loadingBar.DismissLoadingDialog();


                         final FirebaseUser user = mAuth.getCurrentUser();


                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                final String UserId = user.getUid();
                                UserRef = FirebaseDatabase.getInstance().getReference("Users").child(UserId);
                                storageReference = FirebaseStorage.getInstance().getReference("uploads");
                                final StorageReference imageFilePath = storageReference.child(pickedImgUri.getLastPathSegment());

                                imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {

                                                String imagedownloadlink = uri.toString();

                                                HashMap<String, String> hashmap = new HashMap<>();
                                                hashmap.put("id", UserId);
                                                hashmap.put("username", username);
                                                hashmap.put("imageURL",imagedownloadlink);

                                                UserRef.setValue(hashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){

                                                            Toast.makeText(RegisterActivity.this, "Verification has sent", Toast.LENGTH_SHORT).show();
                                                            mAuth.signOut();
                                                            Intent intent = new Intent(RegisterActivity.this, secondpage.class);
                                                            startActivity(intent);
                                                            finish();

                                                        }
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegisterActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                });




                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(RegisterActivity.this, "Enter a correct email", Toast.LENGTH_SHORT).show();

                            }
                        });








                    } else {

                        loadingBar.DismissLoadingDialog();
                        Toast.makeText(RegisterActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }
    }
}
