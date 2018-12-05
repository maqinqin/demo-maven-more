package com.git.cloud.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheUtil {
	private static Logger logger = LoggerFactory.getLogger(CacheUtil.class);
	private static Cache sysMenuCache;
	private String sysMenuCacheName;
	CacheFactory cacheFactory;

	public void init() {
		
			try {
				if (this.sysMenuCacheName != null)
//				CacheUtil.sysMenuCache = this.cacheFactory.buildCache(this.sysMenuCacheName);
				setSysMenuCache(this.cacheFactory.buildCache(this.sysMenuCacheName));
			} catch (CacheException e) {
				logger.error("初始化缓存异常",e);
			}
	}

	public static Cache getSysMenuCache() {
		return sysMenuCache;
	}
	
	public void setSysMenuCache(Cache sysMenuCache) {
		setSysMenuCacheValue(sysMenuCache);
	}
	
	public static void setSysMenuCacheValue(Cache sysMenuCache) {
		CacheUtil.sysMenuCache = sysMenuCache;
	}


	public String getSysMenuCacheName() {
		return sysMenuCacheName;
	}

	public void setSysMenuCacheName(String sysMenuCacheName) {
		this.sysMenuCacheName = sysMenuCacheName;
	}

	public CacheFactory getCacheFactory() {
		return cacheFactory;
	}

	public void setCacheFactory(CacheFactory cacheFactory) {
		this.cacheFactory = cacheFactory;
	}

}
