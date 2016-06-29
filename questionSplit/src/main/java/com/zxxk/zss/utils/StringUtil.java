package com.zxxk.zss.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
static Map<String, String> charMap = new HashMap<String, String>();
	
	static {
		initCharMap();
	}

	/**
	 * 将中文字符替换为英文字符
	 * @param source
	 * @return
	 */
	public static String chineseCharacter2EnglishCharacter(String source) {
		char c;
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < source.length(); i++) {
			c = source.charAt(i);
			String destStr = charMap.get(c + "");
			if (destStr == null) {
				sb.append(c);
			} else {
				sb.append(destStr);
			}
		}
		
		return sb.toString();
	}

	private static void initCharMap() {
		// 半角
		charMap.put("　", " ");
		charMap.put("，", ",");
		charMap.put("、", ",");
		charMap.put("。", ".");
		charMap.put("‘", "'");
		charMap.put("’", "'");
		charMap.put("；", ";");
		charMap.put("“", "\"");
		charMap.put("”", "\"");
		charMap.put("？", "?");
		charMap.put("！", "!");
		charMap.put("：", ":");
		charMap.put("（", "(");
		charMap.put("）", ")");
//		charMap.put("【", "[");
//		charMap.put("】", "]");
		charMap.put("｛", "{");
		charMap.put("｝", "}");
		
		charMap.put("１", "1");
		charMap.put("２", "2");
		charMap.put("３", "3");
		charMap.put("４", "4");
		charMap.put("５", "5");
		charMap.put("６", "6");
		charMap.put("７", "7");
		charMap.put("８", "8");
		charMap.put("９", "9");
		charMap.put("０", "0");
		
		charMap.put("ａ", "a");
		charMap.put("ｂ", "b");
		charMap.put("ｃ", "c");
		charMap.put("ｄ", "d");
		charMap.put("ｅ", "e");
		charMap.put("ｆ", "f");
		charMap.put("ｇ", "g");
		charMap.put("ｈ", "h");
		charMap.put("ｉ", "i");
		charMap.put("ｊ", "j");
		charMap.put("ｋ", "k");
		charMap.put("ｌ", "l");
		charMap.put("ｍ", "m");
		charMap.put("ｎ", "n");
		charMap.put("ｏ", "o");
		charMap.put("ｐ", "p");
		charMap.put("ｑ", "q");
		charMap.put("ｒ", "r");
		charMap.put("ｓ", "s");
		charMap.put("ｔ", "t");
		charMap.put("ｕ", "u");
		charMap.put("ｖ", "v");
		charMap.put("ｗ", "w");
		charMap.put("ｘ", "x");
		charMap.put("ｙ", "y");
		charMap.put("ｚ", "z");
		
		charMap.put("Ａ", "A");
		charMap.put("Ｂ", "B");
		charMap.put("Ｃ", "C");
		charMap.put("Ｄ", "D");
		charMap.put("Ｅ", "E");
		charMap.put("Ｆ", "F");
		charMap.put("Ｇ", "G");
		charMap.put("Ｈ", "H");
		charMap.put("Ｉ", "I");
		charMap.put("Ｊ", "J");
		charMap.put("Ｋ", "K");
		charMap.put("Ｌ", "L");
		charMap.put("Ｍ", "M");
		charMap.put("Ｎ", "N");
		charMap.put("Ｏ", "O");
		charMap.put("Ｐ", "P");
		charMap.put("Ｑ", "Q");
		charMap.put("Ｒ", "R");
		charMap.put("Ｓ", "S");
		charMap.put("Ｔ", "T");
		charMap.put("Ｕ", "U");
		charMap.put("Ｖ", "V");
		charMap.put("Ｗ", "W");
		charMap.put("Ｘ", "X");
		charMap.put("Ｙ", "Y");
		charMap.put("Ｚ", "Z");
		
		
	}
	
	
	
	/**
	 * 将连续的多个空格替换为一个空格
	 * @param string
	 * @return
	 */
	public static String replaceContinuousSpaceToOne(String string){
		String regEx="[ ]+";//一个或多个空格   
        Pattern p = Pattern.compile(regEx);
        
        Matcher m = p.matcher(string);
        return m.replaceAll(" ");//替換為一個空格
	}
	

	public static String replaceHtml(String s){
		s = s.replaceAll("<font[^>]*>([\\s\\S]*?)", "");
		s = s.replaceAll("<span[^>]*>([\\s\\S]*?)", "");
		s = s.replaceAll("<p[^>]*>([\\s\\S]*?)", "");
		s = s.replaceAll("<a[^>]*>([\\s\\S]*?)", "");
		s = s.replaceAll("<u[^>]*>([\\s\\S]*?)", "");
		s = s.replaceAll("<sup[^>]*>([\\s\\S]*?)", "");
		s = s.replaceAll("<b[^>]*>([\\s\\S]*?)", "");
		s = s.replaceAll("</font>", "");
		s = s.replaceAll("</span>", "");
//		s = s.replaceAll("</p>", "");
		s = s.replaceAll("</a>", "");
		s = s.replaceAll("</u>", "");
		s = s.replaceAll("</sup>", "");
		s = s.replaceAll("</b>", "");
		s = s.replaceAll("<br/>","");
		s = s.replaceAll("</body>","");
		s = s.replaceAll("</html>","");
		return s.trim();
	}
	
	public static String replaceContent(String s){
		//除去多少分（25）分
		s=s.replaceAll("[(（][1-9][0-9]{0,1}分([^)）]*)[）)]", "");
		//出去空白行
		s=s.replaceAll("\n[\\s| ]*\r", "");
		
		return s.trim();
	}
	
	public static String replaceAnswer(String s){
		s=s.replaceAll(".*[一二三四五六七八九十][)）、.,]", "");
		s=s.replaceAll("多项选择题和非选择题答案", "");
		s=s.replaceAll("选择题", "");
		s=s.replaceAll("选择题", "");
		return s.trim();
	}
	
	//把list内容转化为String；
	public static String listToString(List<String> lstContent){
		StringBuffer sb = new StringBuffer();
		for(String s:lstContent){
			sb.append(s);
		}
		return sb.toString();
	}
}
