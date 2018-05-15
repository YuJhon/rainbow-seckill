package com.jhon.rain.common.exception;

import com.jhon.rain.common.response.RainCodeMsg;
import lombok.Getter;

/**
 * <p>功能描述</br>自定义全局异常</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/2/7 9:10
 */
public class RainGlobalException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  @Getter
  private RainCodeMsg rainCodeMsg;

  /**
   * <pre>全局异常初始化</pre>
   *
   * @param rainCodeMsg
   */
  public RainGlobalException(RainCodeMsg rainCodeMsg) {
    super(rainCodeMsg.toString());
    this.rainCodeMsg = rainCodeMsg;
  }
}
