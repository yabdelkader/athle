<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bibliothèque</title>
	
	<link media="screen" rel="stylesheet" type="text/css" href="../css/bootstrap.min.css" />

	<link rel="stylesheet" type="text/css" href="../cross/css/jquery.dataTables.min.css">
	<link rel="stylesheet" type="text/css" href="../css/fixedHeader.dataTables.min.css">
	
	<style type="text/css">

footer {
	width: 100%;
	color: #fff;
	padding-top: 20px;
	padding-bottom: 20px;
	background-color: #239fcc;
}

.infos {
	margin: auto;
	width: 40%;
	overflow: auto;
}

.right {
	float: right;
}

.ligne-header {
	height: 1px;
	background-color: #999;
	margin: 0 250px 0 0;
}
.row-selected {
	background-color: #239fcc;
}

</style>
</head>
<body>


	<article class="content">

	<div class="row" style="margin-bottom:50px">

		<div class="well well-sm atih" align="center" style="color:white;background-color: #239FCC">
			<h2>Bibliothèque</h2>
			<p>Liste et commentaires</p>
		</div>


		<div class="col-sm-10"></div>
		
		<div class="col-sm-2"><a class="btn btn-primary" href="ajout_document.html" role="button" style="background-color: #239FCC">Ajouter document</a></div>
		
		<div class="col-sm-12" style="margin: 0 20px;width:97%">
		
			<table id="example" class="display" style="width:100%">
				<thead>
					<tr>
						<th>Id</th>
						<th>Titre</th>
						<th>Auteur</th>
	 					<th>Année</th>
						<th>Type</th>
						<th>Thème</th>
						<th>Bibliothèque</th>
						<th>Commentaire</th>
					</tr>
				</thead>		
				<tbody></tbody>
			</table>

		</div>

	</div>

	</article>

	
	<footer>

		<div class="infos">
			<div>
			</div>
			
		</div>
	</footer>
	</body>
	
	
	<script type="text/javascript" src="../cross/js/jquery-3.3.1.js"></script>
	<script type="text/javascript" src="../cross/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="../js/dataTables.fixedHeader.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/select/1.3.3/js/dataTables.select.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.7.0/js/dataTables.buttons.min.js"></script>
	<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.7.0/js/buttons.colVis.min.js"></script>

	<script type="text/javascript" class="init">

$(document).ready(function() {
	// Setup - add a text input to each footer cell
	$('#example thead tr').clone(true).appendTo( '#example thead' );
	$('#example thead tr:eq(1) th').each( function (i) {
		var title = $(this).text();
		$(this).html( '<input type="text" placeholder="Filtre '+title+'" />' );

		$( 'input', this ).on( 'keyup change', function () {
			if ( table.column(i).search() !== this.value ) {
				table
					.column(i)
					.search( this.value )
					.draw();
			}
		} );
	} );

	var table = $('#example').DataTable( {
	
	
		data :
<?php

// ===================
include '../constantes.php';

// --------------
// OLD PHP FOR FREE
// Create connection http://php.net/manual/fr/function.mysql-connect.php
$link  =  mysql_connect($servername, $username, $password) or die( "Impossible de se connecter : "  .  mysql_error ());
mysql_select_db($dbname);


$sql = "SELECT d.id AS id, d.titre AS titre, d.auteur AS auteur, d.type AS type, d.theme AS theme, d.bibli AS bibli, d.annee AS annee, d.commentaire AS commentaire FROM Document d";	
	
$req = mysql_query($sql) or die('Erreur SQL !<br>'.$sql.'<br>'.mysql_error());

$nb = mysql_num_rows($req);

$hint = "";

// on fait une boucle qui va faire un tour pour chaque enregistrement
while($row = mysql_fetch_assoc($req)){
	if ($hint === "") {
	    $hint = '[{"id":' . $row["id"] . ', "titre":"' . $row["titre"] . '","auteur":"' . $row["auteur"] . '","annee":"' . $row["annee"] . '","type":"' . $row["type"] . '","theme":"' . $row["theme"] . '","bibli":"' . $row["bibli"] . '","commentaire":"' . $row["commentaire"] . '"}';
	} else {
	    $hint .= ',{"id":' . $row["id"] . ', "titre":"' . $row["titre"] . '","auteur":"' . $row["auteur"] . '","annee":"' . $row["annee"] . '","type":"' . $row["type"] . '","theme":"' . $row["theme"] . '","bibli":"' . $row["bibli"] . '","commentaire":"' . $row["commentaire"] . '"}';
	}
}
$hint .= '],';

echo $hint;

?>


		aLengthMenu: [ [100, 500, 1000, -1], [100, 500, 1000, "Tous"] ],
		iDisplayLength: 100,
		dom: 'Bfrtip',
		select: true,
		orderCellsTop: true,
		fixedHeader: true,
			columnDefs: [
				{ width: 150, targets: 0 }
		],
		columns: [
			{ data: "id" },
			{ data: "titre" },
			{ data: "auteur" },
			{ data: "annee" },
			{ data: "type" },
			{ data: "theme" },
			{ data: "bibli" },
			{ data: "commentaire" }
		],
		columnDefs: [
			{
				"targets": [ 0 ],
				"visible": false,
				"searchable": false
			},
			{
				"targets": [ 7 ],
				"visible": false,
				"searchable": true
			}
		],
		buttons: [
			{
				extend: 'colvis',
				text: 'Visibilité des colonnes'
			}
		]
	} );
	
//	 $('#example tbody').on( 'click', 'tr', function () {
//	 } );

	table.on( 'select', function ( e, dt, type, indexes ) {
	    table[ type ]( indexes ).nodes().to$().addClass( 'row-selected' );
	} );

	table.on( 'select', function ( e, dt, type, indexes ) {
		var rowData = table.rows( indexes ).data().toArray();
			window.location.href = '/bibli/document?id=' + rowData[0].id;
		} )

	$('#example_filter').hide();
	$('#example_wrapper > div.dt-buttons > button').css('margin-bottom', '22px');
	
} );

	</script>
</html>