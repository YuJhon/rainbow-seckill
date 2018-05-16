package com.jhon.rain.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>功能描述</br>订单表</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:45
 */
@Data
@ToString
public class Order {

  /**
   * 主键ID
   */
  private Long id;
  /**
   * 用户Id
   */
  private Long userId;
  /**
   * 商品ID
   */
  private Long goodsId;
  /**
   * 发货地址
   */
  private Long deliveryAddrId;
  /**
   * 商品名称
   */
  private String goodsName;
  /**
   * 商品数量
   */
  private Integer goodsCount;
  /**
   * 商品价格
   */
  private BigDecimal goodsPrice;
  /**
   * 订单渠道
   */
  private Integer orderChannel;
  /**
   * 订单状态
   */
  private Integer status;
  /**
   * 创建时间
   */
  private Date createDate;
  /**
   * 支付时间
   */
  private Date payDate;
}
