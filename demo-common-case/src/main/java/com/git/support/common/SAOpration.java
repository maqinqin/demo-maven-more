package com.git.support.common;

public final class SAOpration {
	
	private final static int BASE = 2000;
	public final static int EXEC_SHELL = BASE + 1;
	public final static int EXEC_SINGLE_SHELL = BASE + 2;
	public final static int DISTR_SCRIPTS = BASE + 3;

	public final static int EXEC_SCP_DOWNLOAD = BASE + 4;
	public final static int EXEC_SCP_UPLOAD = BASE + 5;
	public final static int EXEC_PACKAGE_DISTR = BASE + 6;
	
	//物理机安装
	public final static int PM_INSTALL = BASE + 7;
	
	//虚拟机操作
	public final static int VM_OPERATION  = BASE + 8;
	
	// 上传字符串并在目标主机上生成文件
	public final static int MAKE_AND_DISTRIBUTE_FILE = 3001;
}
