package com.seoulmetro.safetykeeper.util.crypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seoulmetro.safetykeeper.api.service.MobileAPIValidate;

/**
 * 전화번호 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class PhoneUtil {

	/** 로거 */
	private final static Logger LOGGER = LoggerFactory.getLogger(MobileAPIValidate.class);
	
	/**
	 * HEX
	 */
	private final static String HEX = "0123456789ABCDEF";
	/**
	 * SEED
	 */
	private final static String SEED = "ENC(4jdcMwQIbNM9j1GN7jo7fw==)";

	/**
	 * 전화번호를 암호화한다.<br/>
	 * (AES 128bit)
	 * 
	 * @param cleartext
	 *            평문 문자열
	 * @return 암호화된 문자열
	 * @throws Exception
	 */
	public static String encrypt(String cleartext) throws Exception {
		byte[] rawKey = getRawKey(SEED.getBytes());
		byte[] result = encrypt(rawKey, cleartext.getBytes());
		return toHex(result);
	}

	/**
	 * 전화번호를 복호화한다.<br/>
	 * (AES 128bit)
	 * 
	 * @param encrypted
	 *            암호화된 문자열
	 * @return 평문 문자열
	 * @throws Exception
	 */
	public static String decrypt(String encrypted) throws Exception {
		if (encrypted == null || encrypted.equals("")) {
			return "";
		}
		byte[] rawKey = getRawKey(SEED.getBytes());
		byte[] enc = toByte(encrypted);
		byte[] result = decrypt(rawKey, enc);
		return new String(result);
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		}
		return result;
	}

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	public static void main(String[] args) {
		try {
			LOGGER.debug("encrypt phone number : {}", encrypt("010-0000-0000"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
