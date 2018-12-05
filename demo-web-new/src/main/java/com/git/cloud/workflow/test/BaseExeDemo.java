package com.git.cloud.workflow.test;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class BaseExeDemo {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String apiExe(HashMap<String,Object> params){
		Map<String,Object> callBackParams = new HashMap<String, Object>();
		try{
//			if(1>0)
//				throw new Exception("异常00000000000");
			Thread.sleep(10000);
			callBackParams.put("checkCode", "0000");
			StringBuffer sb = new StringBuffer();
			for(int i = 0  ; i < 10 ; i++){
				sb.append("我就是一个测试API执行的方法.我没有返回结果.hohohoho");
//				sb.append("");
			}
			callBackParams.put("exeMsg", sb.toString());
			Map temp = new HashMap();
			temp.put("PA", "i am new PA");
			callBackParams.put("exeParams", temp);
		}catch(Exception e){
			
		}
		return JSON.toJSONString(callBackParams);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String commandExe(HashMap<String,Object> params){
		Map<String,Object> callBackParams = new HashMap<String, Object>();
		try{
			Thread.sleep(10000);
			callBackParams.put("checkCode", "00000");
			StringBuffer sb = new StringBuffer();
			for(int i = 0  ; i < 500 ; i++){
				sb.append("我就是一个测试命令执行的方法.我没有返回结果.hohohoho");
			}
			callBackParams.put("exeMsg", null);
//			callBackParams.put("exeMsg", "我就是一个测试命令执行的方法.我没有返回结果.hohohoho");
			Map temp = new HashMap();
			temp.put("PA", "i am new PA");
			callBackParams.put("exeParams", temp);
		}catch(Exception e){
			
		}
		return JSON.toJSONString(callBackParams);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String scriptExe(HashMap<String,Object> params){
		Map<String,Object> callBackParams = new HashMap<String, Object>();
		try{
			Thread.sleep(10000);
			callBackParams.put("checkCode", "0000");
			StringBuffer sb = new StringBuffer();
			for(int i = 0  ; i < 500 ; i++){
				sb.append("我就是一个测试Script执行的方法.我没有返回结果.hohohoho");
			}
			callBackParams.put("exeMsg", sb.toString());
//			callBackParams.put("exeMsg", "我就是一个测试命令执行的方法.我没有返回结果.hohohoho");
			Map temp = new HashMap();
			temp.put("PA", "i am new PA");
			callBackParams.put("exeParams", temp);
		}catch(Exception e){
			
		}
		return JSON.toJSONString(callBackParams);
	}
		
}
