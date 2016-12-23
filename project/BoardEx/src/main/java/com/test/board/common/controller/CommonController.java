package com.test.board.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 공통 콘트롤러 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2015.08.01
 * @version 1.0
 */
@Controller
public class CommonController {

	/** 로거 */
	private final static Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

	/**
	 * index
	 * 
	 * @param model
	 *            ModelMap
	 * @return "redirect:/board/"
	 */
	@RequestMapping(value = "/")
	public String index(ModelMap model) {

		LOGGER.debug("index");
		return "redirect:/boards/";
	}
}
