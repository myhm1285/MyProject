package com.test.board.boards.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.test.board.boards.service.CommentService;
import com.test.board.boards.vo.CommentVO;
import com.test.board.util.BoardUtil;

/**
 * 댓글 Service 구현
 *
 * @author hmyoo
 * @since 2016. 6. 20.
 * @version 1.0
 */
@Service("commentService")
public class CommentServiceImpl implements CommentService {

	/**
	 * 댓글 Mapper
	 */
	@Resource(name = "commentMapper")
	private CommentMapper commentDAO;

	/**
	 * 댓글 목록 조회
	 * 
	 * @param commentVO
	 *            조회할 정보가 담긴 CommentVO
	 * @return List<CommentVO>
	 * @throws Exception
	 */
	@Override
	public List<CommentVO> selectCommentList(CommentVO commentVO) throws Exception {

		// 게시물 시작 번호
		int listNo = BoardUtil.getListNo(commentVO.getTotalCnt(), commentVO.getPg(), commentVO.getCntPerPage());

		// 목록
		List<CommentVO> resultVOList = commentDAO.selectCommentList(commentVO);

		for (CommentVO resultVO : resultVOList) {
			// 게시물 시작 번호
			resultVO.setListNo(listNo--);
		}

		return resultVOList;
	}

	/**
	 * 댓글 목록 총 개수 조회
	 * 
	 * @param commentVO
	 *            조회할 정보가 담긴 CommentVO
	 * @return 목록 총 개수
	 * @throws Exception
	 */
	@Override
	public int selectCommentListTotalCnt(CommentVO commentVO) throws Exception {
		return commentDAO.selectCommentListTotalCnt(commentVO);
	}

	/**
	 * 댓글 조회
	 * 
	 * @param commentVO
	 *            조회할 정보가 담긴 CommentVO
	 * @return CommentVO
	 * @throws Exception
	 */
	@Override
	public CommentVO selectComment(CommentVO commentVO) throws Exception {
		return commentDAO.selectComment(commentVO);
	}

	/**
	 * 댓글 등록
	 * 
	 * @param commentVO
	 *            등록할 정보가 담긴 CommentVO
	 * @throws Exception
	 */
	@Override
	public void insertComment(CommentVO commentVO) throws Exception {
		commentDAO.insertComment(commentVO);
	}

	/**
	 * 댓글 삭제
	 * 
	 * @param commentVO
	 *            삭제할 정보가 담긴 CommentVO
	 * @throws Exception
	 */
	@Override
	public void deleteComment(CommentVO commentVO) throws Exception {
		commentDAO.deleteComment(commentVO);
	}
}
