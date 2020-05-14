package com.chainfuture.code.controller;


import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.SysManage;
import com.chainfuture.code.bean.SysUser;
import com.chainfuture.code.common.MD5;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysManageMapper sysManageMapper;


    //@RequestMapping("/login")
    public String Login(HttpServletRequest request, HttpServletResponse response){ return "page/main";}
    @RequestMapping("/adminList")
    public String adminList(HttpServletRequest request, HttpServletResponse response){ return "see/admin/adminList";}
    @RequestMapping("/adminList1")
    public ModelAndView adminList1(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("see/admin/adminList");
        return mv;
    }

    @RequestMapping("/dologin")
    public ModelAndView loginin(HttpServletRequest request, HttpServletResponse response){
        ModelAndView  mv = new ModelAndView();
        mv.setViewName("redirect:/authmanage/toUserList");
        String username= WwSystem.ToString(request.getParameter("username"));
        String passwd=WwSystem.ToString(request.getParameter("passwd"));
        String vcode=WwSystem.ToString(request.getParameter("validateCode"));
        String password = MD5.md5(passwd);
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(passwd)||StringUtils.isEmpty(vcode)){
            mv.setViewName("redirect:/index.jsp");
            mv.addObject("msg", "不能为空！");
            return mv;
        }
        String validateCode = request.getSession().getAttribute("session_validatecode").toString().toLowerCase();
        if (!validateCode.equals(vcode)){
            mv.addObject("msg","验证码不正确");
            mv.setViewName("redirect:/index.jsp");
            return mv;
        }
        SysUser user=sysUserService.getByUserName(username);
        if (user==null||!user.getPasswd().equals(password)){
            mv.addObject("msg", "用户不存在或密码错误！");
            mv.setViewName("redirect:/index.jsp");
            return mv;
        }
        String systime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        request.getSession().setAttribute("admin",username);//MD5.md5(username+(int)(Math.random()*100)+systime)

        user.setLastLoginTime(systime);

        sysUserService.updateSysUserName(user);
        return mv;
    }

    @RequestMapping("checkMainAddressManage")
    @ResponseBody
    public JSONObject  checkMainAddressManage(HttpServletRequest request,HttpServletResponse response) {
        JSONObject  res = new JSONObject();
        res.put("code",0);
        try{
//            NrcMain nm = new NrcMain();
//            nm.initRPC();
//            String primeaddr = nm.getPrimeaddr();
            //            res.put("primeaddr",primeaddr);
            res.put("primeaddr","1KxXvMYjhhrr8KGRF4LxgtrKhrMHREui5n");
            SysManage sysManage = sysManageMapper.selSysManageByManageAddr("1KxXvMYjhhrr8KGRF4LxgtrKhrMHREui5n");
            if (sysManage==null){
                res.put("code",1);
                return res;
            }
        }catch (Exception e){
            res.put("code",1);
        }
        return res;
    }

    @RequestMapping(value="/loginOut")
    public ModelAndView login_out(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv=new ModelAndView();

        request.getSession().removeAttribute("admin");
        mv.addObject("message", "已退出！");
        mv.setViewName("redirect:/index.jsp");
        return mv;
    }
}
