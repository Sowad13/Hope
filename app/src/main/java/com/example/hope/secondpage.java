package com.example.hope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class secondpage extends AppCompatActivity implements View.OnClickListener {

    Button login,signup;
    EditText name,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);

        login = (Button) findViewById(R.id.button);
        signup = (Button) findViewById(R.id.button2);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);

        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent= new Intent(secondpage.this,Dashboard.class);
        startActivity(intent);

    }
}
