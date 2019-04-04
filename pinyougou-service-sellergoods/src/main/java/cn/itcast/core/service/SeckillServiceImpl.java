package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillOrderDao seckillOrderDao;

    @Autowired
    private SeckillGoodsDao seckillGoodsDao;

    //查询分页对象
    @Override
    public PageResult search(Integer page, Integer rows, SeckillOrder seckillOrder) {

        PageHelper.startPage(page, rows);
        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        SeckillOrderQuery.Criteria criteria = seckillOrderQuery.createCriteria();


        //审核状态
        if (null != seckillOrder.getStatus() && !"".equals(seckillOrder.getStatus())) {
            criteria.andStatusEqualTo(seckillOrder.getStatus());
        }

        //只能查询当前登陆人 他家的商品
        //如果是商家后台调用  goods里面就会有当前登陆人  如果是运营商 就没有当前登陆人
        if (null != seckillOrder.getSellerId()) {
            criteria.andSellerIdEqualTo(seckillOrder.getSellerId());
        }

        //查询
        Page<SeckillOrder> p = (Page<SeckillOrder>) seckillOrderDao.selectByExample(seckillOrderQuery);

        return new PageResult(p.getTotal(), p.getResult());
    }

    //删除秒杀订单
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            seckillOrderDao.deleteByPrimaryKey(id);
        }
    }

    /**
     * 秒杀审核
     * @param ids
     * @param status
     */
    @Override
    public void updateStatus(Long[] ids, String status) {
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setStatus(status);
        for (Long id : ids) {
            seckillGoods.setId(id);
            //保存
            seckillGoodsDao.updateByPrimaryKeySelective(seckillGoods);
        }
    }

    @Override
    public PageResult searchGoods(Integer page, Integer rows, SeckillGoods seckillGoods) {
        PageHelper.startPage(page, rows);
        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();


        //审核状态
        if (null != seckillGoods.getTitle() && !"".equals(seckillGoods.getTitle())) {
            criteria.andTitleLike(seckillGoods.getTitle());
        }

        //只能查询当前登陆人 他家的商品
        //如果是商家后台调用  goods里面就会有当前登陆人  如果是运营商 就没有当前登陆人
        if (null != seckillGoods.getSellerId()) {
            criteria.andSellerIdEqualTo(seckillGoods.getSellerId());
        }

        //查询
        Page<SeckillGoods> p = (Page<SeckillGoods>) seckillGoodsDao.selectByExample(seckillGoodsQuery);

        return new PageResult(p.getTotal(), p.getResult());
    }

}
