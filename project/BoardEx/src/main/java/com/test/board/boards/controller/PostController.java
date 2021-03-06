package com.test.board.boards.controller;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.board.boards.service.PostService;
import com.test.board.boards.vo.PostVO;
import com.test.board.manage.service.BoardService;
import com.test.board.manage.vo.BoardVO;
import com.test.board.util.BoardUtil;

/**
 * 게시글 Controller
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/boards")
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
	 * @param model
	 *            ModelMap
	 * @param boardName
	 *            게시판 이름
	 * @param boardVO
	 *            BoardVO
	 * @param postVO
	 *            PostVO
	 * @return "/boards/post_list"
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String allPostList(ModelMap model, BoardVO boardVO, PostVO postVO) {

		// 0. 조건 세팅
		boardVO.setMode("VIEW");
		postVO.setMode("VIEW");

		// 1. 총 게시글 수
		postVO.setTotalCnt(boardService.selectBoardAllPostTotalCnt(boardVO));
		boardVO.setName("전체");
		boardVO.setPostCnt(postVO.getTotalCnt());

		// 2. 목록
		model.addAttribute("postVOViewList", postService.selectPostViewList(postVO));

		// 3. 페이징
		model.addAttribute("viewListPaging", BoardUtil.getPaging(postVO.getTotalCnt(), postVO.getPg(), postVO.getCntPerPage(), "viewListPageMove"));

		// 4. 게시판 정보
		model.addAttribute(boardVO);

		return "/boards/post_list";
	}

	/**
	 * 게시글 목록 조회 (내용 포함)
	 * 
	 * @param model
	 *            ModelMap
	 * @param boardName
	 *            게시판 이름
	 * @param boardVO
	 *            BoardVO
	 * @param postVO
	 *            PostVO
	 * @return "/boards/post_list"
	 */
	@RequestMapping(value = "/{boardName}", method = RequestMethod.GET)
	public String postList(ModelMap model, @PathVariable("boardName") String boardName, BoardVO boardVO, PostVO postVO) {

		// 0. 조건 세팅
		boardVO = this.getBoardInfo(boardName);
		if (boardVO == null) {
			return "/common/404";
		}
		postVO.setMode("VIEW");
		postVO.setBoardIdx(boardVO.getIdx());
		postVO.setCntPerPage(boardVO.getPageCnt());
		postVO.setTotalCnt(boardVO.getPostCnt());

		// 1. 목록
		model.addAttribute("postVOViewList", postService.selectPostViewList(postVO));

		// 2. 페이징
		model.addAttribute("viewListPaging", BoardUtil.getPaging(postVO.getTotalCnt(), postVO.getPg(), postVO.getCntPerPage(), "viewListPageMove"));

		// 3. 게시판 정보
		model.addAttribute(boardVO);

		return "/boards/post_list";
	}

	/**
	 * 게시글 목록 조회 (Ajax)
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param boardVO
	 *            BoardVO
	 * @param postVO
	 *            PostVO
	 * @return 게시글 목록
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{boardName}/ajax", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject postListAjax(@PathVariable("boardName") String boardName, BoardVO boardVO, PostVO postVO) {

		JSONObject jsonObj = new JSONObject();

		// 0. 조건 세팅
		if (boardName != null && !"_ALL_".equals(boardName)) {
			boardVO = this.getBoardInfo(boardName);
			if (boardVO == null) {
				return null;
			}
			postVO.setMode("VIEW");
			postVO.setBoardIdx(boardVO.getIdx());
			postVO.setTotalCnt(boardVO.getPostCnt());
		} else {
			boardVO.setMode("VIEW");
			postVO.setMode("VIEW");
			postVO.setTotalCnt(boardService.selectBoardAllPostTotalCnt(boardVO));
		}

		// 1. 목록
		jsonObj.put("postVOList", postService.selectPostList(postVO));

		// 2. 페이징
		jsonObj.put("listPaging", BoardUtil.getPaging(postVO.getTotalCnt(), postVO.getPg(), postVO.getCntPerPage(), "loadPostList"));

		return jsonObj;
	}

	/**
	 * 페이지 번호로 게시글 일련번호 조회 (Ajax)
	 * 
	 * @param boardName
	 *            게시판 이름
	 * @param boardVO
	 *            BoardVO
	 * @param postVO
	 *            PostVO
	 * @return 게시글 일련번호
	 */
	@RequestMapping(value = "/{boardName}/find/idx", method = RequestMethod.GET)
	@ResponseBody
	public int postIdxForPgAjax(@PathVariable("boardName") String boardName, BoardVO boardVO, PostVO postVO) {

		// 0. 조건 세팅
		if (boardName != null && !"_ALL_".equals(boardName)) {
			boardVO = this.getBoardInfo(boardName);
			if (boardVO == null) {
				return 0;
			}
			postVO.setMode("VIEW");
			postVO.setBoardIdx(boardVO.getIdx());
		} else {
			return 0;
		}

		return postService.selectPostIdxForPg(postVO);
	}

	/**
	 * 게시글 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param boardName
	 *            게시판 이름
	 * @param idx
	 *            게시글 일련번호
	 * @param boardVO
	 *            BoardVO
	 * @param postVO
	 *            PostVO
	 * @return "/boards/board_view"
	 */
	@RequestMapping(value = "/{boardName}/{idx}", method = RequestMethod.GET)
	public String postView(ModelMap model, @PathVariable("boardName") String boardName, @PathVariable("idx") int idx, BoardVO boardVO,
			PostVO postVO) {

		// 0. 조건 세팅
		boardVO = this.getBoardInfo(boardName);
		if (boardVO == null) {
			return "/common/404";
		}
		postVO.setMode("VIEW");
		postVO.setBoardIdx(boardVO.getIdx());
		postVO.setCntPerPage(1);
		postVO.setTotalCnt(boardVO.getPostCnt());

		// 1. 조회
		PostVO resultVO = postService.selectPost(postVO);
		if (resultVO == null) {
			return "/common/404";
		}
		postVO.setPg(resultVO.getPg());
		model.addAttribute(resultVO);

		// 2. 페이징
		model.addAttribute("viewPaging", BoardUtil.getPaging(postVO.getTotalCnt(), postVO.getPg(), postVO.getCntPerPage(), "viewPageMove"));

		// 3. 게시판 정보
		model.addAttribute(boardVO);

		return "/boards/post_view";
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
		boardVO.setMode("VIEW");
		return boardService.selectBoard(boardVO);
	}

}
