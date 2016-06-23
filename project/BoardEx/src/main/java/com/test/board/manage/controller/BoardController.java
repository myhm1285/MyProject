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

import com.test.board.manage.service.BoardService;
import com.test.board.manage.vo.BoardVO;
import com.test.board.util.BoardUtil;
import com.test.board.util.PropertyUtil;

/**
 * 게시판 Controller
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/setting")
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
	 *            조회할 정보가 담긴 boardVO
	 * @return "/board/board_list"
	 */
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String boardList(ModelMap model, @ModelAttribute("searchVO") BoardVO boardVO) {

		// 0. 조건 세팅
		boardVO.setCntPerPage(PropertyUtil.getPropertyInt("board", "board.board.boardList.cntPerPage", 10));

		// 1. 총 게시물 수
		boardVO.setTotalCnt(boardService.selectBoardListTotalCnt(boardVO));

		// 2. 목록
		model.addAttribute(boardService.selectBoardList(boardVO));

		// 3. 페이징
		model.addAttribute("paging", BoardUtil.getPaging(boardVO.getTotalCnt(), boardVO.getPg(), boardVO.getCntPerPage()));

		return "/board/board_list";
	}

	/**
	 * 게시판 등록 화면 조회
	 * 
	 * @param model
	 *            ModelMap
	 * @param boardVO
	 *            조회할 정보가 담긴 boardVO
	 * @return "/board/board_write"
	 */
	@RequestMapping(value = "/board/new", method = RequestMethod.GET)
	public String boardWrite(ModelMap model, @ModelAttribute("searchVO") BoardVO boardVO) {

		return "/board/board_write";
	}

	/**
	 * 게시판 수정 화면 조회
	 * 
	 * @param idx
	 *            게시판 일련번호
	 * @param model
	 *            ModelMap
	 * @param boardVO
	 *            조회할 정보가 담긴 boardVO
	 * 
	 * @return "/board/board_write"
	 */
	@RequestMapping(value = "/board/{idx}/edit", method = RequestMethod.GET)
	public String boardModify(@PathVariable("idx") int idx, ModelMap model, @ModelAttribute("searchVO") BoardVO boardVO) {

		// 조회
		BoardVO resultVO = boardService.selectBoard(boardVO);
		if (resultVO == null) {
			return "redirect:/board";
		}
		model.addAttribute(resultVO);

		return "/board/board_write";
	}

	/**
	 * 게시판 등록
	 * 
	 * @param model
	 *            ModelMap
	 * @param boardVO
	 *            조회할 정보가 담긴 boardVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/board", method = RequestMethod.PUT)
	@ResponseBody
	public String boardWriteProc(@PathVariable("idx") int idx, ModelMap model, @ModelAttribute("searchVO") BoardVO boardVO) {

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
	 *            수정할 정보가 담긴 boardVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/board/{idx}", method = RequestMethod.POST)
	@ResponseBody
	public String boardModifyProc(@PathVariable("idx") int idx, ModelMap model, @ModelAttribute("searchVO") BoardVO boardVO) {

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
	 *            삭제할 정보가 담긴 boardVO
	 * @return 성공이면 Y, 실패이면 N
	 */
	@RequestMapping(value = "/board/{idx}", method = RequestMethod.DELETE)
	@ResponseBody
	public String boardDeleteProc(@PathVariable("idx") int idx, ModelMap model, @ModelAttribute("searchVO") BoardVO boardVO) {

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
