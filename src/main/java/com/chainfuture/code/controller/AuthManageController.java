package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.AuthManage;
import com.chainfuture.code.bean.SysManage;
import com.chainfuture.code.bean.SysManageExample;
import com.chainfuture.code.bean.WeChatUser;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.service.AuthManageService;
import com.chainfuture.code.utils.ApiUtil;
import com.chainfuture.code.utils.ObjectUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

@Controller
@RequestMapping("/authmanage")
public class AuthManageController {

    @Autowired
    private AuthManageService authManageService;
    @Autowired
    private SysManageMapper sysManageMapper;
    public static final Logger LOGGER = Logger.getLogger(AuthManageController.class);


    @ResponseBody
    @RequestMapping("/toUserList")
    public ModelAndView  toUserList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        try{
            SysManageExample  sysManage1 = new SysManageExample();
            sysManage1.setStatus(1);
            List<SysManage> sysManageList = sysManageMapper.selSysManageList(sysManage1);
            mv.addObject("sysManageList",sysManageList);
        }catch (Exception e){
            e.getMessage();
            mv.addObject("code",1);
            mv.addObject("msg","失败"+e.getMessage());
        }
        mv.setViewName("see/index");
        return mv;
    }
    @ResponseBody
    @RequestMapping("/toManageList")
    public ModelAndView  toManageList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        try{

        }catch (Exception e){
            e.getMessage();
            mv.addObject("code",1);
            mv.addObject("msg","失败"+e.getMessage());
        }
        mv.setViewName("page/manage_list");
        return mv;
    }

    @RequestMapping("/manageList")
    @ResponseBody
    public JSONObject manageList(SysManageExample sysManage, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        List<SysManage> data=null;
        int count=0;
        try{
            data = sysManageMapper.selSysManageList(sysManage);
            res.put("data",data);
            count = sysManageMapper.selSysManageCount();
            res.put("count",count);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }
    @RequestMapping("/userList")
    @ResponseBody
    public JSONObject userList(WeChatUser user, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        int pageIndex = user.getPageIndex();
        int limit = user.getLimit();
        Map<String,String> map =  new HashMap<>();
        Map<String,String> map1 =  new HashMap<>();
        String  address = "1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
        map.put("address",address);
        map1.put("primeAddr",address);
        try{
            String signCount = ApiUtil.getSign(map1);
            System.out.println("signCount："+signCount);
            String urlCount = "api/getMyUserCount";
            String paramCounts="primeAddr="+address;
            paramCounts += "&signCode="+signCount;
            String count = ApiUtil.sendPost(urlCount, paramCounts);
            JSONObject jsonObjectCount = JSON.parseObject(count);
            JSONObject resultCount = (JSONObject) jsonObjectCount.get("result");
            Object userCount = resultCount.get("userCount");
            res.put("count", userCount);

            map.put("pageIndex",String.valueOf(pageIndex));
            map.put("limit",String.valueOf(limit));
            String sign = ApiUtil.getSign(map);
            System.out.println(sign);
            String url = "api/getMyAllUser";
            String params="address="+address;
            params += "&signCode="+sign;
            params += "&pageIndex="+pageIndex;
            params += "&limit="+limit;
            String s1 = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s1);
            Object result = jsonObject.get("result");
            res.put("data",result);
        }catch (Exception e){
            e.getMessage();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }


    @RequestMapping("/authManageList")
    @ResponseBody
    public JSONObject authManageList(AuthManage  authManage, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("status",0);
        res.put("hint","成功");
        List<AuthManage> data=null;
        int count=0;
        try{
            data = authManageService.selAuthManageList(authManage);

            count = authManageService.selAuthManageCount();
        }catch (Exception e){
            e.getMessage();
            res.put("status",1);
            res.put("hint","失败"+e.getMessage());
        }

        res.put("rows", data);
        res.put("total", count);
        return res;
    }
    @RequestMapping("/checkAuthManage")
    @ResponseBody
    public JSONObject  checkAuthManage(String manageAddr,
                                     HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        SysManage sysManage = null;
        try{
            sysManage = sysManageMapper.selSysManageByManageAddr(manageAddr);
            if (!StringUtils.isEmpty(sysManage.getAddress())){
                res.put("code",1);
                res.put("msg","仅支持单个管理员");
            }
        }catch (Exception e){
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }

    @RequestMapping("/checkManager/{address}")
    @ResponseBody
    public JSONObject  checkManager(@PathVariable("address")String address, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("code", 0);res.put("msg", "成功");
        SysManage sysManage = null;
        try{
            sysManage = sysManageMapper.selSysManageByManageAddr(address);
            if (sysManage==null) {
                res.put("code", 1);
                res.put("msg", "请先给管理地址添加管理员");
                return res;
            }
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }
    @RequestMapping("/addAuthManage1")
    @ResponseBody
    public JSONObject  addAuthManage1(String extend,String address,String operation,String manageAddr,Integer isPayment,String openId,String primeaddr,
            HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        String message="";
        String signMessage = "";
        SysManage sysManage = null;
        String msg = "";
        try{
            NrcMain nm = new NrcMain();
            nm.initRPC();
              /*SysManage addSysManage = new SysManage();
                addSysManage.setStatus(0);
                addSysManage.setAddress(address);
                addSysManage.setManageAddr(manageAddr);
                sysManageMapper.addSysManage(addSysManage);*/

            Map<String,String> map =  new HashMap<>();
            AuthManage  authManage = new AuthManage();
            if (address!=null&&!address.equals("")){
                map.put("openid",address);
                String sign = ApiUtil.getSign(map);
                System.out.println("sign："+sign);
                String url = "api/getUserMessage";
                String params="signCode="+sign;
                params += "&openid="+address;
                String s1 = ApiUtil.sendPost(url, params);
                JSONObject jsonObject = JSON.parseObject(s1);
                JSONObject weChatUser = (JSONObject) jsonObject.get("result");
//                LOGGER.info("weChatUser："+weChatUser);
                authManage.setAddress(weChatUser.getString("address"));
                authManage.setOpenId(weChatUser.getString("openid"));
            }else {
                sysManage = sysManageMapper.selSysManageByManageAddr(primeaddr);
                if (sysManage.getAddress()==null||sysManage.getAddress()==""){
                    res.put("code",1);
                    res.put("msg","请先添加管理员");
                    return res;
                }
                authManage.setAddress(sysManage.getAddress());
            }

            authManage.setManageAddr(manageAddr);
            authManage.setStatus(0);
            authManage.setExtend(extend);
            authManage.setIsPayment(isPayment);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation(operation.trim());
            authManageService.addAuthManage(authManage);
            if(authManage.getMid()<0){
                res.put("code",1);
                res.put("msg","新增失败");
                return res;
            }
            msg="id:"+authManage.getMid()+"*work:0";
            signMessage = nm.signMessage(manageAddr,msg);
            LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            message = "https://reitschain.com/code/workCard?s="+signMessage+"&o="+msg+"&a="+manageAddr;
            res.put("msg",message);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("reInvite")
    public JSONObject reInvite(Integer mid,HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        String signMessage = "";
        String message = "";
        String msg = "";
        res.put("id",mid);
        try{
            AuthManage authManage = authManageService.selAuthManageById(mid);
            if (authManage==null){
                res.put("code",1);
                res.put("msg","记录不存在");
            }
            NrcMain nm = new NrcMain();
            nm.initRPC();
            msg+="id:"+String.valueOf(mid)+"*work:0";
            signMessage = nm.signMessage(authManage.getManageAddr(), msg);
            res.put("signMessage",signMessage);
            LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
//            message = "https://reitschain.com/co?s="+signMessage+"&o=id:"+id+"&a="+authManage.getManageAddr();
            message = "https://reitschain.com/code/workCard?s="+signMessage+"&o="+msg+"&a="+authManage.getManageAddr();
            res.put("msg",message);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
            return res;
        }
        return res;
    }

    @RequestMapping("/addAuthManage")
    @ResponseBody
    public JSONObject  addAuthManage(String extend,String address,String operation,String manageAddr,Integer isPayment,String openId,String primeAddr,
                                     HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        String message="";
        String signMessage = "";
        SysManage sysManage = null;
        String msg = "";
        try{
            NrcMain nm = new NrcMain();
            nm.initRPC();
            AuthManage  authManage = new AuthManage();
            if(StringUtils.isEmpty(address)){
                authManage.setAddress(address);
            }
            if (isPayment!=null&&isPayment>0){
                SysManage sysManage1 = sysManageMapper.selSysManageByManageAddr(primeAddr);
                if (sysManage1!=null){
                    authManage.setAddress(sysManage1.getAddress());
                }
            }
            authManage.setManageAddr(manageAddr);
            authManage.setStatus(0);
            authManage.setExtend(extend);
            authManage.setIsPayment(isPayment);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation(operation.trim());
            authManageService.addAuthManage(authManage);
            if(authManage.getMid()<0){
                res.put("code",1);
                res.put("msg","新增失败");
                return res;
            }
            msg="id:"+authManage.getMid()+"*work:0";
            signMessage = nm.signMessage(manageAddr,msg);
            LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            message = "https://reitschain.com/code/workCard?s="+signMessage+"&o="+msg+"&a="+manageAddr;
//            message = "https://reitschain.com/code/workCard?s=null&o=id:"+authManage.getMid()+"&a="+manageAddr;
            res.put("msg",message);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }

}
