package cn.itcast.core.service;

import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.Areas;
import cn.itcast.core.pojo.address.Cities;
import cn.itcast.core.pojo.address.Provinces;

import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Address> findListByLoginUser(String name);

    List<Provinces> selectProvincesList();

    List<Cities> selectCityList(String provinceid);

    List<Areas> selectTownList(String cityid);

    void save(Address address);

    void setdefault(String id, String name);

    void deleteAddress(Address address);
}
