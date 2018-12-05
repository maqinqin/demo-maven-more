package com.git.cloud.appmgt.tools;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.sql.CLOB;

/**
 * 输出工具类
 * @author djq
 *
 */
public class OutPut {
	
	private static Logger logger = LoggerFactory.getLogger(OutPut.class);
	
	/**
	 * 输出json(text/x-json)内容
	 * @param response
	 * @param result
	 * @throws IOException
	 */
	public static void outJsonString(HttpServletResponse response,String result) throws IOException{
		response.setContentType("text/x-json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		//System.out.println("json = " + result);
		
		response.getWriter().write(result);
	}
	
	
	
	
	/**
	 * 输出json(application/json)内容
	 * @param response
	 * @param result
	 * @throws IOException
	 */
	public static void outJsonAppString(HttpServletResponse response,String result) throws IOException{
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		//System.out.println("json = " + result);
		
		response.getWriter().write(result);
	}
	
	/**
	 * 对于ajaxForm提交的表单的返回值
	 * @param response
	 * @param result
	 * @throws IOException
	 */
	public static void outJsonHtmlForAjaxForm(HttpServletResponse response,String result) throws IOException{
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().write(result);
	}
	
	/**
	 * 格式化结果集的日期
	 * @param resls
	 * @return
	 * @throws SQLException 
	 */
	public static List<Map>formatResult(List<Map>resls) throws SQLException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map> resLsContent=new ArrayList();
		if(!resls.isEmpty()){
			for(int i=0;i<resls.size();i++){
				Map res_map=resls.get(i);
				Map resNewContent=new HashMap();
				Set keys=res_map.keySet();
				Iterator keyls=keys.iterator();
				while(keyls.hasNext()){
					
					String key=String.valueOf(keyls.next());
					Object item=res_map.get(key);
					if(item!=null){
						
						if(java.sql.Timestamp.class.isInstance(item) || oracle.sql.TIMESTAMP.class.isInstance(item)){
							String itemDate="";
							if(oracle.sql.TIMESTAMP.class.isInstance(item)){
								oracle.sql.TIMESTAMP newitem=( oracle.sql.TIMESTAMP) item;
								itemDate=sdf.format(newitem.timestampValue());
							}else{
								itemDate=sdf.format(item);
							}
							
						
							resNewContent.put(key, itemDate);
						}else{
							resNewContent.put(key, item);
						}
					}else{
						resNewContent.put(key, item);
					}
				}
				resLsContent.add(resNewContent);
			}
		}
		
		return resLsContent;
	}
	
	/***
	 * 将Map转换成String {root:[{key:,value:,}]};的格式
	 * @param sourceMap
	 * @return
	 */
	public static String mapToJsonString(Map sourceMap){
		StringBuffer result=new StringBuffer();
		result.append("{\"root\":[");
		
		if(!sourceMap.isEmpty()){
			Set keys=sourceMap.keySet();
			
			Iterator keyls=keys.iterator();
			for(int i=0;i<keys.size();i++){
				
				String key=String.valueOf(keyls.next());
				if(sourceMap.get(key)!=null){
					String value=String.valueOf(sourceMap.get(key)) ;
					result.append("{\"key\":\""+key.toUpperCase()+"\",\"value\":\""+value+"\"}");
					if(i+1<keys.size()){
						result.append(",");
					}
				}
				
			}
		}
		
		result.append("]}");
		return result.toString();
	}
	
	
	/**
	 * 将Map转换成String {root:[{key:,value:,}]};的格式,可排序 的
	 * @param sourceMap
	 * @param sort true为可排序的
	 * @return
	 */
	public static String mapToJsonString(Map sourceMap,boolean sort){
		StringBuffer result=new StringBuffer();
		
		result.append("{\"root\":[");
		
		if(!sourceMap.isEmpty()){
			Set keys=sourceMap.keySet();
			
			List<String>lsKeys=new ArrayList();
			Object[]strKeys= keys.toArray();
			for(int j=0;j<strKeys.length;j++){
				lsKeys.add(String.valueOf(strKeys[j]));
			}
			
			
		
			if(sort){
				Collections.sort(lsKeys);
			}
			for(int i=0;i<lsKeys.size();i++){
				
				String key=String.valueOf(lsKeys.get(i));
				if(sourceMap.get(key)!=null){
					String value=String.valueOf(sourceMap.get(key)) ;
					result.append("{\"key\":\""+key.toUpperCase()+"\",\"value\":\""+value+"\"}");
					if(i+1<keys.size()){
						result.append(",");
					}
				}
				
			}
		}
		
		result.append("]}");
		return result.toString();
	}
	
	
	
	
	/**
	 * map key值为String的排序
	 * @param sourceMap
	 * @return 排序后的key值
	 */
	public static List<String> mapSort(Map sourceMap){
		List<String>lsKeys=new ArrayList();
	
		if(!sourceMap.isEmpty()){
			Set keys=sourceMap.keySet();
			
			Object[]strKeys= keys.toArray();
			for(int j=0;j<strKeys.length;j++){
				lsKeys.add(String.valueOf(strKeys[j]));
			}
			
			Collections.sort(lsKeys);
			
	}
		return lsKeys;
}		
	
	/**
	 * 将Map转换成String {root:[{key:,value:,}]};的格式,可排序 的
	 * @param sourceMap
	 * @param sort true为可排序的
	 * @return
	 */
	public static String mapToJsonStringNumSort(Map sourceMap,boolean sort){
		StringBuffer result=new StringBuffer();
		
		result.append("{\"root\":[");
		
		if(!sourceMap.isEmpty()){
			Set keys=sourceMap.keySet();
			
			List<Integer>lsKeys=new ArrayList();
			Object[]strKeys= keys.toArray();
			for(int j=0;j<strKeys.length;j++){
				lsKeys.add(Integer.valueOf(String.valueOf(strKeys[j])));
			}
			
			if(sort){
				Collections.sort(lsKeys);
			}
			for(int i=0;i<lsKeys.size();i++){
				
				String key=String.valueOf(lsKeys.get(i));
				if(sourceMap.get(key)!=null){
					String value=String.valueOf(sourceMap.get(key)) ;
					result.append("{\"key\":\""+key.toUpperCase()+"\",\"value\":\""+value+"\"}");
					if(i+1<keys.size()){
						result.append(",");
					}
				}
				
			}
		}
		
		result.append("]}");
		return result.toString();
	}
	
	
	/***
	 * 把列表中的cloName替换成字典里的中文
	 * @param v
	 * @param cloName
	 * @param directoroy
	 * @return
	 */
	public static List<Map> replaceValueByMap(List<Map> v,String cloName,Map directoroy){
		if(directoroy!=null && cloName!=null &&cloName.length()>0){
		try{
			if(!v.isEmpty()){
				Iterator itV=v.iterator();
				while(itV.hasNext()){
					Map map=(Map)itV.next();
					if(map.containsKey(cloName)){
						Object objSor=map.get(cloName);
						if(objSor!=null && !"".equals(objSor)){
							String sorStr=objSor.toString();
							if(directoroy.containsKey(sorStr)){
								sorStr= directoroy.get(sorStr).toString();
								if(!checkNull(sorStr)){
									map.remove(cloName);
									map.put(cloName,sorStr);
								}
							}
							
						}
						
					}
					
				}
			}
		}catch(Throwable e){
			logger.error("cloName替换成字典里的中文异常",e);
		}
		}
		return v; 
	}
	
	
	
	/***
	 * 把列表中的cloName中在字典里的中文加入列表以cloName+“_name”为名
	 * @param v
	 * @param cloName
	 * @param directoroy
	 * @return
	 */
	public static List<Map> addValueByMap(List<Map> v,String[] cloNames,Map[] directoroys,Integer count){
		if(count==null){
			count=v.size();
		}else if(count>v.size()){
			count=v.size();
		}
		if(directoroys!=null && directoroys.length>0 && cloNames!=null &&cloNames.length>0){
			if(directoroys.length!=cloNames.length){
				return v;
			}
			if(!v.isEmpty()){
				
				Iterator itV=v.iterator();
				for(int j=0;j<count;j++){
					Map map=(Map)itV.next();
					for(int i=0;i<cloNames.length;i++){
						if(map.containsKey(cloNames[i])){
							Object objSor=map.get(cloNames[i]);
							if(objSor!=null && !"".equals(objSor)){
								String sorStr=objSor.toString();
								if(directoroys[i].containsKey(sorStr)){
									sorStr=directoroys[i].get(sorStr).toString();
									if(!checkNull(sorStr)){
										map.put(cloNames[i]+"_NAME",sorStr);
									}
								}
								
							}
							
						}
					}
					
					
				}
			}
		}
			
		return v; 
	}
	
	
	
	/**
	 * 错误信息的json字符串
	 * @return
	 */
	public static String getErrorJsonStr(){
		return "{\"result\":\"error\"}";
	}
	
	/**
	 * 返回"{result:status,msg:msg}"的字符串
	 * @param status 状态
	 * @param msg 返回的消息
	 * @return
	 */
	public static String getJsonStrManual(String status,String msg){
		return "{\"result\":\""+status+"\",\"msg\":\""+msg+"\"}";
	}
	
	
	/**
	 * 错误信息的json字符串
	 * @return
	 */
	public static String getSuccessJsonStr(){
		return "{\"result\":\"success\"}";
	}
	
	
	public static String getSuccessJsonStr(String content){
		return "{\"result\":\"success\",\"otherContent\":\""+content+"\"}";
	}	
	
	public static String getIdJsonStr(String content){
		return "{\"result\":\"success\",\"id\":\""+content+"\"}";
	}	
	
	
	/**
	 * 把content内容拼成jqGrid支持的字符串
	 * @param content
	 * @return
	 */
	public static String getPageJson(List<Map> content,int pageSize,int currentPage){
	
		int totalCount = 0;
		int n = 1;
		List<Map> resultV=new ArrayList();
		Iterator it = content.iterator();
		totalCount = content.size();
		while (it.hasNext()) {
			Map row = (Map) it.next();
			if (n >= ((currentPage - 1) * pageSize + 1)
					&& n <= (currentPage * pageSize)) {
				resultV.add(row);
			}
			n++;
		}
		StringBuffer result = new StringBuffer();
		result.append("{\"pageCount\":\""
				+ (totalCount % pageSize == 0 ? totalCount / pageSize
						: totalCount / pageSize + 1)
				+ "\",\"totalCount\":\"" + totalCount
				+ "\",\"currentPage\":\"" + currentPage + "\",\"data\":");
		result.append(Vector2JsonString(resultV,null));
		result.append("}");
		return result.toString();
	}
	
	/**
	 * 检查是否为空
	 * @param item
	 * @return
	 */
	public static boolean checkNull(String item){
		if(item!=null && !"".equals(item) && !"null".equals(item)){
			return false;
		}
		return true;
	}
	
	/**
	 * 以root为根得到没有列表Json串
	 * @param v
	 * @return
	 */
	public static String getListJson(List<Map> v,Integer count){
		StringBuffer sb = new StringBuffer();
		if(count==null){
			count=v.size();
		}
		sb.append("{\"total\":"+count+",\"root\":");
		sb.append(Vector2JsonString(v,count));
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 生成指定的以rootName为根的Json串但不完整，生成的串如："rootName":[{},{}]
	 * @param v
	 * @param count
	 * @param rootName
	 * @return
	 */
	public static String getSpecialJson(List<Map> v,Integer count,String rootName){
		StringBuffer sb = new StringBuffer();
		if(count==null){
			count=v.size();
		}
		sb.append("\""+rootName+"\":");
		sb.append(Vector2JsonString(v,count));
		return sb.toString();
	}
	
	/**
	 * 从parentV中减去sbV中相同的sameContent
	 * @param parentV
	 * @param sbV
	 * @return
	 */
	public static Vector minusVector(Vector parentV,Vector sbV,String parentKey,String subKey){
		
		if(parentKey==null || subKey==null || parentV==null ||parentV.size()<=0||sbV==null||sbV.size()<=0){
			return parentV;
		}
		Iterator itParent = parentV.iterator();
		
		while(itParent.hasNext()){
			Map mapParent = (Map) itParent.next();
			String parentCont=(String) mapParent.get(parentKey);
			Iterator itSbv=sbV.iterator();
			while(itSbv.hasNext()){
					Map sbMap = (Map) itSbv.next();
					String subContent=(String)sbMap.get(subKey);
					if(parentCont.equals(subContent)){
						itParent.remove();
					}
				}
			}
		
		return parentV;
	}
	
	/**
	 *将Vector转换成Json字符串
	 * @param v
	 * @return
	 */
	public static  String Vector2JsonString(List<Map> v,Integer count) {
		StringBuffer sb = new StringBuffer();
		if(count==null){
			count=v.size();
		}else if(count>v.size()){
			count=v.size();
		}
		sb.append("[");
		
		for(int i=0;i<count;i++) {
			sb.append("{");
			Map map = (Map) v.get(i);
			Iterator it2 = map.keySet().iterator();
			while (it2.hasNext()) {
				String name = (String) it2.next();
				String value="";
				if(map.get(name)!=null){
					value = map.get(name).toString();
				}
				
				sb.append("\"");
				sb.append(name.toUpperCase());
				sb.append("\":\"");
				value = value.replaceAll("\\\\", "\\\\\\\\");//把\换成\\
				value = value.replaceAll("\"", "\\\\\"");//把"换成\"
				value = value.replaceAll("\n", "");//把\n换成
				sb.append(value);
				sb.append("\",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			
			if( i+1<count){
				sb.append("},");
			}else{
				sb.append("}");
			}
			
		}
		
		
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * @param v
	 * @return
	 */
	public static  String objectToJson(List<Map> v,Integer count) {
		StringBuffer sb = new StringBuffer();
		if(count==null){
			count=v.size();
		}else if(count>v.size()){
			count=v.size();
		}
		sb.append("[");
		Iterator it = v.iterator();
		
		for(int i=0;i<count;i++) {
			sb.append("{");
			Map map = (Map) it.next();
			Iterator it2 = map.keySet().iterator();
			while (it2.hasNext()) {
				String name = (String) it2.next();
				String value="";
				if(map.get(name) instanceof CLOB)
				{
					value = clob2String((CLOB)map.get(name));
				}
				else if(map.get(name)!=null){
					value = map.get(name).toString();
				}
				sb.append("\"");
				sb.append(name.toLowerCase());
				sb.append("\":\"");
				value = value.replaceAll("\\\\", "\\\\\\\\");//把\换成\\
				value = value.replaceAll("\"", "\\\\\"");//把"换成\"
				value = value.replaceAll("\n", "");//把\n换成
				sb.append(value);
				sb.append("\",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			
			if( i+1<count){
				sb.append("},");
			}else{
				sb.append("}");
			}
			
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * clob 字段转String
	 */
	public final static String clob2String(CLOB clob)
	{
		if(clob==null)
		{
			return null;
		}
		StringBuffer sb = new StringBuffer(65535);
		Reader clobStream = null;
		
		try
		{
			clobStream = clob.getCharacterStream();
			char[]b = new char[60000];
			int i = 0;
			while((i = clobStream.read(b))!=-1)
			{
				sb.append(b,0,i);
			}
		}
		catch(Exception ex)
		{
			sb = null;
		}
		finally
		{
			try{
				if(clobStream!=null)
					clobStream.close();
			}
			catch(Exception e)
			{
				if(sb == null)
					return null;
				else
					return sb.toString();
			}
		}
		if(sb==null)
			return "";
		return sb.toString();
	}
}
