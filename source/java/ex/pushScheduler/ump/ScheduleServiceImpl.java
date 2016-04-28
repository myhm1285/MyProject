package com.cosquare.ump.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cosquare.ump.common.service.ScheduleService;
import com.cosquare.ump.send.service.OsTypeConstants;
import com.cosquare.ump.send.service.impl.SendMasterMapper;
import com.cosquare.ump.send.vo.SendMasterVO;
import com.cosquare.ump.util.PropertyUtil;
import com.cosquare.ump.util.crypt.PhoneUtil;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotifications;

/**
 * 스케줄 Service 구현
 * 
 * @author 컨버전스스퀘어
 * @since 2016.04.25
 * @version 1.0
 */
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	/** LOGGER */
	private final static Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	/**
	 * 발송 마스터 Mapper
	 */
	@Resource(name = "sendMasterMapper")
	private SendMasterMapper sendMasterDAO;

	/**
	 * 푸시 스케줄 스케줄러 동작 여부
	 */
	private boolean isSendMessagesTaskRunning = false;

	/** 경로 위치 */
	private final static String PATH_PREFIX = PropertyUtil.class.getResource("").getPath().substring(0,
			PropertyUtil.class.getResource("").getPath().lastIndexOf("com"));

	/**
	 * 푸시 서버로 메시지 요청 전송 스케줄러
	 */
	@Override
	@Async
	@Scheduled(cron = "*/15 * * * * *")
	public void sendMessagesTask() {

		// 개발이면 동작 안함
		if (PropertyUtil.MODE_DEV.equals(PropertyUtil.getMode())) {
			return;
		}

		// 스케줄러 미동작
		if (isSendMessagesTaskRunning == false) {
			// 스케줄러 동작
			isSendMessagesTaskRunning = true;

			try {
				// 1. 발송 마스터 목록 조회 (스케줄용)
				List<SendMasterVO> sendMasterVOList = sendMasterDAO.selectSendMasterListForTask(new SendMasterVO());

				// 운영개발 여부
				boolean isReal = PropertyUtil.getPropertyBoolean("service", "service.apns.isReal", true);

				// GCM API Key
				String gcmApiKey = PropertyUtil.getProperty("service", "service.gcm.apiKey");

				// GCM Message Main Key
				String msgMainKey = PropertyUtil.getProperty("service", "service.msg.mainKey");

				// APNS 인증서 경로
				String certificatePath = PATH_PREFIX + "conf" + System.getProperty("file.separator") + "certificate"
						+ System.getProperty("file.separator") + (isReal ? "real" : "dev") + System.getProperty("file.separator") + "apns.p12";

				// APNS 인증서 패스워드
				String certificatePw = PhoneUtil.decrypt(PropertyUtil.getProperty("service", "service.apns.certificatePw"));

				for (SendMasterVO sendMasterVO : sendMasterVOList) {

					// 2. 메시지 발송
					// 안드로이드 수신자
					if (OsTypeConstants.ANDROID.equals(sendMasterVO.getOsType())) {
						try {
							// 2.1.1 GCM 푸시 전송
							Sender sender = new Sender(gcmApiKey);
							Builder builder = new Message.Builder();
							builder.addData(msgMainKey, sendMasterVO.getMsgContent());
							builder.delayWhileIdle(true);

							Result gcmResult;
							gcmResult = sender.send(builder.build(), sendMasterVO.getRegId(), 1);

							Boolean isSucess = false;
							try {
								if (gcmResult.getMessageId() != null) {
									isSucess = true;
								}
							} catch (Exception e1) {
							}

							// 2.1.2 발송 타겟 완료 갱신
							sendMasterVO.setPushResSts(isSucess ? "Y" : "N");
							sendMasterDAO.updateSendMasterListForTask(sendMasterVO);

							LOGGER.info("---------------------> GCM Message Send Result : {} - {}", (isSucess ? "SUCCESS" : "FAIL"), gcmResult);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// 아이폰 수신자
					else if (OsTypeConstants.IOS.equals(sendMasterVO.getOsType())) {
						try {
							// 2.2.1 APNS 푸시 전송
							PushNotificationPayload payload = PushNotificationPayload.complex();
							payload.addAlert(sendMasterVO.getMsgContent()); // 상태표시줄에 표시될 푸시 내용
							payload.addCustomDictionary(msgMainKey, sendMasterVO.getMsgContent()); // 개별적으로 파싱하여 사용할 내용

							PushedNotifications notifications = Push.payload(payload, certificatePath, certificatePw, isReal,
									sendMasterVO.getRegId());

							Boolean isSucess = false;
							// 성공했으면
							if (notifications != null && notifications.size() > 0 && notifications.get(0).isSuccessful()) {
								isSucess = true;
							}

							// 2.2.2 발송 타겟 완료 갱신
							sendMasterVO.setPushResSts(isSucess ? "Y" : "N");
							sendMasterDAO.updateSendMasterListForTask(sendMasterVO);

							try {
								LOGGER.info("---------------------> APNS Message Send Result : {} - {}", (isSucess ? "SUCCESS" : "FAIL"),
										notifications);
							} catch (Exception e) {
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			} catch (Exception e) {
				LOGGER.error(e.getLocalizedMessage());
				e.printStackTrace();
			} finally {
				// 스케줄러 완료
				isSendMessagesTaskRunning = false;
			}
		}

	}
}
