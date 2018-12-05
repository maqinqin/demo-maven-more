package com.git.cloud.request.model;

/**
 * @ClassName:SrTypeMarkEnum
 * @Description:TODO
 * @author sunhailong
 * @date 2014-9-29 下午3:46:48
 */
public enum SrTypeMarkEnum {

	/**
	 * 虚拟机供给
	 */
	VIRTUAL_SUPPLY("VS"),
	/**
	 * 虚拟机扩容
	 */
	VIRTUAL_EXTEND("VE"),
	/**
	 * 虚拟机回收
	 */
	VIRTUAL_RECYCLE("VR"),
	/**
	 * 物理机供给
	 */
	PHYSICAL_SUPPLY("PS"),
	/**
	 * 物理机回收
	 */
	PHYSICAL_RECYCLE("PR"),
	/**
	 * 服务自动化
	 */
	SERVICE_AUTO("SA"),
	/**
	 * 迁移
	 */
	VIRTUAL_MIGRATION("MIGRATION"),
	/**
	 * 新增
	 */
	VIRTUAL_NEW("NEW"),
	/**
	 * 被变更
	 */
	VIRTUAL_ALTER("ALTER"),
	/**
	 * 丢失
	 */
	VIRTUAL_LOSE("LOSE"),
	/**
	 * 定价审批
	 */
	PRICE_EXAMINE_APPROVE("PEA"),
	/**
	 * 防火墙开通
	 */
	FIREWALL("FW"),
	/**
	 * 防火墙回收
	 */
	FIREWALL_RECYCLE("FWR");
	
	private final String value;
	
	private SrTypeMarkEnum(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
