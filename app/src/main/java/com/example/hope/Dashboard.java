package com.example.hope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.makeramen.roundedimageview.RoundedImageView;

public class Dashboard extends AppCompatActivity  {


    FirebaseAuth mAuth;
    FirebaseUser user;
    ChipNavigationBar bottomNavigationView ;
    ImageButton menubtn;
    CustomLoadingBar loadingBar;
    TextView DisplayName;
    DatabaseReference ref;
    RoundedImageView DisplayUserImage;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        menubtn = findViewById(R.id.menu_button);
        loadingBar = new CustomLoadingBar(Dashboard.this);
        DisplayName = findViewById(R.id.diplay_name_txt);
        ref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());

        //Nusraat or sowad use this variable to display user image on dashboard
        // DO the code on onStart method
        DisplayUserImage = findViewById(R.id.user_image);








        // checking if email has been verified
        if(!user.isEmailVerified())
        {
            Toast.makeText(Dashboard.this, "Verify your email first", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent intent = new Intent(Dashboard.this, secondpage.class);
            startActivity(intent);
            finish();
        }

        //setting home fragment as initial fragment
        if(savedInstanceState==null)
        {
            bottomNavigationView.setItemSelected(R.id.home,true);
        }
        bottomNavigationView.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();


        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(getApplicationContext(),v);
                popup.inflate(R.menu.main_menu);
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.settings:
                                Toast.makeText(Dashboard.this,"Settings",Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.logout:
                                loadingBar.StartLoadingDialog();
                                mAuth.signOut();
                                loadingBar.DismissLoadingDialog();
                                Intent intent = new Intent(Dashboard.this,secondpage.class);
                                startActivity(intent);
                                finish();

                                return true;

                        }

                        return false;
                    }
                });



            }
        });



    }

    private ChipNavigationBar.OnItemSelectedListener navListener = new ChipNavigationBar.OnItemSelectedListener() {
        @Override
        public void onItemSelected(int i) {

            Fragment selectedFragment =null;

            switch (i){

                case R.id.home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.selfcare:
                    selectedFragment = new SelfcareFragment(); // replace this part
                    break;

                case R.id.psychiatrist:
                    selectedFragment = new UsersFragment(); // replace this part
                    break;
                case R.id.community:
                    selectedFragment = new ComunityFragment(); // replace this part
                    break;



            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();




        }
    };

    @Override
    protected void onStart() {
        super.onStart();

       ref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if(dataSnapshot.hasChild("username"))
               {
                   DisplayName.setText("Hi, "+dataSnapshot.child("username").getValue());
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });




       //Display image 



    }
}
