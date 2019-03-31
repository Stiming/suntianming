package cn.itcast.core.controller;

import cn.itcast.core.pojo.address.Areas;
import cn.itcast.core.pojo.address.Cities;
import cn.itcast.core.pojo.address.Provinces;
import cn.itcast.core.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Reference
    private AddressService addressService;
    @RequestMapping("/selectProvincesList")
    public List<Provinces> selectProvincesList(){
        return addressService.selectProvincesList();
    }
    @RequestMapping("/selectCityList")
    public List<Cities> selectCityList(String provinceid){
        return addressService.selectCityList(provinceid);
    }
    @RequestMapping("/selectTownList")
    public List<Areas> selectTownList(String cityid){
        return addressService.selectTownList(cityid);
    }
}
