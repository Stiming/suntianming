package cn.itcast.core.service;

import cn.itcast.core.dao.address.AddressDao;
import cn.itcast.core.dao.address.AreasDao;
import cn.itcast.core.dao.address.CitiesDao;
import cn.itcast.core.dao.address.ProvincesDao;
import cn.itcast.core.pojo.address.*;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        List<Address> addresses = addressDao.selectByExample(addressQuery);
        String a ="";
        for (Address address : addresses) {
            if(null !=address.getProvinceId()){
                ProvincesQuery provincesQuery = new ProvincesQuery();
                provincesQuery.createCriteria().andProvinceidEqualTo(address.getProvinceId());
                List<Provinces> provincesList = provincesDao.selectByExample(provincesQuery);
                a += provincesList.get(0).getProvince()+" ";
                if(null !=address.getCityId()){
                    CitiesQuery citiesQuery = new CitiesQuery();
                    citiesQuery.createCriteria().andCityidEqualTo(address.getCityId());
                    List<Cities> citiesList = citiesDao.selectByExample(citiesQuery);
                    a += citiesList.get(0).getCity()+" ";
                    if(null !=address.getTownId()){
                        AreasQuery areasQuery = new AreasQuery();
                        areasQuery.createCriteria().andAreaidEqualTo(address.getTownId());
                        List<Areas> areasList = areasDao.selectByExample(areasQuery);
                        a += areasList.get(0).getArea()+" "+address.getAddress();
                        address.setAddress(a);
                    }
                }
            }
        }

        return addresses;
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

    @Override
    public void save(Address address) {
        address.setCreateDate(new Date());
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(address.getUserId()).andIsDefaultEqualTo("1");
        List<Address> addresses = addressDao.selectByExample(addressQuery);
        if (null == addresses || addresses.size()<1){
            address.setIsDefault("1");
        }else {
            address.setIsDefault("0");
        }
        if (null == address.getId()){
            addressDao.insertSelective(address);
        }else {
            addressDao.updateByPrimaryKeySelective(address);
        }
    }
    //修改默认地址
    @Override
    public void setdefault(String id, String name) {
        AddressQuery addressQuery = new AddressQuery();
        addressQuery.createCriteria().andUserIdEqualTo(name).andIsDefaultEqualTo("1");
        List<Address> addresses = addressDao.selectByExample(addressQuery);
        //先把原先默认地址重置
        if(null != addresses && addresses.size() >0){
            for (Address address : addresses) {
                address.setIsDefault("0");
                addressDao.updateByPrimaryKeySelective(address);
            }
        }
        Address address = new Address();
        address.setIsDefault("1");
        address.setId(Long.parseLong(id));
        addressDao.updateByPrimaryKeySelective(address);
    }

    @Override
    public void deleteAddress(Address address) {
        addressDao.deleteByPrimaryKey(address.getId());
    }


}
