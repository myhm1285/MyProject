package com.test.board.common.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.test.board.util.DateUtil;

/**
 * 엑셀 다운로드를 위한 엑셀 파일을 생성하는 클래스
 *
 * @author 컨버전스스퀘어
 * @since 2015. 10. 1.
 * @version 1.0
 */
public class ExcelDownloadView extends AbstractExcelView {

	/** 로거 */
	private final static Logger LOGGER = LoggerFactory.getLogger(ExcelDownloadView.class);

	/**
	 * 타이틀 폰트
	 */
	private HSSFFont titleFont;

	/**
	 * 타이틀 셀 스타일
	 */
	private HSSFCellStyle titleCellStyle;

	/**
	 * 타이틀 셀 스타일
	 */
	private HSSFCellStyle dataCellStyle;

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook wb, HttpServletRequest request, HttpServletResponse response) {

		LOGGER.debug("buildExcelDocument : START");

		// 타이틀 폰트 생성
		titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 10); // 폰트 크기
		titleFont.setColor(IndexedColors.WHITE.getIndex()); // 폰트 컬러
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 볼드체

		// 타이틀 스타일 생성
		titleCellStyle = wb.createCellStyle();
		// 테두리
		titleCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titleCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 정렬
		titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 배경 색상
		titleCellStyle.setFillForegroundColor(HSSFColor.BLACK.index);
		titleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 폰트
		titleCellStyle.setFont(titleFont);

		// 데이터 스타일 생성
		dataCellStyle = wb.createCellStyle();
		// 테두리
		dataCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dataCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// 정렬
		dataCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 파일명
		String fileName = (String) model.get("fileName");
		// 타이틀
		String titleArray = (String) model.get("titleArray");
		// 컬럼명
		String columnArray = (String) model.get("columnArray");
		// 컬럼 사이즈
		String sizeArray = (String) model.get("sizeArray");
		// 데이터 리스트
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) model.get("dataList");

		// 파일명 세팅
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + getKoreanFileName(fileName, request) + "_" + DateUtil.currentDate("yyyyMMdd_HHmmss") + ".xls\"");

		// 시트명 세팅
		HSSFSheet excelSheet = wb.createSheet(fileName);

		// 사이즈 세팅
		setExcelSize(excelSheet, sizeArray);

		// 타이틀 세팅
		setExcelTitle(excelSheet, titleArray);

		// 데이터 세팅
		setDataList(excelSheet, dataList, columnArray);

		LOGGER.debug("buildExcelDocument : END");
	}

	/**
	 * 브라우저에 따른 한글 파일명 처리
	 * 
	 * @param fileName
	 *            파일명
	 * @param request
	 *            HttpServletRequest
	 * @return 인코딩 된 한글 파일 명
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

	/**
	 * 타이틀 세팅
	 * 
	 * @param excelSheet
	 *            HSSFSheet
	 * @param sizeArray
	 *            타이틀 배열
	 */
	public void setExcelSize(HSSFSheet excelSheet, String sizeArray) {
		// 사이즈 배열이 null이 아니면
		if (sizeArray != null) {
			int cellIdx = 0;
			// 사이즈로 반복
			for (String size : sizeArray.split("[|]")) {
				try {
					excelSheet.setColumnWidth(cellIdx++, Integer.parseInt(size));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 타이틀 세팅
	 * 
	 * @param excelSheet
	 *            HSSFSheet
	 * @param titleArray
	 *            타이틀 배열
	 */
	public void setExcelTitle(HSSFSheet excelSheet, String titleArray) {
		HSSFRow titleRow = excelSheet.createRow(0);
		titleRow.setHeightInPoints((short) 20);

		// 타이틀명 배열이 null이 아니면
		if (titleArray != null) {
			int cellIdx = 0;
			// 타이틀명으로 반복
			HSSFCell cell;
			for (String title : titleArray.split("[|]")) {
				// 셀 생성
				cell = titleRow.createCell(cellIdx++);
				// 셀 스타일 세팅
				cell.setCellStyle(titleCellStyle);
				// 셀 값 세팅
				cell.setCellValue(title);
			}
		}
	}

	/**
	 * 데이터 세팅
	 * 
	 * @param excelSheet
	 *            HSSFSheet
	 * @param dataList
	 *            Map 데이터 목록
	 * @param columnArray
	 *            컬럼명 배열
	 */
	public void setDataList(HSSFSheet excelSheet, List<Map<String, Object>> dataList, String columnArray) {
		// 데이터 목록과 컬럼 배열이 null이 아니면
		if (dataList != null && columnArray != null) {
			int recordIdx = 1;
			// 데이터 목록으로 반복
			HSSFCell cell;
			for (Map<String, Object> map : dataList) {
				HSSFRow excelRow = excelSheet.createRow(recordIdx++);
				excelRow.setHeightInPoints((short) 20);
				int cellIdx = 0;
				// 컬럼명으로 반복
				for (String column : columnArray.split("[|]")) {
					// 셀 생성
					cell = excelRow.createCell(cellIdx++);
					// 셀 스타일 세팅
					cell.setCellStyle(dataCellStyle);
					Object value = map.get(column);
					if (value == null) {
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					} else if (column.endsWith("_DT")) {
						// 길이가 20 이상이면
						if (value.toString().length() > 20) {
							// 마이크로 타임 시간을 자름
							cell.setCellValue(value.toString().substring(0, 19));
						} else {
							cell.setCellValue(value.toString());
						}
					} else if (column.equals("IDX")) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Long.parseLong(String.valueOf(value)));
					} else if (column.endsWith("_CNT")) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Long.parseLong(String.valueOf(value)));
					} else if (column.endsWith("_AVG")) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Long.parseLong(String.valueOf(value)));
					} else if (column.endsWith("_SCORE")) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.parseDouble(String.valueOf(value)));
					} else if (column.endsWith("_RATE")) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(Double.parseDouble(String.valueOf(value)));
					} else {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(value.toString());
					}
				}
			}
		}
	}
}