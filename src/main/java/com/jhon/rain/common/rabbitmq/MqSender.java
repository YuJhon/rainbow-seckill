package com.jhon.rain.common.rabbitmq;

import com.jhon.rain.common.redis.RedisHelper;
import com.jhon.rain.pojo.dto.SecKillMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>功能描述</br>消息发送者</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/19 15:39
 */
@Component
@Slf4j
public class MqSender {

  @Autowired
  private AmqpTemplate amqpTemplate;

  /**
   * <pre>发送秒杀消息</pre>
   *
   * @param message 秒杀消息
   */
  public void sendSecKillMsg(SecKillMessage message) {
    String msg = RedisHelper.beanToString(message);
    log.info("Send Message Info:{}", msg);
    amqpTemplate.convertAndSend(MqConfig.SEC_KILL_QUEUE, msg);
  }
}
