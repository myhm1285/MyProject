package com.cosquare.tld.functions;

import com.itw.util.PropertyUtil;

/**
 * 커스텀 태그 라이브러리 함수용 클래스
 *
 * @author 컨버전스스퀘어
 * @since 2015. 10. 4.
 * @version 1.0
 */
public class Functions {

	/**
	 * 줄바꿈(\n)을 &lt;br/&gt; 태그로 치환한다.
	 * 
	 * @param content
	 *            컨텐츠
	 * @return 변경된 문자열
	 */
	public static String replaceLineToBr(String content) {
		return content.replace("\n", "<br/>"); // 개행문자(\n)
	}

	/**
	 * 줄바꿈(\n)을 제거한다.
	 * 
	 * @param content
	 *            컨텐츠
	 * @return 변경된 문자열
	 */
	public static String removeLine(String content) {
		return content.replace("\n", ""); // 개행문자(\n)
	}

	/**
	 * 특수문자로 변환된 문자열을 제거한다. (개행문자 포함)<br/>
	 * 제거되는 문자열 : \n &amp; &apos; &quot; &lt; &gt; &lsquo; &rsquo; &middot;
	 * 
	 * @param content
	 *            컨텐츠
	 * @return 변경된 문자열
	 */
	public static String removeAllEscString(String content) {
		return removeAllEscString(content, true);
	}

	/**
	 * 특수문자로 변환된 문자열을 제거한다. (개행문자 제외)<br/>
	 * 제거되는 문자열 : &amp; &apos; &quot; &lt; &gt; &lsquo; &rsquo; &middot;<br/>
	 * 
	 * @param content
	 *            컨텐츠
	 * @param hasLine
	 *            개행문자(\n)를 제거하면 true, 아니면 false
	 * @return 변경된 문자열
	 */
	public static String removeAllEscStringExceptLine(String content) {
		return removeAllEscString(content, false);
	}

	/**
	 * 특수문자로 변환된 문자열을 제거한다. (개행문자 제외)<br/>
	 * 제거되는 문자열 : &amp; &apos; &quot; &lt; &gt; &lsquo; &rsquo; &middot;<br/>
	 * 제거되는 옵션 문자열 : 개행문자(\n)
	 * 
	 * @param result
	 *            컨텐츠
	 * @param hasLine
	 *            개행문자(\n)를 제거하면 true, 아니면 false
	 * @return 변경된 문자열
	 */
	private static String removeAllEscString(String content, boolean hasLine) {
		String result = content;
		if (hasLine) {
			result = result.replace("\n", ""); // 개행문자(\n)
		}
		result = result.replace("&amp;", ""); // &
		result = result.replace("&#39;", ""); // '
		result = result.replace("&apos;", ""); // '
		result = result.replace("&quot;", ""); // "
		result = result.replace("&lt;", ""); // <
		result = result.replace("&gt;", ""); // >
		result = result.replace("&lsquo;", ""); // ‘
		result = result.replace("&rsquo;", ""); // ’
		result = result.replace("&middot;", ""); // ·
		return result;
	}
	
	/**
	 * 키에 해당하는 값을 반환
	 * 
	 * @param propName
	 *            프라퍼티 파일 이름
	 * @param keyName
	 *            프라퍼티 키 이름
	 * @return 값
	 */
	public static String getProperty(String propName, String keyName) {
		return PropertyUtil.getProperty(propName, keyName);
	}
	
	/**
	 * 키에 해당하는 값을 반환 (에러 발생시 기본값을 반환)
	 * 
	 * @param propName
	 *            프라퍼티 파일 이름
	 * @param keyName
	 *            프라퍼티 키 이름
	 * @param defaultVal
	 *            기본값
	 * @return 값
	 */
	public static int getPropertyInt(String propName, String keyName, int defaultVal) {
		return PropertyUtil.getPropertyInt(propName, keyName, defaultVal);
	}
	
	/**
	 * 키에 해당하는 값을 반환 (에러 발생시 기본값을 반환)
	 * 
	 * @param propName
	 *            프라퍼티 파일 이름
	 * @param keyName
	 *            프라퍼티 키 이름
	 * @param defaultVal
	 *            기본값
	 * @return 값
	 */
	public static boolean getPropertyBoolean(String propName, String keyName, boolean defaultVal) {
		return PropertyUtil.getPropertyBoolean(propName, keyName, defaultVal);
	}
}
