function validateinput() {

    var alerts = "", shouldShowAlert = false;
    var emailField = document.getElementById("email").value;
    console.log(emailField + " : ");

    if (!emailField.includes("@") || !emailField.includes(".")) {
        alerts += ("Write valid email!");
        shouldShowAlert = true;
    }

    var nameField = document.getElementById("password").value;
    console.log(nameField);
    if (nameField.length < 3) {
        alerts += ("Password field cant be less than 3 digits long!");
        shouldShowAlert = true;
    }

    var usertype = document.querySelectorAll('input[name="userType"]:checked');


    var rates = document.getElementsByName('usertype');
    var selectedRadioBtn;
    for (var i = 0; i < rates.length; i++) {
        if (rates[i].checked) {
            selectedRadioBtn = rates[i].value;
        }
    }


    if (shouldShowAlert) {
        alert(alerts);
    } else {
        // console.log("usertype : "+ usertype);
        if (selectedRadioBtn == "admin") {
            if (window.navigate) window.navigate('/dashboard'); else location.replace("admindashboard.html");
        } else if (selectedRadioBtn == "manager") {
            if (window.navigate) window.navigate('/dashboard'); else location.replace("manager-dashboard.html");
        } else if (selectedRadioBtn == "member") {
            if (window.navigate) window.navigate('/dashboard'); else location.replace("member-dashboard.html");
        } else {
            alert("Wronge checkbox selected!")
        }
        // location.replace("admindashboard.html")
    }

}

// Attach to window for React
window.validateinput = validateinput;