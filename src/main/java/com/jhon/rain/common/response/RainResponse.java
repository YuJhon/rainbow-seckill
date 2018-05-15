package com.jhon.rain.common.response;

import lombok.Data;

/**
 * <p>功能描述</br>统一返回值对象</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/2/6 20:16
 */
@Data
public class RainResponse<T> {
  /**
   * 返回码
   */
  private Integer code;
  /**
   * 返回消息
   */
  private String msg;
  /**
   * 返回数据
   */
  private T data;

  public static <T> RainResponse<T> success(T data) {
    return new RainResponse<T>(data);
  }

  public static <T> RainResponse<T> error(RainCodeMsg codeMsg) {
    return new RainResponse<T>(codeMsg);
  }

  private RainResponse(T data) {
    this.data = data;
  }

  private RainResponse(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  private RainResponse(RainCodeMsg codeMsg) {
    if (codeMsg != null) {
      this.code = codeMsg.getCode();
      this.msg = codeMsg.getMsg();
    }
  }

}
