package fr.yann.model.championnat;

public class LigneChampionnat {

	private int		place;
	private String	chrono;
	private String	nom;
	private String	club;
	private String	dep;
	private String	ligue;
	private String	categorie;
	private int		naissance;
	private int		points;
	private int		annee;
	private int		dist;

	public LigneChampionnat(int place, String chrono, String nom, String club, String dep, String ligue, String categorie, int naissance, int points, int annee, int dist) {
		this.place = place;
		this.chrono = chrono;
		this.nom = nom;
		this.club = club;
		this.dep = dep;
		this.ligue = ligue;
		this.categorie = categorie;
		this.naissance = naissance;
		this.points = points;
		this.annee = annee;
		this.dist = dist;
	}

	@Override
	public String toString() {
		return "{\"place\":" + place
				+ ", \"annee\":"		+ annee
				+ ", \"cat\":\""		+ categorie
				+ "\", \"chrono\":\""	+ chrono
				+ "\", \"nom\":\""		+ nom
				+ "\", \"club\":\""		+ club
				+ "\", \"dep\":\""		+ dep
				+ "\", \"ligue\":\""	+ ligue
				+ "\", \"naissance\":"	+ naissance
				+ ", \"pts\":"			+ points
				+ ", \"dist\":"			+ dist
				+ "},";
	}





	public String toStringInsert() {
		String str = "place :" + place;
		return str;
	}

}
