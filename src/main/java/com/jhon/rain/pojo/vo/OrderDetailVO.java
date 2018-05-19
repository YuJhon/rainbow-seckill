package com.jhon.rain.pojo.vo;

import com.jhon.rain.entity.Order;
import lombok.Data;
import lombok.ToString;

/**
 * <p>功能描述</br>订单详细信息VO对象</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/19 8:45
 */
@Data
@ToString
public class OrderDetailVO {
  /**
   * 商品信息
   */
  private GoodsVO goodsVO;
  /**
   * 订单信息
   */
  private Order order;
}
