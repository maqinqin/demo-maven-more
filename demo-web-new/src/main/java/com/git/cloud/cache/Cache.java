package com.git.cloud.cache;

import java.io.IOException;
import java.util.Collection;

public abstract interface Cache
{
  public abstract Object get(Object paramObject)
    throws CacheException;

  public abstract void put(Object paramObject1, Object paramObject2)
    throws CacheException;

  public abstract void remove(Object paramObject)
    throws CacheException;

  public abstract Collection getKeys()
    throws CacheException;

  public abstract void clear()
    throws CacheException,IOException;

  public abstract void destroy()
    throws CacheException;
}
