package com.jhon.rain.pojo.vo;

import com.jhon.rain.entity.Goods;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>功能描述</br>商品视图对象</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/17 9:16
 */
@Data
@ToString
public class GoodsVO extends Goods {

  /**
   * 秒杀价格
   */
  private BigDecimal miaoshaPrice;

  /**
   * 库存数量
   */
  private Integer stockCount;
  /**
   * 开始日期
   */
  private Date startDate;
  /**
   * 结束日期
   */
  private Date endDate;
}
