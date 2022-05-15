package fr.yann.parser.cross;

public class LigneCross {

	// private static final String SEPARATOR = ";";
	private int rang;
	private String chrono;
	private String nom;
	private String club;
	private String dep;
	private String cat;
	private int naissance;
	private int sexe;
	private String championnat;
	private int annee;

	public LigneCross(int rang, String chrono, String nom, String club, String dep, String cat, int naissance, int sexe,
			String championnat, int annee) {
		super();
		this.rang = rang;
		this.chrono = chrono;
		this.nom = nom;
		this.club = club;
		this.dep = dep;
		this.cat = cat;
		this.naissance = naissance;
		this.sexe = sexe;
		this.championnat = championnat;
		this.annee = annee;
	}

	@Override
	public String toString() {

		String str = "(" + rang + ",'" + chrono + "','" + nom + "','" + club + "'," + sexe + ",'" + dep + "','"
				+ naissance + ",'" + championnat + "," + annee;

		if ((rang % 250) == 0) {
			return str + ");";
		}
		return str + "),";

	}

	public String toJson() {
		
		// { "name": "DOUKKANA Rabii (Mar)", "info": "29'22'' qi", "color":"#00BCF2"},
		String str = "{ n: \"" + nom + "\", \"i\": \"" + chrono + "\", \"color\":\"#00BCF2\"";
		
		if ((rang % 250) == 0) {
			return str + "};";
		}
		return str + "},";
		
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}

	/*
	public String toStringInsert() {
		String str = "INSERT INTO `ligne` (`rang`, `perf`, `nom`, `prenom`, `sexe`, `idPays`, `club`, `ligue`, `datePerf`, `naissance`, `ville`, `idEpreuve`) VALUES("
				+ rang + ",'" + perf + "','" + nom + "','" + prenom + "'," + sexe + ",'" + pays + "','" + club + "','"
				+ ligue + "','" + datePerf + "'," + anneeNaissance + ",'" + ville + "'," + idEpreuve + ");";

		return str;
	}

	public String toStringHtmlTrOfTable() {
		return "<tr><td>" + rang + SEPARATOR_TD + perf + SEPARATOR_TD + nom + SEPARATOR_TD + prenom + SEPARATOR_TD
				+ pays + SEPARATOR_TD + club + SEPARATOR_TD + anneeNaissance + SEPARATOR_TD + datePerf + SEPARATOR_TD
				+ ville + "</td></tr>";
	}
	 */
}
