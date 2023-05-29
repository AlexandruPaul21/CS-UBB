<?php
require_once 'config.php';

$recordsPerPage = 3;

$page = $_GET['page'];

$offset = max(0, ($page - 1) * $recordsPerPage);

$query = "SELECT * FROM persons LIMIT $offset, $recordsPerPage";
$result = mysqli_query($conn, $query);

$records = array();
while ($row = mysqli_fetch_assoc($result)) {
    $records[] = $row;
}

$countQuery = "SELECT COUNT(*) AS totalRecords FROM persons";
$countResult = mysqli_query($conn, $countQuery);
$row = mysqli_fetch_assoc($countResult);
$totalRecords = $row['totalRecords'];

$response = array(
    'records' => $records,
    'totalRecords' => $totalRecords
);

header('Content-Type: application/json');
echo json_encode($response);
?>