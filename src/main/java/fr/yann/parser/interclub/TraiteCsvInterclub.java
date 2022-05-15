package fr.yann.parser.interclub;

import java.util.StringTokenizer;

import fr.yann.model.enums.CategorieEnum;
import fr.yann.model.enums.EpreuveEnum;
import fr.yann.model.enums.NiveauEnum;
import fr.yann.model.enums.SexeEnum;
import fr.yann.parser.interclub.csv.IC;

public class TraiteCsvInterclub {

	/*
		;;;4 X 400m / SEF;;Finale 2;-;DQ;0;F/;43588;;;
		CRANE Sabrina;;;1 500m / SEF;;Finale 1;8;5'33''86;571;CAF/92;43588;;;
	 */
	public static IC getIC(String line, int annee) {

		// relais
		if (line.contains(" X")) {
			return traiteRelais(clean(line), annee);
		}

		// ligne bidon
		if (!line.contains(";")) {
			return null;
		}

		return traiteCourseConcours(clean(line), annee);

	}

	private static String clean(String line) {
		line.replace(";;;", ";");
		line.replace(";;", ";");
		line.replace(";;", ";");
		return line;
	}

	/*
	 * 1                2             3    4   5        6   7      8
	 * CRANE Sabrina;1 500m / SEF;Finale 1;8;5'33''86;571;CAF/92;43588;
	 */
	private static IC traiteCourseConcours(String str, int annee) {

		IC perf = new IC();

		perf.setAnnee(annee);

		StringTokenizer st = new StringTokenizer(str, ";");

		while (st.hasMoreElements()) {
			String val = (String) st.nextElement();

			if (val.contains("Finale")) {
				continue;
			}
			if (val.length() < 2) {
				continue;
			}

			EpreuveEnum epreuve = EpreuveEnum.getEnumFromCode(EpreuveUtil.cleanCodeEpreuve(val));
			if (epreuve != null) {
				perf.setEpreuve(epreuve);
				continue;
			}

			if (isNom(val)) {
				perf.setNom(val);
				continue;
			}
			if (perf.perf == null && isPerf(val)) {
				perf.setPerf(val.replace("''", ".").replace("'", "."));
				continue;
			}
			if (perf.points == 0 && isPoints(val)) {
				perf.setPoints(Integer.parseInt(val));
				continue;
			}

			if (perf.niveau == null && isNiveau(val)) {
				perf.setNiveau(val);
				continue;
			}

			if (perf.categorie == null && isCategorieNaissance(val, perf)) {
				continue; // c'est bon cat et naissance sont valués
			}			

			if (isDate(val)) {
				perf.setDate(val);
				continue;
			}

		}

		return perf;
	}

	private static boolean isPoints(String val) {
		if (val.length() != 3 && val.length() != 4) {
			return false;
		}
		if (val.matches("[0-9]{3}")) {
			return true;
		}
		if (val.matches("[0-9]{4}")) {
			return true;
		}

		return false;
	}

	/* IR1 */
	private static boolean isNiveau(String val) {
//		if (val.length() > 4){
//			return false;
//		}
		try {
			NiveauEnum.valueOf(val);
		} catch (@SuppressWarnings("unused") Exception e) {
			// System.err.println(val + " NOT NIVEAU");
			return false;
		}
		return true;
	}


	/* ESF/88 */
	private static boolean isCategorieNaissance(String val, IC perf) {
		if (val.length() != 6) {
			return false;
		}
		CategorieEnum cat = CategorieEnum.getFromCode(val.substring(0, 3));
		if (cat == null) {
			return false;
		}
		perf.setSexe(SexeEnum.getEnumFromCode(val.substring(2, 3)));
		perf.setCategorie(cat);
		perf.setNaissance(val.substring(4, 6));
		return true;
	}

	/*
	 * 1                 2       3  
	 * 4 X 400m / SEF;Finale 2;DQ;0;F/;43588;
	 */
	private static IC traiteRelais(String line, int annee) {
		IC relais = new IC();

		relais.setAnnee(annee);
		relais.setNom("ACPJ");

		StringTokenizer st = new StringTokenizer(line, ";");

		while (st.hasMoreElements()) {
			String val = (String) st.nextElement();

			if (val.contains("100")) {
				relais.setEpreuve(EpreuveEnum.RELAIS_4x100);
				if (val.contains("F")) {
					relais.sexe = SexeEnum.FEMININ;
				} else if (val.contains("M")) {
					relais.sexe = SexeEnum.MASCULIN;
				}
				continue;
			}
			if (val.contains("400")) {
				relais.setEpreuve(EpreuveEnum.RELAIS_4x400);
				if (val.contains("F")) {
					relais.sexe = SexeEnum.FEMININ;
				} else if (val.contains("M")) {
					relais.sexe = SexeEnum.MASCULIN;
				}
			}
			if (val.contains("Finale")) {
				continue;
			}

			if (isPerf(val)) {
				relais.setPerf(val.replace("''", ".").replace("'", "."));
			}
		}


		return relais;
	}

	private static boolean isNaissance(String str) {
		if (str.length() < 2 && str.length() > 3) {
			return false;
		}
		boolean bContient2Caracteres = str.matches(".*[0-9]{2}.*");
		return bContient2Caracteres;
	}

	private static boolean isDate(String str) {
		boolean bContient4Chiffres = str.matches(".*[0-9]{4}.*");
		if (bContient4Chiffres) {
			return true;
		}

		boolean bContientMois = str.contains("mai");

		if (bContientMois) {
			return true;
		}

		boolean bFormatJJ_MM = str.matches("[0-9]{2}/[0-9]{2}");

		return bFormatJJ_MM;

	}

	private static boolean isPerf(String str) {

		if (str.length() < 3) {
			return false;
		}

		str = str.replace("''", ".").replace("'", ".");

		if (str.matches(".*[a-zA-Z]{4}.*")) {
			return false;
		}

		boolean bContient2Chiffres = str.matches(".*[0-9]{2}.*");
		return bContient2Chiffres;
	}

	private static boolean isNom(String str) {
		boolean bContient3Caracteres = str.matches(".*[a-zA-Z]{4}.*");
		
		if (!bContient3Caracteres) {
			return false;
		}
		if (str.contains("Finale")) {
			return false;
		}
		return true;
	}
}
