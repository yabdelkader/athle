package fr.yann.parser.championnat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.yann.model.championnat.LigneChampionnat;
import fr.yann.parser.ParserSimpleTxtTab;
import fr.yann.parser.record.ParserRecord;

/**
 * 
 * HTML -> JSon
 *
 */
public class E1_TraiteFichierChampionnat {

	public  static final String PATH_PAGES_CHAMPIONNAT = "C:\\Non_Sauvegardé\\Projets\\workspace_athle\\athle\\championnat";

	public static void main(String[] args) throws Exception {

		// List<Integer>	annees		= new ArrayList<Integer>(Arrays.asList(2016, 2017, 2018, 2019, 2020, 2021, 2022));
		List<Integer>	annees		= new ArrayList<Integer>(Arrays.asList(2023));
		Integer			epreuve		= 800;
		String			categorie	= "35M";

		for (Integer annee : annees) {
			
			System.out.println();
			StringBuffer sb = new StringBuffer();

			String path = PATH_PAGES_CHAMPIONNAT + "\\" + annee + "\\" + annee + "_" + epreuve + "_" + categorie + ".html";

			List<LigneChampionnat> liste = traite(path, annee, epreuve, categorie);

			// System.out.println("NOMBRE : " + liste.size() + "\n");
			for (LigneChampionnat ligne : liste) {

				System.out.println(ligne.toString());
				sb.append(ligne.toString() + "\n");
			}
			// SqlService.writeFile(path, sb.toString());
		}


	}

	private static List<LigneChampionnat> traite(final String pathFile, int annee, int epreuve, String categorie) throws Exception {

		
		List<LigneChampionnat>	listeLigneChampionnat	= new ArrayList<>();
		
		File f = new File(pathFile);

		try (FileReader fr = new FileReader(f);BufferedReader br = new BufferedReader(fr);){

	
			String ligneHtml;
	
			while ((ligneHtml = br.readLine()) != null) {
	
				if (!ligneHtml.startsWith("<tr")) {
					continue;
				}
				if (ligneHtml.contains("Records principaux")) {
					continue;
				}
				if (ligneHtml.contains("Championnat")) {
					continue;
				}
	
				// on traite les td : epreuve, perf, nom, date 
				// System.out.println(line);
	
				String ligneUnderscore = ParserRecord.replaceHtmlTagByJoker(ligneHtml)
						.replace("____", "_").replace("___", "_").replace("__", "_")
						.replace("*", "")
						.replace("&", "")
						.replace("(", "").replace(")", "");
				LigneChampionnat		ligneChampionnat		= ParserSimpleTxtTab.traiteLigneFromHtml(ligneUnderscore, annee, epreuve, categorie);
	
				if (ligneChampionnat == null) {
					System.err.println(" null " + ligneHtml);
				} else {
					listeLigneChampionnat.add(ligneChampionnat);
				}
				
			}
		}

		return listeLigneChampionnat;
	}
}
