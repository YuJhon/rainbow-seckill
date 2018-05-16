package com.jhon.rain.service.impl;

import com.jhon.rain.common.Constants;
import com.jhon.rain.common.exception.RainGlobalException;
import com.jhon.rain.common.keyprefix.SeckillUserKey;
import com.jhon.rain.common.redis.RedisHelper;
import com.jhon.rain.common.response.RainCodeMsg;
import com.jhon.rain.common.utils.EncryptUtil;
import com.jhon.rain.common.utils.UUIDUtil;
import com.jhon.rain.dao.UserDAO;
import com.jhon.rain.entity.User;
import com.jhon.rain.pojo.vo.LoginVO;
import com.jhon.rain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>功能描述</br>用户服务接口实现类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 16:30
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private RedisHelper redisHelper;

  @Override
  public User getUserByPhone(String phone) {
    /** 先从缓存中查询 **/
    User user = redisHelper.get(SeckillUserKey.getByPhone, phone, User.class);
    if (user != null) {
      return user;
    }
    /** 缓存中没有的话就从数据库查询 **/
    return userDAO.getUserByPhone(phone);
  }

  @Override
  public String loginProcess(HttpServletResponse response, LoginVO loginInfo) {
    if (loginInfo == null) {
      throw new RainGlobalException(RainCodeMsg.SERVER_ERROR);
    }
    /** 获取电话和密码 **/
    String mobile = loginInfo.getMobile();
    String password = loginInfo.getPassword();
    /** 判断用户是否存在 **/
    User user = getUserByPhone(mobile);
    if (user == null) {
      throw new RainGlobalException(RainCodeMsg.MOBILE_NOT_EXIST);
    }
    String dbPassword = user.getPassword();
    String salt = user.getSalt();
    String calcPass = EncryptUtil.formPassToDBPass(password, salt);
    if (!calcPass.equals(dbPassword)) {
      throw new RainGlobalException(RainCodeMsg.PASSWORD_ERROR);
    }
    /** 生成token **/
    String token = UUIDUtil.uuid();
    /** 添加到cokie中 **/
    addCookie(response, token, user);
    return token;
  }

  @Override
  public boolean updatePassword(String token, String mobile, String formPass) {
    User user = getUserByPhone(mobile);
    if (user == null) {
      throw new RainGlobalException(RainCodeMsg.MOBILE_NOT_EXIST);
    }
    /** 更新数据库 **/
    User toBeUpdate = new User();
    toBeUpdate.setMobile(mobile);
    toBeUpdate.setPassword(EncryptUtil.formPassToDBPass(formPass, user.getSalt()));
    userDAO.update(toBeUpdate);
    /** 更新缓存 **/
    redisHelper.delete(SeckillUserKey.getByPhone, mobile);
    user.setPassword(toBeUpdate.getPassword());
    redisHelper.set(SeckillUserKey.token, token, user);
    return true;
  }

  /**
   * <pre>添加cookie</pre>
   *
   * @param response 响应对象
   * @param token    token
   * @param user     用户信息
   */
  private void addCookie(HttpServletResponse response, String token, User user) {
    /** redis中缓存一份 **/
    redisHelper.set(SeckillUserKey.token, token, user);
    /** 设置到cookie中  **/
    Cookie cookie = new Cookie(Constants.COOKIE_NAME_TOKEN, token);
    cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
    cookie.setPath("/");
    response.addCookie(cookie);
  }

}
