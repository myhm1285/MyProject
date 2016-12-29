package com.test.board.manage.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.board.boards.service.PostService;
import com.test.board.boards.vo.PostVO;

/**
 * 게시글 관리 Controller
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Controller("managePostController")
@RequestMapping(value = "/manage/post")
public class PostController {

	/** LOGGER */
	private final static Logger LOGGER = LoggerFactory.getLogger(PostController.class);

	/** 게시글 Service */
	@Resource(name = "postService")
	private PostService postService;

	/**
	 * 게시글 등록 화면 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param boardVO
	 *            조회할 정보가 담긴 BoardVO
	 * @return "/manage/board_list"
	 */
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String postWrite(ModelMap model, @ModelAttribute("searchVO") PostVO postVO) {

		return "/manage/post_write";
	}

}
