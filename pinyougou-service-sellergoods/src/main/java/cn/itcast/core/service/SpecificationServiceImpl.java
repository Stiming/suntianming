package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.PageResult;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import vo.SpecificationVo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 规格管理
 */
@SuppressWarnings("all")
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationDao specificationDao;
    @Autowired
    private SpecificationOptionDao specificationOptionDao;
    //条件 查询分页对象
    @Override
    public PageResult search(Integer page, Integer rows, Specification specification) {
        PageHelper.startPage(page,rows);

        Page<Specification> p = (Page<Specification>) specificationDao.selectByExample(null);

        return new PageResult(p.getTotal(),p.getResult());
    }

    //添加
    @Override
    public void add(SpecificationVo vo) {
        //规格表 并返回ID
        specificationDao.insertSelective(vo.getSpecification());

 
        //规格选项表
        List<SpecificationOption> specificationOptionList = vo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            //设置外键
            specificationOption.setSpecId(vo.getSpecification().getId());
            specificationOptionDao.insertSelective(specificationOption);
        }
        

    }

    //查询一个Vo对象
    @Override
    public SpecificationVo findOne(Long id) {
        SpecificationVo vo = new SpecificationVo();
        //规格
        vo.setSpecification(specificationDao.selectByPrimaryKey(id));
        //规格选项结果集
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        query.createCriteria().andSpecIdEqualTo(id);
        vo.setSpecificationOptionList(specificationOptionDao.selectByExample(query));

        return vo;
    }

    //修改
    @Override
    public void update(SpecificationVo vo) {
        //规格表
        specificationDao.updateByPrimaryKeySelective(vo.getSpecification());
        //规格选项表 多
        //1:先删除
        SpecificationOptionQuery query = new SpecificationOptionQuery();
        query.createCriteria().andSpecIdEqualTo(vo.getSpecification().getId());
        specificationOptionDao.deleteByExample(query);
        //2:后添加
        List<SpecificationOption> specificationOptionList = vo.getSpecificationOptionList();
        for (SpecificationOption specificationOption : specificationOptionList) {
            //设置外键
            specificationOption.setSpecId(vo.getSpecification().getId());
            specificationOptionDao.insertSelective(specificationOption);
        }

    }

    @Override
    public List<Map> selectOptionList() {
        return specificationDao.selectOptionList();
    }

    @Override
    public List<Specification> seleExecle() {
        List<Specification> specifications = specificationDao.selectByExample(null);
        return specifications;
    }

    @Override
    public void insertExcel(String s) {
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
                String spec_name = cells.getCell(1).getStringCellValue();
                Cell cell1 = cells.getCell(2);
                cell1.setCellType(CellType.STRING);
                String status = cell1.getStringCellValue();

//            String  address=cells.getCell(2).getStringCellValue();
//            String  phone=cells.getCell(3).getStringCellValue();
//            String  emali=cells.getCell(4).getStringCellValue();
                Specification specification = new Specification();

                specification.setId(Long.parseLong(id));
                specification.setSpecName(spec_name);
                specification.setStatus(Long.parseLong(status));

                specificationDao.insertSelective(specification);
            }
            i++;

        }
    }
}
