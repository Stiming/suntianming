package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TongjiServiceImpl implements TongjiService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public List<Map> findAll(String name) {
        OrderQuery orderQuery = new OrderQuery();
        orderQuery.createCriteria().andStatusEqualTo("2");
        List<Order> orders = orderDao.selectByExample(orderQuery);
        ArrayList<Long> longs = new ArrayList<>();
        for (Order order : orders) {
            longs.add(order.getOrderId());
        }
        OrderItemQuery itemquery = new OrderItemQuery();
        itemquery.createCriteria().andSellerIdEqualTo(name);
        List<OrderItem> orderItems = orderItemDao.selectByExample(itemquery);
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<Map> lists = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            Integer number=0;
            int i = longs.indexOf(orderItem.getOrderId());
            /*System.out.println(i);*/
            if (i!=-1) {
                for (OrderItem item : orderItems) {
                    int i1 = longs.indexOf(item.getOrderId());
                    if (orderItem.getTitle().equals(item.getTitle()) && i1!=-1) {
                        number+=item.getNum();
                        map.put(orderItem.getTitle(), number);

                    }
                }
            }
        }
        lists.add(map);
        System.out.println(lists);
       /* for (Map list : lists) {
            for (String s : map.keySet()) {
               *//* System.out.println(s);
                System.out.println(list.get(s));*//*
            }
        }*/
        return lists;
    }

    @Override
    public List<Map> findAll1() {
        List<Map> selectordertj = orderDao.selectordertj();
        return selectordertj;
    }
}
