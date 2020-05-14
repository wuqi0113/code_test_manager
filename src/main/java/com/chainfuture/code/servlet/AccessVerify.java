package com.chainfuture.code.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.utils.ApiUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class AccessVerify {

    public static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AccessVerify.class);

    public JSONObject verify(HttpServletRequest request) {
        JSONObject res = new JSONObject();
        res.put("success",false);
        String code = WwSystem.ToString(request.getParameter("code"));
        if (code==null||code.isEmpty()){
            return  res;
        }
        Map<String, String> map =new HashMap<>(0);
        map.put("code",code);
        try {
            String sign = ApiUtil.getSign(map);
            System.out.println("sign：" + sign);
            String urlCount = "code/getAddress";
            String paramsCount = "signCode=" + sign;
            paramsCount += "&code=" + code;
            String s = ApiUtil.sendPost(urlCount, paramsCount);
            if (StringUtils.isEmpty(s)) {
                return res;
            }
            JSONObject jsonObjectCount = JSON.parseObject(s);
            if (jsonObjectCount.get("status").toString().equals("false")) {
                return res;
            }
            res.put("data",jsonObjectCount.get("result"));
        }catch (Exception e){
            return res;
        }
        res.put("success",true);
        return res;
    }

}

