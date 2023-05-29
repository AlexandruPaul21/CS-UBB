<?php
$mysqli = new mysqli("localhost", "root", "", "Web");
if($mysqli->connect_error) {
  exit('Could not connect');
}

function test_input($data) {
  $data = trim($data);
  $data = stripslashes($data);
  $data = htmlspecialchars($data);
  return $data;
}

$sql = "SELECT id FROM users WHERE username = ? AND password = ?";

$username = test_input($_POST['username']);
$password = test_input($_POST['password']);

$stmt = $mysqli->prepare($sql);
$stmt->bind_param('ss', $username, $password);
$stmt->execute();
$result = $stmt->get_result()->fetch_assoc();

if (!$result) {
  header("Location: index.html");
  return;
}

session_start();
$_SESSION['id'] = $result['id'];
$_SESSION['username'] = $username;

header("Location: userView.php");

?>
