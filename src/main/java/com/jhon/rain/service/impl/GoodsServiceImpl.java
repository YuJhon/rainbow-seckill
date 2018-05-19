package com.jhon.rain.service.impl;

import com.jhon.rain.dao.GoodsDAO;
import com.jhon.rain.entity.SecKillGoods;
import com.jhon.rain.pojo.vo.GoodsVO;
import com.jhon.rain.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>功能描述</br>商品服务接口实现类</p>
 *
 * @author jiangy19
 * @version v1.0
 * @projectName rainbow-seckill
 * @date 2018/5/16 19:37
 */
@Service
public class GoodsServiceImpl implements GoodsService {

  @Autowired
  private GoodsDAO goodsDAO;

  @Override
  public List<GoodsVO> listGoodsVO() {
    return goodsDAO.listGoodsVO();
  }

  @Override
  public GoodsVO getGoodsVOByGoodsId(long goodsId) {
    return goodsDAO.getGoodsVoByGoodsId(goodsId);
  }

  @Override
  public boolean reduceStock(GoodsVO goods) {
    SecKillGoods secKillGoods = new SecKillGoods();
    secKillGoods.setGoodsId(goods.getId());
    int records = goodsDAO.reduceStock(secKillGoods);
    return records > 0;
  }
}
