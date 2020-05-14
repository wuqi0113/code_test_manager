package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.bitcoin.WwHttpRequest;
import com.chainfuture.code.common.FileUploadUtil;
import com.chainfuture.code.common.MD5;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.filter.BackApiLoginContext;
import com.chainfuture.code.filter.BackApiValid;
import com.chainfuture.code.mapper.*;
import com.chainfuture.code.service.*;
import com.chainfuture.code.servlet.AccessVerify;
import com.chainfuture.code.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/")
public class ApiController {

    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private SysCarteMapper  sysCarteMapper;
    @Autowired
    private PrimeTxService primeTxService;
    @Autowired
    private ProductService productService;
    @Autowired
    private AuthManageMapper authManageMapper;
    @Autowired
    private CustStrategyInfoMapper custStrategyInfoMapper;
    @Autowired
    private CustStrategyMapper custStrategyMapper;
    @Autowired
    private SysManageMapper sysManageMapper;
    @Autowired
    private WeChatUserService weChatUserService;
    @Autowired
    private AuthProductMapper authProductMapper;
    @Autowired
    private SignMsgMapper  signMsgMapper;
    @Autowired
    private MajorLogService majorLogService;
    @Autowired
    private AuthManageService authManageService;
    @Autowired
    private PublicTipsService publicTipsService;
    @Autowired
    private InuBaseMapper inuBaseMapper;
    @Autowired
    private WorkapiRecordMapper workapiRecordMapper;
    @Autowired
    private SysModuleMapper sysModuleMapper;
    @Autowired
    private BindUserService bindUserService;
    @Autowired
    private HomePageService homePageService;
    @Autowired
    private TaskFramService taskFramService;
    @Autowired
    private OrginMessageService orginMessageService;
    @Autowired
    private SignDetailService signDetailService;
    @Autowired
    private ProductTraceService productTraceService;
    //引入apache的log4j日志包
    public static final Logger LOGGER = Logger.getLogger(ApiController.class);


    public ApiController() {
    }
    @ResponseBody
    @RequestMapping("testInterface")
    public JSONObject  testInterface(HttpServletRequest request,HttpServletResponse  response){
        JSONObject  res = new JSONObject();
        /*String codeJson = WwSystem.ToString(request.getParameter("codeJson"));
        JSONObject jsonObject = JSONObject.parseObject(codeJson);
        System.out.println(jsonObject);
        Object data1 = jsonObject.get("data");
        System.out.println(data1);
        res.put("jsonObject",jsonObject);
        res.put("data1",data1);*/
        int status = 0;
        List<SignDetail>  list = signDetailService.selProductCode(status);
        if (list.size()<=0){
            res.put("code",1);
            res.put("msg","无数据");
            return res;
        }
        return  res;
    }

    @ResponseBody
    @RequestMapping("insertOrginMessage")
    public JSONObject  insertOrginMessage(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res  = new JSONObject();
        res.put("success",false);
       //message,location,img,productAddress,openid,singleId
        String message = WwSystem.ToString(request.getParameter("message"));
        if (StringUtils.isEmpty(message)){
            res.put("msg","参数message不能为空");
            return res;
        }
        String location = WwSystem.ToString(request.getParameter("location"));
        String img = WwSystem.ToString(request.getParameter("img"));
        String productAddress = WwSystem.ToString(request.getParameter("productAddress"));
        String openid = WwSystem.ToString(request.getParameter("openid"));
        if (StringUtils.isEmpty(openid)){
            res.put("msg","参数openid不能为空");
            return res;
        }
        String singleId = WwSystem.ToString(request.getParameter("singleId"));
        if (StringUtils.isEmpty(singleId)){
            res.put("msg","参数singleId不能为空");
            return res;
        }
        try{
            ProductTrace productTrace = new ProductTrace();
            productTrace.setImg(img);
            productTrace.setLocation(location);
            productTrace.setMessage(message);
            productTrace.setOpenId(openid);
            productTrace.setProAddress(productAddress);
            productTrace.setSingleId(singleId);
            productTraceService.insertProductTrace(productTrace);
        }catch (Exception e){
            res.put("msg","失败");
            return res;
        }
        res.put("success",true);
        res.put("msg","成功");
        return res;
    }

    @RequestMapping("taskDistribute")
    @ResponseBody
    public JSONObject taskDistribute(HttpServletRequest request, HttpServletResponse response ) {
        JSONObject  res  = new JSONObject();
        res.put("success",false);
        String address = WwSystem.ToString(request.getParameter("userAddress"));
        if (StringUtils.isEmpty(address)){
            res.put("msg","参数有误");
            return res;
        }
        try{
            List<SysManage>  list = sysManageMapper.selSysManageListByAddr(address);
            if (list.size()<=0){
                res.put("msg","无工作");
                return res;
            }
            //TODO 可随时将用户拥有的权限返回
            List<TaskFram> framList = new ArrayList<>();
            for (int i=0;i<list.size();i++){
                List<TaskFram>  list1 = taskFramService.getTaskFramByModuleName(list.get(i).getSname());
//                    framList.addAll(list1);
               for (int j=0;j<list1.size();j++){
                   int id = list1.get(j).getId();
                   List<TaskFram> list2 = taskFramService.getTaskFramByParentId(list1.get(j).getId());
                    if (list2.size()>0){
                        framList.addAll(list2);
                    }
               }
            }
            if (framList.size()<=0){
                res.put("msg","无工作");
                return res;
            }
            res.put("data",framList);
        }catch (Exception e){
            res.put("msg","失败");
            return res;
        }
        res.put("success",true);
        res.put("msg","成功");
        return res;
    }

    @RequestMapping("updateActivities")
    @ResponseBody
    public JSONObject updateActivities(@RequestParam("picture") MultipartFile[] pictures, HttpServletRequest request) {
        JSONObject  res = new JSONObject();
        //获取文件在服务器的储存位置
        String path = request.getSession().getServletContext().getRealPath("/public/file_list/images/");
        File filePath = new File(path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdir();
        }
        List<String> list = new ArrayList<String>();
        if (pictures != null && pictures.length > 0) {
            for (int i = 0; i < pictures.length; i++) {
                MultipartFile file = pictures[i];
                // 保存文件
                list = saveFile(request, file, list, path);
            }
        }
        if (list.size()<0){
            res.put("success", false);
            res.put("msg", "上传失败");
            return res;
        }
        res.put("success", true);
        res.put("data", list);
        res.put("msg", "上传成功");
        return res;
    }

    private List<String> saveFile(HttpServletRequest request,
                                  MultipartFile file, List<String> list, String path) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            //获取原始文件名称(包含格式)
            String originalFileName = file.getOriginalFilename();
            //获取文件类型，以最后一个`.`为标识
            String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
            //获取文件名称（不包含格式）
            String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            //设置文件新名称: 当前时间+文件名称（不包含格式）
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String date = sdf.format(d);
            String fileName = date + name + "." + type;

            //在指定路径下创建一个文件
            File targetFile = new File(path, fileName);
            //将文件保存到服务器指定位置
            try {
                String fullRoot = WwSystem.getFullRoot(request);
                file.transferTo(targetFile);
                list.add(fullRoot+"public/file_list/images/"+fileName);
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @RequestMapping("upload")
    @ResponseBody
    public FileUploadUtil.UploadResult upload(@RequestParam("picture") MultipartFile picture, HttpServletRequest request) {

        //获取文件在服务器的储存位置
        String path = request.getSession().getServletContext().getRealPath("/public/file_list/images/");
        File filePath = new File(path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdir();
        }
        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);
        //将文件保存到服务器指定位置
        try {
            String fullRoot = WwSystem.getFullRoot(request);
            picture.transferTo(targetFile);
            //将文件在服务器的存储路径返回
            return new FileUploadUtil.UploadResult(true,originalFileName, fullRoot+"public/file_list/images/"+fileName, name,picture.getSize());
        } catch (IOException e) {
            e.printStackTrace();
            return new FileUploadUtil.UploadResult(false,e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping("getApiTest")
    public JSONObject getApiTest(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success",false);
        try{

            PublicTipsExample  publicTipsExample = new PublicTipsExample();
            publicTipsExample.setType(1);
            PublicTipsExample tips = publicTipsService.getTipsMessage(publicTipsExample);
            if (tips==null){
                res.put("success", false);
                res.put("msg", "Tips  is  not  exist");
                return res;
            }
            res.put("msg","<img src='"+tips.getMessage()+"'/>");
        }catch (Exception e){
            res.put("msg","失败");
            return res;
        }
        res.put("success",true);
        return res;
    }
    public JSONObject  ress (long codeId){
        JSONObject res = new JSONObject();
        Map<String,Object>  map = new HashMap<>();
        map.put("proAddress","17ijAKeVSa8SruSc3DRo5YiZ1ZCBK7foP");
        map.put("signId",codeId);

        SignMsg  sm = signMsgMapper.getSignMsgByAddrAndSignId(map);
        res.put("data",sm);
        return res;
    }

    public JSONObject  ress1 (Long codeId){
        JSONObject res = new JSONObject();
        Map<String,Object>  map = new HashMap<>();
        map.put("proAddress","17ijAKeVSa8SruSc3DRo5YiZ1ZCBK7foP");
        map.put("signId",codeId);

        SignMsg  sm = signMsgMapper.getSignMsgByAddrAndSignId(map);
        res.put("data",sm);
        return res;
    }

    @ResponseBody
    @RequestMapping(value="manageList", method = RequestMethod.POST)
    public JSONObject manageList(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String moduleAddress = WwSystem.ToString(request.getParameter("moduleAddress"));
        if (StringUtils.isEmpty(moduleAddress)) {
            res.put("code", 1);
            res.put("msg", "参数有误");
            return res;
        }
        String startTime = WwSystem.ToString(request.getParameter("startTime"));
        String endTime = WwSystem.ToString(request.getParameter("endTime"));
        try{
            HashMap<String,Object>  map = new HashMap<String,Object>();
            map.put("manageAddr",moduleAddress);
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            List<HashMap<String,Object>>  list = sysManageMapper.manageList(map);
            if (list.size()<=0){
                res.put("code", 2);
                res.put("msg", "无记录");
                return res;
            }
            res.put("manageData",list);
        }catch (Exception e){
            res.put("msg","失败");
            res.put("code",3);
            return res;
        }
        res.put("code",0);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping("noWayApiTest")
    public JSONObject noWayApiTest(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success",false);
        WeChatUserExample user = new WeChatUserExample();
        BackApiValid bav = new BackApiValid();
        String URI=request.getRequestURI();
        user.setAddress("19KLLe9ezsLS1r1yGnxjWn1kGEcx3BpKmh");
        List<SysManage> list =sysManageMapper.selSysManageListByAddr(user.getAddress());
        res.put("list",list);
        boolean valid = bav.Valid("19KLLe9ezsLS1r1yGnxjWn1kGEcx3BpKmh",URI,request, response);
        res.put("valid",valid);
        res.put("msg","无限制方式调用成功");
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "postApiTest", method = RequestMethod.POST)
    public JSONObject postApiTest(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success",false);
        String address = WwSystem.ToString(request.getParameter("address"));
        if (StringUtils.isEmpty(address)){
            res.put("msg","参数为空，值没传过来");
            return res;
        }
        try{
            int  count = sysManageMapper.selSysManageCount();
            res.put("count",count);
        }catch (Exception e){
            res.put("msg","失败");
            return res;
        }
        res.put("success",true);
        res.put("msg","POST请求成功");
        return res;
    }







    @ResponseBody
    @RequestMapping(value="upTest",method = RequestMethod.POST)
    public JSONObject upTest(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();

        String signChinese = WwSystem.ToString(request.getParameter("signChinese"));
        String msg = "";
        if (StringUtils.isEmpty(signChinese)){
            msg = "尝试签名中文";
        }else {
            msg = signChinese;
        }

        NrcMain nm = new NrcMain();
        nm.initRPC();
        try {
            String accountAddress = nm.getAccountAddress(msg);
            res.put("account",accountAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @ResponseBody
    @RequestMapping(value="getAddressById",method = RequestMethod.POST)
    public JSONObject getAddressById(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String userId = WwSystem.ToString(request.getParameter("userId"));
        if (StringUtils.isEmpty(userId)) {
            res.put("msg", "参数有误");
            return res;
        }
        try{
            AuthManageExample   authManageExample = authManageMapper.selAuthManageByOpenId(userId);
            if (authManageExample==null){
                res.put("msg","未查到记录");
                return res;
            }
            res.put("address",authManageExample.getAddress());
            res.put("userId",userId);
        }catch (Exception e){
            res.put("msg", "失败");
            return res;
        }
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value="bindingUser",method = RequestMethod.POST)
    public JSONObject bindingUser(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        String userId = WwSystem.ToString(request.getParameter("userId"));
        if (StringUtils.isEmpty(userId)||userId==null){
            res.put("msg","参数有误");
            return res;
        }
        try{
            AuthManageExample   authManageExample = authManageMapper.selAuthManageByOpenId(userId);
            if (authManageExample!=null){
                res.put("msg","已绑定");
                return res;
            }
            NrcMain nm = new NrcMain();
            nm.initRPC();
            AuthManage authManage = new AuthManage();
            authManage.setOpenId(userId);
            authManage.setManageAddr(nm.getPrimeaddr());
            authManage.setStatus(1);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation("绑定地址");
            authManageMapper.addAuthManage(authManage);
            if(authManage.getMid()<0){
                res.put("code",1);
                res.put("msg","记录失败");
                return res;
            }

            String msg = "id:" + authManage.getMid() + "*work:0";
            String signMessage = nm.signMessage(nm.getPrimeaddr(), msg);
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                res.put("msg","签名失败");
                return res;
            } else {
                LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            String message = "https://reitschain.com/code/workCard?s=" + signMessage + "&o=" + msg + "&a=" + nm.getPrimeaddr();
            res.put("msg",message);
        }catch (Exception e){
            res.put("msg","失败"+e.getMessage());
            return res;
        }
        res.put("success",true);
        return res;
    }


    @ResponseBody
    @RequestMapping(value="bindingAddress",method = RequestMethod.POST)
    public JSONObject bindingAddress(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("success", false);
        String userId = WwSystem.ToString(request.getParameter("userId"));
        if (StringUtils.isEmpty(userId)) {
            res.put("msg", "参数有误");
            return res;
        }
        String code = WwSystem.ToString(request.getParameter("code"));
        if (StringUtils.isEmpty(code)) {
            res.put("msg", "code参数必传");
            return res;
        }
        try{
            AccessVerify av = new AccessVerify();
            JSONObject verify = av.verify(request);
            if (verify.get("success").toString().equals("false")){
                res.put("msg","连接失敗");
                return res;
            }

            BindUserExample bindUserExample = new BindUserExample();
            bindUserExample.setAddress(verify.get("data").toString());
            bindUserExample.setUserId(userId);
            bindUserService.addBindUser(bindUserExample);

            res.put("address",verify.get("data"));
            res.put("userId",userId);
        }catch (Exception e){
            res.put("msg", "失败");
            return res;
        }
        res.put("success",true);
        return res;
    }




    @RequestMapping("jsonTest")
    @ResponseBody
    public JSONObject  jsonTest(HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        String param_txId = "txId="+WwSystem.ToString(request.getParameter("txId"));
        while (true) {
            String s = WwHttpRequest.sendGet("http://127.0.0.1:9590/getRawTxBusiData", param_txId);
            JSONObject jsonObject1 = JSONObject.parseObject(s);
            if (Integer.parseInt(JSONObject.toJSONString(jsonObject1.get("code"))) == 0) {
                JSONObject data = (JSONObject) jsonObject1.get("data");
                if (data != null) {
                    result.put("cccc", 1111111);
                    result.put("data1", data);
                    break;
                }
            }
        }

        /*JSONObject jsonObject1 = JSONObject.parseObject(s);
        JSONArray data = (JSONArray) jsonObject1.get("data");
        JSONObject o = (JSONObject) data.get(0);
        result.put("o",o);
        JSONObject data1 = (JSONObject) o.get("Data");
        Object id = data1.get("id");
        result.put("id",id);
        Object address = data1.get("address");
        result.put("address",address);*/

       /* String fromAddress1 = JSONObject.toJSONString(data.get("FromAddress"));
        result.put("fromAddress",fromAddress1);
        JSONObject data1 = (JSONObject) jsonObject1.get("Data");
        String id = JSONObject.toJSONString(data1.get("id"));*/
        return result;
    }
    @RequestMapping("newsListApi")
    @ResponseBody
    public JSONObject newsListApi(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        int pageIndex = WwSystem.ToInt(request.getParameter("pageIndex"));
        int limit = WwSystem.ToInt(request.getParameter("limit"));
        String assetName = WwSystem.ToString(request.getParameter("assetName"));
        if (pageIndex<0){
            pageIndex=0;
        }
        if (limit<0){
            limit=0;
        }
        if (assetName==null||StringUtils.isEmpty(assetName)){
            res.put("msg","参数不能为空");
            res.put("succcess",false);
        }
        Map<String,String> map =  new HashMap<>();
        map.put("assetName",assetName);
        //查询总数
        String signCount = ApiUtil.getSign(map);
        System.out.println("sign："+signCount);
        String urlCount = "api/getNewsCount";
        String paramsCount="signCode="+signCount;
        paramsCount += "&assetName="+assetName;
        String s = ApiUtil.sendPost(urlCount, paramsCount);
        JSONObject jsonObjectCount = JSON.parseObject(s);
        JSONObject result1 = (JSONObject) jsonObjectCount.get("result");
        Object newsCount = result1.get("newsCount");
        res.put("count", newsCount);
        //查询所有
        map.put("pageIndex",String.valueOf(pageIndex));
        map.put("limit",String.valueOf(limit));
        String sign = ApiUtil.getSign(map);
        System.out.println("sign："+sign);
        String url = "api/getNews";
        String params="assetName="+assetName;
        params += "&signCode="+sign;
        params += "&pageIndex="+pageIndex;
        params += "&limit="+limit;
        String s1 = ApiUtil.sendPost(url, params);
        JSONObject jsonObject = JSON.parseObject(s1);
        Object result = jsonObject.get("result");
        res.put("data",result);
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }

    @RequestMapping("insureApi")
    @ResponseBody
    public JSONObject insureApi(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        int year = WwSystem.ToInt(request.getParameter("year"));//年份
        if (year<=0){
            res.put("success", false);
            res.put("msg", "interval invalid!");
            return res;
        }
        String payNum = WwSystem.ToString(request.getParameter("payNum"));//凭证编号
        String way = WwSystem.ToString(request.getParameter("way"));//凭证编号对应的支付方式
        if (StringUtils.isEmpty(payNum) && StringUtils.isEmpty(way) ){
            res.put("success", false);
            res.put("msg", "certificates and ways should not be empty!");
            return res;
        }
        int type = WwSystem.ToInt(request.getParameter("type"));//保险类型
        int amount = WwSystem.ToInt(request.getParameter("amount"));//保险类型
        if (type<0||amount<0){
            res.put("success", false);
            res.put("msg", "params invalid!");
            return res;
        }
        try{
            InuBase  inuBase = inuBaseMapper.selInuBaseByType(type);
            if (inuBase==null){
                res.put("success", false);
                res.put("msg", "result is not exist");
                return res;
            }
            long time = new Date().getTime();
            String message = "id:";
            if (year<10){
                message+=time+"0"+year+""+amount;
            }else {
                message+=time+""+year+""+amount;
            }
            message+="*work:"+inuBase.getId();
            //签名信息
            NrcMain  nm = new NrcMain();
            nm.initRPC();
            String signMessage = nm.signMessage(inuBase.getInsurancePro(),message);
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                res.put("msg", "签名失败");
                res.put("success", false);
                return res;
            }else {
                LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            String msg = "https://reitschain.com/code/workCard?s="+signMessage+"&o="+message+"&a="+inuBase.getInsurancePro();
            WorkapiRecord  wr = new WorkapiRecord();
            wr.setSignMsg(msg);
            wr.setAmount(amount);
            wr.setPayNum(payNum);
            wr.setType(type);
            wr.setWay(way);
            wr.setStatus(1);
            wr.setYear(year);
            wr.setAppId(null);
            wr.setRecordTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            workapiRecordMapper.addWorkapiRecord(wr);
            if (wr.getId()<0){
                res.put("msg", "添加失败");
                res.put("success", false);
                return res;
            }
            res.put("data", msg);
            res.put("success", true);
        }catch (Exception e){
            res.put("message", e.getMessage());
            res.put("success", false);
        }
        return res;
    }


    @RequestMapping("nodeSendToAddr")
    @ResponseBody
    public JSONObject  nodeSendToAddr(HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        NrcMain nm = new NrcMain();
        nm.initRPC();
        String s = nm.sendToAddress("16nTo9eyf3gGSVTbyobYkaWBZzGEKpa3XU", "1", null, null);
        result.put("address",s);
        return result;
    }

    @RequestMapping("imageTest")
    @ResponseBody
    public JSONObject  imageTest(HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        String imgUrl = WwSystem.ToString(request.getParameter("imgUrl"));
        String filePath = WwSystem.ToString(request.getParameter("filePath"));
        String myFileName = WwSystem.ToString(request.getParameter("myFileName"));
        String htmlPicture = new TxController().getHtmlPicture(imgUrl, filePath, myFileName);
        result.put("htmlPicture",htmlPicture);
        return result;
    }

    @RequestMapping("nodeTest")
    @ResponseBody
    public JSONObject  nodeTest(HttpServletRequest request, HttpServletResponse response){
        long timeStamp = new Date().getTime();
        String url = "https://reitschain.com/api/test";
        String  appId="test";
        String  secret="test";
        JSONObject result = new JSONObject();
        String src="test"+"test"+timeStamp;
        System.out.println(src);
        String sign_code2= MD5.md5(src);
        System.out.println(sign_code2);
        MedalIconController mc = new MedalIconController();
        WwHttpRequest req=new WwHttpRequest();
        String params="appId="+appId;
        params += "&timeStamp="+timeStamp;
        params += "&signCode="+sign_code2;
        String s = HttpRequestUtil.sendGet(url, params);
        String ss = HttpRequestUtil.sendPost(url, params,true);
        result.put("s",s);
        result.put("ss",ss);
        String s1 = req.get(url, params);
        System.out.println("s1:"+s1);
        result.put("s1",s1);
        String res=req.post(url, params);
        result.put("res",res);
        System.out.println(res);
        try {
            mc.farmPort(url,
                    "appId="+appId+
                            "&timeStamp="+timeStamp+
                            "&signCode="+sign_code2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("receiveInfo")
    @ResponseBody
    public JSONObject receiveInfo(HttpServletRequest request, HttpServletResponse response ) {
        JSONObject  res = new JSONObject();
        return res;
    }

    @RequestMapping("authMsg")
    @ResponseBody
    public JSONObject authMsg(HttpServletRequest request, HttpServletResponse response ) {
        JSONObject  res = new JSONObject();
        int id = WwSystem.ToInt(request.getParameter("id"));
        int workId = WwSystem.ToInt(request.getParameter("work"));
        if (id<0){
            res.put("success", false);
            res.put("msg", "id is error");
            return res;
        }
        if (workId==0){
            AuthManage authManage = authManageMapper.selAuthManageById(id);
            if (authManage==null){
                res.put("success", false);
                res.put("msg", "result is not exist");
                return res;
            }
            String  detailClause = "";
            String operation = authManage.getOperation();
            WeChatUserExample  user = weChatUserService.selUserByAddr(authManage.getDutyAddress());
            HomePage  homePage = homePageService.getHomePage();
            detailClause+="<div style='margin-bottom:10px'>责任人:"+homePage.getCompanyIntroduce()+"</div>";
            detailClause+="<br/>";
            detailClause+="<div style='margin-bottom:10px'>邀约人:"+user.getNickname()+"</div>";
            if (operation.equals("邀约")){
                detailClause+="<div style='margin-bottom:10px'>>邀约人职务:人事管理员</div>";
                SysModule sysModule = sysModuleMapper.moduleInfo(authManage.getManageAddr());
                if (sysModule!=null){
                    detailClause+="<div style='margin-bottom:10px'>邀请您成为:"+sysModule.getModuleName()+"</div>";
                    detailClause+="<div style='margin-bottom:10px'>工作描述:"+sysModule.getWorkDescribe()+"</div>";
                }else {

                }
            }
            if (operation.equals("挂接")){
                detailClause+="<div style='margin-bottom:10px'>职务:生产管理员</div>";
                detailClause+="<br/>";
                detailClause+="<div style='margin-bottom:10px'>工作描述:挂接信息上链</div>";
                detailClause+="<div style='margin-bottom:10px'>请点击按钮完成上链操作</div>";
            }



            authManage.setDetailClause(detailClause);
            res.put("data",authManage);
        }else {
            String  workMsg = "";
            WorkapiRecord  workapiRecord  = workapiRecordMapper.selWorkapiRecordById(workId);
            if (workapiRecord==null) {
                res.put("success", false);
                res.put("msg", "workapiRecord is not exist");
                return res;
            }
            InuBase  inuBase = inuBaseMapper.selInuBaseByType(workapiRecord.getType());
            if (inuBase==null){
                res.put("success", false);
                res.put("msg", "insurance is not exist");
                return res;
            }
            int type = inuBase.getInsuranceType();
            if (type==100){
                if (type>0){
                    workMsg+="<div style='margin-bottom:10px'>保障项目:"+inuBase.getInsuranceName()+"</div>";
                }
                int year = workapiRecord.getYear();
                if (year>0){
                    String nextYear = DateUtil.getNextYear(DateUtil.strToDateLong(workapiRecord.getRecordTime()), year);
                    Date date = DateUtil.strToDateLong(nextYear);
                    Date date1 = DateUtil.getDate(date, -1);
                    String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
                    workMsg+="<div style='margin-bottom:10px'>保障年限:"+workapiRecord.getRecordTime()+"~"+format+"</div>";
                }
                int amount = workapiRecord.getAmount();
                if (amount>0){
    //                        hashMap.put(column_comment,amount+"");
                    workMsg+="<div style='margin-bottom:10px'>支付金额:"+amount+" INU"+"</div>";
                    workMsg+="<div style='margin-bottom:10px'>赔付金额:"+(amount*2000)/year+" INU"+"</div>";
                }
                List<CustStrategy>  custStrategyList = custStrategyMapper.selFiled(inuBase.getId());
                if (custStrategyList.size()<0){
                    res.put("success", false);
                    res.put("msg", "insurance is not exist");
                    return res;
                }
                res.put("result",custStrategyList);
            }else if (type == 1){
                    workMsg+="<div style='margin-bottom:10px'>"+inuBase.getDetailClause()+"</div>";
                    workMsg+="<div style='margin-bottom:10px'>您将向:"+workapiRecord.getPurchaser()+"</div>";
                    workMsg+="<div style='margin-bottom:10px'>支付金额:"+workapiRecord.getAmount()+"</div>";
            }else if (type == 0){
                workMsg+="<div style='margin-bottom:10px'>"+inuBase.getDetailClause()+"</div>";
                workMsg+="<div style='margin-bottom:10px'>您将向系统充值</div>";
                workMsg+="<div style='margin-bottom:10px'>充值金额:"+workapiRecord.getAmount()+"</div>";
            } else {
                        workMsg+="<div style='margin-bottom:10px'>"+inuBase.getDetailClause()+"</div>";
                        workMsg+="<div style='margin-bottom:10px'>项目名称:链云店-湄县大会</div>";
                        workMsg+="<div style='margin-bottom:10px'>支付金额:10 INU"+"</div>";
            }
            inuBase.setDetailClause(workMsg);

           /* List<HashMap<String,String>> list = workapiRecordMapper.selFiledComment();
            if (list.size()<=0){
                res.put("success", false);
                res.put("msg", "workapiRecord description is not exist");
                return res;
            }
            for (int i=0;i<list.size();i++){
                String column_name = list.get(i).get("COLUMN_NAME");
                String column_comment = list.get(i).get("COLUMN_COMMENT");
                if (column_name.equals("type")){
                    int type = inuBase.getInsuranceType();
                    if (type>0){
//                        hashMap.put(column_comment,inuBase.getInsuranceName());
                        workMsg+="<div>"+column_comment+":"+inuBase.getInsuranceName()+"</div>";
                    }
                }
                if (column_name.equals("year")){
                    int year = workapiRecord.getYear();
                    if (year>0){
//                        hashMap.put(column_comment,workapiRecord.getRecordTime()+"~"+DateUtil.getNextYear(DateUtil.strToDateLong(workapiRecord.getRecordTime()),2));
                        workMsg+="<div>"+column_comment+":"+workapiRecord.getRecordTime()+"~"+DateUtil.getNextYear(DateUtil.strToDateLong(workapiRecord.getRecordTime()),2)+"</div>";
                    }
                }
                if (column_name.equals("amount")){
                    int amount = workapiRecord.getAmount();
                    if (amount>0){
//                        hashMap.put(column_comment,amount+"");
                        workMsg+="<div>"+column_comment+":"+amount+" INU"+"</div>";
//                        workMsg+="<div>赔付金额:"+(amount*2000)/year+" INU"+"</div>";
                    }
                }
                if (column_name.equals("payNun")){
                    String payNum = workapiRecord.getPayNum();
                    if (StringUtils.isEmpty(payNum)){
//                        hashMap.put(column_comment,payNum);
                        workMsg+="<div>"+column_comment+":"+payNum+"</div>";
                    }
                }
            }*/

            res.put("data",inuBase);

        }
        res.put("msg","成功");
        res.put("success",true);
        return res;
    }

    //扫码支付回调
    @ResponseBody
    @RequestMapping("sweepCallBack")
    public JSONObject  sweepCallBack(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        int type = WwSystem.ToInt(request.getParameter("type"));
        String toPlantTxId = WwSystem.ToString(request.getParameter("toPlantTxId"));
        String toTargetTxId = WwSystem.ToString(request.getParameter("toTargetTxId"));
        String time = WwSystem.ToString(request.getParameter("time"));
        String userAddress = WwSystem.ToString(request.getParameter("userAddress"));
        String targetAddress = WwSystem.ToString(request.getParameter("targetAddress"));
        if (StringUtils.isEmpty(toPlantTxId)||toPlantTxId==null){
            res.put("msg","缺少参数，toPlantTxId");
            return res;
        }
        if (StringUtils.isEmpty(time)||time==null){
            res.put("msg","缺少参数，time");
            return res;
        }
        if (StringUtils.isEmpty(userAddress)||userAddress==null){
            res.put("msg","缺少参数，userAddress");
            return res;
        }
        if (StringUtils.isEmpty(targetAddress)||targetAddress==null){
            res.put("msg","缺少参数，toPlantTxId");
            return res;
        }

        PrimeTxExample  primeTxExample  = new PrimeTxExample();
        primeTxExample.setFromAddress(userAddress);
        primeTxExample.setToAddress(targetAddress);
        primeTxExample.setToTxData(toPlantTxId);
        primeTxExample.setCreateTime(time);
        primeTxExample.setPayStatus(1);
        try{
            NrcMain nm = new NrcMain();
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            primeTxExample.setPrimeAddress(primeaddr);
            primeTxService.addPrimeTx(primeTxExample);
            String payNum = "";
            if (StringUtils.isEmpty(toTargetTxId)){
                payNum+="https://nrc.one/explorer/tx/"+toPlantTxId;
            }else {
                payNum+="https://nrc.one/explorer/tx/"+toPlantTxId+"|"+"https://nrc.one/explorer/tx/"+toTargetTxId;
            }
            WorkapiRecord  wr = new WorkapiRecord();
            String formateTime = DateUtil.stampToDate(time);
            wr.setRecordTime(formateTime);
            wr.setPurchaser(userAddress);
            wr.setPayNum(payNum);
            wr.setStatus(1);
            int i = workapiRecordMapper.updateforPayNum(wr);
            LOGGER.info("WR："+wr);
            if (i<0){
                res.put("message", "记录支付凭证失败");
                res.put("success", false);
                return res;
            }
        }catch (Exception e){
            res.put("msg","失败");
            return res;
        }
        res.put("msg","已完成支付");
        res.put("success",true);
        return res;
    }

    //双交易
    @RequestMapping("transfer")
    @ResponseBody
    public JSONObject transfer(HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
        String fromAddress = WwSystem.ToString(request.getParameter("productAddress"));
        String toTxData = WwSystem.ToString(request.getParameter("toTxData"));
        String toAddress = WwSystem.ToString(request.getParameter("address"));
        String busiJson = WwSystem.ToString(request.getParameter("busiJson"));
        int amount = WwSystem.ToInt(request.getParameter("amount"));
//        int id = WwSystem.ToInt(request.getParameter("mid"));
        String id = WwSystem.ToString(request.getParameter("mid"));
        int workId = WwSystem.ToInt(request.getParameter("work"));
        Map<String,Object> map = new  HashMap<>();
        Map<String,Object> map1 = new  HashMap<>();
        Map<String,Object> msg = new  HashMap<>();
        if (toAddress.equals("")|| StringUtils.isEmpty(toAddress)){
            PublicTipsExample  publicTipsExample = new PublicTipsExample();
            publicTipsExample.setType(1);
            PublicTipsExample tips = publicTipsService.getTipsMessage(publicTipsExample);
            if (tips==null){
                msg.put("type",0);
                msg.put("content","Tips  is  not  exist");
                result.put("success", false);
                result.put("msg", msg);
                return result;
            }
            msg.put("type",1);
            msg.put("content",tips.getMessage());
            result.put("msg",msg);
            result.put("success", false);
            return result;
        }
        if (amount<=0){
            amount = 10;
        }
        WeChatUserExample weChatUser = weChatUserService.selUserByAddr(toAddress);
        LOGGER.info("WeChat:"+weChatUser);
        WeChatUserExample weChatUser1 = null;
        if (weChatUser==null){
            Map<String, String> map2 =new HashMap<>(0);
            map2.put("openid",toAddress);
            String sign1 = ApiUtil.getSign(map2);
            System.out.println("sign1："+sign1);
            String url = "api/getUserMessage";
            String params="signCode="+sign1;
            params += "&openid="+toAddress;

            String ss = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSONObject.parseObject(ss);
            if (jsonObject.get("status").toString().equals("false")) {
                result.put("success",false);
                result.put("msg","连接失败");
                return result;
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
        PrimeTxExample  primeTxExample  = new PrimeTxExample();
        primeTxExample.setFromAddress(fromAddress);
        primeTxExample.setToAddress(toAddress);
        primeTxExample.setToTxData(toTxData);
        primeTxExample.setBusiJson(busiJson);
        primeTxExample.setAmount(amount);
        primeTxExample.setProductId(id);
        primeTxExample.setWorkId(workId+"");
        primeTxExample.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        NrcMain nm = new NrcMain();
        nm.initRPC();
        String primeaddr = nm.getPrimeaddr();
        primeTxExample.setPrimeAddress(primeaddr);
        try {
            if (StringUtils.isEmpty(id)){//id  不传为产品防伪
                if(!toTxData.equals("")&&!StringUtils.isEmpty(toTxData)){
                    JSONObject jsonObject1 =  HttpRequestUtil.doTxWidthBusiData(fromAddress,toAddress, 100006, 1, busiJson);
                    LOGGER.info("jsonObject2："+jsonObject1);
                    Thread.sleep(5000);
                    if (jsonObject1.get("success").equals(true)){
                        map.put("platformTxId",jsonObject1.get("message"));
                        primeTxExample.setPlatformTxId(jsonObject1.get("message").toString());
                    }else {
                        map1.put("message",jsonObject1.get("message"));
                        map.put("error",map1);
                        result.put("txData",map);
                        result.put("success", false);
                        primeTxExample.setPayStatus(0);
                        primeTxExample.setErrorMsg(jsonObject1.get("message").toString());
                        primeTxService.addPrimeTx(primeTxExample);
                        return result;
                    }
                    result.put("orginTxId",toTxData);
                }
                JSONObject jsonObject = HttpRequestUtil.doTxWidthBusiData(primeaddr,toAddress, 999999, amount, busiJson);
                LOGGER.info("jsonObject："+jsonObject);
                if (jsonObject.get("success").equals(true)){
                    map.put("busiTxId",jsonObject.get("message"));
                    primeTxExample.setBusiTxId(jsonObject.get("message").toString());
                }else {
                    map1.put("message",jsonObject.get("message"));
                    map.put("error",map1);
                    result.put("txData",map);
                    result.put("success", false);
                    primeTxExample.setPayStatus(0);
                    primeTxExample.setErrorMsg(jsonObject.get("message").toString());
                    primeTxService.addPrimeTx(primeTxExample);
                    return result;
                }
                result.put("txData",map);
            }else {
                Thread.sleep(5000);
                JSONObject jsonObject1 =  HttpRequestUtil.doTxWidthBusiData(fromAddress,toAddress, 100006, 1, busiJson);
                LOGGER.info("jsonObject1："+jsonObject1);
                if (jsonObject1.get("success").equals(true)){
                    map.put("platformTxId",jsonObject1.get("message"));
                    primeTxExample.setPlatformTxId(jsonObject1.get("message").toString());
                }else {
                    map1.put("message",jsonObject1.get("message"));
                    map.put("error",map1);
                    result.put("txData",map);
                    result.put("success", false);
                    primeTxExample.setPayStatus(0);
                    primeTxExample.setErrorMsg(jsonObject1.get("message").toString());
                    primeTxService.addPrimeTx(primeTxExample);
                    return result;
                }
                result.put("txData",map);
                String operation = "";
                int tmpid = 0;
                AuthManage  authManage=null;
                if (workId==0){
                    tmpid = Integer.valueOf(id);
                    if (id.length()>11){
                        result.put("msg","other errors");
                        result.put("success", false);
                        return result;
                    }
                    authManage = authManageMapper.selAuthManageById(tmpid);
                    LOGGER.info("进来了"+authManage);
                    if(authManage.getOperation().equals("")&&StringUtils.isEmpty(authManage.getOperation())){
                        result.put("success", false);
                        result.put("msg", "record is not  exist");
                        return result;
                    }
                    operation = authManage.getOperation().trim();
                    LOGGER.info("oper"+operation);
                }else if (workId>0){
                    LOGGER.info("**************************************");
                    WorkapiRecord wr = workapiRecordMapper.getById(workId);
//                    wr.setId(workId);
                    wr.setBusiJson(busiJson);
                    wr.setReceiver(toAddress);
                    wr.setReceiveTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    LOGGER.info("WorkapiRecord："+wr);
                    int i = workapiRecordMapper.updateById(wr);
                    if (i<0){
                        result.put("success", false);
                        result.put("msg", "Modification failed");
                        return result;
                    }
                    PublicTipsExample  publicTipsExample = new PublicTipsExample();
                    publicTipsExample.setType(1);
                    PublicTipsExample tips = publicTipsService.getTipsMessage(publicTipsExample);
                    if (tips==null){
                        result.put("success", false);
                        result.put("msg", "Tips  is  not  exist");
                        return result;
                    }
                    result.put("msg","<img src='"+tips.getMessage()+"'/>");
                }

                if (operation.equals("邀约")){
                    Map<String,Object>  stringObjectMap = new HashMap<>();
                    stringObjectMap.put("manageAddr",authManage.getInsurancePro());
                    stringObjectMap.put("address",toAddress);
                    SysManage sysm1 = sysManageMapper.selSysManageByPAddrAndUAddr(stringObjectMap);
                    LOGGER.info("sysm;"+sysm1);
                    if (sysm1!= null){
                        result.put("success", false);
                        result.put("msg", "操作失败！您已拥有该权限，无法重复接受邀约！");
                        return result;
                    }
                    SysModule  sysModule= sysModuleMapper.moduleInfo(authManage.getInsurancePro());
                    if (sysModule==null){
                        result.put("success", false);
                        result.put("msg", "模块不存在，无法获得其权限");
                        return result;
                    }
                    SysManage sysManage = new SysManage();
                    sysManage.setStatus(1);
                    sysManage.setManageAddr(authManage.getInsurancePro());
                    sysManage.setAddress(toAddress);
                    sysManage.setSpecialValid(authManage.getSpecialValid());
                    sysManage.setName(sysModule.getModuleName());
                    sysManage.setSname(sysModule.getSname());
                    sysManageMapper.addSysManage(sysManage);
                    AuthManage authManage1 = new AuthManage();
                    authManage1.setStatus(1);
                    authManage1.setAddress(toAddress);
                    authManage1.setMid(tmpid);
                    authManageMapper.upSysAuthManageStatus(authManage1);
                    result.put("msg", "你已成为该地址管理员，拥有对其操作的权限");
                }else if (operation.equals("生成产品")){
                   /* LOGGER.info("authManage.getAddress()"+authManage.getAddress());
                    LOGGER.info("toAddress"+toAddress);
                    if (!authManage.getAddress().equals(toAddress)){
                        result.put("success", false);
                        result.put("msg", "Inconsistent privileges");
                        return result;
                    }*/
                    LOGGER.info("生成产品："+Integer.valueOf(authManage.getExtend())+ authManage.getAddress());
                    JSONObject product = createProduct(request, Integer.valueOf(authManage.getExtend()), authManage.getAddress());
                    if (!(boolean)product.get("success")){
                        result.put("success", false);
                        result.put("msg", product.get("message"));
                        return result;
                    }
                    AuthManage authManage1 = new AuthManage();
                    authManage1.setStatus(1);
                    authManage1.setMid(tmpid);
                    authManageMapper.upSysAuthManageStatus(authManage1);
                    Product  pro =new Product();
                    pro.setId(Integer.valueOf(authManage.getExtend()));
                    pro.setStatus(1);
                    authProductMapper.upProduct(pro);
                    result.put("msg", "产品已生成，拥有建立批次或产生防伪码的权限");
                }else if (operation.equals("产生防伪码")){
                    LOGGER.info("产生防伪码");
                   /* if (!authManage.getAddress().equals(toAddress)){
                        result.put("success", false);
                        result.put("msg", "Inconsistent privileges");
                        return result;
                    }*/
//                    createQRCode(authManage.getManageAddr(),Integer.valueOf(authManage.getIsPayment()),request,response);
                    SignMsg  signMsg = new SignMsg();
                    signMsg.setNum(Integer.valueOf(authManage.getIsPayment()));
                    signMsg.setProAddress(authManage.getInsurancePro());
                    signMsg.setStatus(0);
                    signMsg.setSignId(new Date().getTime());
                    signMsgMapper.insertSignMsg(signMsg);
                    AuthManage authManage1 = new AuthManage();
                    authManage1.setStatus(1);
                    authManage1.setMid(tmpid);
                    authManageMapper.upSysAuthManageStatus(authManage1);
                    result.put("msg", "管理员已授权"+authManage.getInsurancePro()+"产生防伪码，请前往相应页面生成");
                }else if (operation.equals("绑定地址")){
                    LOGGER.info("绑定用户地址");
                    AuthManage authManage1 = new AuthManage();
                    authManage1.setAddress(toAddress);
                    authManage1.setMid(tmpid);
                    authManageMapper.upSysAuthManageStatus(authManage1);
                    result.put("msg", "绑定成功");
                }else if (operation.equals("挂接")){
                    LOGGER.info("挂接");
                    AuthManage authManage1 = new AuthManage();
                    authManage1.setStatus(1);
                    authManage1.setMid(tmpid);
                    authManageMapper.upSysAuthManageStatus(authManage1);
                    result.put("msg", "挂接成功");
                }
            }
            primeTxExample.setPayStatus(1);
            primeTxService.addPrimeTx(primeTxExample);
        }catch (Exception e){
            LOGGER.info("error"+e.getMessage());
            result.put("success", false);
            result.put("msg", "error"+e.getMessage());
            primeTxExample.setPayStatus(0);
            primeTxService.addPrimeTx(primeTxExample);
            return result;
        }
//        result.put("txData",map);
        result.put("success", true);
        return result;
    }
    @RequestMapping("getFiledsByUserAddr")
    @ResponseBody
    public JSONObject getFiledsByUserAddr(HttpServletRequest request, HttpServletResponse response ) {
        JSONObject result = new JSONObject();
        int roleId = WwSystem.ToInt(request.getParameter("roleId"));
        if (roleId<0){
            result.put("success", false);
            result.put("msg", "roleId is null");
            return result;
        }
//        CustStrategyInfo custStrategyInfo = custStrategyInfoMapper.selectStrateList(roleInfo);
//        if (custStrategyInfo==null||custStrategyInfo.equals("")){
//            result.put("success", false);
//            result.put("msg", "result is null");
//            return result;
//        }
//        result.put("strategyInfo",custStrategyInfo);
        List<CustStrategy> custFiledsList = custStrategyMapper.selectStrateAll(roleId);
        if (custFiledsList.size()<0){
            result.put("success", false);
            result.put("msg", "result is null");
            return result;
        }
        result.put("strates",custFiledsList);
        result.put("success", true);
        return result;
    }

    @RequestMapping("getStrateList")
    @ResponseBody
    public JSONObject getStrateList(HttpServletRequest request, HttpServletResponse response ) {
        JSONObject result = new JSONObject();
        String roleInfo = WwSystem.ToString(request.getParameter("roleInfo"));
        if (roleInfo.equals("") || roleInfo.length()<0){
            result.put("success", false);
            result.put("msg", "roleId is null");
            return result;
        }
        List<CustStrategyInfo> custStrategyInfoList = custStrategyInfoMapper.selectStrateList(roleInfo);
        if (custStrategyInfoList.size()<0){
            result.put("success", false);
            result.put("msg", "result is null");
            return result;
        }
        result.put("strateList",custStrategyInfoList);
        result.put("success", true);
        return result;
    }

    @RequestMapping("createAddr")
    @ResponseBody
    public JSONObject createAddr(HttpServletRequest request, HttpServletResponse response ) {
        JSONObject  res = new JSONObject();
        String addrName = WwSystem.ToString(request.getParameter("addrName"));
        if (addrName.equals("")|| addrName.length()<0){
            res.put("success", false);
            res.put("msg", "addrName is null");
            return res;
        }
        NrcMain nm =new NrcMain();
        nm.initRPC();
        String accountAddress = nm.getAccountAddress(addrName);
        if (accountAddress==null){
//            String s = sendForAddr("1QA68c6yxPZSJM6MDasjc1UsRp6pC84Jis", 1);
            String param="toAddress=1Nx69PCDKL9z7rU8Gq1QrW4gnLMcy7MHvm&assetId=999999&amount=1";
            String s = HttpRequestUtil.sendPost("http://127.0.0.1:9590/sendToAddress", param, true);
            LOGGER.error("发送交易:"+s+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            res.put("success", false);
            res.put("msg", " addr pool overflow,please reflesh and try again");
            return res;
        }
        LOGGER.info("账户:"+addrName+"产生地址:"+accountAddress+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        res.put("accountAddress",accountAddress);
        res.put("success",true);
        return res;
    }

    //sendtoAddress 普通交易
    @RequestMapping("sendToAddress")
    @ResponseBody
    public JSONObject  sendToAddress(HttpServletRequest request, HttpServletResponse response){
        JSONObject result = new JSONObject();
//        String fromAddress = WwSystem.ToString(request.getParameter("productAddress"));
        String toAddress = WwSystem.ToString(request.getParameter("toAddress"));
//        String busiJson = WwSystem.ToString(request.getParameter("busiJson"));
        int amount = WwSystem.ToInt(request.getParameter("amount"));
        if (toAddress.equals("")|| toAddress.length()<0){
            result.put("success", false);
            result.put("msg", "address is null");
            return result;
        }
        if (amount<=0){
            amount = 10;
        }
        String txId = sendForAddr(toAddress, amount);
        if (txId.equals("")){
            result.put("success", false);
            result.put("msg", "tx false");
            return result;
        }
        result.put("success", false);
        result.put("msg", "tx success");
        result.put("data", txId);
        return result;
    }

    private String  sendForAddr(String toAddress,int amount){
        String param = "toAddress="+toAddress+"&assetId=999999&amount="+amount;;
        String s = HttpRequestUtil.sendPost("http://127.0.0.1:9590/sendToAddress", param, true);
        if (s==null||s.equals("")){
            return "";
        }
        JSONObject jsonObject = JSONObject.parseObject(s);
        String txId = (String) jsonObject.get("txId");
        return txId;
    }



    private JSONObject  getTxInfoByTxId(String param_txId){
        JSONObject res = new JSONObject();
        String s = WwHttpRequest.sendGet("http://127.0.0.1:9590/getRawTxBusiData", param_txId);
        JSONObject jsonObject1 = JSONObject.parseObject(s);
        JSONObject data1 = null;
        int amount1 = 0;
       if (Integer.parseInt(JSONObject.toJSONString(jsonObject1.get("code")))==1){
            res.put("getRawTxBusiData","listBusiData");
            String ss = WwHttpRequest.sendGet("http://127.0.0.1:9590/listBusiData", param_txId);
            JSONObject jsonObjectss = JSONObject.parseObject(ss);
            JSONArray data = (JSONArray) jsonObjectss.get("data");
            JSONObject o = (JSONObject) data.get(0);
            amount1 = Integer.parseInt(JSONObject.toJSONString(o.get("Amount")));
            data1 = (JSONObject) o.get("Data");
        }else {
            res.put("getRawTxBusiData","getRawTxBusiData");
            JSONObject data = (JSONObject) jsonObject1.get("data");
            amount1 = Integer.parseInt(JSONObject.toJSONString(data.get("Amount")));
            data1 = (JSONObject) data.get("Data");
        }
        res.put("data1",data1);
        res.put("amount1",amount1);
        return   res;
    }



    //双交易
    @RequestMapping("transferTest")
    @ResponseBody
    public JSONObject transferTest(HttpServletRequest request, HttpServletResponse response ){
        JSONObject result = new JSONObject();
        String fromAddress = WwSystem.ToString(request.getParameter("productAddress"));
        String toAddress = WwSystem.ToString(request.getParameter("address"));
        String txId = WwSystem.ToString(request.getParameter("toTxData"));
        String busiJson = WwSystem.ToString(request.getParameter("busiJson"));
        int amount = WwSystem.ToInt(request.getParameter("amount"));
        String  param_txId = "txId="+txId;
        Map<String,Object> map = new  HashMap<>();
        Map<String,Object> map1 = new  HashMap<>();

        try {
            if (txId.equals("")){
                result.put("aaaqwqwdf",2222);
            }
            if (!StringUtils.isEmpty(txId)){


                String param1 = "fromAddress=1ojviBeqSqasEKRWqifwLwwP5MzJN181y&toAddress=" + toAddress + "&assetId=999999&amount=" + 1 + "&tag=1&busiJson=" + null;
                String  s = HttpRequestUtil.sendPost("http://127.0.0.1:9590/doTxWidthBusiData", param1, true);
                if (s!=null){
                    JSONObject jsonObject2 = JSONObject.parseObject(s);
                    String txId2 = (String) jsonObject2.get("txId");
                    map.put("result",txId2);
                }
                result.put("ssssss",new Date().getTime()+"s:"+s);
                result.put("timebefore",new Date().getTime());
                Thread.sleep(5000);
                result.put("timeafter",new Date().getTime());
                result.put("sswwss",new Date().getTime()+"s:"+s);

                String param = "fromAddress=1ojviBeqSqasEKRWqifwLwwP5MzJN181y&toAddress=19KLLe9ezsLS1r1yGnxjWn1kGEcx3BpKmh&assetId=999999&amount="+amount+"&tag=1&busiJson=" + busiJson;
                String s1 = HttpRequestUtil.sendPost("http://127.0.0.1:9590/doTxWidthBusiData", param, true);
                result.put("s123123",new Date().getTime()+"s1:"+s1);
                if (s1!=null){
                    JSONObject jsonObject2 = JSONObject.parseObject(s1);
                    String txId2 = (String) jsonObject2.get("txId");
                    map.put("result1",txId2);
                }else {
                    map1.put("message1","交易2失败");
                    map.put("error1",map1);
                    result.put("success",false);
                    result.put("msg","交易2失败");
                    result.put("txData1",map);
                    return result;
                }


        } else {
                String param = "fromAddress=1ojviBeqSqasEKRWqifwLwwP5MzJN181y&toAddress=" + toAddress + "&assetId=999999&amount="+amount+"&tag=1&busiJson=" + busiJson;
                String s1 = HttpRequestUtil.sendPost("http://127.0.0.1:9590/doTxWidthBusiData", param, true);
                if (s1!=null){
                    JSONObject jsonObject2 = JSONObject.parseObject(s1);
                    String txId2 = (String) jsonObject2.get("txId");
                    map.put("result",txId2);
                }else {
                    map1.put("message","交易2失败");
                    map.put("error",map1);
                    result.put("success",false);
                    result.put("msg","交易2失败");
                    result.put("txData",map);
                    return result;
                }
            }
        }catch (InterruptedException e1) {
//        e1.printStackTrace();
            result.put("success", false);
            result.put("msg", "error"+e1.getMessage());
            return result;
    }   catch (Exception e){
//            e.printStackTrace();
            result.put("success", false);
            result.put("msg", "error"+e.getMessage());
            return result;
        }
        result.put("txData",map);
        result.put("success", true);
        return result;
    }


    //带数据的交易
    @RequestMapping("transfer3")
    @ResponseBody
    public JSONObject transfer3(HttpServletRequest request, HttpServletResponse response ){
        JSONObject result = new JSONObject();
        String fromAddress = WwSystem.ToString(request.getParameter("productAddress"));
        String toAddress = WwSystem.ToString(request.getParameter("address"));
        String busiJson = WwSystem.ToString(request.getParameter("busiJson"));
        int amount = WwSystem.ToInt(request.getParameter("amount"));
        if (fromAddress=="" || fromAddress.length()<0||fromAddress==null){
            result.put("success", false);
            result.put("msg", "fromAddress is null");
            return result;
        }
        if (toAddress==""|| toAddress.length()<0||toAddress==null){
            result.put("success", false);
            result.put("msg", "address is null");
            return result;
        }
        if (amount<=0){
            amount = 10;
        }
        String  param="fromAddress=1ojviBeqSqasEKRWqifwLwwP5MzJN181y&toAddress="+toAddress+"&assetId=999999&amount=10&tag=1&busiJson="+busiJson;
        String txId = HttpRequestUtil.sendPost("http://127.0.0.1:9590/doTxWidthBusiData", param, true);
        JSONObject jsonObject = JSONObject.parseObject(txId);
        String txId1 = (String) jsonObject.get("txId");
        String message = (String) jsonObject.get("message");
        Map<String,Object> map = new  HashMap<>();
        Map<String,Object> map1 = new  HashMap<>();
        if (txId!=null||txId.equals("")){
            map.put("result",txId1);
            map.put("error",null);
        }else {
            map1.put("message",message);
            map.put("error",map1);
            result.put("success",false);
            result.put("msg","交易失败");
            result.put("txData",map);
            return result;
        }
        result.put("txData",map);
        result.put("success", true);

        return result;
    }

    @RequestMapping(value = "transfer1")
    @ResponseBody
    public JSONObject transfer1(HttpServletRequest request, HttpServletResponse response ){
        JSONObject result = new JSONObject();
        String proAddress = WwSystem.ToString(request.getParameter("productAddress"));
        String address = WwSystem.ToString(request.getParameter("address"));
        Integer amount = WwSystem.ToInt(request.getParameter("amount"));
        if (proAddress=="" || proAddress.length()<0||proAddress==null){
            result.put("success", false);
            result.put("msg", "product is null");
            return result;
        }
        if (address==""|| address.length()<0||address==null){
            result.put("success", false);
            result.put("msg", "address is null");
            return result;
        }
        result.put("toAddress",address);
        result.put("fromAddress",proAddress);
        Product product = productService.getTransferInfo(proAddress);
        int assetId = product.getAssetId();
        if (amount<=0||amount==null){
            amount = product.getReward();
        }

        String  num = assetId + ":" + amount;
        NrcMain nm=new NrcMain();
        String txId ="";
        Map<String,Object> map = new  HashMap<>();
        Map<String,Object> map1 = new  HashMap<>();
        nm.initRPC();
        txId = nm.sendToAddress(address, num, null, null);
        if (txId!=null){
            map.put("result",txId);
            map.put("error",null);
        }else {
            map1.put("message","txId is null");
            map.put("error",map1);
            result.put("success",false);
            result.put("msg","交易失败");
            result.put("txData",map);
            return result;
        }
        result.put("txData",map);
        result.put("success", true);

        return result;
    }

    /**14wsJoKvHo3se3RAURZVQno6heF12PZJ6K
     * 1GwcYyryJdhZpk2vPi7PM2XHdsaAuqQDbR
     *1HgCrBrmqapweFNmoxDChSb3YdXyMC6cee
     *1Cw7bJ7BiANBLVKt5eovYVayzSBgh4pLeJ
     */
    @RequestMapping("sendMany")
    @ResponseBody
    public JSONObject  sendMany(HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        String manyAddress = WwSystem.ToString(request.getParameter("manyAddress"));
        NrcMain nm=new NrcMain();
        String manyTxid="";
        Object account="";
        res.put("success",true);
        res.put("msg","成功");
        try{
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            account= nm.getAccount(primeaddr);
            manyTxid = nm.sendMany(account.toString(), manyAddress);
        }catch (Exception e){
            e.getMessage();
            res.put("msg","交易失败");
            res.put("success",false);
            return res;
        }
        res.put("account", account);
        res.put("manyTxid",manyTxid);
        return  res;
    }

   @RequestMapping("quartzTest")
    @ResponseBody
    public JSONObject  quartzTest(HttpServletRequest request, HttpServletResponse response){
       JSONObject res = new JSONObject();
       res.put("success",true);
       res.put("msg","成功");
       String  param="address=1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
       String txId = HttpRequestUtil.sendGet("http://127.0.0.1:9590/listBusiDataByFromAddress", param);
       JSONObject jsonObject = JSONObject.parseObject(txId);
       String s = JSONObject.toJSONString(jsonObject.get("data"));
       List<SysTxTest> sysTxTests = JSONObject.parseArray(s, SysTxTest.class);
       res.put("count",sysTxTests.size());
       res.put("sysTxTests",sysTxTests);
       return res;
   }

   private JSONObject createProduct(HttpServletRequest request,int id,String address) throws Exception{
        JSONObject  res = new JSONObject();
       Product product = authProductMapper.selProductById(id);
       LOGGER.info("PRODUCT:"+product.toString());
       String sname = product.getSname();
       NrcMain nm =new NrcMain();
       nm.initRPC();
       MajorLog  ml= new MajorLog();
       ml.setMember(address);
       String localAddr = request.getLocalAddr();
       ml.setRemoteAddr(localAddr);
       ml.setNodeAddress(product.getPrimeAddr());
       ml.setMethod(request.getRequestURL().toString());
       ml.setOperatime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
       String accountAddress = nm.getAccountAddress(sname);
       LOGGER.info("生成产品地址："+accountAddress);
       ml.setOperation(address+"产生产品："+sname+"，产品地址："+accountAddress);
       if (accountAddress==null){
           String param="toAddress=1Nx69PCDKL9z7rU8Gq1QrW4gnLMcy7MHvm&assetId=999999&amount=1";
           String s = HttpRequestUtil.sendPost("http://127.0.0.1:9590/sendToAddress", param, true);
           ml.setOperation("账号超出，向1Nx69PCDKL9z7rU8Gq1QrW4gnLMcy7MHvm打一个币，节点地址："+product.getPrimeAddr());
           LOGGER.error("发送交易:"+s+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
       }
       product.setProAddress(accountAddress);
       authProductMapper.upProduct(product);

       Map<String,String> map =  new HashMap<>();
       String params = "";
       String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
       String primeAddr = "1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
       String assetId = "999999";
       String proThumbnail = product.getProThumbnail();
       if (proThumbnail!=null){
           map.put("proThumbnail",proThumbnail);
           params += "&proThumbnail="+proThumbnail;
       }
       String proName = product.getProName();
       if (proName!=null){
           map.put("proName",proName);
           params += "&proName="+proName;
       }
       if (sname!=null){
           map.put("sname",sname);
           params += "&sname="+sname;
       }
       String proDisplay = product.getProDisplay();
       if (proDisplay!=null){
           map.put("proDisplay",proDisplay);
           params += "&proDisplay="+proDisplay;
       }
       String proAddress = product.getProAddress();
       if (proAddress!=null){
           map.put("proAddress",proAddress);
           params += "&proAddress="+proAddress;
       }
       String proDescribe = product.getProDescribe();
       if (proDescribe!=null){
           map.put("proDescribe",proDescribe);
           params += "&proDescribe="+proDescribe;
       }
       String s2 = String.valueOf(product.getReward());
       if (s2!=null&&s2!=""){
           map.put("reward",s2);
           params += "&reward="+s2;
       }
     /*  String s = String.valueOf(product.getMedalId());
       if (s!=null&&s!=""){
           map.put("medalId",s);
           params += "&medalId="+s;
       }*/
       String rewardWhere = product.getRewardWhere();
       if (rewardWhere!=null){
           map.put("rewardWhere",rewardWhere);
           params += "&rewardWhere="+rewardWhere;
       }
       String createTime = product.getCreateTime();
       if (createTime!=null){
           map.put("createTime",createTime);
           params += "&createTime="+createTime;
       }
           map.put("status","1");
           params += "&status=1";

       String s4 = String.valueOf(product.getProNum());
       if (s4!=null&&s4!=""){
           map.put("proNum",s4);
           params += "&proNum="+s4;
       }
       String producer = product.getProducer();
       if (producer!=null){
           map.put("producer",producer);
           params += "&producer="+producer;
       }
       map.put("primeAddr",primeAddr);
       map.put("assetId",assetId);
       params += "&primeAddr="+product.getPrimeAddr();
       params += "&assetId="+product.getAssetId();
       String proAdvertImg = product.getProAdvertImg();
       if (proAdvertImg!=null){
           map.put("proAdvertImg",proAdvertImg);
           params += "&proAdvertImg="+proAdvertImg;
       }
       String extraInfo = product.getExtraInfo();
       if (extraInfo!=null){
           map.put("extraInfo",extraInfo);
           params += "&extraInfo="+extraInfo;
       }
       String productLicense = product.getProductLicense();
       if (productLicense!=null){
           map.put("productLicense",productLicense);
           params += "&productLicense="+productLicense;
       }
       String productSize = product.getProductSize();
       if (productSize!=null){
           map.put("productSize",productSize);
           params += "&productSize="+productSize;
       }
       String qualityPeriod = product.getQualityPeriod();
       if (qualityPeriod!=null){
           map.put("qualityPeriod",qualityPeriod);
           params += "&qualityPeriod="+qualityPeriod;
       }
       String classify = product.getClassify();
       if (classify!=null){
           map.put("classify",classify);
           params += "&classify="+classify;
       }

       String sign = ApiUtil.getSign(map);
       System.out.println("sign："+sign);
       String url = "api/addProduct";
       params += "&signCode="+sign;
       if (params.startsWith("&")){
           params.replaceFirst("&","");
       }
       String s1 = ApiUtil.sendPost(url, params);
       JSONObject jsonObject = JSON.parseObject(s1);
       res.put("success",jsonObject.get("status"));
       res.put("message",jsonObject.get("result"));
       return res;
   }


    private   JSONObject  doTxWidthBusiData(String toAddress, int assetId,int amount1,String busiJson){
        JSONObject res =new  JSONObject();
        String stringCallable = "";
        synchronized (this){
            String param1 = "fromAddress=1ojviBeqSqasEKRWqifwLwwP5MzJN181y&toAddress=" + toAddress + "&assetId="+assetId+"&amount="+amount1+"&tag=1&busiJson=" + busiJson;
            stringCallable = HttpRequestUtil.sendPost("http://127.0.0.1:9590/doTxWidthBusiData", param1, true);
            String txId1 = "";
            if (!stringCallable.equals("")&&!StringUtils.isEmpty(stringCallable)){
                JSONObject jsonObject = JSONObject.parseObject(stringCallable);
                txId1 = (String) jsonObject.get("txId");
                res.put("message",txId1);
                res.put("success",true);
                return    res;
            }
        }
        res.put("message",stringCallable);
        res.put("success",false);
        return res;
    }
    private JSONObject  getRawTxBusiData(String param_txId){
        JSONObject res = new JSONObject();
        JSONObject data1 = null;
        int amount1 = 0;
        while (true) {
            String s = WwHttpRequest.sendGet("http://127.0.0.1:9590/getRawTxBusiData", param_txId);
            JSONObject jsonObject1 = JSONObject.parseObject(s);
            if (Integer.parseInt(JSONObject.toJSONString(jsonObject1.get("code"))) == 0) {
                JSONObject data = (JSONObject) jsonObject1.get("data");
                if (data!=null){
                    amount1 = Integer.parseInt(JSONObject.toJSONString(data.get("Amount")));
                    data1 = (JSONObject) data.get("Data");
                    break;
                }
            }
        }
        res.put("data1",data1);
        res.put("amount1",amount1);
        return   res;
    }

    //********************************
    @RequestMapping("sendMsgTest")
    @ResponseBody
    public JSONObject sendMsgTest(String mobile){
        JSONObject res = new JSONObject();
        String msg = "【NRC】您的验证码是1111";
        res.put("mobile",mobile);
        String s = singleSendSms(msg, mobile);
        res.put("sms",s);
        String s1 = singleSendus(msg, mobile);
        res.put("us",s1);
        try {
            String encode = URLEncoder.encode(mobile, "UTF-8");
            String s2 = singleSendSms(msg, encode);
            res.put("smsen",s2);
            String s3 = singleSendus(msg, encode);
            res.put("usen",s3);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    @RequestMapping("sendMsgTestTpl")
    @ResponseBody//【NRC】 Your verification code is 9377
    public JSONObject sendMsgTestTpl(String mobile){
        JSONObject res = new JSONObject();
        String msg = "【NRC】Your NRC verification code is: 9377";
        res.put("mobile",mobile);
        String s = singleSendSms(msg, mobile);
        res.put("sms",s);
        String s1 = singleSendus(msg, mobile);
        res.put("us",s1);

        String s5 = singleSendSms(msg, "urlencode("+mobile+")");
        res.put("sms1",s5);
        String s4 = singleSendus(msg, "urlencode("+mobile+")");
        res.put("us1",s4);

        try {
            String encode = URLEncoder.encode(mobile, "UTF-8");
            String s2 = singleSendSms(msg, encode);
            res.put("smsen",s2);
            String s3 = singleSendus(msg, encode);
            res.put("usen",s3);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    private static String ENCODING = "UTF-8";
    private static String apikey = "e50da878f115a22f9031df0b0ffe9214";

    public static String singleSendSms( String text, String mobile) {
        Map<String, String> params = new HashMap<>();//请求参数集合
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return post("https://sms.yunpian.com/v2/sms/single_send.json", params);//请自行使用post方式请求,可使用Apache HttpClient
    }
    public static String singleSendus( String text, String mobile) {
        Map<String, String> params = new HashMap<>();//请求参数集合
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return post("https://us.yunpian.com/v2/sms/single_send.json", params);//请自行使用post方式请求,可使用Apache HttpClient
    }
    //云片短信

    public static String post(String url, Map < String, String > paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List <NameValuePair> paramList = new ArrayList <
                        NameValuePair > ();
                for (Map.Entry < String, String > param: paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(),
                            param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList,
                        ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, ENCODING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }


    @ResponseBody
    @RequestMapping("getMonth")
    public JSONObject  getMonth(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("code",0);
        res.put("success",true);
        try{
            String picker = WwSystem.ToString(request.getParameter("picker"));
            if (StringUtils.isEmpty(picker)){
                res.put("code",2);
                res.put("success",false);
                return res;
            }
            int month = WwSystem.ToInt(request.getParameter("month"));
            Date date = DateUtil.strToDateShort(picker);
            String nextMonth = DateUtil.getNextMonth2(date, month);
            res.put("picker",picker);
            res.put("month",month);
            res.put("resMonth",nextMonth);
        }catch (Exception e){
            res.put("code",1);
            res.put("success",false);
            return res;
        }
        return res;
    }

    public  boolean  Valid(String address,String uri,HttpServletRequest request, HttpServletResponse response){

        List<SysManage> list =sysManageMapper.selSysManageListByAddr(address);
        LOGGER.info("管理权限："+list);
        if (list.size()<=0){
            List<SysCarteExample> list1 = sysCarteMapper.getSysCartes(address);
            LOGGER.info("菜单列表："+list1);
            if (list1.size()<=0){
                return false;
            }else {
                for (int j=0;j<list1.size();j++){
                    if (uri.contains(list1.get(j).getHref())){
                        return true;
                    }
                }
            }
        }else {
            for (int i=0;i<list.size();i++){
                SysModule sysModule = sysModuleService.moduleInfo(list.get(i).getManageAddr());
                LOGGER.info("模块权限："+sysModule);
                if (uri.contains(sysModule.getPerms())){
                    return true;
                }
            }
        }

        return true;
    }

    public static void  main(String[] args) {
        SysManage  sysManage = new SysManage();
        if (sysManage==null){
            System.out.println("****");
        }
        sysManage.setStatus(1);
        sysManage.setSname("asdfasdfa");
        if (sysManage!=null){
            System.out.println("------");
        }
    }
}
