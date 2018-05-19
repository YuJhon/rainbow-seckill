package com.jhon.rain.controller;

import com.jhon.rain.common.response.RainCodeMsg;
import com.jhon.rain.common.response.RainResponse;
import com.jhon.rain.entity.Order;
import com.jhon.rain.entity.SecKillOrder;
import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.GoodsVO;
import com.jhon.rain.service.GoodsService;
import com.jhon.rain.service.OrderService;
import com.jhon.rain.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>功能描述</br>优化之前的秒杀</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/19 13:34
 */
@Controller
@RequestMapping("secKill_V1")
public class SecKillOriginController {


  @Autowired
  private GoodsService goodsService;

  @Autowired
  private OrderService orderService;

  @Autowired
  private SecKillService secKillService;

  /**
   * <pre>一般情况下的秒杀操作</pre>
   *
   * @param model   模型
   * @param user    用户信息
   * @param goodsId 商品信息
   * @return
   */
  @RequestMapping(value = "/secKill", method = RequestMethod.POST)
  @ResponseBody
  public RainResponse<Order> secKill(Model model, User user,
                                     @RequestParam("goodsId") long goodsId) {
    model.addAttribute("user", user);
    if (user == null) {
      return RainResponse.error(RainCodeMsg.SESSION_ERROR);
    }
    /** 判断库存 **/
    GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
    int stock = goods.getStockCount();
    if (stock <= 0) {
      return RainResponse.error(RainCodeMsg.SEC_KILL_OVER);
    }
    /** 判断是否已经秒杀到了 **/
    SecKillOrder order = orderService.getSecKillOrderByUserMobileGoodsId(user.getMobile(), goodsId);
    if (order != null) {
      return RainResponse.error(RainCodeMsg.REPEAT_SEC_KILL);
    }
    /** 减库存 下订单 写入秒杀订单 **/
    Order orderInfo = secKillService.secKillProcessV1(user, goods);
    return RainResponse.success(orderInfo);
  }
}
