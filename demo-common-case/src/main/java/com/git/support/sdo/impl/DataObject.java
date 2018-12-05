package com.git.support.sdo.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.git.support.constants.PubConstants;
import com.git.support.sdo.inf.IDataObject;
import com.git.support.util.PwdUtil;

public class DataObject implements IDataObject {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> container = null;

	protected DataObject() {
		this.container = new HashMap<String, Object>();
	}

	protected DataObject(HashMap<String, Object> container) {
		this.container = new HashMap<String, Object>(container);
	}

	public static DataObject CreateDataObject() {
		return new DataObject();
	}

	public static DataObject CreateDataObject(HashMap<String, Object> container) {
		return new DataObject(container);
	}

	@Override
	public HashMap<String, Object> getContianer() {
		return new HashMap<String, Object>(container);
	}

	public void setContainer(HashMap<String, Object> container) {
		this.container = new HashMap<String, Object>(container);
	}

	@Override
	public int getItemCnt() {
		return container.size();
	}

	@Override
	public Set<String> getItemKSet() {
		return container.keySet();
	}

	@Override
	public Object get(String name) {
		return container.get(name);
	}

	@Override
	public void set(String name, Object value) {
		container.put(name, value);

	}

	@Override
	public boolean isSet(String name) {
		return container.containsKey(name);
	}

	@Override
	public void unset(String name) {
		container.remove(name);
	}

	@Override
	public String toString() {
		return "DataObject [container=" + container + "]";
	}

	@Override
	public String getString(String name) {
		String str = (String) get(name);
		if (isStartsWith(str)) {
			return getPwd(str);
		}
		return str;
	}

	@Override
	public boolean getBoolean(String name) {
		return (Boolean) get(name);
	}

	@Override
	public byte getByte(String name) {
		return (Byte) get(name);
	}

	@Override
	public char getChar(String name) {
		return (Character) get(name);
	}

	@Override
	public double getDouble(String name) {
		return (Double) get(name);
	}

	@Override
	public float getFloat(String name) {
		return (Float) get(name);
	}

	@Override
	public int getInt(String name) {
		return (Integer) get(name);
	}

	@Override
	public long getLong(String name) {
		return (Long) get(name);
	}

	@Override
	public short getShort(String name) {
		return (Short) get(name);
	}

	@Override
	public byte[] getBytes(String name) {
		return (byte[]) get(name);
	}

	@Override
	public BigDecimal getBigDecimal(String name) {
		return (BigDecimal) get(name);
	}

	@Override
	public BigInteger getBigInteger(String name) {
		return (BigInteger) get(name);
	}

	@Override
	public Date getDate(String name) {
		return (Date) get(name);
	}

	@Override
	public List getList(String name) {
		return (List) get(name);
	}

	@Override
	public HashMap getHashMap(String name) {
		return (HashMap) get(name);
	}

	@Override
	public IDataObject getDataObject(String name) {
		Object obj = get(name);
		if (obj == null) {
			return null;
		}

		if (obj instanceof DataObject) {
			return (DataObject) obj;
		} else if (obj instanceof HashMap) {
			DataObject d = new DataObject((HashMap<String, Object>) obj);
			return d;
		} else {
			throw new RuntimeException(name + "类型转换出现错误,obj=" + obj.getClass());
		}
	}

	@Override
	public <T extends IDataObject> T getDataObject(String name, Class<T> clazz) {
		Object obj = get(name);
		if (obj == null) {
			return null;
		}
		if (obj.getClass().equals(clazz)) {
			return (T) obj;
		} else {
			T da = (T) BeanUtils.instantiateClass(clazz);
			if (obj instanceof DataObject) {
				da.setContainer(((DataObject) obj).getContianer());
			} else if (obj instanceof HashMap) {
				da.setContainer((HashMap<String, Object>) obj);
			} else {
				throw new RuntimeException(name + "类型转换出现错误,obj=" + obj.getClass());
			}
			set(name, da);
			return da;
		}
	}

	@Override
	public void setString(String name, String value) {
		set(name, value);
	}

	@Override
	public void setBoolean(String name, boolean value) {
		set(name, value);
	}

	@Override
	public void setByte(String name, byte value) {
		set(name, value);
	}

	@Override
	public void setChar(String name, char value) {
		set(name, value);
	}

	@Override
	public void setDouble(String name, double value) {
		set(name, value);
	}

	@Override
	public void setFloat(String name, float value) {
		set(name, value);
	}

	@Override
	public void setInt(String name, int value) {
		set(name, value);
	}

	@Override
	public void setLong(String name, long value) {
		set(name, value);
	}

	@Override
	public void setShort(String name, short value) {
		set(name, value);
	}

	@Override
	public void setBytes(String name, byte[] value) {
		set(name, value);
	}

	@Override
	public void setBigDecimal(String name, BigDecimal value) {
		set(name, value);
	}

	@Override
	public void setBigInteger(String name, BigInteger value) {
		set(name, value);
	}

	@Override
	public void setDate(String name, Date value) {
		set(name, value);
	}

	@Override
	public void setList(String name, List value) {
		set(name, value);
	}

	@Override
	public void setHashMap(String name, HashMap value) {
		set(name, value);
	}

	@Override
	public void setDataObject(String name, IDataObject value) {
		set(name, value);
	}

	@Override
	public void setLong(String name, Long value) {
		// TODO Auto-generated method stub
		set(name, value);
	}

	@Override
	public void setBoolean(String name, Boolean value) {
		// TODO Auto-generated method stub
		set(name, value);
	}

	@Override
	public Boolean getBooleanObj(String name) {
		// TODO Auto-generated method stub
		return (Boolean) get(name);
	}

	@Override
	public Long getLongObj(String name) {
		// TODO Auto-generated method stub
		return (Long) get(name);
	}

	private boolean isStartsWith(String str) {
		if (StringUtils.isNotBlank(str)) {
			return str.startsWith(PubConstants.PWD_KEY);
		}
		return false;
	}

	private String getPwd(String str) {
		String tmp = str.substring(PubConstants.PWD_KEY.length(), str.length());
		return PwdUtil.decryption(tmp);
	}

}
