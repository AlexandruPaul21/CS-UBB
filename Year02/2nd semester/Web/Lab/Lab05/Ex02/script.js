$('#send_btn').on('click', function() {
    let name = $('#name').val();
    let age = $('#age').val();
    let birthdate = $('#birthdate').val();
    let email = $('#email').val();

    const nameRegex = /^[A-Za-z ,.\'-]+$/;
    const ageRegex = /^[1-9][0-9]*$/;
    const birthdateRegex = /^[0-3][0-9]-[0-1][0-9]-[1-2][0-9]{3}$/;
    const emailRegex = /^[a-zA-Z.0-9]+@[a-zA-z.]+\.[a-z]+$/;

    let err = "";
    if (!name.match(nameRegex)) {
        console.log("Invalid name");
        err += "name ";
        $('#name').css('border-color', 'red');
    } else {
        $('#name').css('border-color', 'black');
    }

    if (!age.match(ageRegex)) {
        console.log("Invalid age");
        err += "age ";
        $('#age').css('border-color', 'red');
    } else {
        $('#age').css('border-color', 'black');
    }

    if (!birthdate.match(birthdateRegex)) {
        err += "birthdate ";
        console.log("Invalid birthdate");
        $('#birthdate').css('border-color', 'red');
    } else {
        $('#birthdate').css('border-color', 'black');
    }

    if (!email.match(emailRegex)) {
        err += "email ";
        console.log("Invalid email");
        $('#email').css('border-color', 'red');
    } else {
        $('#email').css('border-color', 'black');
    }

    if (err !== "") {
        console.log("Fields: " + err + "are not valid");
        $('#error_text').text("Fields: " + err + "are not valid");
    } else {
        console.log("All fields are valid");
        $('#error_text').text("All fields are valid");
    }
});
