package cn.itcast.core.service;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.specification.Specification;
import entity.PageResult;
import vo.SpecificationVo;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    PageResult search(Integer page, Integer rows, Specification specification);

    void add(SpecificationVo vo);

    SpecificationVo findOne(Long id);

    void update(SpecificationVo vo);

    List<Map> selectOptionList();

    List<Specification> seleExecle();

    void insertExcel(String s);
}
