package com.jhon.rain.service.impl;

import com.jhon.rain.common.keyprefix.OrderKey;
import com.jhon.rain.common.redis.RedisHelper;
import com.jhon.rain.dao.OrderDAO;
import com.jhon.rain.entity.Order;
import com.jhon.rain.entity.SecKillOrder;
import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.GoodsVO;
import com.jhon.rain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>功能描述</br>订单用外接口实现类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:55
 */
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderDAO orderDAO;

  @Autowired
  private RedisHelper redisHelper;

  @Override
  public SecKillOrder getSecKillOrderByUserIdGoodsId(String mobile, Long goodsId) {
    return redisHelper.get(OrderKey.getSecKillOrderByUidGid, mobile + "_" + goodsId, SecKillOrder.class);
  }

  @Override
  public Order getOrderById(Long orderId) {
    return orderDAO.getOrderById(orderId);
  }

  @Override
  @Transactional
  public Order createOrderInfo(User user, GoodsVO goods) {
    Order orderInfo = new Order();
    orderInfo.setCreateDate(new Date());
    orderInfo.setDeliveryAddrId(0L);
    orderInfo.setGoodsCount(1);
    orderInfo.setGoodsId(goods.getId());
    orderInfo.setGoodsName(goods.getGoodsName());
    orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
    orderInfo.setOrderChannel(1);
    orderInfo.setStatus(0);
    orderInfo.setUserId(user.getId());
    long orderId = orderDAO.insert(orderInfo);
    SecKillOrder secKillOrder = new SecKillOrder();
    secKillOrder.setGoodsId(goods.getId());
    secKillOrder.setOrderId(orderId);
    secKillOrder.setUserId(user.getId());
    orderDAO.insertSecKillOrder(secKillOrder);
    // 模拟事务回滚 int a = 1/0;
    return orderInfo;
  }
}
