package com.jhon.rain.common.rabbitmq;

import com.jhon.rain.common.redis.RedisHelper;
import com.jhon.rain.entity.SecKillOrder;
import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.dto.SecKillMessage;
import com.jhon.rain.pojo.vo.GoodsVO;
import com.jhon.rain.service.GoodsService;
import com.jhon.rain.service.OrderService;
import com.jhon.rain.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>功能描述</br>消息接收者</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/19 15:39
 */
@Component
@Slf4j
public class MqReceiver {

  @Autowired
  private GoodsService goodsService;

  @Autowired
  private OrderService orderService;

  @Autowired
  private SecKillService secKillService;

  @RabbitListener(queues = MqConfig.SEC_KILL_QUEUE)
  public void receiveSecKillMsg(String msg) {
    log.info("Receive Sec Kill Queue Msg:{}", msg);
    SecKillMessage secKillMessage = RedisHelper.stringToBean(msg, SecKillMessage.class);
    secKillOperation(secKillMessage);
  }

  /**
   * <pre>秒杀消息处理操作</pre>
   *
   * @param secKillMessage
   */
  private void secKillOperation(SecKillMessage secKillMessage) {
    User user = secKillMessage.getUser();
    long goodsId = secKillMessage.getGoodsId();
    GoodsVO goodsVO = goodsService.getGoodsVOByGoodsId(goodsId);
    if (goodsVO.getStockCount() <= 0) {
      return;
    }
    SecKillOrder order = orderService.getSecKillOrderByUserMobileGoodsId(user.getMobile(), goodsId);
    if (order != null) {
      return;
    }
    secKillService.secKillProcessV2(user, goodsVO);
  }
}
