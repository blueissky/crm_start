package net.qingsoft.crm.util;

import java.util.Calendar;

public class SystemTime {

	public SystemTime() {
	}
	Calendar calendar=Calendar.getInstance();//获得当前时间
	/**
	 * 获得系统当前时间(年-月-日- 小时:分钟)
	 * @return
	 */
	public String getSystemTime(){
		int year=calendar.get(Calendar.YEAR);//年
		int month=calendar.get(Calendar.MONTH)+1;//月份需要+1月份从0开始
		int date=calendar.get(Calendar.DATE);//日
		int hour=calendar.get(Calendar.HOUR_OF_DAY);//24小时制
		int minute=calendar.get(Calendar.MINUTE);//分钟
		int  second=calendar.get(Calendar.SECOND);//秒
		String currentTime=year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
		return currentTime;
	}
	/**
	 * 获得当前系统年份
	 * @return
	 */
	public int getYear(){
		int year=calendar.get(Calendar.YEAR);//年;
		return year;
	}
}
