package com.jhon.rain.service;

import com.jhon.rain.entity.User;

import java.awt.image.BufferedImage;

/**
 * <p>功能描述</br>秒杀服务</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/18 9:39
 */
public interface SecKillService {
  /**
   * <pre>生成验证码</pre>
   *
   * @param user    用户
   * @param goodsId 商品ID
   * @return
   */
  BufferedImage generateVerifyCode(User user, long goodsId);

  /**
   * <pre>验证码的校验</pre>
   *
   * @param user             用户信息
   * @param goodsId          商品Id
   * @param verifyCodeResult 验证码
   * @return
   */
  boolean checkVerifyCode(User user, Long goodsId, int verifyCodeResult);

  /**
   * <pre>生成秒杀路径</pre>
   *
   * @param user    用户信息
   * @param goodsId 商品ID
   * @return
   */
  String generateSecKillPath(User user, Long goodsId);

  /**
   * <pre>获取秒杀结果</pre>
   *
   * @param user    用户信息
   * @param goodsId 商品Id
   * @return
   */
  long getSecKillResult(User user, Long goodsId);

  /**
   * <pre>校验path</pre>
   *
   * @param user    用户信息
   * @param goodsId 商品Id
   * @param path    路径
   * @return
   */
  boolean checkPath(User user, Long goodsId, String path);
}
