<?php
require_once 'config.php';

$sourceCity = $_GET['source'];

$query = "SELECT DISTINCT to_station FROM train_schedule WHERE from_station = ?";

$stmt = mysqli_prepare($conn, $query);
mysqli_stmt_bind_param($stmt, "s", $sourceCity);

if (!$stmt) {
    die(mysqli_error($conn));
}

$result = mysqli_stmt_execute($stmt);

if (!$result) {
    die(mysqli_error($conn));
}

mysqli_stmt_bind_result($stmt, $destination);

$destinations = array();
while (mysqli_stmt_fetch($stmt)) {
    $destinations[] = $destination;
}

header('Content-Type: application/json');
echo json_encode($destinations);

mysqli_stmt_close($stmt);
mysqli_close($conn);
?>