package com.shineoxygen.work.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author 王辉阳
 * 
 * @date 2015年9月29日 下午4:50:16
 * 
 * @Description 正在工具类
 */
public class RegexUtil {
	/**
	 * 
	 * @author 王辉阳
	 * @date 2015年9月15日 下午1:45:05
	 * @param destStr
	 *            需要匹配的目标字符串
	 * @param regexStr
	 *            正则表达式字符串
	 * @return true表示 目标字符串被匹配， false 表示 目标字符串匹配失败
	 */
	public static boolean isMatch(String destStr, String regexStr) {
		if (destStr == null || regexStr == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(destStr);
		return matcher.find();
	}

	/**
	 * 
	 * @author 王辉阳
	 * @date 2015年9月15日 下午2:06:44
	 * @param destStr
	 *            目标字符串
	 * @param matchStr
	 *            需要匹配的字符串，模糊匹配
	 * @return true表示destStr中存在matchStr即匹配成功，false表示匹配失败
	 */
	public static boolean isLeftRightFuzzyMatch(String destStr, String matchStr) {
		if (destStr == null || matchStr == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(".*" + matchStr + ".*");
		Matcher matcher = pattern.matcher(destStr);
		return matcher.find();
	}

	/**
	 * 做模糊匹配
	 * 
	 * @author 王辉阳
	 * @date 2015年9月15日 下午2:06:44
	 * @param destStr
	 *            目标字符串
	 * @param matchStr
	 *            需要匹配的字符串，模糊匹配
	 * @return true表示destStr中存在matchStr即匹配成功，false表示匹配失败
	 */
	public static boolean isLeftFuzzyMatch(String destStr, String matchStr) {
		if (destStr == null || matchStr == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(".*" + matchStr);
		Matcher matcher = pattern.matcher(destStr);
		return matcher.find();
	}

	/**
	 * 右模糊
	 * 
	 * @author 王辉阳
	 * @date 2015年9月15日 下午2:08:58
	 * @param destStr
	 * @param matchStr
	 * @return
	 */
	public static boolean isRightFuzzyMatch(String destStr, String matchStr) {
		if (destStr == null || matchStr == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(matchStr + ".*");
		Matcher matcher = pattern.matcher(destStr);
		return matcher.find();
	}

	/**
	 * 校验字符个数
	 * 
	 * @author 王辉阳
	 * @date 2015年12月8日 下午7:30:49
	 * @param max
	 *            最大的字符限制，一个汉字两个字符，一个英语或数字算作一个字符
	 * @return true表示字符没超过最大的字符限制
	 */
	public static boolean charNumLimit(String destStr, int max) {
		if (max < 0) {
			return false;
		}
		if (destStr == null) {
			return true;
		}
		int num = 0;
		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5\uf900-\ufa2d]*");
		Matcher matcher = pattern.matcher(destStr);
		while (matcher.find()) {
			num += matcher.group().length() * 2;
		}

		Pattern pattern2 = Pattern.compile("[a-zA-Z\\d]*");
		Matcher matcher2 = pattern2.matcher(destStr);
		while (matcher2.find()) {
			num += matcher2.group().length();
		}

		return num <= max ? true : false;
	}
	
    public  static List<String> getStrings(String str,String regexStr) {
        Pattern p = Pattern.compile(regexStr);
        Matcher m = p.matcher(str);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group(1));            
        } 
        return strs;
    }
	public static void main(String[] args) {
//		String str = "汉字a12Bx";
//		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5\uf900-\ufa2d]*");
//		Matcher matcher = pattern.matcher(str);
//
//		System.out.println(charNumLimit(str, 9));
		String str = "columns[0][searchable]";
		getStrings(str,"\\[(.*?)\\]");
		
		String str2 = "draw=1";
		getStrings(str2,".*=");
		
	}
}
