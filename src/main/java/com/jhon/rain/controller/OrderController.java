package com.jhon.rain.controller;

import com.jhon.rain.common.response.RainCodeMsg;
import com.jhon.rain.common.response.RainResponse;
import com.jhon.rain.entity.Order;
import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.GoodsVO;
import com.jhon.rain.pojo.vo.OrderDetailVO;
import com.jhon.rain.service.GoodsService;
import com.jhon.rain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>功能描述</br>订单控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/19 8:41
 */
@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private GoodsService goodsService;

  @Autowired
  private OrderService orderService;

  /**
   * <pre>查询订单详细信息</pre>
   *
   * @param user    用户
   * @param orderId 订单ID
   * @return
   */
  @RequestMapping("/detail")
  public RainResponse<OrderDetailVO> orderInfo(User user, @RequestParam("orderId") Long orderId) {
    if (user == null) {
      return RainResponse.error(RainCodeMsg.SESSION_ERROR);
    }
    Order order = orderService.getOrderById(orderId);
    if (order == null) {
      return RainResponse.error(RainCodeMsg.ORDER_NOT_EXIST);
    }
    long goodsId = order.getGoodsId();
    GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
    OrderDetailVO detailVO = new OrderDetailVO();
    detailVO.setOrder(order);
    detailVO.setGoodsVO(goods);
    return RainResponse.success(detailVO);
  }
}
