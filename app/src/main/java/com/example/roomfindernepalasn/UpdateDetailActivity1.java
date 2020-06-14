package com.example.roomfindernepalasn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UpdateDetailActivity1 extends AppCompatActivity {

    ImageView uploadImage;
    Uri uri;
    EditText txt_Description, txt_Location, txt_Price;
    String imageUrl;
    String key,oldImageUrl;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String roomDescription,roomLocation,roomPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_detail1);

        uploadImage = (ImageView) findViewById(R.id.iv_UploadRoomImage);
        txt_Description = (EditText) findViewById(R.id.uploadRoomDescription);
        txt_Location = (EditText) findViewById(R.id.uploadRoomLocation);
        txt_Price = (EditText) findViewById(R.id.uploadRoomPrice);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            Glide.with(UpdateDetailActivity1.this)
                    .load(bundle.getString("roomImage"))
                    .into(uploadImage);

            txt_Description.setText(bundle.getString("roomDescription"));
            txt_Location.setText(bundle.getString("roomLocation"));
            txt_Price.setText(bundle.getString("roomPrice"));
            key = bundle.getString("key");
            oldImageUrl = bundle.getString("roomImage");

        }

        databaseReference = FirebaseDatabase.getInstance().getReference("SaleRoomDetail").child(key);



    }


    public void btnSelectImage(View view) {

        Intent roomPhotoPicker = new Intent(Intent.ACTION_PICK);
        roomPhotoPicker.setType("image/*");
        startActivityForResult(roomPhotoPicker, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            uri = data.getData();
            uploadImage.setImageURI(uri);

        } else {
            Toast.makeText(this, "You haven't picked image", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnUpdateRoomDetail(View view) {

        roomDescription=txt_Description.getText().toString().trim();
        roomLocation=txt_Location.getText().toString().trim();
        roomPrice=txt_Price.getText().toString().trim();


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Room Detail Updating....");
        progressDialog.show();

        storageReference = FirebaseStorage.getInstance()
                .getReference().child("RoomImage").child(uri.getLastPathSegment());

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

    public void uploadRentRoomDetailMain(){




        RoomDataList roomDataList = new RoomDataList(
                imageUrl,
                roomDescription,
                roomLocation,
                roomPrice

        );

        databaseReference.setValue(roomDataList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                storageReferenceNew.delete();

                Toast.makeText(UpdateDetailActivity1.this,"Data Updated",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}