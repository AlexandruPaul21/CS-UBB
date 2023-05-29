<?php
// Include the config.php file
require_once 'config.php';

$sql = "SELECT id FROM persons";
$result = mysqli_query($conn, $sql);

$ids = array();
while ($row = mysqli_fetch_assoc($result)) {
    $ids[] = $row['id'];
}

echo json_encode($ids);

$conn->close();
?>