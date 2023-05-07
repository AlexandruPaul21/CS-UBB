let value_array = ['0', '0', '1', '1', '2', '2', '3', '3', '4', '4', '5', '5', '6', '6', '7', '7', '8', '8', '9', '9'];
let values = [];
let tile_ids = [];
let noTilesFlipped = 0;

Array.prototype.tile_shuffle = function()
{
    for (let i = this.length - 1; i > 0; i--)
    {
        let j = Math.floor(Math.random() * (i + 1));
        let temp = this[j];
        this[j] = this[i];
        this[i] = temp;
    }
}

$(document).ready(function() {
    startNewGame();
});

function startNewGame()
{
    noTilesFlipped = 0;
    let output = '';
    value_array.tile_shuffle();
    for (let i = 0; i < value_array.length; i++)
    {
        output += '<div id="tile_' + i + '" class="tile"></div>';
    }
    $('#board').html(output);
    $('.tile').on('click', function() {
        flip($(this), value_array[$(this).index()]);
    });
}

function flip(tile, val)
{
    if (tile.html() === "" && values.length < 2)
    {
        tile.css('background', 'url(img/' + val + '.jpg) no-repeat');
        tile.html(val);
        if (values.length === 0)
        {
            values.push(val);
            tile_ids.push(tile.attr('id'));
        }
        else if(values.length === 1)
        {
            values.push(val);
            tile_ids.push(tile.attr('id'));
            if (values[0] === values[1])
            {
                noTilesFlipped += 2;
                values = [];
                tile_ids = [];
                if (noTilesFlipped === value_array.length)
                {
                    alert("Congrats!");
                    $('#board').html("");
                    startNewGame();
                }
            }
            else
            {
                function flipBack() {
                    let tile1 = $('#' + tile_ids[0]);
                    let tile2 = $('#' + tile_ids[1]);
                    tile1.html("");
                    tile1.css('background', '#FFFFFF');
                    tile2.html("");
                    tile2.css('background', '#FFFFFF');
                    values = [];
                    tile_ids = [];
                }
                setTimeout(flipBack, 700);
            }
        }
    }
}
