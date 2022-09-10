package com.astro.navigation_drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.astro.navigation_drawer.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();

        binding.buttonlogin.setOnClickListener(v -> {
            binding.progressbarlog.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(binding.edittextlogin.getText().toString(),binding.edittextpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    binding.progressbarlog.setVisibility(View.INVISIBLE);
                    String uid=firebaseAuth.getCurrentUser().getUid();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);

                }else {
                    binding.progressbarlog.setVisibility(View.INVISIBLE);

                }
                }
            });
        });


        binding.buttonopenregister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        });
    }
}