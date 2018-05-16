package com.jhon.rain.dao;

import com.jhon.rain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>功能描述</br>用户数据访问层</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/15 16:49
 */
@Mapper
public interface UserDAO {

  /**
   * <pew>通过电话号码查询用户信息</pew>
   *
   * @param phone
   * @return
   */
  @Select("select * from t_user where mobile = #{mobile}")
  User getUserByPhone(@Param("mobile") String phone);

  /**
   * <pre>根据电话更新用户密码</pre>
   *
   * @param toBeUpdate
   */
  @Update("update t_user set password= #{password} where mobile= #{mobile}")
  void update(User toBeUpdate);
}
