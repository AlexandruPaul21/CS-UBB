<?php
    $servername = "localhost";
    $username = "root";
    $database = "Web";

    $conn = mysqli_connect($servername, $username, '', $database);

    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
    }
?>