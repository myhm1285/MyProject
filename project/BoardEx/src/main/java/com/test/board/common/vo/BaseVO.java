package com.test.board.common.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 기본 VO
 *
 * @author hmyoo
 * @since 2016. 4. 25.
 * @version 1.0
 */
public class BaseVO {

	/** 메뉴 On/Off */
	private boolean menuOn;

	/** 총 게시물 수 */
	private int totalCnt;

	/** 현재 페이지 번호 */
	private int pg;

	/** 게시물 시작 번호 */
	private int listNo;

	/** 시작 rownum (SQL문에서 사용) */
	@SuppressWarnings("unused")
	private int startRownum;

	/** 한 페이지당 게시물 수 */
	private int cntPerPage;

	/** 총 페이지 수 */
	@SuppressWarnings("unused")
	private int totalPg;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * @return the menuOn
	 */
	public boolean isMenuOn() {
		return menuOn;
	}

	/**
	 * @param menuOn
	 *            the menuOn to set
	 */
	public void setMenuOn(boolean menuOn) {
		this.menuOn = menuOn;
	}

	/**
	 * @return the totalCnt
	 */
	public int getTotalCnt() {
		return totalCnt;
	}

	/**
	 * @param totalCnt
	 *            the totalCnt to set
	 */
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	/**
	 * @return the pg
	 */
	public int getPg() {
		if (pg <= 0) {
			pg = 1;
		}
		return pg;
	}

	/**
	 * @param pg
	 *            the pg to set
	 */
	public void setPg(int pg) {
		this.pg = pg;
	}

	/**
	 * @return the listNo
	 */
	public int getListNo() {
		return listNo;
	}

	/**
	 * @param listNo
	 *            the listNo to set
	 */
	public void setListNo(int listNo) {
		this.listNo = listNo;
	}

	/**
	 * @return the startRownum
	 */
	public int getStartRownum() {
		return ((getPg() - 1) * getCntPerPage());
	}

	/**
	 * @param startRownum
	 *            the startRownum to set
	 */
	public void setStartRownum(int startRownum) {
		this.startRownum = startRownum;
	}

	/**
	 * @return the cntPerPage
	 */
	public int getCntPerPage() {
		if (cntPerPage <= 0) {
			cntPerPage = 5;
		}
		return cntPerPage;
	}

	/**
	 * @param cntPerPage
	 *            the cntPerPage to set
	 */
	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}

	/**
	 * @return the totalPg
	 */
	public int getTotalPg() {
		return ((getTotalCnt() - 1) / getCntPerPage()) + 1;
	}

	/**
	 * @param totalPg
	 *            the totalPg to set
	 */
	public void setTotalPg(int totalPg) {
		this.totalPg = totalPg;
	}

}
