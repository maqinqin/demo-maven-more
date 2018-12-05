package com.git.cloud.taglib.dic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class SortUtils {
	
	/**
	 * @param objects objects collection
	 * @param property sort property name
	 * @param desc true if sort as desc  else false 
	 * @return 
	 */
	public static List sortByProperty(Collection objects,String property,boolean isnumberic, boolean desc){
		List result=new ArrayList();
		try{
			Comparator c=new PropertyComparator(property,isnumberic,desc);
			result.addAll(objects);
			Collections.sort(result,c);
		}catch(Exception e){
			result.addAll(objects);
		}
		return result;
	}
	
	public static List sortByProperty(Collection objects,String property){
		return sortByProperty(objects,property,false,false);
	}
	
	public static List sortByProperty(Collection objects, String property, String sort){
		return sortByProperty(objects,property,false,"desc".equalsIgnoreCase(sort));
	}

}

/**
 * @author wxg
 */
class PropertyComparator implements Comparator{
	private boolean desc=false;
	private boolean isNumberic;
	private String propertyName;
	
	public PropertyComparator(String propertyName){
		this.propertyName=propertyName;
	}
	
	public PropertyComparator(String propertyName,boolean isnumberic,boolean desc){
		this.propertyName=propertyName;
		this.desc=desc;
		this.isNumberic = isnumberic;
	}
	
	public int compare(Object obj1,Object obj2){
		try{
			String value1 = BeanUtils.getProperty(obj1,propertyName);
			String value2 = BeanUtils.getProperty(obj2,propertyName);
			if(value1==null) value1="";
			if(value2==null) value2="";
			value1=new String(value1.getBytes("GBK"),"ISO-8859-1");
			value2=new String(value2.getBytes("GBK"),"ISO-8859-1");
			if(desc){
				if(this.isNumberic){
					return (int)(Double.parseDouble(value2) - Double.parseDouble(value1));
				}else{
					return value2.compareTo(value1);
				}
			}else{
				if(this.isNumberic){
					return (int)(Double.parseDouble(value1) - Double.parseDouble(value2));
				}else{
					return value1.compareTo(value2);
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}