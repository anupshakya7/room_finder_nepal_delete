package com.example.roomfindernepalasn;







import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Rent_Fragment extends Fragment {

    RecyclerView recyclerView;
    List<RoomDataList> myRoomList1;
    RoomDataList mRoomDataList;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    RoomAdapter roomAdapter;
    EditText txt_Search;



    FloatingActionButton mFloatActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rent_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        mFloatActionButton = (FloatingActionButton)view.findViewById(R.id.floatingBtn);
        txt_Search=(EditText)view.findViewById(R.id.search_bar);


//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading Items....");

        myRoomList1 = new ArrayList<>();


        

//        mRoomDataList = new RoomDataList(R.drawable.room2,"Top Floor","Patan","Rs. 10000");
//        myRoomList1.add(mRoomDataList);
//
//        mRoomDataList= new RoomDataList(R.drawable.room3,"Bottom Floor","Kathmandu","Rs. 20000");
//        myRoomList1.add(mRoomDataList);

        final RoomAdapter roomAdapter = new RoomAdapter(Rent_Fragment.this,myRoomList1);
        recyclerView.setAdapter(roomAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("RentRoomDetail");

        progressDialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRoomList1.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    RoomDataList roomDataList = itemSnapshot.getValue(RoomDataList.class);
                    roomDataList.setKey(itemSnapshot.getKey());
                    myRoomList1.add(roomDataList);

                }

                roomAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });





        mFloatActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(v.getContext(),Upload_RoomData.class));
            }
        });

        return view;
    }


    }



