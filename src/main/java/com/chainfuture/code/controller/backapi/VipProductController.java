package com.chainfuture.code.controller.backapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.filter.BackApiLoginContext;
import com.chainfuture.code.filter.OpenApiLoginContext;
import com.chainfuture.code.mapper.ProductTraceabilityMapper;
import com.chainfuture.code.mapper.SignMsgMapper;
import com.chainfuture.code.service.*;
import com.chainfuture.code.utils.ApiUtil;
import com.chainfuture.code.utils.HttpRequestUtil;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/backapi/vipproduct/")
public class VipProductController {

    @Autowired
    private AuthProductService authProductService;
    @Autowired
    private SignMsgMapper signMsgMapper;
    @Autowired
    private AuthManageService authManageService;
    @Autowired
    private ProductTraceabilityMapper productTraceabilityMapper;
    @Autowired
    private PrimeTxService primeTxService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private ProductBounsService  productBounsService;
    @Autowired
    private HomePageService homePageService;
    public static final Logger LOGGER = Logger.getLogger(VipProductController.class);



    @ResponseBody
    @RequestMapping(value = "getTraceabilityCode", method = RequestMethod.POST)
    public JSONObject  getTraceabilityCode(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String proAddress = WwSystem.ToString(request.getParameter("proAddress"));
        try{
            String codeId = new Date().getTime()+"";
            NrcMain  nm = new NrcMain();
            nm.initRPC();
            if (StringUtils.isEmpty(proAddress)){
                proAddress = nm.getPrimeaddr();
            }
            String originMessage=null;
            String signMessage=null;
            String message=null;
            originMessage = "id:"+codeId+",type:888";
            signMessage = nm.signMessage(proAddress,originMessage);
//                LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.warn("签名失败:"+codeId+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
                res.put("code", 1);
                res.put("msg", "签名失败");
                return res;
            }
            message = "https://reitschain.com/co?s="+signMessage+"&o="+originMessage+"&a="+proAddress;
            res.put("data",message);
        }catch (Exception e){
            res.put("code", 2);
            res.put("msg", "失败");
            return res;
        }
        res.put("code", 0);
        res.put("msg", "成功");
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "confirmHangUp", method = RequestMethod.POST)
    public JSONObject  confirmHangUp(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String proAddress = WwSystem.ToString(request.getParameter("productAddress"));//产品地址
        String batchAddress = WwSystem.ToString(request.getParameter("batchAddress"));
        if (StringUtils.isEmpty(proAddress)&&StringUtils.isEmpty(batchAddress)){
            res.put("code", 1);
            res.put("msg", "参数有误");
            return res;
        }
        AuthManage authManage = new AuthManage();
        authManage.setDutyAddress(user.getAddress());
        authManage.setAddress(user.getAddress());

        try{
            SysModule sm = new SysModule();
            sm.setSname("ProductionManageAddress");
            List<SysModule> sysModule = sysModuleService.getModuleName(sm);
            authManage.setManageAddr(sysModule.get(0).getAddress());
            authManage.setStatus(0);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation("挂接");
            String  info = batchAddress+"∈"+proAddress;
            authManage.setUpLinkInfo(info);
            authManageService.addAuthManage(authManage);
            if(authManage.getMid()<0){
                res.put("code",1);
                res.put("msg","新增失败");
                return res;
            }
            NrcMain nm = new NrcMain();
            nm.initRPC();
            String msg="id:"+authManage.getMid()+"*work:0";
            String signMessage = nm.signMessage(sysModule.get(0).getAddress(),msg);
            LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                res.put("code",2);
                res.put("msg","签名失败");
                return res;
            }
            String message = "https://reitschain.com/code/workCard?s="+signMessage+"&o="+msg+"&a="+sysModule.get(0).getAddress();
            res.put("message",message);

        }catch (Exception e){
            res.put("code", 2);
            res.put("msg", "失败");
            return res;
        }
        res.put("code", 0);
        res.put("msg", "成功");
        res.put("success",true);
        return res;
    }

    //根据地址获取产品码
    @ResponseBody
    @RequestMapping(value = "coinMoney", method = RequestMethod.POST)
    public JSONObject  coinMoney(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String proAddress = WwSystem.ToString(request.getParameter("productAddress"));//产品地址
        String address = WwSystem.ToString(request.getParameter("address"));//用户地址
        String busiJson = WwSystem.ToString(request.getParameter("busiJson"));//
        int assetId = WwSystem.ToInt(request.getParameter("assetId"));
        if (proAddress=="" || proAddress.length()<0||proAddress==null){
            res.put("success", false);
            res.put("msg", "product is null");
            return res;
        }
        if (address==""|| address.length()<0||address==null){
            res.put("success", false);
            res.put("msg", "address is null");
            return res;
        }
        res.put("toAddress",address);
        res.put("fromAddress",proAddress);

        try{
            ProductExample  product = authProductService.getProductByAddress(proAddress);
            if (product==null){
                res.put("success", false);
                res.put("msg", "product is null");
                return res;
            }
//            String  num = assetId + ":" + 1;
            PrimeTxExample  pt = new PrimeTxExample();
            pt.setFromAddress(proAddress);
            pt.setToAddress(address);
            pt.setBusiJson(busiJson);
            NrcMain nm=new NrcMain();
            nm.initRPC();
            String  param="fromAddress="+product.getPrimeAddr()+"&toAddress="+address+"&assetId="+product.getAssetId()+"&amount=1&tag=1&busiJson="+busiJson;
            String txId = HttpRequestUtil.sendPost("http://127.0.0.1:9590/doTxWidthBusiData", param, true);
            LOGGER.info("txId:"+txId);
            if (StringUtils.isEmpty(txId)){
                res.put("success", false);
                res.put("msg", "tx is error");
                return res;
            }
            JSONObject jsonObject = JSONObject.parseObject(txId);


            String txId1 = (String) jsonObject.get("txId");
//            String message = (String) jsonObject.get("message");
            res.put("txId",txId1);
            pt.setBusiTxId(txId1);
            pt.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            pt.setAmount(1);
            primeTxService.addPrimeTx(pt);
        }catch (Exception e){
            res.put("msg", "失败");
            res.put("code", 2);
            return res;
        }
        res.put("msg", "成功");
        res.put("code", 0);
        res.put("success",true);
        return res;
    }

    //产品列表
    @ResponseBody
    @RequestMapping(value="addressList", method = RequestMethod.POST)
    public JSONObject addressList(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int type = 0;
        try{
            List<ProductExample> list = authProductService.selVipProductList(type);
            if (list.size()<=0){
                res.put("code", 1);
                res.put("msg", "无记录");
                return res;
            }
            res.put("data",list);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",2);
            return res;
        }
        res.put("code",0);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }

    //根据地址获取产品码
    @ResponseBody
    @RequestMapping(value = "getBatchById", method = RequestMethod.POST)
    public JSONObject  getBatchById(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int id = WwSystem.ToInt(request.getParameter("id"));
        if (id<0){
            res.put("msg", "参数有误");
            res.put("code", 1);
            return res;
        }
        try{
            Map<String,Object> map =signMsgMapper.getBatchById(id);
            if (map==null){
                res.put("msg", "无数据");
                res.put("code", 1);
                return res;
            }
            res.put("data",map);
            res.put("userAddress",user.getAddress());
        }catch (Exception e){
            res.put("msg", "失败");
            res.put("code", 2);
            return res;
        }
        res.put("msg", "成功");
        res.put("code", 0);
        res.put("success",true);
        return res;
    }

    //根据地址获取产品码
    @ResponseBody
    @RequestMapping(value = "getProductById", method = RequestMethod.POST)
    public JSONObject  getProductById(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int id = WwSystem.ToInt(request.getParameter("id"));
        if (id<0){
            res.put("msg", "参数有误");
            res.put("code", 1);
            return res;
        }
        try{
            ProductExample  map = authProductService.getProductById(id);
            if (map==null){
                res.put("msg", "无数据");
                res.put("code", 1);
                return res;
            }
            res.put("data",map);
        }catch (Exception e){
            res.put("msg", "失败");
            res.put("code", 2);
            return res;
        }
        res.put("msg", "成功");
        res.put("code", 0);
        res.put("success",true);
        return res;
    }



    //绑定产品
    @ResponseBody
    @RequestMapping(value = "bindProduct", method = RequestMethod.POST)
    public JSONObject bindProduct(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("msg", "无效token");
            res.put("code", 101);
            return res;
        }
        String productAddress = WwSystem.ToString(request.getParameter("productAddress"));
        if (StringUtils.isEmpty(productAddress) || productAddress ==null){
            res.put("msg", "缺少参数");
            res.put("code", 1);
            return res;
        }
        String batchAddress = WwSystem.ToString(request.getParameter("batchAddress"));
        if (StringUtils.isEmpty(batchAddress) || batchAddress ==null){
            res.put("msg", "缺少参数");
            res.put("code", 1);
            return res;
        }
        int assetId = 999999;
        try{
            ProductExample productExample = authProductService.getProductByAddress(productAddress);
            if (productExample==null){
                res.put("msg", "产品标识无效");
                res.put("code", 2);
                return res;
            }
            SignMsg signMsg = signMsgMapper.getBatchByAddress(batchAddress);
            if (signMsg==null){
                res.put("msg", "产品码标识无效");
                res.put("code", 2);
                return res;
            }

            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            Map<String,String> map =  new HashMap<>();
            String  params = "";

            NrcMain nm = new NrcMain();
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            String proName = productExample.getProName();
            if (!StringUtils.isEmpty(proName)){
                map.put("proName",proName);
                params += "&proName="+proName;
            }
            String sname = signMsg.getSname();
            if (sname!=null){
                map.put("sname",sname);
                params += "&sname="+sname;
            }
            String proAddress = signMsg.getProAddress();
            if (proAddress!=null){
                map.put("proAddress",proAddress);
                params += "&proAddress="+proAddress;
            }
            String proDescribe = productExample.getProDescribe();
            if (proDescribe!=null){
                map.put("proDescribe",proDescribe);
                params += "&proDescribe="+proDescribe;
            }
            int reward = productExample.getReward();
            if (reward>0){
                map.put("reward",reward+"");
                params += "&reward="+reward;
            }
            map.put("createTime",time);
            params += "&createTime="+time;

            String producer = signMsg.getProducer();
            if (producer!=null){
                map.put("producer",producer);
                params += "&producer="+producer;
            }
            String proAdvertImg = productExample.getProAdvertImg();
            if (proAdvertImg!=null){
                map.put("proAdvertImg",proAdvertImg);
                params += "&proAdvertImg="+proAdvertImg;
            }
            String productLicense = productExample.getProductLicense();
            if (productLicense!=null){
                map.put("productLicense",productLicense);
                params += "&productLicense="+productLicense;
            }
            String productSize = productExample.getProductSize();
            if (productSize!=null){
                map.put("productSize",productSize);
                params += "&productSize="+productSize;
            }
            String qualityPeriod = productExample.getQualityPeriod();
            if (qualityPeriod!=null){
                map.put("qualityPeriod",qualityPeriod);
                params += "&qualityPeriod="+qualityPeriod;
            }
            map.put("type","0");
            params += "&type="+0;
            map.put("status","1");
            params += "&status="+1;
            String   url = "";
            url = "api/addProduct";
            map.put("proNum","0");
            params += "&proNum="+0;
            map.put("primeAddr",primeaddr);
            params += "&primeAddr="+primeaddr;
            map.put("assetId",assetId+"");
            params += "&assetId="+assetId;
            map.put("batchAddress",productAddress);
            params += "&batchAddress="+productAddress;

            String so = "";
            String sign = ApiUtil.getSign(map);
            params += "&signCode="+sign;
            if (params.startsWith("&")){
                so = params.replaceFirst("&","");
            }
            if (productExample.getIsGroup()==2){
                String url1 = "api/addProductContainer";
                String s11 = ApiUtil.sendPost(url1, so);
                JSONObject jsonObject = JSON.parseObject(s11);
                LOGGER.info("addProductContainer:"+jsonObject);
                String resStatus1 = JSONObject.toJSONString(jsonObject.get("status"));
            }
            String s1 = ApiUtil.sendPost(url, so);
            JSONObject jsonObject = JSON.parseObject(s1);
            String resStatus = JSONObject.toJSONString(jsonObject.get("status"));
            if (resStatus.equals("false")){
                res.put("code", 1);
                res.put("msg", jsonObject.get("message"));
                return res;
            }
            signMsg.setStatus(0);
            signMsg.setBindAddress(productAddress);
            signMsg.setAmount(productExample.getReward());
            signMsg.setProducer(user.getAddress());
            signMsgMapper.updateSignMsg(signMsg);
            int num = signMsg.getNum();
            int proNum = productExample.getProNum();
            productExample.setProNum(num+proNum);
            authProductService.upProduct(productExample);
        }catch (Exception e){
            res.put("msg", "失败");
            res.put("code", 2);
            return res;
        }
        res.put("msg", "成功");
        res.put("code", 0);
        res.put("success",true);
        return res;
    }
    //批次列表
    @ResponseBody
    @RequestMapping(value = "queryBatchList", method = RequestMethod.POST)
    public JSONObject  queryBatchList(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int mold = 0;
        try{
            List<Map<String,Object>> map =signMsgMapper.queryBatchList(mold);
            if (map==null){
                res.put("msg", "无数据");
                res.put("code", 1);
                return res;
            }
            res.put("data",map);
        }catch (Exception e){
            res.put("msg", "失败"+e.getMessage());
            res.put("code", 2);
            return res;
        }
        res.put("msg", "成功");
        res.put("code", 0);
        res.put("success",true);
        return res;
    }

    //添加溯源信息
    @ResponseBody
    @RequestMapping(value = "saveTraceabilityInfo", method = RequestMethod.POST)
    public JSONObject  saveTraceabilityInfo(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String proAddress = WwSystem.ToString(request.getParameter("proAddress"));//为空即为物料码
        String enterPrise = WwSystem.ToString(request.getParameter("enterPrise"));
        if (StringUtils.isEmpty(enterPrise)){
            res.put("code", 1);
            res.put("msg", "责任企业必填");
            return res;
        }
        String handler = WwSystem.ToString(request.getParameter("handler"));
        if (StringUtils.isEmpty(handler)){
            res.put("code", 1);
            res.put("msg", "责任人必填");
            return res;
        }
        String itemName = WwSystem.ToString(request.getParameter("itemName"));
        if (StringUtils.isEmpty(itemName)){
            res.put("code", 1);
            res.put("msg", "物品名称必填");
            return res;
        }
        String itemSize = WwSystem.ToString(request.getParameter("itemSize"));
        if (StringUtils.isEmpty(itemSize)){
            res.put("code", 1);
            res.put("msg", "物品规格必填");
            return res;
        }
        String time = WwSystem.ToString(request.getParameter("time"));
        if (StringUtils.isEmpty(time)){
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        }
        String remarks = WwSystem.ToString(request.getParameter("remarks"));
        String longitude = WwSystem.ToString(request.getParameter("longitude"));
        String latitude = WwSystem.ToString(request.getParameter("latitude"));
        String country = WwSystem.ToString(request.getParameter("country"));
        String province = WwSystem.ToString(request.getParameter("province"));
        String city = WwSystem.ToString(request.getParameter("city"));
        String area = WwSystem.ToString(request.getParameter("area"));
        String detailInfo = WwSystem.ToString(request.getParameter("detailInfo"));
        String image = WwSystem.ToString(request.getParameter("image"));

        try{
            ProductTraceability pt = new ProductTraceability();
            pt.setProAddress(proAddress);
            pt.setEnterPrise(enterPrise);
            pt.setHandler(handler);
            pt.setItemName(itemName);
            pt.setItemSize(itemSize);
            pt.setTime(time);
            pt.setRemarks(remarks);
            pt.setLongitude(longitude);
            pt.setLatitude(latitude);
            pt.setCountry(country);
            pt.setProvince(province);
            pt.setCity(city);
            pt.setArea(area);
            pt.setDetailInfo(detailInfo);
            pt.setImage(image);
            productTraceabilityMapper.saveProductTraceability(pt);
        }catch (Exception e){
            res.put("msg", "失败"+e.getMessage());
            res.put("code", 2);
            return res;
        }
        res.put("msg", "成功");
        res.put("code", 0);
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "getCompantInfo", method = RequestMethod.POST)
    public JSONObject  getCompantInfo(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        try{
            HomePage homePage = homePageService.getHomePage();
            if (homePage==null){
                res.put("msg","获取数据失败");
                res.put("code",2);
                return res;
            }
            res.put("data",homePage);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",1);
            return res;
        }
        res.put("msg","成功");
        res.put("success",true);
        res.put("code",0);
        return res;
    }

}
