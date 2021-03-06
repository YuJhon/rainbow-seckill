package com.jhon.rain.common.keyprefix;

import com.jhon.rain.common.Constants;

/**
 * <p>功能描述</br>用户key</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 20:13
 */
public class UserKey extends BasePrefix {

  /**
   * <pre>构造器</pre>
   *
   * @param expireSeconds
   * @param prefix
   */
  public UserKey(int expireSeconds, String prefix) {
    super(expireSeconds, prefix);
  }

  /**
   * token
   */
  public static UserKey token = new UserKey(Constants.TOKEN_EXPIRE, Constants.TK);

  /**
   * mobile
   */
  public static UserKey getByPhone = new UserKey(Constants.NEVER_EXPIRE, Constants.PHONE);

}
