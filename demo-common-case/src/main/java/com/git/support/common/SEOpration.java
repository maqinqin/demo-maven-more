package com.git.support.common; 


public final class SEOpration {
	private final static int  BASE                     =     3000;
	public  final static int  TODO                     =     BASE + 1;
	public  final static int  GETLUNSBYSN              =     BASE + 2;
	public  final static int  GETPORTGROUP             =     BASE + 3;
	public  final static int  GETCREATEZONEINFO        =     BASE + 4;
	public  final static int  CREATEZONE               =     BASE + 5;
	public  final static int  CREATEVIEW               =     BASE + 6;
	public  final static int  GETRETRIEVESTORAGEINFO   =     BASE + 7;
	public  final static int  GETRETRIEVEZONEINFO      =     BASE + 8;
	public  final static int  RETRIEVEVIEW             =     BASE + 9;
	public  final static int  RETRIEVEZONE             =     BASE + 10;
	public  final static int  SELECTSTORAGE            =     BASE + 11;
	public  final static int  CREATE_VOLUME            =     BASE + 17;
	public  final static int  CONF_VOLUME              =     BASE + 18;
	
	
	//回收存储、删除zone相关操作节点编号
	public  final static int  GETRECYCLESTORAGE         =     BASE + 12;
	public  final static int  GETDELETEZONEINFO         =     BASE + 13;
	public  final static int  GETDELETEZONEGENERATEINFO =     BASE + 14;
	public  final static int  RECYCLESTORAGE		    =     BASE + 15;
	public  final static int  DELETEZONE		   	    =     BASE + 16;
	
	
	/*
	 * new add
	 */
	public  final static int  SELECT_STORAGE_UINT       =     BASE + 19;
	public  final static int  SELECT_LUN                =     BASE + 20;
	public  final static int  GET_VIEW_NAME             =     BASE + 21;
	public  final static int  ASSIGN_LUN                =     BASE + 22;
	public  final static int  CONFIG_SWITCH             =     BASE + 23;
	public  final static int  GET_HBA_FA_INFO           =     BASE + 24;
	
	/*
	 * check 
	 */
	/*
	 * check volume and qtree
	 */
	public  final static int  CHECK_NAS_CREATE           =     BASE + 25;
	/*
	 * check export,export config file,snapshot
	 */
	public  final static int  CHECK_NAS_CONFIG           =     BASE + 26;
	/*
	 * check select lun
	 */
	public  final static int  CHECK_LUN                   =     BASE + 27;
	
}
