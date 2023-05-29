<?php
require_once 'config.php';

$data = json_decode(file_get_contents('php://input'), true);

if (!empty($data)) {
    $ID = $data['id'];
    $name = $data['name'];
    $surname = $data['surname'];
    $telephone = $data['telephone'];
    $email = $data['email'];

    $sql = "UPDATE persons SET name = '$name', surname = '$surname', telephone = '$telephone', email = '$email' WHERE id = '$ID'";
    $result = mysqli_query($conn, $sql);

    if ($result) {
        echo "Record updated successfully.";
    } else {
        echo "Error updating record: " . mysqli_error($conn);
    }
} else {
    echo "Invalid data received.";
}

$conn->close();
?>