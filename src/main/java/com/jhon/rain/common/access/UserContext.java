package com.jhon.rain.common.access;

import com.jhon.rain.entity.User;

/**
 * <p>功能描述</br>用户上下文对象</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 9:41
 */
public class UserContext {

  private static ThreadLocal<User> userContextHolder = new ThreadLocal<User>();

  /**
   * 设置用户
   *
   * @param user
   */
  public static void setUser(User user) {
    userContextHolder.set(user);
  }

  /**
   * 获取用户
   */
  public static User getUser() {
    return userContextHolder.get();
  }
}
