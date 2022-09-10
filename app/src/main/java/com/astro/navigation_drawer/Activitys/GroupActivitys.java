package com.astro.navigation_drawer.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.astro.navigation_drawer.Chat;
import com.astro.navigation_drawer.ChatAdapter;
import com.astro.navigation_drawer.R;
import com.astro.navigation_drawer.databinding.ActivityGroupActivitysBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class GroupActivitys extends AppCompatActivity {

    ActivityGroupActivitysBinding binding;
    DatabaseReference databaseReference;
    String username="";
    String fullname="";
    String imageurl="";

    ArrayList<Chat> chatArrayList;
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupActivitysBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatArrayList=new ArrayList<>();

        databaseReference= FirebaseDatabase.getInstance().getReference().child("chat");
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        fullname=intent.getStringExtra("fullname");
        imageurl=intent.getStringExtra("imageurl");

        binding.imageviewsend.setOnClickListener(v -> {
            Calendar calendar=Calendar.getInstance();
            int hour=calendar.get(Calendar.HOUR_OF_DAY);
            int minut=calendar.get(Calendar.MINUTE);




            Chat chat=new Chat();
            chat.setUsername(username+" "+fullname);
            chat.setMessage(binding.edittextmessage.getText().toString());

            chat.setImageurl(imageurl);

            if(hour<10){
                chat.setDatatime("0"+hour+":"+minut);
                if (minut<10){
                    chat.setDatatime("0"+hour+":0"+minut);

                }else {
                    chat.setDatatime(hour+":"+minut);

                }
            }else{
                if (minut<10){
                    chat.setDatatime(hour+":0"+minut);

                }else {
                    chat.setDatatime(hour+":"+minut);

                }
            }



            databaseReference.push().setValue(chat);
            binding.edittextmessage.setText("");
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatArrayList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Chat chat=dataSnapshot.getValue(Chat.class);
                    chatArrayList.add(chat);
                }
                chatAdapter=new ChatAdapter(GroupActivitys.this,chatArrayList);
                binding.recyclerviewviewchat.setLayoutManager(new LinearLayoutManager(GroupActivitys.this));
                binding.recyclerviewviewchat.setAdapter(chatAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}