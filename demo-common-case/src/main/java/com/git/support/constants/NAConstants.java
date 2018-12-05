package com.git.support.constants;

/**
 * @author liufei
 * @version 1.0 2013-6-8 
 * @see
 */
public class NAConstants {
	//F5 stub超时时长(单位毫秒)
	public final static int STUB_TIME_OUT = 60000;
	
	//定义创建Pool时LBMethod类型
	public final static String LB_METHOD_ROUND_ROBIN = "ROBIN";
	public final static String LB_METHOD_RATIO_MEMBER = "RATIO";
	public final static String LB_METHOD_LEAST_CONNECTION_MEMBER = "LEAST_CONNECTION";
	
	//定义pool的监控规则类型
	public final static String LB_MONITOR_RULE_TYPE_SINGLE = "SINGLE";
	public final static String LB_MONITOR_RULE_TYPE_NONE = "NONE";
	
	//定义LocalLBPersistenceMode类型
	public final static String LB_PERSISTENCE_MODE_SOURCE_ADDRESS_AFFINITY = "AFFINITY";
	public final static String LB_PERSISTENCE_MODE_COOKIE = "COOKIE";
	
	//定义LocalLB CommonProtocolType
	public final static String LB_COMMON_PROTOCOL_TCP = "PROTOCOL_TCP";
	public final static String LB_COMMON_PROTOCOL_UDP = "PROTOCOL_UDP";
	public final static String LB_COMMON_PROTOCOL_SCTP = "PROTOCOL_SCTP";
	
	//定义LocalLB VirtualServerType
	public final static String LB_VS_TYPE_FAST_L4 = "FASTL4";
	public final static String LB_VS_TYPE_POOL = "LB_VS_TYPE_POOL";
	
	//定义LocalLBProfileContextType
	public final static String LB_PROFILE_CONTEXT_TYPE_ALL = "LB_PROFILE_CONTEXT_TYPE_ALL";
	public final static String LB_PROFILE_CONTEXT_TYPE_CLIENT = "LB_PROFILE_CONTEXT_TYPE_CLIENT";
	public final static String LB_PROFILE_CONTEXT_TYPE_SERVER = "LB_PROFILE_CONTEXT_TYPE_SERVER";
	
	//定义LocalLBMonitorTemplateType
	public final static String LB_MONITOR_TEMPLATE_TYPE_TCP = "LB_MONITOR_TEMPLATE_TYPE_TCP";
	public final static String LB_MONITOR_TEMPLATE_TYPE_GATEWAY_ICMP = "LB_MONITOR_TEMPLATE_TYPE_GATEWAY_ICMP";
	public final static String LB_MONITOR_TEMPLATE_TYPE_HTTP = "LB_MONITOR_TEMPLATE_TYPE_HTTP";
	public final static String LB_MONITOR_TEMPLATE_TYPE_UDP = "LB_MONITOR_TEMPLATE_TYPE_UDP";
	
	//定义LocalLBAddressType
	public final static String LB_ADDRESS_TYPE_STAR_PORT = "STAR_PORT";
	
	//定义LocalLB.ProfileMode
	public final static String LB_PROFILE_MODE_DISABLED = "LB_PROFILE_MODE_DISABLED";
	public final static String LB_PROFILE_MODE_ENABLED  = "LB_PROFILE_MODE_ENABLED";
	
	//定义LocalLBHttpRedirectRewriteMode
	public final static String LB_HTTP_REDIRECT_REWRITE_MODE_NONE = "LB_HTTP_REDIRECT_REWRITE_MODE_NONE";
	public final static String LB_HTTP_REDIRECT_REWRITE_MODE_ALL = "LB_HTTP_REDIRECT_REWRITE_MODE_ALL";
	public final static String LB_HTTP_REDIRECT_REWRITE_MODE_NODES = "LB_HTTP_REDIRECT_REWRITE_MODE_NODES";
	public final static String LB_HTTP_REDIRECT_REWRITE_MODE_MATCHING = "LB_HTTP_REDIRECT_REWRITE_MODE_MATCHING";
	
	//定义CommonEnabledState 
	public final static String COMMON_ENABLED_STATE_ENABLED = "COMMON_ENABLED_STATE_ENABLED";
	public final static String COMMON_ENABLED_STATE_DISABLED = "COMMON_ENABLED_STATE_DISABLED";
	
	//定义StrPropertyType 
	public final static String STR_PROPERTY_TYPE_UNSET = "STR_PROPERTY_TYPE_UNSET";
	
	//定义关联逻辑方式
	//四层应用逻辑
	public final static String APP_TYPE_FOUR_LEVEL = "APP_TYPE_FOUR_LEVEL";
	//七层应用逻辑
	public final static String APP_TYPE_SEVEN_LEVEL = "APP_TYPE_SEVEN_LEVEL";
	
	//定义CommonEnabledState
	public final static String ENABLED_STATE_ENABLED = "STATE_ENABLED";
	public final static String ENABLED_STATE_DISABLED = "STATE_DISABLED";
	
	//定义LocalLBMonitorStrPropertyType
	public final static String STR_PROPERTY_TYPE_SEND = "STYPE_SEND";
	public final static String STR_PROPERTY_TYPE_RECEIVE = "STYPE_RECEIVE";
	public final static String STR_PROPERTY_TYPE_USERNAME = "STYPE_USERNAME";
	public final static String STR_PROPERTY_TYPE_PASSWORD = "STYPE_PASSWORD";
	public final static String STR_PROPERTY_TYPE_SEND_PACKETS = "STYPE_SEND_PACKETS";
	public final static String STR_PROPERTY_TYPE_TIMEOUT_PACKETS = "STYPE_TIMEOUT_PACKETS";
	public final static String STR_PROPERTY_TYPE_DEBUG = "STYPE_DEBUG";
	
	//定义LocalLBMonitorIntPropertyType
	public final static String INT_PROPERTY_TYPE_UP_INTERVAL = "ITYPE_UP_INTERVAL";
	public final static String INT_PROPERTY_TYPE_TIME_UNTIL_UP = "ITYPE_TIME_UNTIL_UP";
	
	//定义LocalLBServiceDownAction
	public final static String SERVICE_DOWN_ACTION_DROP = "SERVICE_DOWN_ACTION_DROP";
	public final static String SERVICE_DOWN_ACTION_NONE = "SERVICE_DOWN_ACTION_NONE";
	public final static String SERVICE_DOWN_ACTION_RESELECT = "SERVICE_DOWN_ACTION_RESELECT";
	public final static String SERVICE_DOWN_ACTION_RESET = "SERVICE_DOWN_ACTION_RESET";
	
	//定义LocalLBTCPOptionMode
	public final static String TCP_OPTION_MODE_PRESERVE = "TCP_OPTION_MODE_PRESERVE";
	public final static String TCP_OPTION_MODE_REWRITE = "TCP_OPTION_MODE_REWRITE";
	public final static String TCP_OPTION_MODE_STRIP = "TCP_OPTION_MODE_STRIP";
	
	//定义LocalLBCookiePersistenceMethod
	public final static String COOKIE_PERSISTENCE_METHOD_HASH = "COOKIE_PERSISTENCE_METHOD_HASH";
	public final static String COOKIE_PERSISTENCE_METHOD_INSERT = "COOKIE_PERSISTENCE_METHOD_INSERT";
	public final static String COOKIE_PERSISTENCE_METHOD_NONE = "COOKIE_PERSISTENCE_METHOD_NONE";
	public final static String COOKIE_PERSISTENCE_METHOD_PASSIVE = "COOKIE_PERSISTENCE_METHOD_PASSIVE";
	public final static String COOKIE_PERSISTENCE_METHOD_REWRITE = "COOKIE_PERSISTENCE_METHOD_REWRITE";
	
	//定义CommonSourcePortBehavior
	public final static String SOURCE_PORT_BEHAVIOR_CHANGE = "SOURCE_PORT_BEHAVIOR_CHANGE";
	public final static String SOURCE_PORT_BEHAVIOR_PRESERVE = "SOURCE_PORT_BEHAVIOR_PRESERVE";
	public final static String SOURCE_PORT_BEHAVIOR_STRICT = "SOURCE_PORT_BEHAVIOR_STRICT";

	//定义LocalLBClonePoolType	
	public final static String CLONE_POOL_TYPE_CLIENTSIDE = "CLONE_POOL_TYPE_CLIENTSIDE";
	public final static String CLONE_POOL_TYPE_SERVERSIDE = "CLONE_POOL_TYPE_SERVERSIDE";
	public final static String CLONE_POOL_TYPE_UNDEFINED = "CLONE_POOL_TYPE_UNDEFINED";
	
	//定义TCPCongestionControlMode
	public final static String TCP_CONGESTION_CONTROL_NONE = "TCP_CONGESTION_CONTROL_NONE";
	public final static String TCP_CONGESTION_CONTROL_RENO = "TCP_CONGESTION_CONTROL_RENO";
	public final static String TCP_CONGESTION_CONTROL_NEWRENO = "TCP_CONGESTION_CONTROL_NEWRENO";
	public final static String TCP_CONGESTION_CONTROL_SCALABLE = "TCP_CONGESTION_CONTROL_SCALABLE";
	public final static String TCP_CONGESTION_CONTROL_HIGHSPEED = "TCP_CONGESTION_CONTROL_HIGHSPEED";
	
	//定义HttpChunkMode
	public final static String HTTP_CHUNK_MODE_PRESERVE = "HTTP_CHUNK_MODE_PRESERVE";
	public final static String HTTP_CHUNK_MODE_SELECTIVE = "HTTP_CHUNK_MODE_SELECTIVE";
	public final static String HTTP_CHUNK_MODE_UNCHUNK = "HTTP_CHUNK_MODE_UNCHUNK";
	public final static String HTTP_CHUNK_MODE_RECHUNK = "HTTP_CHUNK_MODE_RECHUNK";
	
	//定义HttpCompressionMode
	public final static String HTTP_COMPRESSION_MODE_DISABLE = "HTTP_COMPRESSION_MODE_DISABLE";
	public final static String HTTP_COMPRESSION_MODE_ENABLE = "HTTP_COMPRESSION_MODE_ENABLE";
	public final static String HTTP_COMPRESSION_MODE_SELECTIVE = "HTTP_COMPRESSION_MODE_SELECTIVE";
	
	//定义LocalLBCompressionMethod
	public final static String COMPRESSION_METHOD_DEFLATE = "COMPRESSION_METHOD_DEFLATE";
	public final static String COMPRESSION_METHOD_GZIP = "COMPRESSION_METHOD_GZIP";
	
	//定义LocalLBRamCacheCacheControlMode
	public final static String RAMCACHE_CACHE_CONTROL_MODE_NONE = "RAMCACHE_CACHE_CONTROL_MODE_NONE";
	public final static String RAMCACHE_CACHE_CONTROL_MODE_MAX_AGE = "RAMCACHE_CACHE_CONTROL_MODE_MAX_AGE";
	public final static String RAMCACHE_CACHE_CONTROL_MODE_ALL = "RAMCACHE_CACHE_CONTROL_MODE_ALL";
	
	//定义LocalLBProfileType
	public final static String PROFILE_TYPE_PERSISTENCE = "PROFILE_TYPE_PERSISTENCE";
	public final static String PROFILE_TYPE_FAST_L4 = "PROFILE_TYPE_FAST_L4";
	public final static String PROFILE_TYPE_TCP = "PROFILE_TYPE_TCP";
	public final static String PROFILE_TYPE_UDP = "PROFILE_TYPE_UDP";
	public final static String PROFILE_TYPE_HTTP = "PROFILE_TYPE_HTTP";
	public final static String PROFILE_TYPE_FTP = "PROFILE_TYPE_FTP";
	
	//定义snmp访问类型
	public final static String SNMP_ACCESS_TYPE_READONLY = "SNMP_ACCESS_TYPE_READONLY";
	public final static String SNMP_ACCESS_TYPE_READWRITE = "SNMP_ACCESS_TYPE_READWRITE";
	
	//定义snmp访问ip协议类型
	public final static String SNMP_ACCESS_TYPE_IPV4 = "SNMP_ACCESS_TYPE_IPV4";
	public final static String SNMP_ACCESS_TYPE_IPV6 = "SNMP_ACCESS_TYPE_IPV6";
	
	//定义snmp client类型
	public final static String SNMP_CLIENT_TYPE_HOST = "SNMP_CLIENT_TYPE_HOST";
	public final static String SNMP_CLIENT_TYPE_NETWORK = "SNMP_CLIENT_TYPE_NETWORK";

	
}