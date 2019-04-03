package cn.itcast.core.controller;

import cn.itcast.core.service.TongjiService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tongji")
public class TongjiController {
    @Reference
    private TongjiService tongjiService;
    @RequestMapping("/showshop")
    public List<Map> findAll(){

        List<Map> all1 = tongjiService.findAll1();
        return all1;
    }
}
