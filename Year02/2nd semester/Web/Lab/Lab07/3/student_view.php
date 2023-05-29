<?php
session_start();

$mysqli = new mysqli("localhost", "root", "", "Web");
if($mysqli->connect_error) {
  exit('Could not connect');
}

echo "<table>";
echo "<tr><th>Subject</th><th>Nota</th></tr>";

$sql = "SELECT name, grade FROM grades JOIN subjects ON subject_id = subjects.id WHERE student_id = ?";

$stmt = $mysqli->prepare($sql);
$stmt -> bind_param('i', $_SESSION['id']);
$stmt -> execute();
$stmt -> bind_result($name, $grade);
$stmt -> store_result();

while($stmt -> fetch()) {
    echo '<tr><td>'. $name .'</td><td>'. $grade .'</td></tr>';
}

echo "</table>";
?>