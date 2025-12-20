package com.hsbc.bookit.services;

public interface RoomService {
    public void addRoom(int id,String roomName, int seatingCapacity);
    public void updateRoom(int roomId, String roomName, int seatingCapacity);
    public void deleteRoom(int roomId);
    public void getAllRooms();
}
