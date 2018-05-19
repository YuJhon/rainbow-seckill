package com.jhon.rain.controller;

import com.jhon.rain.common.Constants;
import com.jhon.rain.common.keyprefix.GoodsKey;
import com.jhon.rain.common.keyprefix.OrderKey;
import com.jhon.rain.common.keyprefix.SecKillKey;
import com.jhon.rain.common.redis.RedisHelper;
import com.jhon.rain.common.response.RainCodeMsg;
import com.jhon.rain.common.response.RainResponse;
import com.jhon.rain.entity.SecKillOrder;
import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.GoodsVO;
import com.jhon.rain.service.GoodsService;
import com.jhon.rain.service.OrderService;
import com.jhon.rain.service.SecKillService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * <p>功能描述</br>秒杀控制器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/18 9:14
 */
@Controller
@RequestMapping("/secKill")
public class SecKillController implements InitializingBean {


  @Autowired
  private GoodsService goodsService;

  @Autowired
  private SecKillService secKillService;

  @Autowired
  private OrderService orderService;

  @Autowired
  private RedisHelper redisHelper;

  private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

  @Override
  public void afterPropertiesSet() throws Exception {
    List<GoodsVO> goodsList = goodsService.listGoodsVO();
    if (goodsList == null) {
      return;
    }
    for (GoodsVO goods : goodsList) {
      redisHelper.set(GoodsKey.getSeckillGoodsStock, "" + goods.getId(), goods.getStockCount());
      localOverMap.put(goods.getId(), false);
    }
  }


  /**
   * <pre>获取验证码</pre>
   *
   * @param response
   * @param user     用户
   * @param goodsId  商品ID
   * @return
   */
  @GetMapping("/verifyCode")
  @ResponseBody
  public RainResponse<String> getSecKillVerifyCode(HttpServletResponse response, User user,
                                                   @RequestParam("goodsId") long goodsId) {
    if (user == null) {
      return RainResponse.error(RainCodeMsg.SESSION_ERROR);
    }
    OutputStream out = null;
    try {
      BufferedImage image = secKillService.generateVerifyCode(user, goodsId);
      out = response.getOutputStream();
      ImageIO.write(image, Constants.VerifyCodeConstants.IMG_FORMAT, out);
      out.flush();
    } catch (Exception e) {
      e.printStackTrace();
      return RainResponse.error(RainCodeMsg.GENERATE_VERIFY_CODE_ERROR);
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return RainResponse.success("success");
  }

  /**
   * <pre>生成秒杀路径</pre>
   *
   * @param user       用户
   * @param goodsId    商品Id
   * @param verifyCode 验证码
   * @return
   */
  @GetMapping("/path")
  @ResponseBody
  public RainResponse<String> getSecKillPath(User user, @RequestParam("goodsId") Long goodsId,
                                             @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode) {
    /** 用户校验 **/
    if (user == null) {
      return RainResponse.error(RainCodeMsg.SESSION_ERROR);
    }
    /** 验证码的校验 **/
    boolean check = secKillService.checkVerifyCode(user, goodsId, verifyCode);
    if (!check) {
      return RainResponse.error(RainCodeMsg.REQUEST_ILLEGAL);
    }
    String path = secKillService.generateSecKillPath(user, goodsId);
    return RainResponse.success(path);
  }

  /**
   * <pre>秒杀处理</pre>
   *
   * @param model   页面模型
   * @param user    当前登陆用户
   * @param goodsId 商品ID
   * @param path    秒杀路径
   * @return
   */
  @PostMapping("/{path}/do_secKill")
  @ResponseBody
  public RainResponse<Integer> secKillProcess(Model model, User user,
                                              @RequestParam("goodsId") Long goodsId,
                                              @PathVariable("path") String path) {
    /** 1.用户的校验 **/
    model.addAttribute("user", user);
    if (user == null) {
      return RainResponse.error(RainCodeMsg.SESSION_ERROR);
    }
    /** 2.验证path **/
    boolean check = secKillService.checkPath(user, goodsId, path);
    if (!check) {
      return RainResponse.error(RainCodeMsg.REQUEST_ILLEGAL);
    }
    /** 3.内存标记，减少redis访问 **/
    boolean over = localOverMap.get(goodsId);
    if (over) {
      return RainResponse.error(RainCodeMsg.SEC_KILL_OVER);
    }
    /** 4.预减库存 **/
    long stock = redisHelper.decr(GoodsKey.getSeckillGoodsStock, "" + goodsId);
    if (stock < 0) {
      localOverMap.put(goodsId, true);
      return RainResponse.error(RainCodeMsg.SEC_KILL_OVER);
    }
    /** 5.判断是否已经秒杀 **/
    SecKillOrder order = orderService.getSecKillOrderByUserIdGoodsId(user.getMobile(), goodsId);
    if (order != null) {
      return RainResponse.error(RainCodeMsg.REPEAT_SEC_KILL);
    }
    /** 6.发送消息，通知生成订单 **/

    return RainResponse.success(0);
  }

  /**
   * <pre>获取秒杀结果</pre>
   *
   * @param model   页面视图模型
   * @param user    当前用户
   * @param goodsId 商品ID
   * @return
   */
  @GetMapping("/result")
  @ResponseBody
  public RainResponse<Long> secKillResult(Model model, User user,
                                          @RequestParam("goodsId") Long goodsId) {
    model.addAttribute("user", user);
    if (user == null) {
      return RainResponse.error(RainCodeMsg.SESSION_ERROR);
    }
    long result = secKillService.getSecKillResult(user, goodsId);
    return RainResponse.success(result);
  }

  /**
   * <pre>重置</pre>
   *
   * @param model 模型
   * @return
   */
  @GetMapping("reset")
  @ResponseBody
  public RainResponse<Boolean> reset(Model model) {
    List<GoodsVO> goodsList = goodsService.listGoodsVO();
    for (GoodsVO goods : goodsList) {
      goods.setStockCount(10);
      redisHelper.set(GoodsKey.getSeckillGoodsStock, "" + goods.getId(), 10);
      localOverMap.put(goods.getId(), false);
    }
    redisHelper.delete(OrderKey.getSecKillOrderByUidGid);
    redisHelper.delete(SecKillKey.goodsOver);
    secKillService.reset(goodsList);
    return RainResponse.success(true);
  }

}
