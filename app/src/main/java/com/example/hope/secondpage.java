package com.example.hope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class secondpage extends AppCompatActivity implements View.OnClickListener {

    Button login,signup;
    EditText email,password;
    FirebaseAuth mAuth;
    CustomLoadingBar loadingBar;
    ImageView LoginVisibility;

    int visible = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);

        login = (Button) findViewById(R.id.button);
        signup = (Button) findViewById(R.id.button2);
        email = (EditText) findViewById(R.id.loginEmail);
        password = (EditText) findViewById(R.id.loginPassword);
        LoginVisibility = findViewById(R.id.login_visibility);
        loadingBar = new CustomLoadingBar(secondpage.this);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(this);


        LoginVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(visible==0) {
                    LoginVisibility.setImageResource(R.drawable.ic_visibility_black_24dp);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    visible =1;
                }
                else if(visible == 1)
                {
                    LoginVisibility.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    visible =0;
                }


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(secondpage.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        String userPassword = password.getText().toString();
        String userEmail = email.getText().toString();

        if(userEmail.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches())
        {
            email.setError("Enter Valid Email");
            //Toast.makeText(secondpage.this,"Enter your email",Toast.LENGTH_SHORT).show();
        }
       else if(userPassword.length() < 6)
        {
            password.setError("Password length should be more than 6");
            //Toast.makeText(secondpage.this,"Enter password more than 6 word  ",Toast.LENGTH_SHORT).show();
        }

       else
        {
            // User login process
            loadingBar.StartLoadingDialog();
            mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        loadingBar.DismissLoadingDialog();
                        Intent intent= new Intent(secondpage.this,Dashboard.class);
                        startActivity(intent);
                        finish();
                    }

                    else
                    {
                        loadingBar.DismissLoadingDialog();
                        Toast.makeText(secondpage.this,"ERROR",Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }




    }
}
