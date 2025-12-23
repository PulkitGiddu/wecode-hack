package com.wecode.bookit;

import com.wecode.bookit.model.Amenities;
import com.wecode.bookit.model.Meetings;
import com.wecode.bookit.model.Users;
import com.wecode.bookit.exceptions.SameDateTimeException;
import com.wecode.bookit.exceptions.WrongDateFormatException;
import com.wecode.bookit.services.*;
import com.wecode.bookit.services.serviceImpl.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

//testing all the methods for now
public class MainApp {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        Calendar calendar = Calendar.getInstance();
        LoginServiceImpl loginService = new LoginServiceImpl(calendar);

        // Authentication
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        Users authenticatedUser = loginService.login(username, password);
        loginService.resetCredits(authenticatedUser);

        // Services initialization
        RoomService roomService = new RoomServiceImpl();
        AmenityService amenityService = new AmenityServiceImpl();
        UserService userService = new UserServiceImpl(authenticatedUser);
        MeetingService meetingService = new MeetingServiceImpl(authenticatedUser);

        boolean exit = false;

        while (!exit) {
            System.out.println("\n---- Booking System Menu ----");
            System.out.println("1. Admin Menu");
            System.out.println("2. Manager Menu");
            System.out.println("3. Member Menu");
            System.out.println("4. Exit");
            System.out.print("Choose your option: ");
            int choice1 = Integer.parseInt(scanner.nextLine());

            switch (choice1) {
                case 1:
                    if (authenticatedUser.getRole().equalsIgnoreCase("admin")) {
                        adminMenu(scanner, userService, roomService, meetingService);
                    } else {
                        System.out.println("Access denied. Admin access only.");
                    }
                    break;

                case 2:
                    if (authenticatedUser.getRole().equalsIgnoreCase("manager") || authenticatedUser.getRole().equalsIgnoreCase("admin")) {
                        try {
                            managerMenu(scanner, roomService, amenityService, meetingService);
                        } catch (SameDateTimeException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("Access denied. Manager/Admin access only.");
                    }
                    break;

                case 3:
                     memberMenu(scanner, meetingService);
                    break;

                case 4:
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }

        scanner.close();
    }


    private static void adminMenu(Scanner scanner, UserService userService, RoomService roomService, MeetingService meetingService) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n---- Admin Menu ----");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. View All Users");
            System.out.println("4. Get User by Username");
            System.out.println("5. Add Room");
            System.out.println("6. Update Room");
            System.out.println("7. Delete Meeting");
            System.out.println("8. Back to Main Menu");
            System.out.print("Choose your option: ");
            int adminOption = Integer.parseInt(scanner.nextLine());

            switch (adminOption) {
                case 1:
                    System.out.println("Enter User ID:");
                    String userId = scanner.nextLine();
                    System.out.println("Enter UserName:");
                    String username = scanner.nextLine();
                    System.out.println("Enter User Password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter Name:");
                    String Name = scanner.nextLine();
                    System.out.println("Enter User Email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter User PhoneNumber:");
                    String phone = scanner.nextLine();
                    System.out.println("Enter User Role:");
                    String role = scanner.nextLine();
                    System.out.println("Enter Credits:");
                    int credits = Integer.parseInt(scanner.nextLine());
                    userService.addUserdata(userId,username,password,Name,email,phone,role,credits);
                    break;
                case 2:
                    System.out.println("Enter UserName to delete:");
                    String user_name = scanner.nextLine();
                    userService.deleteUserdata(user_name);
                    break;
                case 3:
                    userService.getAllUsers();
                    break;
                case 4:
                    System.out.println("Enter UserName to Search :");
                    String userName = scanner.nextLine();
                   userService.getUsersByUsername(userName);
                    break;
                case 5:
                    System.out.println("Enter Room ID:");
                    int roomId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter Room Name:");
                    String roomName = scanner.nextLine();
                    System.out.println("Enter Seating Capacity:");
                    int seatingCapacity = Integer.parseInt(scanner.nextLine());
                    roomService.addRoom(roomId, roomName, seatingCapacity);
                    break;
                case 6:
                    System.out.println("Enter Room ID to update:");
                    roomId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new Room Name:");
                    roomName = scanner.nextLine();
                    System.out.println("Enter new Seating Capacity:");
                    seatingCapacity = Integer.parseInt(scanner.nextLine());
                    roomService.updateRoom(roomId, roomName, seatingCapacity);
                    break;
                case 7:
                    System.out.println("Enter Meeting ID to delete:");
                    String meetingId = scanner.nextLine();
                    meetingService.deleteMeeting(meetingId);
                    break;
                case 8:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static void managerMenu(Scanner scanner, RoomService roomService, AmenityService amenityService, MeetingService meetingService) throws SameDateTimeException {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n---- Manager Menu ----");
            System.out.println("1. View All Rooms");
            System.out.println("2. View All Amenities");
            System.out.println("3. Book Meeting with Default Room");
            System.out.println("4. Book Meeting with Custom Room");
            System.out.println("5. View All Meetings");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose your option: ");
            int managerOption = Integer.parseInt(scanner.nextLine());

            switch (managerOption) {
                case 1:
                    roomService.getAllRooms();
                    break;
                case 2:
                    List<Amenities> amenities = amenityService.viewAllAmenities();
                    amenities.forEach(System.out::println);
                    break;
                case 3:
                   // bookMeetingWithDefaultRoom(scanner, meetingService);
                    System.out.println("Enter Meeting ID:");
                    int meetingId = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter Room ID:");
                    int roomId = Integer.parseInt(scanner.nextLine());
                    Timestamp startTime = getTimestamp(scanner, "start");
                    Timestamp endTime = getTimestamp(scanner, "end");

                    System.out.println("Choose Default Room Option:");
                    for (MeetingServiceImpl.DefaultRoom option : MeetingServiceImpl.DefaultRoom.values()) {
                        System.out.println(option.name() + " - " + option.getAmenities() + " - " + option.getCost() + " credits");
                    }
                    String roomOptionName = scanner.nextLine();
                    MeetingServiceImpl.DefaultRoom roomOption = MeetingServiceImpl.DefaultRoom.valueOf(roomOptionName);

                    meetingService.bookMeetingWithDefaultRoom(meetingId, roomId, startTime, endTime, roomOption);
                    break;

                case 4:
                    System.out.println("Enter Meeting ID:");
                    int meetingIdC = Integer.parseInt(scanner.nextLine());

                    System.out.println("Enter Room ID:");
                    int roomIdC = Integer.parseInt(scanner.nextLine());

                    Timestamp startTimeC = getTimestamp(scanner, "start");
                    Timestamp endTimeC = getTimestamp(scanner, "end");

                    System.out.println("Enter Seating Capacity:");
                    int seatingCapacity = Integer.parseInt(scanner.nextLine());

                    System.out.println("Select Amenities (comma separated):");
                    List<Amenities> availableAmenities = amenityService.viewAllAmenities();

                    availableAmenities.forEach(amenity -> System.out.println(amenity.getName() + " - " + amenity.getCost() + " credits"));
                    String[] selectedAmenitiesArray = scanner.nextLine().split(",");
                    List<String> selectedAmenities = Arrays.asList(selectedAmenitiesArray);

                    meetingService.bookMeetingWithCustomRoom(meetingIdC, roomIdC, startTimeC, endTimeC, selectedAmenities, seatingCapacity);
                  //  bookMeetingWithCustomRoom(scanner, amenityService, meetingService);
                    break;
                case 5:
                    List<Meetings> allMeetings = meetingService.viewMeetings();
                    allMeetings.forEach(System.out::println);
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static void memberMenu(Scanner scanner, MeetingService meetingService) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n---- Member Menu ----");
            System.out.println("1. View All Meetings");
            System.out.println("2. Back to Main Menu");
            System.out.print("Choose your option: ");
            int memberOption = Integer.parseInt(scanner.nextLine());

            switch (memberOption) {
                case 1:
                    List<Meetings> allMeetings = meetingService.viewMeetings();
                    allMeetings.forEach(System.out::println);
                    break;
                case 2:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static Timestamp getTimestamp(Scanner scanner, String type) {
        System.out.println("Enter date and time (yyyy-MM-dd HH:mm:ss) :");
        String input = scanner.nextLine();

        try {
            LocalDateTime dateTime = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return Timestamp.valueOf(dateTime);
        } catch (DateTimeParseException e) {
            throw new WrongDateFormatException("Invalid  date and time format. Please use 'yyyy-MM-dd HH:mm:ss'.");
        }
    }



}
