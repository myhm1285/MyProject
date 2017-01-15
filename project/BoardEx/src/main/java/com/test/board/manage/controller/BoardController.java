package com.test.board.manage.controller;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.board.boards.service.AdminMenu;
import com.test.board.manage.service.BoardService;
import com.test.board.manage.vo.BoardVO;

/**
 * 게시판 Controller
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@AdminMenu
@Controller
@RequestMapping(value = "/manage/board")
public class BoardController {

	/** LOGGER */
	private final static Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

	/** 게시판 Service */
	@Resource(name = "boardService")
	private BoardService boardService;

	/**
	 * 게시판 목록 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param boardVO
	 *            BoardVO
	 * @return "/manage/board_list"
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String boardList(ModelMap model, BoardVO boardVO) {

		return "/manage/board_list";
	}

	/**
	 * 게시판 목록 조회 (Ajax)
	 * 
	 * @param boardVO
	 *            BoardVO
	 * @return jsonObj
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ajax", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject boardListAjax(BoardVO boardVO) {

		JSONObject jsonObj = new JSONObject();

		// 1. 총 게시판 수
		jsonObj.put("boardListTotalCnt", boardService.selectBoardListTotalCnt(boardVO));

		// 2. 목록
		jsonObj.put("boardVOList", boardService.selectBoardList(boardVO));

		return jsonObj;
	}

	/**
	 * 게시판 상세 조회
	 * 
	 * @param idx
	 *            게시판 일련번호
	 * @param boardVO
	 *            BoardVO
	 * @return jsonObj
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/{idx}", method = RequestMethod.GET)
	public JSONObject boardView(@PathVariable("idx") int idx, BoardVO boardVO) {

		JSONObject jsonObj = new JSONObject();

		// 0. 조건 세팅
		boardVO.setIdx(idx);

		// 1. 게시판 상세 조회
		jsonObj.put("boardVO", boardService.selectBoard(boardVO));

		return jsonObj;
	}

	/**
	 * 게시판 이름 중복 조회
	 * 
	 * @param idx
	 *            게시판 일련번호
	 * @param boardVO
	 *            BoardVO
	 * @return 중복이면 Y, 아니면 N
	 */
	@ResponseBody
	@RequestMapping(value = "/find/{name}", method = RequestMethod.GET)
	public String boardModify(@PathVariable("name") String name, BoardVO boardVO) {

		// 0. 조건 세팅
		if (!"_ALL_".equals(name)) {
			boardVO.setName(name);
			BoardVO reseultVO = boardService.selectBoard(boardVO);

			// 1. 이름으로 게시판 조회
			if (reseultVO == null) {
				return "N";
			}
		}
		return "Y";
	}

	/**
	 * 게시판 등록
	 * 
	 * @param model
	 *            ModelMap
	 * @param boardVO
	 *            BoardVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public String boardWriteProc(ModelMap model, @RequestBody BoardVO boardVO) {

		try {
			// 1. 등록
			boardService.insertBoard(boardVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

	/**
	 * 게시판 수정
	 * 
	 * @param idx
	 *            게시판 일련번호
	 * @param model
	 *            ModelMap
	 * @param boardVO
	 *            BoardVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/{idx}", method = RequestMethod.PUT)
	@ResponseBody
	public String boardModifyProc(@PathVariable("idx") int idx, ModelMap model, @RequestBody BoardVO boardVO) {

		try {
			// 0. 조건 세팅
			boardVO.setIdx(idx);

			// 1. 수정
			boardService.updateBoard(boardVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

	/**
	 * 게시판 삭제
	 * 
	 * @param idx
	 *            게시판 일련번호
	 * @param model
	 *            ModelMap
	 * @param boardVO
	 *            BoardVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/{idx}", method = RequestMethod.DELETE)
	@ResponseBody
	public String boardDeleteProc(@PathVariable("idx") int idx, ModelMap model, BoardVO boardVO) {

		try {
			// 0. 조건 세팅
			boardVO.setIdx(idx);

			// 1. 삭제
			boardService.deleteBoard(boardVO);

		} catch (Exception e) {
			LOGGER.debug("Exception : {}", e.getLocalizedMessage());
			return "N";
		}

		return "Y";
	}

}
