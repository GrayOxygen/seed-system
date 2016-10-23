package com.shineoxygen.work.base.utils;

import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StrUtils {
	public static final Charset GBK = Charset.forName("GBK");
	public static final Charset UTF_8 = Charset.forName("UTF-8");
	public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

	static final char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	static final char[] nums = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private static final char[] charMap = new char[64];

	static {
		for (int i = 0; i < 10; ++i) {
			charMap[i] = (char) (48 + i);
		}
		for (int i = 10; i < 36; ++i) {
			charMap[i] = (char) (97 + i - 10);
		}
		for (int i = 36; i < 62; ++i) {
			charMap[i] = (char) (65 + i - 36);
		}
		charMap[62] = '_';
		charMap[63] = '-';
	}

	public static final int getAsciiLength(String str) {
		return new String(str.getBytes(GBK), ISO_8859_1).length();
	}

	public static final String truncate(String input, int maxlength) {
		if (input == null)
			return null;
		if (input.length() <= maxlength)
			return input;
		String temp = input.substring(0, maxlength);
		byte[] bytes = temp.getBytes();
		int count = 0;
		for (int i = 0; i < bytes.length; ++i) {
			if (bytes[i] > 0) {
				++count;
			}
		}
		if (count > 0) {
			count = count / 2 + 1;
		}
		if (input.length() <= maxlength + count) {
			return input;
		}
		return input.substring(0, maxlength + count);
	}

	public static boolean isNotEmpty(CharSequence cs) {
		return (!(isEmpty(cs)));
	}

	public static boolean isEmpty(CharSequence cs) {
		return ((cs == null) || (cs.length() == 0));
	}

	public static final String format(String input, Object[] arguments) {
		if (input == null)
			return null;
		return MessageFormat.format(input, arguments);
	}

	public static final String jsStrEscape(String input) {
		if (input == null)
			return null;
		return input.replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\'").replaceAll("(\\r)?\\n", "\\\\n");
	}

	public static final String htmlEscape(String input) {
		if (input == null)
			return null;
		return input.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
	}

	public static final String sqlEscape(String input) {
		if (input == null)
			return null;
		return input.replaceAll("'", "''");
	}

	public static final String escapeCSV(String input) {
		if ((input == null) || (input.trim().length() == 0))
			return "";
		if (input.indexOf(10) > -1) {
			input = input.replaceAll("(\\r)?\\n", "<BR/>");
		}
		if ((input.indexOf(34) > -1) || (input.indexOf(",") > -1)) {
			input = input.replaceAll("\"", "\"\"");
			return '"' + input + '"';
		}
		return input;
	}

	public static final String toHTML(String input) {
		if (input == null)
			return null;
		return input.replaceAll(" ", "&nbsp;").replaceAll("(\\r)?\\n", "<BR>");
	}

	public static final String htmlTag2LowerCase(String html) {
		Matcher matcher = Pattern.compile("<[^>]+>").matcher(html);
		int lastIdx = 0;
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String str = matcher.group();
			sb.append(html.substring(lastIdx, matcher.start()));
			sb.append(str.toLowerCase());
			lastIdx = matcher.end();
		}
		return html.substring(lastIdx);
	}

	public static final String clearScriptCode(String input) {
		if (input == null)
			return null;
		Matcher matcher = Pattern.compile("<[^>]*>").matcher(input);
		int lastIdx = 0;
		StringBuilder sb = new StringBuilder();
		while (matcher.find()) {
			String str = matcher.group();
			sb.append(input.substring(lastIdx, matcher.start()));
			String tag = str.toLowerCase();
			if ((tag.startsWith("<script")) || (tag.startsWith("</script")))
				sb.append(tag);
			else {
				sb.append(str);
			}
			lastIdx = matcher.end();
		}
		sb.append(input.substring(lastIdx));
		return sb.toString().replaceAll("<script[^>]*>[^<]*</script[^>]*>", "");
	}

	public static final String html2text(String html) {
		if (html == null)
			return null;
		return htmlTag2LowerCase(html).replaceAll("<script[^>]*>[^<]*</script[^>]*>", "").replaceAll("<style[^>]*>[^<]*</style[^>]*>", "").replaceAll("&([a-zA-Z]+);", " ")
				.replaceAll("(\\r)?\\n", " ").replaceAll("<[^>]+>", "").replaceAll("(\\s)+", " ");
	}

	public static final String truncateHTML(String html, int length) {
		if (html == null)
			return null;
		return truncate(html2text(html).trim(), length).replaceAll("\"", "&quot;");
	}

	public static final String truncateHTML2(String html, int length) {
		if (html == null)
			return null;
		String lowerHtml = html.toLowerCase();
		String START_TAG = "<span class=\"s_highlight\">";
		String END_TAG = "</span>";
		int startPos = lowerHtml.indexOf("<span class=\"s_highlight\">");
		List<String> words = new ArrayList();
		while (startPos > -1) {
			int endPos = lowerHtml.indexOf("</span>", startPos + "<span class=\"s_highlight\">".length());
			if (endPos == -1)
				break;
			String word = html.substring(startPos + "<span class=\"s_highlight\">".length(), endPos);
			words.add(word);
			startPos = lowerHtml.indexOf("<span class=\"s_highlight\">", endPos + "</span>".length());
		}
		html = html.replaceAll("<[^>]*>", "").replaceAll("(\\r)?\\n", " ").replaceAll("(\\s)+", " ");
		float count;
		if (html.length() > length) {
			StringBuilder sb = new StringBuilder(length * 2);
			count = 0.0F;
			for (int i = 0; (i < html.length()) && (count < length); ++i) {
				char c = html.charAt(i);
				count += ((c < 128) ? 0.5F : 1.0F);
				sb.append(c);
			}
			html = sb.toString();
		}

		for (String word : words)
			html = html.replaceAll(word, "<span class=\"s_highlight\">" + word + "</span>");
		return html.trim().replaceAll("\\\\", " ");
	}

	public static final String arr2str(Object[] values) {
		if (values == null)
			return null;
		String str = Arrays.toString(values);
		return str.substring(1, str.length() - 1).replaceAll(", ", ",");
	}

	public static final String filterKeyWord(String keyWord) {
		String result = keyWord;
		if (isEmpty(result)) {
			return "";
		}

		String regEx = "[`~!@#$%^&*=|{}()':;',//[//].<>/?~！@#￥%……&*_|｛｝（）【】‘；：”“’。，、？\\[\\](\\\\)+\\-]+";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(result);
		result = m.replaceAll("").trim();

		regEx = "[\\s]+";
		p = Pattern.compile(regEx);
		m = p.matcher(result);
		result = m.replaceAll(" ").trim();

		return result.toLowerCase();
	}

	public static final String transformSolrMetacharactor(String input) {
		StringBuffer sb = new StringBuffer();
		String regex = "[+\\-&|!(){}\\[\\]^\"~*?:(\\)]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			matcher.appendReplacement(sb, "\\\\" + matcher.group());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static final List<String> stringToList(String s, String delimiter) {
		String[] sArr = s.split(delimiter);
		List list = new ArrayList();
		for (int i = 0; i < sArr.length; ++i) {
			list.add(sArr[i]);
		}
		return list;
	}

	public static String getShortUUID() {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}

	private static String hexTo64(String hex) {
		StringBuffer r = new StringBuffer();
		int index = 0;
		int[] buff = new int[3];
		int l = hex.length();
		for (int i = 0; i < l; ++i) {
			index = i % 3;
			buff[index] = Integer.parseInt(String.valueOf(hex.charAt(i)), 16);
			if (index == 2) {
				r.append(charMap[(buff[0] << 2 | buff[1] >>> 2)]);
				r.append(charMap[((buff[1] & 0x3) << 4 | buff[2])]);
			}
		}
		return r.toString();
	}

	public static String get64UUID() {
		String uuid = "0" + getShortUUID();
		return hexTo64(uuid);
	}

	public static String getMac(long num) {
		String s = String.format("%X", new Object[] { Long.valueOf(num) });
		s = StringUtils.leftPad(s, 12, '0');
		StringBuffer sb = new StringBuffer(17);
		for (int i = 0; i < s.length(); ++i) {
			sb.append(s.charAt(i));
			if ((i != s.length() - 1) && (i % 2 == 1)) {
				sb.append('-');
			}
		}
		return sb.toString();
	}

	public static String getMatchStr(String s) {
		if (StringUtils.isNotBlank(s)) {
			return s.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", "");
		}
		return s;
	}
}