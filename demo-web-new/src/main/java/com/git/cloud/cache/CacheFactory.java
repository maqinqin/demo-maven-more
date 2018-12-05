package com.git.cloud.cache;


public abstract interface CacheFactory
{
  public static final String BEAN_ID = "cacheFactory";

  public abstract Cache buildCache(String paramString)
    throws CacheException;
}
