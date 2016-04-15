package com.seoulmetro.safetykeeper.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 가상전화번호 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class VirtualCallUtil {

	/** 로거 */
	private final static Logger LOGGER = LoggerFactory.getLogger(VirtualCallUtil.class);

	/**
	 * 사업자 구분코드
	 */
	private final static String COMPANY_ID = PropertyUtil.getProperty("service", "service.virtualcall.cid");
	/**
	 * 사업자 자체 시스템 구분 번호
	 */
	private final static String SYSTEM_ID = PropertyUtil.getProperty("service", "service.virtualcall.sid");
	/**
	 * 가상번호 서버 IP
	 */
	private final static String IP = PropertyUtil.getProperty("service", "service.virtualcall.ip");
	/**
	 * 가상번호 서버 PORT
	 */
	private final static int PORT = PropertyUtil.getPropertyInt("service", "service.virtualcall.port", 61311);

	private final static String START_CHAR = "#";
	private final static String END_CHAR = "$";

	/**
	 * @Class Name : PacketId.java
	 * @Description : 패킷 타입 정의 클래스
	 * 
	 * @author 컨버전스스퀘어
	 * @since 2014.08.01
	 * @version 1.0
	 */
	private class PacketId {
		/**
		 * 번호 사용 등록요구
		 */
		public final static String REQ_2501 = "2501";

		/**
		 * 번호 사용 해지요구
		 */
		public final static String REQ_2502 = "2502";

		/**
		 * 번호 사용 일시정지 요구
		 */
		@SuppressWarnings("unused")
		public final static String REQ_2503 = "2503";

		/**
		 * 번호 사용 일시정지해지 요구
		 */
		@SuppressWarnings("unused")
		public final static String REQ_2504 = "2504";

		/**
		 * 상태 체크 요구
		 */
		public final static String REQ_2600 = "2600";

		/**
		 * 번호 사용 등록요구 응답패킷
		 */
		@SuppressWarnings("unused")
		public final static String RES_3501 = "3501";

		/**
		 * 번호 사용 해지요구 응답패킷
		 */
		@SuppressWarnings("unused")
		public final static String RES_3502 = "3502";

		/**
		 * 번호 사용 일시정지 요구 응답패킷
		 */
		@SuppressWarnings("unused")
		public final static String RES_3503 = "3503";

		/**
		 * 번호 사용 일시정지해지 요구 응답패킷
		 */
		@SuppressWarnings("unused")
		public final static String RES_3504 = "3504";

		/**
		 * 상태 체크 요구 응답패킷
		 */
		@SuppressWarnings("unused")
		public final static String RES_3600 = "3600";
	}

	/**
	 * 가상전화번호를 등록한다.
	 * 
	 * @param cpNo
	 *            민원번호
	 * @param userPhone
	 *            사용자 전화번호
	 * @param virtualPhone
	 *            가상 전화번호
	 * @return 성공/실패
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static boolean register(String cpNo, String userPhone, String virtualPhone) {
		return process(PacketId.REQ_2501, cpNo, userPhone, virtualPhone);
	}

	/**
	 * 가상전화번호를 해지한다.
	 * 
	 * @param cpNo
	 *            민원번호
	 * @param userPhone
	 *            사용자 전화번호
	 * @param virtualPhone
	 *            가상 전화번호
	 * @return 성공/실패
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static boolean unregister(String cpNo, String userPhone, String virtualPhone) {
		return process(PacketId.REQ_2502, cpNo, userPhone, virtualPhone);
	}

	/**
	 * 가상전화번호를 상태를 조회한다.
	 * 
	 * @param cpNo
	 *            민원번호
	 * @param userPhone
	 *            사용자 전화번호
	 * @param virtualPhone
	 *            가상 전화번호
	 * @return 성공/실패
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static boolean getStatus(String cpNo, String userPhone, String virtualPhone) {
		return process(PacketId.REQ_2600, cpNo, userPhone, virtualPhone);
	}

	/**
	 * 가상전화번호를 등록한다.
	 * 
	 * @param packetId
	 *            패킷 아이디
	 * @param cpNo
	 *            민원번호
	 * @param userPhone
	 *            사용자 전화번호
	 * @param virtualPhone
	 *            가상 전화번호
	 * @return 성공/실패
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private static boolean process(String packetId, String cpNo, String userPhone, String virtualPhone) {

		if (userPhone == null || virtualPhone == null) {
			return false;
		}
		Socket socket = null;
		InputStream read = null;
		OutputStream write = null;

		String fixedUserPhone = userPhone.replaceAll("-", "");
		String fixedVirtualPhone = virtualPhone.replaceAll("-", "");

		// 성공 여부
		boolean isResult = false;
		try {
			// 초기화
			socket = new Socket(IP, PORT);
			read = socket.getInputStream();
			write = socket.getOutputStream();

			String sendMsg = "";

			sendMsg += START_CHAR;
			sendMsg += matchLength(packetId, 4);
			sendMsg += matchLength(COMPANY_ID, 3);
			sendMsg += matchLength(SYSTEM_ID, 3);
			sendMsg += matchLength(cpNo, 20);
			sendMsg += matchLength(null, 2); // 결과값으로 요구시에는 없음
			sendMsg += matchLength("1", 1); // 0504 번호를 사업자가 미리 할당 후 등록 요청
			sendMsg += matchLength(fixedVirtualPhone, 15); // 가상번호
			sendMsg += matchLength(fixedUserPhone, 15); // 사용자 전화번호1
			sendMsg += matchLength(null, 15); // 사용자 전화번호2
			sendMsg += matchLength(null, 15); // 가상번호
			sendMsg += matchLength(null, 15); // 사용자 전화번호1
			sendMsg += matchLength(null, 15); // 사용자 전화번호2
			sendMsg += matchLength("1", 1); // 사용상태 구분 (1:사용,2:사용정지)
			sendMsg += END_CHAR;

			LOGGER.debug("온세텔레콤 가상번호 소켓 통신");
			LOGGER.debug(sendMsg);

			// 메시지 전송
			write.write(sendMsg.getBytes());

			// 메시지 수신
			byte buffer[] = new byte[126];
			read.read(buffer);
			String res = new String(buffer);

			LOGGER.debug(res);

			String isSuccess = res.substring(31, 33);
			if (isSuccess.equals("00")) {
				isResult = true;
			}
		} catch (Exception e) {
			LOGGER.debug("socket : {}", socket);
			if (socket != null) {
				LOGGER.debug("socket.isConnected() : {}", socket.isConnected());
				LOGGER.debug("socket.isClosed() : {}", socket.isClosed());
				LOGGER.debug("socket.isBound() : {}", socket.isBound());
				LOGGER.debug("socket.isInputShutdown() : {}", socket.isInputShutdown());
				LOGGER.debug("socket.isOutputShutdown() : {}", socket.isOutputShutdown());
			}
			e.printStackTrace();
		} finally {
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
				} finally {
					read = null;
				}
			}
			if (write != null) {
				try {
					write.close();
				} catch (IOException e) {
				} finally {
					write = null;
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				} finally {
					socket = null;
				}
			}
		}
		return isResult;
	}

	/**
	 * 문자열을 원하는 길이만큼 빈공백을 채워서 반환
	 * 
	 * @param str
	 *            문자열
	 * @param length
	 * @return 빈공백이 채워진 문자열
	 */
	private static String matchLength(String str, int length) {
		String result = "";
		if (str == null) {
			for (int i = 0; i < length; i++) {
				result += " ";
			}
			return result;
		} else if (str.length() < length) {
			result += str;
			length -= str.length();
			for (int i = 0; i < length; i++) {
				result += " ";
			}
			return result;
		} else if (str.length() > length) {
			return str.substring(0, length);
		}
		return str;
	}

	// public static void main(String[] args) {
	// try {
	// // 가상번호 사용 등록
	// //VirtualCallUtil.register("141169680484444", "01055642700",
	// "050377460000");
	// // 가상번호 사용 해지
	// VirtualCallUtil.unregister("141169680484444", "01055642700",
	// "050377460000");
	// } catch (UnknownHostException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
