package com.jhon.rain.controller;

import com.jhon.rain.common.keyprefix.GoodsKey;
import com.jhon.rain.common.redis.RedisHelper;
import com.jhon.rain.common.response.RainResponse;
import com.jhon.rain.dao.GoodsDetailVO;
import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.GoodsVO;
import com.jhon.rain.service.GoodsService;
import com.jhon.rain.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
  private GoodsService goodsService;

  @Autowired
  ThymeleafViewResolver thymeleafViewResolver;

  @Autowired
  ApplicationContext applicationContext;


  @RequestMapping(value = "/to_list", produces = "text/html")
  @ResponseBody
  public String list(HttpServletRequest request, HttpServletResponse response,
                     Model model, User user) {

    model.addAttribute("user", user);
    //取缓存
    String html = redisHelper.get(GoodsKey.getGoodsList, "", String.class);
    if (!StringUtils.isEmpty(html)) {
      return html;
    }
    List<GoodsVO> goodsList = goodsService.listGoodsVO();
    model.addAttribute("goodsList", goodsList);

    SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),
            request.getLocale(), model.asMap(), applicationContext);

    /** 手动渲染 **/
    html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
    if (!StringUtils.isEmpty(html)) {
      redisHelper.set(GoodsKey.getGoodsList, "", html);
    }
    return html;
  }


  @RequestMapping(value = "/detail/{goodsId}")
  @ResponseBody
  public RainResponse<GoodsDetailVO> detail(HttpServletRequest request, HttpServletResponse response,
                                            Model model, User user, @PathVariable("goodsId") long goodsId) {
    /** 获取商品的ID **/
    GoodsVO goods = goodsService.getGoodsVOByGoodsId(goodsId);
    long startAt = goods.getStartDate().getTime();
    long endAt = goods.getEndDate().getTime();
    long now = System.currentTimeMillis();
    int secKillStatus = 0;
    int remainSeconds = 0;

    if (now < startAt) {
      /** 秒杀还没开始 **/
      secKillStatus = 0;
      remainSeconds = (int) ((startAt - now) / 1000);
    } else if (now > endAt) {
      /** 秒杀已经结束 **/
      secKillStatus = 2;
      remainSeconds = -1;
    } else {
      /** 秒杀进行中 **/
      secKillStatus = 1;
      remainSeconds = 0;
    }
    GoodsDetailVO goodsDetail = new GoodsDetailVO();
    goodsDetail.setGoods(goods);
    goodsDetail.setUser(user);
    goodsDetail.setRemainSeconds(remainSeconds);
    goodsDetail.setMiaoshaStatus(secKillStatus);
    return RainResponse.success(goodsDetail);
  }
}
