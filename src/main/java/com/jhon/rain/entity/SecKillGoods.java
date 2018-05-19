package com.jhon.rain.entity;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>功能描述</br>秒杀商品</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:43
 */
@Data
@ToString
public class SecKillGoods {

  /**
   * 主键ID
   */
  private Long id;
  /**
   * 商品ID
   */
  private Long goodsId;
  /**
   * 秒杀价格
   */
  private BigDecimal miaoshaPrice;
  /**
   * 库存
   */
  private Integer stockCount;
  /**
   * 开始时间
   */
  private Date startDate;
  /**
   * 结束时间
   */
  private Date endDate;
}
