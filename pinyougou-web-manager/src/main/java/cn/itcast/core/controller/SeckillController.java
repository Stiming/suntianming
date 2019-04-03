package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀管理
 */
@SuppressWarnings("all")
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Reference
    private SeckillService seckillService;


    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status){
        try {
            seckillService.updateStatus(ids,status);
            return new Result(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"操作失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows,@RequestBody SeckillGoods seckillGoods){
        return seckillService.searchGoods(page, rows, seckillGoods);
    }

}
