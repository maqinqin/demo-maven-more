package com.git.cloud.appmgt.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateIntervalUtil {

    private static final Integer DEFAULT_INTERVAL = 30;
    private static final Integer DEFAULT_DATE_OFFSET = 1;
    private Integer customDateOffset = null;
    private Integer customInterval = null;
    private boolean offsetEnable = false;
    private Calendar startCal = null;
    private Calendar endCal = null;
    private Calendar singleCal = null;
    private boolean isDayInterval = true;
//    static SimpleDateFormat  tt = new SimpleDateFormat("yyyy-MM-dd");
    static String tt = "yyyy-MM-dd";

    public DateIntervalUtil() {
    }

    public DateIntervalUtil(Date singleDate) {
        singleCal = Calendar.getInstance();
        if (singleDate != null) singleCal.setTime(singleDate);
    }

    public DateIntervalUtil(Date startDate, Date endDate) {
        startCal = Calendar.getInstance();
        endCal = Calendar.getInstance();
        if (startDate != null && endDate != null) {
            startCal.setTime(startDate);
            endCal.setTime(endDate);
        } else if (startDate != null && endDate == null) {
            startCal.setTime(startDate);
            endCal.setTime(startDate);
        } else if (startDate == null && endDate != null) {
            startCal.setTime(endDate);
            endCal.setTime(endDate);
        }
    }

    public java.lang.Object[] getIntervalResult() throws ParseException {
        java.lang.Object[] result = null;

        if (startCal.equals(endCal)) {
            result = this.fullMonthInterval(startCal);
        } else {
            result = this.periodTimeInterval(startCal, endCal);
        }
        if (offsetEnable) {
            return this.processOffset(result);
        }
        return result;
    }

    private Calendar getIntervalStart(Calendar cal) {
        cal.add(Calendar.DAY_OF_MONTH, -(customInterval == null ? DEFAULT_INTERVAL : customInterval ));
        return cal;
    }

    private Calendar getIntervalStart(Calendar cal, int interval) {
        cal.add(Calendar.DAY_OF_MONTH, -interval);
        return cal;
    }

    private Calendar getIntervalEnd(Calendar cal) {
        cal.add(Calendar.DAY_OF_MONTH, 0);
//        cal.add(Calendar.DAY_OF_MONTH, (customInterval == null ? DEFAULT_INTERVAL : customInterval) / 2);
        return cal;
    }

    private Calendar getIntervalEnd(Calendar cal, int interval) {
        cal.add(Calendar.DAY_OF_MONTH,interval);
        return cal;
    }

    private java.lang.Object[] fullMonthInterval(Calendar cal) throws ParseException {
        Calendar start = getIntervalStart((Calendar) cal.clone());
        Calendar end = getIntervalEnd((Calendar) cal.clone());
        if (isDayInterval) {
            return generateDayInterval(start, end);
        } else {
            return generateMonthInterval(start, end);
        }

    }

    private java.lang.Object[] periodTimeInterval(Calendar start, Calendar end) throws ParseException {
        if (isDayInterval) {
           return generateDayInterval(start, end);
        } else {
            return generateMonthInterval(start, end);
        }
    }

    private java.lang.Object[] processOffset(java.lang.Object[] arr) throws ParseException {
        if (arr == null || arr.length == 0) return null;

        int offset = DEFAULT_DATE_OFFSET;
        if (customDateOffset != null) offset = customDateOffset;

        List<Object> sourceList = Arrays.asList(arr);
        DateFormat df = new SimpleDateFormat(tt);
        Date firstElement = df.parse(arr[0].toString());
        Date lastElement = df.parse(arr[arr.length-1].toString());

        Calendar firstCal = Calendar.getInstance();
        firstCal.setTime(firstElement);
        Calendar lastCal = Calendar.getInstance();
        lastCal.setTime(lastElement);

        Calendar c1 = getIntervalStart((Calendar) firstCal.clone(), offset);
        List<Object> beforeList = Arrays.asList(periodTimeInterval(c1, firstCal));
        Calendar c2 = getIntervalEnd((Calendar) lastCal.clone(), offset);
        List<Object> afterList = Arrays.asList(periodTimeInterval(lastCal, c2));

        List<Object> total = new ArrayList<Object>();
        total.addAll(beforeList);
        total.addAll(sourceList);
        total.addAll(afterList);

        return total.toArray();
    }


    private java.lang.Object[] generateDayInterval(Calendar start, Calendar end) {
        List<String> list = new ArrayList<String>();
        int i = 0;
        while (!start.equals(end)) {
            start.add(Calendar.DAY_OF_MONTH, i);
            list.add(new SimpleDateFormat(tt).format(start.getTime()));
            if (i == 0) i++;
        }
        return list.toArray();
    }

    private java.lang.Object[] generateMonthInterval(Calendar start, Calendar end) throws ParseException {
      /* List<String> list = new ArrayList<String>();
        while (!((end.get(Calendar.YEAR) == start.get(Calendar.YEAR))
        		&& (end.get(Calendar.MONTH) + 1 == start.get(Calendar.MONTH)))) {
        	list.add(tt.format(start.getTime()));
        	start.add(Calendar.MONTH, 1);
        }
        return list.toArray();*/
    	
    	/*modify by wmy 2016-12-10*/
    	ArrayList<String> result = new ArrayList<String>();
	    start.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), 1);
	    end.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), 2);
	    Calendar curr = start;
	    while (curr.before(end)) {
	     result.add(new SimpleDateFormat(tt).format(curr.getTime()));
	     curr.add(Calendar.MONTH, 1);
	    }
	    return result.toArray();
		    
    }
    
    /**
     * 
      * 去年的现在 
      * @return String    返回类型
     */
    public static String getNowOfLastYear() {
        // Date Format will be display
        SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00");
        GregorianCalendar aGregorianCalendar = new GregorianCalendar();
        // Get last month GregorianCalendar object
        aGregorianCalendar.set(Calendar.YEAR, aGregorianCalendar
                .get(Calendar.YEAR) - 1);
        // Format the date to get year and month
        String currentYearAndMonth = aSimpleDateFormat
                .format(aGregorianCalendar.getTime());
        return currentYearAndMonth;
    }
    /**
     * 
      * 去年的这个月
      * @return String    返回类型
     */
    public static String getThisMonthOfLastYear() {
        // Date Format will be display
        SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyy-MM-01 00:00");
        GregorianCalendar aGregorianCalendar = new GregorianCalendar();
        // Get last month GregorianCalendar object
        aGregorianCalendar.set(Calendar.YEAR, aGregorianCalendar
                .get(Calendar.YEAR) - 1);
        // Format the date to get year and month
        String currentYearAndMonth = aSimpleDateFormat
                .format(aGregorianCalendar.getTime());
        return currentYearAndMonth;
    }

    public boolean isOffsetEnable() {
        return offsetEnable;
    }

    public void setOffsetEnable(boolean offsetEnable) {
        this.offsetEnable = offsetEnable;
    }

    public Integer getCustomDateOffset() {
        return customDateOffset;
    }

    public void setCustomDateOffset(Integer customDateOffset) {
        this.customDateOffset = customDateOffset;
    }

    public Integer getCustomInterval() {
        return customInterval;
    }

    public void setCustomInterval(Integer customInterval) {
        this.customInterval = customInterval;
    }

    public boolean isDayInterval() {
        return isDayInterval;
    }

    public void setDayInterval(boolean dayInterval) {
        isDayInterval = dayInterval;
    }
}