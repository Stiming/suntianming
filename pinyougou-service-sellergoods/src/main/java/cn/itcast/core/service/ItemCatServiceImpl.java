package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 分类管理
 */
@SuppressWarnings("all")
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao itemCatDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<ItemCat> findByParentId(Long parentId) {

        //1:从Mysql数据库将所有数据查询出来 保存到缓存中一份
        List<ItemCat> itemCats = itemCatDao.selectByExample(null);
        //2:保存到缓存中  五大数据类型hash

        for (ItemCat itemCat : itemCats) {
            redisTemplate.boundHashOps("itemCat").put(itemCat.getName(),itemCat.getTypeId());
        }

        //正常商品分类列表查询
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        itemCatQuery.createCriteria().andParentIdEqualTo(parentId);
        return itemCatDao.selectByExample(itemCatQuery);
    }


    //查询一个
    @Override
    public ItemCat findOne(Long id) {
        return itemCatDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return itemCatDao.selectByExample(null);
    }

    @Override
    public List<ItemCat> seleExecle() {
        return itemCatDao.selectByExample(null);
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
                Cell cell1 = cells.getCell(1);
                cell1.setCellType(CellType.STRING);
                String parentid = cell1.getStringCellValue();
                String name = cells.getCell(2).getStringCellValue();
                Cell cell2 = cells.getCell(3);
                cell2.setCellType(CellType.STRING);
                String typeid = cell2.getStringCellValue();

                Cell cell3 = cells.getCell(4);
                cell3.setCellType(CellType.STRING);
                String status = cell3.getStringCellValue();

//            String  address=cells.getCell(2).getStringCellValue();
//            String  phone=cells.getCell(3).getStringCellValue();
//            String  emali=cells.getCell(4).getStringCellValue();
                ItemCat itemCat = new ItemCat();
                itemCat.setId(Long.parseLong(id));
                itemCat.setParentId(Long.parseLong(parentid));
                itemCat.setName(name);
                itemCat.setTypeId(Long.parseLong(typeid));
                itemCat.setStatus(Long.parseLong(status));
                itemCatDao.insertSelective(itemCat);
            }
            i++;

        }
    }
}
