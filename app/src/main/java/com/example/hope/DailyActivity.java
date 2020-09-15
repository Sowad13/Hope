package com.example.hope;


import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class DailyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference reference;
    String onlineUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        recyclerView = findViewById(R.id.recyclerview);
        floatingActionButton = findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("tasks").child(onlineUserId);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTask();
            }
        });







    }

    private void addTask() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DailyActivity.this,R.style.BottomSheetDialogTheme);

        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.input_file,
                        (RelativeLayout)findViewById(R.id.bottomSheetContainer)
                );


        final EditText task = bottomSheetView.findViewById(R.id.edit_title_text);
        final EditText description = bottomSheetView.findViewById(R.id.edit_description_text);
        Button save = bottomSheetView.findViewById(R.id.save_button);

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                String mTask = task.getText().toString().trim();
                String mDescription= description.getText().toString().trim();
                String id = reference.push().getKey();
                String date = DateFormat.getDateInstance().format(new Date());

                if(TextUtils.isEmpty(mTask))
                {
                    task.setError("Need to fill up");
                }
                if(TextUtils.isEmpty(mDescription))
                {
                    description.setError("Need to fill up");
                }
                else if(!TextUtils.isEmpty(mTask)&&!TextUtils.isEmpty(mDescription))
                {
                    Modle model = new Modle(mTask,mDescription,id,date);
                    reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                 bottomSheetDialog.dismiss();
                                Toast.makeText(DailyActivity.this,"Your Task is added",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }






            }
        });

        bottomSheetView.findViewById(R.id.c_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
            }
        });



        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
        bottomSheetDialog.setCancelable(false);

    }
}
