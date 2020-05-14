package com.chainfuture.code.controller.papi;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.controller.backapi.VipAdminController;
import com.chainfuture.code.filter.BackApiLoginContext;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.mapper.SysModelMapper;
import com.chainfuture.code.mapper.WorkapiRecordMapper;
import com.chainfuture.code.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/papi/forum/")
public class ForumController {

    @Autowired
    private AuthProductService authProductService;
    @Autowired
    private PrimeTxService primeTxService;
    @Autowired
    private SysManageMapper sysManageMapper;
    @Autowired
    private AuthManageService authManageService;
    @Autowired
    private SysModelMapper sysModelMapper;
    @Autowired
    private HomePageService homePageService;
    @Autowired
    private WorkapiRecordMapper workapiRecordMapper;
    @Autowired
    private SysModuleService sysModuleService;
    public static final Logger LOGGER = Logger.getLogger(ForumController.class);

    public ForumController(){
        // TODO Auto-generated constructor stub
    }

    @RequestMapping("getHomePage")
    @ResponseBody
    public JSONObject getHomePage(HttpServletRequest request, HttpServletResponse response){
        JSONObject mv = new JSONObject();
        mv.put("success",false);
        try{
            HomePage data= homePageService.getHomePage();
            if(data==null){
                mv.put("code",1);
                mv.put("msg","失败");
                return mv;
            }
            mv.put("data",data);
        }catch (Exception e){
            mv.put("code",1);
            mv.put("msg","失败");
            return mv;
        }
        mv.put("code",0);
        mv.put("success",true);
        return mv;
    }
    @RequestMapping(value = "addForumManage" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject  addForumManage(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success",false);
        String userAddress = WwSystem.ToString(request.getParameter("userAddress"));
        if (StringUtils.isEmpty(userAddress)){
            res.put("code",2);
            res.put("msg","参数错误");
            return res;
        }
        List<SysManage> list = null;
        SysModule  sysm = null;
        try{
            SysModule sm = new SysModule();
            sm.setSname("ForumManageAddress");
            List<SysModule> sysModule = sysModuleService.getModuleName(sm);
            AuthManage authManage = new AuthManage();
            authManage.setSpecialValid(0);
            authManage.setManageAddr(sysModule.get(0).getAddress());
            authManage.setDutyAddress(userAddress);
            authManage.setStatus(0);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation("邀约");
            authManageService.addAuthManage(authManage);
            if(authManage.getMid()<0){
                res.put("code",1);
                res.put("msg","新增失败");
                return res;
            }
            NrcMain nm = new NrcMain();
            nm.initRPC();
            String msg="id:"+authManage.getMid()+"*work:0";
            String signMessage = nm.signMessage(sysModule.get(0).getAddress(),msg);
            LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                res.put("code",2);
                res.put("msg","签名失败");
                return res;
            }
            String message = "https://reitschain.com/code/workCard?s="+signMessage+"&o="+msg+"&a="+sysModule.get(0).getAddress();
            res.put("message",message);
            sysm = sysModuleService.moduleInfo(sysModule.get(0).getAddress());
            if (sysm==null){
                res.put("code",4);
                res.put("msg","模块信息有误");
                return res;
            }
        }catch (Exception e){
            res.put("code",3);
            res.put("msg","失败"+e.getMessage());
            return res;
        }
        res.put("code", 0);
        res.put("success",true);
        res.put("msg", "请使用微信扫码，成为"+sysm.getModuleName()+"管理员");
        return res;
    }

    @RequestMapping(value = "appropriation", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject appropriation(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String fromAddress = WwSystem.ToString(request.getParameter("fromAddress"));
        if (StringUtils.isEmpty(fromAddress)){
            res.put("msg","参数有误");
            res.put("code",1);
            return res;
        }
        String busiJson = WwSystem.ToString(request.getParameter("busiJson"));
        String toAddress = WwSystem.ToString(request.getParameter("toAddress"));
        if (StringUtils.isEmpty(toAddress)){
            res.put("msg","参数有误");
            res.put("code",1);
            return res;
        }
        int amount = WwSystem.ToInt(request.getParameter("amount"));
        if (amount<=0){
            res.put("msg","支付金额必须大于零");
            res.put("code",2);
            return res;
        }
        try{
            NrcMain nm = new NrcMain();
            String primeaddr = nm.getPrimeaddr();
            if (StringUtils.isEmpty(primeaddr)){
                res.put("msg","节点内部错误");
                res.put("code",3);
                return res;
            }
            String primeFromAddress = nm.getPrimeaddr(fromAddress);
            if (StringUtils.isEmpty(primeFromAddress)  || !primeaddr.equals(primeFromAddress)){
                res.put("msg","节点内部错误");
                res.put("code",3);
                return res;
            }
            String primeToAddress = nm.getPrimeaddr(toAddress);
            if (StringUtils.isEmpty(primeToAddress)){
                res.put("msg","节点内部错误");
                res.put("code",3);
                return res;
            }
            PrimeTxExample  pt = new PrimeTxExample();
            pt.setFromAddress(fromAddress);
            pt.setToAddress(toAddress);
            pt.setAmount(amount);
            pt.setPayStatus(0);
            pt.setBusiJson(busiJson);
            pt.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            pt.setPrimeAddress(primeToAddress);
            primeTxService.addPrimeTx(pt);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",3);
            return res;
        }
        res.put("msg","支付成功");
        res.put("code",0);
        return res;
    }

    @RequestMapping(value = "getActivityList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getActivityList(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        int type = WwSystem.ToInt(request.getParameter("type"));
        if (type<0){
            res.put("msg","参数有误");
            res.put("code",2);
            return res;
        }
        try{
            List<ProductExample> list = authProductService.selVipProductList(type);
            if (list.size()<=0){
                res.put("code", 1);
                res.put("msg", "无记录");
                return res;
            }
            res.put("data",list);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",1);
            return res;
        }
        res.put("success",true);
        res.put("msg","成功");
        res.put("code",0);
        return  res;
    }

    @RequestMapping(value = "getActivityListByAddress", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getActivityListByAddress(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String userAddress = WwSystem.ToString(request.getParameter("userAddress"));
        String address = WwSystem.ToString(request.getParameter("address"));
        int type = WwSystem.ToInt(request.getParameter("type"));
        if (type<0 || StringUtils.isEmpty(address)){
            res.put("msg","参数有误");
            res.put("code",2);
            return res;
        }
        try{
            PrimeTxExample ptx = new PrimeTxExample();
            ptx.setFromAddress(address);
            ptx.setToAddress(userAddress);
            List<PrimeTxExample> list = primeTxService.selPrimeTxByFromAndToAddr(ptx);
            if (list.size()<=0){
                res.put("msg","未参加");
                res.put("success",true);
                res.put("code",3);
                return res;
            }
//            res.put("data",list);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",1);
            return res;
        }
        res.put("success",true);
        res.put("msg","已参加");
        res.put("code",0);
        return  res;
    }

    @RequestMapping(value = "getActivityListByPerson", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getActivityListByPerson(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String address = WwSystem.ToString(request.getParameter("userAddress"));
        if (StringUtils.isEmpty(address)){
            res.put("msg","参数有误");
            res.put("code",2);
            return res;
        }
        int pageIndex = WwSystem.ToInt(request.getParameter("pageIndex"));
        int pageSize = WwSystem.ToInt(request.getParameter("pageSize"));
        if (pageIndex<0){
            pageIndex=0;
        }
        if (pageSize<=0){
            pageSize=20;
        }
        int type = WwSystem.ToInt(request.getParameter("type"));
        if (type<0){
            res.put("msg","参数有误");
            res.put("code",2);
            return res;
        }
        try{
            Map<String,Object> map = new HashMap<>();
            map.put("address",address);
            map.put("type",type);
            map.put("pageIndex",pageIndex);
            map.put("pageSize",pageSize);
            List<Map<String,Object>> list = primeTxService.selPrimeTxByPerson(map);
            res.put("data",list);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",1);
            return res;
        }
        res.put("success",true);
        res.put("msg","成功");
        res.put("code",0);
        return  res;
    }

    @RequestMapping(value = "getAccountAddress", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getAccountAddress(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String name = WwSystem.ToString(request.getParameter("name"));
        if (StringUtils.isEmpty(name)){
            res.put("msg","参数有误");
            res.put("code",2);
            return res;
        }
        try{
           NrcMain nm = new NrcMain();
           nm.initRPC();
           String accountAddress = nm.getAccountAddress(name);
           if (StringUtils.isEmpty(accountAddress)){
                res.put("msg","节点内部错误");
                res.put("code",3);
                return res;
            }
            res.put("account",accountAddress);

        }catch (Exception e){
            res.put("msg","获取失败");
            res.put("code",1);
            return res;
        }
        res.put("success",true);
        res.put("msg","成功");
        res.put("code",0);
        return  res;
    }
}
