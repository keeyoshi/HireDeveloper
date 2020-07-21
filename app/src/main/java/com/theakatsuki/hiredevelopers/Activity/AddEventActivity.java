package com.theakatsuki.hiredevelopers.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theakatsuki.hiredevelopers.Model.User;
import com.theakatsuki.hiredevelopers.R;
import com.theakatsuki.hiredevelopers.ui.profile.ProfileFragment;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddEventActivity extends AppCompatActivity {

    CircleImageView profileImage;
    ImageButton addPhoto;
    ImageView eventImage;
    TextView fullName,content;
    Button btnPost;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    StorageReference storageReference;
    private Uri imageURl;
    private StorageTask<UploadTask.TaskSnapshot> uploadsTask;
    ProgressBar progressBar;
    public AddEventActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        profileImage = findViewById(R.id.add_profile_image);
        eventImage = findViewById(R.id.showEventImage);
        addPhoto = findViewById(R.id.addPhoto);
        fullName = findViewById(R.id.add_event_username);
        content = findViewById(R.id.addEventContent);
        btnPost = findViewById(R.id.btnPostEvent);
        progressBar = findViewById(R.id.progress);
        storageReference = FirebaseStorage.getInstance().getReference("EventImage");



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                fullName.setText(user.getFullname());
                if(user.getProfileImage().equals("Default"))
                {
                    profileImage.setImageResource(R.mipmap.ic_launcher);
                }
                else
                {
                    Glide.with(getApplicationContext()).load(user.getProfileImage()).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });
    }
    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK && data != null )
        {
            imageURl =data.getData();
            eventImage.setVisibility(View.VISIBLE);
            eventImage.setImageURI(imageURl);
        }
    }


    private void uploadImage()
    {
        progressBar.setVisibility(View.VISIBLE);
        final String text = content.getText().toString();
        if(imageURl !=null)
        {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    +"."+getFileExtension(imageURl));
            uploadsTask =fileReference.putFile(imageURl);
            uploadsTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>(){
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();
                    String text = content.getText().toString();
                    if (task.isSuccessful())
                    {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("content",text);
                        hashMap.put("eventImage",mUri);
                        hashMap.put("userid",firebaseUser.getUid());
                        reference.child("Events").push().setValue(hashMap);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(AddEventActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        content.setText("");
                        eventImage.setVisibility(View.GONE);
                    }
                    else
                    {

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("content",text);
            hashMap.put("eventImage","Blank");
            hashMap.put("userid",firebaseUser.getUid());
            reference.child("Events").push().setValue(hashMap);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
            content.setText("");
            eventImage.setVisibility(View.GONE);
        }

    }
}