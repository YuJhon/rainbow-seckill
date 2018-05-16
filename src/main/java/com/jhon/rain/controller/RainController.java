package com.jhon.rain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>功能描述</br>测试控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/2/6 20:21
 */
@Controller
@RequestMapping("/")
public class RainController {

  @GetMapping("/index")
  public String index(Model model) {
    model.addAttribute("name", "Jhon");
    return "index";
  }
}
