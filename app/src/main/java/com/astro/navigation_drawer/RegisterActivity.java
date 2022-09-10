package com.astro.navigation_drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.astro.navigation_drawer.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Userlarim");

        binding.buttonregister.setOnClickListener(v -> {
            binding.progressbarreg.setVisibility(View.VISIBLE);
            firebaseAuth.createUserWithEmailAndPassword(binding.edittextloginreg.getText().toString(),binding.edittextpasswordreg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        binding.progressbarreg.setVisibility(View.INVISIBLE);
                        String uid=firebaseAuth.getCurrentUser().getUid();
                        User user=new User();
                        user.setEmail(binding.edittextloginreg.getText().toString());
                        user.setPassword(binding.edittextpasswordreg.getText().toString());
                        user.setName(binding.edittextname.getText().toString());
                        user.setFulname(binding.edittextfullname.getText().toString());
                        user.setPhone(binding.edittexphone.getText().toString());
                        user.setImageurl("https://static.tildacdn.com/tild3235-3561-4633-a130-386336396266/o20wvlynqf40wkcc48w4.png");
                        databaseReference.child(uid).setValue(user);
                        finish();

                    }
                }
            });
        });


    }
}