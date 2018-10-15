package com.marryou.metadata.service;

import java.util.List;
import java.util.Set;

/**
 * Created by linhy on 2018/6/21.
 */
public interface RedisService {

    public boolean set(final String key, Object value);

    public boolean set(final String key, Object value, Long expireTime);

    public void remove(final String... keys);

    public void removePattern(final String pattern);

    public void remove(final String key);

    public boolean exists(final String key);

    public Object get(final String key);

    public void hmSet(String key, Object hashKey, Object value);

    public Object hmGet(String key, Object hashKey);

    public void lPush(String k,Object v);

    public List<Object> lRange(String k, long l, long l1);

    public void add(String key,Object value);

    public Set<Object> setMembers(String key);

    public void zAdd(String key,Object value,double scoure);

    public Set<Object> rangeByScore(String key, double scoure, double scoure1);
}
