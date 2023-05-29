<?php

session_start();

$mysqli = new mysqli("localhost", "root", "", "Web");
if($mysqli->connect_error) {
  exit('Could not connect');
}

if(!is_int($_POST['grade']) || $_POST['grade'] < 1 || $_POST['grade'] > 10) {
    $_SESSION['message'] = "Grade could not be added";
    header("Location: teacher_view.php");
}

$sql = "INSERT INTO grades(subject_id, student_id, grade) VALUES(?,?, ?)";

$stmt = $mysqli->prepare($sql);
$stmt -> bind_param('iii', $_POST['subject'], $_POST['student'], $_POST['grade']);

if($stmt->execute()) {
    $_SESSION['message'] = "Grade was added successfully";
} else {
    $_SESSION['message'] = "Grade could not be added";
}
header("Location: teacher_view.php");
?>