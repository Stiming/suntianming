package cn.itcast.core.service;

import cn.itcast.core.pojo.order.OrderItem;

import java.util.List;
import java.util.Map;

public interface TongjiService {
    List<Map> findAll(String name);

    List<Map> findAll1();
}
