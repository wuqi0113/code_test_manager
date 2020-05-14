package com.chainfuture.code.controller.openapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.filter.OpenApiLoginContext;
import com.chainfuture.code.mapper.InuBaseMapper;
import com.chainfuture.code.mapper.WorkapiRecordMapper;
import com.chainfuture.code.service.InuSetService;
import com.chainfuture.code.service.WeChatUserService;
import com.chainfuture.code.utils.BalanceUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/openapi/vipuser/")
public class VipUserController {
    @Autowired
    private WeChatUserService weChatUserService;
    @Autowired
    private InuBaseMapper inuBaseMapper;
    @Autowired
    private InuSetService inuSetService;
    @Autowired
    private WorkapiRecordMapper workapiRecordMapper;
    public static final Logger LOGGER = Logger.getLogger(VipUserController.class);

    @ResponseBody
    @RequestMapping(value="openApiTest")
    public JSONObject openApiTest(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        String address = WwSystem.ToString(request.getParameter("address"));
        res.put("address",address);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "sweepPayCallBack",method = RequestMethod.POST)
    public JSONObject  sweepPayCallBack(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("token"));
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        String time = WwSystem.ToString(request.getParameter("time"));
        LOGGER.info("time:"+time);
        if (StringUtils.isEmpty(time)){
            res.put("msg", "参数有误");
            res.put("code",1);
            return res;
        }
        try{
            WorkapiRecord  wr = new WorkapiRecord();
//            String formateTime = DateUtil.stampToDate(time);
            wr.setRecordTime(time);
            wr.setPurchaser(user.getAddress());
           WorkapiRecord  workapiRecord = workapiRecordMapper.selWorkapiRecordByTimeAndAddr(wr);
            if (workapiRecord==null){
                res.put("message", "支付失败");
                res.put("code",2);
                res.put("success", false);
                return res;
            }
            if (workapiRecord.getStatus()!=1){
                res.put("message", "支付失败");
                res.put("code",2);
                res.put("success", false);
                return res;
            }
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",3);
            return res;
        }
        res.put("msg","已完成支付");
        res.put("code",200);
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "selProfit",method = RequestMethod.POST)
    public JSONObject  selProfit(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("token"));
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        try{
            HashMap<String,Object>  totalMap = new HashMap<>();
            List<WorkapiRecord>  yearList = workapiRecordMapper.getYearSection(user.getAddress());
            for (int i=0; i<yearList.size();i++){
                HashMap<String,String> hashMap  =new HashMap<>();
                if (i==0){
                    hashMap.put("startTime",yearList.get(i).getRecordTime());
                }else {
                    if (i==yearList.size()-1){
                        break;
                    }
                    hashMap.put("startTime",yearList.get(i+1).getRecordTime());
                    hashMap.put("endTime",yearList.get(i).getRecordTime());
                }
                hashMap.put("receiver",user.getAddress());
                List<WorkapiRecord> wr = workapiRecordMapper.selWorkapiRecordSectionMax(hashMap);
                if (wr.size()<=0){
                    continue;
                }
                LOGGER.info("wrmax:"+wr);
                InuSetExample inuSet =inuSetService.selInuSet(wr.get(0).getYear());
                if (inuSet==null){
                    continue;
                }
                LOGGER.info("inuSet:"+inuSet);
                HashMap<String,Object> map = new HashMap<>();
                map.put("purchaser",user.getAddress());
                if (i==0){
                    map.put("startTime",yearList.get(i).getRecordTime());
                }else {
                    map.put("startTime",yearList.get(i+1).getRecordTime());
                    map.put("endTime",yearList.get(i).getRecordTime());
                }
                map.put("profitAmount",inuSet.getRewardNum());
                HashMap<String,Object>  workapiRecord = workapiRecordMapper.selProfitSection(map);
                if (Integer.parseInt(workapiRecord.get("profitCount").toString())<=0){
                    continue;
                }
                if (totalMap.size()<=0){
                    totalMap.put("profitCount",workapiRecord.get("profitCount"));
                    totalMap.put("profitSum",workapiRecord.get("profitSum"));
                }else {
                    totalMap.put("profitCount",Integer.parseInt(workapiRecord.get("profitCount").toString())+Integer.parseInt(totalMap.get("profitCount").toString()));
                    totalMap.put("profitSum",Integer.parseInt(workapiRecord.get("profitSum").toString())+Integer.parseInt(totalMap.get("profitSum").toString()));
                }
            }
            if (totalMap.size()<=0){
                res.put("msg", "无记录");
                res.put("code",1);
                return res;
            }
            res.put("data",totalMap);



            /*HashMap<String,Object>  totalMap = new HashMap<>();
            List<WorkapiRecord>  yearList = workapiRecordMapper.getYearSection(user.getAddress());
            for (int i=0; i<yearList.size();i++){
                HashMap<String,String> hashMap  =new HashMap<>();
                if (i==0){
                    hashMap.put("startTime",yearList.get(i).getRecordTime());
                }else {
                    if (i==(yearList.size()-1)){
                        break;
                    }
                    hashMap.put("startTime",yearList.get(i+1).getRecordTime());
                    hashMap.put("endTime",yearList.get(i).getRecordTime());
                }
                LOGGER.info("年份区间："+yearList.get(i).getRecordTime()+"~"+yearList.get(i+1).getRecordTime());
                hashMap.put("receiver",user.getAddress());
                List<WorkapiRecord> wr = workapiRecordMapper.selWorkapiRecordSectionMax(hashMap);
                if (wr.size()<=0){
                    continue;
                }
                LOGGER.info("wrmax:"+wr);
                InuSetExample inuSet =inuSetService.selInuSet(wr.get(0).getYear());
                if (inuSet==null){
                    continue;
                }
                LOGGER.info("inuSet:"+inuSet);
                HashMap<String,Object> map = new HashMap<>();
                map.put("purchaser",user.getAddress());
                if (i==0){
                    map.put("startTime",yearList.get(i).getRecordTime());
                }else {
                    map.put("startTime",yearList.get(i+1).getRecordTime());
                    map.put("endTime",yearList.get(i).getRecordTime());
                }
                map.put("profitAmount",inuSet.getRewardNum());
                HashMap<String,Object>  workapiRecord = workapiRecordMapper.selProfitSection(map);
                if (Integer.parseInt(workapiRecord.get("profitCount").toString())<=0){
                    continue;
                }
                if (totalMap.size()<=0){
                    totalMap.put("profitCount",workapiRecord.get("profitCount"));
                    totalMap.put("profitSum",workapiRecord.get("profitSum"));
                }else {
                    totalMap.put("profitCount",Integer.parseInt(workapiRecord.get("profitCount").toString())+Integer.parseInt(totalMap.get("profitCount").toString()));
                    totalMap.put("profitSum",Integer.parseInt(workapiRecord.get("profitSum").toString())+Integer.parseInt(totalMap.get("profitSum").toString()));
                }
            }
            if (totalMap.size()<=0){
                res.put("msg", "无记录");
                res.put("code",1);
                return res;
            }*/
            /*List<WorkapiRecord> wr = workapiRecordMapper.selWorkapiRecordMax(user.getAddress());
            LOGGER.info("wrmax:"+wr);

            InuSetExample  inuSetExample = inuSetService.selInuSet(wr.get(0).getYear());

            HashMap<String,Object> map = new HashMap<>();
            map.put("purchaser",user.getAddress());
            map.put("profitAmount",inuSetExample.getRewardNum());
            HashMap<String,Object>  workapiRecord = workapiRecordMapper.selProfit(map);
            LOGGER.info("推广收益"+workapiRecord);*/
            res.put("data",totalMap);
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
    @RequestMapping(value = "getExtensionRecord",method = RequestMethod.POST)
    public JSONObject  getExtensionRecord(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("token"));
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        try{
            List<WorkapiRecord> totalList = new ArrayList<>();
            List<WorkapiRecord>  yearList = workapiRecordMapper.getYearSection(user.getAddress());
            for (int i=0; i<yearList.size();i++){
                HashMap<String,String> hashMap  =new HashMap<>();
                if (i==0){
                    hashMap.put("startTime",yearList.get(i).getRecordTime());
                }else {
                    if (i==yearList.size()-1){
                        break;
                    }
                    hashMap.put("startTime",yearList.get(i+1).getRecordTime());
                    hashMap.put("endTime",yearList.get(i).getRecordTime());
                }
                hashMap.put("receiver",user.getAddress());
                List<WorkapiRecord> wr = workapiRecordMapper.selWorkapiRecordSectionMax(hashMap);
                if (wr.size()<=0){
                    continue;
                }
                LOGGER.info("wrmax:"+wr.get(0).getYear());
                InuSetExample inuSet =inuSetService.selInuSet(wr.get(0).getYear());
                if (inuSet==null){
                    continue;
                }
                LOGGER.info("inuSet:"+inuSet.getRewardNum());
                HashMap<String,Object> map = new HashMap<>();
                map.put("purchaser",user.getAddress());
                if (i==0){
                    map.put("startTime",yearList.get(i).getRecordTime());
                }else {
                    map.put("startTime",yearList.get(i+1).getRecordTime());
                    map.put("endTime",yearList.get(i).getRecordTime());
                }
                map.put("profitAmount",inuSet.getRewardNum());
                List<WorkapiRecord>  workapiRecord = workapiRecordMapper.getExtensionRecordSection(map);
                if (workapiRecord.size()>0){
                    totalList.addAll(workapiRecord);
                }
            }
            if (totalList.size()<=0){
                res.put("msg", "无记录");
                res.put("code",3);
                return res;
            }
            /*List<WorkapiRecord> wr = workapiRecordMapper.selWorkapiRecordMax(user.getAddress());
            if (wr.size()<=0){
                res.put("msg", "无购买记录");
                res.put("code",3);
                return res;
            }

            InuSetExample inuSet =inuSetService.selInuSet(wr.get(0).getYear());
            if (inuSet==null){
                res.put("msg", "保障不存在");
                res.put("code",3);
                return res;
            }
            LOGGER.info("inuSet:"+inuSet);
            HashMap<String,Object> map = new HashMap<>();
            map.put("purchaser",user.getAddress());
            map.put("profitAmount",inuSet.getRewardNum());
            List<WorkapiRecord>  workapiRecord = workapiRecordMapper.getExtensionRecord(map);
            LOGGER.info("推广收益"+workapiRecord);
            if (workapiRecord.size()<=0){
                res.put("msg", "无记录");
                res.put("code",1);
                return res;
            }*/
            res.put("data",totalList);
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
    @RequestMapping(value = "getEffectiveInsurance",method = RequestMethod.POST)
    public JSONObject  getEffectiveInsurance(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("token"));
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        try{
            List<WorkapiRecord>  workapiRecord = workapiRecordMapper.getEffectiveInsurance(user.getAddress());
            LOGGER.info("workapiRecord"+workapiRecord);
            if (workapiRecord==null){
                res.put("msg", "无记录");
                res.put("code",1);
                return res;
            }
            for(int i=0;i<workapiRecord.size();i++){
                String busiJson = workapiRecord.get(i).getBusiJson();
                JSONObject jsonObject = JSONObject.parseObject(busiJson);
                String userInput = jsonObject.get("userInput").toString();
                if (StringUtils.isEmpty(userInput)){
                    break;
                }
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
    @RequestMapping(value = "getUnopenedInsurance",method = RequestMethod.POST)
    public JSONObject  getUnopenedInsurance(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("token"));
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        try{
            List<HashMap<String,String>> workapiRecord = workapiRecordMapper.getUnopenedInsurance(user.getAddress());
            if (workapiRecord==null){
                res.put("msg", "无记录");
                res.put("code",1);
                return res;
            }
            res.put("data",workapiRecord);
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
    @RequestMapping(value = "getPurchaseRecord",method = RequestMethod.POST)
    public JSONObject  getPurchaseRecord(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("token"));
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        try{
            List<HashMap<String,Object>> workapiRecord = workapiRecordMapper.getPurchaseRecord(user.getAddress());
            if (workapiRecord==null){
                res.put("msg", "无记录");
                res.put("code",1);
                return res;
            }
            res.put("data",workapiRecord);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",2);
            return res;
        }
        res.put("msg","成功");
        res.put("code",200);
        res.put("success",true);
        return res;
    }

    @RequestMapping("insureQRcode")
    @ResponseBody
    public JSONObject insureQRcode(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        String token = WwSystem.ToString(request.getParameter("token"));
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        int id = WwSystem.ToInt(request.getParameter("id"));
        if (id<0){
            res.put("msg", "参数有误");
            res.put("code",1);
            return res;
        }
        try{
            WorkapiRecord wr = workapiRecordMapper.selWorkapiRecordById(id);
            if (wr==null){
                res.put("success", false);
                res.put("msg", "记录不存在");
                res.put("code",2);
                return res;
            }
            if (wr.getStatus()!=1){
                res.put("success", false);
                res.put("msg", "该条不需要填写");
                res.put("code",3);
                return res;
            }
            InuBase inuBase = inuBaseMapper.selInuBaseByType(wr.getType());
            if (inuBase==null){
                res.put("success", false);
                res.put("msg", "result is not exist");
                res.put("code",4);
                return res;
            }
            long time = new Date().getTime();
            String message = "id:";
            if (wr.getYear()<10){
                message+=time+"0"+wr.getYear()+""+wr.getAmount();
            }else {
                message+=time+""+wr.getYear()+""+wr.getAmount();
            }
            message+="*work:"+id;
            //签名信息
            NrcMain  nm = new NrcMain();
            nm.initRPC();
            String signMessage = nm.signMessage(inuBase.getInsurancePro(),message);
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                res.put("msg", "签名失败");
                res.put("success", false);
                res.put("code",5);
                return res;
            }else {
                LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            String msg = "https://reitschain.com/code/workCard?s="+signMessage+"&o="+message+"&a="+inuBase.getInsurancePro();
            res.put("data", msg);
            res.put("success", true);
        }catch (Exception e){
            res.put("msg", e.getMessage());
            res.put("success", false);
            res.put("code",6);
            return res;
        }
        res.put("msg","成功");
        res.put("code",200);
        return res;
    }


    @ResponseBody
    @RequestMapping(value="sweepPayment",method= RequestMethod.POST)
    public JSONObject sweepPayment(String token,HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        /*int  assetId = WwSystem.ToInt(request.getParameter("assetId"));
        if (assetId<0){
            res.put("message", "资产编号有误");
            return res;
        }*/
//        int assetId = 100003;
        int assetId = 999999;
        int  amount = WwSystem.ToInt(request.getParameter("amount"));
        if (amount<=0){
            res.put("msg", "支付金额有误");
            res.put("code",1);
            return res;
        }
        int type = WwSystem.ToInt(request.getParameter("type"));
        int year = WwSystem.ToInt(request.getParameter("year"));
        if (year<=0){
            res.put("msg", "所选年份错误");
            res.put("code",2);
            return res;
        }
//        String toAddress = "3BVWRxTckkd3XnyVcznGF3sXMXWSor7hyN";
        String fromAddress = user.getAddress();
        long time = new Date().getTime();
        NrcMain nm = new NrcMain();
        nm.initRPC();
        String msg = "";
        try{
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//            String primeaddr = nm.getPrimeaddr();
            InuBase  inuBase = inuBaseMapper.selInuBaseByType(type);
            if (inuBase==null){
                res.put("msg", "保障类型有误");
                res.put("code",6);
                return res;
            }

            WorkapiRecord wr = new WorkapiRecord();
            wr.setPurchaser(user.getAddress());
            wr.setAmount(amount);
            wr.setType(type);
            wr.setWay("INU");
            wr.setStatus(0);
            wr.setYear(year);
            wr.setRecordTime(format);
            res.put("time",format);
            workapiRecordMapper.addWorkapiRecord(wr);
            if (wr.getId()<0){
                res.put("msg", "添加失败");
                res.put("code",4);
                res.put("success", false);
                return res;
            }
//            String message = "assetId*999999,amount*1,toAddress*1Pu4zHy1BDLPY6HSAMqHsmoJe4aEJ3ycH3,fromAddress*1PKspzX5uxGiiMFLVDXPfacWm3WzgtYwMg,time*1559551019769";
            String message = "assetId*"+assetId+",amount*"+amount+",toAddress*"+inuBase.getInsurancePro()+",fromAddress*"+fromAddress+",time*"+time+",work*"+wr.getId();
            String signMessage = nm.signMessage(inuBase.getInsurancePro(), message);
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ format);
                res.put("message", "签名失败");
                res.put("code",3);
                res.put("success", false);
                return res;
            }else {
                LOGGER.info("签名:"+signMessage+ "时间:"+ format);
            }
            msg = "https://www.reitschain.com/code/pay?s="+signMessage+"&o="+message+"&a="+inuBase.getInsurancePro();
            wr.setSignMsg(signMessage);
            workapiRecordMapper.updateById(wr);

        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",5);
            return res;
        }
        res.put("data", msg);
        res.put("code",200);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value="/getbalancebyaddr",method=RequestMethod.POST)
    public JSONObject  getbalancebyaddr(String token,HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        int assetId = 999999;
        try{
            Map<String, String> map =new HashMap<>();
            map.put("rootAddress",user.getAddress());
            map.put("assetId",String.valueOf(assetId));
            String sign = BalanceUtil.getSign(map);
            String paramsCount = "sign_code=" + sign;
            paramsCount += "&rootAddress=" + user.getAddress();
            paramsCount += "&assetId=" + assetId;
            String url = "getbalancebyaddr";
            String s = BalanceUtil.sendPost(url, paramsCount);
            LOGGER.info("getbalancebyaddr:"+s);
            if (StringUtils.isEmpty(s)) {
                res.put("msg", "連接失敗");
                res.put("code",1);
                return res;
            }
            JSONObject jsonObjectCount = JSON.parseObject(s);
            if (jsonObjectCount.get("success").toString().equals("false")) {
                res.put("msg", "接口查詢失敗");
                res.put("code",2);
                return res;
            }
            Object data1 = jsonObjectCount.get("balance");
            res.put("data",data1);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",3);
            return res;
        }
        res.put("msg","成功");
        res.put("code",200);
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "getassetaccbookbyaddr",method = RequestMethod.POST)
    public JSONObject getAssetAccBookByAddr(String token,HttpServletRequest request, HttpServletResponse response){
        JSONObject   res = new JSONObject();
        res.put("success",false);
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        LOGGER.info("user"+user);
        Map<String, String> map =new HashMap<>(0);
        int assetId = WwSystem.ToInt(request.getParameter("assetId"));
        int pageIndex = WwSystem.ToInt(request.getParameter("pageIndex"));
        int pageSize = WwSystem.ToInt(request.getParameter("pageSize"));
        if (assetId<0){
            res.put("msg","资产ID有误");
            return  res;
        }
        if (pageIndex<0){
            pageIndex=0;
        }
        if (pageSize<0){
            pageSize=10;
        }
        try {
            String url ="getassetaccbookbyaddr";
            String s = tranferInfo("19KLLe9ezsLS1r1yGnxjWn1kGEcx3BpKmh", assetId, pageIndex, pageSize, url);
            if (StringUtils.isEmpty(s)) {
                res.put("msg", "連接失敗");
                res.put("code",1);
                return res;
            }
            JSONObject jsonObjectCount = JSON.parseObject(s);
            if (jsonObjectCount.get("success").toString().equals("false")) {
                res.put("msg", "接口查詢失敗");
                res.put("code",2);
                return res;
            }
            Object data1 = jsonObjectCount.get("data");
            res.put("data",data1);
        }catch (Exception e){
            res.put("msg",e.getMessage());
            res.put("code",3);
            return res;
        }
        res.put("success",true);
        res.put("code",200);
        res.put("msg","成功");
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "getAssetAccBookByAddr_send",method = RequestMethod.POST)
    public JSONObject getassetaccbookbyaddr_send(String token,HttpServletRequest request, HttpServletResponse response){
        JSONObject   res = new JSONObject();
        res.put("success",false);
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        int assetId = WwSystem.ToInt(request.getParameter("assetId"));
        int pageIndex = WwSystem.ToInt(request.getParameter("pageIndex"));
        int pageSize = WwSystem.ToInt(request.getParameter("pageSize"));
        if (assetId<0){
            res.put("msg","资产ID有误");
            return  res;
        }
        if (pageIndex<0){
            pageIndex=0;
        }
        if (pageSize<0){
            pageSize=10;
        }
        try {
            String url ="getAssetAccBookByAddr_send";
            String s = tranferInfo(user.getAddress(), assetId, pageIndex, pageSize, url);
            if (StringUtils.isEmpty(s)) {
                res.put("msg", "連接失敗");
                res.put("code",1);
                return res;
            }
            JSONObject jsonObjectCount = JSON.parseObject(s);
            if (jsonObjectCount.get("success").toString().equals("false")) {
                res.put("msg", "接口查詢失敗");
                res.put("code",2);
                return res;
            }
            Object data1 = jsonObjectCount.get("data");
            res.put("data",data1);
        }catch (Exception e){
            res.put("msg",e.getMessage());
            res.put("code",3);
            return res;
        }
        res.put("success",true);
        res.put("code",200);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "getassetaccbookbyaddr_receive",method = RequestMethod.POST)
    public JSONObject getassetaccbookbyaddr_receive(String token,HttpServletRequest request, HttpServletResponse response){
        JSONObject   res = new JSONObject();
        res.put("success",false);
        WeChatUserExample user = OpenApiLoginContext.getUser(token);
        if(user==null){
            res.put("msg", "无效token");
            res.put("code",101);
            return res;
        }
        int assetId = WwSystem.ToInt(request.getParameter("assetId"));
        int pageIndex = WwSystem.ToInt(request.getParameter("pageIndex"));
        int pageSize = WwSystem.ToInt(request.getParameter("pageSize"));
        if (assetId<0){
            res.put("msg","资产ID有误");
            return  res;
        }
        if (pageIndex<0){
            pageIndex=0;
        }
        if (pageSize<0){
            pageSize=10;
        }
        try {
            String url ="getassetaccbookbyaddr_receive";
            String s = tranferInfo(user.getAddress(), assetId, pageIndex, pageSize, url);
            if (StringUtils.isEmpty(s)) {
                res.put("msg", "連接失敗");
                res.put("code",1);
                return res;
            }
            JSONObject jsonObjectCount = JSON.parseObject(s);
            if (jsonObjectCount.get("success").toString().equals("false")) {
                res.put("msg", "接口查詢失敗");
                res.put("code",2);
                return res;
            }
            Object data1 = jsonObjectCount.get("data");
            res.put("data",data1);
        }catch (Exception e){
            res.put("msg",e.getMessage());
            res.put("code",3);
            return res;
        }
        res.put("success",true);
        res.put("code",200);
        return res;
    }



    /*================*********=================*/
    private String   tranferInfo(String address,int assetId,int pageIndex,int pageSize,String url){

        Map<String, String> map =new HashMap<>(0);
        map.put("rootAddress",address);
        map.put("assetId",String.valueOf(assetId));
        map.put("pageIndex",String.valueOf(pageIndex));
        map.put("pageSize",String.valueOf(pageSize));
        String sign = BalanceUtil.getSign(map);
        String paramsCount = "sign_code=" + sign;
        paramsCount += "&rootAddress=" + address;
        paramsCount += "&assetId=" + assetId;
        paramsCount += "&pageIndex=" + pageIndex;
        paramsCount += "&pageSize=" + pageSize;
        String s = BalanceUtil.sendPost(url, paramsCount);
//        LOGGER.info("交易信息："+s);
        return s;
    }
}
