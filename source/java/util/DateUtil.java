package com.seoulmetro.safetykeeper.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 날짜 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public class DateUtil {

	/**
	 * 패턴에 맞는 날짜 형식으로 변환
	 * 
	 * @param date
	 *            날짜 데이터
	 * @param pattern
	 *            날짜 포맷
	 * @return 변환된 문자열
	 */
	public static String toText(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, new Locale("ko", "KOREA"));

		return formatter.format(date);
	}

	/**
	 * Date형 날짜를 yyyy-MM-dd 형식의 텍스트로 변환
	 * 
	 * @param date
	 *            날짜 데이터
	 * @return 변환된 문자열
	 */
	public static String toText(Date date) {
		if (date == null)
			return "";

		return toText(date, "yyyy-MM-dd");
	}

	/**
	 * 날짜정보를 String형 (yyyy-MM-dd)에서 Date형으로 변환
	 * 
	 * @param date
	 *            날짜 포멧에 맞는 String형 날짜
	 * @return 변환된 날짜 데이터
	 */
	public static Date toDate(String date) {
		if (date == null || date.equals(""))
			return null;

		return toDate(date, "yyyy-MM-dd");
	}

	/**
	 * 날짜정보를 String형에서 Date형으로 변환
	 * 
	 * @param date
	 *            날짜 포멧에 맞는 String형 날짜
	 * @param pattern
	 *            날짜 포멧
	 * @return 변환된 날짜 데이터
	 */
	public static Date toDate(String date, String pattern) {
		if (date == null)
			return null;

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		try {
			return (Date) sdf.parseObject(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 날짜정보를 String형 Timestamp형으로 변환 ( MSSQL용 )
	 * 
	 * @param strDate
	 *            날짜 포멧에 맞는 String형 날짜
	 * @return Timestamp형 날짜 데이터
	 */
	public static Timestamp toDateMSSQL(String strDate) {
		if (strDate == null)
			return null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = (Date) sdf.parseObject(strDate);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 날짜 포멧 변경
	 * 
	 * @param str
	 *            날짜 문자열
	 * @param beforePattern
	 *            변경 전 포멧
	 * @param afterPattern
	 *            변경 후 포멧
	 * @return 변환된 문자열
	 */
	public static String format(String str, String beforePattern, String afterPattern) {
		return toText(toDate(str, beforePattern), afterPattern);
	}

	/**
	 * 오늘 날짜를 yyyy-MM-dd 형식으로 가져온다
	 * 
	 * @return 오늘 날짜 데이터 문자형으로 반환 (yyyy-MM-dd)
	 */
	public static String currentDate() {
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());

		return toText(cal.getTime());
	}

	/**
	 * 오늘 날짜를 원하는 날짜 패턴으로 가져온다
	 * 
	 * @param pattern
	 *            날짜 패턴
	 * @return 오늘 날짜 데이터 문자형으로 반환
	 */
	public static String currentDate(String pattern) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());

		return toText(cal.getTime(), pattern);
	}

	/**
	 * 날짜에 해당하는 월의 첫 날짜 반환
	 * 
	 * @param strDate
	 *            문자열 날짜
	 * @param inOutPattern
	 *            입출력 날짜 패턴
	 * @return 첫 날짜
	 */
	public static String firstDayOfMonth(String strDate, String inOutPattern) {

		return firstDayOfMonth(toDate(strDate, inOutPattern), inOutPattern);
	}

	/**
	 * 날짜에 해당하는 월의 마지막 날짜 반환
	 * 
	 * @param strDate
	 *            문자열 날짜
	 * @param inOutPattern
	 *            입출력 패턴
	 * @return 마지막 날짜
	 */
	public static String lastDayOfMonth(String strDate, String inOutPattern) {

		return lastDayOfMonth(toDate(strDate, inOutPattern), inOutPattern);
	}

	/**
	 * 날짜에 해당하는 월의 첫 날짜 반환
	 * 
	 * @param strDate
	 *            문자열 날짜
	 * @param inPattern
	 *            입력 패턴
	 * @param outPattern
	 *            출력 패턴
	 * @return 첫 날짜
	 */
	public static String firstDayOfMonth(String strDate, String inPattern, String outPattern) {

		return firstDayOfMonth(toDate(strDate, inPattern), outPattern);
	}

	/**
	 * 날짜에 해당하는 월의 마지막 날짜 반환
	 * 
	 * @param strDate
	 *            문자열 날짜
	 * @param inPattern
	 *            입력 패턴
	 * @param outPattern
	 *            출력 패턴
	 * @return 마지막 날짜
	 */
	public static String lastDayOfMonth(String strDate, String inPattern, String outPattern) {

		return lastDayOfMonth(toDate(strDate, inPattern), outPattern);
	}

	/**
	 * 날짜에 해당하는 월의 첫 날짜 반환
	 * 
	 * @param date
	 *            Date 날짜
	 * @param pattern
	 *            패턴
	 * @return 첫 날짜
	 */
	public static String firstDayOfMonth(Date date, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int firstDate = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE, firstDate);
		return toText(cal.getTime(), pattern);
	}

	/**
	 * 날짜에 해당하는 월의 마지막 날짜 반환
	 * 
	 * @param date
	 *            Date 날짜
	 * @param pattern
	 *            패턴
	 * @return 마지막 날짜
	 */
	public static String lastDayOfMonth(Date date, String pattern) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int firstDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE, firstDate);
		return toText(cal.getTime(), pattern);
	}

	/**
	 * 24시간 이내의 경우 New 아이콘을 보여준다.
	 * 
	 * @param date
	 *            날짜 데이터
	 * @param newIcon
	 *            아이콘 이미지 ( 예: <img src=\"/images/huser/global/ico_new.gif\" border=\"0\" align=\"absmiddle\"> )
	 * @return new 아이콘 여부에 대한 문자열
	 */
	public static String newIcon(Date date, String newIcon) {
		return newIcon(date, newIcon, 0);
	}

	/**
	 * 24시간 이내의 경우 New 아이콘을 보여준다.
	 * 
	 * @param date
	 *            문자열 날짜 데이터
	 * @param pattern
	 *            날짜 패턴 지정
	 * @param newIcon
	 *            아이콘 이미지 ( 예: <img src=\"/images/huser/global/ico_new.gif\" border=\"0\" align=\"absmiddle\"> )
	 * @return new 아이콘 여부에 대한 문자열
	 */
	public static String newIcon(String date, String pattern, String newIcon) {
		return newIcon(toDate(date, pattern), newIcon);
	}

	/**
	 * 원하는 시간 이내의 경우 New 아이콘을 보여준다.
	 * 
	 * @param date
	 *            날짜 데이터
	 * @param newIcon
	 *            아이콘 이미지 ( 예: <img src=\"/images/huser/global/ico_new.gif\" border=\"0\" align=\"absmiddle\"> )
	 * @return new 아이콘 여부에 대한 문자열
	 */
	public static String newIcon(Date date, String newIcon, int hour) {

		if (date == null)
			return "";

		int calHour = hour;

		if (hour <= 0) {
			calHour = 24;
		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());

		long today = cal.getTime().getTime();

		cal.setTime(date);

		long inputDate = cal.getTime().getTime();

		if ((today - inputDate) / (calHour * 60 * 60 * 1000) <= 1)
			return newIcon;
		else
			return "";
	}

	/**
	 * 두 날짜간 간격을 일(day) 단위로 반환한다.<br>
	 * 만일 두 날짜간의 간격이 24시간(1일) 이내일 경우 1일로 반환
	 * 
	 * @param date1
	 *            java.util.Date형 날짜
	 * @param date2
	 *            java.util.Date형 날짜
	 * @return 일(day) 단위로 반환 ( date1 또는 date2 값이 null 일 경우 -1 반환 )
	 */
	public static int getIntervalDate(Date date1, Date date2) {

		if (date1 == null || date2 == null) {
			return -1;
		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(date1);

		long date1Time = cal.getTime().getTime();

		cal.setTime(date2);

		long date2Time = cal.getTime().getTime();

		double interval = ((double) Math.abs(date2Time - date1Time)) / (double) (24 * 60 * 60 * 1000);

		return ((int) Math.floor(interval)) + 1;
	}

	/**
	 * 두 날짜간 간격을 초(sec) 단위로 반환한다.<br>
	 * 만일 두 날짜간의 간격이 1초 이내일 경우 1초로 반환
	 * 
	 * @param date1
	 *            java.util.Date형 날짜
	 * @param date2
	 *            java.util.Date형 날짜
	 * @return 일(day) 단위로 반환 ( date1 또는 date2 값이 null 일 경우 -1 반환 )
	 */
	public static int getIntervalSec(Date date1, Date date2) {

		if (date1 == null || date2 == null) {
			return -1;
		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(date1);

		long date1Time = cal.getTime().getTime();

		cal.setTime(date2);

		long date2Time = cal.getTime().getTime();

		double interval = ((double) Math.abs(date2Time - date1Time)) / (double) (1000);

		return ((int) Math.floor(interval)) + 1;
	}

	/**
	 * 현재 날짜를 기준으로 원하는 기간만큼의 날짜를 반환한다.<br>
	 * 예) 현재날짜가 : 2007-09-01 이면 한달전 날짜를 뽑는다면 2007-08-01 를 반환해 준다.
	 * 
	 * @param yearTake
	 *            계산하고 싶은 년 변수
	 * @param monthTake
	 *            계산하고 싶은 월 변수
	 * @param dayTake
	 *            계산하고 싶은 일 변수
	 * @return 계산된 날짜 반환 ( YYYY-MM-DD )
	 */
	public static String currentDateTake(int yearTake, int monthTake, int dayTake) {
		return currentDateTake(yearTake, monthTake, dayTake, "yyyy-MM-dd");
	}

	/**
	 * 현재 날짜를 기준으로 원하는 기간만큼의 날짜를 반환한다.<br>
	 * 예) 현재날짜가 : 2007-09-01 이면 한달전 날짜를 뽑는다면 2007-08-01 를 반환해 준다.
	 * 
	 * @param yearTake
	 *            계산하고 싶은 년 변수
	 * @param monthTake
	 *            계산하고 싶은 월 변수
	 * @param dayTake
	 *            계산하고 싶은 일 변수
	 * @param pattern
	 *            날짜 패턴
	 * @return 계산된 날짜 반환
	 */
	public static String currentDateTake(int yearTake, int monthTake, int dayTake, String pattern) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(new Date());

		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + yearTake);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + monthTake);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + dayTake);

		Date date = cal.getTime();

		return toText(date, pattern);
	}

	/**
	 * 날짜 데이터에 맞는 요일을 반환한다.<br>
	 * 1 = 일요일<br>
	 * 2 = 월요일<br>
	 * 3 = 화요일<br>
	 * 4 = 수요일<br>
	 * 5 = 목요일<br>
	 * 6 = 금요일<br>
	 * 7 = 토요일<br>
	 * 
	 * @param strDate
	 *            문자형 날짜 데이터
	 * @param partten
	 *            날짜 패턴
	 * @return 요일 데이터를 숫자값으로 구분해서 반환
	 */
	public static int getDayOfWeek(String strDate, String partten) {
		return getDayOfWeek(toDate(strDate, partten));
	}

	/**
	 * 날짜 데이터에 맞는 요일을 반환한다.<br>
	 * 1 = 일요일<br>
	 * 2 = 월요일<br>
	 * 3 = 화요일<br>
	 * 4 = 수요일<br>
	 * 5 = 목요일<br>
	 * 6 = 금요일<br>
	 * 7 = 토요일<br>
	 * 
	 * @param date
	 *            날짜 데이터
	 * @return 요일 데이터를 숫자값으로 구분해서 반환
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 일요일
	 */
	public final static int SUN = 1;
	/**
	 * 월요일
	 */
	public final static int MON = 2;
	/**
	 * 화요일
	 */
	public final static int TUE = 3;
	/**
	 * 수요일
	 */
	public final static int WED = 4;
	/**
	 * 목요일
	 */
	public final static int THU = 5;
	/**
	 * 금요일
	 */
	public final static int FRI = 6;
	/**
	 * 토요일
	 */
	public final static int SAT = 7;

	/**
	 * 국어
	 */
	public final static int KOR = 1;
	/**
	 * 영어
	 */
	public final static int ENG = 2;
	/**
	 * 중국어
	 */
	public final static int CHN = 3;

	/**
	 * 요일 코드에 해당하는 값을 해당 국가의 언어로 변경해준다.<br>
	 * 예) getDayOfWeekToName( DateUtil.KOR, DateUtil.SAT )
	 * 
	 * @param dayOfWeekType
	 *            1 : 일요일, 2 : 월요일, 3 : 화요일, 4 : 수요일, 5 : 목요일, 6 : 금요일, 7 : 토요일
	 * @param languageCode
	 *            1 : 국문, 2 : 영문, 3 : 중문
	 * @return 요일 반환
	 */
	public static String getDayOfWeekToName(int dayOfWeekType, int languageCode) {
		if (languageCode == 1) {
			switch (dayOfWeekType) {
			case 1:
				return "일";
			case 2:
				return "월";
			case 3:
				return "화";
			case 4:
				return "수";
			case 5:
				return "목";
			case 6:
				return "금";
			case 7:
				return "토";
			}
		} else if (languageCode == 2) {
			switch (dayOfWeekType) {
			case 1:
				return "Sunday";
			case 2:
				return "Monday";
			case 3:
				return "Tuesday";
			case 4:
				return "Wednesday";
			case 5:
				return "Thursday";
			case 6:
				return "Friday";
			case 7:
				return "Saturday";
			}
		} else if (languageCode == 3) {
			switch (dayOfWeekType) {
			case 1:
				return "日";
			case 2:
				return "月";
			case 3:
				return "火";
			case 4:
				return "水";
			case 5:
				return "木";
			case 6:
				return "金";
			case 7:
				return "土";
			}
		}

		return "";
	}

	/**
	 * 단위를 초에서 분+초로 변환한다.
	 * 
	 * @param avgSec
	 *            - 초 단위 데이터
	 * @return 분+초 단위 데이터
	 */
	public static String getAvgMinuteSec(int avgSec) {

		String avgMinuteSec;

		if (avgSec == 0) {
			avgMinuteSec = "-";
		} else if (avgSec < 60) {
			avgMinuteSec = avgSec + "초";
		} else {
			avgMinuteSec = (avgSec / 60) + "분 " + (avgSec % 60) + "초";
		}
		return avgMinuteSec;
	}

	public static void main(String[] args) {

		// System.out.println( getIntervalSec(DateUtil.toDate("2014-10-09 18:45:20", "yyyy-MM-dd HH:mm:ss"),
		// DateUtil.toDate(DateUtil.currentDate("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss")));
		// System.out.println( getIntervalSec(DateUtil.toDate("14-10-09 18:45:20", "yy-MM-dd HH:mm:ss"),
		// DateUtil.toDate(DateUtil.currentDate("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss")));
		// System.out.println(DateUtil.currentDate("yy-MM-dd HH:mm:ss"));
	}
}
