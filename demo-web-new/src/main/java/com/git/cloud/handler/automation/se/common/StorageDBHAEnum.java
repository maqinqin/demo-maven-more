package com.git.cloud.handler.automation.se.common;
//AIX-DB-HA默认参数
public enum StorageDBHAEnum {
	RES_POOL_TYPE,//资源池类型
	AVAILABLE_RATE_THRESHOLD,//存储可用率
	PLATINUM_SAN_STORAGE_COUNT,//白金SAN选择存储数量
	GLOD_SAN_STORAGE_COUNT,//金SAN选择存储数量
	SILVER_SAN_STORAGE_COUNT,//银SAN选择存储数量
	PLATINUM_HEARTBEAT_COUNT,//白金心跳盘数量
	GLOD_HEARTBEAT_COUNT,//金级心跳盘数量
	SILVER_HEARTBEAT_COUNT,//银级心跳盘数量
	HEARTBEAT_NAME,//心跳盘
	HEARTBEAT_CAPACITY,//心跳盘容量大小
	DATA_DISK_NAME,//DATA盘名称
	DATA_DISK_CAPACITY,//DATA盘容量大小
	ARCH_DISK_NAME,//ARCH盘名称
	ARCH_DISK_CAPACITY,//ARCH盘容量大小
	CELL_CAPACITY//分盘单位大小
}
