package com.git.cloud.shiro.realm;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;

import com.git.cloud.common.exception.RollbackableBizException;
import com.git.cloud.sys.dao.SysMenuDao;
import com.git.cloud.sys.model.po.SysMenuPo;
/**
 * 
  * @ClassName: ChainDefinitionSectionMetaSource
  * @Description: TODO
  * @author guojianjun
  *
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SysMenuDao sysMenuDaoImpl;

	private String filterChainDefinitions;

	/**
	 * 默认premission字符串
	 */
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	@Override
	public Section getObject() throws BeansException {

		Ini ini = new Ini();
		// 加载默认的url
		ini.load(filterChainDefinitions);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		//获取所有Resource  
		List<SysMenuPo> functions ;  
		try {
			functions = sysMenuDaoImpl.findAll("function");
	        //循环Resource的url,逐个添加到section中。section就是filterChainDefinitionMap,  
	        //里面的键就是链接URL,值就是存在什么条件才能访问该链接  
	        for (Iterator<SysMenuPo> it = functions.iterator(); it.hasNext();) {  
	        	SysMenuPo f = it.next();  
	        	 //如果不为空值添加到section中
					if (StringUtils.isNotEmpty(f.getMenuUrl()) && StringUtils.isNotEmpty(f.getMenuCode())) {
						section.put("/" + f.getMenuUrl(), MessageFormat.format(PREMISSION_STRING, f.getMenuCode()));
					}
					//System.err.println(f.getMenuCode()+"---"+f.getMenuUrl());
	        }  
		} catch (RollbackableBizException e) {
			logger.error("异常exception",e);
		}
		return section;
	}

	/**
	 * 通过filterChainDefinitions对默认的url过滤定义
	 * 
	 * @param filterChainDefinitions
	 *            默认的url过滤定义
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public SysMenuDao getSysMenuDaoImpl() {
		return sysMenuDaoImpl;
	}

	public void setSysMenuDaoImpl(SysMenuDao sysMenuDaoImpl) {
		this.sysMenuDaoImpl = sysMenuDaoImpl;
	}

}
