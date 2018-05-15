package com.jhon.rain.common;

import lombok.Getter;

/**
 * <p>功能描述</br>错误消息枚举</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/2/7 9:14
 */
public enum CodeMsgEnums {

  /** 系统繁忙错误提示 **/
  SERVER_ERROR(500100, "系统繁忙，请稍后再试！");

  @Getter
  private Integer code;

  @Getter
  private String desc;

  CodeMsgEnums(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
