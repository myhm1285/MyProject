package com.test.board.manage.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.board.manage.service.AccountService;
import com.test.board.manage.vo.AccountVO;
import com.test.board.util.BoardUtil;
import com.test.board.util.PropertyUtil;

/**
 * 계정 Controller
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/setting")
public class AccountController {

	/** LOGGER */
	private final static Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	/** 계정 Service */
	@Resource(name = "accountService")
	private AccountService accountService;

	/**
	 * 계정 목록 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param accountVO
	 *            조회할 정보가 담긴 accountVO
	 * 
	 * @return "/account/account_list"
	 */
	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String accountList(ModelMap model, @ModelAttribute("searchVO") AccountVO accountVO) {

		// 0. 조건 세팅
		accountVO.setCntPerPage(PropertyUtil.getPropertyInt("account", "account.account.accountList.cntPerPage", 10));

		// 1. 총 게시물 수
		accountVO.setTotalCnt(accountService.selectAccountListTotalCnt(accountVO));

		// 2. 목록
		model.addAttribute(accountService.selectAccountList(accountVO));

		// 3. 페이징
		model.addAttribute("paging", BoardUtil.getPaging(accountVO.getTotalCnt(), accountVO.getPg(), accountVO.getCntPerPage()));

		return "/account/account_list";
	}

	/**
	 * 계정 등록 화면 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param accountVO
	 *            조회할 정보가 담긴 accountVO
	 * 
	 * @return "/account/account_write"
	 */
	@RequestMapping(value = "/account/new", method = RequestMethod.GET)
	public String accountWrite(ModelMap model, @ModelAttribute("searchVO") AccountVO accountVO) {

		return "/account/account_write";
	}

	/**
	 * 계정 수정 화면 조회
	 * 
	 * @param idx
	 *            계정 일련번호
	 * @param model
	 *            ModelMap
	 * @param accountVO
	 *            조회할 정보가 담긴 accountVO
	 * 
	 * @return "/account/account_write"
	 */
	@RequestMapping(value = "/account/{idx}/edit", method = RequestMethod.GET)
	public String accountModify(@PathVariable("idx") int idx, ModelMap model, @ModelAttribute("searchVO") AccountVO accountVO) {

		// 조회
		AccountVO resultVO = accountService.selectAccount(accountVO);
		if (resultVO == null) {
			return "redirect:/account";
		}
		model.addAttribute(resultVO);

		return "/account/account_write";
	}

	/**
	 * 계정 등록
	 * 
	 * @param model
	 *            ModelMap
	 * @param accountVO
	 *            조회할 정보가 담긴 accountVO
	 * 
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/account", method = RequestMethod.PUT)
	@ResponseBody
	public String accountWriteProc(@PathVariable("idx") int idx, ModelMap model, @ModelAttribute("searchVO") AccountVO accountVO) {

		try {
			// 1. 등록
			accountService.insertAccount(accountVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

	/**
	 * 계정 수정
	 * 
	 * @param idx
	 *            계정 일련번호
	 * @param model
	 *            ModelMap
	 * @param accountVO
	 *            수정할 정보가 담긴 accountVO
	 * 
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/account/{idx}", method = RequestMethod.POST)
	@ResponseBody
	public String accountModifyProc(@PathVariable("idx") int idx, ModelMap model, @ModelAttribute("searchVO") AccountVO accountVO) {

		try {
			// 0. 조건 세팅
			accountVO.setIdx(idx);

			// 1. 수정
			accountService.updateAccount(accountVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

	/**
	 * 계정 삭제
	 * 
	 * @param idx
	 *            계정 일련번호
	 * @param model
	 *            ModelMap
	 * @param accountVO
	 *            삭제할 정보가 담긴 accountVO
	 * 
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/account/{idx}", method = RequestMethod.DELETE)
	@ResponseBody
	public String accountDeleteProc(@PathVariable("idx") int idx, ModelMap model, @ModelAttribute("searchVO") AccountVO accountVO) {

		try {
			// 0. 조건 세팅
			accountVO.setIdx(idx);

			// 1. 삭제
			accountService.deleteAccount(accountVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

}
