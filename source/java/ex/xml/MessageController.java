package com.cosquare.smp.message.controller;

import javax.annotation.Resource;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.cosquare.smp.batch.service.BatchService;
import com.cosquare.smp.batch.vo.BatchMasterVO;
import com.cosquare.smp.login.vo.LoginVO;
import com.cosquare.smp.message.service.MessageService;
import com.cosquare.smp.message.vo.MsgKeyVO;
import com.cosquare.smp.message.vo.MsgMasterVO;
import com.cosquare.smp.send.service.SendService;
import com.cosquare.smp.send.vo.SendMasterVO;
import com.cosquare.smp.util.BoardUtil;
import com.cosquare.smp.util.PropertyUtil;

/**
 * 메시지 Controller
 *
 * @author 컨버전스스퀘어
 * @since 2016. 2. 23.
 * @version 1.0
 */
@Controller
public class MessageController {

	/** LOGGER */
	private final static Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

	/** Message Service */
	@Resource(name = "messageService")
	private MessageService messageService;

	/** Send Service */
	@Resource(name = "sendService")
	private SendService sendService;

	/** Batch Service */
	@Resource(name = "batchService")
	private BatchService batchService;

	/**
	 * 메시지 목록 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param msgMasterVO
	 *            조회할 정보가 담긴 MsgMasterVO
	 * 
	 * @return "/message/message_list"
	 * @throws Exception
	 */
	@RequestMapping(value = "/message/messageList")
	public String messageList(ModelMap model, @ModelAttribute("searchVO") MsgMasterVO msgMasterVO) throws Exception {

		// 0. 조건 세팅
		LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);
		msgMasterVO.setAccIdx(loginVO.getAccIdx());
		msgMasterVO.setCntPerPage(PropertyUtil.getPropertyInt("board", "board.message.messageList.cntPerPage", 10));

		// 1. 총 게시물 수
		msgMasterVO.setTotalCnt(messageService.selectMessageListTotalCnt(msgMasterVO));

		// 2. 목록
		model.addAttribute(messageService.selectMessageList(msgMasterVO));

		// 3. 페이징
		model.addAttribute("paging", BoardUtil.getPaging(msgMasterVO.getTotalCnt(), msgMasterVO.getPg(), msgMasterVO.getCntPerPage()));

		return "/message/message_list";
	}

	/**
	 * 메시지 등록/수정 화면 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param msgMasterVO
	 *            조회할 정보가 담긴 MsgMasterVO
	 * @return "/message/message_write"
	 * @throws Exception
	 */
	@RequestMapping(value = "/message/messageWrite")
	public String messageWrite(ModelMap model, @ModelAttribute("searchVO") MsgMasterVO msgMasterVO) throws Exception {
		LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);
		msgMasterVO.setAccIdx(loginVO.getAccIdx());
		// 메시지 조회
		if (msgMasterVO.getMsgIdx() != 0) {
			// 1. 메시지 마스터 조회
			MsgMasterVO resultVO = messageService.selectMessage(msgMasterVO);
			if (resultVO == null) {
				return "redirect:/message/messageList.do";
			}
			model.addAttribute(resultVO);

			// 2. 메시지 KEY 조회
			MsgKeyVO msgKeyVO = new MsgKeyVO();
			msgKeyVO.setMsgIdx(msgMasterVO.getMsgIdx());
			msgKeyVO.setCamIdx(resultVO.getCamIdx());
			model.addAttribute(messageService.selectMsgKeyList(msgKeyVO));
		}

		return "/message/message_write";
	}

	/**
	 * 메시지 내용 화면 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @return "/message/message_write_pop"
	 * @throws Exception
	 */
	@RequestMapping(value = "/message/messageWritePop")
	public String messageWritePop(ModelMap model) throws Exception {

		return "/message/message_write_pop";
	}

	/**
	 * 메시지 내용 JSON 형태로 변환
	 * 
	 * @param model
	 *            ModelMap
	 * @return 변환 결과
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/message/messageWritePopForChangeType")
	public String messageWritePopForChangeType(ModelMap model, @RequestBody MsgMasterVO msgMasterVO) throws Exception {
		String result = "";
		try {
			// JSON 형태로 메시지 변환
			if ("JSON".equals(msgMasterVO.getType())) {
				JSONObject jsonMainObj = new JSONObject();
				JSONObject jsonObj = new JSONObject();
				for (MsgKeyVO msgKeyVO : msgMasterVO.getMsgKeyVOList()) {
					jsonObj.put(msgKeyVO.getKeyCd(), msgKeyVO.getVal());
				}

				// 메시지 메인 KEY가 존재하는 경우
				if (msgMasterVO.getMsgMainKey() != null && !"".equals(msgMasterVO.getMsgMainKey())) {
					jsonMainObj.put(msgMasterVO.getMsgMainKey(), jsonObj);
				}
				// 메시지 메인 KEY가 존재하는 경우
				else {
					jsonMainObj = jsonObj;
				}
				result = jsonMainObj.toJSONString();

			}
			// XML 형태로 메시지 변환
			else if ("XML".equals(msgMasterVO.getType())) {
				// 메시지 메인 KEY(root) 생성 (필수)
				Element root = new Element(msgMasterVO.getMsgMainKey());
				Document xmlDoc = new Document(root);

				// 하위 element 생성 및 연결
				Element element;
				for (MsgKeyVO msgKeyVO : msgMasterVO.getMsgKeyVOList()) {
					element = new Element(msgKeyVO.getKeyCd());
					element.setText(msgKeyVO.getVal());

					root.addContent(element);
				}

				// xml 출력
				XMLOutputter xo = new XMLOutputter();
				Format fo = xo.getFormat();
				fo.setEncoding("UTF-8"); // UTF-8
				fo.setIndent(" "); // 들여쓰기
				fo.setLineSeparator("\r\n"); // 줄바꿈
				fo.setTextMode(Format.TextMode.TRIM); // 공백처리
				xo.setFormat(fo);
				result = xo.outputString(xmlDoc);

			}
		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "ERROR";
		}
		return result;
	}

	/**
	 * 메시지 등록
	 * 
	 * @param model
	 *            ModelMap
	 * @param msgMasterVO
	 *            등록할 정보가 담긴 MsgMasterVO
	 * @return 성공하면 Y, 실패하면 N
	 * @throws Exception
	 */
	@RequestMapping(value = "/message/messageWriteProc")
	@ResponseBody
	public String messageWriteProc(ModelMap model, @RequestBody MsgMasterVO msgMasterVO) throws Exception {

		try {
			LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);
			// 등록
			msgMasterVO.setAccIdx(loginVO.getAccIdx());
			messageService.insertMessage(msgMasterVO);
			LOGGER.info("msgMasterVO : {}", msgMasterVO);
		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}
		return "Y";
	}

	/**
	 * 메시지 수정
	 * 
	 * @param model
	 *            ModelMap
	 * @param msgMasterVO
	 *            수정할 정보가 담긴 MsgMasterVO
	 * @return 성공하면 Y, 실패하면 N
	 * @throws Exception
	 */
	@RequestMapping(value = "/message/messageUpdateProc")
	@ResponseBody
	public String messageUpdateProc(ModelMap model, @RequestBody MsgMasterVO msgMasterVO) throws Exception {

		try {
			// 수정
			LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);
			msgMasterVO.setAccIdx(loginVO.getAccIdx());
			messageService.updateMessage(msgMasterVO);
		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}
		return "Y";
	}

	/**
	 * 메시지 삭제
	 * 
	 * @param model
	 *            ModelMap
	 * @param msgMasterVO
	 *            삭제할 정보가 담긴 MsgMasterVO
	 * @return 성공하면 Y, 실패하면 N
	 * @throws Exception
	 */
	@RequestMapping(value = "/message/messageDeleteProc")
	@ResponseBody
	public String messageDeleteProc(ModelMap model, MsgMasterVO msgMasterVO) throws Exception {

		try {
			LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);
			msgMasterVO.setAccIdx(loginVO.getAccIdx());

			// 삭제 가능 여부 확인
			SendMasterVO sendMasterVO = new SendMasterVO();
			sendMasterVO.setAccIdx(loginVO.getAccIdx());
			sendMasterVO.setCamIdx(msgMasterVO.getCamIdx());
			sendMasterVO.setSendYn("N");
			int sndCntUsingCam = sendService.selectSendListTotalCnt(sendMasterVO);

			BatchMasterVO batchMasterVO = new BatchMasterVO();
			batchMasterVO.setAccIdx(loginVO.getAccIdx());
			batchMasterVO.setCamIdx(msgMasterVO.getCamIdx());
			int batCntUsingCam = batchService.selectBatchListTotalCnt(batchMasterVO);

			if ((sndCntUsingCam + batCntUsingCam) > 0) {
				return "D";
			}

			// 삭제
			messageService.deleteMessage(msgMasterVO);
		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}
		return "Y";
	}

	/**
	 * 메시지 목록 Ajax
	 * 
	 * @param model
	 *            ModelMap
	 * @param msgMasterVO
	 *            삭제할 정보가 담긴 MsgMasterVO
	 * @return 메시지 목록
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/message/messageSelectAjax")
	@ResponseBody
	public JSONObject messageSelectAjax(ModelMap model, MsgMasterVO msgMasterVO) throws Exception {

		// 0. 조건 세팅
		JSONObject jsonObject = new JSONObject();
		LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("loginVO", RequestAttributes.SCOPE_SESSION);
		msgMasterVO.setAccIdx(loginVO.getAccIdx());
		msgMasterVO.setCntPerPage(1000);

		// 1. 목록
		jsonObject.put("msgMasterVOList", messageService.selectMessageList(msgMasterVO));

		return jsonObject;
	}

}
