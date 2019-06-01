package fr.yann.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.yann.model.enums.EpreuveEnum;
import fr.yann.model.enums.SexeEnum;

public class CotationService {
	
	private static final String pathFolderCsv = "C:\\workspace_athle\\Yann\\resources\\csv\\";

	private static List<PerfPoints> listePerfPoints = null;
	
	// EpreuveEnum epreuve, SexeEnum sexe en amont dans init()
	private static int getPoints(double chrono) throws Exception{
		
		for (int tentative = 0; tentative < 4; tentative++) {
			double add = Double.parseDouble("0.0" + tentative);
			int points = getPoints(chrono + add, tentative);
			if (points > 0) {
				return points;
			}
		}
		throw new Exception(chrono + " non trouvé");
	}

	private static int getPoints(double chrono, int tentative) throws Exception{
		for (PerfPoints pp:listePerfPoints){
			if(pp == null){
				continue;
			}
			double current = Double.parseDouble(pp.perf);
			// System.out.println("test " + current + " " + chrono);
			if (current == chrono){
				return pp.points;
			}
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {

		init(EpreuveEnum.COURSE_800, SexeEnum.MASCULIN);
		
		double chronoSecondes = Utilities.getChronoSecondesFromPerf("1.59.99");
		
		//		double chrono =Double.parseDouble("100.00");
		//		double tenth  = Double.parseDouble("0.04");
		//		System.out.println(tenth);
		System.out.println(">>>>>> " + getPoints(chronoSecondes));
		
	}
	

	// perf, points
	public static void init(EpreuveEnum e, SexeEnum s) {
		
		if (listePerfPoints != null){
			return;
		}

		String fileName = "Table Outdoor 2017 - MALE - 800m";
		
		try (BufferedReader br = new BufferedReader(new FileReader(pathFolderCsv + fileName + ".csv"))) {

			List<PerfPoints> perfPoints = br.lines().map(mapToPerfPoints)
					// .limit(100)
					.collect(Collectors.toList());
			listePerfPoints = perfPoints;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// fonction lambada pour créer une personne
	public static Function<String, PerfPoints> mapToPerfPoints = (line) -> {
		if (line.startsWith("p")){
			return null;
		}
		String[] p = line.split(",");
		return new PerfPoints(p[0], Integer.parseInt(p[1]));
	};

	public static int getPoints(String perf) throws NumberFormatException, Exception {
		return getPoints(Utilities.parseTimeDot(perf));
	}
}
