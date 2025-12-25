# wecode-hack : BookIt

# 🤖 Automated meeting room booking system: 

<h2>Problem Scope :</h2>
The system automates the procedure of booking a meeting room in a company. Employees can search and book a meeting room based on their requirements

<h1>Description</h1>
<p>There should be three types of users in the system like Admin, Managers and Members
  <li>Admin should be able to create and configure meeting rooms.</li>
  <li>The manager should be able to book a meeting room as per the requirements such as seating capacity, amenities- projector, conference call facility, whiteboard, water dispenser, TV etc.</li> 
 <li>Credits should be required to book a room. You can refer below information about credits.</li>
<li>If the role is manager, credits should be inserted as 2000, other users will have 0 credits.</li>
<li>Members should not be able to book the room.</li>
<li>Booked rooms status should be recorded and displayed.</li>
<li>Meeting rooms schedule should be displayed</li>
<ul>The system should also maintain the additional information of meeting rooms like the meeting room has a per hour cost (in credits) based on the amenities.
<li>Credits per amenities: The per hour cost is the sum of credits based on the amenities.</li>
<li>Seating capacity <=5   =      0 credits</li>
<li>Seating capacity > 5<= 10    =  10 credits</li>
<li>Seating capacity > 10    =  20 credits</li>
<li>Projector  =   5 credits</li>
<li>Wi-Fi Connection   =  10 credits</li>
<li>Conference call facility  ====== 15 credits</li>
<li>Whiteboard  ==== 5 credits</li>
<li>Water dispenser ==== 5 credits </li>
<li>TV ==== 10 credits</li>
<li>Coffee machine  ===== 10 credits</li>  
</ul>

<li>Users of type managers have 2000 credits points, when booking the room, they will have to pay per hour cost from these credits,</li>
<li>The manager credits are renewed back to 2000 points every Monday morning.</li>

<li> Mandatory Amenities required based on meeting type
<li>Classroom training ----- Whiteboard projector </li>
<li>Online training ----- wifi and projector </li>
<li>Conference call ------- conference call facility </li>
<li>Business ------- Projector</li>
</li>
</p>
