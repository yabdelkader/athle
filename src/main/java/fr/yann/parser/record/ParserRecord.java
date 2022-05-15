package fr.yann.parser.record;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.yann.model.enums.SexeEnum;
import fr.yann.model.record.LigneRecord;

public class ParserRecord {

	private static final String CHAR_JOKER = "_";

	public static void main(String[] args) {

		String line = "<tr><td class=\"datas0\">50m - Salle</td><td class=\"separator3\"></td><td class=\"datas0\">5''96</td><td class=\"separator3\"></td><td class=\"datas0\">&nbsp;</td><td class=\"separator3\"></td><td class=\"datas0\">PRIVALOVA Irina (RUS)</td><td class=\"separator3\"></td><td class=\"datas0\">68</td><td class=\"separator3\"></td><td class=\"datas0\">&nbsp;</td><td class=\"separator3\"></td><td class=\"datas0\">09/02/95</td><td class=\"separator3\"></td><td class=\"datas0\">Madrid (esp)</td></tr>";

		System.out.println(traiteLine(line).toString());

	}

	private static String replace(String line, String oldStr, String newStr) {
		return line.replace(oldStr, newStr);
	}

	private static String replaceHtmlTagByJoker(final String str) {

		// supression des balises html
		String patternCleanHtml = "<[^>]*>";
		Pattern p1 = Pattern.compile(patternCleanHtml);
		Matcher m1 = p1.matcher(str);
		String strPlainText = m1.replaceAll(CHAR_JOKER);

		// supression des balises html
		String patternCleanRP = "[\\s]*\\(RP\\)[\\s]*";
		Pattern p2 = Pattern.compile(patternCleanRP);
		Matcher m2 = p2.matcher(strPlainText);
		String strPlainTextWithoutRP = m2.replaceAll(CHAR_JOKER);

		strPlainTextWithoutRP = strPlainTextWithoutRP.replaceAll("&nbsp;", "");

		return strPlainTextWithoutRP;

	}

	// 1_2_3_4__5_6_7_8_9
	private static LigneRecord parseStringWithJoker(String line) {

		final String PATTERN = "[_]*([^_]+)";
		final int NB_COL_HTML = 11;

		StringBuffer sbPattern = new StringBuffer();

		for (int i = 0; i < NB_COL_HTML; i++) {
			sbPattern.append(PATTERN);
		}

		// Create a Pattern object
		Pattern r = Pattern.compile(sbPattern.toString());

		// Now create matcher object.
		Matcher m = r.matcher(line);

		LigneRecord lb = null;

		if (m.find()) {

			lb = new LigneRecord(
					"categorie",
					m.group(1), // epreuve
					m.group(2), // perf
					m.group(3), // nom
					Integer.parseInt(m.group(4)), // age
					getAnnee(m.group(5)), // date
					SexeEnum.FEMININ);
		} else {
			System.err.println(line);
		}
		return lb;
	}

	private static int getAnnee(String date) {

		int annee2;

		try {
			annee2 = Integer.parseInt(date.substring(6, 8));
		} catch (Exception e) {
			return 9999;
		}
		if (annee2 > 50) {
			return annee2 + 1900;
		}
		return annee2 + 2000;
	}

	public static LigneRecord traiteLine(String line) {

		if (line.contains("NL la veille de")) {
			return null;
		}

		String plainText = replaceHtmlTagByJoker(line);

		// String lineWithoutNL = replace(plainText, "NL la veille de la compétition", "null");
		String lineWithoutCF = replace(plainText, " (F)", "");

		return parseStringWithJoker(lineWithoutCF);
	}

}
