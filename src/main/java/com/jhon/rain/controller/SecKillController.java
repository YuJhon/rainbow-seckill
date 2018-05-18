package com.jhon.rain.controller;

import com.jhon.rain.common.response.RainCodeMsg;
import com.jhon.rain.common.response.RainResponse;
import com.jhon.rain.entity.User;
import com.jhon.rain.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

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
public class SecKillController {


  @Autowired
  private SecKillService secKillService;

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
      ImageIO.write(image, "JPEG", out);
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
   * @param response
   * @param user       用户
   * @param goodsId    商品Id
   * @param verifyCode 验证码
   * @return
   */
  @GetMapping("/path")
  @ResponseBody
  public RainResponse<String> getSecKillPath(HttpServletResponse response, User user,
                                             @RequestParam("goodsId") Long goodsId,
                                             @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode) {
    if (user == null) {
      return RainResponse.error(RainCodeMsg.SESSION_ERROR);
    }
    boolean check = secKillService.checkVerifyCode(user, goodsId, verifyCode);
    if (!check) {
      return RainResponse.error(RainCodeMsg.REQUEST_ILLEGAL);
    }
    String path = secKillService.generateSecKillPath(user, goodsId);
    return RainResponse.success(path);
  }


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

}
