package com.example.roomfindernepalasn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Upload_RoomData extends AppCompatActivity {

    ImageView uploadImage;
    Uri uri;
    EditText txt_Description,txt_Location,txt_Price;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__room_data);

        uploadImage=(ImageView)findViewById(R.id.iv_UploadRoomImage);
        txt_Description=(EditText) findViewById(R.id.uploadRoomDescription);
        txt_Location=(EditText)findViewById(R.id.uploadRoomLocation);
        txt_Price=(EditText)findViewById(R.id.uploadRoomPrice);

    }

    public void btnSelectImage(View view) {

        Intent roomPhotoPicker = new Intent(Intent.ACTION_PICK);
        roomPhotoPicker.setType("image/*");
        startActivityForResult(roomPhotoPicker,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            uri = data.getData();
            uploadImage.setImageURI(uri);

        }
        else{
            Toast.makeText(this,"You haven't picked image",Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadImage(){




        StorageReference storageReference= FirebaseStorage.getInstance()
                .getReference().child("RoomImage").child(uri.getLastPathSegment());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Room Detail Uploading....");
        progressDialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                    uploadRentRoomDetailMain();
                    progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });
    }

    public void btnUploadRoomDetail(View view) {

        uploadImage();
    }

    public void uploadRentRoomDetailMain(){




        RoomDataList roomDataList = new RoomDataList(
                imageUrl,
                txt_Description.getText().toString(),
                txt_Location.getText().toString(),
                txt_Price.getText().toString()

        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("RentRoomDetail")
                .child(myCurrentDateTime).setValue(roomDataList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(Upload_RoomData.this,"Room Detail Uploaded",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Upload_RoomData.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
