package com.test.board.common.vo;

/**
 * 프로파일러가 셋팅할 메인 객체<br/>
 * web.xml의 컨텍스트 파라미터 spring.profiles.active의 값에 따라<br/>
 * context-profile에서 분기처리하여 주입해준다.
 *
 * @author hmyoo
 * @since 2017. 1. 15.
 * @version 1.0
 */
public class DefinesVO {

	/** 프로파일 확인을 위한 프로파일 이름 */
	private String configArea;

	/** 서비스 정보 - 도메인 */
	private String domain;

	/** 서비스 정보 - 서비스 이름 */
	private String serviceIdentityName;

	/** 파일 업로드 경로 */
	private String filePath;

	/** 관리자 계정 ID */
	private String adminInfoId;

	/**
	 * @return the configArea
	 */
	public String getConfigArea() {
		return configArea;
	}

	/**
	 * @param configArea
	 *            the configArea to set
	 */
	public void setConfigArea(String configArea) {
		this.configArea = configArea;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the serviceIdentityName
	 */
	public String getServiceIdentityName() {
		return serviceIdentityName;
	}

	/**
	 * @param serviceIdentityName
	 *            the serviceIdentityName to set
	 */
	public void setServiceIdentityName(String serviceIdentityName) {
		this.serviceIdentityName = serviceIdentityName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the adminInfoId
	 */
	public String getAdminInfoId() {
		return adminInfoId;
	}

	/**
	 * @param adminInfoId
	 *            the adminInfoId to set
	 */
	public void setAdminInfoId(String adminInfoId) {
		this.adminInfoId = adminInfoId;
	}

}
