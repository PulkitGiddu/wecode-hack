function addAmenity(){
    let ans = prompt("Are you sure you want to add an amenity? (y/n)");
    if(ans.toLowerCase == "y" || ans.toLowerCase() == "yes"){
        //  submit form
    }
    location.replace("./manageamenities.html");
}

window.addEventListener('load', function() {
    setTimeout(function() {
        document.getElementById('myloader').style.display = 'none';
    }, 3000); // 3 seconds
});