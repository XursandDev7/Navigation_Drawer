package com.astro.navigation_drawer.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.astro.navigation_drawer.R;
import com.astro.navigation_drawer.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewusername,textViewemail,textViewphone;
    String uid="";
    String username="";
    String email="";
    String phone="";
    String fullname="";
    String imageurl="";
    ImageView imageView,imageviewprofil;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textViewusername=findViewById(R.id.textviewprofileusername);
        textViewemail=findViewById(R.id.textviewprofilemail);
        textViewphone=findViewById(R.id.textviewprofilephone);
        imageView=findViewById(R.id.imagevviewopengallery);
        imageviewprofil=findViewById(R.id.imageviewprofil);


        databaseReference= FirebaseDatabase.getInstance().getReference().child("Userlarim");
        storageReference= FirebaseStorage.getInstance().getReference().child("rasmlarim");



        Intent intent=getIntent();
        uid=intent.getStringExtra("uid");
        username=intent.getStringExtra("name");
        fullname=intent.getStringExtra("fullname");
        email=intent.getStringExtra("gmail");
        phone=intent.getStringExtra("phone");
        imageurl=intent.getStringExtra("url");
        Picasso.get().load(imageurl).into(imageviewprofil);

        textViewusername.setText("Username\n"+username);
        textViewemail.setText("Email\n"+email);
        textViewphone.setText("Phone\n"+phone);


        imageView.setOnClickListener(v -> {
            Intent intent2 = new Intent();
            intent2.setType("image/*");
            intent2.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent2, 1);
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData(); //external/storage/telegram/telegramimages/image.png
            Picasso.get().load(uri).into(imageviewprofil);
            uploadimage();
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void uploadimage(){


        if (uri!=null){
            StorageReference filereference=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
            filereference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            User user=new User();
                            user.setName(username);
                            user.setFulname(fullname);
                            user.setEmail(email);
                            user.setPhone(phone);
                            user.setImageurl(uri.toString());
                            databaseReference.child(uid).setValue(user);


                        }
                    });
                }

            });
        }
    }



}