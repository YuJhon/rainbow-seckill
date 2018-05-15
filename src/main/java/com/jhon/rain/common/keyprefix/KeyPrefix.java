package com.jhon.rain.common.keyprefix;

/**
 * <p>功能描述</br>键前缀</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 20:06
 */
public interface KeyPrefix {

  /**
   * 过期时间
   *
   * @return
   */
  int expireSeconds();

  /**
   * 获取前缀
   *
   * @return
   */
  String getPrefix();
}
