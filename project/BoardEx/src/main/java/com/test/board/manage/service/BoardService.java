package com.test.board.manage.service;

import java.util.List;

import com.test.board.manage.vo.BoardVO;

/**
 * 게시판 Service
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
public interface BoardService {

	/**
	 * 게시판 목록 조회
	 * 
	 * @param boardVO
	 *            조회할 정보가 담긴 BoardVO
	 * @return List<BoardVO>
	 * @throws Exception
	 */
	public List<BoardVO> selectBoardList(BoardVO boardVO) throws Exception;

}
