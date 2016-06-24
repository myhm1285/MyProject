package com.test.board.manage.vo;

import com.test.board.common.vo.BaseVO;

/**
 * 계정 검색 VO
 *
 * @author hmyoo
 * @since 2016. 6. 24.
 * @version 1.0
 */
public class AccountSearchVO extends BaseVO {

	/** 로그인ID (검색) */
	private String searchAccId;

	/** SNS 타입 (검색) */
	private String searchSnsType;

	/** 이름 (검색) */
	private String searchAccName;

	/**
	 * @return the searchAccId
	 */
	public String getSearchAccId() {
		return searchAccId;
	}

	/**
	 * @param searchAccId
	 *            the searchAccId to set
	 */
	public void setSearchAccId(String searchAccId) {
		this.searchAccId = searchAccId;
	}

	/**
	 * @return the searchSnsType
	 */
	public String getSearchSnsType() {
		return searchSnsType;
	}

	/**
	 * @param searchSnsType
	 *            the searchSnsType to set
	 */
	public void setSearchSnsType(String searchSnsType) {
		this.searchSnsType = searchSnsType;
	}

	/**
	 * @return the searchAccName
	 */
	public String getSearchAccName() {
		return searchAccName;
	}

	/**
	 * @param searchAccName
	 *            the searchAccName to set
	 */
	public void setSearchAccName(String searchAccName) {
		this.searchAccName = searchAccName;
	}

}
