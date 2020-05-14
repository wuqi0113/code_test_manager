package com.chainfuture.code.controller;


import com.chainfuture.code.bean.CustFileds;
import com.chainfuture.code.bean.CustStrategy;
import com.chainfuture.code.bean.CustStrategyInfo;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.mapper.CustStrategyInfoMapper;
import com.chainfuture.code.mapper.CustStrategyMapper;
import com.chainfuture.code.mapper.FiledsMapper;
import com.chainfuture.code.utils.ExcelUtil;
import com.chainfuture.code.utils.ResultUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/fileds")
public class FiledsController {

    @Autowired
    private FiledsMapper filedsMapper;
    @Autowired
    private CustStrategyInfoMapper custStrategyInfoMapper;
    @Autowired
    private CustStrategyMapper custStrategyMapper;
    private static final CountDownLatch ctl = new CountDownLatch(10);


/*    @RequestMapping(value = "uploadFieldExcel")
    @ResponseBody
    public ResultUtil uploadFieldExcel(@PathVariable("file")MultipartFile file) {

        InputStream inputStream = null;
        Workbook wb = null;
        String originalFilename="";
        try {
            inputStream = file.getInputStream();
            originalFilename = file.getOriginalFilename();
           *//* final int length = originalFilename.getBytes().length;
            filedsMapper.truncateTable();
            wb = new XSSFWorkbook(inputStream);
            int numberOfSheets = wb.getNumberOfSheets();
            readExcel(wb, 0, 1);*//*

        } catch (Exception e) {
            return new ResultUtil(500,"上传数据失败");
        }
        return ResultUtil.ok(originalFilename);
    }*/


    @RequestMapping(value = "uploadFieldExcel")
    @ResponseBody
    public ResultUtil uploadFieldExcel(@PathVariable("file")MultipartFile file) {

        InputStream inputStream = null;
        Workbook wb = null;
        try {
            inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            final int length = originalFilename.getBytes().length;
            filedsMapper.truncateTable();
            wb = new XSSFWorkbook(inputStream);
            int numberOfSheets = wb.getNumberOfSheets();
         /*  synchronized (this){
                for (int i=0;i<numberOfSheets;i++){
                    readExcel(wb, i, 1);
                }
            }*/
            readExcel(wb, 0, 1);

//            custFiledServiceImpl.insertCustFiledBatch(custFileds);
        } catch (Exception e) {
//            e.printStackTrace();
            return new ResultUtil(500,"上传数据失败");
        }
        return ResultUtil.ok();
    }

    public   void readExcel(Workbook wb, int sheetIndex, int startReadLine ) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
//      获取sheet的最后一行
        int lastRow = sheet.getLastRowNum();

        Row row = null;
        CustFileds cf=null;
        List<CustFileds> cfList = new ArrayList<>();
        int flag=0;
        for (int i = startReadLine; i <= sheet.getLastRowNum() ; i++) {
            cf = new CustFileds();
            row = sheet.getRow(i);
            int lastCellNum = row.getLastCellNum();
            if (lastCellNum > 7) {
                //如果不是7列就不读这一行了。
                continue;
            }
            for (int j = 0; j < 7; j++) {
                Cell cell = row.getCell(j);//获取一个cell
                if (j == 0) {
                    cf.setSname(cell.getStringCellValue());//通用标识
                    continue;
                } else if (j == 1) {
                    cf.setName(cell.getStringCellValue());//名称
                    continue;
                } else if (j == 2) {
                    String s="";
                    if (cell==null){
                        s="text";
                    }else {
                        s = String.valueOf(cell.getStringCellValue());
                    }
                    cf.setStype(s);//类型
                    continue;
                }  else if (j == 3) {
                    String s="";
                    if (cell==null){
                        s="无";
                    }else {
                        s = String.valueOf(cell.getStringCellValue());
                    }
                    cf.setUnit(s);//单位
                    continue;
                }  else if (j == 4) {
                    String s="";
                    if (cell==null){
                        s="TRUE";
                    }else {
                        s = String.valueOf(cell.getBooleanCellValue());
                    }
                    cf.setIssingle(s);//字段描述
                    continue;
                }  else if (j == 5) {
                    int s=0;
                    if (cell==null){
                        s=0;
                    }else {
                        s = (int)cell.getNumericCellValue();
                    }
                    cf.setLengthes(s);//字段描述
//                    boolean b = objCheckIsNull(cf);
//                    if (!b){
//                        custFiledsMapper.insert(cf);
//                    }
                    continue;
                }else if(j==6){
                    if (cell!=null){
                        int ss= (int) cell.getNumericCellValue();
                        cf.setParentid(ss);
                        int insert = filedsMapper.insert(cf);
                        int id = cf.getId();
                        if (insert > 0) {
                            flag = id;
                        } else {

                        }

                    }else{
                        cf.setParentid(flag);
                        filedsMapper.insert(cf);
                    }
                    cf = new CustFileds();
                }
                boolean b = objCheckIsNull(cf);
                if (!b){
                    filedsMapper.insert(cf);
                }
            }
        }
    }

    public  boolean objCheckIsNull(Object object){
        Class clazz = (Class)object.getClass(); // 得到类对象
        Field fields[] = clazz.getDeclaredFields(); // 得到所有属性
        boolean flag = true; //定义返回结果，默认为true
        for(Field field : fields){
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object); //得到属性值
//                Type fieldType =field.getGenericType();//得到属性类型
//                String fieldName = field.getName(); // 得到属性名
//                System.out.println("属性类型："+fieldType+",属性名："+fieldName+",属性值："+fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(fieldValue != null){  //只要有一个属性值不为null 就返回false 表示对象不为null
                flag = false;
                break;
            }
        }
        return flag;
    }





    @RequestMapping(value = "uploadStrateExcel")
    @ResponseBody
    public ResultUtil uploadStrateExcel(@PathVariable("file1")MultipartFile file) {

        InputStream inputStream = null;
        Workbook wb = null;
        try {
            inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            wb = new XSSFWorkbook(inputStream);
            String s = firstExcel(wb, 0);
            CustStrategyInfo custStrategyInfo = new CustStrategyInfo();
            custStrategyInfo.setStratename(originalFilename);
            custStrategyInfo.setStratemem(s);
            int sss= custStrategyInfoMapper.insert(custStrategyInfo);
            int id= custStrategyInfo.getId();

            int numberOfSheets = wb.getNumberOfSheets();
//            ThreadA t1 = new ThreadA("t1");
            synchronized (this){
                for (int i=0;i<numberOfSheets;i++){
                    readExcel2(wb, i, 1,id);
                }
            }

//            custFiledServiceImpl.insertCustFiledBatch(custFileds);
        } catch (Exception e) {
//            e.printStackTrace();
            return new ResultUtil(500,"上传数据失败");
        }
        return ResultUtil.ok();
    }

    public   String firstExcel(Workbook wb, int sheetIndex ) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);
        String stringCellValue = cell.getStringCellValue();
        return stringCellValue;
    }

    public   void readExcel2(Workbook wb, int sheetIndex, int startReadLine,int id ) {
            Sheet sheet = wb.getSheetAt(sheetIndex);
//      获取sheet的最后一行
            int lastRow = sheet.getLastRowNum();

            Row row = null;
            CustStrategy custStrategy =null;
            for (int i = startReadLine; i <= sheet.getLastRowNum() ; i++) {
                if (i==1){
                    custStrategy =new CustStrategy();
                    custStrategy.setStrateid(id);
                }
                row = sheet.getRow(i);
                for (Cell c : row) {
                    boolean isMerge = ExcelUtil.isMergedRegion(sheet, i, c.getColumnIndex());
                    //判断是否具有合并单元格
                    if (isMerge) {
                        String rs = ExcelUtil.getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex()).trim();
                        custStrategy.setRoles(rs);
                    } else {
                        String s = c.getRichStringCellValue().toString();
                        custStrategy.setIdents(s);
                        custStrategyMapper.insert(custStrategy);
                        custStrategy =new CustStrategy();
                        custStrategy.setStrateid(id);
                    }
                }

            }
    }

}



