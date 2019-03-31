package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.Provinces;

import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Address> findListByLoginUser(String name);

    List<Provinces> selectProvincesList();
}
