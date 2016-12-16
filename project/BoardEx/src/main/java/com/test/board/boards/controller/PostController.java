package com.test.board.boards.controller;

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

import com.test.board.boards.service.PostService;
import com.test.board.boards.vo.PostVO;
import com.test.board.manage.service.BoardService;
import com.test.board.manage.vo.BoardVO;
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
@RequestMapping(value = "/board")
public class PostController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

	/** 게시글 Service */
	@Resource(name = "postService")
	private PostService postService;

	/** 게시판 Service */
	@Resource(name = "boardService")
	private BoardService boardService;

	/**
	 * 게시글 목록 조회
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return "/boards/post_list"
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String allPostList(ModelMap model, @ModelAttribute("searchVO") PostVO postVO) {

		postVO.setCntPerPage(PropertyUtil.getPropertyInt("board", "board.post.postList.cntPerPage", 10));

		// 1. 총 게시글 수
		postVO.setTotalCnt(postService.selectPostListTotalCnt(postVO));

		// 2. 목록
		model.addAttribute(postService.selectPostList(postVO));

		// 3. 페이징
		model.addAttribute("paging", BoardUtil.getPaging(postVO.getTotalCnt(), postVO.getPg(), postVO.getCntPerPage()));

		return "/boards/post_list";
	}

	/**
	 * 게시글 목록 조회
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return "/boards/post_list"
	 */
	@RequestMapping(value = "/{boardName}", method = RequestMethod.GET)
	public String postList(@PathVariable("boardName") String boardName, ModelMap model, @ModelAttribute("searchVO") PostVO postVO) {

		// 0. 조건 세팅
		int boardIdx = this.getBoardIdx(boardName);
		if (boardIdx == 0) {
			return "/common/404";
		}
		postVO.setBoardIdx(boardIdx);
		postVO.setCntPerPage(PropertyUtil.getPropertyInt("board", "board.post.postList.cntPerPage", 10));

		// 1. 총 게시글 수
		postVO.setTotalCnt(postService.selectPostListTotalCnt(postVO));

		// 2. 목록
		model.addAttribute(postService.selectPostList(postVO));

		// 3. 페이징
		model.addAttribute("paging", BoardUtil.getPaging(postVO.getTotalCnt(), postVO.getPg(), postVO.getCntPerPage()));

		return "/boards/post_list";
	}

	/**
	 * 게시글 조회
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param idx
	 *            게시글 일련번호
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return "/boards/board_view"
	 */
	@RequestMapping(value = "/{boardName}/{idx}", method = RequestMethod.GET)
	public String postView(@PathVariable("boardName") String boardName, @PathVariable("idx") int idx, ModelMap model,
			@ModelAttribute("searchVO") PostVO postVO) {

		// 0. 조건 세팅
		int boardIdx = this.getBoardIdx(boardName);
		if (boardIdx == 0) {
			return "/common/404";
		}
		postVO.setBoardIdx(boardIdx);

		// 1. 조회
		PostVO resultVO = postService.selectPost(postVO);
		if (resultVO == null) {
			return "/common/404";
		}
		model.addAttribute(resultVO);

		return "/boards/board_view";
	}

	/**
	 * 게시글 등록 화면 조회
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return "/boards/board_write"
	 */
	@RequestMapping(value = "/{boardName}/new", method = RequestMethod.GET)
	public String postWrite(@PathVariable("boardName") String boardName, ModelMap model, @ModelAttribute("searchVO") PostVO postVO) {

		// 0. 조건 세팅
		int boardIdx = this.getBoardIdx(boardName);
		if (boardIdx == 0) {
			return "/common/404";
		}
		postVO.setBoardIdx(boardIdx);

		return "/boards/board_write";
	}

	/**
	 * 게시글 수정 화면 조회
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param idx
	 *            게시글 일련번호
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return "/boards/board_write"
	 */
	@RequestMapping(value = "/{boardName}/{idx}/modify", method = RequestMethod.GET)
	public String postModify(@PathVariable("boardName") String boardName, @PathVariable("idx") int idx, ModelMap model,
			@ModelAttribute("searchVO") PostVO postVO) {

		// 0. 조건 세팅
		int boardIdx = this.getBoardIdx(boardName);
		if (boardIdx == 0) {
			return "/common/404";
		}
		postVO.setBoardIdx(boardIdx);

		// 1. 조회
		PostVO resultVO = postService.selectPost(postVO);
		if (resultVO == null) {
			return "/common/404";
		}
		model.addAttribute(resultVO);

		return "/boards/board_write";
	}

	/**
	 * 게시글 등록
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            등록할 정보가 담긴 PostVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/{boardName}", method = RequestMethod.PUT)
	@ResponseBody
	public String postWriteProc(@PathVariable("boardName") String boardName, ModelMap model, @ModelAttribute("searchVO") PostVO postVO)
			throws Exception {

		try {
			// 0. 조건 세팅
			int boardIdx = this.getBoardIdx(boardName);
			if (boardIdx == 0) {
				return "/common/404";
			}
			postVO.setBoardIdx(boardIdx);

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
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            수정할 정보가 담긴 PostVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/{boardName}/{idx}", method = RequestMethod.POST)
	@ResponseBody
	public String postModifyProc(@PathVariable("boardName") String boardName, @PathVariable("idx") int idx, ModelMap model,
			@ModelAttribute("searchVO") PostVO postVO) {

		try {
			// 0. 조건 세팅
			int boardIdx = this.getBoardIdx(boardName);
			if (boardIdx == 0) {
				return "/common/404";
			}
			postVO.setBoardIdx(boardIdx);

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
	 * @param model
	 *            ModelMap
	 * @param postVO
	 *            삭제할 정보가 담긴 PostVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/{boardName}/{idx}", method = RequestMethod.DELETE)
	@ResponseBody
	public String postDeleteProc(@PathVariable("boardName") String boardName, @PathVariable("idx") int idx, ModelMap model,
			@ModelAttribute("searchVO") PostVO postVO) {

		try {
			// 0. 조건 세팅
			int boardIdx = this.getBoardIdx(boardName);
			if (boardIdx == 0) {
				return "/common/404";
			}
			postVO.setBoardIdx(boardIdx);

			// 1. 삭제
			postService.deletePost(postVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

	/**
	 * 게시판 이름으로 일련번호를 찾는다.
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @return 게시판 일련번호
	 */
	private int getBoardIdx(String boardName) {
		BoardVO boardVO = new BoardVO();
		boardVO.setName(boardName);
		boardVO = boardService.selectBoardForName(boardVO);

		if (boardVO == null) {
			return 0;
		}

		return boardVO.getIdx();
	}

}
