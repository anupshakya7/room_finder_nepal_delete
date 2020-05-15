package com.example.roomfindernepalasn;







import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


public class Rent_Fragment extends Fragment {

    RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.rent_fragment,container,false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recycleView);

        return view;
    }
}
