Array.prototype.shuffle = function() {
    for (let i = this.length - 1; i > 0; i--) {
        let j = Math.floor(Math.random() * (i + 1));
        let temp = this[j];
        this[j] = this[i];
        this[i] = temp;
    }
}

function arrange(values, n) {
    let output = '<table>';
    let i;
    let j;
    for (i = 0; i < n; ++i) {
        output += '<tr>'
        for (j = 0; j < n; ++j) {
            let show = ' ';
            if (values[i * n + j]) {
                show = values[i * n + j];
            }
            output += '<td>' + show + '</td>';
        }
        output += '</tr>'
    }
    output += '</table>'

    return output;
}

let zeroPos = 0;
let dim = 0;
let values = [];

function startGame(n) {
    dim = n;
    let i;
    values = [];
    for (i = 0; i < n * n; ++i) {
        values.push(i);
    }

    values.shuffle();

    for (i = 0; i < n * n; ++i) {
        if (values[i] === 0) {
            zeroPos = i;
        }
    }

    $('#board').html(arrange(values, n));
}

$(document).on('keydown', function(e) {
    let newZeroPos = 0;

    e = e || window.event;

    if (e.keyCode === 38) {
        console.log("Up");
        newZeroPos = zeroPos - dim;
    } else if (e.keyCode === 40) {
        console.log("Down");
        newZeroPos = zeroPos + dim;
    } else if (e.keyCode === 37) {
        console.log("Left");

        newZeroPos = zeroPos - 1;

        if ((newZeroPos + 1) % dim === 0) {
            return;
        }
    } else if (e.keyCode === 39) {
        console.log("Right");
        newZeroPos = zeroPos + 1;

        if (newZeroPos % dim === 0) {
            return;
        }
    }

    if (valid(newZeroPos)) {
        values[zeroPos] = values[newZeroPos];
        values[newZeroPos] = 0;

        zeroPos = newZeroPos;

        $('#board').html(arrange(values, dim));
    }
});

function valid(position) {
    return !(position >= dim * dim || position < 0);
}
