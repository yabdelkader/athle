<?php

$hint = "";

include '../constantes.php';

// --------------
// OLD PHP FOR FREE
// Create connection http://php.net/manual/fr/function.mysql-connect.php
$link  =  mysql_connect($servername, $username, $password) or die( "Impossible de se connecter : "  .  mysql_error ());
mysql_select_db($dbname);

$sql = "SELECT utilisateur, departement, temps FROM log";


$req = mysql_query($sql) or die("['Erreur SQL !','" .$sql. "','" . mysql_error() . "]");


$nb = mysql_num_rows($req);

if ( $nb == 0 ) {
    // rien à faire
}
else {
    // on fait une boucle qui va faire un tour pour chaque enregistrement
    while($row = mysql_fetch_assoc($req)){
        // while($row = $result->fetch_assoc()) {
        if ($hint === "") {
            $hint = '{ "logs": [{"u":"' . $row["utilisateur"] . '","d":"' . $row["departement"] . '","dt":"' . $row["temps"] . '"}';
        } else {
            $hint .= ',{"u":"' . $row["utilisateur"] . '","d":"' . $row["departement"] . '","dt":"' . $row["temps"] . '"}';
        }
    }
    $hint .= ']}';
}

// echo $hint === "" ? "aucun nom trouvé" : $hint;
echo $hint === "" ? '{"cross" : [ {"log":"_","d":"_","dt":""}' : $hint;

?>