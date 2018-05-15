package com.jhon.rain.common.redis;

import com.alibaba.fastjson.JSON;
import com.jhon.rain.common.keyprefix.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>功能描述</br>redis的辅助工具</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 20:24
 */
@Component
public class RedisHelper {

  @Autowired
  private JedisPool jedisPool;

  /**
   * <pre>获取对象</pre>
   *
   * @param prefix
   * @param key
   * @param clazz
   * @param <T>
   * @return
   */
  public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      String realKey = prefix.getPrefix() + key;
      String str = jedis.get(realKey);
      T t = stringToBean(str, clazz);
      return t;
    } finally {
      returnToPool(jedis);
    }
  }

  /**
   * <pre>设置值</pre>
   *
   * @param keyPrefix
   * @param key
   * @param value
   * @param <T>
   * @return
   */
  public <T> boolean set(KeyPrefix keyPrefix, String key, T value) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      String str = beanToString(value);
      if (str == null || str.length() <= 0) {
        return false;
      }
      String realKey = keyPrefix.getPrefix() + key;
      int second = keyPrefix.expireSeconds();
      if (second <= 0) {
        jedis.set(realKey, str);
      } else {
        jedis.setex(realKey, second, str);
      }
      return true;
    } finally {
      returnToPool(jedis);
    }
  }

  /**
   * <pre>判断是否存在</pre>
   *
   * @param prefix
   * @param key
   * @param <T>
   * @return
   */
  public <T> boolean exist(KeyPrefix prefix, String key) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      String realKey = prefix.getPrefix() + key;
      return jedis.exists(realKey);
    } finally {
      returnToPool(jedis);
    }
  }

  /**
   * <pre>删除操作</pre>
   *
   * @param prefix
   * @param key
   * @return
   */
  public boolean delete(KeyPrefix prefix, String key) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      String realKey = prefix.getPrefix() + key;
      long ret = jedis.del(realKey);
      return ret > 0;
    } finally {
      returnToPool(jedis);
    }
  }

  /**
   * <pre>增加值</pre>
   *
   * @param prefix
   * @param key
   * @param <T>
   * @return
   */
  public <T> Long incr(KeyPrefix prefix, String key) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      String realKey = prefix.getPrefix() + key;
      return jedis.incr(realKey);
    } finally {
      returnToPool(jedis);
    }
  }

  /**
   * <pre>减少值</pre>
   *
   * @param prefix
   * @param key
   * @param <T>
   * @return
   */
  public <T> Long decr(KeyPrefix prefix, String key) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      String realKey = prefix.getPrefix() + key;
      return jedis.decr(realKey);
    } finally {
      returnToPool(jedis);
    }
  }

  /**
   * <pre>删除</pre>
   *
   * @param prefix
   * @return
   */
  public boolean delete(KeyPrefix prefix) {
    if (prefix == null) {
      return false;
    }
    List<String> keys = scanKeys(prefix.getPrefix());
    if (keys == null || keys.size() <= 0) {
      return true;
    }
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      jedis.del(keys.toArray(new String[0]));
      return true;
    } catch (final Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      returnToPool(jedis);
    }
  }

  /**
   * 获取所有的key
   *
   * @param key
   * @return
   */
  public List<String> scanKeys(String key) {
    Jedis jedis = null;
    try {
      jedis = jedisPool.getResource();
      List<String> keys = new ArrayList<String>();
      String cursor = "0";
      ScanParams sp = new ScanParams();
      sp.match("*" + key + "*");
      sp.count(100);
      do {
        ScanResult<String> ret = jedis.scan(cursor, sp);
        List<String> result = ret.getResult();
        if (result != null && result.size() > 0) {
          keys.addAll(result);
        }
        // 再处理cursor
        cursor = ret.getStringCursor();
      }
      while (!"0".equals(cursor));
      return keys;
    } finally {
      returnToPool(jedis);
    }
  }

  /**
   * bean转换为json
   *
   * @param value
   * @param <T>
   * @return
   */
  public static <T> String beanToString(T value) {
    if (value == null) {
      return null;
    }
    Class<?> clazz = value.getClass();
    if (clazz == int.class || clazz == Integer.class) {
      return "" + value;
    } else if (clazz == String.class) {
      return (String) value;
    } else if (clazz == long.class || clazz == Long.class) {
      return "" + value;
    } else {
      return JSON.toJSONString(value);
    }
  }

  /**
   * json串装换为bean
   *
   * @param str
   * @param clazz
   * @param <T>
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T stringToBean(String str, Class<T> clazz) {
    if (str == null || str.length() <= 0 || clazz == null) {
      return null;
    }
    if (clazz == int.class || clazz == Integer.class) {
      return (T) Integer.valueOf(str);
    } else if (clazz == String.class) {
      return (T) str;
    } else if (clazz == long.class || clazz == Long.class) {
      return (T) Long.valueOf(str);
    } else {
      return JSON.toJavaObject(JSON.parseObject(str), clazz);
    }
  }

  /**
   * 归还连接
   *
   * @param jedis
   */
  private void returnToPool(Jedis jedis) {
    if (jedis != null) {
      jedis.close();
    }
  }
}
