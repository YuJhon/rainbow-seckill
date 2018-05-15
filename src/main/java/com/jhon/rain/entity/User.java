package com.jhon.rain.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>功能描述</br>用户</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 15:51
 */
@Data
public class User {

  /** 主键 **/
  private Long id;

  /** 昵称 **/
  private String nickname;

  /** 电话 **/
  private String mobile;

  /** 盐值 **/
  private String salt;

  /** 密码 **/
  private String password;

  /** 头像 **/
  private String headImg;

  /** 注册日期 **/
  private Date registerDate;

  /** 最后一次登录时间 **/
  private Date lastLoginDate;

  /** 登录次数 **/
  private Integer loginCount;

}
