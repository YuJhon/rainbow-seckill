package com.jhon.rain.dao;

import com.jhon.rain.entity.Order;
import com.jhon.rain.entity.SecKillOrder;
import org.apache.ibatis.annotations.*;

/**
 * <p>功能描述</br>订单数据访问层</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:51
 */
@Mapper
public interface OrderDAO {

  /**
   * <pre>查询订单信息（返回插入的记录ID）</pre>
   *
   * @param order 订单信息
   * @return
   */
  @Insert("insert into t_order(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
          + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
  @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
  long insert(Order order);

  /**
   * <pre>插入秒杀的订单信息</pre>
   *
   * @param secKillOrder
   * @return
   */
  @Insert("insert into t_seckill_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
  int insertSecKillOrder(SecKillOrder secKillOrder);

  /**
   * <pre>通过ID查询订单信息</pre>
   *
   * @param orderId 订单ID
   * @return 返回订单详细信息
   */
  @Select("select * from t_order where id = #{orderId}")
  Order getOrderById(@Param("orderId") Long orderId);

  /**
   * <pre>删除订单信息</pre>
   */
  @Delete("delete from t_order")
  void deleteOrders();

  /**
   * <pre>删除秒杀订单信息</pre>
   */
  @Delete("delete from t_seckill_order")
  void deleteSecKillOrders();
}
