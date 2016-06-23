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
	 */
	public List<BoardVO> selectBoardList(BoardVO boardVO);

	/**
	 * 게시판 목록 총 개수 조회
	 * 
	 * @param boardVO
	 *            조회할 정보가 담긴 BoardVO
	 * @return 목록 총 개수
	 */
	public int selectBoardListTotalCnt(BoardVO boardVO);

	/**
	 * 게시판 조회
	 * 
	 * @param boardVO
	 *            조회할 정보가 담긴 BoardVO
	 * @return BoardVO
	 */
	public BoardVO selectBoard(BoardVO boardVO);

	/**
	 * 게시판 등록
	 * 
	 * @param boardVO
	 *            등록할 정보가 담긴 BoardVO
	 */
	public void insertBoard(BoardVO boardVO);

	/**
	 * 게시판 수정
	 * 
	 * @param boardVO
	 *            수정할 정보가 담긴 BoardVO
	 */
	public void updateBoard(BoardVO boardVO);

	/**
	 * 게시판 삭제
	 * 
	 * @param boardVO
	 *            삭제할 정보가 담긴 BoardVO
	 */
	public void deleteBoard(BoardVO boardVO);

}
