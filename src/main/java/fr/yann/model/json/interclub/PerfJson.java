package fr.yann.model.json.interclub;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PerfJson {

	@SerializedName("n")
	@Expose
	private String	n;

	@SerializedName("c")
	@Expose
	private String	c;

	
	@SerializedName("s")
	@Expose
	private String	s;
	
	
	@SerializedName("e")
	@Expose
	private String e;
	
	@SerializedName("p")
	@Expose
	private String p;
	
	@SerializedName("pt")
	@Expose
	private String pt;

	@SerializedName("niv")
	@Expose
	private String niv;

	@SerializedName("nai")
	@Expose
	private String nai;
	
	@SerializedName("a")
	@Expose
	private int a;
	
	@SerializedName("d")
	@Expose
	private String d;
	
	
	public String getN() {
		return n;
	}

	public String getC() {
		return c;
	}

	public String getS() {
		return s;
	}

	public String getE() {
		return e;
	}

	public String getP() {
		return p;
	}

	public String getPt() {
		return pt;
	}

	public String getNiv() {
		return niv;
	}

	public String getNai() {
		return nai;
	}

	public int getA() {
		return a;
	}

	public String getD() {
		return d;
	}

	public void setPt(int i) {
		this.pt = i + "";
	}

	@Override
	public String toString() {
		return "{"
				+ "\"n\":\"" + n + "\","
				+ "\"c\":\"" + c + "\","
				+ "\"s\":\"" + s + "\","
				+ "\"e\":\"" + e + "\","
				+ "\"p\":\"" + p + "\","
				+ "\"pt\":\"" + pt + "\","
				+ "\"niv\":\"" + niv + "\","
				// + "\"nai\":\"" + nai + "\","
				+ "\"a\":\"" + a + "\","
				+ "\"d\":\"" + d + "\""
				+ "},";
	}
	
	public String display() {
		return "PerfJson [n=" + n + ", c=" + c + ", s=" + s + ", e=" + e + ", p=" + p + ", pt=" + pt + ", niv=" + niv
				+ ", nai=" + nai
				+ ", a=" + a + ", d=" + d + "]";
	}

	public String ligneSansQuotes() {
		return "{"
				+ "n:'" + n + "',"
				+ "c:'" + c + "',"
				+ "s:'" + s + "',"
				+ "e:'" + e + "',"
				+ "p:'" + p + "',"
				+ "pt:" + pt + ","
				+ "niv:'" + niv + "',"
				+ "nai:'" + nai + "',"
				+ "a:" + a + ","
				+ "d:'" + d + "'"
				+ "},";
	}
	
	public String ligneSqlInsert() {
		return "INSERT INTO ic (nom, sexe, epreuve, perf, points, niveau, annee, date)"
				+ " VALUES ('"
				+ n + "','"
				// + c + "','"
				+ s + "','" + e + "','" + p + "'," + pt + ",'" + niv + "',"
				// + nai + ", "
				+ a + ",'" + d + "');";
	}
	
}