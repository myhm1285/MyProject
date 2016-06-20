package com.test.board.manage.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
public class AccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

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
	 * @throws Exception
	 */
	@RequestMapping(value = "/account/accountList")
	public String accountList(ModelMap model, @ModelAttribute("searchVO") AccountVO accountVO) throws Exception {

		// 0. 조건 세팅
		accountVO.setCntPerPage(PropertyUtil.getPropertyInt("account", "account.account.accountList.cntPerPage", 10));

		// 1. 총 게시물 수
		// accountVO.setTotalCnt(accountService.selectaccountListTotalCnt(accountVO));
		accountVO.setTotalCnt(0);

		// 2. 목록
		// model.addAttribute(accountService.selectaccountList(accountVO));

		// 3. 페이징
		model.addAttribute("paging", BoardUtil.getPaging(accountVO.getTotalCnt(), accountVO.getPg(), accountVO.getCntPerPage()));

		return "/account/account_list";
	}

	/**
	 * 계정 상세 화면 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param accountVO
	 *            조회할 정보가 담긴 accountVO
	 * @return "/account/account_write"
	 * @throws Exception
	 */
	@RequestMapping(value = "/account/accountView")
	public String accountWrite(ModelMap model, @ModelAttribute("searchVO") AccountVO accountVO) throws Exception {

		// if (accountVO.getIdx() != 0) {
		// // 조회
		// AccountVO resultVO = accountService.selectaccount(accountVO);
		// if (resultVO == null) {
		// return "redirect:/account/accountList.do";
		// }
		// model.addAttribute(resultVO);
		// }

		return "/account/account_view";
	}

}
