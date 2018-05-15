package com.jhon.rain.pojo.vo;

import lombok.Data;

/**
 * <p>功能描述</br>用户登录的参数</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 17:00
 */
@Data
public class LoginVO {

  private String mobile;

  private String password;
}
