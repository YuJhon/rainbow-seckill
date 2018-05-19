package com.jhon.rain.service;

import com.jhon.rain.pojo.vo.GoodsVO;

import java.util.List;

/**
 * <p>功能描述</br>商品服务</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:37
 */
public interface GoodsService {

  /**
   * <pre>获取商品展示列表</pre>
   *
   * @return
   */
  List<GoodsVO> listGoodsVO();

  /**
   * <pre>获取商品详细信息</pre>
   *
   * @param goodsId
   * @return
   */
  GoodsVO getGoodsVOByGoodsId(long goodsId);

  /**
   * <pre>扣减库存</pre>
   *
   * @param goods 商品信息
   * @return
   */
  boolean reduceStock(GoodsVO goods);
}
