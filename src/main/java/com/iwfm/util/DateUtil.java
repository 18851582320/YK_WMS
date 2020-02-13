package com.iwfm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * ClassName: DateUtil 
 * @Description: 日期处理类
 * @author yk
 * @date 2017年8月1日
 */
public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private final static SimpleDateFormat sdfYYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat sdfYYMMDD = new SimpleDateFormat("yyMMdd");
	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmss");
	private final static SimpleDateFormat sdfHHMMSS = new java.text.SimpleDateFormat("HH:mm:ss");
	
	/**
	 * 
	 * @Title: getMonthFirstDay
	 * @Description: 获取当月第一天
	 * @author yk
	 * @date: 2018年10月29日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	public static String getMonthFirstDay() {
		 Calendar c = Calendar.getInstance();    
		 c.add(Calendar.MONTH, 0);
		 c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		 String first = sdfDay.format(c.getTime());
		 return first;
	}
	
	
	/**
	 * 
	 * @Title: getMonthLastDay
	 * @Description: 获取当月最后一天
	 * @author yk
	 * @date: 2018年10月29日
	 * @param: @return   
	 * @return: String  
	 * @throws:
	 */
	public static String getMonthLastDay() {
		Calendar ca = Calendar.getInstance();   
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = sdfDay.format(ca.getTime());
		return last;
	}
	
	/**
	 * @Title: getSdfTimes
	 * @Description: 获取YYYY格式
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getyyyyMMddHHmmss() {
		return sdfTimes.format(new Date());
	}
	
	/**
	 * @Title: getYear
	 * @Description: 获取YYYY格式
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * @Title: getDay
	 * @Description: 获取YYYY-MM-DD格式
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * @Title: getYYYYMMDD
	 * @Description: 获取YYYYMMDD格式
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getYYYYMMDD(){
		return sdfYYYYMMDD.format(new Date());
	}
	
	/**
	 * @Title:getYYMMDD
	 * @Description: 获取YYMMDD格式日期
	 * @author yk
	 * @date 2018年1月22日
	 * @param @return   
	 * @return String  
	 * @throws
	 */
	public static String getYYMMDD(){
		return sdfYYMMDD.format(new Date());
	}

	/**
	 * 
	 * @Title: getTime
	 * @Description: 获取YYYY-MM-DD HH:mm:ss格式
	 * @author yk
	 * @date 2017年8月1日
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	 * @Title: compareDate
	 * @Description: 日期比较，如果s>=e 返回true 否则返回false
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param s
	 * @param @param e
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}
	
	
	/**
	 * 
	 * @Title: compareDateDayu
	 * @Description: 比较两个日期大小
	 * @author: HB-PC-042
	 * @date: 2018年10月26日
	 * @param: @param s
	 * @param: @param e
	 * @param: @return   
	 * @return: boolean  
	 * @throws:
	 */
	public static boolean compareDateDayu(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >fomatDate(e).getTime();
	}
	
	
	

	/**
	 * @Title: fomatDate
	 * @Description: 格式化日期
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param date
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String fomatDateByDate(Date date){
		return sdfTime.format(new Date());
	}

	/**
	 * @Title: isValidDate
	 * @Description: 校验日期是否合法
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param s
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @Title: getDiffYear
	 * @Description: 时间相减得到年数
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param startTime
	 * @param @param endTime
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			//long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	 
	/**
	 * @Title: getDaySub
	 * @Description: 时间相减得到天数
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param beginDateStr
	 * @param @param endDateStr
	 * @param @return
	 * @return long
	 * @throws
	 */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    /**
	 * @Title: getDiffYear
	 * @Description: 时间相减得到分钟
	 * @author yk
	 * @date 2017年8月1日
	 * @param @param startTime
	 * @param @param endTime
	 * @param @return
	 * @return double
	 * @throws
	 */
	public static double getDiffMinute(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			
			double mins=(double) ((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60));
			return mins;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0.0;
		}
	}
    /**
     * @Title: getAfterDayDate
     * @Description: 得到n天之后的日期
     * @author yk
     * @date 2017年8月1日
     * @param @param days
     * @param @return
     * @return String
     * @throws
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * @Title: getAfterDayWeek
     * @Description: 得到n天之后是周几
     * @author yk
     * @date 2017年8月1日
     * @param @param days
     * @param @return
     * @return String
     * @throws
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }
    /**
     * 
     * @Title:IsTimespinner
     * @Description: 是否时分秒格式 HH:mm:ss
     * @author yk
     * @date 2018年1月31日
     * @param @param timespinner
     * @param @return   
     * @return Boolean  
     * @throws
     */
    public static Boolean IsTimespinner(String timespinner)
    {
    	try {
	    	
	    	Date tmpDate = sdfHHMMSS.parse(timespinner);
			String strTempDate = sdfHHMMSS.format(tmpDate);
	        if (!strTempDate.equals(timespinner)) {
	            return false;
	        }
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	        return false;
	    }
	    return true;
    }
    
    
    public static List<String> getYearFullDay(int year){
        List<String> fullDayList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();// 获得当前日期对象
        for(int i=1;i<=12;i++){
        	cal.clear();// 清除信息
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, i - 1);// 1月从0开始
            cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
            int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int j = 0; j <= (count-1);) {
                if(sdf.format(cal.getTime()).equals(getLastDay(year, i)))
                    break;
                cal.add(Calendar.DAY_OF_MONTH, j == 0 ? +0 : +1);
                j++;
                fullDayList.add(sdf.format(cal.getTime()));
            }
        }
        return fullDayList;
    }
    
    
    
    /**
     * 
     * @Title: getCalendarSetByYear
     * @Description: TODO
     * @author yk
     * @date: 2018年10月22日
     * @param: @param year
     * @param: @return   
     * @return: List<PageData>  
     * @throws:
     */
    public static List<PageData> getCalendarSetByYear(String year){
    	List<PageData> calendarLst=new ArrayList<PageData>();
    	
    	Calendar calendarStart=Calendar.getInstance();//定义一个日历，变量作为年初
    	Calendar calendarEnd=Calendar.getInstance();//定义一个日历，变量作为年末
    	
    	calendarStart.set(Calendar.YEAR,Integer.valueOf(year));
    	calendarStart.set(Calendar.MONTH,0);
    	calendarStart.set(Calendar.DAY_OF_MONTH,1);
    	
    	calendarEnd.set(Calendar.YEAR,Integer.valueOf(year));
    	calendarEnd.set(Calendar.MONTH,11);
    	calendarEnd.set(Calendar.DAY_OF_MONTH,31);
    	
    	PageData pd=new PageData();
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	while(calendarStart.getTime().getTime()<=calendarEnd.getTime().getTime()) {
    		
    		pd=new PageData();
    		pd.put("calendarYear", year);
    		pd.put("calendarDay",format.format(calendarStart.getTime()));
    		
    		
    		switch (calendarStart.get(Calendar.DAY_OF_WEEK)) {
			case 1:
				pd.put("calendarWeek","星期日");
				pd.put("isRest","1");
				break;
			case 2:
				pd.put("calendarWeek","星期一");
				pd.put("isRest","0");
				break;
			case 3:
				pd.put("calendarWeek","星期二");
				pd.put("isRest","0");
				break;
			case 4:
				pd.put("calendarWeek","星期三");
				pd.put("isRest","0");
				break;
			case 5:
				pd.put("calendarWeek","星期四");
				pd.put("isRest","0");
				break;
			case 6:
				pd.put("calendarWeek","星期五");
				pd.put("isRest","0");
				break;
			case 7:
				pd.put("calendarWeek","星期六");
				pd.put("isRest","1");
				break;

			default:
				break;
			}
    		
    		calendarLst.add(pd);
    		calendarStart.add(Calendar.DAY_OF_MONTH,1);
    		
    		
    	}
    	return calendarLst;
    	
    }
    
    
    
    
    /**
     * 根据年份获取一年的每一天 
     * @author AaronKevin
     * @param year
     * @param month
     * @return
     */
    public static String getLastDay(int year,int month){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return sdf.format(cal.getTime());
    }
    
    /**
     * 日期转星期
     * @author AaronKevin
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
