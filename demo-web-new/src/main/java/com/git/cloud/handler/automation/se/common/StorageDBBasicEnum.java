package com.git.cloud.handler.automation.se.common;

public enum StorageDBBasicEnum {
	DATACENTER_ENAME,//数据中心英文名
	PROJECTABBR, // 应用名称服务器角色缩写
	APP_ID,//应用系统ID
	DU_ID,//服务器角色ID
	APPLICATION_LEVEL, // 应用级别
	DATA_TYPE, // 数据类型(生产、应急、历史、归档、日志)
	SERVERFUNCTION,//服务器功能 
	DESCRIBE,//描述(生产P，测试T)
	DATA_APP_TYPE,//数据使用方式	(DB/FILE)
	//HOST_SEAT,//主机位置
	//DEVICE_NAME,//设备名称
	//DEVICE_IP,//生产IP地址
	//HBA_WWPN,//HBA卡信息
	//OS_TYPE,//操作系统类型
	//OS_VERSION,//操作系统版本
	OS_NAME,//操作系统名称
	CLUSTER_TYPE,//集群类型RAC/HA/SINGLE/NULL
	//MUTI_PATH_VERSION,//多路径版本
	//MUTI_PATH_TYPE,//多路径型号
	DATA_DISK_CAPACITY,//数据盘容量大小
	ARCH_DISK_CAPACITY,//ARCH盘容量
	RESPONSE_TIME,//响应时间
	IOPS,
	MBPS
}
