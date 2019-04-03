package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.PageResult;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 品牌管理
 */
@Service
public class BrandServiceImpl implements BrandService{



    @Autowired
    private BrandDao brandDao;
    @Autowired
    OrderDao orderDao;

    @Override
    public List<Brand> findAll() {
        return brandDao.selectByExample(null);
    }

    //查询分页对象
    @Override
    public PageResult findPage(Integer pageNum, Integer pageSize) {

        //分页插件
        PageHelper.startPage(pageNum, pageSize);

        //查询结果集
        Page<Brand> page = (Page<Brand>) brandDao.selectByExample(null);
        //总条数
        //结果集 select * from tb_brand  limit 开始行,每页数
        return new PageResult(page.getTotal(), page.getResult());
    }

    //保存
    @Override
    public void add(Brand brand) {
        brandDao.insertSelective(brand);
        //insert into tb_tt (id,name,98个) values (3,haha,null 98个)  执行的效果是一样的 但是执行的效率是一样的
        //insert into tb_tt (id,name) values (3,haha)
        //update tb_tt set id = #{id},name= ......   where id
    }

    @Override
    public Brand findOne(Long id) {
        return brandDao.selectByPrimaryKey(id);
    }

    //修改
    @Override
    public void update(Brand brand) {
        brandDao.updateByPrimaryKeySelective(brand);
    }

    //删除
    @Override
    public void deletes(Long[] ids) {
        //判断
        if(null != ids && ids.length > 0){
            for (Long id : ids) {
                brandDao.deleteByPrimaryKey(id);
            }
        }
        //批量删除  delete from tt_tt where id in (1,2,4,5) 动态Sql
        //BrandQuery brandQuery = new BrandQuery();
       // brandQuery.createCriteria().andIdIn(Arrays.asList(ids));  //数组换集合
        //brandDao.deleteByExample(brandQuery);


    }

    //条件的分页对象查询

    @Override
    public PageResult search(Integer pageNum, Integer pageSize, Brand brand) {

        //分页插件
        PageHelper.startPage(pageNum, pageSize);
        //条件对象
        BrandQuery brandQuery = new BrandQuery();
        //创建内部条件对象
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        //判断名称是否有值
        if(null != brand.getName() && !"".equals(brand.getName().trim())){
            criteria.andNameLike("%"+brand.getName().trim()+"%");
        }
        //判断首字母
        if(null != brand.getFirstChar() && !"".equals(brand.getFirstChar().trim())){
            criteria.andFirstCharEqualTo(brand.getFirstChar().trim());
        }

        //查询结果集
        Page<Brand> page = (Page<Brand>) brandDao.selectByExample(brandQuery);
        //总条数
        //结果集 select * from tb_brand  limit 开始行,每页数
        return new PageResult(page.getTotal(), page.getResult());
    }
//      @Value("${execurl}")
//      private String execurl;
    //查询所有品牌 并返回
    @Override
    public List<Map> selectOptionList() {
        return brandDao.selectOptionList();
    }

    @Override

    public List<Brand> seleExecle() {
        List<Brand> brands = brandDao.selectByExample(null);
          return brands;


        }

    @Override
    public List<Map> showList(List<Map<String, String>> tt) {
        HashMap<String, Integer> map1 = new HashMap<>();

        OrderQuery orderQuery = new OrderQuery();
        orderQuery.createCriteria().andStatusEqualTo("2");
        List<Order> orders = orderDao.selectByExample(orderQuery);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Map<String, String> map : tt) {
            int number=1;
            for (Order order : orders) {
                Date createTime = order.getCreateTime();
                String format = simpleDateFormat.format(createTime);
                String substring = format.substring(0, 4);
               if (substring.equals(map.get("year"))){
                   map1.put(map.get("year"),number);
                   number++;
               }
            }
        }
//        for (String s : map1.keySet()) {
//            System.out.println(s);
//            System.out.println(map1.get(s));
//        }
        ArrayList<Map> list = new ArrayList<>();
        list.add(map1);
        return list;
    }

    @Override
    public void insertExcel(String s) {
        System.out.println(123);
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(new FileInputStream(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet sheetAt = wb.getSheetAt(0);


        int i=0;
        for (Row cells : sheetAt) {
            if (i > 1) {
                Cell cell = cells.getCell(0);
                cell.setCellType(CellType.STRING);
                String id = cell.getStringCellValue();
                String name = cells.getCell(1).getStringCellValue();
                String firstChar = cells.getCell(2).getStringCellValue();
                Cell cell1 = cells.getCell(3);
                cell1.setCellType(CellType.STRING);
                String status = cell1.getStringCellValue();
//            String  address=cells.getCell(2).getStringCellValue();
//            String  phone=cells.getCell(3).getStringCellValue();
//            String  emali=cells.getCell(4).getStringCellValue();
                Brand brand = new Brand();
                brand.setId(Long.parseLong(id));
                brand.setName(name);
                brand.setFirstChar(firstChar);
                brand.setStatus(Long.parseLong(status));
                System.out.println(brand);
                 brandDao.insertSelective(brand);
            }
            i++;

        }

    }


}
