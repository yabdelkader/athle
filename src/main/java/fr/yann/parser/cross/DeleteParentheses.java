package fr.yann.parser.cross;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Parsing du texte simple issu du copier coller de PDF
 * <tr>
 * <td class="datas0">
 * <tr>
 * <td class="datas1">
 */
public class DeleteParentheses {

	public static void main(String[] args) {
		System.out.println(clean("aa(__)bb"));
	}
	
	public static String clean(String str) {
		Pattern p = Pattern.compile("\s(\\()(.*)(\\))");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return(m.replaceAll(""));
		}
		return str;
	}
}

