function removeAmenity(){
    let ans = prompt("Are you sure you want to delete Amenity: "+document.getElementById("selectAmenity").value + " ? (y/n)");
    if(ans.toLowerCase == "y" || ans.toLowerCase() == "yes"){
        // submit form here
    }
    location.replace("./manageamenities.html");
}


window.addEventListener('load', function() {
    setTimeout(function() {
        document.getElementById('myloader').style.display = 'none';
    }, 3000); // 3 seconds
});