package com.cosquare.smp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * 프라퍼티 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class PropertyUtil {

	/**
	 * 프라퍼티 경로 위치
	 */
	private final static String PATH_PREFIX = PropertyUtil.class.getResource("").getPath().substring(0,
			PropertyUtil.class.getResource("").getPath().lastIndexOf("com"));

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
		String value = "";
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(PATH_PREFIX + "conf" + System.getProperty("file.separator") + "property" + System.getProperty("file.separator")
					+ propName + ".properties");
			props.load(new java.io.BufferedInputStream(fis));
			value = props.getProperty(keyName).trim();
			value = getValue(props, value);
		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return value;
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
		try {
			return Integer.parseInt(getProperty(propName, keyName));
		} catch (NumberFormatException e) {
			return defaultVal;
		}
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
		try {
			return Boolean.parseBoolean(getProperty(propName, keyName));
		} catch (Exception e) {
			return defaultVal;
		}
	}

	private static String getValue(Properties props, String parentkeyName) {
		int deps = 0;
		String value = "";
		HashMap<Integer, Integer> keyIdx = new HashMap<Integer, Integer>();

		try {
			for (int i = 0; i < parentkeyName.length(); i++) {
				if (parentkeyName.charAt(i) == '$' && parentkeyName.charAt(i + 1) == '{') {
					deps++;
					i++;
					keyIdx.put(deps, i + 1);
				} else if (parentkeyName.charAt(i) == '}') {
					if (deps >= 1) {
						value = props.getProperty(parentkeyName.substring(keyIdx.get(deps), i));
						if (value == null) {
							return null;
						}
						parentkeyName = parentkeyName.replace("${" + parentkeyName.substring(keyIdx.get(deps), i) + "}", value);
						i = keyIdx.get(deps) - 3;
						deps--;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return parentkeyName;

	}
}
