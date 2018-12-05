package com.git.cloud.handler.automation.se.common;

public enum StorageFileBasicEnum {
	DATACENTER_ENAME,//数据中心英文名
	PROJECTABBR, // 应用名称服务器角色缩写	
	APP_ID,//应用系统ID
	DU_ID,//服务器角色ID
	APPLICATION_LEVEL, // 应用级别
	DATA_TYPE, // 数据类型(生产、应急、历史、归档、日志)
	SERVERFUNCTION,//服务器功能 
	DESCRIBE,//描述(生产P，测试T)
	DATA_APP_TYPE,//数据使用方式	(DB/FILE)
	TRADE_TYPE,//交易类型(OLAP/OLTP)
	//HOST_SEAT,//主机位置
	//DEVICE_NAME,//设备名称
	//OS_TYPE,//操作系统类型	
	//DEVICE_IP,//生产IP地址
	VOL_MOUNT_POINT,//卷挂载点
	IS_VOL_SHARED,//是否共享已分配卷
	VOL_SHARE,//需要共享的卷
	CREATE_VOL_CAPACITY,//新创建NAS容量
	RESPONSE_TIME,//响应时间
	IOPS,
	MBPS,
	//RES_POOL_TYPE,//资源池类型
	NAS_AVAILABLE_RATE_THRESHOLD//存储可用率
}
