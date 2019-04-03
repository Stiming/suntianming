package cn.itcast.core.service;

import cn.itcast.core.pojo.seller.Seller;

import java.util.List;
import java.util.Map;

public interface SellerService {
    void add(Seller seller);

    Seller findSellerByUsername(String username);

    List<Map> showshop(String name);
}
