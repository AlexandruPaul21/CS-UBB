let valueArray = ['0', '0', '1', '1', '2', '2', '3', '3', '4', '4', '5', '5', '6', '6', '7', '7', '8', '8', '9', '9'];
let clickedValues = [];
let tileIDs = [];
let noTilesFlipped = 0;

Array.prototype.shuffle = function()
{
    for (let i = this.length - 1; i > 0; i--)
    {
        let j = Math.floor(Math.random() * (i + 1));
        let temp = this[j];
        this[j] = this[i];
        this[i] = temp;
    }
}

function startNewGame()
{
    noTilesFlipped = 0;
    let output = '';
    valueArray.shuffle();
    for (let i = 0; i < valueArray.length; i++)
    {
        output += '<div id="tile_' + i + '"></div>';
    }
    $('#board').html(output);

    $('[id^=tile_]').click(function() {
        flip($(this), valueArray[$(this).attr('id').split('_')[1]]);
    });
}

function flip(tile, val)
{
    if (tile.html() === "" && clickedValues.length < 2)
    {
        tile.css('background', '#FFF');
        tile.css('color', 'black');
        tile.html(val);
        if (clickedValues.length === 0)
        {
            clickedValues.push(val);
            tileIDs.push(tile.attr('id'));
        }
        else if(clickedValues.length === 1)
        {
            clickedValues.push(val);
            tileIDs.push(tile.attr('id'));
            if (clickedValues[0] === clickedValues[1])
            {
                noTilesFlipped += 2;
                $('#' + tileIDs[0]).css('background-color', 'green');
                $('#' + tileIDs[1]).css('background-color', 'green');
                clickedValues = [];
                tileIDs = [];
                if (noTilesFlipped === valueArray.length)
                {
                    alert("Congrats!");
                    $('#board').html("");
                    startNewGame();
                }
            }
            else
            {
                function flipBack() {
                    $('#' + tileIDs[0]).html("");
                    $('#' + tileIDs[1]).html("");
                    clickedValues = [];
                    tileIDs = [];
                }
                setTimeout(flipBack, 700);
            }
        }
    }
}

// Attach click event to the Start New Game button
$('#start_btn').click(function() {
    startNewGame();
});
