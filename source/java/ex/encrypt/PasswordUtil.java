package com.seoulmetro.safetykeeper.util.crypt;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

/**
 * 비밀번호 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class PasswordUtil {

	/**
	 * 비밀번호를 암호화 한다<br/>
	 * (복호화가 되면 안되므로 SHA-256 인코딩 방식 적용)
	 * 
	 * @param clearPwd
	 *            암호화할 비밀번호
	 * @return 암호화된 비밀번호
	 * @throws Exception
	 */
	public static String encrypt(String clearPwd) throws Exception {

		if (clearPwd == null || clearPwd.equals("")) {
			return "";
		}

		byte[] plainText = null; // 평문
		byte[] hashValue = null; // 해쉬값
		plainText = clearPwd.getBytes();

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		hashValue = md.digest(plainText);

		return new String(Base64.encodeBase64(hashValue));
	}

	/**
	 * 비밀번호가 일치하는지 체크한다.
	 * 
	 * @param clearPwd
	 *            평문 비밀번호
	 * @param encryptedPwd
	 *            암호화된 비밀번호
	 * @return 일치하면 true, 일치하지 않으면 false
	 * @throws Exception
	 */
	public static boolean checkPassword(String clearPwd, String encryptedPwd) throws Exception {
		// 기본 정보가 없으면
		if (clearPwd == null || clearPwd.equals("") || encryptedPwd == null || encryptedPwd.equals("")) {
			return false;
		}

		if (encrypt(clearPwd).equals(encryptedPwd)) {
			return true;
		} else {
			return false;
		}

	}
}
