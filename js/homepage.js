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

let btnOne = document.getElementsByClassName("btnOne");
let btnTwo = document.getElementsByClassName("btnTwo");
let btnThree = document.getElementsByClassName("btnThree");

function myFunction() {
    document.getElementById("imgTobeSet").setAttribute("src", "./assets/img/481602-PH02WC-347.jpg");
  }

  function myFunction2() {
    document.getElementById("imgTobeSet").setAttribute("src", "./assets/img/4214796.jpg");
  }

  function myFunction3() {
    document.getElementById("imgTobeSet").setAttribute("src", "assets/img/6895861.jpg");
  }

document.getElementById("btnOne").addEventListener("click", myFunction);
document.getElementById("btnTwo").addEventListener("click", myFunction2);
document.getElementById("btnThree").addEventListener("click", myFunction3);
