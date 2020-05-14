package com.chainfuture.code.utils;

import org.apache.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;

/**
 * Created by admin on 2018/11/27.
 */
public class DownloadTxt {
    public static final Logger LOGGER = Logger.getLogger(DownloadTxt.class);

    /* 导出txt文件
     * @author
     * @param	response
     * @param	text 导出的字符串
     * @return
     */
    public  void exportTxt(HttpServletResponse response, String text,String cnName){
        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader("Content-Disposition","attachment;filename="+cnName
               // + genAttachmentFileName( cnName, "JSON_FOR_UCC_")//设置名称格式，没有这个中文名称无法显示
                + ".txt");
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(text.getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                buff.close();
                outStr.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    //防止中文文件名显示出错
    public  String genAttachmentFileName(String cnName, String defaultName) {
        try {
            cnName = new String(cnName.getBytes("gb2312"), "ISO8859-1");
        } catch (Exception e) {
            cnName = defaultName;
        }
        return cnName;
    }

    public static   boolean resExportTxt(HttpServletResponse response, String text,String cnName){
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        response.addHeader("Content-Disposition","attachment;filename="+cnName
                + ".txt");
        BufferedOutputStream buff = null;
        ServletOutputStream outStr = null;
        try {
            outStr = response.getOutputStream();
            buff = new BufferedOutputStream(outStr);
            buff.write(text.getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (Exception e) {
//            e.getMessage();
            return false;
        } finally {
            try {
                buff.close();
                outStr.close();
            } catch (Exception e) {
//                e.getMessage();
                return false;
            }
        }
        return true;
    }

}
