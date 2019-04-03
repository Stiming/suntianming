package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.pojo.seller.Seller;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家管理
 */
@Service
@Transactional
public class SellerServiceImpl implements  SellerService {

    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    OrderDao orderDao;
    @Override
    public void add(Seller seller) {

        //用户名
        //密码
        seller.setPassword(
                new BCryptPasswordEncoder().encode(seller.getPassword()));
        //公司名
        //店铺
        //状态 未审核
        seller.setStatus("0");


        sellerDao.insertSelective(seller);
    }

    //查询商家对象  通过用户名
    @Override
    public Seller findSellerByUsername(String username) {

        return sellerDao.selectByPrimaryKey(username);
    }

    @Override
    public List<Map> showshop(String name) {
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
            System.out.println(i);
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
        for (Map list : lists) {
            for (String s : map.keySet()) {
                System.out.println(s);
                System.out.println(list.get(s));
            }
        }
        return lists;
    }

    //


}
