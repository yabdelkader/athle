package fr.yann.parser.interclub;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import fr.yann.model.enums.EpreuveEnum;
import fr.yann.model.enums.SexeEnum;
import fr.yann.model.json.interclub.ICJson;
import fr.yann.model.json.interclub.PerfJson;
import fr.yann.service.CotationServiceCourse;
import fr.yann.service.CotationServiceSautLancer;

public class InterclubJsonToSql {

	// private static final String	pathFolder	= "D:\\workspace_athle\\parser\\src\\main\\java\\fr\\yann\\parser\\interclub\\json\\";
	private static final String	pathFolder	= "C:\\Non_Sauvegardé\\Projets\\workspace_athle\\athle\\src\\main\\java\\fr\\yann\\parser\\interclub\\json\\";

	private static List<ICJson> json;
	
	
	public static void main(String[] args) throws NumberFormatException, Exception {

		// 1. JSON to Java object, read it from a file.
		// Cross cross = gson.fromJson(new FileReader("cross.json"), Cross.class);
		Type listType = new TypeToken<List<ICJson>>() {}.getType();
		json = new Gson().fromJson(new FileReader(pathFolder + "acpj.json"), listType);

		// corrigePointsAll(json.get(0));
		// displayJsonForAll(json.get(0));
		// displayJsonForFile(json.get(0));
		// displayJsonForGraph(json.get(0));
		displayJsonForSql(json.get(0));

	}

	private static void corrigePointsAll(ICJson json) throws NumberFormatException, Exception {

		corrigePoints(json, EpreuveEnum.COURSE_400_HAIES, SexeEnum.MASCULIN);
		corrigePoints(json, EpreuveEnum.COURSE_400_HAIES, SexeEnum.FEMININ);
		/*
		corrigePoints(json, EpreuveEnum.COURSE_3000_STEEPLE, SexeEnum.MASCULIN);
		
		corrigePoints(json, EpreuveEnum.MARCHE_3000, SexeEnum.FEMININ);
		corrigePoints(json, EpreuveEnum.MARCHE_5000, SexeEnum.MASCULIN);
		
		corrigePoints(json, EpreuveEnum.LANCER_DISQUE, SexeEnum.FEMININ);
		corrigePoints(json, EpreuveEnum.LANCER_JAVELOT, SexeEnum.FEMININ);
		corrigePoints(json, EpreuveEnum.LANCER_MARTEAU, SexeEnum.FEMININ);
		corrigePoints(json, EpreuveEnum.LANCER_POIDS, SexeEnum.FEMININ);
		
		corrigePoints(json, EpreuveEnum.SAUT_HAUTEUR, SexeEnum.FEMININ);
		corrigePoints(json, EpreuveEnum.SAUT_LONGUEUR, SexeEnum.FEMININ);
		corrigePoints(json, EpreuveEnum.SAUT_TRIPLE, SexeEnum.FEMININ);
		
		corrigePoints(json, EpreuveEnum.LANCER_DISQUE, SexeEnum.MASCULIN);
		corrigePoints(json, EpreuveEnum.LANCER_JAVELOT, SexeEnum.MASCULIN);
		corrigePoints(json, EpreuveEnum.LANCER_MARTEAU, SexeEnum.MASCULIN);
		corrigePoints(json, EpreuveEnum.LANCER_POIDS, SexeEnum.MASCULIN);
		
		corrigePoints(json, EpreuveEnum.SAUT_HAUTEUR, SexeEnum.MASCULIN);
		corrigePoints(json, EpreuveEnum.SAUT_LONGUEUR, SexeEnum.MASCULIN);
		corrigePoints(json, EpreuveEnum.SAUT_TRIPLE, SexeEnum.MASCULIN);
		corrigePoints(json, EpreuveEnum.SAUT_PERCHE, SexeEnum.MASCULIN);
		corrigePoints(json, EpreuveEnum.SAUT_PERCHE, SexeEnum.FEMININ);
		*/
	}
	
	
	private static void displayJsonForFile(ICJson json) {
		for (PerfJson perf : json.getPerfs()) {
			System.out.println(perf);
		}
	}
	
	// {n:'COLLIARD Julie',c:'SE',s:'F',e:'800',p:'2.28.50',pt:703,niv:'R1',nai:'87',a:2011,d:'08 mai'},
	private static void displayJsonForAll(ICJson json) {
		for (PerfJson perf : json.getPerfs()) {
			System.out.println(perf.ligneSansQuotes());
		}
	}
	
	private static void displayJsonForSql(ICJson json) {
		for (PerfJson perf : json.getPerfs()) {
			System.out.println(perf.ligneSqlInsert());
		}
	}
	
	private static void displayJsonForGraph(ICJson json) {
		for (PerfJson perf : json.getPerfs()) {
			System.out.println("+ '" + perf + "'");
		}
	}
	
	private static void corrigePoints(ICJson json, EpreuveEnum epreuve, SexeEnum sexe) throws NumberFormatException, Exception {

		if (epreuve.name().startsWith("COURSE")){
			CotationServiceCourse.init(epreuve, sexe);
		}
		else {
			CotationServiceSautLancer.init(epreuve, sexe);
		}
		
		for (PerfJson perf : json.getPerfs()) {
			if (perf.getPt().equals("0")){
				setCotation(perf, epreuve, sexe);
			}
		}
	}

	private static void setCotation(PerfJson perf, EpreuveEnum epreuve, SexeEnum sexe) throws NumberFormatException, Exception {
		if (perf.getE().equals(epreuve.getCode()) && perf.getS().equals(sexe.getCodeStr())){

			if (epreuve.name().startsWith("COURSE")){
				perf.setPt(CotationServiceCourse.getPoints(perf.getP()));
			}
			else {
				perf.setPt(CotationServiceSautLancer.getPoints(perf.getP()));
			}

			System.out.println("CORRECTION " + perf);
		}
	}

	
}
