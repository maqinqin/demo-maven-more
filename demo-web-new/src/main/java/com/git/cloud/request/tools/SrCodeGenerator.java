package com.git.cloud.request.tools;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.foundation.common.WebApplicationManager;
import com.git.cloud.foundation.util.DateUtil;
import com.git.cloud.request.model.po.BmSrPo;
import com.git.cloud.request.service.IRequestBaseService;
import com.git.cloud.resmgt.common.model.po.CmDevicePo;
import com.git.cloud.resmgt.common.service.ICmDeviceService;

/**
 * 服务单号生成类
 * @ClassName:SrCodeGenerator
 * @Description:TODO
 * @author sunhailong
 * @date 2014-10-28 下午6:37:17
 */
public class SrCodeGenerator {
	private static Logger logger = LoggerFactory.getLogger(SrCodeGenerator.class);
	private static SrCodeGenerator srCodeGenerator = null;
	private String hisDate = "";
	private int orderNum = 1;
	private String orderNum1 = null;
	private String initString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String[] sr3 = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	// 私有的构造函数
	public SrCodeGenerator () {}
	
	public static synchronized SrCodeGenerator getInstance() {
		if(srCodeGenerator == null) {
			srCodeGenerator = new SrCodeGenerator();
		}
		return srCodeGenerator;
	}
	
	public synchronized String getSRID(String srTypeCode) {
		String today = DateUtil.getCurrentDataString("yyyyMMdd");
		if(!"".equals(hisDate) && !today.equals(hisDate)) {
			orderNum = 1;
		} else { // 从数据库查询最大值
			IRequestBaseService requestBaseServiceImpl = (IRequestBaseService) WebApplicationManager.getBean("requestBaseServiceImpl");
			if(requestBaseServiceImpl == null) {
				orderNum = 1;
			} else {
				BmSrPo bmSr = null;
				try {
					bmSr = requestBaseServiceImpl.findBmSrNewestRecord();
				} catch (RollbackableBizException e) {
					logger.error("异常exception",e);
				}
				if(bmSr == null) {
					orderNum = 1;
				} else {
					String srCode = bmSr.getSrCode();
					String dateStr = srCode.substring(srCode.length()-12, srCode.length()-4);
					String orderStr = srCode.substring(srCode.length()-4);
					if(today.equals(dateStr)) {
						orderNum = Integer.valueOf(orderStr) + 1;
					} else {
						orderNum = 1;
					}
				}
			}
		}
		hisDate = today;
		return "SR" + srTypeCode + today + String.format("%1$04d", orderNum);
	}
	public synchronized String getNextNum() throws Exception{
	//获取当前日期
		String today = DateUtil.getCurrentDataString("yyMMdd");
		/*if(today.equals(hisDate) && !orderNum1.equals("00")){
			orderNum1 = getVmSign(today,orderNum1);
		}else{*/
			 // 从数据库查询最大值
			ICmDeviceService cmDeviceServiceImpl = (ICmDeviceService) WebApplicationManager.getBean("cmDeviceServiceImpl");
			if(cmDeviceServiceImpl == null){
				orderNum1 = "00";
			}else{
				CmDevicePo cmDevicePo = null;
				try {
					//获取最新的设备信息
					cmDevicePo = cmDeviceServiceImpl.findNewDevcieByHostId();
				} catch (RollbackableBizException e) {
					logger.error("异常exception",e);
				}
				if(cmDevicePo == null) {
					orderNum1 = "00";
				}else {
					SimpleDateFormat time=new SimpleDateFormat("yyMMdd"); 
					String createTime = time.format(cmDevicePo.getCreateDateTime());
					if(createTime.equals(today)){
					//获取设备的后两位
					String sign = cmDevicePo.getDeviceName();	//从最新的服务申请中获取
					orderNum1 = getVmSign(today,sign);
					}else{
						orderNum1 = "00";
					}
				}
			}
		/*	hisDate = today;
		}*/
		return orderNum1;
	}
	public String getVmSign(String today,String sign) throws Exception{
		if(sign.length()==1){						//一位数，要补齐
			sign="0"+sign;
		}
		String firstNum = sign.substring(0,1);		//获取到第一个字符
		String secondNum = sign.substring(1);		//获取第二个字符
		if(secondNum.equals("Z") && firstNum.equals("Z")){
			logger.error("虚拟机创建今日已经达到上限！");
			 throw new Exception("虚拟机创建今日已经达到上限！");
		}else if(secondNum.equals("Z") && !firstNum.equals("Z")){
			secondNum="0";
			int inta = initString.indexOf(firstNum);
			orderNum1 = initString.substring(inta+1, inta+2)+secondNum;
			logger.info(today+"，创建虚拟机序号："+orderNum1);
			System.out.println("orderNum1:"+orderNum1);
		}else{
			int intb = initString.indexOf(secondNum);
			orderNum1 = firstNum+initString.substring(intb+1, intb+2);
			logger.info(today+"，创建虚拟机序号："+orderNum1);
			System.out.println("orderNum1:"+orderNum1);
		}
		return orderNum1;
	}
	
	public static void main(String[] args) throws Exception {
		SrCodeGenerator te = new SrCodeGenerator();
		//te.getNextNum();
		//te.test();
		//te.test2(4);
		te.test3("AB");

	}
	public String test2(int num){
		int b = num/36;
		int c = num%36;
		String d = sr3[b].toString()+sr3[c];
		System.out.println("test2:"+d);
		return d;
	}
	public String test()throws Exception{
		//计算数值的公式为(n-1)*36+Y,A是11，测试数据如下AB--372，9A--335
		String srCode = "AB";				//从最新的服务申请中获取
		if(srCode.length()==1){				//一位数，要补齐
			srCode="0"+srCode;
		}
		String a = srCode.substring(0,1);	//获取到第一个字符
		String b = srCode.substring(1);		//获取第二个字符
		if(b.equals("Z") && a.equals("Z")){
			logger.error("虚拟机创建今日已经达到上限！");
			 throw new Exception("虚拟机创建今日已经达到上限！");
		}else if(b.equals("Z") && !a.equals("Z")){
			b="0";
			int inta = initString.indexOf(a);
			orderNum1 = initString.substring(inta+1, inta+2)+b;
			System.out.println("orderNum1:"+orderNum1);
		}else{
			int intb = initString.indexOf(b);
			orderNum1 = a+initString.substring(intb+1, intb+2);
			System.out.println("orderNum1:"+orderNum1);
		}
		return orderNum1;
	}
	public String test3(String sign){
		String firstNum = sign.substring(0,1);	//获取到第一个字符
		String secondNum = sign.substring(1);		//获取第二个字符
		if(secondNum.equals("Z") && firstNum.equals("Z")){
			logger.error("虚拟机创建今日已经达到上限！");
		}else if(secondNum.equals("Z") && !firstNum.equals("Z")){
			secondNum="0";
			int inta = initString.indexOf(firstNum);
			orderNum1 = initString.substring(inta+1, inta+2)+secondNum;
			System.out.println("orderNum1:"+orderNum1);
		}else{
			int intb = initString.indexOf(secondNum);
			orderNum1 = firstNum+initString.substring(intb+1, intb+2);
			System.out.println("orderNum1:"+orderNum1);
		}
		return orderNum1;
	}
}
