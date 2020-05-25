package com.example.hope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText email, password;
    Button registerButton;
    FirebaseAuth mAuth;
    CustomLoadingBar loadingBar;
   DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerpassword);
        registerButton = findViewById(R.id.registerbutton);
        UserRef = FirebaseDatabase.getInstance().getReference();
        loadingBar = new CustomLoadingBar(RegisterActivity.this);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingBar.StartLoadingDialog();

                RegisterNewUser();
            }
        });


    }

    private void RegisterNewUser() {

        String userPassword = password.getText().toString();
        String userEmail = email.getText().toString();

        if (userEmail.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.setError("Enter Valid Email");
            loadingBar.DismissLoadingDialog();
            // Toast.makeText(RegisterActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
        }
       else if (userPassword.length() < 6) {
            password.setError("Password length should be more than 6");
            loadingBar.DismissLoadingDialog();
            //Toast.makeText(RegisterActivity.this, "Enter password more than 6 word  ", Toast.LENGTH_SHORT).show();
        } else {

           // creating new user
            mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        loadingBar.DismissLoadingDialog();


                        final FirebaseUser user = mAuth.getCurrentUser();
                        final  String UserId = mAuth.getCurrentUser().getUid();

                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                UserRef.child("Users").child(UserId).setValue("");
                                Toast.makeText(RegisterActivity.this, "Verification has sent", Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    Intent intent = new Intent(RegisterActivity.this, secondpage.class);
                                    startActivity(intent);
                                    finish();


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
