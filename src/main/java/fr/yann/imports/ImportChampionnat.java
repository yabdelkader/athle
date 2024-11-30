package fr.yann.imports;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ImportChampionnat {

	private static final String URL_BASES_ATHLE_FR = "https://bases.athle.fr/asp.net/liste.aspx?frmbase=resultats&frmmode=1&frmespace=0&frmcompetition=";

	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		Map<Integer, Integer> mapAnneeIdCompetition = new HashMap<>();

		/*
		// 2016 Compiègne
		mapAnneeIdCompetition.put(2016, 182722);

		// 2017 Arles
		mapAnneeIdCompetition.put(2017, 197642);

		// 2018 Angers
		mapAnneeIdCompetition.put(2018, 213163);

		// 2019 Dreux
		mapAnneeIdCompetition.put(2019, 227697);

		// 2020 Chalon-sur-Saône
		mapAnneeIdCompetition.put(2020, 238757);

		// 2021 Albi
		mapAnneeIdCompetition.put(2021, 251189);

		// 2022 Chateauroux
		mapAnneeIdCompetition.put(2022, 264984);
		*/
		
		// urls.add("264984&frmepreuve=800m+%2f+45M");
		// urls.add("264984&frmepreuve=1+500m+%2f+40M");
		
		
		// 2023 Laval
		// mapAnneeIdCompetition.put(2023, 278693);
		
		// 2024 Thonon
		mapAnneeIdCompetition.put(2024, 291388);
		
		
		// List<Integer> epreuves = new ArrayList<>(Arrays. asList(800, 1500, 5000));
		List<Integer> epreuves = new ArrayList<>(Arrays. asList(400));
		List<String> categories = new ArrayList<>(Arrays. asList("35M", "40M", "45M", "50M", "55M", "60M"));
		
		for (Integer annee : mapAnneeIdCompetition.keySet()) {
			
			for (Integer epreuve:epreuves) {
				for (String categorie:categories) {
			
					String strEpreuve = getStrEpreuve(epreuve);
		
					String url = URL_BASES_ATHLE_FR + mapAnneeIdCompetition.get(annee) + "&frmepreuve=" + strEpreuve +  "m+%2f+" + categorie;
					// System.err.println(url);
					importPage(url, annee, epreuve, categorie);
				}
			}
		}
		
		

	}

	private static String getStrEpreuve(Integer epreuve) {
		String strEpreuve = "" + epreuve;
		if (epreuve == 1500) {
			strEpreuve = "1+500";
		}
		else if (epreuve == 5000) {
			strEpreuve = "5+000";
		}
		return strEpreuve;
	}

	private static void importPage(String url, int annee, int epreuve, String categorie) throws MalformedURLException, InterruptedException {
		
		System.out.println("call " + url);
		
		Thread.sleep(1000 * getRandomNumberInRange(0, 10));
		
		Path p = FileSystems.getDefault().getPath("./championnat", annee + "_" + epreuve + "_" + categorie + ".html");

		URL website = new URL(url);
		try (InputStream in = website.openStream()) {
			Files.copy(in, p, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
