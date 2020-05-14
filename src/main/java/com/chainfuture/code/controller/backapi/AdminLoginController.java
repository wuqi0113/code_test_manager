package com.chainfuture.code.controller.backapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.FileUploadUtil;
import com.chainfuture.code.common.Md5Encrypt;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.filter.BackApiLoginContext;
import com.chainfuture.code.filter.OpenApiLoginContext;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.mapper.SysModelMapper;
import com.chainfuture.code.service.*;
import com.chainfuture.code.servlet.AccessVerify;
import com.chainfuture.code.utils.ApiUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/backapi/admin/")
public class AdminLoginController {

    @Autowired
    private SysManageMapper sysManageMapper;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private SysModelMapper sysModelMapper;
    @Autowired
    private HomePageService homePageService;
    @Autowired
    private AuthManageService authManageService;
    @Autowired
    private WeChatUserService weChatUserService;
    public static final Logger LOGGER = Logger.getLogger(AdminLoginController.class);

    @Autowired
    private FxUserService fxUserService;

    @ResponseBody
    @RequestMapping(value="fxLogin")
    public JSONObject fxLogin(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        try{
            String invite = WwSystem.ToString(request.getParameter("invite"));
            AccessVerify av = new AccessVerify();
            JSONObject verify = av.verify(request);
            if (verify.get("success").toString().equals("false")){
                res.put("code",101);
                res.put("msg","登陆验证失敗");
                return res;
            }
            Object result = verify.get("data");
            FxUser user = fxUserService.selUserByAddr(result.toString());
            FxUser uu = new FxUser();
            if (user==null){
                Map<String, String> map1 =new HashMap<>(0);
                map1.put("openid",result.toString());
                String sign1 = ApiUtil.getSign(map1);
                String url = "api/getUserMessage";
                String params="signCode="+sign1;
                params += "&openid="+result.toString();
                String ss = ApiUtil.sendPost(url, params);
                JSONObject jsonObject = JSONObject.parseObject(ss);
                if (jsonObject.get("status").toString().equals("false")) {
                    res.put("code",2);
                    res.put("msg","连接失败");
                    return res;
                }
                String s = JSONObject.toJSONString(jsonObject.get("result"));
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                uu.setUserName(JSONObject.toJSONString(jsonObject1.get("nickname")));
                uu.setAddress(JSONObject.toJSONString(jsonObject1.get("address")));
                uu.setHeadImage(JSONObject.toJSONString(jsonObject1.get("headimgurl")));
                uu.setInviteCode(invite);
                fxUserService.insertUser(uu);
                if (uu.getId()==null){
                    LOGGER.info("更新数据失败");
                }
            }
            request.getSession().setAttribute("admin",uu.getId());
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失敗"+e.getMessage());
            return  res;
        }
        res.put("success",true);
        res.put("msg", "登录成功");
        res.put("code",0);
        return  res;
    }


    @ResponseBody
    @RequestMapping(value="adminLogin")
    public JSONObject adminLogin(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        try{
            String code = WwSystem.ToString(request.getParameter("code"));
            AccessVerify av = new AccessVerify();
            JSONObject verify = av.verify(request);
            if (verify.get("success").toString().equals("false")){
                res.put("code",101);
                res.put("msg","登陆验证失敗");
                return res;
            }
            Object result = verify.get("data");
            WeChatUserExample weChatUser = weChatUserService.selUserByAddr(result.toString());
            WeChatUserExample weChatUser1 = null;
            if (weChatUser==null){
                Map<String, String> map1 =new HashMap<>(0);
                map1.put("openid",result.toString());
                String sign1 = ApiUtil.getSign(map1);
                System.out.println("sign1："+sign1);
                String url = "api/getUserMessage";
                String params="signCode="+sign1;
                params += "&openid="+result.toString();

                String ss = ApiUtil.sendPost(url, params);
                JSONObject jsonObject = JSONObject.parseObject(ss);
                if (jsonObject.get("status").toString().equals("false")) {
                    res.put("code",2);
                    res.put("msg","连接失败");
                    return res;
                }
                String s = JSONObject.toJSONString(jsonObject.get("result"));
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                weChatUser1 = JSON.toJavaObject(jsonObject1, WeChatUserExample.class);
                weChatUser1.setOnlyOne(1);
                weChatUserService.insertUser(weChatUser1);
                if (weChatUser1.getId()==null){
                    LOGGER.info("更新数据失败");
                }
            }
            AuthManage  authManage  =new AuthManage();
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation("进入系统");
            String loginToken = "";
            if (weChatUser==null){
                loginToken= Md5Encrypt.md5(weChatUser1.getAddress()+(new Date()).getTime());
                authManage.setDutyAddress(weChatUser1.getAddress());
                BackApiLoginContext.setToken(weChatUser1, loginToken);
                res.put("backLogin", loginToken);
                res.put("data",weChatUser1);
            }else {
                loginToken= Md5Encrypt.md5(weChatUser.getAddress()+(new Date()).getTime());
                authManage.setDutyAddress(weChatUser.getAddress());
                BackApiLoginContext.setToken(weChatUser, loginToken);
                res.put("backLogin", loginToken);
                res.put("data",weChatUser);
            }
            int i = authManageService.addAuthManage(authManage);
            if (i<0){
                res.put("code",3);
                res.put("msg","记录失败");
                return res;
            }
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失敗"+e.getMessage());
            return  res;
        }
        res.put("success",true);
        res.put("msg", "登录成功");
        res.put("code",0);
        return  res;
    }

    @ResponseBody
    @RequestMapping(value="getPrimeAddr")
    public JSONObject getPrimeAddr(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        NrcMain nm = new NrcMain();
        try{
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            if (StringUtils.isEmpty(primeaddr)){
                res.put("msg","连接错误");
                res.put("code",1);
                return res;
            }
            res.put("primeAddr",primeaddr);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",1);
            return res;
        }
        res.put("code",0);
        res.put("msg","成功");
        return res;
    }



    @RequestMapping("checkAuthManage")
    @ResponseBody
    public JSONObject  checkAuthManage(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success",false);
        String address = WwSystem.ToString(request.getParameter("address"));
        List<SysManage> list = null;
        try{
            AuthManage authManage = new AuthManage();
            list = sysManageMapper.checkManager(address);
            if (list.size()<=0){
                res.put("code", 1);
                res.put("msg", "管理员不存在，请先添加");
                return res;
            }
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



    @ResponseBody
    @RequestMapping(value="personListByModule",method=RequestMethod.POST)
    public JSONObject personListByModule(HttpServletRequest request,HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("success", false);
        String address = WwSystem.ToString(request.getParameter("address"));
        String moduleAddress = WwSystem.ToString(request.getParameter("moduleAddress"));
        if (StringUtils.isEmpty(moduleAddress)) {
            result.put("code", 1);
            result.put("msg", "参数有误");
            return result;
        }
        try{
            AuthManageExample  authManageExample  = new AuthManageExample();
            authManageExample.setDutyAddress(address);
            authManageExample.setManageAddr(moduleAddress);
            List<HashMap<String,Object>> list = authManageService.getRecordListByModule(authManageExample);
            if (list.size()<=0){
                result.put("code", 2);
                result.put("msg", "无记录");
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
}
