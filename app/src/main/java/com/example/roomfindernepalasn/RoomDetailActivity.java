package com.example.roomfindernepalasn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RoomDetailActivity extends AppCompatActivity {

    TextView roomDetailDescription,roomDetailLocation,roomDetailPrice;
    ImageView roomDetailImage;
    String key="";
    String imageUrl="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        roomDetailDescription=(TextView)findViewById(R.id.txtDescription);
        roomDetailLocation=(TextView)findViewById(R.id.txtLocation);
        roomDetailPrice=(TextView)findViewById(R.id.txtPrice);
        roomDetailImage=(ImageView)findViewById(R.id.ivImage1);


        Bundle mBundle = getIntent().getExtras();

        if(mBundle!=null){

            roomDetailDescription.setText(mBundle.getString("Description"));
            roomDetailLocation.setText(mBundle.getString("Location"));
            roomDetailPrice.setText(mBundle.getString("Price"));
            key = mBundle.getString("keyValue");

            imageUrl = mBundle.getString("Image");
            //roomDetailImage.setImageResource(mBundle.getInt("Image"));

            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(roomDetailImage);

        }


    }



    public void updateBtn(View view) {

        startActivity(new Intent(getApplicationContext(),UpdateDetailActivity.class)
        .putExtra("roomDescription",roomDetailDescription.getText().toString())
        .putExtra("roomLocation",roomDetailLocation.getText().toString())
                .putExtra("roomPrice",roomDetailPrice.getText().toString())
                .putExtra("roomImage",imageUrl)
                .putExtra("key",key)
        );

        finish();

    }

//    public void btnDeleteRoomItem(View view) {
//
//        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RentRoomDetail");
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//
//        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
//
//        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//
//                reference.child(key).removeValue();
//                Toast.makeText(RoomDetailActivity.this,"Room Detail Deleted",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(),Rent_Fragment.class));
//                finish();
//
//            }
//        });
//
//
//    }


    public void btnDeleteRoom(View view) {

        final DatabaseReference reference =FirebaseDatabase.getInstance().getReference("RentRoomDetail");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                reference.child(key).removeValue();
                Toast.makeText(RoomDetailActivity.this,"Room Detail Detele",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));

                finish();

            }
        });

    }
}
