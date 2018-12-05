package com.git.support.common;

public final class Mcopration {
	
	private final static int BASE = 2000;
	public final static int EXEC_SHELL = BASE + 1; //运行脚本
	
	
	public static String getMcoMessageYaml(){
		return    "--- \n" 
				+":ttl: $ttl$\n" 
				+":callerid: cert=$caller$\n" 
				+":senderid: $senderid$\n" 
				+":msgtime: $ssl_msgtime$\n" 
				+":body: |\n" 
				+"  --- \n" 
				+"  :ssl_ttl: $ttl$\n" 
				+"  :ssl_msgtime: $ssl_msgtime$\n" 
				+"  :ssl_msg: \n" 
				+"    :agent: shell\n" 
				+"    :data: \n" 
				+"      :process_results: true\n" 
				+"      :cmd: $cmd$\n" 
				+"    :action: execute\n" 
				+"    :caller: cert=$caller$" 
				+"\n" 
				+":agent: [shell]\n" 
				+":filter: \n" 
				+"  fact: []\n" 
				+"  cf_class: []\n" 
				+"  agent: \n" 
				+"  - shell\n" 
				+"  identity: [$hosts$]\n" 
				+"  compound: []\n" 
				+":collective: mcollective\n" 
				+":hash: |\n" 
				+"  $hash$" 
				+"\n" ;
		
	}
}
