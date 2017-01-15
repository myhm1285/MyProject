package com.test.board.manage.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.board.boards.service.PostService;
import com.test.board.boards.vo.PostVO;
import com.test.board.manage.service.BoardService;
import com.test.board.manage.vo.BoardVO;

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

	/** 게시판 Service */
	@Resource(name = "boardService")
	private BoardService boardService;

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

	/**
	 * 게시글 등록
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param boardVO
	 *            BoardVO
	 * @param postVO
	 *            PostVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/{boardName}", method = RequestMethod.POST)
	@ResponseBody
	public String postWriteProc(@PathVariable("boardName") String boardName, BoardVO boardVO, @RequestBody PostVO postVO) throws Exception {

		try {
			// 0. 조건 세팅
			boardVO = this.getBoardInfo(boardName);
			if (boardVO == null) {
				return "N";
			}
			postVO.setBoardIdx(boardVO.getIdx());
			postVO.setWriterIdx(1);

			// 1. 등록
			postService.insertPost(postVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

	/**
	 * 게시글 수정
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param idx
	 *            게시글 일련번호
	 * @param boardVO
	 *            BoardVO
	 * @param postVO
	 *            PostVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/{boardName}/{idx}", method = RequestMethod.PUT)
	@ResponseBody
	public String postModifyProc(@PathVariable("boardName") String boardName, @PathVariable("idx") int idx, BoardVO boardVO, PostVO postVO) {

		try {
			// 0. 조건 세팅
			boardVO = this.getBoardInfo(boardName);
			if (boardVO == null) {
				return "N";
			}
			postVO.setBoardIdx(boardVO.getIdx());

			// 1. 수정
			postService.updatePost(postVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

	/**
	 * 게시글 삭제
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param idx
	 *            게시글 일련번호
	 * @param boardVO
	 *            BoardVO
	 * @param postVOF
	 *            PostVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/{boardName}/{idx}", method = RequestMethod.DELETE)
	@ResponseBody
	public String postDeleteProc(@PathVariable("boardName") String boardName, @PathVariable("idx") int idx, BoardVO boardVO, PostVO postVO) {

		try {
			// 0. 조건 세팅
			boardVO = this.getBoardInfo(boardName);
			if (boardVO == null) {
				return "N";
			}
			postVO.setBoardIdx(boardVO.getIdx());

			// 1. 삭제
			postService.deletePost(postVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

	/**
	 * 게시판 이름으로 게시판 정보를 찾는다.
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @return BoardVO
	 */
	private BoardVO getBoardInfo(String boardName) {
		BoardVO boardVO = new BoardVO();
		boardVO.setName(boardName);
		return boardService.selectBoard(boardVO);
	}

}
