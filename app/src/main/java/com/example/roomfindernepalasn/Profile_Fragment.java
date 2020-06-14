package com.example.roomfindernepalasn;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URISyntaxException;


public class Profile_Fragment extends Fragment {
    Button logoutBtn;
    TextView txtFullName,txtUserName,txtEmail,txtSetting;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private static final String USER="users";
    String user="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.profile_fragment,container,false);

        Intent intent = null;
        try {
            intent = Intent.getIntent(user);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        final String email = intent.getStringExtra("email");


        logoutBtn =(Button) view.findViewById(R.id.btnLogOut);
        txtFullName =(TextView) view.findViewById(R.id.title);
        txtUserName =(TextView) view.findViewById(R.id.userName);
        txtEmail =(TextView) view.findViewById(R.id.emailA);
        txtSetting =(TextView) view.findViewById(R.id.settingsa);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(USER);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    if(ds.child("Email").getValue().equals(email)){
                        txtFullName.setText(ds.child("FullName").getValue(String.class));
                        txtUserName.setText(ds.child("UserName").getValue(String.class));
                        txtEmail.setText(ds.child("Email").getValue(String.class));

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Login_Page.class));
                Toast.makeText(getContext(),"Logout Sucessfully",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
