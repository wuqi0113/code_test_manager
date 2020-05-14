package com.chainfuture.code.controller.openapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.common.MD5;
import com.chainfuture.code.common.Md5Encrypt;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.filter.OpenApiLoginContext;
import com.chainfuture.code.mapper.WorkapiRecordMapper;
import com.chainfuture.code.service.HomePageService;
import com.chainfuture.code.service.InuPriceService;
import com.chainfuture.code.service.InuSetService;
import com.chainfuture.code.service.WeChatUserService;
import com.chainfuture.code.servlet.AccessVerify;
import com.chainfuture.code.utils.ApiUtil;
import com.chainfuture.code.utils.BigDecimalMaxPrecision;
import com.chainfuture.code.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.apache.commons.codec.binary.Base64;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/openapi/novalid/")
public class NoValidController {

    @Autowired
    private WeChatUserService weChatUserService;
    @Autowired
    private InuPriceService inuPriceService;
    @Autowired
    private WorkapiRecordMapper workapiRecordMapper;
    @Autowired
    private InuSetService inuSetService;
    @Autowired
    private HomePageService  homePageService;
    public static final Logger LOGGER = Logger.getLogger(NoValidController.class);


    @ResponseBody
    @RequestMapping(value = "getExtensionRecord",method = RequestMethod.POST)
    public JSONObject  getExtensionRecord(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success",false);
        try{
            List<Object> tt = new ArrayList<>();
            List<WeChatUserExample>  user = weChatUserService.getWeChatAll();
            for (int j=0;j<user.size();j++){
                HashMap<String,Object>  totalMap = new HashMap<>();
                //推广记录
                List<WorkapiRecord> extensionList = workapiRecordMapper.selExtensionList(user.get(j).getAddress());
                if (extensionList.size()<=0){
                    continue;
                }
                int profitCount = 0;
                int profitSum = 0;
                for (int i=0; i<extensionList.size();i++){
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("purchaser",user.get(j).getAddress());
                    map.put("extensionTime",extensionList.get(i).getRecordTime());
                    List<WorkapiRecord> maxProfitSection = workapiRecordMapper.selMaxProfitSection(map);
                    if (maxProfitSection.size()<=0){
                        continue;
                    }else {
                        ++profitCount;
                    }
                    InuSetExample inuSet =inuSetService.selInuSet(maxProfitSection.get(0).getYear());
                    if (inuSet==null){
                        continue;
                    }
                    profitSum = profitSum+extensionList.get(i).getYear()*inuSet.getRewardNum();

                }
                if (profitCount>=1){
                    totalMap.put("nickname",user.get(j).getNickname());
                    totalMap.put("address",user.get(j).getAddress());
                    totalMap.put("profitCount",profitCount);
                    totalMap.put("profitSum",profitSum);
                }
                if (totalMap.size()>0){
                    tt.add(totalMap);
                }
            }
            res.put("data",tt);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",2);
            return res;
        }
        res.put("msg","成功");
        res.put("success",true);
        res.put("code",200);
        return res;
    }

    @ResponseBody
    @RequestMapping("insAdd")
    public JSONObject  insAdd(HttpServletRequest request,HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String nickName = WwSystem.ToString(request.getParameter("nickName"));

        try{
            WeChatUserExample weChatUser1 = new WeChatUserExample();
//            nickName = Base64.encodeBase64String(nickName.getBytes("utf-8"));
            weChatUser1.setNickname(nickName);
            weChatUserService.insertUser(weChatUser1);
            if (weChatUser1.getId()==null){
                LOGGER.info("更新数据失败");
            }
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",2);
            return res;
        }
        res.put("success",true);
        res.put("code",200);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "getEffectiveInsurance")
    public JSONObject  getEffectiveInsurance(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success",false);
        try{
            List<WorkapiRecord>  workapiRecord = workapiRecordMapper.getEffectiveInsurance("1BCJghungwEzott8VHLuixrnkiTDMVpQTg");
            LOGGER.info("workapiRecord"+workapiRecord);
            if (workapiRecord==null){
                res.put("msg", "无记录");
                res.put("code",1);
                return res;
            }
            for(int i=0;i<workapiRecord.size();i++){
                String busiJson = workapiRecord.get(i).getBusiJson();
                JSONObject jsonObject = JSONObject.parseObject(busiJson);
                if (jsonObject.size()<=2){
                    continue;
                }
                String userInput = jsonObject.get("userInput").toString();
                JSONArray objects = JSONObject.parseArray(userInput);
                for (int j=0;j<objects.size();j++){
                    JSONObject o = (JSONObject) objects.get(j);
                    if (o.get("name").equals("name")){
                        workapiRecord.get(i).setRealName(o.get("value").toString());
                    }
                    if(o.get("name").equals("idNum")) {
                        workapiRecord.get(i).setIdNum(o.get("value").toString());
                    }
                }
            }
            res.put("data",workapiRecord);
        }catch (Exception e){
            res.put("msg","失败"+e.getMessage());
            res.put("code",2);
            return res;
        }
        res.put("msg","成功");
        res.put("success",true);
        res.put("code",200);
        return res;
    }

    @ResponseBody
    @RequestMapping(value="sweepCodeLogin")
    public JSONObject sweepCodeLogin(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        try{
            AccessVerify av = new AccessVerify();
            JSONObject verify = av.verify(request);
            if (verify.get("success").toString().equals("false")){
                res.put("success",false);
                res.put("code",101);
                res.put("msg","登陆验证失敗");
                return res;
            }
            Object result = verify.get("data");
            WeChatUserExample weChatUser = weChatUserService.selUserByAddr(result.toString());
            LOGGER.info("WeChat:"+weChatUser);
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
                    res.put("success",false);
                    res.put("code",2);
                    res.put("msg","连接失败");
                    return res;
                }
                String s = JSONObject.toJSONString(jsonObject.get("result"));
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                weChatUser1 = JSON.toJavaObject(jsonObject1, WeChatUserExample.class);
                weChatUser1.setOnlyOne(1);
                LOGGER.info("weChatUser1:"+weChatUser1);
                weChatUserService.insertUser(weChatUser1);
                if (weChatUser1.getId()==null){
                    LOGGER.info("更新数据失败");
                }
            }
            String loginToken = "";
            if (weChatUser==null){
                loginToken= Md5Encrypt.md5(weChatUser1.getAddress()+(new Date()).getTime());
                OpenApiLoginContext.setToken(weChatUser1, loginToken);
                LOGGER.info("注册进这里："+weChatUser1+"loginToken："+loginToken);
                res.put("token", loginToken);
                res.put("data",weChatUser1);
            }else {
                loginToken= Md5Encrypt.md5(weChatUser.getAddress()+(new Date()).getTime());
                OpenApiLoginContext.setToken(weChatUser, loginToken);
                LOGGER.info("登陆进这里："+weChatUser+"loginToken："+loginToken);
                res.put("token", loginToken);
                res.put("data",weChatUser);
            }
        }catch (Exception e){
            e.printStackTrace();
            res.put("success",false);
            res.put("code",1);
            res.put("msg","失敗");
            return  res;
        }
        res.put("success",true);
        res.put("message", "登录成功");
        res.put("code",200);
        return  res;
    }

    @ResponseBody
    @RequestMapping("getPublicNumImg")
    public JSONObject  getPublicNumImg(HttpServletRequest request,HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        try{
            HomePage  homePage = homePageService.getHomePage();
            if (homePage==null){
                res.put("msg","获取数据失败");
                res.put("code",1);
                return res;
            }
            res.put("data",homePage.getCompanyPublic());
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",2);
            return res;
        }
        res.put("success",true);
        res.put("code",200);
        return res;
    }

    @ResponseBody
    @RequestMapping("getInuPrice")
    public JSONObject  getInuPrice(HttpServletRequest request,HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success",false);
        try{
            String recordTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            InuPriceExample  inuPriceExample  = inuPriceService.selPrice();
            if (inuPriceExample==null){
                res.put("msg","获取数据失败");
                res.put("code",1);
                return res;
            }
            res.put("price",inuPriceExample.getPrice());
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",2);
            return res;
        }
        res.put("success",true);
        res.put("code",200);
        return res;
    }
    @ResponseBody
    @RequestMapping("getInuInfoByType")
    public JSONObject  getInuInfoByType(HttpServletRequest request,HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
             int insuranceType = WwSystem.ToInt(request.getParameter("type"));
        if (insuranceType<=0){
            res.put("msg","参数有误");
            res.put("code",1);
            return res;
        }
        try{
            List<InuSetExample> list = inuSetService.getInuSetList(insuranceType);
            if(list.size()<=0){
                res.put("msg","没有数据");
                res.put("code",2);
                return res;
            }
            res.put("data",list);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",3);
            return res;
        }
        res.put("success",true);
        res.put("code",200);
        return res;
    }



    /*public static  void  main(String[] args){
        BigDecimal bigDecimal = new BigDecimal("1.222".toString());
        System.out.println(bigDecimal);
        int doublePrecision = getDoublePrecision(bigDecimal);
        System.out.println(doublePrecision);
    }

    *//**
     * 判断输入的值value的小数点数
     * @param value
     * @return
     *//*
    public static int getDoublePrecision(BigDecimal bigDecimal) {
        String valueStr = bigDecimal.toString();
        int indexOf = valueStr.indexOf(".");
        int doublePrecision = 0;
        if (indexOf > 0) {
            doublePrecision = valueStr.length()  -indexOf;
        }
        return doublePrecision;
    }*/



    @ResponseBody
    @RequestMapping("mapTest")
    public  JSONObject   mapTest(HttpServletRequest request,HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        try{
            HashMap<String,String> hashMap = new HashMap<>();
            WorkapiRecord  workapiRecord  = workapiRecordMapper.selWorkapiRecordById(1);
            if (workapiRecord==null){
                res.put("success", false);
                res.put("msg", "workapiRecord is not exist");
                return res;
            }
//            res.put("workapiRecord",workapiRecord);
            List<HashMap<String,String>> list = workapiRecordMapper.selFiledComment();
            if (list.size()<=0){
                res.put("success", false);
                res.put("msg", "workapiRecord description is not exist");
                return res;
            }
//            res.put("data",list);

            for (int i=0;i<list.size();i++){
                String column_name = list.get(i).get("COLUMN_NAME");
                String column_comment = list.get(i).get("COLUMN_COMMENT");
                if (column_name.equals("type")){
                    int type = workapiRecord.getType();
                    if (type>0){
                        hashMap.put(column_comment,type+"");
                    }
                }
                if (column_name.equals("year")){
                    int year = workapiRecord.getYear();
                    if (year>0){
                        hashMap.put(column_comment,workapiRecord.getRecordTime()+"~"+DateUtil.getNextYear(DateUtil.strToDateLong(workapiRecord.getRecordTime()),2));
                    }
                }
                if (column_name.equals("amount")){
                    int amount = workapiRecord.getAmount();
                    if (amount>0){
                        hashMap.put(column_comment,amount+"");
                    }
                }
                if (column_name.equals("payNun")){
                    String payNum = workapiRecord.getPayNum();
                    if (StringUtils.isEmpty(payNum)){
                        hashMap.put(column_comment,payNum);
                    }
                }
            }
            res.put("hashMap",hashMap);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败");
        }
        return res;
    }



    public static  void  main(String[] args){

        String nextYear = DateUtil.getNextYear(new Date(),10);
        System.out.println(nextYear);


        Map<String,String> map = new HashMap<>();
       map.put("更新","武琦");
       map.put("安算法","武琦");
       map.put("是否","武琦");
       map.put("让他","武琦");
        String result = "";
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        // 构造签名键值对的格式
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> item : infoIds) {
            if (item.getKey() != null || item.getKey() != "") {
                String key = item.getKey();
                String val = item.getValue();
                if (!(val == "" || val == null)) {
                        sb.append(key + ":" + val + ":");
                    LOGGER.info(key + ":" + val + ";");
                }
            }
        }
        result = sb.toString();
        System.out.println(result);

    }


}
