package com.test.board.manage.vo;

import com.test.board.common.vo.BaseVO;

/**
 * 게시판 VO
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
public class BoardVO extends BaseVO {

	/** 일련번호 */
	private int idx;

	/** 이름 */
	private String name;

	/** 순서 */
	private int order;

	/** 게시물 개수 */
	private int postCnt;

	/** 권한 */
	private String right;

	/** 공개여부 */
	private String isOpen;

	/** 삭제여부 */
	private String isDel;

	/** 등록일시 */
	private String writeDt;

	/** 수정일시 */
	private String modifyDt;

	/** 메모 */
	private String note;

	/**
	 * @return the idx
	 */
	public int getIdx() {
		return idx;
	}

	/**
	 * @param idx
	 *            the idx to set
	 */
	public void setIdx(int idx) {
		this.idx = idx;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return the postCnt
	 */
	public int getPostCnt() {
		return postCnt;
	}

	/**
	 * @param postCnt
	 *            the postCnt to set
	 */
	public void setPostCnt(int postCnt) {
		this.postCnt = postCnt;
	}

	/**
	 * @return the right
	 */
	public String getRight() {
		return right;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	public void setRight(String right) {
		this.right = right;
	}

	/**
	 * @return the isOpen
	 */
	public String getIsOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen
	 *            the isOpen to set
	 */
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * @return the isDel
	 */
	public String getIsDel() {
		return isDel;
	}

	/**
	 * @param isDel
	 *            the isDel to set
	 */
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	/**
	 * @return the writeDt
	 */
	public String getWriteDt() {
		return writeDt;
	}

	/**
	 * @param writeDt
	 *            the writeDt to set
	 */
	public void setWriteDt(String writeDt) {
		this.writeDt = writeDt;
	}

	/**
	 * @return the modifyDt
	 */
	public String getModifyDt() {
		return modifyDt;
	}

	/**
	 * @param modifyDt
	 *            the modifyDt to set
	 */
	public void setModifyDt(String modifyDt) {
		this.modifyDt = modifyDt;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

}
