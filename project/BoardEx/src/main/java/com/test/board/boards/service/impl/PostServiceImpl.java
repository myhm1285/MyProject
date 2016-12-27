package com.test.board.boards.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.test.board.boards.service.PostService;
import com.test.board.boards.vo.CommentVO;
import com.test.board.boards.vo.PostVO;
import com.test.board.util.BoardUtil;

/**
 * 게시글 Service 구현
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Service("postService")
public class PostServiceImpl implements PostService {

	/**
	 * 게시글 Mapper
	 */
	@Resource(name = "postMapper")
	private PostMapper postDAO;

	/**
	 * 댓글 Mapper
	 */
	@Resource(name = "commentMapper")
	private CommentMapper commentDAO;

	/**
	 * 게시글 목록 조회
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return List<PostVO>
	 */
	@Override
	public List<PostVO> selectPostList(PostVO postVO) {

		// 게시물 시작 번호
		int listNo = BoardUtil.getListNo(postVO.getTotalCnt(), postVO.getPg(), postVO.getCntPerPage());

		// 목록
		List<PostVO> resultVOList = postDAO.selectPostList(postVO);

		for (PostVO resultVO : resultVOList) {
			// 게시물 시작 번호
			resultVO.setListNo(listNo--);
		}

		return resultVOList;
	}

	/**
	 * 게시글 목록 조회 (내용 포함)
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return List<PostVO>
	 */
	@Override
	public List<PostVO> selectPostViewList(PostVO postVO) {
		List<PostVO> resultPostVOList = postDAO.selectPostViewList(postVO);

		// 댓글 목록 조회
		CommentVO commentVO = null;
		for (PostVO resultPostVO : resultPostVOList) {
			commentVO = new CommentVO();
			commentVO.setPostIdx(resultPostVO.getIdx());

			resultPostVO.setCommentVOList(commentDAO.selectCommentList(commentVO));
		}
		return resultPostVOList;
	}

	/**
	 * 게시글 목록 총 개수 조회
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return 목록 총 개수
	 */
	@Override
	public int selectPostListTotalCnt(PostVO postVO) {
		return postDAO.selectPostListTotalCnt(postVO);
	}

	/**
	 * 게시글 조회
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return PostVO
	 */
	@Override
	public PostVO selectPost(PostVO postVO) {
		return postDAO.selectPost(postVO);
	}

	/**
	 * 게시글 등록
	 * 
	 * @param postVO
	 *            등록할 정보가 담긴 PostVO
	 */
	@Override
	public void insertPost(PostVO postVO) {
		postDAO.insertPost(postVO);
	}

	/**
	 * 게시글 수정
	 * 
	 * @param postVO
	 *            수정할 정보가 담긴 PostVO
	 */
	@Override
	public void updatePost(PostVO postVO) {
		postDAO.updatePost(postVO);
	}

	/**
	 * 게시글 삭제
	 * 
	 * @param postVO
	 *            삭제할 정보가 담긴 PostVO
	 */
	@Override
	public void deletePost(PostVO postVO) {
		postDAO.deletePost(postVO);
	}
}
