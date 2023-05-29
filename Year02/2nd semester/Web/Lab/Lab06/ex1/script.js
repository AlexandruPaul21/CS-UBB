function getStations() {
    let source = $('#source').get(0);
    let destination = $('#destination');
    let sourceCity = source.options[source.selectedIndex].value;

    let request = new XMLHttpRequest();
    request.onreadystatechange = function () {
        if (request.readyState === 4) {
            if (request.status === 200) {
                destination.html('');
                let array = JSON.parse(request.responseText);
                let optionElements = [];
                for (let i = 0; i < array.length; i++) {
                    let option = document.createElement('option');
                    option.text = array[i];
                    option.value = array[i];
                    optionElements.push(option);
                }
                destination.html('').append($(optionElements));
            }
        }
    }

    request.open('GET', 'getDestinations.php?source=' + sourceCity);
    request.send();
}