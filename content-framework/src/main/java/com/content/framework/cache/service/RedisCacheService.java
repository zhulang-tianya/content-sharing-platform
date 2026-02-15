package com.content.framework.cache.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisCacheService {

    // 字符串操作
    <T> void set(String key, T value);
    
    <T> void set(String key, T value, long timeout, TimeUnit unit);
    
    <T> T get(String key);
    
    Boolean delete(String key);
    
    Long delete(List<String> keys);
    
    Boolean expire(String key, long timeout, TimeUnit unit);
    
    Long getExpire(String key);
    
    Boolean exists(String key);
    
    Long increment(String key);
    
    Long increment(String key, long delta);
    
    Long decrement(String key);
    
    Long decrement(String key, long delta);
    
    // Hash操作
    <T> void hSet(String key, String hashKey, T value);
    
    <T> T hGet(String key, String hashKey);
    
    <T> Map<String, T> hGetAll(String key);
    
    Boolean hDelete(String key, String... hashKeys);
    
    Boolean hExists(String key, String hashKey);
    
    Long hSize(String key);
    
    Set<String> hKeys(String key);
    
    List<Object> hValues(String key);
    
    // List操作
    <T> Long lPush(String key, T... values);
    
    <T> Long rPush(String key, T... values);
    
    <T> T lPop(String key);
    
    <T> T rPop(String key);
    
    <T> List<T> lRange(String key, long start, long end);
    
    Long lSize(String key);
    
    // Set操作
    <T> Long sAdd(String key, T... values);
    
    <T> Set<T> sMembers(String key);
    
    <T> Boolean sIsMember(String key, T value);
    
    Long sSize(String key);
    
    <T> Long sRemove(String key, T... values);
    
    <T> T sPop(String key);
    
    // ZSet操作
    <T> Boolean zAdd(String key, T value, double score);
    
    <T> Set<T> zRange(String key, long start, long end);
    
    <T> Set<T> zRangeByScore(String key, double min, double max);
    
    Long zRemove(String key, Object... values);
    
    Double zScore(String key, Object value);
    
    Long zSize(String key);
    
    // 通用操作
    Set<String> keys(String pattern);
    
    void flushDB();
    
    void flushAll();
    
    Long dbSize();
    
    // 缓存管理
    <T> void put(String key, T value);
    
    <T> void put(String key, T value, long timeout, TimeUnit unit);
    
    <T> T getCache(String key);
    
    void remove(String key);
    
    void removePattern(String pattern);
    
    void clear();
    
    boolean containsKey(String key);
    
    // 分布式锁
    boolean lock(String key, long timeout, TimeUnit unit);
    
    boolean unlock(String key);
    
    boolean isLocked(String key);
}
