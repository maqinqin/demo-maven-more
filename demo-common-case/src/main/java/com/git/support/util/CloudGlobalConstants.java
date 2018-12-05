package com.git.support.util;

/**
 * 全局常量类，统一维护云平台相关的常量 ,避免代码释义不一致以及分散重复维护的情况
 * 各开发人员添加常量前先检查是是否已经定义好的常量
 * @version 1.0 2013-3-15 
 * @see
 */
public class CloudGlobalConstants {
	
	/**数据是否有效*/
	public static final String ACTIVE_STS_YES = "Y";//有效
	public static final String ACTIVE_STS_NO = "N";//无效
	
	/**排序方式*/
	public static final String DESC = "DESC";//降序
	public static final String ASC = "ASC";//升序
	
	
	
	/**平台类型*/
	/*public static final String AIX_PLATFORM_TYPE = "AIX";  
	public static final String HP_PLATFORM_TYPE = "HPUX";
	public static final String X86_PLATFORM_TYPE = "X86";*/
	
	/**虚拟机管理软件*/
	/*public static final String POWERVM_TYPE = "HMC"; 
	public static final String VMWARE_TYPE = "VCENTER"; */
	
	/** 虚拟机管理机类型，取值为HMC和VCENTER */
	
	public static enum VM_SERVER_TYPE {
		HMC,
		VCENTER
	};
	
	public static enum VM_OP_TYPE {
		START,
		STOP,
		REBOOT
	};
	
	/**是否默认*/
	public static final String DEFAULT_STS_YES = "Y";//默认
	public static final String DEFAULT_STS_NO = "N";//非默认
	
	/**是否采用*/
	public static final String ADOPT_STS_YES = "Y";//采用
	public static final String ADOPT_STS_NO = "N";//不采用

	public static final long HEAT_BEAT_TIME = 180000;//心跳时间
	
	public static final String VCENTER_URL_HTTPS = "https://"; 
	public static final String VCENTER_URL_SDK = "/sdk"; 

    /**授权资源类型-应用系统授权*/
	public static final String AUTH_RES_TYPE_APP = "APP_RES";//
	
    /**操作系统用户类型*/
	public static final String USER_TYPE_COOMMON= "01";//通用的用户如，root\Administrator
	public static final String USER_TYPE_SYS_CREATE= "02";//系统创建的用户

	/**授权树前缀*/
	public static final String APP = "app_"; //应用系统
	public static final String COMP = "comp_"; //组件
	public static final String DU = "du_"; //部署单元
	public static final String FIRSTLEVEL = "0"; //授权列表中需要展开的第一级别
	
	/** 
	 * 工作流参数获取 
	 */
	/** 服务请求ID */
	public static final String SRV_REQ_ID = "srvReqId";
	/** 服务资源请求ID */
	public static final String SRV_RES_REQ_ID = "rrinfoId";
	/** 执行脚本ID */
    public static final String EXEC_SCRIPT_ID = "EXEC_SCRIPT_ID";
    /** 业务执行版本 */
    public static final String BUS_VERSION = "BUS_VERSION";

    /**系统操作使用范围*/
	public static final String SYS_OP_SCOPE_GLOBAL = "G"; //全局
	public static final String SYS_OP_SCOPE_APP = "A"; //应用
	
    /**系统操作类型*/
	public static final String SYS_OP_TYPE_NORMAL = "N"; //标准
	public static final String SYS_OP_TYPE_UD = "U"; //自定义
	public static final String SYS_OP_TYPE_SYS = "S"; //系统
	/** HP用户名密码 用于物理机申请-自动预分配*/
	public static final String HP_OA_USER = "HP_OA_USER";
	public static final String HP_OA_PASSWORD = "HP_OA_PASSWORD";
	public static final String HP_ILO_USER = "HP_ILO_USER";
	public static final String HP_ILO_PASSWORD = "HP_ILO_PASSWORD";
	
	public static final String AIX_USERNAME = "AIX_USERNAME";
	public static final String AIX_PASSWORD = "AIX_PASSWORD";
	public static final String HP_USERNAME = "HP_USERNAME";
	public static final String HP_PASSWORD = "HP_PASSWORD";
	public static final String WINDOWS_USERNAME = "WINDOWS_USERNAME";
	public static final String WINDOWS_PASSWORD = "WINDOWS_PASSWORD";
	public static final String LINUX_USERNAME = "LINUX_USERNAME";
	public static final String LINUX_PASSWORD = "LINUX_PASSWORD";
	
	
	//应用系统状态01:启用，02：停用
	public static final String SYS_STATUS_VALID = "01";
	public static final String SYS_STATUS_INVALID = "02";
	
	
//	应用系统iomp-cloud的编码
	public static final String SYS_CLOUD_NAME = "iomp-cloud";
//	应用系统bpm-flex的编码
	public static final String SYS_FLEX_NAME = "bpm-flex";	
	
	//自动化操作类型_通用应用类型
	public static final String AMOPTYPE_APPLICATION = "Generic-Application";
	//自动化操作类型_通用平台类型
	public static final String AMOPTYPE_PLATFORM = "Generic-Platform";
	//自动化操作类型_其他类型
	public static final String AMOPTYPE_OTHER = "Generic-Other";
}