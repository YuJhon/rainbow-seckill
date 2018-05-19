package com.jhon.rain.common.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

/**
 * <p>功能描述</br>消息配置</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/19 15:38
 */
@Configuration
public class MqConfig {

  public static final String SEC_KILL_QUEUE = "rainbow.sec.kill.queue";

  @Bean
  public Queue queue(){
    return new Queue(SEC_KILL_QUEUE,true);
  }

}
