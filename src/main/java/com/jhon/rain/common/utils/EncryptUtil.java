package com.jhon.rain.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>功能描述</br>加密工具类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 17:09
 */
public class EncryptUtil {

  private static final String SALT = "1a2b3c4d";

  /**
   * <pre>MD5加密方法</pre>
   *
   * @param source 需要加密的字符串
   * @return
   */
  public static String md5(String source) {
    return DigestUtils.md5Hex(source);
  }

  /**
   * <pre>用户输入密码第一次加密</pre>
   *
   * @param inputPass
   * @return
   */
  public static String inputPassToFormPass(String inputPass) {
    String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5)
            + SALT.charAt(4);
    System.out.println(str);
    return md5(str);
  }

  /**
   * <pre>表单加密后的密码到数据库密码</pre>
   *
   * @param formPass
   * @param salt
   * @return
   */
  public static String formPassToDBPass(String formPass, String salt) {
    String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
    return md5(str);
  }

  /**
   * <pre>用户的明文密码到数据库加密密码</pre>
   *
   * @param inputPass
   * @param saltDB
   * @return
   */
  public static String inputPassToDbPass(String inputPass, String saltDB) {
    String formPass = inputPassToFormPass(inputPass);
    String dbPass = formPassToDBPass(formPass, saltDB);
    return dbPass;
  }
}
