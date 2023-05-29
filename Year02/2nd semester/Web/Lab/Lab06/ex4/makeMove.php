<?php
$inputData = file_get_contents('php://input');
$data = json_decode($inputData, true);

$occupiedPositions = $data['occupiedPositions'];
$symbol = $data['symbol'];

function checkWinner($occupiedPositions)
{
    $winningCombinations = [
        [0, 1, 2], [3, 4, 5], [6, 7, 8],
        [0, 3, 6], [1, 4, 7], [2, 5, 8],
        [0, 4, 8], [2, 4, 6]
    ];

    foreach ($winningCombinations as $combination) {
        $cell1 = $occupiedPositions[$combination[0]];
        $cell2 = $occupiedPositions[$combination[1]];
        $cell3 = $occupiedPositions[$combination[2]];

        if ($cell1 !== '-' && $cell1 === $cell2 && $cell2 === $cell3) {
            return $cell1;
        }
    }

    return false;
}

$winner = checkWinner($occupiedPositions);
if ($winner) {
    echo json_encode(['gameResult' => $winner]);
} else {
    $unoccupiedPositions = [];

    for ($i = 0; $i < count($occupiedPositions); $i++) {
        if ($occupiedPositions[$i] === '-') {
            $unoccupiedPositions[] = $i;
        }
    }

    if (count($unoccupiedPositions) === 0) {
        echo json_encode(['gameResult' => 'Nobody']);
    } else {
        $randomIndex = array_rand($unoccupiedPositions);
        $chosenPosition = $unoccupiedPositions[$randomIndex];

        $occupiedPositions[$chosenPosition] = $symbol;

        $winner = checkWinner($occupiedPositions);
        if ($winner) {
            echo json_encode([
                'chosenPosition' => $chosenPosition,
                'gameResult' => $winner
            ]);
        } else {
            echo json_encode([
                'chosenPosition' => $chosenPosition,
                'gameResult' => null
            ]);
        }
    }
}
?>