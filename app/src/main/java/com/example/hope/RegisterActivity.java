package com.example.hope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText userName,email, password,confirmPassword;
    ImageButton profileImage;
    Button registerButton;
    FirebaseAuth mAuth;
    CustomLoadingBar loadingBar;
   DatabaseReference UserRef;
   ImageView visibility,visibility2;
   FirebaseUser firebaseUser;


   int visible = 0,visible2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.registerusername);
        profileImage = findViewById(R.id.regDP);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerpassword);
        registerButton = findViewById(R.id.registerbutton);
        UserRef = FirebaseDatabase.getInstance().getReference();
        confirmPassword = findViewById(R.id.register_confirm_password);
        visibility = findViewById(R.id.visibility);
        visibility2 = findViewById(R.id.visibility_2);

        //Glide.with(RegisterActivity.this).load(firebaseUser.getPhotoUrl()).into(profileImage);
        loadingBar = new CustomLoadingBar(RegisterActivity.this);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingBar.StartLoadingDialog();

                RegisterNewUser();
            }
        });


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
       else if (userPassword.length() < 6) {
            password.setError("Password length should be more than 6");
            loadingBar.DismissLoadingDialog();
            //Toast.makeText(RegisterActivity.this, "Enter password more than 6 word  ", Toast.LENGTH_SHORT).show();
        }
       else if(confirm.isEmpty()||!confirm.equals(userPassword))
        {
            confirmPassword.setError("Password does not match");
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

                                String UserId = user.getUid();
                                UserRef = FirebaseDatabase.getInstance().getReference("Users").child(UserId);

                                HashMap<String, String> hashmap = new HashMap<>();
                                hashmap.put("id", UserId);
                                hashmap.put("username", username);
                                hashmap.put("imageURL","default");

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
