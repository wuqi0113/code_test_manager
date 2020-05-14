package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.MajorLog;
import com.chainfuture.code.service.MajorLogService;
import com.chainfuture.code.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/majorlog")
public class MajorLogController {

    @Autowired
    private MajorLogService majorLogService;


    @RequestMapping("/toMajorLogPage")
    public String   toMajorLogPage(HttpServletRequest request, HttpServletResponse response){
        return  "view/majorlog_view";
    }

   @RequestMapping("/majorLogList")
   @ResponseBody
    public JSONObject majorLogList(MajorLog majorLog,HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<MajorLog> list = null;
        Integer count = 0;
        try{
            list = majorLogService.listMajorLog(majorLog);
            count = majorLogService.queryCount(majorLog);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败");
        }
        res.put("data",list);
        res.put("count",count);
        return  res;
    }

    @RequestMapping("/getById")
    @ResponseBody
    public ModelAndView getById(Integer id, HttpServletRequest request, HttpServletResponse response){
        ModelAndView res = new ModelAndView();
        res.addObject("code", 0);
        res.addObject("msg", "成功");
        List<MajorLog> list = null;
        try{
            list = majorLogService.getById(id);
        }catch (Exception e){
            e.printStackTrace();
            res.addObject("code", 1);
            res.addObject("msg", "失败");
        }
        res.addObject("data",list);
        res.setViewName("view/majorlog_edit");
        return  res;
    }

    @RequestMapping("/insertMajorLog")
    @ResponseBody
    public ModelAndView insertMajorLog(HttpServletRequest request, MajorLog model){
        ModelAndView mv =new ModelAndView();
        mv.addObject("code", 0);
        mv.addObject("msg", "保存成功");
        try {
            majorLogService.insertMajorLog(model);
        } catch (Exception e) {
            e.printStackTrace();
            mv.addObject("code", 1);
            mv.addObject("msg", "保存失败，错误信息：" + e.getMessage());
        }
        mv.setViewName("redirect:/majorlog/toMajorLogPage");
        return mv;
    }
    @RequestMapping("/getAll")
    @ResponseBody
    public JSONObject getAll(HttpServletRequest request) {
        JSONObject res = new JSONObject();
        res.put("Inet1","浏览器基本信息："+request.getHeader("user-agent"));
        res.put("Inet2","客户端系统名称："+System.getProperty("os.name"));
        res.put("Inet3","客户端系统版本："+System.getProperty("os.version"));
        res.put("Inet4","客户端操作系统位数："+System.getProperty("os.arch"));
        res.put("Inet5","HTTP协议版本："+request.getProtocol());
        res.put("Inet6","请求编码格式："+request.getCharacterEncoding());
        res.put("Inet7","Accept："+request.getHeader("Accept"));
        res.put("Inet8","Accept-语言："+request.getHeader("Accept-Language"));
        res.put("Inet9","Accept-编码："+request.getHeader("Accept-Encoding"));
        res.put("Inet10","Connection："+request.getHeader("Connection"));
        res.put("Inet11","Cookie："+request.getHeader("Cookie"));
        res.put("Inet12","客户端发出请求时的完整URL"+request.getRequestURL());
        res.put("Inet13","请求行中的资源名部分"+request.getRequestURI());
        res.put("Inet14","请求行中的参数部分"+request.getRemoteAddr());
        res.put("Inet15","客户机所使用的网络端口号"+request.getRemotePort());
        res.put("Inet16","WEB服务器的IP地址"+request.getLocalAddr());
        res.put("Inet17","WEB服务器的主机名"+request.getLocalName());
        res.put("Inet18","客户机请求方式"+request.getMethod());
        res.put("Inet19","请求的文件的路径"+request.getServerName());
        res.put("Inet30","请求的文件的路径"+ IPUtils.getRemoteAddr(request));
        try {
            res.put("Inet20","请求体的数据流"+request.getReader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br= null;
        try {
            br = request.getReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String ras = "";
        try {
            while ((ras = br.readLine()) != null) {
                res.put("Inet21","request body:" + ras);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        res.put("Inet22","请求所使用的协议名称"+request.getProtocol());
        res.put("Inet23","请求中所有参数的名字"+request.getParameterNames());
        Enumeration enumNames= request.getParameterNames();
        while (enumNames.hasMoreElements()) {
            String key = (String) enumNames.nextElement();
            res.put("Inet24","参数名称："+key);
        }
        return res;
    }

    
}
