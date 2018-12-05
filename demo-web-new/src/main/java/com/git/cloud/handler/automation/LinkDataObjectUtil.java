package com.git.cloud.handler.automation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.git.cloud.handler.common.Utils;
import com.git.support.common.MesgFlds;
import com.git.support.sdo.impl.BodyDO;
import com.git.support.sdo.impl.DataObject;
import com.git.support.sdo.impl.HeaderDO;
import com.git.support.sdo.inf.IDataObject;

/**
 * 构造请求对象的通用方法
 * @author zhuzhaoyong.co
 * 修改：
 * 		2015-11-06 构造请求的方法中增加操作bean名称
 *
 */
public class LinkDataObjectUtil {
	
	private static Logger log = LoggerFactory.getLogger(LinkDataObjectUtil.class);

	/**
	 * 构造通用请求对象	
	 * @param keys				请求对象中需要的key集合,key的格式为: (type)ctx_keyname:api_keyname，其中ctx_keyname为在contextparam中
	 * 							的key，api_keyname为api接口中的key
	 * @param contextParams		value集合
	 * @param resourceClass		资源类别，如：VM 等
	 * @param resourceType		资源类型，如：VMWARE 等
	 * @param operation			操作编码，整数
	 * @param queueIdentify		数据中心队列标识
	 * @return
	 * @throws Exception
	 */
	public static IDataObject buildRequestData(List<String> keys, Map<String, Object> contextParams, String resourceClass, String resourceType, 
			int operation, String queueIdentify, String operationBean) throws Exception {
		IDataObject reqData = DataObject.CreateDataObject();
		HeaderDO header = HeaderDO.CreateHeaderDO();
		header.setResourceClass(resourceClass);
		header.setResourceType(resourceType);
		header.setOperation(operation);
		header.setOperationBean(operationBean);
		header.set(LinkRouteType.DATACENTER_QUEUE_IDEN.getValue(), queueIdentify);
		reqData.setDataObject(MesgFlds.HEADER, header);
		BodyDO body = BodyDO.CreateBodyDO();
		// 添加body
		for (String key : keys) {
			log.info("key: " + key);
			key = key.trim();
			String apiKey = null;
			if (key.indexOf(":") > 0) {
				apiKey = key.substring(key.indexOf(":") + 1).trim();
				key = key.substring(0, key.indexOf(":")).trim();
			}
			if (key.startsWith("(") && key.indexOf(")") > 0) {
				// 包含(type)
				String type = key.substring(1, key.indexOf(")")).trim();
				String keyName = key.substring(key.indexOf(")") + 1).trim();
				log.info("keyName: " + keyName);
				Object value = null;
				value = contextParams.get(keyName);
				log.info("value: " + value);
				if (type.toUpperCase().indexOf("MAP") > -1 || type.toUpperCase().indexOf("HASHMAP") > -1) {
					type = "java.util.HashMap";
				} else if (type.toUpperCase().indexOf("LIST") > -1) {
					type = "java.util.ArrayList";
				}
				log.info("type: " + type);
				@SuppressWarnings("rawtypes")
				Class cls = Class.forName(type);
				@SuppressWarnings("unchecked")
				Object obj = JSON.parseObject((String) value, cls);
				log.info("value: " + obj);
				if (obj != null) {
					if (apiKey == null) {
						apiKey = keyName;
					}
					body.set(apiKey, obj);
				} else {
					throw new Exception("没有发现初始化参数信息，参数名：" + keyName + "，类型：" + type);
				}
			} else {
				Object value = contextParams.get(key);
				log.info("value= " + value);
				if (value != null) {
					if (apiKey == null) {
						apiKey = key;
					}
					body.setString(apiKey, (String) value);
				} else {
					throw new Exception("没有发现初始化参数信息，参数名：" + key);
				}
			}
		}
		reqData.setDataObject(MesgFlds.BODY, body);
		return reqData;
	}
	
	/**
	 * 读取消息对象中body中的值
	 * @param responseData
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getReturnBodyItem(IDataObject responseData, String key) throws Exception {
		if (responseData == null) {
			throw new Exception("responseObject is null.");
		}
		BodyDO respBody = responseData.getDataObject(MesgFlds.BODY, BodyDO.class);
		if (respBody != null) {
			String trimKey = key.trim();
			if (trimKey.indexOf(".") > -1) {
				// 如果key是嵌套格式
				String[] ary = trimKey.split("\\.");
				String bodyKey = ary[0];
				Object object = respBody.get(bodyKey);
				String value = getItemValue(object, ary, 1);
				return value;
			} else {
				// 如果key是简单格式
				Object obj = respBody.get(trimKey);
				if (obj == null) {
					return null;
				} else {
					return obj.toString();
				}
			}
		} else {
			throw new Exception("responseObject body is null.");
		}
	}
	
	private static String getItemValue(Object obj, String[] aryKey, int index) throws Exception {
		log.info("Get item value, obj:{}, aryKey:{}, aryKey size:{}, index:{}", 
				Arrays.toString(new Object[]{obj, Arrays.toString(aryKey), aryKey.length, index}));
		Object objValue = null;
		if (index >= aryKey.length) {
			return (obj == null ? null : obj.toString());
		}
		String trimKey = aryKey[index++].trim();
		if (obj instanceof DataObject) {
			DataObject doj = (DataObject) obj;
			objValue = doj.get(trimKey);
			return getItemValue(objValue, aryKey, index);
		} else if (obj instanceof java.util.Map) {
			Map map = (Map) obj;
			objValue = map.get(trimKey);
			return getItemValue(objValue, aryKey, index);
		} else if (obj instanceof java.util.List) {
			List list = (List) obj;
			int ind = 0;
			if (trimKey.equalsIgnoreCase("min")) {
				ind = 0;
			} else if (trimKey.equalsIgnoreCase("max")) {
				ind = list.size() - 1;
			} else {
				ind = Integer.parseInt(trimKey);
				if (ind >= list.size()) {
					throw new Exception("从body中读取信息出错：index超出List的范围");
				}
			}
			objValue = list.get(ind);
			return getItemValue(objValue, aryKey, index);
		} else {
			return (String) objValue;
		}
	}
	
	/**
	 * 设置返回码
	 * @param responseData
	 * @return
	 */
	public static void setReturnCode(IDataObject responseData, String retCode) throws Exception {
		if (responseData == null) {
			throw new Exception("responseObject is null.");
		}
		HeaderDO responseHeader = responseData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		responseHeader.setRetCode(retCode);
	}
	
	/**
	 * 增加返回信息
	 * @param responseData
	 * @return
	 */
	public static void addReturnMesg(IDataObject responseData, String retMesg) throws Exception {
		if (responseData == null) {
			throw new Exception("responseObject is null.");
		}
		HeaderDO responseHeader = responseData.getDataObject(MesgFlds.HEADER, HeaderDO.class);
		responseHeader.setRetMesg(responseHeader.getRetMesg() + Utils.BR + retMesg);
	}
}
