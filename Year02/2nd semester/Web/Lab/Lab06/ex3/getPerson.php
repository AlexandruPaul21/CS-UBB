<?php
require_once 'config.php';

if (isset($_GET['ID'])) {
    $ID = $_GET['ID'];

    $sql = "SELECT * FROM persons WHERE id = '$ID'";
    $result = mysqli_query($conn, $sql);

    if ($result) {
        if (mysqli_num_rows($result) > 0) {
            $record = mysqli_fetch_assoc($result);

            echo json_encode($record);
        } else {
            echo "No record found with the specified ID.";
        }
    } else {
        echo "Query error: " . mysqli_error($conn);
    }
} else {
    echo "ID parameter is missing in the request.";
}

$conn->close();
?>