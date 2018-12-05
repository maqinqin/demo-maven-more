package com.git.support.sdo.impl;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.git.support.sdo.inf.IDataObject;

public class SerialDataObject {

	private static final char GENERAL_VALUE_BEGIN = '{';
	private static final char GENERAL_VALUE_END = '}';
	private static final char LIST_VALUE_BEGIN = '[';
	private static final char LIST_VALUE_END = ']';
	private static final char OBJ_TYPE_BEGIN = '(';
	private static final char OBJ_TYPE_END = ')';

	private boolean isIndent;
	private boolean isLF;
	private boolean isShowType;

	private char indentChar;
	private char lfChar;
	private char entryDeliChar;
	
	Logger logger  =  LoggerFactory.getLogger(SerialDataObject.class);

	public SerialDataObject() {
		this(true, true, true, '\t', '\n', ',');
	}

	public SerialDataObject(boolean isIndent, boolean isLF, boolean isShowType) {
		this(isIndent, isLF, isShowType, '\t', '\n', ',');
	}

	public SerialDataObject(boolean isIndent, boolean isLF, boolean isShowType,
			char indentChar, char lfChar, char entryDeliChar) {
		this.isIndent = isIndent;
		this.isLF = isLF;
		this.isShowType = isShowType;
		this.indentChar = indentChar;
		this.lfChar = lfChar;
		this.entryDeliChar = entryDeliChar;
	}

	/**
	 * @return the isIndent
	 */
	public boolean isIndent() {
		return isIndent;
	}

	/**
	 * @param isIndent
	 *            the isIndent to set
	 */
	public void setIndent(boolean isIndent) {
		this.isIndent = isIndent;
	}

	/**
	 * @return the isLF
	 */
	public boolean isLF() {
		return isLF;
	}

	/**
	 * @param isLF
	 *            the isLF to set
	 */
	public void setLF(boolean isLF) {
		this.isLF = isLF;
	}

	/**
	 * @return the indentChar
	 */
	public char getIndentChar() {
		return indentChar;
	}

	/**
	 * @param indentChar
	 *            the indentChar to set
	 */
	public void setIndentChar(char indentChar) {
		this.indentChar = indentChar;
	}

	/**
	 * @return the lfChar
	 */
	public char getLfChar() {
		return lfChar;
	}

	/**
	 * @param lfChar
	 *            the lfChar to set
	 */
	public void setLfChar(char lfChar) {
		this.lfChar = lfChar;
	}

	/**
	 * @return the entityDeliChar
	 */
	public char getEntryDeliChar() {
		return entryDeliChar;
	}

	/**
	 * @param entityDeliChar
	 *            the entityDeliChar to set
	 */
	public void setEntryDeliChar(char entityDeliChar) {
		this.entryDeliChar = entityDeliChar;
	}

	private void addIndent(int cnt, StringBuffer strBuf) {
		if (isIndent == false) {
			return;
		}
		for (int i = 0; i < cnt; i++) {
			strBuf.append(indentChar);
		}
	}

	public String serial2String(IDataObject dto) {
		try {
			StringBuffer strBuf = new StringBuffer(1024);
			serialObject(dto.getContianer(), strBuf, 0);
			return strBuf.toString();
		} catch (Exception e) {
			logger.warn("Serial DataObject to String error:", e);
			return null;
		}

	}
	
	public void serial2String(IDataObject dto, StringBuffer strBuf) {
		try {
			serialObject(dto.getContianer(), strBuf, 0);
		} catch (Exception e) {
			logger.warn("Serial DataObject to String error:", e);
		}
	}

	private void addLF(StringBuffer strBuf) {
		if (isLF == false) {
			return;
		}
		strBuf.append(lfChar);
	}

	private void serialObject(Map<String, Object> obj, StringBuffer strBuf,
			int level) {
		if (obj == null) {
			strBuf.append(obj);
			return;
		}

		strBuf.append(GENERAL_VALUE_BEGIN);
		if (isLF)
			addLF(strBuf);

		for (Iterator iter = obj.entrySet().iterator(); iter.hasNext();) {
			Map.Entry element = (Map.Entry) iter.next();
			String KeyChild = (String) element.getKey();
			Object objChild = element.getValue();

			serialValue(KeyChild, objChild, strBuf, level + 1, isShowType);
		}

		if (strBuf.charAt(strBuf.length() - (isLF ? 2 : 1)) == entryDeliChar) {
			strBuf.deleteCharAt(strBuf.length() - (isLF ? 2 : 1));
		}

		if (isIndent)
			addIndent(level - 1, strBuf);
		strBuf.append(GENERAL_VALUE_END);
	}

	private void serialObject(List obj, StringBuffer strBuf, int level) {
		if (obj == null) {
			strBuf.append(obj);
			return;
		}

		strBuf.append(LIST_VALUE_BEGIN);
		if (isLF)
			addLF(strBuf);

		for (Iterator iter = obj.iterator(); iter.hasNext();) {
			Object objChild = iter.next();
			serialValue(null, objChild, strBuf, level + 1, isShowType);
		}

		if (strBuf.charAt(strBuf.length() - (isLF ? 2 : 1)) == entryDeliChar) {
			strBuf.deleteCharAt(strBuf.length() - (isLF ? 2 : 1));
		}

		if (isIndent)
			addIndent(level - 1, strBuf);
		strBuf.append(LIST_VALUE_END);
	}

	private void serialObject(Set obj, StringBuffer strBuf, int level) {
		if (obj == null) {
			strBuf.append(obj);
			return;
		}
		strBuf.append(LIST_VALUE_BEGIN);
		if (isLF)
			addLF(strBuf);

		for (Iterator iter = obj.iterator(); iter.hasNext();) {
			Object objChild = iter.next();
			serialValue(null, objChild, strBuf, level + 1, isShowType);
		}

		if (strBuf.charAt(strBuf.length() - (isLF ? 2 : 1)) == entryDeliChar) {
			strBuf.deleteCharAt(strBuf.length() - (isLF ? 2 : 1));
		}

		if (isIndent)
			addIndent(level - 1, strBuf);
		strBuf.append(LIST_VALUE_END);
	}

	private void serialObject(Object[] obj, StringBuffer strBuf, int level) {
		if (obj == null) {
			strBuf.append(obj);
			return;
		}
		strBuf.append(LIST_VALUE_BEGIN);
		if (isLF)
			addLF(strBuf);

		for (Object objChild : obj) {
			serialValue(null, objChild, strBuf, level + 1, false);
		}

		if (strBuf.charAt(strBuf.length() - (isLF ? 2 : 1)) == entryDeliChar) {
			strBuf.deleteCharAt(strBuf.length() - (isLF ? 2 : 1));
		}

		if (isIndent)
			addIndent(level - 1, strBuf);
		strBuf.append(LIST_VALUE_END);
	}

	private void serialPojo(Object obj, StringBuffer strBuf, int level) {
		if (obj == null) {
			strBuf.append(obj);
			return;
		}
		try {
			Class c = obj.getClass();
			Method m[] = c.getDeclaredMethods();
			strBuf.append(GENERAL_VALUE_BEGIN);
			if (isLF)
				addLF(strBuf);
			for (int i = 0; i < m.length; i++) {
				if (m[i].getName().indexOf("get") == 0) {
					serialValue(StringUtils.uncapitalize(m[i].getName().substring(3)),
							m[i].invoke(obj, null), strBuf, level + 1,
							isShowType);
				}
			}
			if (strBuf.charAt(strBuf.length() - (isLF ? 2 : 1)) == entryDeliChar) {
				strBuf.deleteCharAt(strBuf.length() - (isLF ? 2 : 1));
			}
			if (isIndent)
				addIndent(level - 1, strBuf);
			strBuf.append(GENERAL_VALUE_END);
		} catch (Throwable e) {
			System.err.println(e);
		}
	}

	private void serialValue(String key, Object value, StringBuffer strBuf,
			int level, boolean isShowType) {
		addIndent(level, strBuf);
		if (key != null) {
			strBuf.append("\"");
			strBuf.append(key);
			strBuf.append("\":");
		}

		if (value == null) {
			strBuf.append(value);
		} else {
			if (isShowType) {
				strBuf.append(OBJ_TYPE_BEGIN);
				strBuf.append(ClassUtils.getShortClassName(value.getClass()));
				strBuf.append(OBJ_TYPE_END);
			}

			if (String.class.isInstance(value)
					|| char[].class.isInstance(value)
					|| Character[].class.isInstance(value)) {
				strBuf.append("\"");
				strBuf.append(value);
				strBuf.append("\"");
			} else if (IDataObject.class.isInstance(value)) {
				serialObject(((IDataObject) value).getContianer(), strBuf,
						level + 1);
			} else if (Map.class.isInstance(value)) {
				serialObject((Map) value, strBuf, level + 1);
			} else if (List.class.isInstance(value)) {
				serialObject((List) value, strBuf, level + 1);
			} else if (Set.class.isInstance(value)) {
				serialObject((Set) value, strBuf, level + 1);
			} else if (Object[].class.isInstance(value)) {
				serialObject((Object[]) value, strBuf, level + 1);
			} else if (!ClassUtils.isPrimitiveOrWrapper(value.getClass())) {
				serialPojo(value, strBuf, level + 1);
			} else {
				strBuf.append(value);
			}
		}
		strBuf.append(entryDeliChar);
		if (isLF)
			addLF(strBuf);
	}
}
