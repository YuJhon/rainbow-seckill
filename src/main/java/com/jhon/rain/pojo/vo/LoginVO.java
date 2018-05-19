package com.jhon.rain.pojo.vo;

import com.jhon.rain.common.validator.IsMobile;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * <p>功能描述</br>用户登录的参数</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 17:00
 */
@Data
@ToString
public class LoginVO {

  @NotNull
  //@IsMobile
  private String mobile;

  @NotNull
  @Length(min = 32)
  private String password;
}
