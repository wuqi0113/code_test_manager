package com.chainfuture.code.utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelUtil {

    @Autowired
//    private CreateBaseMapper createBaseMapper;
    private static Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);


   /* public static void main(String args[]) {
        FileInputStream fileInputStream = null;
        FileType fileType = null;
        File file =  new File("C:\\Users\\admin\\Desktop\\bbb.xlsx");
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new FileInputStream(file));
//            wb = WorkbookFactory.create(file);
            readExcel(wb, 0, 0, 0,0);
           *//* for(int i=0;i<wb.getNumberOfSheets();i++){
                readExcel(wb, i, 0, 0);
            }*//*
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



    /**
     * 读取excel文件
     * @param wb
     * @param sheetIndex sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine 去除最后读取的行
     */
    public static void readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine,int flag) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;

        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
            row = sheet.getRow(i);
            if (row==null){break;}
            for (Cell c : row) {
                boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
                //判断是否具有合并单元格
                if (isMerge) {
                    String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
                    System.out.print(rs + " ");
                } else {
                    System.out.print(c.getRichStringCellValue() +" ");
                }
            }
            System.out.println();
        }
    }

    /**
     * 读取excel  并建表
     * @param wb
     * @param sheetIndex
     * @param startReadLine
     * @return
     */
    public  void readExcel(Workbook wb, int sheetIndex, int startReadLine ,int tailLine) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        String newTableName = "";
        String tableComment = "";
        Map<String, String> column = null;
        List<Map<String, String>> colums = null;
        for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
            row = sheet.getRow(i);
            colums = new ArrayList<>();
            if (row==null){break;}
            for (Cell c : row) {
                boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
                column = new HashMap();
                //判断是否具有合并单元格
                if (isMerge) {
                    String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
                    if (isEnglish(rs)){ newTableName+="cust_"+rs;}
                    if(isChinese(rs))  { tableComment+=tableComment+rs;}
                } else {
                    String s = c.getRichStringCellValue().toString();
                    if (isEnglish(s)) {
                        column.put("type", "STRING");
                        column.put("code", s);
                        column.put("length", String.valueOf(255));
                    }
                    if (isChinese(s)) {
                        column.put("comment", s);
                    }
                    System.out.print(c.getRichStringCellValue() +" ");
                }
                colums.add(column);
            }
            System.out.println();
//            createBaseMapper.createTable(newTableName,colums,tableComment);
        }
    }

    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet, int row, int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){

                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return null ;
    }

    /**
     * 判断合并了行
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public boolean isMergedRow(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row == firstRow && row == lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断sheet页中是否含有合并单元格
     * @param sheet
     * @return
     */
    public boolean hasMerged(Sheet sheet) {
        return sheet.getNumMergedRegions() > 0 ? true : false;
    }

    /**
     * 合并单元格
     * @param sheet
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     */
    public void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell){

        if(cell == null) return "";

        if(cell.getCellType() == CellType.STRING){

            return cell.getStringCellValue();

        }else if(cell.getCellType() == CellType.BOOLEAN){

            return String.valueOf(cell.getBooleanCellValue());

        }else if(cell.getCellType() == CellType.FORMULA){

            return cell.getCellFormula() ;

        }else if(cell.getCellType() == CellType.NUMERIC){

            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }

    public static boolean isEnglish(String charaString){

        return charaString.matches("^[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*_{0,1}[a-zA-Z]*");

    }

    public static boolean isChinese(String str){

        String regEx = "[\\u4e00-\\u9fa5]+";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        if(m.find())
            return true;
        else
            return false;
    }

    public static List<String> returnValue(String msg) {

        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile("(\\()([a-zA-Z])*(\\))");
//        Pattern p = Pattern.compile("(\\()([0-9a-zA-Z\\.\\/\\=])*(\\))");
        Matcher m = p.matcher(msg);
        while (m.find()) {
            list.add(m.group(0).substring(1, m.group(0).length() - 1));
        }
        return list;
    }

   public static void main(String[] args) throws Exception {
        String msg1 = "mSurface=Surface(nameau2ncher)";
        String msg = "urface";
        List<String> list = returnValue(msg1);
        System.out.println(list);
    }

   /* @RequestMapping("download")
    public void  download(HttpServletResponse response) throws Exception {
        //获取模板的输入流
        InputStream inputStream = UploadController.class.getClassLoader().getResourceAsStream("templates" + File.separator + "xx.xlsx");
        if (inputStream == null) {
            LOGGER.error("模板文件的路径不正确");

        }
        //测试数据
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setUsername("张三" + i + 1);
            user.setPassword("123" + i + 1);
            users.add(user);
        }
        //获取模板的工作薄
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheetAt = workbook.getSheetAt(0);
        XSSFRow row = sheetAt.getRow(0);
        short rowHeight = row.getHeight();
        XSSFRow row1 = null;
        XSSFCell cell = null;
        for (int j = 0; j < users.size(); j++) {
            User user = users.get(j);
            row1 = sheetAt.createRow(j + 1);
            row1.setHeight(rowHeight);
            cell = row1.createCell(0);
            cell.setCellValue(user.getId());

            cell = row1.createCell(1);
            cell.setCellValue(user.getUsername());

            cell = row1.createCell(2);
            cell.setCellValue(user.getPassword());
        }
        String filename = new String("文件".getBytes("UTF-8"),"ISO8859-1");
        LOGGER.info("文件名"+filename);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename="+filename+".xlsx");
        workbook.write(response.getOutputStream());
        LOGGER.info("文件下载成功！！");
        response.getOutputStream().flush();
        response.getOutputStream().close();
        workbook.close();


    }
    */
}

