package com.jhon.rain.common.keyprefix;

/**
 * <p>功能描述</br>键名称的基类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 20:08
 */
public abstract class BasePrefix implements KeyPrefix {

  /**
   * 过期的妙
   */
  private int expireSeconds;

  /**
   * 前缀
   */
  private String prefix;

  public BasePrefix(String prefix) {
    this(0, prefix);
  }

  public BasePrefix(int expireSeconds, String prefix) {
    this.expireSeconds = expireSeconds;
    this.prefix = prefix;
  }

  @Override
  public int expireSeconds() {
    return expireSeconds;
  }

  @Override
  public String getPrefix() {
    String className = getClass().getSimpleName();
    return className + ":" + prefix;
  }
}
