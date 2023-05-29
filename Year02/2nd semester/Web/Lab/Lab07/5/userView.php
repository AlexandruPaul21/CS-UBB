<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

session_start();

function printUser($id, $username) {
    $target_dir = $_SERVER['DOCUMENT_ROOT'] . "/Lab07/5/photos/" . $id . "/";
    $images = glob($target_dir . "*.{png,jpg,jpeg}", GLOB_BRACE);

    if(count($images)) {
        echo '<div style="display: flex;flex-direction: column">';
        echo '<h4>'. $username .'</h4>';
        echo '<div style="display: flex;flex-direction: row">';
        foreach($images as $image) {
            echo '<section style="display:flex;flex-direction: column;max-width:100px;margin-left: 10px">';
            echo '<img style="height: 100px;width: 100px" src="http://localhost/Lab07/5/photos/' . $id . '/' . basename($image) . '"/><br/>';
            echo '</section>';
        }
        echo"</div></div>";
    }
}

echo '<form action="upload.php" method="POST" enctype="multipart/form-data">';
echo '<input type="file" name="image" id="image">';
echo '<input type="submit" name="submit" value="Upload photo">';
echo '</form>';

$target_dir = $_SERVER['DOCUMENT_ROOT'] . "/Lab07/5/photos/" . $_SESSION['id'] . "/";
$dir = "/Lab07/5/photos/" . $_SESSION['id'] . "/";
$images = glob($target_dir . "*.{png,jpg,jpeg}", GLOB_BRACE);

echo '<div style="display: flex;flex-direction: row">';
foreach($images as $image) {
    echo '<section style="display:flex;flex-direction: column;max-width:100px;margin-left: 10px">';
    echo '<img style="height: 100px;width: 100px" src="http://localhost/Lab07/5/photos/' . $_SESSION['id'] . '/' . basename($image) . '"/><br/>';
    echo '<button onclick="window.location.href=\'./deletePhoto.php?filename='. $image . '\'">Delete';
    echo '</button>';
    echo '</section>';
}

$mysqli = new mysqli("localhost", "root", "", "Web");
if($mysqli->connect_error) {
  exit('Could not connect');
}

$sql = 'SELECT id, username FROM users WHERE id != ?';

$stmt = $mysqli->prepare($sql);
$stmt->bind_param('i', $_SESSION['id']);
$stmt->execute();
$stmt->bind_result($id, $username);
$stmt->store_result();

while($stmt->fetch()) {
    printUser($id, $username);
}
?>
