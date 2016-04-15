package com.itw.util;

import javax.servlet.http.HttpServletRequest;

import com.itw.mng.common.login.service.LoginVO;

/**
 * 권한 유틸 클래스
 *
 * @author 컨버전스스퀘어
 * @since 2015. 9. 3.
 * @version 1.0
 */
public class GrantUtil {

	/**
	 * 접속 권한 여부를 반환한다.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param sessionKeyName
	 *            세션 Key
	 * @return 요청 URI에 대해서 권한이 있으면 true, 없으면 false
	 */
	public static boolean isAccessGrant(HttpServletRequest request, String sessionKeyName) {

		// 요청 URI
		String requestURI = request.getRequestURI();

		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(sessionKeyName);

		if (loginVO == null) {
			return false;
		}

		String rights = loginVO.getRights();

		// 필수 항목이 없으면 false;
		if (requestURI == null || rights == null || requestURI.equals("") || rights.equals("")) {
			return false;
		}

		// 관리자 이미지 업로드
		if (requestURI.indexOf("/admin/imageUploadAjax") >= 0) {
			return true;
		}

		// 권한들
		String[] rightArray = rights.split(",");

		for (String right : rightArray) {
			if (requestURI.indexOf(right) >= 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 메뉴에 대해 권한이 있는지 확인한다.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param sessionKeyName
	 *            세션 Key
	 * @param menuURI
	 *            메뉴 URI
	 * @return menuURI에 대해서 권한이 있으면 true, 없으면 false
	 */
	public static boolean isMenuGrant(HttpServletRequest request, String sessionKeyName, String menuURI) {
		return isMenuGrant(request, sessionKeyName, new String[] { menuURI });
	}

	/**
	 * 메뉴에 대해 권한이 있는지 확인한다.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param sessionKeyName
	 *            세션 Key
	 * @param menuURI
	 *            메뉴 URI 들
	 * @return menuURI에 대해서 권한이 있으면 true, 없으면 false
	 */
	public static boolean isMenuGrant(HttpServletRequest request, String sessionKeyName, String[] menuURI) {

		LoginVO loginVO = (LoginVO) request.getSession().getAttribute(sessionKeyName);

		if (loginVO == null) {
			return false;
		}

		String rights = loginVO.getRights();

		// 필수 항목이 없으면 false;
		if (menuURI == null || rights == null || menuURI.equals("") || rights.equals("")) {
			return false;
		}
		for (String menu : menuURI) {
			if (rights.indexOf(menu) >= 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 해당 메뉴가 선택되어 있는지
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param menuURI
	 *            메뉴 URI
	 * @return 해당 메뉴가 활성화 되어 있으면 class 값인 "hover", 아니면 ""
	 */
	public static String getMenuOnOff(HttpServletRequest request, String menuURI) {
		return getMenuOnOff(request, new String[] { menuURI });
	}

	/**
	 * 해당 메뉴가 선택되어 있는지
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param menuURI
	 *            메뉴 URI 들
	 * @return 해당 메뉴가 활성화 되어 있으면 class 값인 "hover", 아니면 ""
	 */
	public static String getMenuOnOff(HttpServletRequest request, String[] menuURI) {
		// 요청 URI
		String requestURI = request.getRequestURI();

		for (String menu : menuURI) {
			if (requestURI.indexOf(menu) >= 0) {
				return "hover";
			}
		}
		return "";
	}
}
