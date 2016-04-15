package com.itw.common.download.service;

import com.itw.common.BaseVO;

/**
 * 다운로드 VO 클래스
 *
 * @author 컨버전스스퀘어
 * @since 2015. 9. 22.
 * @version 1.0
 */
public class DownloadVO extends BaseVO {

	/**
	 * 구분
	 * 
	 * @see DownloadConstants
	 */
	private String gubun;

	/** 업로드된 파일명 */
	private String ufn;

	/** 다운받을 파일명 */
	private String dfn;

	/**
	 * 썸네일
	 * 
	 * @see DownloadConstants
	 */
	private String thumb;

	/**
	 * @return the gubun
	 */
	public String getGubun() {
		return gubun;
	}

	/**
	 * @param gubun
	 *            the gubun to set
	 */
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	/**
	 * @return the ufn
	 */
	public String getUfn() {
		return ufn;
	}

	/**
	 * @param ufn
	 *            the ufn to set
	 */
	public void setUfn(String ufn) {
		this.ufn = ufn;
	}

	/**
	 * @return the dfn
	 */
	public String getDfn() {
		return dfn;
	}

	/**
	 * @param dfn
	 *            the dfn to set
	 */
	public void setDfn(String dfn) {
		this.dfn = dfn;
	}

	/**
	 * @return the thumb
	 */
	public String getThumb() {
		return thumb;
	}

	/**
	 * @param thumb
	 *            the thumb to set
	 */
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

}
