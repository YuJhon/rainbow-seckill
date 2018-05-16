package com.jhon.rain.controller;

import com.jhon.rain.common.response.RainResponse;
import com.jhon.rain.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>功能描述</br>查询用户信息</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:34
 */
@Controller
@RequestMapping("/user")
public class UserController {

  /**
   * <pre>查询用户信息</pre>
   *
   * @param model
   * @param user
   * @return
   */
  @RequestMapping("/info")
  @ResponseBody
  public RainResponse<User> info(Model model, User user) {
    return RainResponse.success(user);
  }
}
