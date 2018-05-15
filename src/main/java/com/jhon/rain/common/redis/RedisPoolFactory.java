package com.jhon.rain.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p>功能描述</br>Redis连接池</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 20:25
 */
@Component
public class RedisPoolFactory {

  @Autowired
  private RedisConfig redisConfig;

  @Bean
  public JedisPool jedisPoolFactory() {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
    poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
    poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait());
    JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout() * 1000);
    return jp;
  }
}
