package com.cosquare.smp.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cosquare.smp.app.service.impl.AppAndroidMapper;
import com.cosquare.smp.app.service.impl.AppIosMapper;
import com.cosquare.smp.app.vo.AppAndroidVO;
import com.cosquare.smp.app.vo.AppIosVO;
import com.cosquare.smp.batch.service.impl.BatchMasterMapper;
import com.cosquare.smp.campaign.service.impl.CampaignConditionMapper;
import com.cosquare.smp.campaign.vo.CampaignConditionVO;
import com.cosquare.smp.common.service.ScheduleService;
import com.cosquare.smp.db.service.ColReqUseConstants;
import com.cosquare.smp.db.service.impl.DbColumnMapper;
import com.cosquare.smp.db.service.impl.DbSchemaMapper;
import com.cosquare.smp.db.vo.DbColumnVO;
import com.cosquare.smp.db.vo.DbSchemaVO;
import com.cosquare.smp.ext.ExtConnManager;
import com.cosquare.smp.message.service.MessageService;
import com.cosquare.smp.send.service.AppTypeConstants;
import com.cosquare.smp.send.service.impl.SendMasterMapper;
import com.cosquare.smp.send.service.impl.SendTargetMapper;
import com.cosquare.smp.send.vo.SendMasterVO;
import com.cosquare.smp.send.vo.SendTargetVO;
import com.cosquare.smp.util.PropertyUtil;
import com.cosquare.smp.util.crypt.PhoneUtil;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotifications;

/**
 * 스케줄 Service 구현 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	/** LOGGER */
	private final static Logger LOGGER = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	/** Message Service */
	@Resource(name = "messageService")
	private MessageService messageService;

	/**
	 * 발송 마스터 Mapper
	 */
	@Resource(name = "sendMasterMapper")
	private SendMasterMapper sendMasterDAO;

	/**
	 * 발송 타겟 Mapper
	 */
	@Resource(name = "sendTargetMapper")
	private SendTargetMapper sendTargetDAO;

	/**
	 * 배치 마스터 Mapper
	 */
	@Resource(name = "batchMasterMapper")
	private BatchMasterMapper batchMasterDAO;

	/**
	 * DB 스키마 Mapper
	 */
	@Resource(name = "dbSchemaMapper")
	private DbSchemaMapper dbSchemaDAO;

	/**
	 * DB 컬럼 Mapper
	 */
	@Resource(name = "dbColumnMapper")
	private DbColumnMapper dbColumnDAO;

	/**
	 * Campaign 조건 Mapper
	 */
	@Resource(name = "campaignConditionMapper")
	private CampaignConditionMapper campaignConditionDAO;

	/**
	 * App 안드로이드 Mapper
	 */
	@Resource(name = "appAndroidMapper")
	private AppAndroidMapper appAndroidDAO;

	/**
	 * App IOS Mapper
	 */
	@Resource(name = "appIosMapper")
	private AppIosMapper appIosDAO;

	/**
	 * 배치용 발송 등록 스케줄러 동작 여부
	 */
	private boolean isAddSendForBatchTaskRunning = false;

	/**
	 * 타겟 등록 스케줄러 동작 여부
	 */
	private boolean isAddSendTargetTaskRunning = false;

	/**
	 * 푸시 스케줄 스케줄러 동작 여부
	 */
	private boolean isSendMessagesTaskRunning = false;

	/**
	 * 배치용 발송 등록 스케줄러
	 */
	@Override
	@Async
	@Scheduled(cron = "*/60 * * * * *")
	public void addSendForBatchTask() {

		// 개발이면 동작 안함
		if (PropertyUtil.MODE_DEV.equals(PropertyUtil.getMode())) {
			return;
		}

		// 스케줄러 미동작
		if (isAddSendTargetTaskRunning == false) {
			// 스케줄러 동작
			isAddSendTargetTaskRunning = true;

			try {
				// 1. 스케줄 시간에 맞는 배치 발송
				sendMasterDAO.insertSendMasterForBatch(new SendMasterVO());

			} catch (Exception e) {
				LOGGER.error(e.getLocalizedMessage());
				e.printStackTrace();
			} finally {
				// 스케줄러 완료
				isAddSendTargetTaskRunning = false;
			}
		}
	}

	/**
	 * 타겟 등록 스케줄러
	 */
	@Override
	@Async
	@Scheduled(cron = "*/30 * * * * *")
	public void addSendTargetTask() {

		// 개발이면 동작 안함
		if (PropertyUtil.MODE_DEV.equals(PropertyUtil.getMode())) {
			return;
		}

		// 스케줄러 미동작
		if (isAddSendForBatchTaskRunning == false) {
			// 스케줄러 동작
			isAddSendForBatchTaskRunning = true;

			try {
				// 1. 발송 마스터 목록 조회 (타겟용)
				SendMasterVO tempSendMasterVO = new SendMasterVO();
				List<SendMasterVO> sendMasterVOList = sendMasterDAO.selectSendMasterListForTarget(tempSendMasterVO);

				for (SendMasterVO sendMasterVO : sendMasterVOList) {
					// 2. DB 정보 확인
					// 2.1 DB 접속정보 확인
					DbSchemaVO dbSchemaVO = new DbSchemaVO();
					dbSchemaVO.setAccIdx(sendMasterVO.getAccIdx());
					dbSchemaVO.setDbsIdx(sendMasterVO.getDbsIdx());
					DbSchemaVO resultDbSchemaVO = dbSchemaDAO.selectDbSchema(dbSchemaVO);

					// DB 호스트 복호화
					if (resultDbSchemaVO.getHost() != null) {
						resultDbSchemaVO.setHost(PhoneUtil.decrypt(resultDbSchemaVO.getHost()));
					}
					// DB 스키마 복호화
					if (resultDbSchemaVO.getDbSchema() != null) {
						resultDbSchemaVO.setDbSchema(PhoneUtil.decrypt(resultDbSchemaVO.getDbSchema()));
					}
					// DB 포트 복호화
					if (resultDbSchemaVO.getPort() != 0) {
					}
					// DB 아이디 복호화
					if (resultDbSchemaVO.getDbId() != null) {
						resultDbSchemaVO.setDbId(PhoneUtil.decrypt(resultDbSchemaVO.getDbId()));
					}
					// DB 비밀번호 복호화
					if (resultDbSchemaVO.getDbPwd() != null) {
						resultDbSchemaVO.setDbPwd(PhoneUtil.decrypt(resultDbSchemaVO.getDbPwd()));
					}

					// 2.2 DB 칼럼 목록 확인
					DbColumnVO dbColumnVO = new DbColumnVO();
					dbColumnVO.setDbsIdx(sendMasterVO.getDbsIdx());
					dbColumnVO.setTableCd(sendMasterVO.getTableCd());
					List<DbColumnVO> resultDbCoulmnVOList = dbColumnDAO.selectDbColumnListByTableCd(dbColumnVO);

					// 2.4 캠페인 조건 확인
					CampaignConditionVO campaignConditionVO = new CampaignConditionVO();
					campaignConditionVO.setCamIdx(sendMasterVO.getCamIdx());
					List<CampaignConditionVO> resultCampaignConditionVOList = campaignConditionDAO.selectCampaignConditionList(campaignConditionVO);

					// 2.5 발송 타겟 등록
					// 안드로이드
					if ("Y".equals(sendMasterVO.getAndYn())) {
						// 2.5.1 조회 쿼리 작성
						String andColCd = "";
						for (DbColumnVO resultDbCoulmnVO : resultDbCoulmnVOList) {
							if (ColReqUseConstants.ANDROID.equals(resultDbCoulmnVO.getColReqUse())) {
								andColCd = resultDbCoulmnVO.getColCd();
								break;
							}
						}
						String andRegIdSelectQuery = makeRegIdSelectQuery(ColReqUseConstants.ANDROID, sendMasterVO.getTableCd(), andColCd,
								resultCampaignConditionVOList);

						// 2.5.2 DB 접속 및 쿼리정보 반환
						ExtConnManager andEcm = new ExtConnManager(resultDbSchemaVO.getHost(), resultDbSchemaVO.getPort(),
								resultDbSchemaVO.getDbSchema(), resultDbSchemaVO.getDbId(), resultDbSchemaVO.getDbPwd(),
								resultDbSchemaVO.getJdbcDriver());
						List<String> andTarRegIdList = andEcm.selectRegIdList(andRegIdSelectQuery);

						// 2.5.3 REG ID 세팅
						List<SendTargetVO> andSendTargetVOList = new ArrayList<SendTargetVO>();
						SendTargetVO temp1SendTargetVO;
						for (String andRegId : andTarRegIdList) {
							if (andRegId != null) {

								temp1SendTargetVO = new SendTargetVO();
								temp1SendTargetVO.setAppType(ColReqUseConstants.ANDROID);
								temp1SendTargetVO.setSndIdx(sendMasterVO.getSndIdx());
								temp1SendTargetVO.setCamIdx(sendMasterVO.getCamIdx());
								temp1SendTargetVO.setMsgIdx(sendMasterVO.getMsgIdx());
								temp1SendTargetVO.setTarRegId(andRegId);

								andSendTargetVOList.add(temp1SendTargetVO);
							}
						}

						// 2.5.4 타겟 등록
						for (SendTargetVO andSendTargetVO : andSendTargetVOList) {
							sendTargetDAO.insertSendTarget(andSendTargetVO);
						}
					}

					// IOS
					if ("Y".equals(sendMasterVO.getIosYn())) {
						// 2.5.1 조회 쿼리 작성
						String iosColCd = "";
						for (DbColumnVO resultDbCoulmnVO : resultDbCoulmnVOList) {
							if (ColReqUseConstants.IOS.equals(resultDbCoulmnVO.getColReqUse())) {
								iosColCd = resultDbCoulmnVO.getColCd();
								break;
							}
						}
						String iosRegIdSelectQuery = makeRegIdSelectQuery(ColReqUseConstants.IOS, sendMasterVO.getTableCd(), iosColCd,
								resultCampaignConditionVOList);

						// 2.5.2 DB 접속 및 쿼리정보 반환
						ExtConnManager iosEcm = new ExtConnManager(resultDbSchemaVO.getHost(), resultDbSchemaVO.getPort(),
								resultDbSchemaVO.getDbSchema(), resultDbSchemaVO.getDbId(), resultDbSchemaVO.getDbPwd(),
								resultDbSchemaVO.getJdbcDriver());
						List<String> iosTarRegIdList = iosEcm.selectRegIdList(iosRegIdSelectQuery);

						// 2.5.3 REG ID 세팅
						List<SendTargetVO> iosSendTargetVOList = new ArrayList<SendTargetVO>();
						SendTargetVO temp2SendTargetVO;
						for (String iosRegId : iosTarRegIdList) {
							if (iosRegId != null) {
								temp2SendTargetVO = new SendTargetVO();
								temp2SendTargetVO.setAppType(ColReqUseConstants.IOS);
								temp2SendTargetVO.setSndIdx(sendMasterVO.getSndIdx());
								temp2SendTargetVO.setCamIdx(sendMasterVO.getCamIdx());
								temp2SendTargetVO.setMsgIdx(sendMasterVO.getMsgIdx());
								temp2SendTargetVO.setTarRegId(iosRegId);

								iosSendTargetVOList.add(temp2SendTargetVO);
							}
						}

						// 2.5.4 타겟 등록
						for (SendTargetVO iosSendTargetVO : iosSendTargetVOList) {
							sendTargetDAO.insertSendTarget(iosSendTargetVO);
						}
					}

					// 2.6 발송 마스터 완료 갱신
					sendMasterDAO.updateSendMasterListForTask(sendMasterVO);

				}

			} catch (Exception e) {
				LOGGER.error(e.getLocalizedMessage());
				e.printStackTrace();
			} finally {
				// 스케줄러 완료
				isAddSendForBatchTaskRunning = false;
			}
		}

	}

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
				SendMasterVO tempSendMasterVO = new SendMasterVO();
				List<SendMasterVO> sendMasterVOList = sendMasterDAO.selectSendMasterListForTask(tempSendMasterVO);

				// 안드로이드 수신자 REG ID
				List<String> toAndRegIdArray;
				// 아이폰 수신자 REG ID
				List<String> toIosRegIdArray;

				AppAndroidVO tempAppAndroidVO;
				AppIosVO tempAppIosVO;
				SendTargetVO tempSendTargetVO;

				// 수신자 정보 목록
				List<SendTargetVO> sendTargetVOList;

				for (SendMasterVO sendMasterVO : sendMasterVOList) {

					// 안드로이드 수신자 REG ID
					toAndRegIdArray = new ArrayList<String>();
					// 아이폰 수신자 REG ID
					toIosRegIdArray = new ArrayList<String>();

					// 2. 수신자 전화번호 수집
					// 한번에 1,000건만 조회함
					tempSendTargetVO = new SendTargetVO();
					tempSendTargetVO.setSndIdx(sendMasterVO.getSndIdx());
					sendTargetVOList = sendTargetDAO.selectSendTargetListForTask(tempSendTargetVO);

					// OS Type 별로 수신자 분리
					for (SendTargetVO sendTargetVO : sendTargetVOList) {
						// 안드로이드 수신자
						if (AppTypeConstants.ANDROID.equals(sendTargetVO.getAppType())) {
							toAndRegIdArray.add(sendTargetVO.getTarRegId());
						}
						// 아이폰 수신자
						else if (AppTypeConstants.IOS.equals(sendTargetVO.getAppType())) {
							toIosRegIdArray.add(sendTargetVO.getTarRegId());
						}
					}

					// 3. 수신받을 APP 정보 조회
					// 안드로이드 APP 정보
					tempAppAndroidVO = new AppAndroidVO();
					tempAppAndroidVO.setAppIdx(sendMasterVO.getAppIdx());
					AppAndroidVO appAndroidVO = appAndroidDAO.selectAppAndroid(tempAppAndroidVO);

					// IOS APP 정보
					tempAppIosVO = new AppIosVO();
					tempAppIosVO.setAppIdx(sendMasterVO.getAppIdx());
					AppIosVO appIosVO = appIosDAO.selectAppIos(tempAppIosVO);

					// 4. 메시지 발송
					// 안드로이드 수신자
					if (toAndRegIdArray.size() > 0) {
						try {
							// 4.1 GCM 푸시 전송
							Sender sender = new Sender(("R".equals(appAndroidVO.getKeyType()) ? appAndroidVO.getKeyRun() : appAndroidVO.getKeyDev()));
							Builder builder = new Message.Builder();
							builder.addData(sendMasterVO.getMsgMainKey() == null ? "" : sendMasterVO.getMsgMainKey(), sendMasterVO.getFullMsg());
							builder.delayWhileIdle(true);

							MulticastResult userAppResult;
							userAppResult = sender.send(builder.build(), toAndRegIdArray, 1);

							Boolean isSucess = false;
							try {
								if (userAppResult.getSuccess() > 0) {
									isSucess = true;
								}
							} catch (Exception e1) {
							}

							// 4.2 발송 타겟 완료 갱신
							for (String toAndRegId : toAndRegIdArray) {
								tempSendTargetVO = new SendTargetVO();
								tempSendTargetVO.setSndIdx(sendMasterVO.getSndIdx());
								tempSendTargetVO.setTarRegId(toAndRegId);
								tempSendTargetVO.setPushResSts(isSucess ? "Y" : "N");
								sendTargetDAO.updateSendTargetByResponse(tempSendTargetVO);
							}
							LOGGER.info("---------------------> GCM Message Send Result : {} - {}", (isSucess ? "SUCCESS" : "FAIL"), userAppResult);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					// 아이폰 수신자
					if (toIosRegIdArray.size() > 0) {
						try {
							// 4.2 APNS 푸시 전송
							int certType = "R".equals(appIosVO.getCertType()) ? 1 : 0; // 개발운영여부 (0:개발,1:운영)

							String certFileUrls[] = { appIosVO.getCertDevFile(), appIosVO.getCertRunFile() };
							String certPwds[] = { appIosVO.getCertDevPwd(), appIosVO.getCertRunPwd() };

							PushNotificationPayload payload = PushNotificationPayload.complex();
							payload.addCustomDictionary(sendMasterVO.getMsgMainKey() == null ? "" : sendMasterVO.getMsgMainKey(),
									sendMasterVO.getFullMsg());

							PushedNotifications notifications = Push.payload(payload,
									PropertyUtil.getProperty("upload", "upload.path.apns.cert", true) + certFileUrls[certType],
									PhoneUtil.decrypt(certPwds[certType]), (certType == 1), toIosRegIdArray);

							Boolean isSucess = false;
							// 성공했으면
							if (notifications != null && notifications.size() > 0 && notifications.get(0).isSuccessful()) {
								isSucess = true;
							}

							// 4.2 발송 타겟 완료 갱신
							for (String toIosRegId : toIosRegIdArray) {
								tempSendTargetVO = new SendTargetVO();
								tempSendTargetVO.setSndIdx(sendMasterVO.getSndIdx());
								tempSendTargetVO.setTarRegId(toIosRegId);
								tempSendTargetVO.setPushResSts(isSucess ? "Y" : "N");
								sendTargetDAO.updateSendTargetByResponse(tempSendTargetVO);
							}

							try {
								LOGGER.info("---APNS Message Send Result : {} - {}", (isSucess ? "SUCCESS" : "FAIL"), notifications);
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

	/**
	 * REG ID SELECT QUERY를 만든다.
	 * 
	 * @param osGbn
	 * @param tableName
	 * @param targetName
	 * @param campaignConditionVOList
	 * @return REG ID SELECT QUERY
	 */
	private String makeRegIdSelectQuery(String osGbn, String tableName, String targetName, List<CampaignConditionVO> campaignConditionVOList) {

		StringBuilder targetSelectQuery = new StringBuilder();
		targetSelectQuery.append("SELECT ");
		targetSelectQuery.append(targetName); // REG ID COLUMN 이름
		targetSelectQuery.append(" AS REG_ID FROM ");
		targetSelectQuery.append(tableName); // TALBE 이름
		targetSelectQuery.append(" WHERE 1=1");
		// 조건
		for (CampaignConditionVO campaignConditionVO : campaignConditionVOList) {
			if (osGbn.equals(campaignConditionVO.getColUse()) || "G".equals(campaignConditionVO.getColUse())) {
				targetSelectQuery.append(" AND ");
				targetSelectQuery.append(campaignConditionVO.getColCd());
				targetSelectQuery.append(" ");
				targetSelectQuery.append(PropertyUtil.getProperty("operator", "operator." + campaignConditionVO.getColOper()));
				targetSelectQuery.append(" ");
				if ("N".equals(campaignConditionVO.getColFormat())) {
					targetSelectQuery.append(campaignConditionVO.getColVal());
				} else {
					targetSelectQuery.append("'");
					targetSelectQuery.append(campaignConditionVO.getColVal());
					targetSelectQuery.append("'");
				}
			}
		}
		return targetSelectQuery.toString();
	}
}
