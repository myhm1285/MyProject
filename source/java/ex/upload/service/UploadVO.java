package com.itw.mng.common.upload.service;

import com.itw.common.BaseVO;

/**
 * 업로드 VO 클래스
 *
 * @author 컨버전스스퀘어
 * @since 2015. 9. 6.
 * @version 1.0
 */
public class UploadVO extends BaseVO {

	/**
	 * 구분
	 * 
	 * @see UploadConstants
	 */
	private String gubun;

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
}
