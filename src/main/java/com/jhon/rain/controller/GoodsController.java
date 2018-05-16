package com.jhon.rain.controller;

import com.jhon.rain.common.redis.RedisHelper;
import com.jhon.rain.service.GoodsService;
import com.jhon.rain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>功能描述</br>商品控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:32
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

  @Autowired
  private RedisHelper redisHelper;

  @Autowired
  private UserService userService;

  @Autowired
  private GoodsService goodsService;


}
