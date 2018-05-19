package com.jhon.rain.service;

import com.jhon.rain.entity.Order;
import com.jhon.rain.entity.SecKillOrder;
import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.GoodsVO;

/**
 * <p>功能描述</br>订单服务接口定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:55
 */
public interface OrderService {

  /**
   * <pre>通过用户和商品信息查询秒杀订单信息</pre>
   *
   * @param mobile  用户ID
   * @param goodsId 商品ID
   * @return
   */
  SecKillOrder getSecKillOrderByUserMobileGoodsId(String mobile, Long goodsId);

  /**
   * <pre>通过订单Id查询订单</pre>
   *
   * @param orderId 订单ID
   * @return
   */
  Order getOrderById(Long orderId);

  /**
   * <pre>创建订单信息</pre>
   * @param user 用户信息
   * @param goods 商品信息
   * @return
   */
  Order createOrderInfo(User user, GoodsVO goods);
}
