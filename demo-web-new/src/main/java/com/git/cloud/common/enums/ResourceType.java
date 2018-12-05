package com.git.cloud.common.enums;

public enum ResourceType {
	/**< 物理机 */
	PHYSICAL("H"), 
	/**< 虚拟机 */
	VIRTUAL("V"), 
	/**< 虚拟机管理机*/
	VIRTUALMANAGE("VM"),
	/**< HMC */
	HMC("HMC"),	
	/**< NIM */
	NIM("NIM"),	
	/**< 脚本服务器 */
	SCRIPTSERVER("SCRIPT"),	
	/**< 镜像服务器 */
	IMAGESERVER("IMAGE") 	
	/** Datastore **/
	,DATASTORE("DS")
	/** ComputeResource **/
	,COMPUTE_RESOURCE("CR")
	/** Datacenter **/
	,DATACENTER("DC")
	/** DistributedVirtualSwitch **/
	,DISTRIBUTED_VIRTUAL_SWITCH("DVS")
	/** Network **/
	,NETWORK("N")
	/** RESOURCE POOL **/
	,RESOURCE_POOL("RP")
	/** 流程节点 **/
	,WORKFLOW_NODE("NODE")
	;
    
    private final String value;

    private ResourceType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static ResourceType fromString(String value ) {
		if (value != null) {
			for (ResourceType c : ResourceType.values()) {
				if (value.equalsIgnoreCase(c.value)) {
					return c;
				}
			}
		}
		return null;
    }


}
