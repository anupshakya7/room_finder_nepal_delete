package com.example.roomfindernepalasn;

public class RentRoomData {

    private String roomDescription;
    private String roomLocation;
    private String roomPrice;
    private int roomImage;

    public RentRoomData(String roomDescription, String roomLocation, String roomPrice, int roomImage) {
        this.roomDescription = roomDescription;
        this.roomLocation = roomLocation;
        this.roomPrice = roomPrice;
        this.roomImage = roomImage;
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

    public int getRoomImage() {
        return roomImage;
    }
}
