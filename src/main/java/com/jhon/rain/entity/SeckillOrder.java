package com.jhon.rain.entity;

import lombok.Data;
import lombok.ToString;

/**
 * <p>功能描述</br>秒杀订单</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:49
 */
@Data
@ToString
public class SeckillOrder {

  /**
   * 主键ID
   */
  private Long id;
  /**
   * 用户ID
   */
  private Long userId;
  /**
   * 订单ID
   */
  private Long orderId;
  /**
   * 商品ID
   */
  private Long goodsId;
}
