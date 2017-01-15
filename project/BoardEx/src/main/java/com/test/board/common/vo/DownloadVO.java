package com.test.board.common.vo;

/**
 * 다운로드 VO 클래스
 *
 * @author hmyoo
 * @since 2015. 9. 22.
 * @version 1.0
 */
public class DownloadVO extends BaseVO {

	/** 위치 */
	private String path;

	/** 업로드된 파일명 */
	private String ufn;

	/** 다운받을 파일명 */
	private String dfn;

	/**
	 * 썸네일 사이즈<br/>
	 * 예를 들어 300필셀 x 200픽셀이면 300x200 으로 값을 넘김
	 */
	private String thumb;

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
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
