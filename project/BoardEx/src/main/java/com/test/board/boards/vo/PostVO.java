package com.test.board.boards.vo;

import com.test.board.common.vo.BaseVO;

/**
 * 게시글 VO
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
public class PostVO extends BaseVO {

	/** 일련번호 */
	private int idx;

	/** 게시판 일련번호 */
	private int boardIdx;

	/** 게시판 이름 */
	private String boardName;

	/** 제목 */
	private String title;

	/** 내용 */
	private String content;

	/** 글쓴이ID */
	private String writerId;

	/** 글쓴이 이름 */
	private String writerName;

	/** 댓글 개수 */
	private int commentCnt;

	/** 삭제여부 */
	private String isDel;

	/** 등록일시 */
	private String writeDt;

	/** 수정일시 */
	private String modifydt;

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
	 * @return the boardIdx
	 */
	public int getBoardIdx() {
		return boardIdx;
	}

	/**
	 * @param boardIdx
	 *            the boardIdx to set
	 */
	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	/**
	 * @return the boardName
	 */
	public String getBoardName() {
		return boardName;
	}

	/**
	 * @param boardName
	 *            the boardName to set
	 */
	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the writerId
	 */
	public String getWriterId() {
		return writerId;
	}

	/**
	 * @param writerId
	 *            the writerId to set
	 */
	public void setWriterId(String writerId) {
		this.writerId = writerId;
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
	 * @return the commentCnt
	 */
	public int getCommentCnt() {
		return commentCnt;
	}

	/**
	 * @param commentCnt
	 *            the commentCnt to set
	 */
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
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
	 * @return the modifydt
	 */
	public String getModifydt() {
		return modifydt;
	}

	/**
	 * @param modifydt
	 *            the modifydt to set
	 */
	public void setModifydt(String modifydt) {
		this.modifydt = modifydt;
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
