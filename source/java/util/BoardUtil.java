package com.seoulmetro.safetykeeper.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seoulmetro.safetykeeper.api.service.MobileAPIValidate;

/**
 * 게시판 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class BoardUtil {

	/** 로거 */
	private final static Logger LOGGER = LoggerFactory.getLogger(MobileAPIValidate.class);

	/**
	 * 게시물 시작 번호를 반환한다.
	 * 
	 * @param totalCnt
	 *            총 게시물 수
	 * @param pageNo
	 *            현재 페이지 번호
	 * @param cntPerPage
	 *            한 페이지당 게시물 수
	 * @return 게시물 시작 번호
	 */
	public static int getListNo(int totalCnt, int pageNo, int cntPerPage) {
		return totalCnt - ((pageNo - 1) * cntPerPage);
	}

	/**
	 * 게시물 페이징을 반환한다.
	 * 
	 * @param totalCnt
	 *            총 게시물 수
	 * @param pageNo
	 *            현재 페이지 번호
	 * @param cntPerPage
	 *            한 페이지당 게시물 수
	 * @return 게시물 페이징
	 */
	public static String getPaging(int totalCnt, int pageNo, int cntPerPage) {
		return getPaging(totalCnt, pageNo, cntPerPage, null);
	}

	/**
	 * 게시물 페이징을 반환한다.
	 * 
	 * @param totalCnt
	 *            총 게시물 수
	 * @param pageNo
	 *            현재 페이지 번호
	 * @param cntPerPage
	 *            한 페이지당 게시물 수
	 * @param functionNm
	 *            펑션 명
	 * @return 게시물 페이징
	 */
	public static String getPaging(int totalCnt, int pageNo, int cntPerPage, String functionNm) {

		String fixedFunctionNm = functionNm;
		if (fixedFunctionNm == null || fixedFunctionNm.equals("")) {
			fixedFunctionNm = "pageMove";
		}

		String paging = "";

		// 현재 페이지 번호
		int currentPageNum = pageNo;
		if (currentPageNum <= 0) {
			currentPageNum = 1;
		}

		// 이전 페이지 번호
		int prevPageNum = currentPageNum - 1 == 0 ? 1 : currentPageNum - 1;

		// 마지막 페이지 번호
		int lastPageNum = totalCnt == 0 ? 1 : ((int) Math.ceil((double) totalCnt / cntPerPage));

		// 다음 페이지 번호
		int nextPageNum = currentPageNum + 1 > lastPageNum ? lastPageNum : currentPageNum + 1;

		// 한 페이징당 페이징 블럭수
		int pageBlock = 10;

		// 페이지 시작 번호
		int startPageNum = ((currentPageNum % pageBlock == 0 ? currentPageNum - 1 : currentPageNum) / pageBlock) * pageBlock + 1;

		// 처음
		paging += "<a class=\"btn_prve_end\" href=\"javascript:" + fixedFunctionNm + "(1)\">";
		paging += PropertyUtil.getProperty("board", "board.paging.first");
		paging += "</a>\n";

		// 이전
		paging += "<a class=\"btn_prve\" href=\"javascript:" + fixedFunctionNm + "(" + String.valueOf(prevPageNum) + ")\">";
		paging += PropertyUtil.getProperty("board", "board.paging.prev");
		paging += "</a>\n";

		// 페이지 번호
		for (int pg = startPageNum; (pg < (startPageNum + pageBlock)) && (pg <= lastPageNum); pg++) {
			// 현재 페이지이면
			if (pg == currentPageNum) {
				paging += "<a class=\"selected\" href=\"javascript:" + fixedFunctionNm + "(" + String.valueOf(pg) + ")\">" + String.valueOf(pg)
						+ "</a>\n";
			} else {
				paging += "<a href=\"javascript:" + fixedFunctionNm + "(" + String.valueOf(pg) + ")\">" + String.valueOf(pg) + "</a>\n";
			}
		}

		// 다음
		paging += "<a class=\"btn_next\" href=\"javascript:" + fixedFunctionNm + "(" + String.valueOf(nextPageNum) + ")\">";
		paging += PropertyUtil.getProperty("board", "board.paging.next");
		paging += "</a>\n";

		// 마지막
		paging += "<a class=\"btn_next_end\" href=\"javascript:" + fixedFunctionNm + "(" + String.valueOf(lastPageNum) + ")\">";
		paging += PropertyUtil.getProperty("board", "board.paging.last");
		paging += "</a>\n";

		return paging;
	}

	public static void main(String[] args) {
		LOGGER.debug("paging : {}", getPaging(9023, 363, 10));
	}
}
