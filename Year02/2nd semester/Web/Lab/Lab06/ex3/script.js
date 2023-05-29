let selectElement = document.getElementById("selectId");
let nume = document.getElementById("inputName");
let prenume = document.getElementById("inputSurname");
let telefon = document.getElementById("inputTelephone");
let email = document.getElementById("inputEmail");
let saveButton = document.getElementById("saveButton");

nume.addEventListener("input", enableSaveButton);
prenume.addEventListener("input", enableSaveButton);
telefon.addEventListener("input", enableSaveButton);
email.addEventListener("input", enableSaveButton);

function enableSaveButton() {
    saveButton.disabled = false;
}

function disableSaveButton() {
    saveButton.disabled = true;
}

let ID = 0;

function getIds() {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                let response = JSON.parse(request.responseText);

                for (let i = 0; i < response.length; i++) {
                    let option = document.createElement("option");
                    option.value = response[i];
                    option.text = response[i];
                    selectElement.appendChild(option);
                }
            }
        }
    }
    request.open('GET', 'getIDs.php');
    request.send();
}

function getIdsJq() {
    $.ajax({
        url: "getIDs.php",
        dataType: "json",
        success: function(response) {
            for (let i = 0; i < response.length; i++) {
                let option = document.createElement("option");
                option.value = response[i];
                option.text = response[i];
                selectElement.appendChild(option);
            }
        }
    });
}


function getPerson() {
    if (saveButton.disabled === false) {
        let confirmed = confirm("You have unsaved changes. Do you want to save them?");
        if (confirmed) {
            savePersonJq();
        }
    }
    let request = new XMLHttpRequest();
    ID = selectElement.value;
    disableSaveButton();
    if (ID === "") {
        nume.value = "";
        prenume.value = "";
        telefon.value = "";
        email.value = "";
        return;
    }
    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                let response = JSON.parse(request.responseText);
                nume.value = response.name;
                prenume.value = response.surname;
                telefon.value = response.telephone;
                email.value = response.email;
            }
        }
    }
    request.open('GET', 'getPerson.php?ID=' + ID);
    request.send();
}

function getPersonJq() {
    if (saveButton.disabled === false) {
        let confirmed = confirm("You have unsaved changes. Do you want to save them?");
        if (confirmed) {
            savePersonJq();
        }
    }

    ID = selectElement.value;
    disableSaveButton();

    if (ID === "") {
        nume.value = "";
        prenume.value = "";
        telefon.value = "";
        email.value = "";
        return;
    }

    $.ajax({
        url: 'getPerson.php?ID=' + ID,
        dataType: 'json',
        success: function(response) {
            nume.value = response.name;
            prenume.value = response.surname;
            telefon.value = response.telephone;
            email.value = response.email;
        },
        error: function(xhr, status, error) {
            console.error('Error fetching person:', error);
        }
    });
}

function savePerson() {
    let request = new XMLHttpRequest();
    disableSaveButton();
    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {

            }
        }
    }
    let requestData = {
        id: ID,
        name: nume.value,
        surname: prenume.value,
        telephone: telefon.value,
        email: email.value
    }
    request.open('PUT', 'savePerson.php');
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify(requestData));
}

function savePersonJq() {
    disableSaveButton();
    let requestData = {
        id: ID,
        name: nume.value,
        surname: prenume.value,
        telephone: telefon.value,
        email: email.value
    };
    $.ajax({
        type: "PUT",
        url: "savePerson.php",
        contentType: "application/json",
        data: JSON.stringify(requestData),
        error: function (request, status, error) {
            console.log(error);
        }
    });
}


window.onload = getIdsJq();