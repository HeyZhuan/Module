package com.Fee.common.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.Fee.common.log.LogUtils;

public class TimeUtils {
	private static Logger log = LoggerFactory.getLogger(TimeUtils.class);
	/**
	 * 获取当前10 位的时间戳
	 * @return
	 */
	public static int getTimeStamp(){
		return (int) (System.currentTimeMillis()/1000);
	}
	
	/**
	 * 获取当前标准时间   格式:yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getStandardTime(Date date){
		if(date==null){
			date =new Date();
		}
		
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(date);
	}
	
	
	/**
	 * 获取对应格式的时间   
	 * 
	 * @return
	 */
	public static String getTimeByFormate(Date date,String format){
		if(date==null){
			date =new Date();
		}
		
		if(StringUtils.isBlank(format)){
			format="yyyy-MM-dd HH:mm:ss";
		}
		
		SimpleDateFormat sd=new SimpleDateFormat(format);
		return sd.format(date);
	}
	
	/**
	 * 获取对应格式的时间   
	 * 
	 * @return
	 */
	public static Date getTimeByFormate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today=null;
		try {
			today = sdf.parse(date);
		} catch (ParseException e) {
			log.error("日期转换yyyy-MM-dd HH:mm:ss错误,time:"+date);
		}
		return today;
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 字符串时间转换为时间戳
	 * @param time
	 * @return
	 */
	public static Long timeStrToLong(String time){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sd.parse(time);
			return date.getTime()/1000;
		} catch (ParseException e) {
			LogUtils.sendExceptionLog(log, "字符串时间转换为时间戳失败:"+time, e);
		}
		return null;
	}
	
	/**
	 * 获取增加了几个月后的时间
	 * @param time
	 * @return
	 */
	public static int getAddMouthTime(int time,int num){
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date(time*1000L));
		
		cal.add(Calendar.MONTH, num);
		return (int)(cal.getTime().getTime()/1000);
	}
	
	/**
	 * 获取增加了几个小时后的时间
	 * @param time
	 * @return
	 */
	public static int getAddHoutTime(int time,int num){
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date(time*1000L));
		
		cal.add(Calendar.HOUR_OF_DAY, num);
		return (int)(cal.getTime().getTime()/1000);
	}
	
	/**
	 * 获取增加了几天后的时间
	 * @param time
	 * @return
	 */
	public static int getAddDayTime(int time,int num){
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date(time*1000L));
		
		cal.add(Calendar.DATE, num);
		return (int)(cal.getTime().getTime()/1000);
	}
	
	/**
	 * 增加天数
	 * @param date 时间
	 * @param num  需要增加的天数
	 * @return
	 */
	public static Date getAddDayTime(Date date,int num){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, num);
		return calendar.getTime();
	}
	/**
	 * 获取10 位时间戳的具体格式时间
	 * @param time
	 */
	public static String getStringFormatTime(Long time){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(new Date(time));
	}
	
	public static void main(String[] args) {
		String baseTime = TimeUtils.getTimeByFormate(new Date(), "yyyy-MM-dd");
		Long start=TimeUtils.timeStrToLong(baseTime+" 00:00:00");
		Long end=TimeUtils.timeStrToLong(baseTime+" 23:59:59");
		
		System.out.println(start+","+end);
	}
}
