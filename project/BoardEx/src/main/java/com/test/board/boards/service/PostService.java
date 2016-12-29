package com.test.board.boards.service;

import java.util.List;

import com.test.board.boards.vo.PostVO;

/**
 * 게시글 Service
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
public interface PostService {

	/**
	 * 게시글 목록 조회
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return List<PostVO>
	 */
	public List<PostVO> selectPostList(PostVO postVO);

	/**
	 * 게시글 목록 조회 (내용 포함)
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return List<PostVO>
	 */
	public List<PostVO> selectPostViewList(PostVO postVO);

	/**
	 * 게시글 조회
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return PostVO
	 */
	public PostVO selectPost(PostVO postVO);

	/**
	 * 페이지 번호로 게시글 일련번호 조회
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return 게시글 일련번호
	 */
	public int selectPostIdxForPg(PostVO postVO);

	/**
	 * 게시글 등록
	 * 
	 * @param postVO
	 *            등록할 정보가 담긴 PostVO
	 */
	public void insertPost(PostVO postVO);

	/**
	 * 게시글 수정
	 * 
	 * @param postVO
	 *            수정할 정보가 담긴 PostVO
	 */
	public void updatePost(PostVO postVO);

	/**
	 * 게시글 삭제
	 * 
	 * @param postVO
	 *            삭제할 정보가 담긴 PostVO
	 */
	public void deletePost(PostVO postVO);

}
