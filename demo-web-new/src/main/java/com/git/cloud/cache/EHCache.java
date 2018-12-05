package com.git.cloud.cache;

/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*     */ import net.sf.ehcache.CacheManager;
/*     */ import net.sf.ehcache.Element;
/*     */ 
/*     */ public class EHCache
/*     */   implements Cache
/*     */ {
/*  22 */   private static Logger logger = LoggerFactory.getLogger(EHCache.class);
/*     */   private net.sf.ehcache.Cache cache;
/*     */ 
/*     */   public EHCache(String name)
/*     */     throws CacheException
/*     */   {
/*     */     try
/*     */     {
/*  28 */       CacheManager manager = CacheManager.getInstance();
/*  29 */       this.cache = manager.getCache(name);
/*  30 */       if (this.cache == null)
/*     */       {
/*  32 */         logger.warn("Could not find configuration for " + name + ". Configuring using the defaultCache settings.");
/*  33 */         manager.addCache(name);
/*  34 */         this.cache = manager.getCache(name);
/*     */       }
/*     */     }
/*     */     catch (net.sf.ehcache.CacheException e)
/*     */     {
/*  39 */       throw new CacheException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object get(Object key)
/*     */     throws CacheException
/*     */   {
/*  51 */     if (logger.isDebugEnabled()) {
/*  52 */       logger.debug("key: " + key);
/*     */     }
/*  54 */     if (key == null) return null;
/*     */     try
/*     */     {
/*  57 */       Element element = this.cache.get((Serializable)key);
/*  58 */       if (element != null) {
/*  59 */         return this.cache.get((Serializable)key).getValue();
/*     */       }
/*  61 */       if (logger.isDebugEnabled()) {
/*  62 */         logger.debug("Element for " + key + " is null");
/*     */       }
/*  64 */       return null;
/*     */     }
/*     */     catch (net.sf.ehcache.CacheException ex) {
/*  67 */       throw new CacheException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Collection getKeys()
/*     */     throws CacheException
/*     */   {
/*     */     try
/*     */     {
/*  79 */       return this.cache.getKeys();
/*     */     }
/*     */     catch (net.sf.ehcache.CacheException ex) {
/*  82 */       throw new CacheException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void put(Object key, Object value)
/*     */     throws CacheException
/*     */   {
/*     */     try
/*     */     {
/*  93 */       Element element = new Element((Serializable)key, (Serializable)value);
/*  94 */       this.cache.put(element);
/*     */     }
/*     */     catch (IllegalStateException ex) {
/*  97 */       throw new CacheException(ex);
/*     */     }
/*     */     catch (IllegalArgumentException ex) {
/* 100 */       throw new CacheException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void remove(Object key)
/*     */     throws CacheException
/*     */   {
/*     */     try
/*     */     {
/* 111 */       this.cache.remove((Serializable)key);
/*     */     }
/*     */     catch (ClassCastException e) {
/* 114 */       throw new CacheException(e);
/*     */     }
/*     */     catch (IllegalStateException e) {
/* 117 */       throw new CacheException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */     throws CacheException
/*     */   {
/*     */     try
/*     */     {
/* 126 */       this.cache.removeAll();
/*     */     }
/*     */     catch (IllegalStateException e) {
/* 129 */       throw new CacheException(e);
/*     */     }
///*     */     catch (IOException e) {
///* 132 */       throw new CacheException(e);
///*     */     }
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */     throws CacheException
/*     */   {
/*     */     try
/*     */     {
/* 141 */       CacheManager.getInstance().removeCache(this.cache.getName());
/*     */     }
/*     */     catch (net.sf.ehcache.CacheException ex) {
/* 144 */       throw new CacheException(ex);
/*     */     }
/*     */     catch (IllegalStateException ex) {
/* 147 */       throw new CacheException(ex);
/*     */     }
/*     */   }
/*     */ }
