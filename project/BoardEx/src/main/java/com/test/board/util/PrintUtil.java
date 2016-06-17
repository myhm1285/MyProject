package com.test.board.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 프린트 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class PrintUtil {

	/**
	 * 프린트 여부에 따라 출력 여부를 반환한다.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return true : 출력, false : 미출력
	 */
	public static boolean isPrint(HttpServletRequest request) {
		return (request.getParameter("isPrint") != null && request.getParameter("isPrint").equals("true"));
	}

	/**
	 * 팝업 여부에 따라 출력 여부를 반환한다.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return true : 출력, false : 미출력
	 */
	public static boolean isPopup(HttpServletRequest request) {
		return (request.getParameter("isPopup") != null && request.getParameter("isPopup").equals("true"));
	}

	/**
	 * 프린트 여부에 따라 배경을 변경한다.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 스타일
	 */
	public static String getBackground(HttpServletRequest request) {
		if (((request.getParameter("isPrint") != null && request.getParameter("isPrint").equals("true")))
				|| ((request.getParameter("isPopup") != null && request.getParameter("isPopup").equals("true")))) {
			return "style=\"background:#fff\"";
		}
		return "";
	}

	/**
	 * 프린트 여부에 최소 가로사이즈를 변경한다.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 스타일
	 */
	public static String getMinWidth(HttpServletRequest request) {
		if (request.getParameter("isPrint") != null && request.getParameter("isPrint").equals("true")) {
			if (request.getParameter("minWidth") != null) {
				return "style=\"min-width:" + request.getParameter("minWidth") + "px\"";
			} else {
				return "style=\"min-width:1250px\"";
			}
		} else if (((request.getParameter("isPopup") != null && request.getParameter("isPopup").equals("true")))) {
			if (request.getParameter("minWidth") != null) {
				return "style=\"min-width:" + request.getParameter("minWidth") + "px\"";
			}
		}
		return "";
	}
}
