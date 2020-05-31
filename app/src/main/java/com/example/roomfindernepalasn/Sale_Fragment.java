package com.example.roomfindernepalasn;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Sale_Fragment extends Fragment {

    RecyclerView recyclerView1;
    EditText text_search;
    List<RoomDataList> myRoomList2;
    FloatingActionButton btnFloating;

    private DatabaseReference databaseReference1;
    private ValueEventListener eventListener1;
    RoomAdapter roomAdapter1;
    ProgressDialog progressDialog1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.sale_fragment,container,false);

        btnFloating=view.findViewById(R.id.floatingBtn);
        text_search=view.findViewById(R.id.search_bar1);
        recyclerView1=view.findViewById(R.id.recycleViewSale);

        GridLayoutManager gridLayoutManager1=new GridLayoutManager(getContext(),1);
        recyclerView1.setLayoutManager(gridLayoutManager1);

        progressDialog1 = new ProgressDialog(view.getContext());
        progressDialog1.setMessage("Loading Items....");

        myRoomList2= new ArrayList<>();

        final RoomAdapter1 roomAdapter1= new RoomAdapter1(Sale_Fragment.this,myRoomList2);
        recyclerView1.setAdapter(roomAdapter1);


        databaseReference1 = FirebaseDatabase.getInstance().getReference("SaleRoomDetail");

        progressDialog1.show();

        eventListener1 = databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRoomList2.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){
                    RoomDataList roomDataList = itemSnapshot.getValue(RoomDataList.class);
                    roomDataList.setKey(itemSnapshot.getKey());
                    myRoomList2.add(roomDataList);

                }

                roomAdapter1.notifyDataSetChanged();
                progressDialog1.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog1.dismiss();
            }
        });



        btnFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),Upload_RoomData.class));
            }
        });



        return view;
    }
}
