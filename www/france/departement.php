<?php

include "../constantes.php";

$utilisateur=$_POST["utilisateur"];
$departement=$_POST["departement"];

// Create connection
// $conn = new mysqli($servername, $username, $password, $dbname);
// --------------
// OLD PHP FOR FREE
// Create connection http://php.net/manual/fr/function.mysql-connect.php
$link  =  mysql_connect($servername, $username, $password) or die( "Impossible de se connecter : "  .  mysql_error ());
mysql_select_db($dbname);



$sql = "INSERT INTO log (utilisateur, departement, temps) VALUES ('"
    . $utilisateur . "','" . $departement . "', NOW());";
    
    echo $sql;
    
    $result = mysql_query($sql);
    if (!$result) {
        die('Requête invalide : ' . mysql_error());
    }
    
    
    mysql_close($link);
?>






