package com.know.code.generation.util;

import java.sql.Timestamp;
import java.text.CollationKey;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 日期工具类
 * 
 * @author lenovo
 */
public class DateUtil {

	/** 步长类型-分钟  **/
	public static final int SIZE_MINUTE_STEP = 0;
	/** 步长类型-小时 **/
	public static final int SIZE_HOUR_STEP = 1;
	/** 步长类型-天 **/
	public static final int SIZE_DAY_STEP = 2;
	/** 步长类型-周 **/
	public static final int SIZE_WEEK_STEP = 3;
	/** 步长类型-月 **/
	public static final int SIZE_MONTH_STEP = 4;
	/** 步长类型-年 **/
	public static final int SIZE_YEAR_STEP = 5;
	/** 步长类型-5分钟 **/
	public static final int SIZE_FIVE_MINUTE_STEP = 105;  //小时内枚举值,对应100-159
	/** 步长类型-30分钟 **/
	public static final int SIZE_THIRTY_MINUTE_STEP = 130;  //小时内枚举值,对应100-159
	/** redis 缓存 公司 用户 配置的button集合使用**/
	public static final String CACHE_COMPANY_USER_BUTTON="COM_USER_BUTTON_";
	/** redis 缓存 系统 用户 配置的button集合使用**/
	public static final String CACHE_SYSTEM_USER_BUTTON="SYS_USER_BUTTON_";
	/**
	 * 日志
	 */
	private static Logger _log = LoggerFactory.getLogger(DateUtil.class.getName());
	/// yyyy年 MMM d日 EEE HH:mm:ss 可以将此字符串写入SimpleDateFormat EEE 2013年 五月 25日 星期六
	/// 14:15:20
	//// hh表示12小时制 HH表示24小时制
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String HOUR_MINUTE_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static int currentYear() {
		return yearInt(null);
	}

	/**
	 * 获取季度开始日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date quarterBegin(Date date) {
		Date quarterBeginDate = null;
		if (null == date) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 获取 传入日期的月份
		int month = monthInt(date);
		switch (month) {
		case 1:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-01-01");
			break;
		case 2:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-01-01");
			break;
		case 3:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-01-01");
			break;
		case 4:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-04-01");
			break;
		case 5:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-04-01");
			break;
		case 6:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-04-01");
			break;
		case 7:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-07-01");
			break;
		case 8:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-07-01");
			break;
		case 9:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-07-01");
			break;
		case 10:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-10-01");
			break;
		case 11:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-10-01");
			break;
		case 12:
			quarterBeginDate = strToDate(c.get(Calendar.YEAR) + "-10-01");
			break;
		}
		return quarterBeginDate;
	}

	/**
	 * 获取季度结束日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date quarterEnd(Date date) {
		Date quarterEndDate = null;
		if (null == date) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 获取 传入日期的月份
		int month = monthInt(date);
		switch (month) {
		case 1:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-03-31");
			break;
		case 2:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-03-31");
			break;
		case 3:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-03-31");
			break;
		case 4:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-06-30");
			break;
		case 5:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-06-30");
			break;
		case 6:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-06-30");
			break;
		case 7:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-09-30");
			break;
		case 8:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-09-30");
			break;
		case 9:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-09-30");
			break;
		case 10:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-12-31");
			break;
		case 11:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-12-31");
			break;
		case 12:
			quarterEndDate = strToDate(c.get(Calendar.YEAR) + "-12-31");
			break;
		}
		return quarterEndDate;
	}

	/**
	 * 获取当前季度
	 * 
	 * @param date
	 * @return
	 */
	public static int quarter(Date date, int dayNum) {
		return quarter(dateAddDay(date, dayNum));
	}

	public static Date dateAddDay(Date date, int dayNum) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		c.add(Calendar.DATE, dayNum); // 加1
		return c.getTime();
	}

	/**
	 * 获取当前季度
	 * 
	 * @param date
	 * @return
	 */
	public static int quarter(Date date) {
		int quarter = 0;
		if (null == date) {
			date = new Date();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 获取 传入日期的月份
		int month = monthInt(date);
		switch (month) {
		case 1:
			quarter = 1;
			break;
		case 2:
			quarter = 1;
			break;
		case 3:
			quarter = 1;
			break;
		case 4:
			quarter = 2;
			break;
		case 5:
			quarter = 2;
			break;
		case 6:
			quarter = 2;
			break;
		case 7:
			quarter = 3;
			break;
		case 8:
			quarter = 3;
			break;
		case 9:
			quarter = 3;
			break;
		case 10:
			quarter = 4;
			break;
		case 11:
			quarter = 4;
			break;
		case 12:
			quarter = 4;
			break;
		}
		_log.info("当前季度为：" + quarter);
		return quarter;
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static int yearInt(Date date, int dayNum) {
		Calendar c = Calendar.getInstance();
		if (null != date) {
			c.setTime(date);
		}
		c.add(Calendar.DATE, dayNum); // 加1
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 * 
	 * @param dayNum
	 *            当前日期+dayNum
	 * @return
	 */
	public static int monthInt(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取月份
	 * 
	 * @param dayNum
	 *            当前日期+dayNum
	 * @return
	 */
	public static int monthInt(Date date, int dayNum) {
		Calendar c = Calendar.getInstance();
		if (null != date) {
			c.setTime(date);
		}
		c.add(Calendar.DATE, dayNum); // 加1
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取月份
	 * 
	 * @param dayNum
	 *            当前日期+dayNum
	 * @return
	 */
	public static int dayInt(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(Calendar.DATE);
	}

	/**
	 * 获日
	 * 
	 * @param dayNum
	 *            当前日期+dayNum
	 * @return
	 */
	public static int dayInt(Date date, int dayNum) {
		Calendar c = Calendar.getInstance();
		if (null != date) {
			c.setTime(date);
		}
		c.add(Calendar.DATE, dayNum); // 加1
		return c.get(Calendar.DATE);
	}

	/**
	 * 获取当前年份
	 * 
	 * @return
	 */
	public static int yearInt(Date date) {
		Calendar c = Calendar.getInstance();
		if (date != null) {
			c.setTime(date);
		}
		return c.get(Calendar.YEAR);
	}

	/**
	 * 时间 加 年
	 * 
	 * @param date
	 * @param yearNum
	 * @return
	 */
	public static Date dateAddYear(Date date, int yearNum) {
		date = DateUtils.addYears(date, yearNum);// 一年后
		return date;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		return currYearLast;
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/** 日期格式转换 */
	public static Date dateFormat(Date date) {
		return dateFormat(date, null);
	}

	/** 日期格式转换 */
	public static Date dateFormat(Date date, String dateFormat) {
		SimpleDateFormat sdf = null;
		if (StringUtils.isNotBlank(dateFormat)) {
			sdf = new SimpleDateFormat(dateFormat);
		} else {
			sdf = new SimpleDateFormat(DATE_FORMAT);
		}
		String dateStr = sdf.format(date);
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/** 日期转字符串 */
	public static String dateToStr(Date date, String dateFormat) {
		if (null == date) {
			return null;
		}

		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = DATE_FORMAT;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/** 日期转的字符串 */
	public static String dateToStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 字符串转日期
	 * 
	 * @param str
	 * @return
	 */
	public static Date strToDate(String str) {
		return strToDate(str, null);
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return
	 */
	public static Date strToDate(String str, String dateFormat) {
		Date value;
		try {
			if (StringUtils.isEmpty(dateFormat)) {
				dateFormat = DATE_FORMAT;
			}
			SimpleDateFormat sdfIn = new SimpleDateFormat(dateFormat);
			value = sdfIn.parse(str);
		} catch (ParseException e) {
			return null;
		}
		return value;
	}

	/**
	 * 获取当前时间的time格式
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp() {
		Date value = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetimeStr = sdf.format(value);
		return Timestamp.valueOf(datetimeStr);
	}

	/**
	 * 获取当前时间的time格式
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp(String dateStr) {
		return Timestamp.valueOf(dateStr);
	}

	/**
	 * 给指定日期加XX天
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		if (null != date) {
			Calendar instance = Calendar.getInstance();
			instance.setTime(date);
			instance.add(Calendar.DAY_OF_MONTH, day);
			return instance.getTime();
		}
		return null;
	}

	/**
	 * 将java的sql.date
	 * 
	 * @param date
	 *            java.sql.Date
	 * @return java.util.Date
	 */
	public static java.util.Date transsqldate(java.sql.Date date) {

		java.util.Date utildate = null;
		if (date == null) {

			return utildate;
		}
		utildate = new Date(date.getTime());

		return utildate;
	}

	/**
	 * 获取月份最大天数
	 * 
	 * @return
	 */
	public static int maxMonthDay(Date date) {
		Calendar a = Calendar.getInstance();
		if (null == date) {
			date = new Date();
		}
		a.setTime(date);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 返回一年的天数
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static int maxYearDay(Date date) {
		int year = yearInt(date);// 要判断的年份，比如2008
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {// 闰年的判断规则
			return 366;
		} else {
			return 365;
		}
	}

	/**
	 * 转换日期格式
	 * 
	 * @param time
	 * @return
	 */
	public static String stringOrFormat(String time) {
		String nowTime = null;
		try {
			Date date = new SimpleDateFormat("yyyyMMdd").parse(time);
			String now = new SimpleDateFormat("yyyy-MM-dd").format(date);
			nowTime = now;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nowTime;
	}

	/**
	 * 转换为 多个周期
	 * 
	 * @param timeBegin
	 * @param timeEnd
	 * @return
	 */
	public static TreeMap<String, Object> convertCycles(int stepSize, Date queryTimeBegin, Date queryTimeEnd) {
		TreeMap<String, Object> treeMap = new TreeMap<String, Object>(new Comparator<String>() {
			Collator collator = Collator.getInstance();

			public int compare(String o1, String o2) {
				// 如果有空值，直接返回0
				if (o1 == null || o2 == null) {
					return 0;
				}
				CollationKey ck1 = collator.getCollationKey(String.valueOf(o1));
				CollationKey ck2 = collator.getCollationKey(String.valueOf(o2));
				return ck1.compareTo(ck2);
			}
		});
		Calendar queryTimeBeginCal = Calendar.getInstance();
		Calendar queryTimeEndCal = Calendar.getInstance();
		queryTimeBeginCal.setTime(queryTimeBegin);
		queryTimeEndCal.setTime(queryTimeEnd);
		String timeFormat = null;
		String timeFormatAbb = null;
		int n = 0;
		int n_max = 9999999;
		switch (stepSize) {
		case DateUtil.SIZE_MINUTE_STEP:
			while (n < n_max && queryTimeBeginCal.before(queryTimeEndCal)) {
				timeFormat = "yyyy-MM-dd HH:mm";
				timeFormatAbb = "HH:mm";
				treeMap.put(dateToStr(queryTimeBeginCal.getTime(), timeFormat),
						dateToStr(queryTimeBeginCal.getTime(), timeFormatAbb));
				queryTimeBeginCal.add(Calendar.MINUTE, 1); // 加1
				n = n + 1;
			}
			break;
		case DateUtil.SIZE_FIVE_MINUTE_STEP:
			while (n < n_max && queryTimeBeginCal.before(queryTimeEndCal)) {
				timeFormat = "yyyy-MM-dd HH:mm";
				timeFormatAbb = "HH:mm";
				treeMap.put(dateToStr(queryTimeBeginCal.getTime(), timeFormat),
						dateToStr(queryTimeBeginCal.getTime(), timeFormatAbb));
				queryTimeBeginCal.add(Calendar.MINUTE, 5); // 加1
				n = n + 1;
			}
			break;
		case DateUtil.SIZE_HOUR_STEP:
			while (n < n_max && queryTimeBeginCal.before(queryTimeEndCal)) {
				timeFormat = "yyyy-MM-dd HH";
				timeFormatAbb = "HH:00";
				treeMap.put(dateToStr(queryTimeBeginCal.getTime(), timeFormat),
						dateToStr(queryTimeBeginCal.getTime(), timeFormatAbb));
				queryTimeBeginCal.add(Calendar.HOUR, 1); // 加1天
				n = n + 1;
			}
			break;
		case DateUtil.SIZE_DAY_STEP:
			while (n < n_max && queryTimeBeginCal.before(queryTimeEndCal)) {
				timeFormat = "yyyy-MM-dd";
				timeFormatAbb = "MM/dd";
				treeMap.put(dateToStr(queryTimeBeginCal.getTime(), timeFormat),
						dateToStr(queryTimeBeginCal.getTime(), timeFormatAbb));
				queryTimeBeginCal.add(Calendar.DATE, 1); // 加1天
				n = n + 1;
			}
			break;
		case DateUtil.SIZE_WEEK_STEP:
			while (n < n_max && queryTimeBeginCal.before(queryTimeEndCal)) {
				// 部委写法
				timeFormat = "yyyy-MM-dd";
				timeFormatAbb = "MM/dd";
				treeMap.put(dateToStr(queryTimeBeginCal.getTime(), timeFormat),
						dateToStr(queryTimeBeginCal.getTime(), timeFormatAbb));
				queryTimeBeginCal.add(Calendar.DATE, 7); // 加7天
				// ----------------------- 周日 为 第一天的写法 结束
				n = n + 1;
			}
			break;
		case DateUtil.SIZE_MONTH_STEP:
			while (n < n_max && queryTimeBeginCal.before(queryTimeEndCal)) {
				timeFormat = "yyyy-MM";
				timeFormatAbb = "yyyy/MM";
				treeMap.put(dateToStr(queryTimeBeginCal.getTime(), timeFormat),
						dateToStr(queryTimeBeginCal.getTime(), timeFormatAbb));
				queryTimeBeginCal.add(Calendar.MONTH, 1); // 加1年
				n = n + 1;
			}
			break;
		case DateUtil.SIZE_YEAR_STEP:
			while (n < n_max && queryTimeBeginCal.before(queryTimeEndCal)) {
				timeFormat = "YYYY";
				timeFormatAbb = "YYYY";
				treeMap.put(dateToStr(queryTimeBeginCal.getTime(), timeFormat),
						dateToStr(queryTimeBeginCal.getTime(), timeFormatAbb));
				queryTimeBeginCal.add(Calendar.YEAR, 1); // 加1年
				n = n + 1;
			}
			break;
		}
		return treeMap;
	}

	/**
	 * 返回日期参数推迟或者提前天数的字符串
	 * 
	 * @param date
	 * @param dayNum
	 * @return
	 */
	public static String dataToStr(Date date, int dayNum) {
		if (null == date) {
			date = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 加入天数
		cal.add(Calendar.DATE, dayNum); // 加1
		return dateToStr(cal.getTime(), DATE_FORMAT);
	}

	/** 日期转的字符串--转换成秒 */
	public static String dateToStrForSecond(Date date) {
		long time = date.getTime();
		// System.currentTimeMillis() 也可以获得时间戳
		// mysq 时间戳是秒只有10位，后三位是毫秒，要做处理
		String dateline = time + "";
		dateline = dateline.substring(0, 10);
		return dateline;
	}

	/** 日期转的字符串--格式化到秒 */
	public static String dateToStrTimestamp(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat(HOUR_MINUTE_TIMESTAMP_FORMAT);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 给指定时间添加 分钟
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		if (null != date) {
			Calendar instance = Calendar.getInstance();
			instance.setTime(date);
			instance.add(Calendar.MINUTE, minute);
			return instance.getTime();
		}
		return null;
	}

	public static Date addSecond(Date date, int Second) {
		if (null != date) {
			Calendar instance = Calendar.getInstance();
			instance.setTime(date);
			instance.add(Calendar.SECOND, Second);
			return instance.getTime();
		}
		return null;
	}

}
