let cont = 1;
let totalRecords;
let table = document.getElementById("myTable");
let prevBtn = document.getElementById("prevBtn");
let nextBtn = document.getElementById("nextBtn");

function getPrevious() {
    cont--;
    if (cont === 1) {
        prevBtn.disabled = true;
    }
    if (nextBtn.disabled === true) {
        nextBtn.disabled = false;
    }
    getPeople();
}

function getNext() {
    cont++;
    if (cont === 2) {
        prevBtn.disabled = false;
    }
    getPeople();
    if (cont * 3 >= totalRecords) {
        nextBtn.disabled = true;
    }
}

function getPeople() {
    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {

                let response = JSON.parse(request.responseText);
                let records = response.records;
                totalRecords = response.totalRecords;

                clearTable();
                for (let i = 0; i < records.length; i++) {
                    let record = records[i];
                    let row = document.createElement("tr");
                    let cell1 = document.createElement("td");
                    cell1.textContent = record.name;
                    row.appendChild(cell1);
                    let cell2 = document.createElement("td");
                    cell2.textContent = record.surname;
                    row.appendChild(cell2);
                    let cell3 = document.createElement("td");
                    cell3.textContent = record.telephone;
                    row.appendChild(cell3);
                    let cell4 = document.createElement("td");
                    cell4.textContent = record.email;
                    row.appendChild(cell4);
                    table.appendChild(row);
                }
            }
        }
    }

    request.open('GET', 'getPeople.php?page=' + cont);
    request.send();
}

function getPeopleAjax() {
    $.ajax({
        url: "getPeople.php?page=" + cont,
        dataType: "json",
        success: function(response) {
            let records = response.records;
            totalRecords = response.totalRecords;

            clearTable();
            for (let i = 0; i < records.length; i++) {
                let record = records[i];
                let row = $("<tr>");
                let cell1 = $("<td>").text(record.name);
                row.append(cell1);
                let cell2 = $("<td>").text(record.surname);
                row.append(cell2);
                let cell3 = $("<td>").text(record.telephone);
                row.append(cell3);
                let cell4 = $("<td>").text(record.email);
                row.append(cell4);
                table.append(row);
            }
        }
    });
}

function clearTable() {
    let rows = table.getElementsByTagName("tr");

    for (let i = rows.length - 1; i > 0; i--) {
        let row = rows[i];

        if (!row.querySelector("th")) {
            row.remove();
        }
    }
}

window.onload = getPeople();