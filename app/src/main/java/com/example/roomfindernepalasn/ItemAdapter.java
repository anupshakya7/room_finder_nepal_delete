package com.example.roomfindernepalasn;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    List<RentRoomData> myRoomList;

    public ItemAdapter(List<RentRoomData> myRoomList){
        this.myRoomList=myRoomList;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {

        holder.roomImage.setImageResource(myRoomList.get(position).getRoomImage());
        holder.roomDescription.setText(myRoomList.get(position).getRoomDescription());
        holder.roomLocation.setText(myRoomList.get(position).getRoomLocation());
        holder.roomPrice.setText(myRoomList.get(position).getRoomPrice());




    }

    @Override
    public int getItemCount() {
        return myRoomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView roomImage;
        TextView roomDescription,roomLocation,roomPrice;
        CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roomImage=itemView.findViewById(R.id.ivImage);
            roomDescription=itemView.findViewById(R.id.tvDescription);
            roomLocation=itemView.findViewById(R.id.tvLocation);
            roomPrice=itemView.findViewById(R.id.tvPrice);

        }
    }
}
