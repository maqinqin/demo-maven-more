package com.git.cloud.cache;


public abstract interface CacheProvider
{
  public abstract Cache buildCache(String paramString)
    throws CacheException;
}
