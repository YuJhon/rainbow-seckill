package com.jhon.rain.common.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 20:25
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

  private String host;

  private int port;

  private int timeout;

  private String password;

  private int poolMaxTotal;

  private int poolMaxIdle;

  private int poolMaxWait;

}
