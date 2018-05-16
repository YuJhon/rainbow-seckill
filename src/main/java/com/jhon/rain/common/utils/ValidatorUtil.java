package com.jhon.rain.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>功能描述</br>校验工具类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 10:12
 */
public class ValidatorUtil {

  private static final Pattern MOBILE_PATTERN = Pattern.compile("1[3,5,7,8,9]\\d{9}");

  /**
   * <pre>通过正则判断是否是手机号</pre>
   *
   * @param src
   * @return
   */
  public static boolean isMobile(String src) {
    if (StringUtils.isEmpty(src)) {
      return false;
    }
    Matcher m = MOBILE_PATTERN.matcher(src);
    return m.matches();
  }
}
