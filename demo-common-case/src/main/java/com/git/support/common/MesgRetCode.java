package com.git.support.common;

public final class MesgRetCode {
	public  final static String  SUCCESS				=  "0000";  //处理成功
	public  final static String  ERR_PROCESS_FAILED		=  "9997";  //处理失败
	public  final static String  ERR_NOT_SUPPORT_OPT  	=  "9998";  //不支持的操作
	public  final static String  ERR_OTHER  			=  "9999";  //其他错误
	
	public  final static String  ERR_PARAMETER_WRONG	=  "9996";  //参数错误
	public  final static String  ERR_SIZE_INSUFFICIENT  =  "9995";  //存储容量不满足
	public  final static String  ERR_TIMEOUT  =  "9994";  //处理超时
	public  final static String  ERR_EXISTED  =  "9993";  //纳管物理机已经存在
	public  final static String  ERR_NOTFOUND  =  "9992";  //解除纳管物理机不存在
}
