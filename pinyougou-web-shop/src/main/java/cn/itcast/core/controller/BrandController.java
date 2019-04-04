package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 品牌管理之品牌审核
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    //分页查询
    @RequestMapping("/search")
    public PageResult search (Integer pageNum, Integer pageSize, @RequestBody Brand brand) {
        return brandService.search(pageNum, pageSize, brand);
    }

    //添加品牌（申请）
    @RequestMapping("/add")
    public Result add (@RequestBody Brand brand) {
        //保存
        try {
            Brand newbrand = new Brand();

            if (null != newbrand && !newbrand.equals(brand)) {
                brandService.add(brand);
                return new Result(true, "添加成功");
            }else {
                return new Result(false, "添加失败");
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }




}
