package com.test.board.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * 썸네일 유틸 클래스
 *
 * @author 컨버전스스퀘어
 * @since 2015. 9. 27.
 * @version 1.0
 */
public class ThumbUtil {

	/**
	 * 썸네일 이미지 생성
	 * 
	 * @param orgImgPath
	 *            오리지널 이미지 경로 (파일명 포함)
	 * @param thumbImgPath
	 *            썸네일 이미지 경로 (파일명 포함)
	 * @param thumbWidth
	 *            썸네일 가로
	 * @param thumbHeight
	 *            썸네일 세로
	 * @return 성공여부 (Y/N)
	 */
	public static boolean createThumbnail(String orgImgPath, String thumbImgPath, String thumbWidth, String thumbHeight) {
		int intThumbWidth = 0;
		int intThumbHeight = 0;
		try {
			intThumbWidth = Integer.parseInt(thumbWidth);
			intThumbHeight = Integer.parseInt(thumbHeight);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}

		return createThumbnail(orgImgPath, thumbImgPath, intThumbWidth, intThumbHeight);
	}

	/**
	 * 썸네일 이미지 생성
	 * 
	 * @param orgImgPath
	 *            오리지널 이미지 경로 (파일명 포함)
	 * @param thumbImgPath
	 *            썸네일 이미지 경로 (파일명 포함)
	 * @param thumbWidth
	 *            썸네일 가로
	 * @param thumbHeight
	 *            썸네일 세로
	 * @return 성공여부 (Y/N)
	 */
	public static boolean createThumbnail(String orgImgPath, String thumbImgPath, int thumbWidth, int thumbHeight) {
		File orgImgFile = null;
		File thumbImgFile = null;
		Graphics2D graphic = null;
		ResampleOp resampleOp = null;
		BufferedImage orgImgBuffer = null;
		BufferedImage thumbImgBuffer = null;
		try {
			orgImgFile = new File(orgImgPath);
			if (!orgImgFile.exists()) {
				return false;
			}
			thumbImgFile = new File(thumbImgPath);
			orgImgBuffer = ImageIO.read(orgImgFile);
			resampleOp = new ResampleOp(thumbWidth, thumbHeight);
			resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
			thumbImgBuffer = resampleOp.filter(orgImgBuffer, null);
			// thumbImgBuffer = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_3BYTE_BGR);
			// graphic = thumbImgBuffer.createGraphics();
			// graphic.drawImage(orgImgBuffer, 0, 0, thumbWidth, thumbHeight, null);
			ImageIO.write(thumbImgBuffer, "png", thumbImgFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (thumbImgBuffer != null) {
				thumbImgBuffer = null;
			}
			if (orgImgBuffer != null) {
				orgImgBuffer = null;
			}
			if (graphic != null) {
				graphic.dispose();
				graphic = null;
			}
		}
		return true;
	}
}
