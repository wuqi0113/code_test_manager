package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.Medal;
import com.chainfuture.code.bean.UserMedal;
import com.chainfuture.code.bean.WeChatUser;
import com.chainfuture.code.service.MedalService;
import com.chainfuture.code.service.UserMedalService;
import com.chainfuture.code.service.WeChatUserService;
import com.chainfuture.code.utils.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechatuser")
public class WeChatUserController {


    @Autowired
    private WeChatUserService weChatUserService;
    @Autowired
    private MedalService medalService;
    @Autowired
    private UserMedalService userMedalService;

    @RequestMapping("/toModuleList")
    @ResponseBody
    public ModelAndView toModuleList(WeChatUser medal,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
        mv.setViewName("see/admin/moduleList");
        return mv;
    }

    @RequestMapping("/toUserList")
    @ResponseBody
    public ModelAndView toUserList(WeChatUser medal,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        int userCount=0;
        int sumCoin=0;
        try{
            userCount= weChatUserService.queryCount();
            sumCoin= weChatUserService.selectCoin();
        }catch (Exception e){
            e.getMessage();
            mv.addObject("code",1);
            mv.addObject("msg","失败"+e.getMessage());
        }
        mv.addObject("userCount",userCount);
        mv.addObject("sumCoin",sumCoin);
        mv.setViewName("view/user-manage");
        return mv;
    }

    @RequestMapping("/listUser")
    @ResponseBody
    public JSONObject listUser(WeChatUser model, HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<WeChatUser> list =null;
        List<Medal> medalData=null;
        Integer total =0;
        Map<String, Object> map = new HashMap<>();
        try{
            list = weChatUserService.listWeChatUser(model);

            total = weChatUserService.queryCount();
        }catch (Exception e){
            e.getMessage();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }

        res.put("data", list);
        res.put("count", total);
        return res;
    }

    @RequestMapping("/medalList")
    @ResponseBody
    public JSONObject medalList( HttpServletRequest request, HttpServletResponse response){
        JSONObject  res =new JSONObject();
        res.put("status",0);
        res.put("hint","成功");
        List<UserMedal> data=null;
        int count=0;
        try{
             data = userMedalService.selectSql();
             count = userMedalService.getMedalCount();
        }catch (Exception e){
            e.getMessage();
            res.put("status",1);
            res.put("hint","失败"+e.getMessage());
        }

        res.put("rows", data);
        res.put("total", count);
        return res;
    }


    @RequestMapping("/getUserInfoByAddrOrOpenId/{openId}")
    @ResponseBody
    public JSONObject getUserInfoByAddrOrOpenId(@PathVariable("openId") String openId, HttpServletRequest request, HttpServletResponse response){
        JSONObject result =new JSONObject();
        result.put("code",0);
        result.put("msg","成功");
        try{
            Map<String,String> map =  new HashMap<>();
            map.put("openid",openId);
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            String url = "api/getUserMessage";
            String params="signCode="+sign;
            params += "&openid="+openId;
            String s1 = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s1);
            Object weChatUser = jsonObject.get("result");
            result.put("weChatUser",weChatUser);
        }catch (Exception e){
            e.getMessage();
            result.put("code",1);
            result.put("msg","失败");
        }
        return result;
    }

    @RequestMapping("/getMedalByUser")
    @ResponseBody
    public JSONObject getMedalByUser( String user,HttpServletRequest request, HttpServletResponse response){
        JSONObject result =new JSONObject();
        result.put("code",0);
        result.put("msg","成功");
        List<Medal> medalData=null;
        try{
            medalData =  medalService.querySql(user);
        }catch (Exception e){
            e.getMessage();
            result.put("code",1);
            result.put("msg","失败");
        }
        result.put("medalData",medalData);
        return result;
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public JSONObject getUserInfo( HttpServletRequest request, HttpServletResponse response){
        JSONObject result =new JSONObject();
        int userCount = weChatUserService.queryCount();
        int sumCoin = weChatUserService.selectCoin();
        result.put("userCount",userCount);
        result.put("sumCoin",sumCoin);
        return result;
    }

}
