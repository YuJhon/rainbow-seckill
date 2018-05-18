package com.jhon.rain.common.keyprefix;

/**
 * <p>功能描述</br>订单键</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/18 16:45
 */
public class OrderKey extends BasePrefix {

  public OrderKey(String prefix) {
    super(prefix);
  }

  public static OrderKey getSecKillOrderByUidGid = new OrderKey("secKillOrderUidGid");

}
