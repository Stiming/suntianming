package cn.itcast.core.controller;

import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.service.SpecificationService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

/**
 * 规格管理（申请）
 *
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    //条件对象的查询分页结果集
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows, @RequestBody Specification specification){
        return specificationService.search(page,rows,specification);
    }
    //保存
    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationVo vo){
        try {

                specificationService.add(vo);
                return new Result(true,"成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"失败");
        }
    }

}
