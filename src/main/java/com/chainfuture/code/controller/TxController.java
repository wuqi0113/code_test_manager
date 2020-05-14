package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.Tx;
import com.chainfuture.code.service.TxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/tx")
public class TxController {

    @Autowired
    private TxService txService;

    @RequestMapping("getUserList")
    @ResponseBody
    public JSONObject  getUserList(String verifiyType,Tx tx,HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<Tx> list=null;
        int count=0;
        try{
            list = txService.getUserList(tx);
            count= txService.getCount(tx);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","查询失败，错误信息：" + e.getMessage());
        }
        res.put("data",list);
        res.put("count",count);
        return res;
    }


    public static   List<String> getImgStr(String htmlStr) {
        List<String> list = new ArrayList<>();
//        list.get(list.size()-1);
        String img = "";
        Pattern p_image;
        Matcher m_image;
        // String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*data-src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("data-src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                list.add(m.group(1));
            }
        }
        return list;
    }

    public static void main(String[] args){
        List<String> imgStr = getImgStr("<p>\n" +
                "    使用的是测试积分，希望各位在开放提币功能后，将其打回系统。谢谢！！！\n" +
                "</p><img class=\"\" data-copyright=\"0\" data-ratio=\"0.936\" data-s=\"300,640\" data-src=\"https://mmbiz.qpic.cn/mmbiz_jpg/FIBFp81yz60ZTwcUPJX20iaYs5IFfKIhtlWKdAYjicQV2QWgpp00KG9iaiccicocKrrYwBRomQmJUibibu5xiaichIF0qibg/640?wx_fmt=jpeg\" data-type=\"jpeg\" data-w=\"750\" _width=\"auto\" src=\"https://mmbiz.qpic.cn/mmbiz_jpg/FIBFp81yz60ZTwcUPJX20iaYs5IFfKIhtlWKdAYjicQV2QWgpp00KG9iaiccicocKrrYwBRomQmJUibibu5xiaichIF0qibg/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1\" crossorigin=\"anonymous\" data-fail=\"0\" style=\"margin: 0px; padding: 0px; height: auto !important; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important; width: auto !important; visibility: visible !important; zoom: 0.727272727272727;\"/><img class=\"\" data-copyright=\"0\" data-ratio=\"0.936\" data-s=\"300,640\" data-src=\"https://mmbiz.qpic.cn/mmbiz_jpg/FIBFp81yz60ZTwcUPJX20iaYs5IFfKIhtlWKdAYjicQV2QWgpp00KG9iaiccicocKrrYwBRomQmJUibibu5xiaichIF0qibg/640?wx_fmt=jpeg\" data-type=\"jpeg\" data-w=\"750\" _width=\"auto\" src=\"https://mmbiz.qpic.cn/mmbiz_jpg/FIBFp81yz60ZTwcUPJX20iaYs5IFfKIhtlWKdAYjicQV2QWgpp00KG9iaiccicocKrrYwBRomQmJUibibu5xiaichIF0qibg/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1\" crossorigin=\"anonymous\" data-fail=\"0\" style=\"margin: 0px; padding: 0px; height: auto !important; max-width: 100%; box-sizing: border-box !important; word-wrap: break-word !important; width: auto !important; visibility: visible !important; zoom: 0.727272727272727;\"/>");
        System.out.println(imgStr);

//        String htmlPicture = getHtmlPicture("https://mmbiz.qpic.cn/mmbiz_jpg/FIBFp81yz60ZTwcUPJX20iaYs5IFfKIhtlWKdAYjicQV2QWgpp00KG9iaiccicocKrrYwBRomQmJUibibu5xiaichIF0qibg/640?wx_fmt=jpeg", "F:\\", null);
       /* String htmlPicture = getHtmlPicture("http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg", "public/file_list/images", null);
        System.out.println(htmlPicture);*/
    }


    /**下载网络图片上传到服务器上
     * @param httpUrl 图片网络地址
     * @param filePath  图片保存路径
     * @param myFileName  图片文件名(null时用网络图片原名)
     * @return  返回图片名称
     */
    public  String getHtmlPicture(String httpUrl, String filePath , String myFileName) {

        URL url;                        //定义URL对象url
        BufferedInputStream in;         //定义输入字节缓冲流对象in
        FileOutputStream file;          //定义文件输出流对象file
        try {
            String fileName = null == myFileName?httpUrl.substring(httpUrl.lastIndexOf("/")).replace("/", ""):myFileName; //图片文件名(null时用网络图片原名)
            url = new URL(httpUrl);     //初始化url对象
            in = new BufferedInputStream(url.openStream());                                 //初始化in对象，也就是获得url字节流
            //file = new FileOutputStream(new File(filePath +"\\"+ fileName));
            file = new FileOutputStream(mkdirsmy(filePath,fileName));
            int t;
            while ((t = in.read()) != -1) {
                file.write(t);
            }
            file.close();
            in.close();
            return fileName;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**判断路径是否存在，否：创建此路径
     * @param dir  文件路径
     * @param realName  文件名
     * @throws IOException  微信游戏   腾讯新闻
     */
    public  File mkdirsmy(String dir, String realName) throws IOException{
        File file = new File(dir, realName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }
   /**
     * @param response
     * @param filePath      //文件完整路径(包括文件名和扩展名)
     * @param fileName      //下载后看到的文件名
     * @return  文件名
     *//*
   /*  public static void fileDownload(final HttpServletResponse response, String filePath, String fileName) throws Exception{
        byte[] data = FileUtil.toByteArray2(filePath);
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName );
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();
    }*/


}
