package com.test.board.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.test.board.manage.service.BoardService;
import com.test.board.manage.vo.BoardVO;
import com.test.board.util.BoardUtil;

/**
 * 게시판 Service 구현
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Service("boardService")
public class BoardServiceImpl implements BoardService {

	/**
	 * 게시판 Mapper
	 */
	@Resource(name = "boardMapper")
	private BoardMapper boardDAO;

	/**
	 * 게시판 목록 조회
	 * 
	 * @param boardVO
	 *            조회할 정보가 담긴 BoardVO
	 * @return List<BoardVO>
	 * @throws Exception
	 */
	@Override
	public List<BoardVO> selectBoardList(BoardVO boardVO) throws Exception {

		// 게시물 시작 번호
		int listNo = BoardUtil.getListNo(boardVO.getTotalCnt(), boardVO.getPg(), boardVO.getCntPerPage());

		// 목록
		List<BoardVO> resultVOList = boardDAO.selectBoardList(boardVO);

		for (BoardVO resultVO : resultVOList) {
			// 게시물 시작 번호
			resultVO.setListNo(listNo--);
		}

		return resultVOList;
	}

	/**
	 * 게시판 조회
	 * 
	 * @param boardVO
	 *            조회할 정보가 담긴 BoardVO
	 * @return BoardVO
	 * @throws Exception
	 */
	@Override
	public BoardVO selectBoard(BoardVO boardVO) throws Exception {
		return boardDAO.selectBoard(boardVO);
	}

}
