-- use only once
create database bookitdb; 
use bookitdb;
CREATE TABLE Users (
    id VARCHAR(10) PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(50),
    role ENUM('Admin', 'Manager', 'Member'),
    credits INT
);

CREATE TABLE Rooms (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255),
    seating_capacity INT
);

CREATE TABLE Amenities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    cost INT
);

CREATE TABLE RoomAmenities (
    room_id INT,
    amenity_id INT,
    FOREIGN KEY (room_id) REFERENCES Rooms(id),
    FOREIGN KEY (amenity_id) REFERENCES Amenities(id),
    PRIMARY KEY (room_id, amenity_id)
);

CREATE TABLE Meetings (
    id VARCHAR(10) PRIMARY KEY,
    room_id VARCHAR(10),
    manager_id VARCHAR(10),
    start_time DATETIME,
    end_time DATETIME,
    status VARCHAR(50),
    FOREIGN KEY (room_id) REFERENCES Rooms(id),
    FOREIGN KEY (manager_id) REFERENCES Users(id)
);

-- Adding data

INSERT INTO Users (id, username, password, name, email, phone, role, credits) VALUES 
("1", 'johndoe', 'password123', 'John Doe', 'john.doe@example.com', '123-456-7890', 'Admin', 0),
("2", 'janesmith', 'password456', 'Jane Smith', 'jane.smith@example.com', '987-654-3210', 'Manager', 2000),
("3", 'alicejohnson', 'password789', 'Alice Johnson', 'alice.johnson@example.com', '555-555-5555', 'Member', 0),
("4", 'bobwilliams', 'password321', 'Bob Williams', 'bob.williams@example.com', '444-444-4444', 'Member', 0);

INSERT INTO Rooms (id,name, seating_capacity) VALUES 
("1",'Conference Room A', 10),
("2",'Conference Room B', 20),
("3",'Executive Room', 5);

INSERT INTO Amenities (name, cost) VALUES 
('Projector', 10),
('Whiteboard', 5),
('Coffee Machine', 15),
('Video Conferencing', 25);

INSERT INTO Meetings (id,room_id, manager_id, start_time, end_time, status) VALUES 
(1,"1", 2, '2024-08-20 09:00:00', '2024-08-20 10:00:00', 'Scheduled'),
(2,"2", 2, '2024-08-21 14:00:00', '2024-08-21 15:30:00', 'Scheduled'),
(3,"3", 2, '2024-08-22 11:00:00', '2024-08-22 12:00:00', 'Scheduled');


select * from Meetings;
select * from users;
select * from rooms;


drop database bookitdb;
