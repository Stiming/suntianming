package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import entity.PageResult;

public interface SeckillService {

    PageResult search(Integer page, Integer rows, SeckillOrder seckillOrder);

    void delete(Long[] ids);

    void updateStatus(Long[] ids, String status);

    PageResult searchGoods(Integer page, Integer rows, SeckillGoods seckillGoods);
}
