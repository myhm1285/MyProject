package com.itw.util;

/**
 * 문자열 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class StringUtil {

	/** Html 특수문자들 */
	private final static String[][] HTML_CHAR = { { "&quot;", "\"" }, { "&apos;", "'" }, { "&#39;", "'" }, { "&lt;", "<" }, { "&gt;", ">" },
			{ "&lsquo;", "‘" }, { "&rsquo;", "’" }, { "&middot;", "·" }, { "&amp;", "&" } };

	/**
	 * Html을 특수문자로 변경한다
	 * 
	 * @param content
	 *            변환할 내용
	 * @return 변환된 내용
	 */
	public static String encodeHtml(String content) {

		for (int i = HTML_CHAR.length - 1; i >= 0; i--) {
			content = content.replace(HTML_CHAR[i][1], HTML_CHAR[i][0]);
		}
		return content;
	}

	/**
	 * 특수문자로 변환된 Html을 원래문자로 변경한다
	 * 
	 * @param content
	 *            변환할 내용
	 * @return 변환된 내용
	 */
	public static String decodeHtml(String content) {

		for (String[] str : HTML_CHAR) {
			content = content.replace(str[0], str[1]);
		}
		return content;
	}
}
