package com.test.board.boards.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.test.board.boards.vo.PostVO;

/**
 * 게시글 Mapper
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Repository("postMapper")
public interface PostMapper {

	/**
	 * 게시글 목록 조회
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return List<PostVO>
	 * @throws Exception
	 */
	public List<PostVO> selectPostList(PostVO postVO) throws Exception;

	/**
	 * 게시글 목록 총 개수 조회
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return 목록 총 개수
	 * @throws Exception
	 */
	public int selectPostListTotalCnt(PostVO postVO) throws Exception;

	/**
	 * 게시글 조회
	 * 
	 * @param postVO
	 *            조회할 정보가 담긴 PostVO
	 * @return PostVO
	 * @throws Exception
	 */
	public PostVO selectPost(PostVO postVO) throws Exception;

	/**
	 * 게시글 등록
	 * 
	 * @param postVO
	 *            등록할 정보가 담긴 PostVO
	 * @throws Exception
	 */
	public void insertPost(PostVO postVO) throws Exception;

	/**
	 * 게시글 수정
	 * 
	 * @param postVO
	 *            수정할 정보가 담긴 PostVO
	 * @throws Exception
	 */
	public void updatePost(PostVO postVO) throws Exception;

	/**
	 * 게시글 삭제
	 * 
	 * @param postVO
	 *            삭제할 정보가 담긴 PostVO
	 * @throws Exception
	 */
	public void deletePost(PostVO postVO) throws Exception;

}
