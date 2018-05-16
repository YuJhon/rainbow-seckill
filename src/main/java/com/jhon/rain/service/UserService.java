package com.jhon.rain.service;

import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>功能描述</br>用户服务接口</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 16:29
 */
public interface UserService {

  /**
   * <pre>通过手机号查询用户</pre>
   *
   * @param phone
   * @return
   */
  User getUserByPhone(String phone);

  /**
   * <pre>登录操作</pre>
   *
   * @param response
   * @param vo
   * @return
   */
  String loginProcess(HttpServletResponse response, LoginVO vo);

  /**
   * <pre>更新密码</pre>
   *
   * @param token
   * @param mobile
   * @param formPass
   * @return
   */
  boolean updatePassword(String token, String mobile, String formPass);

  /**
   * <pre>通过token查询用户</pre>
   *
   * @param response
   * @param token
   * @return
   */
  User getByToken(HttpServletResponse response, String token);
}
