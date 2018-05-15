package com.jhon.rain.controller;

import com.jhon.rain.common.response.RainResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>功能描述</br>用户登录</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 16:19
 */
@RequestMapping("/login")
@Controller
public class LoginController {


  @GetMapping("/gotoLogin")
  public String gotoLogin() {
    return "login";
  }


  @PostMapping("/doLogin")
  @ResponseBody
  public RainResponse<String> doLogin() {
    return RainResponse.success("");
  }
}
