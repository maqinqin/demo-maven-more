package com.git.cloud.cache;

/*    */ 
/*    */ 
/*    */ public class CacheException extends Exception
/*    */ {
/*    */   public CacheException(String s)
/*    */   {
/* 17 */     super(s);
/*    */   }
/*    */ 
/*    */   public CacheException(Exception e) {
/* 21 */     super(e.getMessage(), e);
/*    */   }
/*    */   public CacheException(String s, Exception e) {
/* 24 */     super(s, e);
/*    */   }
/*    */ }
