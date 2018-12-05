package com.git.cloud.common.enums;

public enum OperationType {
	/** 导入虚拟交换机*/
	IMPORT_VSWITCH("IMPORT_VSWITCH"),
	/** 导入虚拟机*/
	IMPORT_VM("IMPORT_VM"),
	/** 创建虚拟机*/
	BUILD_VM("BUILD_VM"), 
	/** 创建物理击*/
	BUILD_HOST("BUILD_HOST"), 
	/** 回收物理击*/
	RECYCLE_HOST("RECYCLE_HOST"), 
	/** 实施 */
	ACTUALIZE("ACTUALIZE"),
	/** 上线*/
	ONLINE("ONLINE"),
	/** 扩容*/
	EXPANSION("EXPANSION"),
	/** 回收*/
	RECYCLE("RECYCLE"),	
	/** 服务自动化*/
	SERVICEAUTO("SERVICEAUTO"),	
	/** 开机*/
	POWERON("POWERON"),	
	/** 关机*/
	SHUTDOWN("SHUTDOWN"),
	/** 重启*/
	RESTART("RESTART"),	
	/** 挂起*/
	SUSPEND("SUSPEND"),
	/** 唤醒*/
	RESUME("RESUME"),
	/** 新建快照*/
	SNAPSHOT("SNAPSHOT"),	
	/** 恢复快照*/
	REVENTSNAPSHOT("REVENTSNAPSHOT"),
	/** 删除快照*/
	REMOVESNAPSHOT("REMOVESNAPSHOT"),
	/** 迁移*/
	MIGRATE("MIGRATE"),
	/** 关联入池*/
	LINK("LINK"),	
	/** 解除关联*/
	UNLINK("UNLINK"),	
	/** 物理机开机*/
	//PMOPEN("PMOPEN"),	
	/** 物理机关机*/
	PMSHUTDOWN("PMSHUTDOWN"),	
	/** 纳管*/
	INVC("INVC"),	
	/** 解除纳管*/
	OUTVC("OUTVC"),	
	/** 连接成功*/
	CONN_SUCCESS("CONN_SUCCESS"),	
	/** 连接失败*/
	CONN_FAIL("CONN_FAIL"),
	/** 漂移*/
	AUTO_MIGRATE("AUTO_MIGRATE"),
	/** 电源变化*/
	POWER_CHANGE("POWER_CHANGE"),
	/** 进入维护模式*/
	ENTER_MAINTENANCE("ENTER_MAINTENANCE"),
	/** 退出维护模式*/
	EXIT_MAINTENANCE("EXIT_MAINTENANCE")
	/** 自动同步报警信息 **/
	,SYNC_ALARM("SYNC_ALARM")
	/** 自动同步虚拟机信息 **/
	,SYNC_VM_INFO("SYNC_VM_INFO")
	/** 自动同步虚拟机信息 **/
	,NODE_ALARM("NODE_ALARM")
	;
    
    private final String value;

    private OperationType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static OperationType fromString(String value ) {
		if (value != null) {
			for (OperationType c : OperationType.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }


}
