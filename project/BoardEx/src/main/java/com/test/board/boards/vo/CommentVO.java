package com.test.board.boards.vo;

import com.test.board.common.vo.BaseVO;

/**
 * 댓글 VO
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
public class CommentVO extends BaseVO {

	/** 일련번호 */
	private int idx;

	/** 게시물 일련번호 */
	private int postIdx;

	/** 내용 */
	private String content;

	/** 글쓴이IDX */
	private String writerIdx;

	/** 글쓴이 이름 */
	private String writerName;

	/** 삭제여부 */
	private String isDel;

	/** 등록일시 */
	private String writeDt;

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
	 * @return the postIdx
	 */
	public int getPostIdx() {
		return postIdx;
	}

	/**
	 * @param postIdx
	 *            the postIdx to set
	 */
	public void setPostIdx(int postIdx) {
		this.postIdx = postIdx;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the writerIdx
	 */
	public String getWriterIdx() {
		return writerIdx;
	}

	/**
	 * @param writerIdx
	 *            the writerIdx to set
	 */
	public void setWriterIdx(String writerIdx) {
		this.writerIdx = writerIdx;
	}

	/**
	 * @return the writerName
	 */
	public String getWriterName() {
		return writerName;
	}

	/**
	 * @param writerName
	 *            the writerName to set
	 */
	public void setWriterName(String writerName) {
		this.writerName = writerName;
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

}
