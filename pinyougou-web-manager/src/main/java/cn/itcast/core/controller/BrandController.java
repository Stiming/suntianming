package cn.itcast.core.controller;

import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import entity.PageResult;
import entity.Result;
import org.apache.http.protocol.HTTP;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.Years;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 品牌管理
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;
    //查询所有品牌结果集
    @RequestMapping("/findAll")
    public List<Brand> findAll(){
        return brandService.findAll();
    }

    //入参：
    //URL:
    //返回值
    @RequestMapping("/findPage")
    public PageResult findPage(Integer pageNum, Integer pageSize){
        return brandService.findPage(pageNum,pageSize);
    }
    //查询分页对象 入参： 当前页 每页数 条件对象 ?id=2
    @RequestMapping("/search")
    //public PageResult search(Integer pageNum, Integer pageSize,@RequestBody(required = false) Brand brand){
    public PageResult search(Integer pageNum, Integer pageSize,@RequestBody Brand brand){


        return brandService.search(pageNum,pageSize,brand);
    }
    //保存
    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand){
        //保存
        try {
            brandService.add(brand);
            return new Result(true,"保存成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return new Result(false,"保存失败");
        }

    }
    //保存
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand){
        //保存
        try {
            brandService.update(brand);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return new Result(false,"修改失败");
        }

    }
    //删除
    @RequestMapping("/deletes")
    public Result deletes(Long[] ids){
        //保存
        try {
            brandService.deletes(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            //e.printStackTrace();
            return new Result(false,"删除失败");
        }

    }
    //查询一个品牌
    @RequestMapping("/findOne")
    public Brand findOne(Long id){
        return brandService.findOne(id);
    }

    //查询所有品牌 并返回List<Map>
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }
    @RequestMapping("/Execle")
    public Result  Execle(HttpServletRequest request){
//        String outputFile="D:\\brand.xls";
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("ss");
        HSSFCellStyle Style1 = wb.createCellStyle();
        Style1.setAlignment(HorizontalAlignment.CENTER);
        //在sheet里创建第一行，参数为行索引
        List<Brand> brands = brandService.seleExecle();
        HSSFRow row2 = sheet.createRow(0);
        Style1.setAlignment(HorizontalAlignment.CENTER);
        row2.createCell(0).setCellValue("品牌数据一览表");
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));

        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("id");
        row1.createCell(1).setCellValue("name");
        row1.createCell(2).setCellValue("first_char");
        row1.createCell(3).setCellValue("status");

        int i=2;
        for (Brand brand : brands) {
            HSSFRow row = sheet.createRow(i);
            row.createCell(0).setCellValue(brand.getId());
            row.createCell(1).setCellValue(brand.getName());
            row.createCell(2).setCellValue(brand.getFirstChar());
            row.createCell(3).setCellValue(brand.getStatus());

            i++;
        }
//        HSSFRow row1=sheet.createRow(0);
//        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个 ）
//        HSSFCell cell=row1.createCell(0);
//        //设置单元格内容
//        cell.setCellValue("一览表");
//        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
//        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));

        try {
//            OutputStream output=response.getOutputStream();
//            response.reset();
//            response.setHeader("Content-disposition", "attachment; filename=details.xls");
//            response.setContentType("application/msexcel");
            String realPath = request.getSession().getServletContext().getRealPath("/");
            System.out.println(realPath);
            UUID uuid = UUID.randomUUID();
            FileOutputStream FOut = new FileOutputStream(realPath+uuid+"brand"+".xls");
            wb.write(FOut);
            FOut.close();
          return   new Result(true,"添加成功了");
        } catch (IOException e) {
            e.printStackTrace();
         return    new Result(false,"添加失败了");
        }

//        FileOutputStream FOut = null;
//        return wb;

    }
    @RequestMapping("/show")
   public List<Map> show( @RequestBody List<Map<String,String>> tt){
       List<Map> showList1= brandService.showList(tt);
        return  showList1;
    }
   @RequestMapping(value = "/pic1",produces="text/html;charset=UTF-8")
    public String pic1(MultipartFile uploadFile,HttpServletRequest request){

       String realPath = request.getSession().getServletContext().getRealPath("/");
       String s = realPath + uploadFile.getOriginalFilename();
       try {
           brandService.insertExcel(s);
           return "导入成功了亲亲";
       } catch (Exception e) {
           e.printStackTrace();
           return "导入失败了亲亲";
       }

   }
}
