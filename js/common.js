function openNav() {
    document.getElementById("mySidebar").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
}

window.addEventListener('load', function() {
    setTimeout(function() {
        document.getElementById('myloader').style.display = 'none';
    }, 3000); // 3 seconds
});