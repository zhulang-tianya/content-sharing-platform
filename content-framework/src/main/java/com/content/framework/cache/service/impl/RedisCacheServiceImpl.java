package com.content.framework.cache.service.impl;

import com.content.framework.cache.service.RedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String LOCK_SCRIPT = "if redis.call('exists', KEYS[1]) == 0 then redis.call('setex', KEYS[1], ARGV[1], ARGV[2]); return 1; else return 0; end";
    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then redis.call('del', KEYS[1]); return 1; else return 0; end";

    @Override
    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <T> void set(String key, T value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long delete(List<String> keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    @Override
    public Long decrement(String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    @Override
    public <T> void hSet(String key, String hashKey, T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public <T> T hGet(String key, String hashKey) {
        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public <T> Map<String, T> hGetAll(String key) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        Map<String, T> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            result.put(entry.getKey().toString(), (T) entry.getValue());
        }
        return result;
    }

    @Override
    public Boolean hDelete(String key, String... hashKeys) {
        Object[] hashKeysArray = hashKeys;
        return redisTemplate.opsForHash().delete(key, hashKeysArray) > 0;
    }

    @Override
    public Boolean hExists(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    @Override
    public Set<String> hKeys(String key) {
        Set<Object> keys = redisTemplate.opsForHash().keys(key);
        Set<String> result = new HashSet<>();
        for (Object keyObj : keys) {
            result.add(keyObj.toString());
        }
        return result;
    }

    @Override
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    @Override
    public <T> Long lPush(String key, T... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    @Override
    public <T> Long rPush(String key, T... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public <T> T lPop(String key) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public <T> T rPop(String key) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public <T> List<T> lRange(String key, long start, long end) {
        List<Object> list = redisTemplate.opsForList().range(key, start, end);
        List<T> result = new ArrayList<>();
        for (Object obj : list) {
            result.add((T) obj);
        }
        return result;
    }

    @Override
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public <T> Long sAdd(String key, T... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public <T> Set<T> sMembers(String key) {
        Set<Object> set = redisTemplate.opsForSet().members(key);
        Set<T> result = new HashSet<>();
        for (Object obj : set) {
            result.add((T) obj);
        }
        return result;
    }

    @Override
    public <T> Boolean sIsMember(String key, T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public <T> Long sRemove(String key, T... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    @Override
    public <T> T sPop(String key) {
        return (T) redisTemplate.opsForSet().pop(key);
    }

    @Override
    public <T> Boolean zAdd(String key, T value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    @Override
    public <T> Set<T> zRange(String key, long start, long end) {
        Set<Object> set = redisTemplate.opsForZSet().range(key, start, end);
        Set<T> result = new HashSet<>();
        for (Object obj : set) {
            result.add((T) obj);
        }
        return result;
    }

    @Override
    public <T> Set<T> zRangeByScore(String key, double min, double max) {
        Set<Object> set = redisTemplate.opsForZSet().rangeByScore(key, min, max);
        Set<T> result = new HashSet<>();
        for (Object obj : set) {
            result.add((T) obj);
        }
        return result;
    }

    @Override
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    @Override
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    @Override
    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        return keys != null ? keys : new HashSet<>();
    }

    @Override
    public void flushDB() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Override
    public void flushAll() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }

    @Override
    public Long dbSize() {
        return redisTemplate.getConnectionFactory().getConnection().dbSize();
    }

    @Override
    public <T> void put(String key, T value) {
        set(key, value);
    }

    @Override
    public <T> void put(String key, T value, long timeout, TimeUnit unit) {
        set(key, value, timeout, unit);
    }

    @Override
    public <T> T getCache(String key) {
        return get(key);
    }

    @Override
    public void remove(String key) {
        delete(key);
    }

    @Override
    public void removePattern(String pattern) {
        Set<String> keys = keys(pattern);
        if (!keys.isEmpty()) {
            delete(new ArrayList<>(keys));
        }
    }

    @Override
    public void clear() {
        flushDB();
    }

    @Override
    public boolean containsKey(String key) {
        return exists(key);
    }

    @Override
    public boolean lock(String key, long timeout, TimeUnit unit) {
        String requestId = UUID.randomUUID().toString();
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(LOCK_SCRIPT, Long.class);
        Long result = redisTemplate.execute(script, Collections.singletonList(key), String.valueOf(unit.toSeconds(timeout)), requestId);
        return result != null && result == 1;
    }

    @Override
    public boolean unlock(String key) {
        String requestId = UUID.randomUUID().toString();
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);
        Long result = redisTemplate.execute(script, Collections.singletonList(key), requestId);
        return result != null && result == 1;
    }

    @Override
    public boolean isLocked(String key) {
        return exists(key);
    }
}
