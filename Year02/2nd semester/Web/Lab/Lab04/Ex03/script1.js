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
        output += '<div id = "tile_' + i + '" onclick = "flip(this,\'' + valueArray[i] + '\')"></div>';
    }
    document.getElementById('board').innerHTML = output;
}

function flip(tile, val)
{
    if (tile.innerHTML === "" && clickedValues.length < 2)
    {
        tile.style.background = "#FFF";
        tile.style.fontcolor = "black";
        tile.innerHTML = val;
        if (clickedValues.length === 0)
        {
            clickedValues.push(val);
            tileIDs.push(tile.id);
        }
        else if(clickedValues.length === 1)
        {
            clickedValues.push(val);
            tileIDs.push(tile.id);
            if (clickedValues[0] === clickedValues[1])
            {
                noTilesFlipped += 2;
                document.getElementById(tileIDs[0]).style.backgroundColor = "green";
                document.getElementById(tileIDs[1]).style.backgroundColor = "green";
                clickedValues = [];
                tileIDs = [];
                if (noTilesFlipped === valueArray.length)
                {
                    alert("Congrats!");
                    document.getElementById('board').innerHTML = "";
                    startNewGame();
                }
            }
            else
            {
                function flipBack() {
                    document.getElementById(tileIDs[0]).innerHTML = "";
                    document.getElementById(tileIDs[1]).innerHTML = "";
                    clickedValues = [];
                    tileIDs = [];
                }
                setTimeout(flipBack, 700);
            }
        }
    }
}