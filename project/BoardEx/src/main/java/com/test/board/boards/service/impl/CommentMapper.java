package com.test.board.boards.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.test.board.boards.vo.CommentVO;

/**
 * 댓글 Mapper
 *
 * @author hmyoo
 * @since 2016. 6. 20.
 * @version 1.0
 */
@Repository("commentMappper")
public interface CommentMapper {

	/**
	 * 댓글 목록 조회
	 * 
	 * @param commentVO
	 *            조회할 정보가 담긴 CommentVO
	 * @return List<CommentVO>
	 * @throws Exception
	 */
	public List<CommentVO> selectCommentList(CommentVO commentVO) throws Exception;

	/**
	 * 댓글 목록 총 개수 조회
	 * 
	 * @param commentVO
	 *            조회할 정보가 담긴 CommentVO
	 * @return 목록 총 개수
	 * @throws Exception
	 */
	public int selectCommentListTotalCnt(CommentVO commentVO) throws Exception;

	/**
	 * 댓글 조회
	 * 
	 * @param commentVO
	 *            조회할 정보가 담긴 CommentVO
	 * @return CommentVO
	 * @throws Exception
	 */
	public CommentVO selectComment(CommentVO commentVO) throws Exception;

	/**
	 * 댓글 등록
	 * 
	 * @param commentVO
	 *            등록할 정보가 담긴 CommentVO
	 * @throws Exception
	 */
	public void insertComment(CommentVO commentVO) throws Exception;

	/**
	 * 댓글 삭제
	 * 
	 * @param commentVO
	 *            삭제할 정보가 담긴 CommentVO
	 * @throws Exception
	 */
	public void deleteComment(CommentVO commentVO) throws Exception;

}
