package com.git.support.sdo.inf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public interface IDataObject extends Serializable
{
	
   /**
	* Returns a map which contains all data in the DataObject instance
	* @param container the name to a valid object.
	* @return the DataObject Instance.
	*/
   HashMap<String, Object> getContianer();
   
   
   /**
	* Returns DataObject item's count in the DataObject instance
	* @return the DataObject item's count.
	*/
  int getItemCnt();
  
  
  /**
	* Returns DataObject item's keyset   in the DataObject instance
	* @return the DataObject item's keyset.
	*/
  Set<String> getItemKSet();
  
   
  /**
   * Returns the value of a name of either this object or an object reachable from it, as identified by the
   * specified name.
   * @param name the name to a valid object.
   * @return the value of the specified name.
   *     
   */
  Object get(String name);


  /**
   * Sets a name of either this object or an object reachable from it, as identified by the specified name,
   * to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
     */
  void set(String name, Object value);


  /**
   * Returns whether a name of either this object or an object reachable from it, as identified by the specified name,
   * is considered to be set.
   * @param name the name to a valid object.
   */
  boolean isSet(String name);

  /**
   * Unsets a name of either this object or an object reachable from it, as identified by the specified name.
   * @param name the name to a valid object.
   */
  void unset(String name);

  /**
   * Returns the value of a <code>boolean</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>boolean</code> value of the specified name.
   * @see #get(String)
   */
  boolean getBoolean(String name);
  
  /**
   * 获取Boolean对象值
   * @param name
   * @return
   */
  Boolean getBooleanObj(String name);

  /**
   * Returns the value of a <code>byte</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>byte</code> value of the specified name.
   * @see #get(String)
   */
  byte getByte(String name);

  /**
   * Returns the value of a <code>char</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>char</code> value of the specified name.
   * @see #get(String)
   */
  char getChar(String name);

  /**
   * Returns the value of a <code>double</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>double</code> value of the specified name.
   * @see #get(String)
   */
  double getDouble(String name);

  /**
   * Returns the value of a <code>float</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>float</code> value of the specified name.
   * @see #get(String)
   */
  float getFloat(String name);

  /**
   * Returns the value of a <code>int</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>int</code> value of the specified name.
   * @see #get(String)
   */
  int getInt(String name);

  /**
   * Returns the value of a <code>long</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>long</code> value of the specified name.
   * @see #get(String)
   */
  long getLong(String name);
  
  /**
   * 获取Long型对象值
   * @param name
   * @return
   */
  Long getLongObj(String name);

  /**
   * Returns the value of a <code>short</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>short</code> value of the specified name.
   * @see #get(String)
   */
  short getShort(String name);

  /**
   * Returns the value of a <code>byte[]</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>byte[]</code> value of the specified name.
   * @see #get(String)
   */
  byte[] getBytes(String name);

  /**
   * Returns the value of a <code>BigDecimal</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>BigDecimal</code> value of the specified name.
   * @see #get(String)
   */
  BigDecimal getBigDecimal(String name);

  /**
   * Returns the value of a <code>BigInteger</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>BigInteger</code> value of the specified name.
   * @see #get(String)
   */
  BigInteger getBigInteger(String name);

  /**
   * Returns the value of a <code>Date</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>Date</code> value of the specified name.
   * @see #get(String)
   */
  Date getDate(String name);

  /**
   * Returns the value of a <code>String</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>String</code> value of the specified name.
   * @see #get(String)
   */
  String getString(String name);

  /**
   * Returns the value of a <code>List</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>List</code> value of the specified name.
   * @see #get(String)
   */
  List getList(String name);
  
  /**
   * Returns the value of a <code>HashMap</code> name identified by the specified name.
   * @param name the name to a valid object.
   * @return the <code>HashMap</code> value of the specified name.
   * @see #get(String)
   */
  HashMap getHashMap(String name);
  
  /**
   * Returns the value of a <code>DataObject</code> name identified by the specified name.
   * @param name the name to a valid object.
 * @return 
   * @return the <code>DataObject</code> value of the specified name.
   * @see #get(String)
   */ 
  IDataObject getDataObject(String name);
  
  public <T extends IDataObject> T getDataObject(String name, Class<T> clazz);
  
  
  /**
	* Override DataObject's data through a map
	* @param container the map contain data items.
	*/
   void setContainer(HashMap<String, Object>container);

  /**
   * Sets the value of a <code>boolean</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setBoolean(String name, boolean value);
  
  /**
   * set Boolean对象型参数
   * @param name
   * @param value
   */
  void setBoolean(String name, Boolean value); 

  /**
   * Sets the value of a <code>byte</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setByte(String name, byte value);

  /**
   * Sets the value of a <code>char</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setChar(String name, char value);

  /**
   * Sets the value of a <code>double</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setDouble(String name, double value);

  /**
   * Sets the value of a <code>float</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setFloat(String name, float value);

  /**
   * Sets the value of a <code>int</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setInt(String name, int value);

  /**
   * Sets the value of a <code>long</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setLong(String name, long value);
  
  /**
   * 增加Long对象型参数
   * @param name
   * @param value
   */
  void setLong(String name, Long value);

  /**
   * Sets the value of a <code>short</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setShort(String name, short value);

  /**
   * Sets the value of a <code>byte[]</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setBytes(String name, byte[] value);

  /**
   * Sets the value of a <code>BigDecimal</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setBigDecimal(String name, BigDecimal value);

  /**
   * Sets the value of a <code>BigInteger</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setBigInteger(String name, BigInteger value);

  /**
   * Sets the value of a <code>Date</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setDate(String name, Date value);

  /**
   * Sets the value of a <code>String</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setString(String name, String value);

  /**
   * Sets the value of a <code>List</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setList(String name, List value);
  
  /**
   * Sets the value of a <code>HashMap</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setHashMap(String name, HashMap value);
  
  /**
   * Sets the value of a <code>DataObject</code> name identified by the specified name, to the specified value.
   * @param name the name to a valid object.
   * @param value the new value for then name.
   * @see #set(String, Object)
   */
  void setDataObject(String name, IDataObject value);
}