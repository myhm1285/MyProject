package com.test.board.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	 * 운영
	 */
	public final static String MODE_REAL = "real";
	/**
	 * 개발
	 */
	public final static String MODE_DEV = "dev";

	/**
	 * 모드 (운영/개발)
	 */
	private static String mode = "";

	/**
	 * 프라퍼티 경로 위치
	 */
	private final static String PATH_PREFIX = PropertyUtil.class.getResource("").getPath().substring(0,
			PropertyUtil.class.getResource("").getPath().lastIndexOf("com"));

	/**
	 * 모드 반환 (운영:real, 개발 dev)
	 * 
	 * @return
	 */
	public static String getMode() {
		// 모드 초기화
		if ("".equals(mode)) {
			initMode();
		}
		return mode;
	}

	/**
	 * 모드 초기화
	 */
	private static void initMode() {
		if ("".equals(mode)) {
			Properties props = new Properties();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(PATH_PREFIX + "conf" + System.getProperty("file.separator") + "property"
						+ System.getProperty("file.separator") + "service.properties");
				props.load(new java.io.BufferedInputStream(fis));
				mode = props.getProperty("service.mode").trim();
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
		}
	}

	/**
	 * 모드(개발/운영)에 따른 키 이름 변경
	 */
	private static String changeKeyName(String keyName) {
		// 모드 초기화
		if ("".equals(mode)) {
			initMode();
		}
		// 운영이면
		if (MODE_REAL.equals(mode)) {
			return keyName + "." + MODE_REAL;
		} else {
			return keyName + "." + MODE_DEV;
		}
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
		return getProperty(propName, keyName, false);
	}

	/**
	 * 키에 해당하는 값을 반환
	 * 
	 * @param propName
	 *            프라퍼티 파일 이름
	 * @param keyName
	 *            프라퍼티 키 이름
	 * @param isMode
	 *            모드(운영/개발) 적용 여부
	 * @return 값
	 */
	public static String getProperty(String propName, String keyName, boolean isMode) {

		// 모드 적용시에만
		if (isMode) {
			// 키값 변경
			keyName = changeKeyName(keyName);
		}

		String value = "";
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(PATH_PREFIX + "conf" + System.getProperty("file.separator") + "property" + System.getProperty("file.separator")
					+ propName + ".properties");
			props.load(new java.io.BufferedInputStream(fis));
			value = props.getProperty(keyName).trim();
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
		return getPropertyInt(propName, keyName, defaultVal, false);
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
	 * @param isMode
	 *            모드(운영/개발) 적용 여부
	 * @return 값
	 */
	public static int getPropertyInt(String propName, String keyName, int defaultVal, boolean isMode) {
		try {
			return Integer.parseInt(getProperty(propName, keyName, isMode));
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
		return getPropertyBoolean(propName, keyName, defaultVal, false);
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
	 * @param isMode
	 *            모드(운영/개발) 적용 여부
	 * @return 값
	 */
	public static boolean getPropertyBoolean(String propName, String keyName, boolean defaultVal, boolean isMode) {
		try {
			return Boolean.parseBoolean(getProperty(propName, keyName, isMode));
		} catch (Exception e) {
			return defaultVal;
		}
	}
}
