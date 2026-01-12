function showCreateRoom() {
    document.getElementById('createRoomDiv').style.display = 'block';
    document.getElementById('editRoomDiv').style.display = 'none';
    document.getElementById('allRoomsInfo').style.display = 'none';
}

function showEditRoom() {
    document.getElementById('createRoomDiv').style.display = 'none';
    document.getElementById('editRoomDiv').style.display = 'block';
    document.getElementById('allRoomsInfo').style.display = 'none';
}
  
window.addEventListener('load', function() {
    setTimeout(function() {
        document.getElementById('myloader').style.display = 'none';
    }, 3000); // 3 seconds
});


function resetManagerCredits(){
    let ans = prompt("Are you sure you want to reset all managers' credits? (y/n)");
    if(ans.toLowerCase == "y" || ans.toLowerCase() == "yes"){
        // submit form here
    }
    location.replace("./admindashboard.html");
}