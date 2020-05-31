package com.example.roomfindernepalasn;

import android.widget.ScrollView;

import java.util.List;

public class RoomDataList {

    private String roomImage;
    private String roomDescription;
    private String roomLocation;
    private String roomPrice;
    private String key;

    public RoomDataList(){

    }

    public RoomDataList(String roomImage, String roomDescription, String roomLocation, String roomPrice) {
        this.roomImage = roomImage;
        this.roomDescription = roomDescription;
        this.roomLocation = roomLocation;
        this.roomPrice = roomPrice;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
