<!doctype html>
<html lang="fr">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet" href="https://getbootstrap.com/docs/4.0/dist/css/bootstrap.min.css" />

<title>Bibliothèque - Document</title>

</head>

<body>

	<form id="formModificationDocument" class="container" method="post" action="modification_document.php" style="margin-top: 30px">

		<div class="card">
			<div class="card-header">Document</div>
			<div class="card-body">
			
				<div class="row">
					<div class="col-md-8" style="margin-bottom: 20px">
<?php

include "../constantes.php";

$id=$_GET["id"];

// Create connection
// $conn = new mysqli($servername, $username, $password, $dbname);
// --------------
// OLD PHP FOR FREE
// Create connection http://php.net/manual/fr/function.mysql-connect.php
$link  =  mysql_connect($servername, $username, $password) or die( "Impossible de se connecter : "  .  mysql_error ());
mysql_select_db($dbname);



$sql = "SELECT d.id AS id, d.titre AS titre, d.auteur AS auteur, d.type AS type, d.theme AS theme, d.bibli AS bibli, d.annee AS annee, d.commentaire AS commentaire FROM Document d WHERE id = " . $id;

$req = mysql_query($sql) or die('Erreur SQL !<br>'.$sql.'<br>'.mysql_error());

$nb = mysql_num_rows($req);

if ( $nb == 0 ) {
	// rien Ã  faire
	echo "pas de ligne id : " . $id;
}
else {
	// on fait une boucle qui va faire un tour pour chaque enregistrement
	while($row = mysql_fetch_assoc($req)){
?>
				<div class="row">
					<div class="col-md-8" style="margin-bottom: 20px">
						<label for="titre" class="col-form-label">Titre</label>
						<input type="hidden" name="id" value="<?php echo $id ?>"/>
						<input type="text" class="form-control" name="titre" id="titre" required="required" value="<?php echo $row["titre"] ?>"/>
					</div>
					<div class="col-md-4"></div>
				</div>

			</div>
			<div class="col-md-4"></div>
		</div>
		<div class="row">
			<div class="col-md-8" style="margin-bottom: 20px">
				<label for="auteur" class="col-form-label">Auteur</label>
				<input class="form-control" type="text" value="<?php echo $row["auteur"];?>" name="auteur" id="auteur" required="required"/>
					</div>
					<div class="col-md-2">
						<label for="annee" class="col-form-label">Année</label>
						<input type="number" class="form-control" name="annee" id="annee" min="1500" max="2030" required="required" value="<?php echo $row["annee"] ?>"/>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4" style="margin-bottom: 20px">

						<div class="form-group">
							<label for="type" class="col-form-label">Type</label>
							<select class="form-control" id="type" name="type">
								<option value="roman"			<?php if ($row['type'] == 'roman')			echo 'selected' ?> >roman</option>
								<option value="article"			<?php if ($row['type'] == 'article')		echo 'selected' ?> >article</option>
								<option value="essai"			<?php if ($row['type'] == 'essai')			echo 'selected' ?> >essai</option>
								<option value="correspondance"	<?php if ($row['type'] == 'correspondance') echo 'selected' ?> >correspondance</option>
								<option value="bd"				<?php if ($row['type'] == 'bd')				echo 'selected' ?> >bande dessinée</option>
							</select>
						</div>

					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="theme" class="col-form-label">Thème</label>
							<select class="form-control" id="theme" name="theme">
								<option value="art"				<?php if ($row['theme'] == 'art')			echo 'selected' ?>>art</option>
								<option value="écologie"		<?php if ($row['theme'] == 'écologie')		echo 'selected' ?>>écologie</option>
								<option value="économie"		<?php if ($row['theme'] == 'économie')		echo 'selected' ?>>économie</option>
								<option value="histoire"		<?php if ($row['theme'] == 'histoire')		echo 'selected' ?>>histoire</option>
								<option value="littérature"		<?php if ($row['theme'] == 'littérature')	echo 'selected' ?>>littérature</option>
								<option value="philosophie"		<?php if ($row['theme'] == 'philosophie')	echo 'selected' ?>>philosophie</option>
								<option value="politique"		<?php if ($row['theme'] == 'politique')		echo 'selected' ?>>politique</option>
								<option value="religion"		<?php if ($row['theme'] == 'religion')		echo 'selected' ?>>religion</option>
								<option value="science"			<?php if ($row['theme'] == 'science')		echo 'selected' ?>>science</option>
								<option value="sport"			<?php if ($row['theme'] == 'sport')			echo 'selected' ?>>sport</option>
							</select>
						</div>
					</div>
					<div class="col-md-2">
						<div class="form-group">
							<label for="bibli" class="col-form-label">Bibliothèque</label>
							<select class="form-control" id="bibli" name="bibli">
								<option value="Merwane"			<?php if ($row['bibli'] == 'Merwane')	echo 'selected' ?>>Merwane</option>
								<option value="Danielle"		<?php if ($row['bibli'] == 'Danielle')	echo 'selected' ?>>Danielle</option>
								<option value="Yann"			<?php if ($row['bibli'] == 'Yann')		echo 'selected' ?>>Yann</option>
								<option value="Juliette"		<?php if ($row['bibli'] == 'Juliette')	echo 'selected' ?>>Juliette</option>
								<option value="Kléber"			<?php if ($row['bibli'] == 'Kléber')	echo 'selected' ?>>Kléber</option>
								<option value="autre"			<?php if ($row['bibli'] == 'autre')	echo 'selected' ?>>Autre</option>
							</select>							
							
						</div>
					</div>
				</div>
				<div class="row">

					<div class="col-md-10">
						<label for="commentaire" class="col-form-label">Commentaire</label>
						<textarea class="form-control" name="commentaire" id="commentaire" rows="6"><?php echo $row["commentaire"] ?></textarea>
					</div>
				</div>

				

			</div>
			<div class="row">
				<div class="col-md-12" style="margin-bottom: 20px; text-align: center;">
					<button type="submit" class="btn btn-info" style="margin-right: 20px">Modifier</button>
					<a class="btn btn-secondary" href="/bibli">Retour</a>
				</div>
			</div>
		</div> 
<?php 
	} // end while
} // end else

echo $hint;

mysql_close($link);
?>
	</form>

	<script src="../js/jquery-1.12.3.js" type="text/javascript"></script>
	<script src="../js/bootstrap.min.js" type="text/javascript"></script>


</body>
</html>