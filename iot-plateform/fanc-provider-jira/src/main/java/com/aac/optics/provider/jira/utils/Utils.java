package com.aac.optics.provider.jira.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SideCar工具类
 * @author 60055656
 */
public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);


	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * NULL值
	 */
	private static final String NULL = "null";

	/**
	 * 最大穴号值
	 */
	public static final int MAX_HOLE_VALUE = 24;

	/**
	 * 光学MES厂区站点
	 */
	public static final String XNY = "2080";
	public static final String XNY_NEW = "2210";
	public static final String SUZHOU = "2090";
	public static final String XINBEI = "5060";

	/**
	 * 中转仓发料类型
	 */
	public static final String MATCH_TRANSFER = "按编组发料";
	public static final String NORMAL_TRANSFER = "正常发料";
	public static final String ENGINEERING_TRANSFER = "工程发料";

	/**
	 * 日班 晚班 起始时刻
	 */
	public static final int DAY_SHIFT_BEGIN = 7;
	public static final int NIGTH_SHIFT_BEGIN = 19;

	/**
	 * 正则：正整数
	 */
	public static final String POSITIVIE_INTEGER = "^\\+?[1-9]\\d*$";
	
	/**
	 * 正则：数字
	 */
	public static final String REGEX_NUMBER = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";

	/**
	 * 编组上料中间表数据状态
	 */
	public static final int UNLOAD = 0;// 下料
	public static final int LOAD = 1;// 上料
	public static final int BIND_RESOURCE_SLOT = 2;// 上到资源安装点
	public static final int MATCH_PRINT_END = 3;// 编组打印结束
	public static final int LOCK = 4;// 堆料锁定
	public static final int UNLOCK = 5;// 解除锁定

	/**
	 *  正则：合法日期1
	 */
	public static final String DATEFORMAT = "^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}"
			+ "|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))"
			+ "|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))"
			+ "|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$";

	/**
	 * 正则：合法日期2
	 */
	public static final String YYYYMMDD = "^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}"
				+ "|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))"
				+ "|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))"
				+ "|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)$";

	/**
	 * 正则：yyyy-MM-dd HH:mm
	 */
	public static final String TIMEFORMAT = "^[1-2][0-9][0-9][0-9]-([1][0-2]|0?[1-9])-([12][0-9]|3[01]|0?[1-9]) ([01]?[0-9]|[2][0-3]):[0-5]?[0-9]$";

	/**
	 * 日期转字符串格式模板
	 */
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String YMDHMS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 东八区时间转格林威治时间
	 * 
	 * @return
	 */
	public static Date getDateTimeZero0() {
		return DateUtils.addHours(new Date(), -8);
	}


	/**
	 * 格林威治时间转东八区时间并获取对应的YYYY-MM-DD HH:MM:SS字符串
	 * Java8建议使用 Instant 代替 Date，LocalDateTime 代替 Calendar，
	 * DateTimeFormatter 代替 SimpleDateFormat  避免线程不安全
	 * @return
	 */
	public static String formatDateTime(Date date) {

		Instant instant = date.toInstant();
		LocalDateTime dateTime = instant
				.atZone( ZoneId.systemDefault() )
				.toLocalDateTime();

		DateTimeFormatter df = DateTimeFormatter.ofPattern(YMDHMS);

		return df.format(dateTime.plusHours(+8L));
	}

	/**
	 * 获取东八区时间YYYY-MM-DD HH:MM:SS字符串
	 * @return
	 */
	public static String formatDateTimeWithMs() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YMDHMS_SSS);

		return formatter.format(LocalDateTime.now());

	}

	/**
	 * Date时间格式转XMLGregorianCalendar格式
	 * 
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar convertToXmlGregorianCalendar(Date date) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar gc = null;
		try {
			gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return gc;
	}

	/**
	 * 导出excel对象
	 * 
	 * @param response
	 *            httpResponse对象
	 * @param workbook
	 *            workBook对象
	 * @param fileName
	 *            导出文件名
	 * @throws Exception
	 *             异常抛出
	 */
	public static void export(HttpServletResponse response, Workbook workbook, String fileName) throws Exception {
		response.reset();
		response.setContentType("application/x-msdownload");
		fileName = fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		response.setHeader("Content-disposition",
				"attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
		ServletOutputStream outStream = null;
		try {
			outStream = response.getOutputStream();
			workbook.write(outStream);
		} finally {
			((OutputStream) workbook).close();
			if (outStream != null) {
				outStream.close();
			}
		}
	}

	public static String getlocalDateAndDuty(Date date) {

		String monthDayAndDuty = "";
		String formatDate = formatDateTime(date);
		String time = StringUtils.split(formatDate, " ")[1];
		int hour = Integer.parseInt(StringUtils.split(time, ":")[0]);
		if (hour >= DAY_SHIFT_BEGIN && hour < NIGTH_SHIFT_BEGIN) {
			monthDayAndDuty = formatDate.substring(5, 10) + "A";
		} else {
			monthDayAndDuty = formatDate.substring(5, 10) + "B";
		}

		return monthDayAndDuty;
	}

	public static List<String> removeDuplicate(List<String> list) {
		LinkedHashSet<String> set = new LinkedHashSet<>(list.size());
		set.addAll(list);
		List<String> retList = new ArrayList<>();
		retList.addAll(set);

		return retList;
	}

	/**
	 * 编组格式转换item-model-hole -> holemodel
	 * @param matchRules
	 * @return
	 */
	public static String formatMatchRules(String matchRules) {

		String[] rules = (matchRules + "-").split("#-");
		StringBuilder matchSave = new StringBuilder();
		for (String itemMatch : rules) {
			String[] rule = itemMatch.split("-");
			matchSave.append(rule[2]).append('#').append(rule[1]).append('-');
		}

		return matchSave.substring(0, matchSave.length() - 1);
	}

	/**
	 * 编组格式转换item-model-markno-hole -> item-model-hole
	 * @param match
	 * @return
	 */
	public static String formatConvertMatchRules(String match) {

		String[] rules = (match + "-").split("#-");
		// BR-N1-N12-3,P1-N1-X10-2,P2-N2-*-6,P3-N3-N12-1,P4-N4-*-9,P5-N5-N15-12
		StringBuilder matchFmt = new StringBuilder();
		for (String itemMatch : rules) {
			String[] rule = itemMatch.split("-");
			if (rule.length < 4) {
				return match;
			}
			matchFmt.append(rule[0]).append("-").append(rule[1]).append("-").append(rule[3]).append("#-");
		}

		return matchFmt.substring(0, matchFmt.length() - 1);
	}

	/**
	 * 判断是否是空字符串
	 * @param matchStr
	 * @return boolean
	 */
	public static boolean isEmpty(String matchStr) {
		return matchStr == null || NULL.equalsIgnoreCase(matchStr.trim()) || "".equals(matchStr.trim())
				|| "N/A".equalsIgnoreCase(matchStr.trim());
	}

	/**
	 * 验证是否符合正则表达式
	 * 
	 * @param patternTxt
	 *            正则表达式文本
	 * @param matchTxt
	 *            匹配文本
	 * @return boolean
	 */
	public static boolean isRegexMatched(String patternTxt, String matchTxt) {
		Pattern pattern = Pattern.compile(patternTxt);
		Matcher isNum = pattern.matcher(matchTxt);
		return isNum.matches();
	}

	// 模板下载工具类
	public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
		try {
			response.reset();

			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);

			if (os != null) {
				os.flush();
				os.close();
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static String deleteSubString(String str1, String str2) {
		StringBuilder sb = new StringBuilder(str1);
		int delCount = 0;

		while (true) {
			int index = sb.indexOf(str2);
			if (index == -1) {
				break;
			}
			sb.delete(index, index + str2.length());
			delCount++;

		}
		if (delCount != 0) {
			str1 = sb.toString();
		}

		return str1;
	}

	public static String getModelOrHoleFromMatch(List<String> match, String itemName, String modelOrHole) {

		String value = "";
		if (match != null && !match.isEmpty()) {
			for (String partRule : match) {
				if (partRule.startsWith(itemName)) {
					if ("MODEL".equals(modelOrHole)) {
						value = partRule.split("-")[1];
					} else if ("HOLE".equals(modelOrHole)) {
						value = partRule.split("-")[2];
						if (!value.endsWith("#")) {
							value = value + "#";
						}
					}
				}
			}
		}

		return value;
	}

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

	/**
	 * SQL语句中In的条件值
	 * @param multiParamWithComma 用英文逗号隔开的同一个参数的多个值的字符串
	 * @return 直接注入SQL in条件的值
	 */
	public static String formatParamsOfSqlInCondition(String multiParamWithComma ){
		if(StringUtils.isBlank(multiParamWithComma)){
			return "";
		}else {
			List<String> paramList = Arrays.asList(multiParamWithComma.split(","));
			return "'" + StringUtils.join(paramList, "','") + "'";
		}
	}
}
