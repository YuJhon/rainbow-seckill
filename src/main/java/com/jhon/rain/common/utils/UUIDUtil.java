package com.jhon.rain.common.utils;


import java.util.UUID;

/**
 * <p>功能描述</br>随机字符串</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 17:09
 */
public class UUIDUtil {
  /**
   * <pre>生成随机的uuid</pre>
   *
   * @return
   */
  public static String uuid() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
