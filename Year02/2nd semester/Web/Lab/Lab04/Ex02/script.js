document.getElementById("send_btn").addEventListener("click", () => {
    let name = document.getElementById("name").value;
    let age = document.getElementById("age").value;
    let birthdate = document.getElementById("birthdate").value;
    let email = document.getElementById("email").value;

    const nameRegex = '^[A-Za-z ,.\'-]+$';
    const ageRegex = '^[1-9][0-9]*$';
    const birthdateRegex = '^[0-3][0-9]-[0-1][0-9]-[1-2][0-9]{3}$';
    const emailRegex = '^[a-zA-Z.0-9]+@[a-zA-z.]+.[a-z]+';

    let err = "";
    if (!name.match(nameRegex)) {
        console.log("Invalid name");
        err += "name ";
        document.getElementById("name").style.borderColor = "red";
    } else {
        document.getElementById("name").style.borderColor = "black";
    }

    if (!age.match(ageRegex)) {
        console.log("Invalid age");
        err += "age ";
        document.getElementById("age").style.borderColor = "red";
    } else {
        document.getElementById("age").style.borderColor = "black";
    }

    if (!birthdate.match(birthdateRegex)) {
        err += "birthdate ";
        console.log("Invalid birthdate");
        document.getElementById("birthdate").style.borderColor = "red";
    } else {
        document.getElementById("birthdate").style.borderColor = "black";
    }

    if (!email.match(emailRegex)) {
        err += "email ";
        console.log("Invalid email");
        document.getElementById("email").style.borderColor = "red";
    } else {
        document.getElementById("email").style.borderColor = "black";
    }
    
    if (err !== "") {
        console.log("Fields: " + err + "are not valid");
        document.getElementById("error_text").innerText = "Fields: " + err + "are not valid";
    } else {
        console.log("All fields are valid");
        document.getElementById("error_text").innerText = "All fields are valid";
    }
})