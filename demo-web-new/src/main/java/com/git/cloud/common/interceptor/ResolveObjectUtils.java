package com.git.cloud.common.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解析对象，将对象所有属性封装固定格式
 * @author sunhailong.co
 */
public class ResolveObjectUtils {
	
	public static final String DIFFERENT_FIELD_ARRAY = "DIFFERENT_FIELD_ARRAY";
	public static final String GROUP_SPILT_SIGN_LEFT = "【"; // 组分割符左括号
	public static final String GROUP_SPILT_SIGN_RIGHT = "】"; // 组分隔符右括号
	public static final String FIELD_EQUAL_SIGN = "："; // 属性与值的分隔符
	public static final String FIELD_SPILT_SIGN = "；"; // 属性之间的分隔符
	private static Logger logger = LoggerFactory.getLogger(ResolveObjectUtils.class);
	
	/**
	 * 解析传入的obj对象的所有属性名和属性值（存放数据字典类型的数据会根据dicCodeList来替换），封装成固定格式返回。
	 * @param obj
	 * @param dicCodeList
	 * @return
	 */
	public static String getObjectFieldString(Object obj, List<String> dicCodeList) {
		if(obj == null) {
			return "";
		}
		HashMap<String, HashMap<String, String>> fieldMap = getFieldMap(dicCodeList);
		return resolveObj(obj, fieldMap);
	}
	
	/**
	 * 解析传入的obj和newObj对象的所有属性名和属性值（存放数据字典类型的数据会根据dicCodeList来替换）
	 * 将从对象obj到对象newObj的不同属性全部记录下来，并按照固定的格式封装起来返回。
	 * @param obj
	 * @param newObj
	 * @param dicCodeList
	 * @return
	 */
	public static String getObjectDifferentFieldString(Object obj, Object newObj, List<String> dicCodeList) {
		if(obj == null || newObj == null) {
			return "";
		}
		if(!obj.getClass().getCanonicalName().equals(newObj.getClass().getCanonicalName())) {
			return "传入对象类型不一致";
		}
		StringBuffer result = new StringBuffer();
		HashMap<String, Object[]> differentFieldMap = getObjectDifferentFieldMap(obj, newObj);
		Object[] differentFieldArr = differentFieldMap.get(DIFFERENT_FIELD_ARRAY);
		if(differentFieldArr.length > 0) {
			HashMap<String, HashMap<String, String>> fieldMap = getFieldMap(dicCodeList);
			for(int i=0 ; i<differentFieldArr.length ; i++) {
				result.append(differentFieldArr[i]);
				result.append(FIELD_EQUAL_SIGN);
				if(fieldMap.get(differentFieldArr[i]) != null) {
					result.append("从" + GROUP_SPILT_SIGN_LEFT);
					result.append(fieldMap.get(differentFieldArr[i]).get(differentFieldMap.get(differentFieldArr[i])[0]));
					result.append(GROUP_SPILT_SIGN_RIGHT + "修改为" + GROUP_SPILT_SIGN_LEFT);
					result.append(fieldMap.get(differentFieldArr[i]).get(differentFieldMap.get(differentFieldArr[i])[1]));
					result.append(GROUP_SPILT_SIGN_RIGHT);
				} else {
					result.append("从" + GROUP_SPILT_SIGN_LEFT);
					result.append(differentFieldMap.get(differentFieldArr[i])[0]);
					result.append(GROUP_SPILT_SIGN_RIGHT + "修改为" + GROUP_SPILT_SIGN_LEFT);
					result.append(differentFieldMap.get(differentFieldArr[i])[1]);
					result.append(GROUP_SPILT_SIGN_RIGHT);
				}
				result.append(FIELD_SPILT_SIGN);
			}
		}
		return result.toString();
	}
	
	/**
	 * 将2个对象不一样的属性和值封装起来
	 * @param obj
	 * @param newObj
	 * @return
	 */
	public static HashMap<String, Object[]> getObjectDifferentFieldMap(Object obj, Object newObj) {
		HashMap<String, Object[]> differentFieldMap = new HashMap<String, Object[]> ();
		Field[] fieldArr = obj.getClass().getDeclaredFields();
		Field[] newFieldArr = newObj.getClass().getDeclaredFields();
		List<String> fieldStrList = new ArrayList<String> ();
		for(int i=0 ; i<fieldArr.length ; i++) {
			Object newValue = getFieldValueByName(newObj, newFieldArr[i].getName(), true);
			if(newValue == null) { // 修改后的对象若属性值为null则不进行记录
				continue;
			}
			Object value = getFieldValueByName(obj, fieldArr[i].getName());
			if(!value.equals(newValue)) {
				fieldStrList.add(fieldArr[i].getName());
				Object[] valueArr = new Object[] {value, newValue};
				differentFieldMap.put(fieldArr[i].getName(), valueArr);
			}
		}
		differentFieldMap.put(DIFFERENT_FIELD_ARRAY, fieldStrList.toArray());
		return differentFieldMap;
	}
	
	/**
	 * 根据传入的字典类型和字段映射关系返回封装的Map
	 * @param dicCodeList 格式为 字段名称:字典类型编码
	 * @return
	 */
	private static HashMap<String, HashMap<String, String>> getFieldMap(List<String> dicCodeList) {
		// fieldMap key：字段名称 value：字典Map
		HashMap<String, HashMap<String, String>> fieldMap = new HashMap<String, HashMap<String, String>> ();
//		int len = dicCodeList == null ? 0 : dicCodeList.size();
//		HashMap<String, String> dicMap;
//		String key, dicCode;
//		String[] subCode;
//		List<DicPo> dicList;
//		for(int i=0 ; i<len ; i++) {
//			dicCode = dicCodeList.get(i);
//			if(dicCode == null) {
//				continue;
//			}
//			subCode = dicCode.split(":");
//			if(subCode.length != 2) {
//				continue;
//			}
//			key = subCode[0]; // 字段名称
//			dicCode = subCode[1]; // 字典类型编码
//			dicList = DicCacheUtil.getActiveDicList(dicCode);
//			int dicLen = dicList == null ? 0 : dicList.size();
//			dicMap = new HashMap<String, String> ();
//			for(int j=0 ; j<dicLen ; j++) {
//				dicMap.put(dicList.get(j).getDicCode(), dicList.get(j).getDicname());
//			}
//			fieldMap.put(key, dicMap);
//		}
		return fieldMap;
	}
	
	private static String resolveObj(Object obj, HashMap<String, HashMap<String, String>> fieldMap) {
		StringBuffer result = new StringBuffer();
		if(obj instanceof List) {
			List objList = (List) obj;
			for(int i=0 ; i<objList.size() ; i++) {
				result.append(GROUP_SPILT_SIGN_LEFT);
				result.append(resolveObj(objList.get(i), fieldMap));
				result.append(GROUP_SPILT_SIGN_RIGHT);
			}
			return result.toString();
		} else if(obj instanceof Object[]) {
			Object [] objArr = (Object[]) obj;
			for(int i=0 ; i<objArr.length ; i++) {
				result.append(GROUP_SPILT_SIGN_LEFT);
				result.append(resolveObj(objArr[i], fieldMap));
				result.append(GROUP_SPILT_SIGN_RIGHT);
			}
		}
		try {
			Field[] fieldArr = obj.getClass().getDeclaredFields();
			for(int i=0 ; i<fieldArr.length ; i++) {
				if(fieldArr[i].getName().equals("serialVersionUID")) {
					continue;
				}
				result.append(fieldArr[i].getName());
				result.append(FIELD_EQUAL_SIGN);
				if(fieldArr[i].getType().toString().indexOf("[L") > 0) { // 数组
					Object ob = getFieldValueByName(obj, fieldArr[i].getName());
					if (ob == null || ob.toString().trim().equals("")) {
						continue;
					}
					Object[] objArr = (Object[]) ob;
					if(objArr != null) {
						result.append(GROUP_SPILT_SIGN_LEFT);
						for(int j=0 ; j<objArr.length ; j++) {
							result.append(resolveObj(objArr[j], fieldMap));
						}
						result.append(GROUP_SPILT_SIGN_RIGHT);
					}
				} else if(fieldArr[i].getType().toString().indexOf("java.util.List") > 0){ // List
					Object listObj = getFieldValueByName(obj, fieldArr[i].getName());
					if(listObj == null || listObj instanceof java.lang.String) {
						continue;
					}
					List objList = (List) listObj;
					if(objList != null) {
						result.append(GROUP_SPILT_SIGN_LEFT);
						for(int j=0 ; j<objList.size() ; j++) {
							result.append(resolveObj(objList.get(j), fieldMap));
						}
						result.append(GROUP_SPILT_SIGN_RIGHT);
					}
				} else if(fieldArr[i].getType().toString().indexOf("com.git.cloud") > 0) { // 实体对象
					if(fieldArr[i].getType().toString().substring(6).equals(obj.getClass().getName())) {
						continue;
					}
					result.append(GROUP_SPILT_SIGN_LEFT);
					result.append(resolveObj(getFieldValueByName(obj, fieldArr[i].getName()), fieldMap));
					result.append(GROUP_SPILT_SIGN_RIGHT);
				} else { // 基本数据类型
					Object value = "";
					if(fieldMap.get(fieldArr[i].getName()) != null) {
						value = fieldMap.get(fieldArr[i].getName()).get(getFieldValueByName(obj, fieldArr[i].getName()));
					} else {
						value = getFieldValueByName(obj, fieldArr[i].getName());
					}
					result.append(value);
					result.append(FIELD_SPILT_SIGN);
				}
			}
		} catch (Exception e) {
			logger.error("解析异常",e);
		}
		return result.toString();
	}
	
	/**
	 * 根据属性名，获取属性值
	 * @param fieldName
	 * @param obj
	 * @return
	 */
	private static Object getFieldValueByName(Object obj, String fieldName) {
		return getFieldValueByName(obj, fieldName, false);
	}
	
	private static Object getFieldValueByName(Object obj, String fieldName, boolean nullFlag) {
		if(fieldName.equals("serialVersionUID")) {
			return "";
		}
		try {
			String getter = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Method method = obj.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(obj, new Object[] {});
			return value == null ? nullFlag ? null : "" : value;
		} catch (Exception e) {
			logger.error("getFieldValueByName error, fileName is" + fieldName);
		}
		return "";
	}
	
	public static void main(String[] args) {
		
	}
}
