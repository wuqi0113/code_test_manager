package com.chainfuture.code.controller.backapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.FileUploadUtil;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.filter.BackApiLoginContext;
import com.chainfuture.code.filter.BackApiValid;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.mapper.SysModelMapper;
import com.chainfuture.code.service.AuthManageService;
import com.chainfuture.code.service.HomePageService;
import com.chainfuture.code.service.SysModuleService;
import com.chainfuture.code.service.WeChatUserService;
import com.chainfuture.code.utils.ApiUtil;
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
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/backapi/vipadmin/")
public class VipAdminController {


    @Autowired
    private SysManageMapper sysManageMapper;
    @Autowired
    private WeChatUserService weChatUserService;
    @Autowired
    private AuthManageService authManageService;
    @Autowired
    private HomePageService homePageService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private SysModelMapper sysModelMapper;
    public static final Logger LOGGER = Logger.getLogger(VipAdminController.class);

    @ResponseBody
    @RequestMapping(value="getUserAuth", method = RequestMethod.POST)
    public JSONObject getUserAuth(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        String sname = WwSystem.ToString(request.getParameter("sname"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        if (StringUtils.isEmpty(sname)){
            res.put("code", 102);
            res.put("msg", "无权访问");
            return res;
        }
        try{
            SysManage sysManage = new SysManage();
            sysManage.setSname(sname);
            sysManage.setAddress(user.getAddress());
            SysManage  sm = sysManageMapper.selSysManagerByUserAndSname(sysManage);
            if (sm==null){
                res.put("code", 102);
                res.put("msg", "无权访问");
                return res;
            }
            res.put("data",sm);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",1);
            return res;
        }
        res.put("code",0);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value="manageList", method = RequestMethod.POST)
    public JSONObject manageList(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        LOGGER.info("backLogin:"+token);
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String moduleAddress = WwSystem.ToString(request.getParameter("moduleAddress"));
        if (StringUtils.isEmpty(moduleAddress)) {
            res.put("code", 1);
            res.put("msg", "参数有误");
            return res;
        }
        String startTime = WwSystem.ToString(request.getParameter("startTime"));
        String endTime = WwSystem.ToString(request.getParameter("endTime"));
        try{
            HashMap<String,Object>  map = new HashMap<String,Object>();
            map.put("manageAddr",moduleAddress);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            List<HashMap<String,Object>>  list = sysManageMapper.manageList(map);
            if (list.size()<=0){
                res.put("code", 2);
                res.put("msg", "无记录");
                return res;
            }
            res.put("manageData",list);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",3);
            return res;
        }
        res.put("code",0);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value="getRolesList", method = RequestMethod.POST)
    public JSONObject getRolesList(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        String userAddress = WwSystem.ToString(request.getParameter("userAddress"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }

        try{
//            SysModule sysModule = new SysModule();
//            List<SysModule> moduleList = sysModuleService.getModuleName(sysModule);
            Map<String,Object> map = new HashMap<>();
            if (StringUtils.isEmpty(userAddress)){
                map.put("address",user.getAddress());
            }else {
                map.put("address",userAddress);
            }
            List<SysManage>  list = sysManageMapper.getManagerListByAddr(map);
            if (list.size()<=0){
                res.put("code",102);
                res.put("msg","无权访问");
                return res;
            }
            res.put("moduleList",list);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",1);
            return res;
        }
        res.put("code",0);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }


    @RequestMapping(value = "addManage" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject  addManage(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String address = WwSystem.ToString(request.getParameter("address"));
        if (StringUtils.isEmpty(address)){
            res.put("code",2);
            res.put("msg","参数错误");
            return res;
        }
        List<SysManage> list = null;
        SysModule  sysm = null;
        try{
            AuthManage authManage = new AuthManage();
            list = sysManageMapper.checkManager(address);
            if (list.size()<=0){
                SysModule sm = new SysModule();
                sm.setSname("PersonManageAddress");
                List<SysModule> sysModule = sysModuleService.getModuleName(sm);
                if(address.equals(sysModule.get(0).getAddress())){
                    authManage.setSpecialValid(1);//第一个进入系统，并确认的人拥有特殊权限，即类似超管的权限
                    address=sysModule.get(0).getAddress();
                }else {
                    authManage.setSpecialValid(0);
                }
            }else {
                authManage.setSpecialValid(0);
            }
            authManage.setManageAddr(address);
//            authManage.setAddress(user.getAddress());
            authManage.setDutyAddress(user.getAddress());
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
            String signMessage = nm.signMessage(address,msg);
            LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                res.put("code",2);
                res.put("msg","签名失败");
                return res;
            }
            String message = "https://reitschain.com/code/workCard?s="+signMessage+"&o="+msg+"&a="+address;
            res.put("message",message);

            sysm = sysModuleService.moduleInfo(address);
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
        res.put("msg", user.getNickname()+"邀请您成为"+sysm.getModuleName()+"管理员");
        return res;
    }

    @RequestMapping("getHomePage")
    @ResponseBody
    public JSONObject getHomePage(HttpServletRequest request, HttpServletResponse response){
        JSONObject mv = new JSONObject();
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            mv.put("code", 101);
            mv.put("msg", "已过期请重新登录");
            return mv;
        }
        HomePage data= homePageService.getHomePage();
        if(data==null){
            mv.put("success",false);
            mv.put("code",1);
            mv.put("msg","失败");
            return mv;
        }
        mv.put("data",data);
        mv.put("code",0);
        mv.put("success",true);
        return mv;
    }

    @ResponseBody
    @RequestMapping(value="personListByModule",method=RequestMethod.POST)
    public JSONObject personListByModule(HttpServletRequest request,HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            result.put("code", 101);
            result.put("msg", "已过期请重新登录");
            return result;
        }
        String address = WwSystem.ToString(request.getParameter("address"));
        String moduleAddress = WwSystem.ToString(request.getParameter("moduleAddress"));
        if (StringUtils.isEmpty(moduleAddress)) {
            result.put("code", 1);
            result.put("msg", "参数有误");
            return result;
        }
        try{
            SysModule sysModule = sysModuleService.moduleInfo(moduleAddress);
            if (sysModule==null){
                result.put("code", 2);
                result.put("msg", "地址信息有误");
                return result;
            }
            List<HashMap<String,Object>> list = new ArrayList<>();
            AuthManageExample  authManageExample  = new AuthManageExample();
            authManageExample.setDutyAddress(address);
//            authManageExample.setManageAddr(moduleAddress);
            if (sysModule.getSname().equals("PersonManageAddress")){
//                list = authManageService.getRecordListByModule(authManageExample);
                authManageExample.setOperation("邀约");
                List<HashMap<String,Object>> list1 = authManageService.getInvitRecord(authManageExample);
                if (list1.size()>0){
                    list.addAll(list1);
                }
                authManageExample.setOperation("删除");
                List<HashMap<String,Object>> list2 = authManageService.getInvitRecord(authManageExample);
                if (list2.size()>0){
                    list.addAll(list2);
                }
                authManageExample.setOperation("修改");
                List<HashMap<String,Object>> list3 = authManageService.getInvitRecord(authManageExample);
                if (list3.size()>0){
                    list.addAll(list3);
                }
              /*  authManageExample.setOperation("基础设置");
                List<HashMap<String,Object>> list4 = authManageService.getBaseSet(authManageExample);
                if (list4.size()>0){
                    list.addAll(list4);
                }*/
                if (list.size()<=0){
                    result.put("code", 2);
                    result.put("msg", "无记录");
                    return result;
                }
            }else if (sysModule.getSname().equals("ProductionManageAddress")){
                authManageExample.setOperation("挂接");
                list = authManageService.getHangUpRecordList(authManageExample);
                if (list.size()<=0){
                    result.put("code", 2);
                    result.put("msg", "无记录");
                    return result;
                }
            }else if (sysModule.getSname().equals("OperationalManageAddress")){
                authManageExample.setOperation("添加产品");
                List<HashMap<String,Object>> list1 = authManageService.getProductRecordList(authManageExample);
                if (list1.size()>0){
                    list.addAll(list1);
                }
                authManageExample.setOperation("修改产品");
                List<HashMap<String,Object>> list2 = authManageService.getProductRecordList(authManageExample);
                if (list2.size()>0){
                    list.addAll(list2);
                }
                authManageExample.setOperation("添加活动");
                List<HashMap<String,Object>> list3 = authManageService.getProductRecordList(authManageExample);
                if (list3.size()>0){
                    list.addAll(list3);
                }
                authManageExample.setOperation("修改活动");
                List<HashMap<String,Object>> list4 = authManageService.getProductRecordList(authManageExample);
                if (list4.size()>0){
                    list.addAll(list4);
                }
                authManageExample.setOperation("添加产品码");
                List<HashMap<String,Object>> list5 = authManageService.getBatchList(authManageExample);
                if (list5.size()>0){
                    list.addAll(list5);
                }
                authManageExample.setOperation("添加活动码");
                List<HashMap<String,Object>> list6 = authManageService.getBatchList(authManageExample);
                if (list6.size()>0){
                    list.addAll(list6);
                }
                if (list.size()<=0){
                    result.put("code", 2);
                    result.put("msg", "无记录");
                    return result;
                }
            }else {
                result.put("code", 2);
                result.put("msg", "地址信息有误");
                return result;
            }
            result.put("data",list);
        }catch (Exception e){
            result.put("code", 4);
            result.put("msg", "失败"+e.getMessage());
            return result;
        }
        result.put("code", 0);
        result.put("success", true);
        result.put("msg", "成功");
        return result;
    }


    @ResponseBody
    @RequestMapping(value="delManager",method=RequestMethod.POST)
    public JSONObject delManager(HttpServletRequest request,HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            result.put("code", 101);
            result.put("msg", "已过期请重新登录");
            return result;
        }
        String address = WwSystem.ToString(request.getParameter("address"));
        String moduleAddress = WwSystem.ToString(request.getParameter("moduleAddress"));
        if (StringUtils.isEmpty(moduleAddress) && StringUtils.isEmpty(address)) {
            result.put("code", 1);
            result.put("msg", "参数有误");
            return result;
        }
        if (user.getAddress().equals(address)){
            result.put("code", 5);
            result.put("msg", "不允许删除自己");
            return result;
        }
        try{
            SysModule sm = new SysModule();
            sm.setSname("PersonManageAddress");
            List<SysModule> sysModule = sysModuleService.getModuleName(sm);
            Map<String,Object> map =new HashMap<>();
            map.put("address",user.getAddress());
            map.put("manageAddr",sysModule.get(0).getAddress());
            SysManage  sysm = sysManageMapper.selSysManageByPAddrAndUAddr(map);
            if (sysm==null){
                result.put("code", 2);
                result.put("msg", "您不是管理员");
                return result;
            }
            if (sysm.getSpecialValid()!=1){
                result.put("code", 3);
                result.put("msg", "你无权进行此操作");
                return result;
            }
            SysManage  sysManage = new SysManage();
            sysManage.setManageAddr(moduleAddress);
            sysManage.setAddress(address);
            sysManageMapper.delManager(sysManage);
            AuthManage  authManage =new AuthManage();
            authManage.setManageAddr(moduleAddress);
            authManage.setAddress(address);
            authManage.setDutyAddress(user.getAddress());
            authManage.setStatus(1);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation("删除");
            authManageService.addAuthManage(authManage);
            if(authManage.getMid()<0){
                result.put("code",1);
                result.put("msg","新增失败");
                return result;
            }
        }catch (Exception e){
            result.put("code", 4);
            result.put("msg", "失败"+e.getMessage());
            return result;
        }
        result.put("code", 0);
        result.put("success", true);
        result.put("msg", "成功");
        return result;
    }

    @ResponseBody
    @RequestMapping(value="getVipUserInfo",method=RequestMethod.POST)
    public JSONObject getVipUserInfo(HttpServletRequest request,HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        String token=WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            result.put("code", 101);
            result.put("msg", "已过期请重新登录");
            return result;
        }
        String address = WwSystem.ToString(request.getParameter("userAddress"));
        if (StringUtils.isEmpty(address)){
            result.put("code", 2);
            result.put("msg", "参数有误");
            return result;
        }
        try{
            WeChatUserExample weChatUser = weChatUserService.selUserByAddr(address);
            if (weChatUser==null){
                result.put("code", 1);
                result.put("msg", "无记录");
                return result;
            }
            result.put("data",weChatUser);
        }catch (Exception e){
            result.put("code", 2);
            result.put("msg", "失败");
            return result;
        }
        result.put("success",true);
        result.put("code", 0);
        result.put("msg", "成功");
        return result;
    }

    @ResponseBody
    @RequestMapping(value="updateVipUserInfo",method=RequestMethod.POST)
    public JSONObject updateVipUserInfo(HttpServletRequest request,HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        String token=WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            result.put("code", 101);
            result.put("msg", "已过期请重新登录");
            return result;
        }
        String address = WwSystem.ToString(request.getParameter("address"));
        if (StringUtils.isEmpty(address)){
            result.put("code", 2);
            result.put("msg", "参数有误");
            return result;
        }
        String nickName = WwSystem.ToString(request.getParameter("nickName"));
        String phone = WwSystem.ToString(request.getParameter("phone"));
        try{
            WeChatUserExample weChatUserExample = weChatUserService.selUserByAddr(address);
            if (weChatUserExample==null){
                result.put("code", 3);
                result.put("msg", "用户不存在");
                return result;
            }
            if (weChatUserExample.getOnlyOne()==0){
                result.put("code", 4);
                result.put("msg", "重要信息只允许修改一次");
                return result;
            }
            weChatUserExample.setNickname(nickName);
            weChatUserExample.setPhone(phone);
            weChatUserExample.setOnlyOne(0);
            weChatUserService.updateUser(weChatUserExample);
            SysModule sm = new SysModule();
            sm.setSname("PersonManageAddress");
            List<SysModule> sysModule = sysModuleService.getModuleName(sm);
            if (sysModule.size()<=0){
                result.put("code", 5);
                result.put("msg", "参数错误");
                return result;
            }
            AuthManage  authManage =new AuthManage();
//            authManage.setManageAddr(sysModule.get(0).getAddress());
            authManage.setAddress(address);
            authManage.setDutyAddress(user.getAddress());
            authManage.setStatus(1);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation("修改");
            authManageService.addAuthManage(authManage);
        }catch (Exception e){
            result.put("code", 2);
            result.put("msg", "失败"+e.getMessage());
            return result;
        }
        result.put("code",0);
        result.put("msg","成功");
        return result;
    }

    @ResponseBody
    @RequestMapping(value="getModelList", method = RequestMethod.POST)
    public JSONObject getModelList(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        try{
            String token=WwSystem.ToString(request.getParameter("backLogin"));
            WeChatUserExample user = BackApiLoginContext.getUser(token);
            if (user == null) {
                res.put("code", 101);
                res.put("msg", "已过期请重新登录");
                return res;
            }
            List<HashMap<String,Object>>  list = sysModelMapper.getModelList();
            if (list.size()<=0){
                res.put("code",102);
                res.put("msg","无权访问");
                return res;
            }
            res.put("modelList",list);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",1);
            return res;
        }
        res.put("code",0);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value="companyBaseSet",method=RequestMethod.POST)
    public JSONObject companyBaseSet(HttpServletRequest request,HttpServletResponse response){
        JSONObject result=new JSONObject();
        result.put("success",false);
        String token=WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if(user==null){
            result.put("code", 101);
            result.put("msg", "已过期请重新登录");
            return result;
        }
        String website=WwSystem.ToString(request.getParameter("website"));
        String companyIntroduce = WwSystem.ToString(request.getParameter("companyName"));
        String contactInfo=WwSystem.ToString(request.getParameter("busiLicense"));//营业执照编号
        String email=WwSystem.ToString(request.getParameter("email"));
        String companyPublic=WwSystem.ToString(request.getParameter("companyPublic"));//宣传图
        String headImg=WwSystem.ToString(request.getParameter("headImg"));//企业应用图
        String assetName=WwSystem.ToString(request.getParameter("assetName"));//资产名称
        LOGGER.info("assetNmae"+assetName);
        int assetId = WwSystem.ToInt(request.getParameter("assetId"));
        LOGGER.info("assetId0"+assetId);
        if (assetId<0){
            assetId = 0;
        }
        HomePage homePage =new HomePage();
        Map<String,String> map =  new HashMap<>();
        String params = "";
        if (!StringUtils.isEmpty(headImg)){
            map.put("headImg",headImg);
            params +="&headImg="+headImg;
            homePage.setHeadImg(headImg);
        }
        if (!StringUtils.isEmpty(assetName)){
            map.put("assetName",assetName);
            params +="&assetName="+assetName;
            homePage.setAssetName(assetName);
        }
        homePage.setAssetId(assetId);
        if (!StringUtils.isEmpty(companyIntroduce)){
            map.put("companyIntroduce",companyIntroduce);
            params +="&companyIntroduce="+companyIntroduce;
            homePage.setCompanyIntroduce(companyIntroduce);
        }
        if (!StringUtils.isEmpty(contactInfo)){
            map.put("contactInfo",contactInfo);
            params +="&contactInfo="+contactInfo;
            homePage.setContactInfo(contactInfo);
        }
        if (!StringUtils.isEmpty(website)){
            map.put("website",website);
            params +="&website="+website;
            homePage.setWebsite(website);
        }
        if (!StringUtils.isEmpty(email)){
            map.put("email",email);
            params +="&email="+email;
            homePage.setEmail(email);
        }
        if (!StringUtils.isEmpty(companyPublic)){
            map.put("companyPublic",companyPublic);
            params +="&companyPublic="+companyPublic;
            homePage.setCompanyPublic(companyPublic);
        }

        SysModule sm = new SysModule();
        sm.setSname("PersonManageAddress");
        List<SysModule> sysModule = sysModuleService.getModuleName(sm);
        if (sysModule.size()<=0){
            result.put("code", 5);
            result.put("msg", "参数错误");
            return result;
        }
        /*AuthManage  authManage =new AuthManage();
        authManage.setManageAddr(sysModule.get(0).getAddress());
        authManage.setDutyAddress(user.getAddress());
        authManage.setStatus(1);
        authManage.setIsPayment(0);
        authManage.setOperation("基础设置");
        authManage.setDetailClause(homePage.toString());
        authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));*/
        if (StringUtils.isEmpty(String.valueOf(homePage.getId()))){
            String sign = ApiUtil.getSign(map);
            LOGGER.info("sign："+sign);
            String url = "api/setHome";
            params += "&signCode="+sign;
            String s="";
            if (params.trim().startsWith("&")){
                s = params.replaceFirst("&", "");
            }
            String s1 = ApiUtil.sendPost(url, s);
            LOGGER.info("s1："+s1);
            JSONObject jsonObject = JSON.parseObject(s1);
            String ss = JSONObject.toJSONString(jsonObject.get("status"));
            if (ss.equals("true")){
                int i = homePageService.insertHomePage(homePage);
                if (i<0){
                    result.put("code",2);
                    result.put("msg","新增失败");
                    return result;
                }
            } else {
                result.put("code",1);
                result.put("msg",jsonObject.get("message"));
                return result;
            }
        }else {
            String sign = ApiUtil.getSign(map);
            LOGGER.info("sign："+sign);
            String url = "api/updateHome";
            params += "&signCode="+sign;
            String s="";
            if (params.trim().startsWith("&")){
                s = params.replaceFirst("&", "");
            }
            String s1 = ApiUtil.sendPost(url, s);
            LOGGER.info("s1："+s1);
            JSONObject jsonObject = JSON.parseObject(s1);
            String ss = JSONObject.toJSONString(jsonObject.get("status"));
            if (ss.equals("true")){
                int i = homePageService.updateHomePage(homePage);
                if (i<0){
                    result.put("code",2);
                    result.put("msg","修改失败");
                    return result;
                }
            }else {
                result.put("code",1);
                result.put("msg",jsonObject.get("message"));
                return result;
            }
        }
        /*int i = authManageService.addAuthManage(authManage);
        if (i<0){
            result.put("code",6);
            result.put("msg","记录失败");
            return result;
        }*/
        result.put("success", true);
        result.put("code", 0);
        result.put("msg", "成功");

        return result;
    }

    @RequestMapping(value = "checkAuthManage", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject  checkAuthManage(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        String address = WwSystem.ToString(request.getParameter("address"));
        List<SysManage> list = null;
        try{
            AuthManage authManage = new AuthManage();
            authManage.setDutyAddress(user.getAddress());
            list = sysManageMapper.checkManager(address);
            if (StringUtils.isEmpty(address) && list.size()<=0){
                SysModule sm = new SysModule();
                sm.setSname("PersonManageAddress");
                List<SysModule> sysModule = sysModuleService.getModuleName(sm);
                address=sysModule.get(0).getAddress();
                authManage.setSpecialValid(1);//第一个进入系统，并确认的人拥有特殊权限，即类似超管的权限
            }else {
                authManage.setSpecialValid(0);
            }
            authManage.setManageAddr(address);
            authManage.setStatus(0);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation("添加管理员");
            authManageService.addAuthManage(authManage);
            if(authManage.getMid()<0){
                res.put("code",1);
                res.put("msg","新增失败");
                return res;
            }
            NrcMain nm = new NrcMain();
            nm.initRPC();
            String msg="id:"+authManage.getMid()+"*work:0";
            String signMessage = nm.signMessage(address,msg);
            LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                res.put("code",2);
                res.put("msg","签名失败");
                return res;
            }
            String message = "https://reitschain.com/code/workCard?s="+signMessage+"&o="+msg+"&a="+address;
            res.put("msg",message);
        }catch (Exception e){
//            e.printStackTrace();
            res.put("code",3);
            res.put("msg","失败"+e.getMessage());
            return res;
        }
        res.put("code", 0);
        res.put("success",true);
        res.put("msg", "成功");
        return res;
    }


}
