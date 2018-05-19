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

  /**
   * 接入标识
   **/
  public static final String ACCESS = "access";

  /**
   * 验证码相关常量
   */
  public class VerifyCodeConstants {

    /**
     * 验证码图片的宽度
     */
    public static final int IMAGE_WIDTH = 80;

    /**
     * 验证码图片的高度
     */
    public static final int IMAGE_HEIGHT = 32;

    /**
     * 字体大小
     */
    public static final int FONT_SIZE = 24;

    /**
     * 字体名称
     */
    public static final String FONT_NAME = "candara";
    /**
     * 干扰元素的个数
     */
    public static final int CONFUSION_COUNT = 50;

    /**
     * 盐值
     */
    public static final String SALT = "123456";

    /**
     * 图片格式
     */
    public static final String IMG_FORMAT = "JPEG";
  }
}
