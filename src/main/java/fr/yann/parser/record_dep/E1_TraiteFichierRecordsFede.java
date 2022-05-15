package fr.yann.parser.record_dep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import fr.yann.model.enums.CategorieEnum;
import fr.yann.model.enums.SexeEnum;
import fr.yann.model.record.LigneRecordDep;
import fr.yann.parser.record_wiki.service.SqlService;

/**
 * 
 * HTML -> SQL
 *
 */
public class E1_TraiteFichierRecordsFede {

	public static final String PATH_PAGES_DEP = "D:\\workspace_athle\\parser\\src\\main\\java\\fr\\yann\\parser\\record_dep\\pages";

	// Insert.main(path + ".sql");
	public static void main(String[] args) throws Exception {

		for (SexeEnum sexeEnum : SexeEnum.values()) {

			for (CategorieEnum catEnum : CategorieEnum.values()) {
			
				for (int num = 1; num < 96; num++) {

					StringBuffer sb = new StringBuffer();

					List<LigneRecordDep> liste = traite(num, sexeEnum, catEnum);

					// System.out.println("NOMBRE : " + liste.size() + "\n");
					for (LigneRecordDep lr : liste) {
						// System.out.println(lr.toStringSql());
						sb.append(lr.toStringSql() + "\n");
					}
					SqlService.writeFile(getPathFile(num, sexeEnum, catEnum), sb.toString());
				}
			}
		}

	}

	private static List<LigneRecordDep> traite(int dep, SexeEnum sexeEnum, CategorieEnum catEnum) throws Exception {

		File f = new File(getPathFile(dep, sexeEnum, catEnum));
		List<LigneRecordDep> listeRecords;
		try (FileReader fr = new FileReader(f);BufferedReader br = new BufferedReader(fr)) {
			LigneRecordDep lr = null;
			listeRecords = new ArrayList<>();

			String line;

			while ((line = br.readLine()) != null) {

				if (line.startsWith("<tr")) {
					lr = new LigneRecordDep(dep, sexeEnum, catEnum);
					ParserRecordDep.traiteLine(line, lr);
					listeRecords.add(lr);
				}
			}
			br.close();
			fr.close();
			return listeRecords;
		}

		// boolean bTrEncours = false;

	}

	private static String getPathFile(int dep, SexeEnum sexeEnum, CategorieEnum catEnum) {
		String str_num = dep + "";
		if (dep < 10) {
			str_num = "0" + dep;
		}
		return PATH_PAGES_DEP + "\\" + sexeEnum.getCodeStr() + "_" + catEnum.name() + "_" + str_num + ".html";
	}
}
