package com.wecode.bookit.services.serviceImpl;

import com.wecode.bookit.dao.RoomDAO;
import com.wecode.bookit.dao.RoomDAOImpl;
import com.wecode.bookit.model.Rooms;
import com.wecode.bookit.model.Users;
import com.wecode.bookit.services.RoomService;

import java.util.Calendar;

public class RoomServiceImpl implements RoomService {
    Calendar calendar = Calendar.getInstance();
    private RoomDAO roomDAO = new RoomDAOImpl();
    private LoginServiceImpl loginService = new LoginServiceImpl(calendar);


    public Users authenticatedUser;
    public RoomServiceImpl(){

    }
    public RoomServiceImpl(RoomDAO roomDAO, LoginServiceImpl loginService) {

        this.roomDAO = roomDAO;
        this.loginService = loginService;
    }

    //check if the user trying to access the methods is an admin or not
    protected boolean adminAccess() {
        if (authenticatedUser == null || !authenticatedUser.getRole().equalsIgnoreCase("admin")){
            return false;
        }
        return true;
    }


    //enter new room data
    public void addRoom(int id,String roomName, int seatingCapacity) {
        Rooms room = new Rooms(id,roomName, seatingCapacity);
        roomDAO.addRoom(room);
        System.out.println("Room added: " + roomName);
    }

    //update the room data
    public void updateRoom(int roomId, String roomName, int seatingCapacity) {
        roomDAO.updateRoom(roomId, roomName, seatingCapacity);
        System.out.println("Room updated: " + roomName);
    }

    public void deleteRoom(int roomId) {
        roomDAO.deleteRoom(roomId);
        System.out.println("Room deleted: " + roomId);
    }

    //view all the rooms in the system
    public void getAllRooms() {
        roomDAO.getAllRooms().forEach(System.out::println);
    }
}
