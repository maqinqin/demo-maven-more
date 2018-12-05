package com.git.cloud.request.tools;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 * @ClassName:SrDateUtil
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class SrDateUtil {
	private static Logger logger = LoggerFactory.getLogger(SrDateUtil.class);
//	private static DateFormat matterday = new SimpleDateFormat("yyyy-MM-dd");
//	private static SimpleDateFormat mattertime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String matterday = "yyyy-MM-dd";
	private static String mattertime = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 时间格式转换
	 * @author djq
	 * @return Timestamp
	 */
	public static Timestamp getSrForday(String time){
		try {
			DateFormat df = new SimpleDateFormat(matterday);
			df.setLenient(false);
//			matterday.setLenient(false);//合法日期校验
				Timestamp stTime = new Timestamp(df.parse(time).getTime());
				return stTime;
		} catch (ParseException e) {
			logger.error("日期转换异常:"+e);
		}
		return null;
	}
	
	/**
	 * 时间格式转换
	 * @author djq
	 * @return Timestamp
	 */
	public static Timestamp getSrFortime(Date data){
		return Timestamp.valueOf(new SimpleDateFormat(mattertime).format(data));
	}
}
