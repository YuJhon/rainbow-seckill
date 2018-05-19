package com.jhon.rain.common.access;

import com.alibaba.fastjson.JSON;
import com.jhon.rain.common.Constants;
import com.jhon.rain.common.keyprefix.AccessKey;
import com.jhon.rain.common.redis.RedisHelper;
import com.jhon.rain.common.response.RainCodeMsg;
import com.jhon.rain.common.response.RainResponse;
import com.jhon.rain.entity.User;
import com.jhon.rain.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * <p>功能描述</br>接入拦截器</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 10:28
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  UserService userService;

  @Autowired
  RedisHelper redisHelper;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod) {
      User user = getUser(request, response);
      UserContext.setUser(user);
      HandlerMethod hm = (HandlerMethod) handler;
      AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
      if (accessLimit == null) {
        return true;
      }
      /** 获取限制 **/
      int seconds = accessLimit.seconds();
      int maxCount = accessLimit.maxCount();
      boolean needLogin = accessLimit.needLogin();
      String urlLink = request.getRequestURI();
      if (needLogin) {
        if (user == null) {
          render(response, RainCodeMsg.SESSION_ERROR);
          return false;
        }
        urlLink += "_" + user.getMobile();
      }

      /** 判断访问次数 **/
      AccessKey ak = AccessKey.withExpire(seconds);
      Integer count = redisHelper.get(ak, urlLink, Integer.class);
      if (count == null) {
        redisHelper.set(ak, urlLink, 1);
      } else if (count < maxCount) {
        redisHelper.incr(ak, urlLink);
      } else {
        render(response, RainCodeMsg.ACCESS_LIMIT_REACHED);
        return false;
      }
    }
    return true;
  }

  /**
   * <pre>错误信息渲染</pre>
   *
   * @param response
   * @param cm
   * @throws Exception
   */
  private void render(HttpServletResponse response, RainCodeMsg cm) throws Exception {
    response.setContentType("application/json;charset=UTF-8");
    OutputStream out = response.getOutputStream();
    String str = JSON.toJSONString(RainResponse.error(cm));
    out.write(str.getBytes());
    out.flush();
    out.close();
  }

  /**
   * <pre>获取用户信息</pre>
   *
   * @param request
   * @param response
   * @return
   */
  private User getUser(HttpServletRequest request, HttpServletResponse response) {
    String paramToken = request.getParameter(Constants.COOKIE_NAME_TOKEN);
    String cookieToken = getCookie(request, Constants.COOKIE_NAME_TOKEN);
    if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
      return null;
    }
    String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
    return userService.getByToken(response, token);
  }

  /**
   * <pre>从cookie中获取数据</pre>
   *
   * @param request
   * @param cookieName
   * @return
   */
  private String getCookie(HttpServletRequest request, String cookieName) {
    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length <= 0) {
      return null;
    }
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(cookieName)) {
        return cookie.getValue();
      }
    }
    return null;
  }
}
