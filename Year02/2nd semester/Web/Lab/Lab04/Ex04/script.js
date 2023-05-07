function sort(index)
{
    let elements, x, y, shouldSwitch, switchCount = 0;
    let table = document.getElementById("table");
    let switching = true;
    let sortDirection = "ASC";

    while (switching)
    {
        let i;
        switching = false;
        elements = table.getElementsByTagName("tr");
        for (i = 1; i < elements.length - 1; i++)
        {
            shouldSwitch = false;
            x = elements[i].getElementsByTagName("td")[index];
            y = elements[i + 1].getElementsByTagName("td")[index];
            if (sortDirection === "ASC") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase())
                {
                    shouldSwitch = true;
                    break;
                }
            }
            else if (sortDirection === "DESC")
            {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase())
                {
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch)
        {
            elements[i].parentNode.insertBefore(elements[i + 1], elements[i]);
            switching = true;
            switchCount++;
        }
        else
        {
            if (switchCount === 0 && sortDirection === "ASC")
            {
                sortDirection = "DESC";
                switching = true;
            }
        }
    }
}