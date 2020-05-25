package com.example.roomfindernepalasn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RoomDetailActivity extends AppCompatActivity {

    TextView roomDetailDescription;
    ImageView roomDetailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        roomDetailDescription=(TextView)findViewById(R.id.txtDesription);
        roomDetailImage=(ImageView)findViewById(R.id.ivImage1);


        Bundle mBundle = getIntent().getExtras();

        if(mBundle!=null){
            roomDetailDescription.setText(mBundle.getString("Description"));
            //roomDetailImage.setImageResource(mBundle.getInt("Image"));

            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(roomDetailImage);

        }


    }
}
