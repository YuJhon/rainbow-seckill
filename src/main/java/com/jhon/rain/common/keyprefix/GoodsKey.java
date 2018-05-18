package com.jhon.rain.common.keyprefix;

/**
 * <p>功能描述</br>Redis中存放的商品键</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/17 9:49
 */
public class GoodsKey extends BasePrefix {

  public GoodsKey(int expireSeconds, String prefix) {
    super(expireSeconds, prefix);
  }

  public static GoodsKey getGoodsList = new GoodsKey(60, "gl");

  public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");

  public static GoodsKey getSeckillGoodsStock = new GoodsKey(0, "gs");


}
