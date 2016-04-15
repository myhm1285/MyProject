package com.itw.test.service;

/**
 * @Class Name : TestVO.java
 * @Description : 로그인 VO 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class TestVO {
	private int rawid;
	private String create_dt;
	private String test_col;

	public int getRawid() {
		return rawid;
	}

	public void setRawid(int rawid) {
		this.rawid = rawid;
	}

	public String getCreate_dt() {
		return create_dt;
	}

	public void setCreate_dt(String create_dt) {
		this.create_dt = create_dt;
	}

	public String getTest_col() {
		return test_col;
	}

	public void setTest_col(String test_col) {
		this.test_col = test_col;
	}
}
