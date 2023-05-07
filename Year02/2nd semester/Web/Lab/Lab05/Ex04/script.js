function sort(index) {
    let elements, x, y, shouldSwitch, switchCount = 0;
    let table = $("#table");
    let switching = true;
    let sortDirection = "ASC";

    while (switching) {
        let i;
        switching = false;
        elements = table.find("tr");
        for (i = 1; i < elements.length - 1; i++) {
            shouldSwitch = false;
            x = $(elements[i]).find("td").eq(index);
            y = $(elements[i + 1]).find("td").eq(index);
            if (sortDirection === "ASC") {
                if (x.text().toLowerCase() > y.text().toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            } else if (sortDirection === "DESC") {
                if (x.text().toLowerCase() < y.text().toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            $(elements[i]).insertAfter($(elements[i + 1]));
            switching = true;
            switchCount++;
        } else {
            if (switchCount === 0 && sortDirection === "ASC") {
                sortDirection = "DESC";
                switching = true;
            }
        }
    }
}
