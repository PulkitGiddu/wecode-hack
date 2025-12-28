//package com.wecode.bookit.services.serviceImpl;
//
//import com.wecode.bookit.dao.*;
//import com.wecode.bookit.model.Meetings;
//import com.wecode.bookit.model.Rooms;
//import com.wecode.bookit.model.Users;
//import com.wecode.bookit.exceptions.AccessDeniedException;
//import com.wecode.bookit.exceptions.NotEnoughCreditsException;
//import com.wecode.bookit.exceptions.SameDateTimeException;
//import com.wecode.bookit.services.MeetingService;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//public class MeetingServiceImpl implements MeetingService {
//
//    List<Meetings> meetings;
//    private int seatingcost;
//
//
//    private UserDAO userDAO = new UserDAOImpl();
//    private MeetingDAO meetingDAO = new MeetingDAOImpl();
//    private AmenityServiceImpl amenityService = new AmenityServiceImpl();
//    private RoomDAO roomDAO = new RoomDAOImpl();
//
//    Users authenticatedUser;
//
//    public MeetingServiceImpl(Users authenticatedUser) {
//        this.authenticatedUser = authenticatedUser;
//    }
//
//    public MeetingServiceImpl(Users authenticatedUser, UserDAO userDAO, MeetingDAO meetingDAO, AmenityServiceImpl amenityService, RoomDAO roomDAO) {
//        if (authenticatedUser == null) throw new IllegalArgumentException("Authenticated user cannot be null");
//        this.authenticatedUser = authenticatedUser;
//        this.userDAO = userDAO;
//        this.meetingDAO = meetingDAO;
//        this.amenityService = amenityService;
//        this.roomDAO = roomDAO;
//    }
//
//    public boolean adminAccess() {
//        if (authenticatedUser == null || !authenticatedUser.getRole().equalsIgnoreCase("admin")) {
//            return false;
//        }
//        return true;
//    }
//
//    protected boolean checkAccess() {
//        if (authenticatedUser == null ||
//                (!authenticatedUser.getRole().equalsIgnoreCase("manager") &&
//                        !authenticatedUser.getRole().equalsIgnoreCase("admin"))) {
//            throw new AccessDeniedException("You do not have access to this feature!");
//        }
//        return true;
//    }
//
//    // ======================================================================= //
//    // Enum to define default room options
//    public enum DefaultRoom {
//        CLASSROOM_TRAINING("Whiteboard, Projector", 10),
//        ONLINE_TRAINING("Wifi, Projector", 15),
//        CONFERENCE_CALL("Conference Call", 15),
//        BUSINESS_CALL("Projector", 5);
//
//        private final String amenities;
//        private final int cost;
//
//        DefaultRoom(String amenities, int cost) {
//            this.amenities = amenities;
//            this.cost = cost;
//        }
//
//        public String getAmenities() {
//            return amenities;
//        }
//
//        public int getCost() {
//            return cost;
//        }
//    }
//
//    // ==============================================================================================================//
//    // Function to get predefined room options
//    public List<Rooms> getDefaultRoomOptions() {
//        return roomDAO.getDefaultRooms();
//    }
//
//    // Function to select and book a predefined room
//    public void bookMeetingWithDefaultRoom(int id, int roomId, Timestamp startTime, Timestamp endTime, DefaultRoom roomOption) throws NotEnoughCreditsException, SameDateTimeException {
//        checkAccess();
//
//        if (startTime.equals(endTime)) {
//            throw new SameDateTimeException("Start time and end time cannot be the same.");
//        }
//
//        // Calculate total cost based on default room option
//        int totalCost = roomOption.getCost();
//
//        Meetings meeting = new Meetings(id, roomId, authenticatedUser.getId(),startTime,endTime,"Scheduled");
//        meeting.setId(id);
//        meeting.setRoomId(roomId);
//        meeting.setManagerId(authenticatedUser.getId());
//        meeting.setStartTime(startTime);
//        meeting.setEndTime(endTime);
//        meeting.setStatus("Scheduled");
//
//        int credits_remaining = authenticatedUser.getCredits() - totalCost;
//
//        if (credits_remaining < 0) {
//            throw new NotEnoughCreditsException("You do not have enough credits!");
//        }
//
//        if (adminAccess()) {
//            meetingDAO.addMeeting(meeting);
//            System.out.println("Meeting booked successfully, Remaining credits: " + authenticatedUser.getCredits());
//        } else {
//            meetingDAO.addMeeting(meeting);
//            userDAO.updateUserCredits(authenticatedUser, credits_remaining);
//            System.out.println("Meeting booked successfully with default room: " + roomOption.name() +
//                    ". Total cost: " + totalCost + " credits. Remaining credits: " + credits_remaining);
//        }
//    }
//    // =========================================================================================================== //
//    // Function to book meeting with custom options (seating capacity and selected amenities)
//    public void bookMeetingWithCustomRoom(int id, int roomId, Timestamp startTime, Timestamp endTime, List<String> selectedAmenities, int seatingCapacity) throws SameDateTimeException {
//        checkAccess();
//
//        if (startTime.equals(endTime)) {
//            throw new SameDateTimeException("Start time and end time cannot be the same.");
//        }
//
//        int totalCostOfAmenities = amenityService.chooseAmenitiesAndCalculateCredits(selectedAmenities);
//
//        // Calculate seating cost based on seating capacity
//        if (seatingCapacity <= 5)
//            seatingcost = 0;
//        if (seatingCapacity > 5 && seatingCapacity <= 10)
//            seatingcost = 10;
//        if (seatingCapacity > 10)
//            seatingcost = 20;
//
//        int totalCost = seatingcost + totalCostOfAmenities;
//
//        Meetings meeting = new Meetings(id,roomId,authenticatedUser.getId(),startTime,endTime,"Scheduled");
//        meeting.setId(id);
//        meeting.setRoomId(roomId);
//        meeting.setManagerId(authenticatedUser.getId());
//        meeting.setStartTime(startTime);
//        meeting.setEndTime(endTime);
//        meeting.setStatus("Scheduled");
//
//        int credits_remaining = authenticatedUser.getCredits() - totalCost;
//        if (credits_remaining < 0) {
//            throw new NotEnoughCreditsException("You do not have enough credits!");
//        }
//
//        if (adminAccess()) {
//            meetingDAO.addMeeting(meeting);
//            System.out.println("Meeting booked successfully, Remaining credits: " + authenticatedUser.getCredits());
//        } else {
//            meetingDAO.addMeeting(meeting);
//            userDAO.updateUserCredits(authenticatedUser, credits_remaining);
//            System.out.println("Meeting booked successfully with total cost: " + totalCost + " credits. Remaining credits: " + credits_remaining);
//        }
//    }
//
//    // Function to delete a meeting
//    public void deleteMeeting(String id) {
//        adminAccess();
//        meetingDAO.removeMeeting(id);
//    }
//
//    //method to view all meetings(accessible to everyone)
//    public List<Meetings> viewMeetings(){
//        meetings = meetingDAO.viewAllMeetings();
//        return meetings;
//    }
//}




/*
*  -> /manager/createMeeting
*  -> /manager/viewAllMeeting/{managerId}
*  -> /manager/getTeamByName  -> ankit@gmail.com -> DB search by email - show result
*
* -> select credit to move -> 100, 200, etc
* -> onClick assign -> deduct amount from manger credits and move credits to that member id we will get from finding email.
* 
* */
