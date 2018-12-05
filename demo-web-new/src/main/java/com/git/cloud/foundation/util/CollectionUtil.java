package com.git.cloud.foundation.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title 		CollectionUtil.java 
 * @Package 	com.git.cloud.foundation.util 
 * @author 		yangzhenhai
 * @date 		2014-9-12下午4:24:37
 * @version 	1.0.0
 * @Description 
 *
 */
public class CollectionUtil {
	/**
	 * convertMap(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param type
	 * @param map
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException 
	 *Object
	 * @exception 
	 * @since  1.0.0
	 */
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); 
        Object obj = type.newInstance(); 

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                Object value = map.get(propertyName);
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
    
    /**
     * 
     * convertBean(这里用一句话描述这个方法的作用)
     * (这里描述这个方法适用条件 – 可选)
     * @param bean
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException 
     *Map
     * @exception 
     * @since  1.0.0
     */
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }
    
    /**
	 * 
	 * @Title: hasContent
	 * @Description: 判断集合是否含有内容
	 * @field: @param collection（list,set等）
	 * @field: @return
	 * @return boolean
	 * @throws
	 */
	public static boolean hasContent(Collection collection){
		
		if(collection != null && collection.size()>0){
			return true;
		}else{
			return false;
		}
	}
}
