package com.theakatsuki.hiredevelopers.ui.profile;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
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
import com.theakatsuki.hiredevelopers.Activity.MainActivity;
import com.theakatsuki.hiredevelopers.Model.User;
import com.theakatsuki.hiredevelopers.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {


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
    private long countPost = 0 ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        eventImage = view.findViewById(R.id.showEventImage);
        addPhoto = view.findViewById(R.id.addPhoto);
        fullName = view.findViewById(R.id.add_event_username);
        content = view.findViewById(R.id.addEventContent);
        btnPost = view.findViewById(R.id.btnPostEvent);
        progressBar = view.findViewById(R.id.progress);
        storageReference = FirebaseStorage.getInstance().getReference("EventImage");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countPost = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                fullName.setText(user.getFullname());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(content.getText().equals(null) || imageURl == null)
                {
                    Toast.makeText(getContext(), "The Event does not have a content", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    uploadImage();
                }
            }
        });
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });
        return view;
    }
    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    public String getFileExtension(Uri uri)
    {
        Context applicationContext = MainActivity.getContextOfApplication();
        ContentResolver contentResolver = applicationContext.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
                        String postId = reference.push().getKey();
                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("content",text);
                        hashMap.put("count",countPost);
                        hashMap.put("eventImage",mUri);
                        hashMap.put("userId",firebaseUser.getUid());
                        hashMap.put("postId",postId);
                        reference.push().setValue(hashMap);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            String postId = reference.push().getKey();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("postId",postId);
            hashMap.put("count",countPost);
            hashMap.put("content",text);
            hashMap.put("eventImage","Blank");
            hashMap.put("userId",firebaseUser.getUid());
            hashMap.put("id",reference.push().getKey());

            reference.child("Events").push().setValue(hashMap);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
            content.setText("");
            eventImage.setVisibility(View.GONE);
        }

    }
}