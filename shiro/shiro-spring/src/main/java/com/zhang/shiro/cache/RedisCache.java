package com.zhang.shiro.cache;

import com.zhang.shiro.utils.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

/**
 * @author zhangyu
 * @create 2018-11-09 14:35
 **/
@Component
public class RedisCache<K, V> implements Cache<K, V> {

    @Autowired
    private JedisUtil jedisUtil;

    private final static String CACHE_PREFIX = "zhang-cache:";

    private byte[] getKey(K k) {
        if (k instanceof String) {
            return (CACHE_PREFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Override
    public V get(K k) throws CacheException {
        System.out.println("从Redis中获取数据...");
        byte[] value = jedisUtil.get(getKey(k));
        if (value != null) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key, value);
        jedisUtil.exprie(key, 600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {

        byte[] key = getKey(k);
        byte[] value = jedisUtil.get(key);

        jedisUtil.delete(key);

        if (null != value) {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        // 不要重写，避免清空了所有redis中的数据
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
