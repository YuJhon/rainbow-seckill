package com.jhon.rain.pojo.dto;

import com.jhon.rain.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>功能描述</br>秒杀消息体</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/19 15:37
 */
@Getter
@Setter
@ToString
public class SecKillMessage {

  /**
   * 用户
   */
  private User user;
  /**
   * 商品
   */
  private long goodsId;
}
