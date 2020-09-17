package com.example.hope;


import android.icu.text.DateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Modle> firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<Modle>()
                .setQuery(reference,Modle.class)
                .build();

        FirebaseRecyclerAdapter<Modle,DailyTaskViewHolder> adapter = new FirebaseRecyclerAdapter<Modle, DailyTaskViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull DailyTaskViewHolder holder, int position, @NonNull final Modle model) {

                holder.setDate(model.getDate());
                holder.setTitl(model.getTask());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView desText;

                        AlertDialog.Builder builder = new AlertDialog.Builder(DailyActivity.this);

                        LayoutInflater inflater = LayoutInflater.from(DailyActivity.this);
                        View view = inflater.inflate(R.layout.daily_description, null);
                        desText = view.findViewById(R.id.show_des);
                        desText.setText(model.getDescription());
                        builder.setView(view);
                        AlertDialog dialog = builder.create();
                        dialog.show();



                    }
                });



            }

            @NonNull
            @Override
            public DailyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dailytask_retrieve_layout,parent,false);

                return new DailyTaskViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            switch(direction)
            {
                case ItemTouchHelper.LEFT:

                    break;

                case ItemTouchHelper.RIGHT:
                    CongoDialog();

                    break;
            }


        }
    };

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



    public static class DailyTaskViewHolder extends RecyclerView.ViewHolder
    {
        View mView;

        public DailyTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }


        public void setTitl(String title)
        {
            TextView tasktitle = mView.findViewById(R.id.title_task);

            tasktitle.setText(title);
        }

        public void setDate(String Date)
        {
            TextView taskDate = mView.findViewById(R.id.date_task);

            taskDate.setText(Date);
        }

    }


void CongoDialog()
{

    AlertDialog.Builder builder = new AlertDialog.Builder(DailyActivity.this);
    LayoutInflater inflater = LayoutInflater.from(DailyActivity.this);
    View view = inflater.inflate(R.layout.congratulation_dialog, null);
    builder.setView(view);
    AlertDialog dialog = builder.create();
    dialog.show();

}



}
