package com.git.cloud.cache;

/*    */ 
/*    */ public class CacheFactoryBean
/*    */   implements CacheFactory
/*    */ {
/*    */   private CacheProvider provider;
/*    */ 
/*    */   public Cache buildCache(String regionName)
/*    */     throws CacheException
/*    */   {
/* 25 */     return this.provider.buildCache(regionName);
/*    */   }
/*    */ 
/*    */   public CacheProvider getProvider() {
/* 29 */     return this.provider;
/*    */   }
/*    */   public void setProvider(CacheProvider provider) {
/* 32 */     this.provider = provider;
/*    */   }
/*    */ }

