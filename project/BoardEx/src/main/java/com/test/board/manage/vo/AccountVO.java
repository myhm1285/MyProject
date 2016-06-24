package com.test.board.manage.vo;

import java.io.Serializable;

/**
 * 계정 VO
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
public class AccountVO extends AccountSearchVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8297024518079959934L;

	/** 일련번호 */
	private int idx;

	/** 로그인ID */
	private String accId;

	/** SNS 타입 */
	private String snsType;

	/** 비밀번호 */
	private String accPwd;

	/** 이름 */
	private String accName;

	/** 이메일 */
	private String email;

	/** 전화번호 */
	private String phoneNum;

	/** 사용자이미지 */
	private String userImg;

	/** 권한 */
	private String right;

	/** 삭제여부 */
	private String isDel;

	/** 가입일시 */
	private String joinDt;

	/** 최근로그인일시 */
	private String lastLoginDt;

	/** 최근로그인IP */
	private String lastLoginIp;

	/** 메모 */
	private String note;

	/** 수정일시 */
	private String modifyDt;

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
	 * @return the accId
	 */
	public String getAccId() {
		return accId;
	}

	/**
	 * @param accId
	 *            the accId to set
	 */
	public void setAccId(String accId) {
		this.accId = accId;
	}

	/**
	 * @return the snsType
	 */
	public String getSnsType() {
		return snsType;
	}

	/**
	 * @param snsType
	 *            the snsType to set
	 */
	public void setSnsType(String snsType) {
		this.snsType = snsType;
	}

	/**
	 * @return the accPwd
	 */
	public String getAccPwd() {
		return accPwd;
	}

	/**
	 * @param accPwd
	 *            the accPwd to set
	 */
	public void setAccPwd(String accPwd) {
		this.accPwd = accPwd;
	}

	/**
	 * @return the accName
	 */
	public String getAccName() {
		return accName;
	}

	/**
	 * @param accName
	 *            the accName to set
	 */
	public void setAccName(String accName) {
		this.accName = accName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * @param phoneNum
	 *            the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	/**
	 * @return the userImg
	 */
	public String getUserImg() {
		return userImg;
	}

	/**
	 * @param userImg
	 *            the userImg to set
	 */
	public void setUserImg(String userImg) {
		this.userImg = userImg;
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
	 * @return the joinDt
	 */
	public String getJoinDt() {
		return joinDt;
	}

	/**
	 * @param joinDt
	 *            the joinDt to set
	 */
	public void setJoinDt(String joinDt) {
		this.joinDt = joinDt;
	}

	/**
	 * @return the lastLoginDt
	 */
	public String getLastLoginDt() {
		return lastLoginDt;
	}

	/**
	 * @param lastLoginDt
	 *            the lastLoginDt to set
	 */
	public void setLastLoginDt(String lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp
	 *            the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
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

}
