package com.itw.common.download.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itw.common.download.service.DownloadConstants;
import com.itw.common.download.service.DownloadVO;
import com.itw.front.common.service.SessionLocaleConstants;
import com.itw.util.PropertyUtil;
import com.itw.util.SessionUtil;
import com.itw.util.ThumbUtil;

/**
 * 다운로드 콘트롤러 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2015.08.01
 * @version 1.0
 */
@Controller
public class DownloadController {

	/** 로거 */
	private final Logger logger = LoggerFactory.getLogger(DownloadController.class);

	/**
	 * 다운로드 (필요한 정보가 충분하지 않을 경우 준비 중 이미지로 대체한다.)
	 * 
	 * @param model
	 *            ModelMap
	 * @param downloadVO
	 *            조회할 정보가 담긴 VO
	 * @param session
	 *            HttpSession
	 * @return downloadView
	 * @throws Exception
	 */
	@RequestMapping(value = "/common/download.do")
	public ModelAndView download(ModelMap model, @ModelAttribute("downloadVO") DownloadVO downloadVO, HttpSession session) throws Exception {

		// 기본정보 누락
		if (downloadVO.getUfn() == null || downloadVO.getUfn().equals("")) {
			logger.debug("기본정보 없음 : {}", downloadVO.toString());
			// 준비중 파일명
			downloadVO.setUfn("ready");
		}

		// 다운로드 파일명이 없으면
		if (downloadVO.getDfn() == null || downloadVO.getDfn().equals("")) {
			downloadVO.setDfn(downloadVO.getUfn());
		}

		// 업로드 경로 확인
		String uploadPath;
		String thumbPath;
		if (DownloadConstants.FEED.equals(downloadVO.getGubun())) {
			uploadPath = PropertyUtil.getProperty("upload", "upload.path.feed");
			thumbPath = PropertyUtil.getProperty("upload", "upload.path.feed.thumb");
		} else if (DownloadConstants.SHOP.equals(downloadVO.getGubun())) {
			uploadPath = PropertyUtil.getProperty("upload", "upload.path.shop");
			thumbPath = PropertyUtil.getProperty("upload", "upload.path.shop.thumb");
		} else if (DownloadConstants.ITEM.equals(downloadVO.getGubun())) {
			uploadPath = PropertyUtil.getProperty("upload", "upload.path.item");
			thumbPath = PropertyUtil.getProperty("upload", "upload.path.item.thumb");
		} else {
			// 준비중 파일로 처리
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("downloadFile", new File(PropertyUtil.getProperty("upload", "upload.path.ready") + System.getProperty("file.separator")
					+ (SessionUtil.getLang(session).equals(SessionLocaleConstants.KOR) ? "ready" : "ready_e")));
			map.put("downloadFileName", downloadVO.getDfn());
			return new ModelAndView("downloadView", map);
		}

		// 썸네일 사이즈가 있으면
		if (DownloadConstants.THUMB_504x300.equals(downloadVO.getThumb()) || DownloadConstants.THUMB_162x90.equals(downloadVO.getThumb())
				|| DownloadConstants.THUMB_108x60.equals(downloadVO.getThumb()) || DownloadConstants.THUMB_81x45.equals(downloadVO.getThumb())
				|| DownloadConstants.THUMB_50x50.equals(downloadVO.getThumb()) || DownloadConstants.THUMB_120x67.equals(downloadVO.getThumb())
				|| DownloadConstants.THUMB_145x81.equals(downloadVO.getThumb())) {
			File thumbFile = new File(thumbPath + System.getProperty("file.separator") + downloadVO.getThumb() + "_" + downloadVO.getUfn());
			// 썸네일 파일이 없으면
			if (!thumbFile.exists()) {
				String orgImgPath = uploadPath + System.getProperty("file.separator") + downloadVO.getUfn();
				String createImgPath = thumbPath + System.getProperty("file.separator") + downloadVO.getThumb() + "_" + downloadVO.getUfn();
				// 썸네일 생성
				if (!ThumbUtil.createThumbnail(orgImgPath, createImgPath, downloadVO.getThumb().split("x")[0], downloadVO.getThumb().split("x")[1])) {
					logger.debug("썸네일 생성 실패 : {}", downloadVO.toString());
					// 준비중 파일로 처리
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("downloadFile", new File(PropertyUtil.getProperty("upload", "upload.path.ready") + System.getProperty("file.separator")
							+ (SessionUtil.getLang(session).equals(SessionLocaleConstants.KOR) ? "ready" : "ready_e")));
					map.put("downloadFileName", downloadVO.getDfn());
					return new ModelAndView("downloadView", map);
				}
			}

			// 다운로드 정보 세팅
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("downloadFile", thumbFile);
			map.put("downloadFileName", downloadVO.getDfn());
			return new ModelAndView("downloadView", map);
		}

		// 업로드된 파일이 없으면
		File uploadFile = new File(uploadPath + System.getProperty("file.separator") + downloadVO.getUfn());
		if (!uploadFile.exists()) {
			logger.debug("다운로드 파일이 없음 : {}", downloadVO.toString());
			// 준비중 파일로 처리
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("downloadFile", new File(PropertyUtil.getProperty("upload", "upload.path.ready") + System.getProperty("file.separator")
					+ (SessionUtil.getLang(session).equals(SessionLocaleConstants.KOR) ? "ready" : "ready_e")));
			map.put("downloadFileName", downloadVO.getDfn());
			return new ModelAndView("downloadView", map);
		}

		// 1. 다운로드 정보 세팅
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("downloadFile", uploadFile);
		map.put("downloadFileName", downloadVO.getDfn());

		return new ModelAndView("downloadView", map);
	}
}
