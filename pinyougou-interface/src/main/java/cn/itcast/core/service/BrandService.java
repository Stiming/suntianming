package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Brand;
import entity.PageResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface BrandService {

    //查询所有品牌
    public List<Brand> findAll();
    //查询分页对象
    PageResult findPage(Integer pageNum, Integer pageSize);

    //保存
    void add(Brand brand);

    Brand findOne(Long id);

    void update(Brand brand);

    void deletes(Long[] ids);

    PageResult search(Integer pageNum, Integer pageSize, Brand brand);

    List<Map> selectOptionList();
    List<Brand> seleExecle();

    List<Map> showList(List<Map<String, String>> tt);

    void insertExcel(String s);
}
