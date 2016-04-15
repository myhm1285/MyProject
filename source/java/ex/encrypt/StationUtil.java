package com.seoulmetro.safetykeeper.util.crypt;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * 역 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class StationUtil {

	/**
	 * StandardPBEStringEncryptor
	 */
	private static StandardPBEStringEncryptor encryptor;

	/**
	 * PASSWORD<br/>
	 * 안드로이드 단말기에서 동일한 키를 사용
	 */
	private final static String PASSWORD = "ENC(s03/0d9IFJKu9Cg2LkfIUw==)";

	/**
	 * 역코드를 암호화 한다.<br/>
	 * 웹 호출용으로 URLEncoder로 추가 인코딩
	 * 
	 * @param cleartext
	 *            평문 문자열
	 * @return 암호화된 문자열
	 * @throws Exception
	 */
	public static String encrypt(String cleartext) throws Exception {
		if (encryptor == null) {
			encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword(PASSWORD);
		}
		return URLEncoder.encode(encryptor.encrypt(cleartext), "utf-8");
	}

	/**
	 * 역코드를 복호화한다.<br/>
	 * 웹 호출용으로 URLDecoder로 우선 디코딩
	 * 
	 * @param encrypted
	 *            암호화된 문자열
	 * @return 평문 문자열
	 * @throws Exception
	 */
	public static String decrypt(String encrypted) throws Exception {
		if (encryptor == null) {
			encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword(PASSWORD);
		}
		return encryptor.decrypt(URLDecoder.decode(encrypted, "utf-8").replaceAll(" ", "+"));
	}

	/**
	 * 역코드를 암호화 한다. (비밀번호 사용)<br/>
	 * 웹 호출용으로 URLEncoder로 추가 인코딩
	 * 
	 * @param cleartext
	 *            평문 문자열
	 * @param password
	 *            비밀번호
	 * @return 암호화된 문자열
	 * @throws Exception
	 */
	public static String encryptByPassword(String cleartext, String password) throws Exception {
		if (encryptor == null) {
			encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword(PASSWORD);
		}
		return URLEncoder.encode(encryptor.encrypt(cleartext), "utf-8");
	}

	/**
	 * 역코드를 복호화한다. (비밀번호 사용)<br/>
	 * 웹 호출용으로 URLDecoder로 우선 디코딩
	 * 
	 * @param encrypted
	 *            암호화된 문자열
	 * @param password
	 *            비밀번호
	 * @return 평문 문자열
	 * @throws Exception
	 */
	public static String decryptByPassword(String encrypted, String password) throws Exception {
		if (encryptor == null) {
			encryptor = new StandardPBEStringEncryptor();
			encryptor.setPassword(PASSWORD);
		}
		return encryptor.decrypt(URLDecoder.decode(encrypted, "utf-8").replaceAll(" ", "+"));
	}
}
