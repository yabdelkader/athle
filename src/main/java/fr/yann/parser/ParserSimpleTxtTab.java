package fr.yann.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.yann.model.championnat.LigneChampionnat;

/**
 * 
 * Parsing du texte simple issu du copier coller de PDF
 * <tr><td class="datas0">
 * <tr><td class="datas1">
 */
public class ParserSimpleTxtTab {

	/**
	 * arg : C:\\Non_Sauvegardé\\Projets\\workspace_athle\\athle\\src\\main\\java\\fr\\yann\\parser\\championnat\\all.csv
	 * @param args 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	
	public static void main(String[] args) throws FileNotFoundException {

		StringBuffer sb = new StringBuffer();

		String path = "C:\\Non_Sauvegardé\\Projets\\workspace_athle\\athle\\src\\main\\java\\fr\\yann\\parser\\championnat\\2019.csv";
		
		lireFichier(path, sb);

		System.out.println(sb.toString());
		
	}

	private static String	place		= "(\\d+)_";
	private static String	chrono		= "(\\d'\\d{2}''\\d{2})_";
	private static String	nom			= "([A-Za-z-\\s]+)_";
	// private static String	club		= "([a-zA-Z\\.]+[a-zA-Z])_";
	private static String	club		= "([A-Za-z\\d\\s-/\\.']+)_";
	private static String	dep			= "(\\d+)_";
	//	private static String	region		= "([a-zA-Z_\\.]+[a-zA-Z])_";
	//	private static String	categorie	= "(M\\d{2})_";
	//	private static String	naissance	= "(\\d{2})_";
	//	private static String	points		= "(\\d{4})_";
	//	private static String	annee		= "(\\d{2})_";
	//	private static String	distance	= "(\\d{4})";

	private static String pattern = place + chrono + nom + club + dep + "([\\w-]+)/(\\d{2})_([A-Z]+\\d)_(\\d+)_(\\d{4})_(\\d+)";
	/*
// place chrono           nom          club
(\d+)_(\d'\d{2}''\d{2})_([A-Za-z\s]+)_([A-Za-z\s]+)_(\d+)_([\w-]+)/(\d{2})_([A-Z]+\d)_(\d+)_(\d{4})_(\d+)
(\d+)_(\d'\d{2}''\d{2})_([A-Za-z\s]+)_([A-Za-z\s-/\.]+)_(\d+)_([\w-]+)/(\d{2})_([A-Z]+\d)_(\d+)_(\d{4})_(\d+)
	*/
	/**
	 * Ex : <tr><td class="datas0">1</td><td class="separator3"></td><td class="datas0"><b>1'44''04</b></td><td class="separator3"></td><td class="datas0">E</td><td class="separator3"></td><td class="datas0"><a href="javascript:bddThrowAthlete('bilans',%20127580,%200)" title="cliquez pour le détail">BAALA Mehdi</a></td><td class="separator3"></td><td class="datas0">Asptt strasbourg*</td><td class="separator3"></td><td class="datas0"><a href="http://bases.athle.com/asp.net/liste.aspx?frmbase=bilans&amp;frmmode=1&amp;frmannee=2006&amp;frmepreuve=208&amp;frmsexe=M&amp;frmligue=ALS">ALS</a></td><td class="separator3"></td><td class="datas0"><a href="http://bases.athle.com/asp.net/liste.aspx?frmbase=bilans&amp;frmmode=1&amp;frmannee=2006&amp;frmepreuve=208&amp;frmsexe=M&amp;frmdepartement=067">067</a></td><td class="separator3"></td><td class="datas0">SEM</td><td class="separator3"></td><td class="datas0"><a href="http://bases.athle.com/asp.net/liste.aspx?frmbase=bilans&amp;frmmode=1&amp;frmannee=2006&amp;frmepreuve=208&amp;frmsexe=M&amp;frmamini=1978&amp;frmamaxi=1978">78</a></td><td class="separator3"></td><td class="datas0">18/08/06</td><td class="separator3"></td><td class="datas0">Zurich (SUI)</td></tr>
	 * @param line
	 * @param sb 
	 * @param p_annee 
	 * @param isLastLine 
	 */
	private static void traiteLigne(String line, StringBuffer sb) {

		if (line == null) {
			return;
		}

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		if (m.find()) {
			String[] l_c = m.group(6).split("_");
			String ligue = l_c[0];
			String cat = l_c[1];
			LigneChampionnat lb = new LigneChampionnat(
					Integer.parseInt(m.group(1)),						// 1 place
					m.group(2).replace("\"", ".").replace("''", ".").replace("'", "."),	// 2 chrono
					m.group(3).replace("'", " "),						// 3 nom
					m.group(4).replace("'", " "),						// 4 club
					m.group(5).replace("'", " "),						// 5 dep
					ligue,												// ligue 
					cat,												// cat 
					Integer.parseInt(m.group(7)),						// naissance
					Integer.parseInt(m.group(9)),						// pts
					Integer.parseInt(m.group(10)),						// annee
					Integer.parseInt(m.group(11))						// distance
			);

			sb.append(lb.toString() + "\n");
		}
		else {
			System.err.println(line);
		}

	}

	private static void lireFichier(String path, StringBuffer sb) throws FileNotFoundException {
		File f = new File(path);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);

		try {
			String line = br.readLine();
			while (line != null) {
				line = br.readLine();
				traiteLigne(line, sb);
			}

			br.close();
			fr.close();
		} catch (IOException exception) {
			System.err.println("Erreur lors de la lecture : " + exception.getMessage());
		}
	}


}
