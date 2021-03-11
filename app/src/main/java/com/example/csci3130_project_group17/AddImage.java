package com.example.csci3130_project_group17;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;
import java.util.UUID;

import android.os.Bundle;

public class AddImage extends AppCompatActivity {
    Button UploadIMG, ChooseIMG;
    ImageView imageView;
    Uri FilePathUri;
    //inital database and storage
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    EditText text;
    private String errorMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        storageReference = FirebaseStorage.getInstance().getReference("jobimages");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("jobimages");
        ChooseIMG = (Button) findViewById(R.id.ChooseIMG);
        UploadIMG = (Button) findViewById(R.id.UploadIMG);
        text = (EditText) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.imageView);
        ChooseIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }
        });
        UploadIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Fileuploader();
            }
        });

    }

    private String getExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }
    //method for upload image and jobname
    public void Fileuploader(){
        String error = getErrorMessage();
        //set loop to detect illegal input
        if(error.isEmpty()) {
            StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getExtension(FilePathUri));
            reference.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String TempImageName = text.getText().toString().trim();
                    //text of successfully upload the image
                    Toast.makeText(getApplicationContext(), "Image Successfully Uploaded", Toast.LENGTH_LONG).show();
                    @SuppressWarnings("VisibleForTests")
                    uploadinfo imageUploadInfo = new uploadinfo(taskSnapshot.getUploadSessionUri().toString());
                    String ImageUploadId = databaseReference.push().getKey();
                    //upload data to database
                    databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                    databaseReference.child(ImageUploadId).child("jobname").setValue(TempImageName);
                }
            });
        }
        else{
            Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    //method for choosing image from cellphone
    private void Filechooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
    }
    //get method
    protected String getJobName() {
        return text.getText().toString().trim();
    }
    //method to check invalid jobname input
    protected boolean jobNameIsEmpty(String s)
    {
        return s.isEmpty();
    }
    //method to determine if name are empty
    protected String getErrorMessage(){
        errorMessage = "";
        if(jobNameIsEmpty(getJobName())){
            errorMessage = "Enter Job Name";
        }
        return errorMessage;
    }
}