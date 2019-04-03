package cn.itcast.core.controller;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.Areas;
import cn.itcast.core.pojo.address.Cities;
import cn.itcast.core.pojo.address.Provinces;
import cn.itcast.core.service.AddressService;
import com.alibaba.dubbo.config.annotation.Reference;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<Provinces> selectProvincesList() {
        return addressService.selectProvincesList();
    }

    @RequestMapping("/selectCityList")
    public List<Cities> selectCityList(String provinceid) {
        return addressService.selectCityList(provinceid);
    }

    @RequestMapping("/selectTownList")
    public List<Areas> selectTownList(String cityid) {
        return addressService.selectTownList(cityid);
    }
    //新建收货地址
    @RequestMapping("save")
    public Result save(@RequestBody Address address) {
        try {
            address.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
            addressService.save(address);
            return new Result(true, "地址保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "地址保存失败");
        }

    }
    //修改默认收货地址
    @RequestMapping("setDefaultAddress")
    public Result setDefaultAddress(String id){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            addressService.setdefault(id,name);
            return new Result(true, "默认地址修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "默认地址修改失败");
        }

    }
    //查询地址
    @RequestMapping("/findListByLoginUser")
    public List<Address> findListByLoginUser(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressService.findListByLoginUser(name);
    }
    @RequestMapping("/deleteAddress")
    public Result deleteAddress(@RequestBody Address address) {
        try {
            //address.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());
            addressService.deleteAddress(address);
            return new Result(true, "地址删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "地址删除失败");
        }

    }
    public void testgit(){

    }
}
