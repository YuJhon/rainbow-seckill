package com.jhon.rain.common.keyprefix;

import com.jhon.rain.common.Constants;

/**
 * <p>功能描述</br></p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 11:11
 */
public class AccessKey extends BasePrefix {

  public AccessKey(int expireSeconds, String prefix) {
    super(expireSeconds, prefix);
  }

  /**
   * <pre>设置过期时间</pre>
   *
   * @param expireSeonds
   * @return
   */
  public static AccessKey withExpire(int expireSeonds) {
    return new AccessKey(expireSeonds, Constants.ACCESS);
  }
}
