package com.jhon.rain.dao;

import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.GoodsVO;
import lombok.Data;
import lombok.ToString;

/**
 * <p>功能描述</br>商品详情</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/17 13:40
 */
@Data
@ToString
public class GoodsDetailVO {

  /**
   * 秒杀状态
   */
  private int miaoshaStatus;

  /**
   * 身下时间
   */
  private int remainSeconds;
  /**
   * 商品
   */
  private GoodsVO goods;
  /**
   * 用户
   */
  private User user;
}
