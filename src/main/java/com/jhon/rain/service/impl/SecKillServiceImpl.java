package com.jhon.rain.service.impl;

import com.jhon.rain.common.Constants;
import com.jhon.rain.common.keyprefix.OrderKey;
import com.jhon.rain.common.keyprefix.SecKillKey;
import com.jhon.rain.common.redis.RedisHelper;
import com.jhon.rain.common.utils.EncryptUtil;
import com.jhon.rain.common.utils.UUIDUtil;
import com.jhon.rain.common.utils.VerifyCodeUtil;
import com.jhon.rain.dao.GoodsDAO;
import com.jhon.rain.dao.OrderDAO;
import com.jhon.rain.entity.Order;
import com.jhon.rain.entity.SecKillOrder;
import com.jhon.rain.entity.SecKillGoods;
import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.GoodsVO;
import com.jhon.rain.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * <p>功能描述</br>秒杀业务逻辑实现</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/18 9:51
 */
@Service
public class SecKillServiceImpl implements SecKillService {

  @Autowired
  private RedisHelper redisHelper;

  @Autowired
  private OrderDAO orderDAO;

  @Autowired
  private GoodsDAO goodsDAO;

  @Override
  @Deprecated
  @Transactional
  public Order secKillProcessV1(User user, GoodsVO goods) {
    /** 减少库存 **/
    SecKillGoods secKillGoods = new SecKillGoods();
    secKillGoods.setGoodsId(goods.getId());
    goodsDAO.reduceStock(secKillGoods);
    /** 创建订单 **/
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

    redisHelper.set(OrderKey.getSecKillOrderByUidGid, "" + user.getId() + "_" + goods.getId(), secKillOrder);
    return orderInfo;
  }

  @Override
  public BufferedImage generateVerifyCode(User user, long goodsId) {
    if (user == null || goodsId < 0) {
      return null;
    }
    /** 1.定义图纸 **/
    BufferedImage image = new BufferedImage(Constants.VerifyCodeConstants.IMAGE_WIDTH, Constants.VerifyCodeConstants.IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
    /** 2.设置背景颜色  **/
    Graphics graphics = image.getGraphics();
    graphics.setColor(new Color(0xDCDCDC));
    graphics.fillRect(0, 0, Constants.VerifyCodeConstants.IMAGE_WIDTH - 1, Constants.VerifyCodeConstants.IMAGE_HEIGHT - 1);
    Random random = new Random();
    /** 3.设置一些干扰信息 **/
    for (int i = 0; i < Constants.VerifyCodeConstants.CONFUSION_COUNT; i++) {
      int x = random.nextInt(Constants.VerifyCodeConstants.IMAGE_WIDTH);
      int y = random.nextInt(Constants.VerifyCodeConstants.IMAGE_HEIGHT);
      graphics.drawOval(x, y, 0, 0);
    }
    /** 4.获取验证码表达式（加减乘算法） **/
    String verifyCodeExpression = VerifyCodeUtil.generateVerifyCodeExpression(random);
    graphics.setColor(new Color(0, 100, 0));
    graphics.setFont(new Font(Constants.VerifyCodeConstants.FONT_NAME, Font.BOLD, Constants.VerifyCodeConstants.FONT_SIZE));
    graphics.drawString(verifyCodeExpression, 8, 24);
    graphics.dispose();
    /** 5.将验证码计算出来的结果缓存到redis中 **/
    int rnd = VerifyCodeUtil.calcVerifyCodeExpression(verifyCodeExpression);
    redisHelper.set(SecKillKey.secKillVerifyCode, user.getMobile() + "," + goodsId, rnd);
    return image;
  }


  @Override
  public boolean checkVerifyCode(User user, Long goodsId, int verifyCodeResult) {
    if (user == null || goodsId <= 0) {
      return false;
    }
    /** 1.从缓存中获取值 **/
    Integer backCalcResult = redisHelper.get(SecKillKey.secKillVerifyCode, user.getMobile() + "," + goodsId, Integer.class);
    /** 2.结果的校验 **/
    if (backCalcResult == null || verifyCodeResult - backCalcResult != 0) {
      return false;
    }
    /** 3.清除缓存中的记录 **/
    redisHelper.delete(SecKillKey.secKillVerifyCode, user.getMobile() + "," + goodsId);
    return true;
  }

  @Override
  public String generateSecKillPath(User user, Long goodsId) {
    if (user == null || goodsId <= 0) {
      return null;
    }
    String path = EncryptUtil.md5(UUIDUtil.uuid() + Constants.VerifyCodeConstants.SALT);
    redisHelper.set(SecKillKey.secKillPath, user.getMobile() + "-" + goodsId, path);
    return path;
  }


  @Override
  public boolean checkPath(User user, Long goodsId, String path) {
    if (user == null || path == null) {
      return false;
    }
    String dbPath = redisHelper.get(SecKillKey.secKillPath, user.getMobile() + "-" + goodsId, String.class);
    return path.equals(dbPath);
  }

  @Override
  public void reset(List<GoodsVO> goodsList) {
    this.resetStock(goodsList);
    this.deleteOrders();
  }

  /**
   * <pre>重置库存</pre>
   *
   * @param goodsList
   */
  public void resetStock(List<GoodsVO> goodsList) {
    for (GoodsVO goods : goodsList) {
      SecKillGoods secKillGoods = new SecKillGoods();
      secKillGoods.setGoodsId(goods.getId());
      secKillGoods.setStockCount(goods.getStockCount());
      goodsDAO.resetStock(secKillGoods);
    }
  }

  /**
   * <pre>删除订单信息</pre>
   */
  public void deleteOrders() {
    orderDAO.deleteOrders();
    orderDAO.deleteSecKillOrders();
  }

  @Override
  public long getSecKillResult(User user, Long goodsId) {
    SecKillOrder order = redisHelper.get(OrderKey.getSecKillOrderByUidGid, "" + user.getMobile() + "_" + goodsId, SecKillOrder.class);
    if (order != null) {
      return order.getOrderId();
    } else {
      boolean isOver = getGoodsOver(goodsId);
      if (isOver) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  /**
   * <pre>获取商品是否已经秒杀完成了</pre>
   *
   * @param goodsId 商品ID
   * @return
   */
  private boolean getGoodsOver(Long goodsId) {
    return redisHelper.exist(SecKillKey.goodsOver, "" + goodsId);
  }
}
