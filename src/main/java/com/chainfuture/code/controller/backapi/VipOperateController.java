package com.chainfuture.code.controller.backapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.controller.ProductController;
import com.chainfuture.code.filter.BackApiLoginContext;
import com.chainfuture.code.filter.OpenApiLoginContext;
import com.chainfuture.code.mapper.SignMsgMapper;
import com.chainfuture.code.mapper.WorkapiRecordMapper;
import com.chainfuture.code.service.*;
import com.chainfuture.code.utils.*;
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
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/backapi/vipoperate/")
public class VipOperateController {

    @Autowired
    private AuthProductService authProductService;
    @Autowired
    private WorkapiRecordMapper workapiRecordMapper;
    @Autowired
    private SignMsgMapper signMsgMapper;
    @Autowired
    private HomePageService homePageService;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private AuthManageService authManageService;
    @Autowired
    private ProductBounsService productBounsService;
    @Autowired
    private BounsBillService  bounsBillService;
    @Autowired
    private MajorLogService majorLogService;
    @Autowired
    private SignDetailService signDetailService;
    public static final Logger LOGGER = Logger.getLogger(VipOperateController.class);//returnManySignMsg

    //删除产品码
    @ResponseBody
    @RequestMapping(value = "delProductCode",method = RequestMethod.POST)
    public JSONObject  delProductCode(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
//        int id = WwSystem.ToInt(request.getParameter("id"));
        long id = WwSystem.ToLong(request.getParameter("id"));
        if (id<0){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        try{
            signDetailService.delProductCode(id);
        }catch (Exception e){
            res.put("code", 1);
            res.put("msg", "失败");
            return res;
        }
        res.put("code",0);
        res.put("msg","删除成功");
        res.put("success",true);
        return res;
    }

    //0-空闲产品码   1-已领取产品码
    @ResponseBody
    @RequestMapping(value = "selProductCode",method = RequestMethod.POST)
    public JSONObject  selFreeProductCode(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int status = WwSystem.ToInt(request.getParameter("status"));
        if (status<0){
            status=0;
        }
        try{
            List<SignDetail>  list = signDetailService.selProductCode(status);
            if (list.size()<=0){
                res.put("code",1);
                res.put("msg","无数据");
                return res;
            }
            res.put("data",list);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败");
            return res;
        }
        res.put("code",0);
        res.put("msg","成功");
        res.put("success",true);
        return  res;
    }

    //按未领取下载码
    @ResponseBody
    @RequestMapping(value = "downloadFreeCode",method = RequestMethod.POST)
    public JSONObject  downloadFreeCode(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String codeJson = WwSystem.ToString(request.getParameter("codeJson"));
        if (StringUtils.isEmpty(codeJson)){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        List<String> strings = null;
        try{
            JSONObject jsonObject = JSONObject.parseObject(codeJson);
            String data1 = JSONObject.toJSONString(jsonObject.get("data"));
            if(StringUtils.isEmpty(data1)){
                res.put("code", 3);
                res.put("msg", "参数格式有误");
                return res;
            }
            List<SignDetail> signDetails = JSONObject.parseArray(data1,SignDetail.class);
            if(signDetails.size()<=0){
                res.put("code", 4);
                res.put("msg", "数据格式转换失败");
                return res;
            }
            ProductController pc = new ProductController();
            strings = pc.newBatchSignMsg(signDetails, request, response);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败");
            return res;
        }
        res.put("success",true);
        res.put("code",0);
        res.put("msg","成功");
        res.put("data",strings);
        return res;
    }

    //确认支付分红
    @ResponseBody
    @RequestMapping(value = "payBounsReward",method = RequestMethod.POST)
    public JSONObject payBounsReward(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String idStr = WwSystem.ToString(request.getParameter("idStr"));
        //传ID分隔处理
        if (StringUtils.isEmpty(idStr)){
            res.put("code",2);
            res.put("msg","参数有误");
            return res;
        }
        try{
            String[] split = idStr.split(",");
            String s = split[0];
            
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败");
            return res;
        }
        res.put("success",true);
        res.put("code",0);
        res.put("msg","支付成功");
        return res;
    }

    //分红账单列表
    @ResponseBody
    @RequestMapping(value = "selBounsBillList",method = RequestMethod.POST)
    public JSONObject  selBounsBillList(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int status = WwSystem.ToInt(request.getParameter("status"));
        if (status<0){
            status = 0;
        }
        try{
            List<BounsBill>  list = bounsBillService.selBounsBillList(status);
            if (list.size()<=0){
                res.put("code",2);
                res.put("msgg","无记录");
                return res;
            }
            res.put("data",list);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败");
            return res;
        }
        res.put("success",true);
        res.put("code",0);
        res.put("msg","成功");
        return  res;
    }

    @ResponseBody
    @RequestMapping(value = "saveProductBouns", method = RequestMethod.POST)
    public JSONObject  saveProductBouns(HttpServletRequest request, HttpServletResponse response) {
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
        int pid = WwSystem.ToInt(request.getParameter("pid"));
        if (pid<=0){
            res.put("code", 1);
            res.put("msg", "参数有误");
            return res;
        }
        int amount = WwSystem.ToInt(request.getParameter("amount"));
        if (amount<0){
            amount=0;
        }
        String time = WwSystem.ToString(request.getParameter("time"));
        int type = WwSystem.ToInt(request.getParameter("type"));//1-日期    2-天数
        try{
            ProductBouns  productBouns = new ProductBouns();
            productBouns.setAmount(amount);
            productBouns.setPid(pid);
            productBouns.setTime(time);
            productBouns.setType(type);
            if (id<=0){
                productBounsService.insertProductBouns(productBouns);
            }else {
                productBouns.setId(id);
                productBounsService.updateProductBouns(productBouns);
            }
        }catch (Exception e){
            res.put("code", 2);
            res.put("msg", "失败");
            return res;
        }
        res.put("code",0);
        res.put("success",true);
        res.put("msg","成功");
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "getProductBounsById", method = RequestMethod.POST)
    public JSONObject  getProductBounsById(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int pid = WwSystem.ToInt(request.getParameter("pid"));
        if (pid<=0){
            res.put("code", 1);
            res.put("msg", "参数有误");
            return res;
        }
        try{

            ProductBouns  pb = productBounsService.selProductBounsById(pid);
            res.put("data",pb);
        }catch (Exception e){
            res.put("code", 2);
            res.put("msg", "失败");
            return res;
        }
        res.put("code",0);
        res.put("success",true);
        res.put("msg","成功");
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
            HomePage  homePage = homePageService.getHomePage();
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


    @ResponseBody
    @RequestMapping(value = "getImageMsg", method = RequestMethod.POST)
    public JSONObject getImageMsg(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res =new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
//        long codeId = WwSystem.ToLong(request.getParameter("signId"));
        String codeId = WwSystem.ToString(request.getParameter("signId"));
        if (StringUtils.isEmpty(codeId)){
            codeId = new Date().getTime()+"";
        }
        int num = WwSystem.ToInt(request.getParameter("num"));
        if (num<0){
            res.put("code", 1);
            res.put("msg", "数字输入有误");
            return res;
        }
        String proAddress = WwSystem.ToString(request.getParameter("address"));
        if (StringUtils.isEmpty(proAddress)){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        String proName = WwSystem.ToString(request.getParameter("proName"));
        if (StringUtils.isEmpty(proName)){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        List<String> strings = null;
        try{
            Map<String,Object>  map = new HashMap<>();
            map.put("proAddress",proAddress);
            map.put("signId",codeId);
            SignMsg  sm = signMsgMapper.getSignMsgByAddrAndSignId(map);
            if (sm==null){
                return null;
            }
            ProductController ptc = new ProductController();
            strings = ptc.returnManySignMsg(sm.getId()+"",sm.getType(),user.getAddress(), codeId+"", num, proAddress, proName, request, response);
            if (strings.size()<=0){
                res.put("code", 3);
                res.put("msg", "连接失败，请休息后再试");
                return res;
            }
        }catch (Exception e){
            res.put("code", 4);
            res.put("msg", "失败"+e.getMessage());
            return res;
        }
        res.put("msg","成功");
        res.put("data",strings);
        res.put("code",0);
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "getImageMsgString", method = RequestMethod.POST)
    public JSONObject getImageMsgString(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res =new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        long codeId = WwSystem.ToLong(request.getParameter("signId"));
        if (codeId<=0){
            codeId = new Date().getTime();
        }
        int num = WwSystem.ToInt(request.getParameter("num"));
        if (num<0){
            res.put("code", 1);
            res.put("msg", "数字输入有误");
            return res;
        }
        String proAddress = WwSystem.ToString(request.getParameter("address"));
        if (StringUtils.isEmpty(proAddress)){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        String proName = WwSystem.ToString(request.getParameter("proName"));
        if (StringUtils.isEmpty(proName)){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        List<String> strings = null;
        try{
            Map<String,Object>  map = new HashMap<>();
            map.put("proAddress",proAddress);
            map.put("signId",codeId);
            SignMsg  sm = signMsgMapper.getSignMsgByAddrAndSignId(map);
            if (sm==null){
                return null;
            }
            ProductController ptc = new ProductController();
            strings = ptc.returnManySignMsg(sm.getId()+"",sm.getType(),user.getAddress(), codeId+"", num, proAddress, proName, request, response);
            if (strings.size()<=0){
                res.put("code", 3);
                res.put("msg", "连接失败，请休息后再试");
                return res;
            }

            String join = String.join(",", strings);
            res.put("data",join);
            char ss='0';
            String s = listToString(strings, ss);
            String substring = s.substring(0);
            res.put("data1",substring);
        }catch (Exception e){
            res.put("code", 4);
            res.put("msg", "失败"+e.getMessage());
            return res;
        }
        res.put("msg","成功");
        res.put("code",0);
        res.put("success",true);
        return res;
    }
    public String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }
    @ResponseBody
    @RequestMapping("download")
    public JSONObject download(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res =new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        long codeId = WwSystem.ToLong(request.getParameter("signId"));
        if (codeId<=0){
            codeId = new Date().getTime();
        }
        int num = WwSystem.ToInt(request.getParameter("num"));
        if (num<0){
            res.put("code", 1);
            res.put("msg", "数字输入有误");
            return res;
        }
        String proAddress = WwSystem.ToString(request.getParameter("address"));
        if (StringUtils.isEmpty(proAddress)){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        String proName = WwSystem.ToString(request.getParameter("proName"));
        if (StringUtils.isEmpty(proName)){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        try{
            ProductController ptc = new ProductController();
            LOGGER.info("download进来了");
            boolean b = ptc.downloadCode(user.getAddress(),codeId, num, proAddress, proName, request, response);
            if (!b){
                res.put("code", 3);
                res.put("msg", "下载失败");
                return res;
            }
        }catch (Exception e){
            res.put("code", 4);
            res.put("msg", "失败"+e.getMessage());
            return res;
        }
        res.put("msg","下载成功");
        res.put("code",0);
        res.put("success",true);
        return res;
    }

    //根据产品地址获取批次
    @ResponseBody
    @RequestMapping(value = "getBatchListByAddress", method = RequestMethod.POST)
    public JSONObject  getBatchListByAddress(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String address = WwSystem.ToString(request.getParameter("address"));
        if (StringUtils.isEmpty(address) || address ==null){
            res.put("msg", "产品标识无效");
            res.put("code", 1);
            return res;
        }
        try{
            List<Map<String,Object>> map =signMsgMapper.getBatchListByAddress(address);
            if (map.size()<=0){
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
        int mold = WwSystem.ToInt(request.getParameter("mold"));
        if (mold<0){
            mold=0;
        }
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

    //添加批次
    @ResponseBody
    @RequestMapping(value = "addBatch", method = RequestMethod.POST)
    public JSONObject  addBatch(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int amount = WwSystem.ToInt(request.getParameter("amount"));
        int type = WwSystem.ToInt(request.getParameter("type"));//1-防伪码   2-防伪码+溯源码
        String sname = WwSystem.ToString(request.getParameter("sname"));
        int mold = WwSystem.ToInt(request.getParameter("mold"));
        String proAddress = "";
        if (amount<=0){
            amount = 0;
        }
        if (mold<0){
            mold=0;
        }
        if (type<=0){
            type = 1;
        }
        if (StringUtils.isEmpty(sname) || sname==null){
            res.put("msg", "缺少参数");
            res.put("code",3);
            return res;
        }
        long time = new Date().getTime();
        try{


            NrcMain nm = new NrcMain();
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();

            SignMsg signMsg = new SignMsg();
            if (mold==1){
                signMsg.setStatus(0);//状态：0-未下载，1-已下载，2-已失效，3-待绑定
                proAddress = WwSystem.ToString(request.getParameter("proAddress"));
                signMsg.setBindAddress(proAddress);
            }
            if (mold==0){
                SignMsg signMsg1 = signMsgMapper.getBatchBySname(sname);
                if (signMsg1!=null){
                    res.put("msg", "产品码不允许重复");
                    res.put("code",3);
                    return res;
                }
                ProductExample  productExample = authProductService.selBySname(sname);
                if (productExample!=null){
                    res.put("msg", "产品码不允许重复");
                    res.put("code",3);
                    return res;
                }
                signMsg.setStatus(3);//状态：0-未下载，1-已下载，2-已失效，3-待绑定
                proAddress = nm.getAccountAddress(sname);
                if (StringUtils.isEmpty(proAddress)){
                    String param="toAddress=1Nx69PCDKL9z7rU8Gq1QrW4gnLMcy7MHvm&assetId=999999&amount=1";
                    String s = HttpRequestUtil.sendPost("http://127.0.0.1:9590/sendToAddress", param, true);
                    LOGGER.error("发送交易:"+s+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    proAddress = nm.getAccountAddress(sname);
                }
            }
            signMsg.setSignId(time);
            signMsg.setNum(amount);
            signMsg.setSname(sname);
            signMsg.setProAddress(proAddress);
            signMsg.setType(type);
            signMsg.setMold(mold);
            signMsgMapper.insertSignMsg(signMsg);
            if (mold==1){
                ProductExample productExample2 = authProductService.getProductByAddress(proAddress);
                if (productExample2==null){
                    res.put("msg", "产品标识无效");
                    res.put("code", 2);
                    return res;
                }
                int proNum = productExample2.getProNum();
                productExample2.setProNum(amount+proNum);
                authProductService.upProduct(productExample2);
            }
            SysModule sm = new SysModule();
            sm.setSname("OperationalManageAddress");
            List<SysModule> sysModule = sysModuleService.getModuleName(sm);
            if (sysModule.size()<=0){
                res.put("code", 5);
                res.put("msg", "参数错误");
                return res;
            }
            AuthManage  authManage =new AuthManage();
            authManage.setManageAddr(sysModule.get(0).getAddress());
            authManage.setAddress(proAddress);
            authManage.setDutyAddress(user.getAddress());
            authManage.setStatus(1);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if (mold==0){
                authManage.setOperation("添加产品码");
            }else {
                authManage.setOperation("添加活动码");
            }
            authManageService.addAuthManage(authManage);
        }catch (Exception e){
            res.put("msg", "失败");
            res.put("code",1);
            return res;
        }
        res.put("msg", "成功");
        res.put("code",0);
        res.put("success",true);
        return res;
    }
    //置顶
    @RequestMapping(value = "moveOrder", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject  moveOrder(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int id = WwSystem.ToInt(request.getParameter("id"));
        if (id<=0){
            res.put("msg", "id无效");
            res.put("code",1);
            return res;
        }
        int orderType = WwSystem.ToInt(request.getParameter("orderType"));//0 --取消置顶    1 --置顶
        Map<String, Object> selfMap = new HashMap<>();
        selfMap.put("id",id);
        try{
            Map<String, Object> order = authProductService.selectMaxOrder();
            LOGGER.info("order:"+order);
            if (orderType==0){
                LOGGER.info("0进这里");
                selfMap.put("orderId",0);
                authProductService.updateCancleOrder(selfMap);
            }else if (orderType==1){
                int maxOrder = order == null ? 1 : Integer.valueOf(order.get("orderId").toString()) + 1;
                selfMap.put("orderId", maxOrder);
                LOGGER.info("1进这里");
                authProductService.updateCancleOrder(selfMap);
            }else {
                res.put("msg","当前没有此操作");
                res.put("code",2);
                return res;
            }
        }catch (Exception e){
            res.put("msg","失败"+e.getMessage());
            res.put("code",3);
            return res;
        }
        res.put("msg","成功");
        res.put("code",0);
        res.put("success",true);
        return res;
    }
    //扫码支付
    @ResponseBody
    @RequestMapping(value="sweepPayment",method= RequestMethod.POST)
    public JSONObject sweepPayment(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int assetId =   WwSystem.ToInt(request.getParameter("assetId"));
        int  amount = WwSystem.ToInt(request.getParameter("amount"));
        if (amount<=0){
            res.put("msg", "支付金额有误");
            res.put("code",1);
            return res;
        }
        int type = WwSystem.ToInt(request.getParameter("type"));
        String fromAddress = user.getAddress();
        long time = new Date().getTime();
        NrcMain nm = new NrcMain();
        nm.initRPC();
        String msg = "";
        try{
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String primeaddr = nm.getPrimeaddr();
            WorkapiRecord wr = new WorkapiRecord();
            wr.setPurchaser(user.getAddress());
            wr.setAmount(amount);
            wr.setType(type);
            wr.setWay("充值");
            wr.setStatus(0);
            wr.setRecordTime(format);
            res.put("time",format);
            workapiRecordMapper.addWorkapiRecord(wr);
            LOGGER.info("work:"+wr.getId());
            if (wr.getId()<0){
                res.put("msg", "添加失败");
                res.put("code",4);
                res.put("success", false);
                return res;
            }
            String message = "assetId*"+assetId+",amount*"+amount+",toAddress*"+primeaddr+",fromAddress*"+fromAddress+",time*"+time+",work*"+wr.getId();
            String signMessage = nm.signMessage(primeaddr, message);
            if(signMessage==null){
                res.put("message", "签名失败");
                res.put("code",3);
                res.put("success", false);
                return res;
            }else {
                LOGGER.info("签名:"+signMessage+ "时间:"+ format);
            }
            msg = "https://www.reitschain.com/code/pay?s="+signMessage+"&o="+message+"&a="+primeaddr;
            wr.setSignMsg(signMessage);
            workapiRecordMapper.updateById(wr);
            SysModule sm = new SysModule();
            sm.setSname("OperationalManageAddress");
            List<SysModule> sysModule = sysModuleService.getModuleName(sm);
            if (sysModule.size()<=0){
                res.put("code", 5);
                res.put("msg", "参数错误");
                return res;
            }
            AuthManage  authManage =new AuthManage();
            authManage.setManageAddr(sysModule.get(0).getAddress());
//            authManage.setAddress(proAddress);
            authManage.setDutyAddress(user.getAddress());
            authManage.setStatus(0);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation("充值");
            authManageService.addAuthManage(authManage);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",5);
            return res;
        }
        res.put("data", msg);
        res.put("code",0);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value="getBalance", method = RequestMethod.POST)
    public JSONObject getBalance(HttpServletRequest request, HttpServletResponse response){
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
            NrcMain nm = new NrcMain();
            nm.initRPC();
            Object balance =  nm.getBalance();
            res.put("balance",balance);
            //产品币数
            LOGGER.info("map查询");
            Map<String,Object> map =  signMsgMapper.selTotalAmount();
            LOGGER.info("map查询"+map);
            int totalAmount =0;
            if (map!=null){
                totalAmount = Integer.valueOf(String.valueOf(map.get("totalAmount")));
            }
            //活动币数
            LOGGER.info("map1查询");
            Map<String,Object> map1 =  authProductService.selProTotalAmount();
            LOGGER.info("map1查询"+map1);
            int totalAmount1 =0;
            if (map1!=null){
                totalAmount1 = Integer.valueOf(String.valueOf(map1.get("totalAmount")));
            }
            res.put("preAmount",+totalAmount+totalAmount1);
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

    @ResponseBody
    @RequestMapping(value="getbalancebyaddr",method=RequestMethod.POST)
    public JSONObject  getbalancebyaddr(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if(user==null){
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int assetId = 999999;
        try{
            NrcMain  nm =new NrcMain();
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            Map<String, String> map =new HashMap<>();
            map.put("rootAddress",primeaddr);
            map.put("assetId",String.valueOf(assetId));
            String sign = BalanceUtil.getSign(map);
            String paramsCount = "sign_code=" + sign;
            paramsCount += "&rootAddress=" + primeaddr;
            paramsCount += "&assetId=" + assetId;
            String url = "getbalancebyaddr";
            String s = BalanceUtil.sendPost(url, paramsCount);
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

    //根据id获取
    @ResponseBody
    @RequestMapping(value="getProductById", method = RequestMethod.POST)
    public JSONObject getProductById(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int id = WwSystem.ToInt(request.getParameter("id"));
//        String address = WwSystem.ToString(request.getParameter("address"));
        try{
           ProductExample productExample = authProductService.getProductById(id);
            res.put("data",productExample);
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
        int type = WwSystem.ToInt(request.getParameter("type"));
        if (type<0){
            type=0;
        }
        try{
            List<ProductExample> list = authProductService.selVipProductList(type);
            if (list.size()<=0){
                res.put("code", 0);
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

    //分红
    @ResponseBody
    @RequestMapping(value="getBounsSet", method = RequestMethod.POST)
    public JSONObject getBounsSet(HttpServletRequest request, HttpServletResponse response) {
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
        if (id<=0){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        try{
            ProductExample productExample = authProductService.getProductById(id);
            if (productExample==null){
                res.put("code", 3);
                res.put("msg", "无数据");
                return res;
            }
            res.put("productPeriod",productExample.getProductPeriod());
            res.put("bouns",productExample.getBouns());
            res.put("data",null);
        }catch (Exception e){
            res.put("code", 1);
            res.put("msg", "失败");
            return res;
        }
        res.put("code", 0);
        res.put("msg", "成功");
        return res;
    }
    //分红设置
    @ResponseBody
    @RequestMapping(value="saveBounsSet", method = RequestMethod.POST)
    public JSONObject saveBounsSet(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        int id = WwSystem.ToInt(request.getParameter("id"));
        if (id<=0){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        int bouns = WwSystem.ToInt(request.getParameter("bouns"));
        if (bouns<0){
            res.put("code", 2);
            res.put("msg", "参数有误");
            return res;
        }
        int type = WwSystem.ToInt(request.getParameter("type"));
        if (type<=0){
            type=1;
        }
        String time = WwSystem.ToString(request.getParameter("time"));
        try{
            ProductExample  productExample = new ProductExample();
            productExample.setBouns(bouns);
            productExample.setId(id);
            authProductService.upProduct(productExample);

        }catch (Exception  e){
           res.put("msg","失败");
           res.put("code",1);
           return res;
        }
        res.put("code", 0);
        res.put("msg", "成功");
        return  null;
    }
    //添加、修改
    /*@ResponseBody
    @RequestMapping(value="saveAddress", method = RequestMethod.POST)
    public JSONObject saveAddress(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String proName = WwSystem.ToString(request.getParameter("proName"));
        if (StringUtils.isEmpty(proName)){
            res.put("code", 1);
            res.put("msg", "产品名称不能为空");
            return res;
        }
        String sname = WwSystem.ToString(request.getParameter("sname"));
        if (StringUtils.isEmpty(proName)){
            res.put("code", 2);
            res.put("msg", "英文ID不能为空");
            return res;
        }
        int partition  = WwSystem.ToInt(request.getParameter("partition"));//整体/个体
        if (partition<0){
            partition=1;
        }
        String productLicense = WwSystem.ToString(request.getParameter("productLicense"));//生产许可证
        int bouns = WwSystem.ToInt(request.getParameter("bouns"));//分红总额
        int productPeriod = WwSystem.ToInt(request.getParameter("productPeriod"));//产品周期
        String productSize = WwSystem.ToString(request.getParameter("productSize"));//产品规格
        String qualityPeriod = WwSystem.ToString(request.getParameter("qualityPeriod"));//产品保质期
        String proAdvertImg = WwSystem.ToString(request.getParameter("proAdvertImg"));//产品广告图
        String proDescribe = WwSystem.ToString(request.getParameter("proDescribe"));//产品描述
        int activityCodeType = WwSystem.ToInt(request.getParameter("activityCodeType"));//产品描述
        if (activityCodeType<=0){
            activityCodeType = 1;
        }
        int id = WwSystem.ToInt(request.getParameter("id"));
        int type = WwSystem.ToInt(request.getParameter("type"));
        if (type<0){
            type=0;
        }
        int reward = WwSystem.ToInt(request.getParameter("reward"));
        if (reward<=0){
            reward=1;
        }
        int assetId = 999999;
        int status = WwSystem.ToInt(request.getParameter("status"));
        if (status<0){
            status = 1;
        }
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String createTime = WwSystem.ToString(request.getParameter("createTime"));
        if (StringUtils.isEmpty(createTime)){
            createTime = time;
        }

        Map<String,String> map =  new HashMap<>();
        String  params = "";
        try{

            NrcMain nm = new NrcMain();
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            ProductExample product = new ProductExample();
            if (type>=0){
                map.put("type",type+"");
                params += "&type="+type;
                product.setType(type);
            }
            product.setActivityCodeType(activityCodeType);
            if (!StringUtils.isEmpty(proName)){
                map.put("proName",proName);
                params += "&proName="+proName;
                product.setProName(proName);
            }
            if (sname!=null){
                map.put("sname",sname);
                params += "&sname="+sname;
                product.setSname(sname);
            }
            String proAddress = "";
            if (id<=0) {
                proAddress = nm.getAccountAddress(sname);
                if (proAddress == null) {
                    String param = "toAddress=1Nx69PCDKL9z7rU8Gq1QrW4gnLMcy7MHvm&assetId=999999&amount=1";
                    String s = HttpRequestUtil.sendPost("http://127.0.0.1:9590/sendToAddress", param, true);
                    LOGGER.error("发送交易:" + s + "时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    proAddress = nm.getAccountAddress(sname);
                }
            }else {
                ProductExample productById = authProductService.getProductById(id);
                proAddress = productById.getProAddress();
            }
            if (proAddress!=null){
                map.put("proAddress",proAddress);
                params += "&proAddress="+proAddress;
                product.setProAddress(proAddress);
            }
            if (productPeriod>=0){
                map.put("productPeriod",productPeriod+"");
                params += "&productPeriod="+productPeriod;
                product.setProductPeriod(productPeriod);
            }
            /*if (bouns>=0){
                map.put("bouns",bouns+"");
                params += "&bouns="+bouns;
                product.setBonus(bouns);
            }*/
           /* if (proDescribe!=null){
                map.put("proDescribe",proDescribe);
                params += "&proDescribe="+proDescribe;
                product.setProDescribe(proDescribe);
            }
            if (reward>0){
                map.put("reward",reward+"");
                params += "&reward="+reward;
                product.setReward(reward);
            }
            map.put("createTime",createTime);
            params += "&createTime="+createTime;
            product.setCreateTime(createTime);
            if (status>=0){
                map.put("status",status+"");
                params += "&status="+status;
                product.setStatus(status);
            }
            String producer = user.getAddress();
            if (producer!=null){
                map.put("producer",producer);
                params += "&producer="+producer;
                product.setProducer(producer);
            }
            if (proAdvertImg!=null){
                map.put("proAdvertImg",proAdvertImg);
                params += "&proAdvertImg="+proAdvertImg;
                product.setProAdvertImg(proAdvertImg);
            }
            if (productLicense!=null){
                map.put("productLicense",productLicense);
                params += "&productLicense="+productLicense;
                product.setProductLicense(productLicense);
            }
            if (productSize!=null){
                map.put("productSize",productSize);
                params += "&productSize="+productSize;
                product.setProductSize(productSize);
            }
            if (qualityPeriod!=null){
                map.put("qualityPeriod",qualityPeriod);
                params += "&qualityPeriod="+qualityPeriod;
                product.setQualityPeriod(qualityPeriod);
            }
            map.put("primeAddr",primeaddr);
            params += "&primeAddr="+primeaddr;
            product.setPrimeAddr(primeaddr);
            map.put("assetId",assetId+"");
            params += "&assetId="+assetId;
            product.setAssetId(assetId);
            product.setIsGroup(partition);
            String   url = "";
            if (id<=0){
                map.put("proNum","0");
                params += "&proNum="+0;
                product.setProNum(0);
                product.setOrderId(0);
                url = "api/addProduct";

            }else {
                url = "api/updateProduct";
                map.put("updateTime",time);
                params += "&updateTime="+time;
                product.setUpdateTime(time);
            }

            String so = "";
            String sign = ApiUtil.getSign(map);
            params += "&signCode="+sign;
            if (params.startsWith("&")){
                so = params.replaceFirst("&","");
            }
            if (partition==2&&id<=0){
                String url1 = "api/addProductContainer";
                String s11 = ApiUtil.sendPost(url1, so);
                JSONObject jsonObject = JSON.parseObject(s11);
                LOGGER.info("addProductContainer:"+jsonObject);
                String resStatus1 = JSONObject.toJSONString(jsonObject.get("status"));
            }
            String s1 = ApiUtil.sendPost(url, so);
            JSONObject jsonObject = JSON.parseObject(s1);
            LOGGER.info("addProduct:"+jsonObject);
            String resStatus = JSONObject.toJSONString(jsonObject.get("status"));
            SysModule sm = new SysModule();
            sm.setSname("OperationalManageAddress");
            List<SysModule> sysModule = sysModuleService.getModuleName(sm);
            if (sysModule.size()<=0){
                res.put("code", 5);
                res.put("msg", "参数错误");
                return res;
            }
            AuthManage  authManage =new AuthManage();
            authManage.setManageAddr(sysModule.get(0).getAddress());
            authManage.setAddress(proAddress);
            authManage.setDutyAddress(user.getAddress());
            authManage.setStatus(1);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if (resStatus.equals("false")){
                res.put("code", 1);
                res.put("msg",jsonObject.get("message") );
                return res;
            }else {
                if (id<=0) {
                    if (type==0){
                        authManage.setOperation("添加产品");
                    }else {
                        authManage.setOperation("添加活动");
                    }
                    authProductService.addProduct(product);
                }else {
                    if (type==0){
                        authManage.setOperation("修改产品");
                    }else {
                        authManage.setOperation("修改活动");
                    }
                    product.setId(id);
                    authProductService.upProduct(product);
                }
            }


            authManageService.addAuthManage(authManage);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",2);
            return res;
        }
        res.put("code",0);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }*/

    @ResponseBody
    @RequestMapping(value="saveAddress", method = RequestMethod.POST)
    public JSONObject saveAddress(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String token = WwSystem.ToString(request.getParameter("backLogin"));
        WeChatUserExample user = BackApiLoginContext.getUser(token);
        if (user == null) {
            res.put("code", 101);
            res.put("msg", "已过期请重新登录");
            return res;
        }
        String proName = WwSystem.ToString(request.getParameter("proName"));
        if (StringUtils.isEmpty(proName)){
            res.put("code", 1);
            res.put("msg", "产品名称不能为空");
            return res;
        }
        String sname = WwSystem.ToString(request.getParameter("sname"));
        if (StringUtils.isEmpty(proName)){
            res.put("code", 2);
            res.put("msg", "英文ID不能为空");
            return res;
        }
        int partition  = WwSystem.ToInt(request.getParameter("partition"));//整体/个体
        if (partition<0){
            partition=1;
        }
        String productLicense = WwSystem.ToString(request.getParameter("productLicense"));//生产许可证
        String productSize = WwSystem.ToString(request.getParameter("productSize"));//产品规格
        String qualityPeriod = WwSystem.ToString(request.getParameter("qualityPeriod"));//产品保质期
        String proAdvertImg = WwSystem.ToString(request.getParameter("proAdvertImg"));//产品广告图
        String proDescribe = WwSystem.ToString(request.getParameter("proDescribe"));//产品描述
        int activityCodeType = WwSystem.ToInt(request.getParameter("activityCodeType"));//产品描述
        if (activityCodeType<=0){
            activityCodeType = 1;
        }
        int id = WwSystem.ToInt(request.getParameter("id"));
        int type = WwSystem.ToInt(request.getParameter("type"));
        if (type<0){
            type=0;
        }
        int reward = WwSystem.ToInt(request.getParameter("reward"));
        if (reward<=0){
            reward=1;
        }
        int assetId = 999999;
        int status = WwSystem.ToInt(request.getParameter("status"));
        if (status<0){
            status = 1;
        }
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String createTime = WwSystem.ToString(request.getParameter("createTime"));
        if (StringUtils.isEmpty(createTime)){
            createTime = time;
        }

        Map<String,String> map =  new HashMap<>();
        String  params = "";
        try{

            NrcMain nm = new NrcMain();
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            ProductExample product = new ProductExample();
            if (type>=0){
                map.put("type",type+"");
                params += "&type="+type;
                product.setType(type);
            }
            product.setActivityCodeType(activityCodeType);
            if (!StringUtils.isEmpty(proName)){
                map.put("proName",proName);
                params += "&proName="+proName;
                product.setProName(proName);
            }
            if (sname!=null){
                map.put("sname",sname);
                params += "&sname="+sname;
                product.setSname(sname);
            }
            String proAddress = "";
            if (id<=0) {
                proAddress = nm.getAccountAddress(sname);
                if (proAddress == null) {
                    String param = "toAddress=1Nx69PCDKL9z7rU8Gq1QrW4gnLMcy7MHvm&assetId=999999&amount=1";
                    String s = HttpRequestUtil.sendPost("http://127.0.0.1:9590/sendToAddress", param, true);
                    LOGGER.error("发送交易:" + s + "时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    proAddress = nm.getAccountAddress(sname);
                }
            }else {
                ProductExample productById = authProductService.getProductById(id);
                proAddress = productById.getProAddress();
            }
            if (proAddress!=null){
                map.put("proAddress",proAddress);
                params += "&proAddress="+proAddress;
                product.setProAddress(proAddress);
            }

            if (proDescribe!=null){
                map.put("proDescribe",proDescribe);
                params += "&proDescribe="+proDescribe;
                product.setProDescribe(proDescribe);
            }
            if (reward>0){
                map.put("reward",reward+"");
                params += "&reward="+reward;
                product.setReward(reward);
            }
            map.put("createTime",createTime);
            params += "&createTime="+createTime;
            product.setCreateTime(createTime);
            if (status>=0){
                map.put("status",status+"");
                params += "&status="+status;
                product.setStatus(status);
            }
            String producer = user.getAddress();
            if (producer!=null){
                map.put("producer",producer);
                params += "&producer="+producer;
                product.setProducer(producer);
            }
            if (proAdvertImg!=null){
                map.put("proAdvertImg",proAdvertImg);
                params += "&proAdvertImg="+proAdvertImg;
                product.setProAdvertImg(proAdvertImg);
            }
            if (productLicense!=null){
                map.put("productLicense",productLicense);
                params += "&productLicense="+productLicense;
                product.setProductLicense(productLicense);
            }
            if (productSize!=null){
                map.put("productSize",productSize);
                params += "&productSize="+productSize;
                product.setProductSize(productSize);
            }
            if (qualityPeriod!=null){
                map.put("qualityPeriod",qualityPeriod);
                params += "&qualityPeriod="+qualityPeriod;
                product.setQualityPeriod(qualityPeriod);
            }
            map.put("primeAddr",primeaddr);
            params += "&primeAddr="+primeaddr;
            product.setPrimeAddr(primeaddr);
            map.put("assetId",assetId+"");
            params += "&assetId="+assetId;
            product.setAssetId(assetId);
            product.setIsGroup(partition);
            String   url = "";
            if (id<=0){
                map.put("proNum","0");
                params += "&proNum="+0;
                product.setProNum(0);
                product.setOrderId(0);
                url = "api/addProduct";

            }else {
                url = "api/updateProduct";
                map.put("updateTime",time);
                params += "&updateTime="+time;
                product.setUpdateTime(time);
            }

            String so = "";
            String sign = ApiUtil.getSign(map);
            params += "&signCode="+sign;
            if (params.startsWith("&")){
                so = params.replaceFirst("&","");
            }
            if (partition==2&&id<=0){
                String url1 = "api/addProductContainer";
                String s11 = ApiUtil.sendPost(url1, so);
                JSONObject jsonObject = JSON.parseObject(s11);
                LOGGER.info("addProductContainer:"+jsonObject);
                String resStatus1 = JSONObject.toJSONString(jsonObject.get("status"));
            }
            String s1 = ApiUtil.sendPost(url, so);
            JSONObject jsonObject = JSON.parseObject(s1);
            LOGGER.info("addProduct:"+jsonObject);
            String resStatus = JSONObject.toJSONString(jsonObject.get("status"));
            SysModule sm = new SysModule();
            sm.setSname("OperationalManageAddress");
            List<SysModule> sysModule = sysModuleService.getModuleName(sm);
            if (sysModule.size()<=0){
                res.put("code", 5);
                res.put("msg", "参数错误");
                return res;
            }
            AuthManage  authManage =new AuthManage();
            authManage.setManageAddr(sysModule.get(0).getAddress());
            authManage.setAddress(proAddress);
            authManage.setDutyAddress(user.getAddress());
            authManage.setStatus(1);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if (resStatus.equals("false")){
                res.put("code", 1);
                res.put("msg",jsonObject.get("message") );
                return res;
            }else {
                if (id<=0) {
                    if (type==0){
                        authManage.setOperation("添加产品");
                    }else {
                        authManage.setOperation("添加活动");
                    }
                    authProductService.addProduct(product);
                }else {
                    if (type==0){
                        authManage.setOperation("修改产品");
                    }else {
                        authManage.setOperation("修改活动");
                    }
                    product.setId(id);
                    authProductService.upProduct(product);
                }
            }


            authManageService.addAuthManage(authManage);
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
}
