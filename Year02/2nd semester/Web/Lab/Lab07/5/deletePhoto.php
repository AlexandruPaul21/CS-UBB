<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

session_start();

if(isset($_GET['filename'])) {
    unlink($_GET['filename']);
    $_SESSION['message'] = 'The image was deleted';
} else {
    $_SESSION['message'] = 'The image cannot be deleted';
}

// header("Location: userView.php");

?>