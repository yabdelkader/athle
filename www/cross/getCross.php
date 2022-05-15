<?php


$cat			= $_GET['categorie'];
$sexe			= $_GET['seye'];
$championnat	= $_GET['championnat'];
$annee			= $_GET['annee'];


if ($cat === "M" and $annee < 2020){
    $cat = "V";
}

$hint = "";

include '../constantes.php';

// --------------
// OLD PHP FOR FREE
// Create connection http://php.net/manual/fr/function.mysql-connect.php
$link  =  mysql_connect($servername, $username, $password) or die( "Impossible de se connecter : "  .  mysql_error ());
mysql_select_db($dbname);

$sql = "SELECT rang, chrono, nom, club, sexe, dep, cat, naissance, championnat, annee FROM cros WHERE annee = " . $annee . " AND cat LIKE '" . $cat . "%' AND championnat = '" . $championnat . "' AND sexe = '" . $sexe . "'";


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
            $hint = '{ "cross": [{"r":"' . $row["rang"] . '","n":"' . $row["nom"] . '","c":"' . $row["chrono"] . '"}';
        } else {
            $hint .= ',{"r":"' . $row["rang"] . '","n":"' . $row["nom"] . '","c":"' . $row["chrono"] . '"}';
        }
    }
    $hint .= ']}';
}

// echo $hint === "" ? "aucun nom trouvé" : $hint;
echo $hint === "" ? '{"cross" : [ {"r" : "0", "n" : "pas de résultats","c" : "0"}]}' : $hint;

?>