package com.test.board.common.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * 파일 다운로드를 위한 파일을 생성하는 클래스
 *
 * @author 컨버전스스퀘어
 * @since 2015. 9. 22.
 * @version 1.0
 */
public class DownloadView extends AbstractView {

	/** 로거 */
	private final static Logger LOGGER = LoggerFactory.getLogger(DownloadView.class);

	public DownloadView() {
		setContentType("applicaiton/download;charset=utf-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		LOGGER.debug("renderMergedOutputModel : START");

		// 다운 받을 파일
		File file = (File) model.get("downloadFile");
		// 파일명
		String fileName = (String) model.get("downloadFileName");

		response.setContentType(getContentType());
		response.setContentLength((int) file.length());

		// 파일명 세팅
		response.setHeader("Content-Disposition", "attachment; filename=\"" + getKoreanFileName(fileName, request) + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");

		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e2) {
				}
			}
		}
		out.flush();

		LOGGER.debug("renderMergedOutputModel : END");
	}

	/**
	 * 브라우저에 따른 한글 파일명 처리
	 * 
	 * @param fileName
	 *            파일명
	 * @param request
	 *            HttpServletRequest
	 * @return 변환된 한글 파일 명
	 */
	private String getKoreanFileName(String fileName, HttpServletRequest request) {

		String resultFileName = fileName;
		// User-Agent 추출
		String userAgent = request.getHeader("User-Agent");

		try {
			// 인터넷 익스플로러이면
			if (userAgent.indexOf("MSIE") >= 0 || userAgent.indexOf("Trident") >= 0) {
				resultFileName = URLEncoder.encode(resultFileName, "utf-8");
			} else {
				resultFileName = new String(resultFileName.getBytes("UTF-8"), "8859_1");
			}
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("IGNORED : {}", e.getLocalizedMessage());
		}

		return resultFileName;
	}

}
