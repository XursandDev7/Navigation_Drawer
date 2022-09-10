package com.astro.navigation_drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.astro.navigation_drawer.Activitys.AboutActivity;
import com.astro.navigation_drawer.Activitys.CallActivity;
import com.astro.navigation_drawer.Activitys.ContactActivity;
import com.astro.navigation_drawer.Activitys.GroupActivitys;
import com.astro.navigation_drawer.Activitys.ProfileActivity;
import com.astro.navigation_drawer.Activitys.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    DrawerLayout drawerLayout;
    DatabaseReference databaseReference;
    NavigationView navigationView;
    TextView textViewusername;
    TextView textViewemailandphone;
    CircleImageView imageviewprofile;
    View header;
    String uid="";
    String name="";
    String fullname="";
    String phone="";
    String gmail="";
    String imageurl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigationview1);
        header=navigationView.getHeaderView(0);
        textViewusername=header.findViewById(R.id.username);
        textViewemailandphone=header.findViewById(R.id.emailandphone);
        imageviewprofile=header.findViewById(R.id.profileimage);

        imageviewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("name",name);
                intent.putExtra("fullname",fullname);
                intent.putExtra("gmail",gmail);
                intent.putExtra("phone",phone);
                intent.putExtra("url",imageurl);
                startActivity(intent);
            }
        });

        imageView=findViewById(R.id.imageviewmenu);
        imageView.setOnClickListener(v -> {
            drawerLayout.openDrawer(Gravity.LEFT);
        });

        Intent intent=getIntent();
        uid=intent.getStringExtra("uid");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Userlarim").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name=snapshot.child("name").getValue().toString();
                fullname=snapshot.child("fulname").getValue().toString();
                gmail=snapshot.child("email").getValue().toString();
                phone=snapshot.child("phone").getValue().toString();
                imageurl=snapshot.child("imageurl").getValue().toString();
                Picasso.get().load(imageurl).into(imageviewprofile);

                textViewusername.setText(name+" "+fullname);
                textViewemailandphone.setText(gmail+"\n"+phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        Intent intent1=new Intent(MainActivity.this, GroupActivitys.class);
                        intent1.putExtra("username",name);
                        intent1.putExtra("fullname",fullname);
                        intent1.putExtra("imageurl",imageurl);

                        startActivity(intent1);


                        break;
                    case R.id.item2:
                        startActivity(new Intent(MainActivity.this, ContactActivity.class));

                        break;
                    case R.id.item3:
                        startActivity(new Intent(MainActivity.this, CallActivity.class));

                        break;
                    case R.id.item4:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));

                        break;
                    case R.id.item5:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));

                        break;

                }
                return true;
            }
        });




    }
}