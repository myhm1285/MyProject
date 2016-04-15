package com.itw.mng.common.upload.controller;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.itw.mng.common.login.service.LoginVO;
import com.itw.mng.common.upload.service.UploadConstants;
import com.itw.mng.common.upload.service.UploadVO;
import com.itw.util.FileUtil;
import com.itw.util.PropertyUtil;

/**
 * 업로드 콘트롤러 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2015.08.01
 * @version 1.0
 */
@Controller
public class UploadController {

	/**
	 * 배너를 등록한다.
	 * 
	 * @param uploadVO
	 *            등록할 정보가 담긴 VO
	 * @param model
	 *            ModelMap
	 * @param session
	 *            HttpSession
	 * @return uploadFileName 업로드된 파일명
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/imageUploadAjax.do")
	@ResponseBody
	public String imageUploadAjax(@ModelAttribute("uploadVO") UploadVO uploadVO, @RequestParam("imgFile") MultipartFile imgFile, ModelMap model,
			HttpSession session) throws Exception {

		// 1. 세션에서 등록자 idx 추출/세팅
		LoginVO loginAdminVO = (LoginVO) session.getAttribute("loginAdminVO");
		LoginVO loginCmsVO = (LoginVO) session.getAttribute("loginCmsVO");
		if (loginAdminVO == null && loginCmsVO == null) {
			return "FAIL";
		}

		// 1. 첨부파일 업로드
		String uploadPath;
		if (UploadConstants.FEED.equals(uploadVO.getGubun())) {
			uploadPath = PropertyUtil.getProperty("upload", "upload.path.feed");
		} else if (UploadConstants.SHOP.equals(uploadVO.getGubun())) {
			uploadPath = PropertyUtil.getProperty("upload", "upload.path.shop");
		} else if (UploadConstants.ITEM.equals(uploadVO.getGubun())) {
			uploadPath = PropertyUtil.getProperty("upload", "upload.path.item");
		} else {
			return "FAIL";
		}

		// 원본 파일명
		String originalFileName = imgFile.getOriginalFilename();
		// 원본 파일명 확장자
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
		// 업로드 파일명
		String uploadFileName = FileUtil.getUploadFileName(uploadPath) + fileExt;
		// 업로드 파일명
		// String uploadFileName = FileUtil.getUploadFileName(uploadPath);
		imgFile.transferTo(new File(uploadPath + System.getProperty("file.separator") + uploadFileName));

		return uploadFileName;
	}
}
