package com.itw.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * MailUtil
 *
 * @author 컨버전스스퀘어
 * @since 2015. 9. 22.
 * @version 1.0
 */
public class MailUtil {

	/**
	 * GMail SMTP를 이용한 이메일 전송 (단건)
	 * 
	 * @param fromMail
	 *            보내는 사람 메일 주소
	 * @param toMailList
	 *            받는 사람 메일 주소
	 * @param mailTitle
	 *            메일 제목
	 * @param mailContent
	 *            메일 내용
	 * @return 성공 여부
	 * @throws UnsupportedEncodingException
	 */
	public static boolean sendMail(String fromMail, String toMail, String mailTitle, String mailContent) throws UnsupportedEncodingException {
		List<String> toMailList = new ArrayList<String>();
		toMailList.add(toMail);

		return sendMail(fromMail, toMailList, mailTitle, mailContent);
	}

	/**
	 * GMail SMTP를 이용한 이메일 전송
	 * 
	 * @param fromMail
	 *            보내는 사람 메일 주소
	 * @param toMailList
	 *            받는 사람 메일 주소 리스트
	 * @param mailTitle
	 *            메일 제목
	 * @param mailContent
	 *            메일 내용
	 * @return 성공 여부
	 * @throws UnsupportedEncodingException
	 */
	public static boolean sendMail(String fromMail, List<String> toMailList, String mailTitle, String mailContent)
			throws UnsupportedEncodingException {

		// GMail 아이디
		String id = PropertyUtil.getProperty("service", "service.mail.id");
		// GMail 비밀번호(ACCESS 비밀번호)
		String pw = PropertyUtil.getProperty("service", "service.mail.pw");

		Authenticator auth = new MyAuthentication(id, pw);

		Properties p = System.getProperties();
		p.put("mail.smtp.starttls.enable", "true"); // gmail은 무조건 true 고정
		p.put("mail.smtp.host", "smtp.gmail.com"); // smtp 서버 주소
		p.put("mail.smtp.auth", "true"); // gmail은 무조건 true 고정
		p.put("mail.smtp.port", "587"); // gmail 포트

		// session 생성 및 MimeMessage생성
		Session session = Session.getDefaultInstance(p, auth);
		MimeMessage msg = new MimeMessage(session);

		try {
			InternetAddress fromAddr = new InternetAddress();
			fromAddr = new InternetAddress(fromMail, "webmaster");

			// 이메일 발신자
			msg.setFrom(fromAddr);

			// 이메일 수신자
			InternetAddress[] addressTo = new InternetAddress[toMailList.size()];
			for (int i = 0; i < toMailList.size(); i++) {
				addressTo[i] = new InternetAddress(toMailList.get(i));
			}
			msg.setRecipients(Message.RecipientType.TO, addressTo);

			// 이메일 보내는 시간
			msg.setSentDate(new Date());

			// 이메일 제목
			msg.setSubject(mailTitle, "UTF-8");

			// 이메일 내용
			msg.setText(mailContent, "UTF-8");

			// 이메일 헤더
			msg.setHeader("content-Type", "text/html");

			// 메일보내기
			Transport.send(msg);

		} catch (AddressException addr_e) {
			addr_e.printStackTrace();

			return false;
		} catch (MessagingException msg_e) {
			msg_e.printStackTrace();

			return false;
		}
		return true;
	}

	/**
	 * 메일 폼
	 * 
	 * @param args
	 * @return 비밀번호 초기화 메일 내용 Html
	 */
	public static String mailContentForm(String fileName, String[][] changeArray) {
		String readLine = "";
		String mailContent = "";
		BufferedReader br = null;
		InputStreamReader ir = null;
		FileInputStream fi = null;

		try {
			fi = new FileInputStream(MailUtil.class.getResource("/").getPath() + "conf/mail/" + fileName + ".html");
			ir = new InputStreamReader(fi, "UTF-8");
			br = new BufferedReader(ir);

			while ((readLine = br.readLine()) != null) {
				for (int i = 0; i < changeArray.length; i++) {
					readLine = readLine.replace(changeArray[i][0], changeArray[i][1]);
				}
				mailContent += readLine + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				ir.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return mailContent;
	}
}

/**
 * MyAuthentication
 *
 * @author 컨버전스스퀘어
 * @since 2015. 9. 22.
 * @version 1.0
 */
class MyAuthentication extends Authenticator {

	PasswordAuthentication pa;

	public MyAuthentication(String id, String pw) {
		// ID와 비밀번호 입력
		pa = new PasswordAuthentication(id, pw);
	}

	// 시스템에서 사용하는 인증정보
	public PasswordAuthentication getPasswordAuthentication() {
		return pa;
	}
}