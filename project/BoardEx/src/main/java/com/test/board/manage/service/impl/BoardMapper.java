package com.test.board.manage.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.test.board.manage.vo.BoardVO;

/**
 * 게시판 Mapper
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Repository("boardMapper")
public interface BoardMapper {

	/**
	 * 게시판 목록 조회
	 * 
	 * @param boardVO
	 *            조회할 정보가 담긴 BoardVO
	 * @return List<BoardVO>
	 * @throws Exception
	 */
	public List<BoardVO> selectBoardList(BoardVO boardVO) throws Exception;

	/**
	 * 게시판 목록 총 개수 조회
	 * 
	 * @param boardVO
	 *            조회할 정보가 담긴 BoardVO
	 * @return 목록 총 개수
	 * @throws Exception
	 */
	public int selectBoardListTotalCnt(BoardVO boardVO) throws Exception;

	/**
	 * 게시판 등록
	 * 
	 * @param boardVO
	 *            등록할 정보가 담긴 BoardVO
	 * @throws Exception
	 */
	public void insertBoard(BoardVO boardVO) throws Exception;

	/**
	 * 게시판 수정
	 * 
	 * @param boardVO
	 *            수정할 정보가 담긴 BoardVO
	 * @throws Exception
	 */
	public void updateBoard(BoardVO boardVO) throws Exception;

	/**
	 * 게시판 삭제
	 * 
	 * @param boardVO
	 *            삭제할 정보가 담긴 BoardVO
	 * @throws Exception
	 */
	public void deleteBoard(BoardVO boardVO) throws Exception;

}
