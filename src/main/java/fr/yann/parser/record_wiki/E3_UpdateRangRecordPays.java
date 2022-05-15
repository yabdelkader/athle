package fr.yann.parser.record_wiki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.yann.model.enums.EpreuveEnum;
import fr.yann.model.enums.SexeEnum;
import fr.yann.parser.record_wiki.service.GetNomPaysWikiEn;

public class E3_UpdateRangRecordPays {

	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/athle";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	public static void main(String[] args) throws SQLException {

		Connection con = null;

		try {
			Class.forName(DRIVER_NAME).newInstance();
			Class.forName(DRIVER_NAME);
			con = DriverManager.getConnection(URL, USER, PASSWORD);

			concatZero(con);
			
			EpreuveEnum e = EpreuveEnum.COURSE_MARATHON;
			for (Integer idPays:GetNomPaysWikiEn.getMap().keySet()) {
					updateRangPaysEpreuveSexe(idPays.intValue(), e, SexeEnum.MASCULIN, con);
					// updateRangPaysEpreuveSexe(idPays.intValue(), e, SexeEnum.FEMININ, con);
			}

			retireZero(con);
			
			con.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			if (con != null) {
				con.close();
			}
		}
	}

	private static void concatZero(Connection con) throws SQLException {
		
		/* ==================
		Statement stmt = con.createStatement();
		String sql;
		int i;
		
		//-- 100
		sql = "UPDATE record r SET r.perf = CONCAT('0', r.perf) WHERE r.epreuve = '100' AND SUBSTRING(r.perf, 1, 1) = '9'";
		i = stmt.executeUpdate(sql);

		System.out.println("100 : " + i + "UPDATE");
		
		
		
		//-- 400 femmes (pour 047.60 < 1.00.00)
		sql = "UPDATE record r SET r.perf = CONCAT('0', r.perf) WHERE r.epreuve = '400' AND SUBSTRING(r.perf, 1, 1) IN ('4', '5')";

		i = stmt.executeUpdate(sql);

		System.out.println("400 : " + i + "UPDATE");
		

		//-- 3000 femmes (pour 047.60 < 1.00.00)
		sql = "UPDATE record r SET r.perf = CONCAT('0', r.perf) WHERE r.epreuve LIKE '3000%' AND SUBSTRING(r.perf, 1, 1) IN ('8', '9')";

		i = stmt.executeUpdate(sql);

		System.out.println("300% : " + i + "UPDATE");
		
		//-- Triple femmes 
		sql = "UPDATE record r SET r.perf = CONCAT('0', r.perf) WHERE r.epreuve LIKE 'Triple%' AND SUBSTRING(r.perf, 1, 1) IN ('8', '9')";

		i = stmt.executeUpdate(sql);

		System.out.println("Triple : " + i + "UPDATE");
		
		//-- Poids
		sql = "UPDATE record r SET r.perf = CONCAT('0', r.perf) WHERE r.epreuve = 'Poids' AND SUBSTRING(r.perf, 1, 1) IN ('8', '9')";
		i = stmt.executeUpdate(sql);

		System.out.println("100 : " + i + "UPDATE");

		
		
		stmt.close();
		===================== */
	}

	//-- puis on retire les '0' disgracieux ajoutés précédement
	private static void retireZero(Connection con) throws SQLException {
		try (Statement stmt = con.createStatement()) {
			int i = stmt.executeUpdate("UPDATE record r SET r.perf = SUBSTRING(r.perf, 2, LENGTH(r.perf)) WHERE SUBSTRING(r.perf, 1, 1) = '0'");

			System.out.println("update substring 0 : " + i);
			
			stmt.close();
		}
		
	}
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	/*
	UPDATE record r SET r.perf = CONCAT('0', r.perf) WHERE r.epreuve = '100' AND SUBSTRING(r.perf, 1, 1) = '9'
	
	SELECT count(*) AS nb FROM record r  WHERE r.epreuve = '100' AND r.sexe = 0 AND r.perf < 
	(
		SELECT recordSelectedPays.perf FROM record recordSelectedPays
		WHERE recordSelectedPays.epreuve = '100' AND recordSelectedPays.idPays = 69 AND recordSelectedPays.sexe = 0
	)
	 */
	private static void updateRangPaysEpreuveSexe(int idPays, EpreuveEnum epreuve, SexeEnum sexe, Connection con) throws Exception {
		
		String clauseEpreuvePaysSexe = getClauseEpreuvePaysSexe("r", epreuve, sexe, idPays);
		
		// le nombre de lignes affectées dans le cas d’un insert, d’un update
		int i;
		try (Statement stmt = con.createStatement()) {
			int nombrePaysMeilleurs = 0;
			try {
				nombrePaysMeilleurs = getNombrePaysMeilleurs(epreuve, sexe, idPays, stmt);
			} catch (Exception e) {
				System.err.println(idPays + " " + epreuve.getCode() + " " + sexe + " " + e.getMessage());
			}
			
			i = stmt.executeUpdate("UPDATE record r SET r.rang = " + nombrePaysMeilleurs + clauseEpreuvePaysSexe);

			System.out.println("UPDATE record r SET r.rang = " + nombrePaysMeilleurs + clauseEpreuvePaysSexe);
			
			stmt.close();

			if (i != 1) {
				// throw new Exception("upddate " + i + " pour " + clauseEpreuvePaysSexe);
				System.err.println("upddate " + i + " pour " + clauseEpreuvePaysSexe);
				// Thread.sleep(2000);
			}
		}
	}

	private static int getNombrePaysMeilleurs(EpreuveEnum epreuve, SexeEnum sexe, int idPays, Statement stmt) throws SQLException {
		
		String sql = "SELECT count(*) AS nb FROM record r " + getClauseEpreuveSexe("r", epreuve, sexe)
		+ getClausePerf(epreuve, epreuve, sexe, idPays);

		System.out.println(sql);

		ResultSet resultSet = stmt.executeQuery(sql);

		while (resultSet.next()) {
			return resultSet.getInt("nb") + 1;

		}
		return -1;
	}

	private static String getClauseEpreuveSexe(String var, EpreuveEnum e, SexeEnum s) {
		return " WHERE " + var + ".epreuve = '" + e.getCode() + "' AND " + var + ".sexe = " + s.getCodeInt();
	}

	private static String getClauseEpreuvePaysSexe(String var, EpreuveEnum e, SexeEnum s, int idPays) {
		return getClauseEpreuveSexe(var, e, s) + " AND " + var + ".idPays = " + idPays;
	}

	private static String getClausePerf(EpreuveEnum epreuve, EpreuveEnum e, SexeEnum s, int idPays) {
		String perf = "(SELECT recordSelectedPays.perf FROM record recordSelectedPays "
					+ getClauseEpreuvePaysSexe("recordSelectedPays", e, s, idPays) + ")";
		if (epreuve.name().startsWith("COURSE")){
			return " AND r.perf < " + perf ;
		}
		return " AND r.perf > " + perf;
	}

	// clean SQL
	/*
	static void clean(){
		String sql;
		sql = "delete  FROM record WHERE epreuve like 'dec%' and sexe = 1";
		sql = "delete  FROM record WHERE annee = 0";
	}
	*/
}
