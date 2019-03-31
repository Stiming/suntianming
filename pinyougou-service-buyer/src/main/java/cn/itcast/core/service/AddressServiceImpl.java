package cn.itcast.core.service;

import cn.itcast.core.dao.address.AddressDao;
import cn.itcast.core.dao.address.AreasDao;
import cn.itcast.core.dao.address.CitiesDao;
import cn.itcast.core.dao.address.ProvincesDao;
import cn.itcast.core.pojo.address.*;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 收货地址管理
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;
    @Autowired
    private ProvincesDao provincesDao;
    @Autowired
    private CitiesDao citiesDao;
    @Autowired
    private AreasDao areasDao;

    @Override
    public List<Address> findListByLoginUser(String name) {
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(name);
        return addressDao.selectByExample(addressQuery);
    }

    @Override
    public List<Provinces> selectProvincesList() {
        return provincesDao.selectByExample(null);
    }

    @Override
    public List<Cities> selectCityList(String provinceid) {
        CitiesQuery citiesQuery = new CitiesQuery();
        citiesQuery.createCriteria().andProvinceidEqualTo(provinceid);
        return citiesDao.selectByExample(citiesQuery);
    }

    @Override
    public List<Areas> selectTownList(String cityid) {
        AreasQuery areasQuery = new AreasQuery();
        areasQuery.createCriteria().andCityidEqualTo(cityid);
        return areasDao.selectByExample(areasQuery);
    }


}
