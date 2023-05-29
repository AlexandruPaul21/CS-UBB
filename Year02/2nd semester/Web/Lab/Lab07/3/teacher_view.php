<?php
session_start();

if(isset($_SESSION['message'])) {
  echo $_SESSION['message'];
  unset($_SESSION['message']);
}

$mysqli = new mysqli("localhost", "root", "", "Web");
if($mysqli->connect_error) {
  exit('Could not connect');
}

echo '<form method="POST" action="add_grade.php">';

$sql = "SELECT id, name FROM subjects";

$stmt = $mysqli->prepare($sql);
$stmt -> execute();
$stmt -> bind_result($id, $name);
$stmt -> store_result();

echo "Subject: ";
echo '<select id="subject" name="subject">';
while($stmt->fetch()) {
  echo '<option value="'. $id .'">'. $name .'</option>';
}
echo '</select>';

$sql = "SELECT id, name FROM students";

$stmt = $mysqli->prepare($sql);
$stmt -> execute();
$stmt -> bind_result($id, $name);
$stmt -> store_result();

echo "Student: ";
echo '<select id="student" name="student">';
while($stmt->fetch()) {
  echo '<option value="'. $id .'">'. $name .'</option>';
}
echo '</select>';

echo '<input type="number" name="grade" id="grade" min="1" max="10"/>';
echo '<input type="submit" value="Add grade"/>';

echo "</form>";
?>