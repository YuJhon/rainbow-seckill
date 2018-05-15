package com.jhon.rain.common;

/**
 * <p>功能描述</br>常量类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 20:03
 */
public class Constants {

  /**
   * cookie的名称
   **/
  public static final String COOKIE_NAME_TOKEN = "sec-kill-token";

  /**
   * token过期时间（2天）
   */
  public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

  /**
   * 永远不过期
   */
  public static final int NEVER_EXPIRE = 0;

  /**
   * token的标识
   */
  public static final String TK = "tk";

  /**
   * mobile的标识
   */
  public static final String PHONE = "mobile";
}
