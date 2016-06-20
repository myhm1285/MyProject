package com.test.board.util;

import java.io.File;
import java.util.Random;

/**
 * 파일 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class FileUtil {

	/**
	 * 유일한 파일명을 반환
	 * 
	 * @param uploadPath
	 *            업로드 경로
	 * @return yyyyMMddHHmmss_랜덤숫자5자리
	 */
	public static String getUploadFileName(String uploadPath) {

		// 업로드 폴더가 없으면 생성
		File path = new File(uploadPath);
		if (!path.exists()) {
			path.mkdirs();
		}

		String uploadFileName;
		Random r = new Random();
		int ranInt;
		while (true) {
			ranInt = r.nextInt(99999);

			// 랜덤 5자리를 만들기 위함
			if (ranInt < 10000) {
				continue;
			}

			uploadFileName = DateUtil.currentDate("yyyyMMddHHmmss") + "_" + String.valueOf(ranInt);

			File file = new File(uploadPath + System.getProperty("file.separator") + uploadFileName);

			if (!file.exists()) {
				return uploadFileName;
			}
		}
	}
}
