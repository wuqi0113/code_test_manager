package com.chainfuture.code.filter;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.mapper.SysCarteMapper;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.service.AuthManageService;
import com.chainfuture.code.service.SysManageService;
import com.chainfuture.code.service.SysModuleService;
import com.chainfuture.code.service.WeChatUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackApiValid {

    @Autowired
    private  SysManageMapper sysManageMapper;
    @Autowired
    private SysManageService sysManageService;
    @Autowired
    private AuthManageService authManageService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private SysCarteMapper  sysCarteMapper;
    @Autowired
    private WeChatUserService weChatUserService;
    public static final Logger LOGGER = Logger.getLogger(BackApiValid.class);

    public  boolean  Valid(String token,String uri,HttpServletRequest request, HttpServletResponse response){

        return true;
    }
}
