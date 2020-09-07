package com.theakatsuki.hiredevelopers.ui.profile;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {


    ImageButton addPhoto;
    ImageView eventImage;
    EditText content,jobTitle;
    Spinner spinner,price;
    Button btnPost;
    LinearLayout addJobLayout;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    StorageReference storageReference;
    private Uri imageURl;
    private StorageTask<UploadTask.TaskSnapshot> uploadsTask;
    ProgressBar progressBar;
    private long countPost = 0 ;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    TextView dateTimePicker;
    String date;
    CheckBox chkDesign, chkData,chkContent,chkWebsite,chkMobile,chkMarketing;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        eventImage = view.findViewById(R.id.showEventImage);
        addPhoto = view.findViewById(R.id.addPhoto);
        spinner = view.findViewById(R.id.profile_spinner);
        addJobLayout = view.findViewById(R.id.addJobLayout);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Event");
        arrayList.add("Job");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        content = view.findViewById(R.id.addEventContent);
        btnPost = view.findViewById(R.id.btnPostEvent);
        progressBar = view.findViewById(R.id.progress);
        jobTitle = view.findViewById(R.id.pro_job_title);
        price = view.findViewById(R.id.pro_job_price);
        dateTimePicker = view.findViewById(R.id.tvDatetimePicker);
        chkContent= view.findViewById(R.id.chkContentDev1);
        chkData= view.findViewById(R.id.chkDataDev1);
        chkMobile= view.findViewById(R.id.chkMobileDev1);
        chkMarketing= view.findViewById(R.id.chkMarketingDev1);
        chkWebsite= view.findViewById(R.id.chkWebsiteDev1);
        chkDesign= view.findViewById(R.id.chkDesignDev1);
        dateTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.Theme_AppCompat_Light_Dialog,onDateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                date=month+"/"+day+"/"+year;
                dateTimePicker.setText(date);
            }
        };
        storageReference = FirebaseStorage.getInstance().getReference("EventImage");
        ArrayList<String> priceList = new ArrayList<>();
        priceList.add("$ 10-30");
        priceList.add("$ 30-250");
        priceList.add("$ 250-750");
        priceList.add("$ 750-1500");
        priceList.add("$ 1500-3000");
        priceList.add("$ 3000-5000");
        priceList.add("$ 5000+");
        ArrayAdapter<String> priceAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, priceList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        price.setAdapter(priceAdapter);
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(parent.getItemAtPosition(position).toString().equals("Job")){
                   addJobLayout.setVisibility(View.VISIBLE);
                   addPhoto.setVisibility(View.GONE);
                   content.setVisibility(View.GONE);
               }
               else
               {
                   addJobLayout.setVisibility(View.GONE);
                   addPhoto.setVisibility(View.VISIBLE);
                   content.setVisibility(View.VISIBLE);
               }


            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinner.getSelectedItem().toString().equals("Event"))
                {
                    if(content.getText().equals(null) && imageURl == null)
                    {
                        Toast.makeText(getContext(), "The Event does not have a content", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        uploadImage();
                    }
                }
                else
                {
                    PostJob();
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

    private void PostJob() {
        final List<String> list=new ArrayList<String>();
        if(chkContent.isChecked())
        {
            list.add("Content");
        }
        if(chkData.isChecked())
        {
            list.add("Data");
        }
        if(chkDesign.isChecked())
        {
            list.add("Design");
        }
        if(chkMobile.isChecked())
        {
            list.add("Mobile");
        }
        if(chkWebsite.isChecked())
        {
            list.add("Website");
        }
        if(chkMarketing.isChecked())
        {
            list.add("Marketing");
        }
        if(list.size()==0)
        {
            chkContent.setError("Please select a requirement");
            chkContent.requestFocus();
        }
        else
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Jobs");
            String postId = reference.push().getKey();
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("title",jobTitle.getText().toString());
            hashMap.put("price",price.getSelectedItem().toString());
            hashMap.put("date",dateTimePicker.getText().toString());
            hashMap.put("id",postId);
            hashMap.put("requirement",list);
            jobTitle.setText("");
            dateTimePicker.setText("Click to select a date");
            reference.child(firebaseUser.getUid()).push().setValue(hashMap);
            Toast.makeText(getContext(), "Job posted", Toast.LENGTH_SHORT).show();
        }


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