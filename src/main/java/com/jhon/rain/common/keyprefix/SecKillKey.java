package com.jhon.rain.common.keyprefix;

/**
 * <p>功能描述</br>秒杀键定义</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/18 10:46
 */
public class SecKillKey extends BasePrefix {

  public SecKillKey(int expireSeconds, String prefix) {
    super(expireSeconds, prefix);
  }

  /**
   * 商品已经没有了
   */
  public static SecKillKey goodsOver = new SecKillKey(0, "goodsOver");
  /**
   * 秒杀路径
   */
  public static SecKillKey secKillPath = new SecKillKey(60, "secKillPath");

  /**
   * 秒杀验证码
   */
  public static SecKillKey secKillVerifyCode = new SecKillKey(300, "secKillVerifyCode");
}
