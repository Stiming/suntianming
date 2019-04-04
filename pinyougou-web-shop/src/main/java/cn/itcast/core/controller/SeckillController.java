package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 秒杀管理
 */
@RestController
@RequestMapping("/seckill")
public class SeckillController {

    @Reference
    private SeckillService seckillService;


    // 分页查询
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody SeckillOrder seckillOrder){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        seckillOrder.setSellerId(name);
        return seckillService.search(page,rows,seckillOrder);
    }

    //删除秒杀订单
    @RequestMapping("/delete")
    public Result delete(Long[] ids){

        try {
            seckillService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }


}
