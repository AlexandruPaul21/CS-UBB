<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

session_start();

$target_dir = $_SERVER['DOCUMENT_ROOT'] . "/Lab07/5/photos/" . $_SESSION['id'] . "/";
if (is_dir($target_dir) && is_writable($target_dir)) {
    $_SESSION['message'] = 'The directory is not valid';
    header("Location: userView.php");
    $uploadOk = 0;
    return;
}

$target_file = $target_dir . basename($_FILES['image']['name']);
$uploadOk = 1;
$imageFileType = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));

if(isset($_POST['submit'])) {
    $check = getimagesize($_FILES['image']['tmp_name']);
    if($check !== false) {
        $uploadOk = 1;
    } else {
        $_SESSION['message'] = 'Incorrect type';
        header("Location: userView.php");
        header("Status: 404 Not Found");
        $uploadOk = 0;
        return;
    }
}

if (file_exists($target_file)) {
    $_SESSION['message'] = 'Image already existing';
    header("Location: userView.php");
    $uploadOk = 0;
    return;
}

if($_FILES['image']['size'] > 300000) {
    $_SESSION['message'] = 'Image size too big';
    header("Location: userView.php");
    $uploadOk = 0;
    return;
}

if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg") {
    $_SESSION['message'] = "Error! Image must be in one of these formats: JPG, JPEG si PNG.";
    header("Location: userView.php");
    $uploadOk = 0;
    return;
}

if(move_uploaded_file($_FILES['image']['tmp_name'], $target_dir . $_FILES['image']['tmp_name'])) {
    $_SESSION['message'] = "Image was successfully uploaded";
} else {
    $_SESSION['message'] = "Image cannot be saved in " . $target_file;
}

header("Location: userView.php");
?>
