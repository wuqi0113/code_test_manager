package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.CustRoles;
import com.chainfuture.code.mapper.CustRolesMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("custroles/")
public class CustRolesController {

    @Autowired
    private CustRolesMapper custRolesMapper;

    @RequestMapping("getCustRolesList")
    @ResponseBody
    public JSONObject getCustRolesList(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res =new JSONObject();
        res.put("success",true);
        res.put("code",0);
        res.put("msg","成功");
        List<CustRoles> custRolesList = custRolesMapper.getCustRolesList();
        if (custRolesList.size()<0){
            res.put("success",false);
            res.put("code",1);
            res.put("msg","失败");
        }
        res.put("data",custRolesList);
        return res;
    }

}
