package com.jhon.rain.entity;

import lombok.Data;
import lombok.ToString;

/**
 * <p>功能描述</br>商品实体</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:39
 */
@Data
@ToString
public class Goods {

  /**
   * 主键ID
   */
  private Long id;
  /**
   * 商品名称
   */
  private String goodsName;
  /**
   * 商品标题
   */
  private String goodsTitle;
  /**
   * 商品图片
   */
  private String goodsImg;

  /**
   * 商品详情
   */
  private String goodsDetail;

  /**
   * 商品价格
   */
  private String goodsPrice;

  /**
   * 商品库存
   */
  private Integer goodsStock;
}
