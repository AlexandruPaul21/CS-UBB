<?php
    $servername = "localhost";
    $username = "root";
    $database = "Web";

    // Create connection
    $conn = mysqli_connect($servername, $username, '', $database);

    // Check connection
    if (!$conn) {
        die("Connection failed: " . mysqli_connect_error());
    }
?>