package com.test.board.boards.vo;

import com.test.board.common.vo.BaseVO;

/**
 * 게시글 VO
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
public class PostSearchVO extends BaseVO {

	/** 제목 (검색) */
	private String searchTitle;

	/** 글쓴이 이름 (검색) */
	private String searchWriterName;

	/**
	 * @return the searchTitle
	 */
	public String getSearchTitle() {
		return searchTitle;
	}

	/**
	 * @param searchTitle
	 *            the searchTitle to set
	 */
	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}

	/**
	 * @return the searchWriterName
	 */
	public String getSearchWriterName() {
		return searchWriterName;
	}

	/**
	 * @param searchWriterName
	 *            the searchWriterName to set
	 */
	public void setSearchWriterName(String searchWriterName) {
		this.searchWriterName = searchWriterName;
	}

}
