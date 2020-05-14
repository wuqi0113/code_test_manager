package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.bitcoin.WwHttpSession;
import com.chainfuture.code.service.MajorLogService;
import com.chainfuture.code.service.MedalIconService;
import com.chainfuture.code.service.MedalService;
import com.chainfuture.code.service.ProductService;
import com.chainfuture.code.utils.HttpRequestUtil;
import com.chainfuture.code.utils.IPUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/medal")
public class MedalController{

    @Autowired
    private MedalService medalService;
    @Autowired
    private MedalIconService medalIconService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MajorLogService majorLogService;
    public  final Logger LOGGER = Logger.getLogger(MedalController.class);

    private WwHttpSession session = null;
    private String error="";

    private static String  host="127.0.0.1"; //换成你们的NRC节点IP
    private static String  userName="qrcode0";//换成你们用户名
    private static String  password="Gnx8nAdGknoQ3VyhfKwKzgQoUbDAzeDUekie-emTCjE=";//换成你们密码
    private static int  port=18999;//换成你们端口

    @RequestMapping("/regTest")
    @ResponseBody
    public JSONObject regTest(HttpServletRequest request, HttpServletResponse response){
        NrcMain nm=new NrcMain();
        JSONObject result=new JSONObject();
        nm.initRPC(host,userName,password,port);
        net.sf.json.JSONObject res=nm.getInfo();
        result.put("info",res);
//        String addr = nm.getAccountAddress("pjj1006");
//        result.put("address",addr);
        return result;
    }

    @RequestMapping("toAdminList")
    public String  toAdminList(Integer id,HttpServletRequest request, HttpServletResponse response){
        return "view/medal_edit";
    }


    @ResponseBody
    @RequestMapping("monitorAddr")
    public JSONObject  monitorAddr(Integer id,Integer page,Integer limit,HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<Product> monitorList = null;
        try{
            monitorList = productService.monitorAddr(id);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败，错误信息：" + e.getMessage());
        }
        res.put("total",monitorList);
        res.put("rows",0);
        return res;
    }


    @RequestMapping("/listMedal")
    public String listMedal(Medal medal,HttpServletRequest request, HttpServletResponse response){
        return "view/medal-manage";
    }

    @RequestMapping("/updateIcon")
    @ResponseBody
    public ModelAndView updateIcon(Medal medal, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        mv.setViewName("redirect:/medal/listMedal");
        try{
            medalService.updateMedal(medal);
        }catch (Exception e){
            e.printStackTrace();
            mv.addObject("code",1);
            mv.addObject("msg","保存失败，错误信息：" + e.getMessage());
        }
        return mv;
    }

    @RequestMapping("/tolistMedal")
    @ResponseBody
    public JSONObject tolistMedal(Medal medal,HttpServletRequest request, HttpServletResponse response){
        JSONObject map =new JSONObject();
        List<Medal> list  = null;
        Integer total = 0;
        map.put("code",0);
        map.put("msg","成功");
        try{
            list = medalService.listMedal(medal);
            total = medalService.queryCount(medal);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",1);
            map.put("msg","保存失败，错误信息：" + e.getMessage());
        }
        map.put("data",list); // 总条数
        map.put("count", total); // 列表数据
        return map;
    }

    @RequestMapping("/getById")
    public ModelAndView getById(Integer id, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
        Medal medal=null;
        List<MedalIcon> medalIcons=null;
        List<Product> productList=null;
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        try{
            medal =  medalService.getById(id);
            medalIcons = medalIconService.listMedalIcon();
            productList = productService.getProductAddr();
        }catch (Exception e){
            e.printStackTrace();
            mv.addObject("code",1);
            mv.addObject("msg", "保存失败，错误信息：" + e.getMessage());
        }
        mv.addObject("data",medal);
        mv.addObject("productList",productList);
        mv.addObject("medalIconList",medalIcons);
        mv.setViewName("view/medal_edit");
        return mv;
    }

    @RequestMapping("/saveMedal")
    @ResponseBody
    public Map<String, Object> saveMedal(HttpServletRequest request, Medal medal, MultipartFile file ){
        Map<String,Object> map = Maps.newHashMap();
        map.put("code", 0);
        map.put("msg", "保存成功");
        MajorLog ml = new MajorLog();
        try {
            if (medal.getId()!=null){
                medal.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                medalService.updateMedal(medal);
            }else {
                NrcMain nm = new NrcMain();
                nm.initRPC();
                String accountAddress = nm.getAccountAddress(medal.getSname());
                ml.setOperation("产生勋章:"+medal.getMedalName()+"："+accountAddress);
                if(accountAddress==null){
//                    String s = nm.sendToAddress("14wsJoKvHo3se3RAURZVQno6heF12PZJ6K", "999999:1", null, null);
                    String param="toAddress=1Nx69PCDKL9z7rU8Gq1QrW4gnLMcy7MHvm&assetId=999999&amount=1";
                    String s = HttpRequestUtil.sendPost("http://127.0.0.1:9590/sendToAddress", param, true);
                    LOGGER.error("发送交易:"+s+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    ml.setOperation("发送交易:"+s+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
                medal.setStatus(1);
                medal.setMedalAddr(accountAddress);
                medal.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                medalService.saveMedal(medal);
                Object admin = request.getSession().getAttribute("admin");
                ml.setMember(admin.toString());
                String remoteAddr = IPUtils.getRemoteAddr(request);
                ml.setRemoteAddr(remoteAddr);
                ml.setNodeAddress(medal.getProduct());
                ml.setMethod(request.getRequestURL().toString());
                ml.setOperatime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                majorLogService.insertMajorLog(ml);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "保存失败，错误信息：" + e.getMessage());
        }
        return map;
    }
}
