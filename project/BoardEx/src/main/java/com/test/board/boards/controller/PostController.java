package com.test.board.boards.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.board.boards.service.PostService;
import com.test.board.boards.vo.PostVO;
import com.test.board.util.BoardUtil;
import com.test.board.util.PropertyUtil;

/**
 * 게시글 Controller
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Controller
public class PostController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

	/** 게시글 Service */
	@Resource(name = "postService")
	private PostService postService;

	/**
	 * 게시글 목록 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * 
	 * @return "/board/board_list"
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/boardList")
	public String boardList(ModelMap model, @ModelAttribute("searchVO") PostVO postVO) throws Exception {

		// 0. 조건 세팅
		postVO.setCntPerPage(PropertyUtil.getPropertyInt("board", "board.board.boardList.cntPerPage", 10));

		// 1. 총 게시물 수
		// postVO.setTotalCnt(postService.selectPostListTotalCnt(postVO));
		postVO.setTotalCnt(0);

		// 2. 목록
		// model.addAttribute(postService.selectPostList(postVO));

		// 3. 페이징
		model.addAttribute("paging", BoardUtil.getPaging(postVO.getTotalCnt(), postVO.getPg(), postVO.getCntPerPage()));

		return "/board/board_list";
	}

	/**
	 * 게시글 상세 화면 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return "/board/board_write"
	 * @throws Exception
	 */
	@RequestMapping(value = "/board/boardView")
	public String boardWrite(ModelMap model, @ModelAttribute("searchVO") PostVO postVO) throws Exception {

		// if (postVO.getIdx() != 0) {
		// // 조회
		// PostVO resultVO = postService.selectPost(postVO);
		// if (resultVO == null) {
		// return "redirect:/board/boardList.do";
		// }
		// model.addAttribute(resultVO);
		// }

		return "/board/board_view";
	}

}
