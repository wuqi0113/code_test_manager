package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.mapper.AuthProductMapper;
import com.chainfuture.code.mapper.SignMsgMapper;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.service.AuthProductService;
import com.chainfuture.code.service.MajorLogService;
import com.chainfuture.code.utils.ApiUtil;
import com.chainfuture.code.utils.DownloadTxt;
import com.chainfuture.code.utils.HttpRequestUtil;
import com.chainfuture.code.utils.IPUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Controller
@RequestMapping("/authproduct/")
public class AuthProductController {

    @Autowired
    private AuthProductService authProductService;
    @Autowired
    private SysManageMapper sysManageMapper;
    @Autowired
    private AuthProductMapper authProductMapper;
    @Autowired
    private MajorLogService majorLogService;
    @Autowired
    private SignMsgMapper  signMsgMapper;
    //引入apache的log4j日志包
    public static final Logger LOGGER = Logger.getLogger(ApiController.class);

    @ResponseBody
    @RequestMapping("toQRCodePage/{id}")
    public ModelAndView  toQRCodePage(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        Product  model=new Product();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        try{
            ProductExample  product = authProductService.getProductById(id);
            if (product==null){
                mv.addObject("code",1);
                mv.addObject("msg","产品不存在");
                return mv;
            }
            mv.addObject("data",product);
        }catch (Exception e){
            e.printStackTrace();
            mv.addObject("code", 1);
            mv.addObject("msg", "失败");
            return mv;
        }
        mv.setViewName("see/product/productQRcode");
        return  mv;
    }

    @RequestMapping("toBatchAddPage")
    @ResponseBody
    public ModelAndView toBatchAddPage(Integer id,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
        try{
            ProductExample  product = authProductService.getProductById(id);
            if (product==null){
                mv.addObject("code",1);
                mv.addObject("msg","产品不存在");
                return mv;
            }
            Product pro = new Product();
            pro.setSname(product.getSname());
            int i = authProductService.getBatchCount(pro);
            int  a = i+1;
            product.setSname(product.getSname()+"--"+a);
            product.setProAddress("");
            mv.addObject("data",product);
        }catch (Exception e){
            mv.addObject("code",1);
            mv.addObject("msg","失败");
            return mv;
        }
        mv.setViewName("see/product/productAdd");
        return mv;
    }
    @RequestMapping("toBatchList")
    @ResponseBody
    public ModelAndView  toBatchList(Integer id, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        try{
            ProductExample  product = authProductService.getProductById(id);
            if (product==null){
                mv.addObject("code",1);
                mv.addObject("msg","产品不存在");
                return mv;
            }
            mv.addObject("product",product);
        }catch (Exception e){
            mv.addObject("code",1);
            mv.addObject("msg","失败");
            return mv;
        }
        mv.setViewName("see/product/productBatchList");
        return mv;
    }

    @ResponseBody
    @RequestMapping("productBatchList")
    public JSONObject  productBatchList(Product product, HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        try{
            List<Product> list = authProductService.getBatchList(product);
            res.put("data",list);
            int count = authProductService.getBatchCount(product);
            res.put("count",count);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }
    @RequestMapping("getProductById/{id}")
    @ResponseBody
    public ModelAndView  getProductById(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        ModelAndView  mv = new ModelAndView();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        try{
            ProductExample  product = authProductService.getProductById(id);
            if (product==null){
                mv.addObject("code",1);
                mv.addObject("msg","产品不存在");
                return mv;
            }
            mv.addObject("data",product);
        }catch (Exception e){
            mv.addObject("code",1);
            mv.addObject("msg","失败");
            return mv;
        }
        mv.setViewName("see/product/productEdit");
        return mv;
    }

    @ResponseBody
    @RequestMapping("checkSname/{sname}")
    public JSONObject  checkSname(@PathVariable("sname") String sname, HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        try{
            ProductExample  product = authProductService.selBySname(sname);
            if (product!=null){
                res.put("code",0);
                res.put("msg","英文名称不允许重复");
                return res;
            }
        }catch (Exception e){
            res.put("code",0);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }

    @RequestMapping("toAddPage")
    @ResponseBody
    public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
        mv.setViewName("see/product/productAdd");
        return mv;
    }

    @ResponseBody
    @RequestMapping("toProductList")
    public ModelAndView toProductList(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv =new ModelAndView();
        mv.setViewName("see/product/productList");
        return mv;
    }
    @RequestMapping("productList")
    @ResponseBody  //'signCode','timeStamp','appId','primeAddr','pageIndex','limit'
    public JSONObject productList(Product product,HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        try{
            int   productCount = authProductService.getProductCount();
            res.put("count", productCount);
            //查询所有
           List<Product> result = authProductService.selProductList(product);
            res.put("data",result);
        }catch (Exception e){
            e.getMessage();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("getSignMsgList")
    public JSONObject  getSignMsgList(SignMsg signMsg, HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<SignMsg> list = null;
        int count = 0;
        try{
            list = signMsgMapper.selSignMsgList(signMsg);
            res.put("data",list);
            count = signMsgMapper.selSignMsgCount(signMsg);
            res.put("count",count);
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("toCreateCodePage/{sname}")
    public ModelAndView  toCreateCodePage(@PathVariable("sname") String sname, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        Product  model=new Product();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        Map<String,String> map =  new HashMap<>();
        String  address = "1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
        map.put("primeAddr",address);
        map.put("sname",sname);
        try{
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            String url = "api/getProductBySname";
            String params="primeAddr="+address;
            params += "&signCode="+sign;
            params += "&sname="+sname;
            String s1 = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s1);
            Object result = jsonObject.get("result");
            if (result!=null){
                mv.addObject("data", result);
                mv.setViewName("page/product_createCode");
            }
        }catch (Exception e){
            e.printStackTrace();
            mv.addObject("code", 1);
            mv.addObject("msg", "失败");
        }
        return  mv;
    }

    @RequestMapping("batchSignMsg")
    @ResponseBody
    public  void  batchSignMsg(Long  signId,Integer num,String proAddress,String sname,HttpServletRequest request, HttpServletResponse response) {
        if (num!=null && proAddress!=null&&sname!=null){
            if (signId==null){
                 signId = new Date().getTime();
            }
            ProductThread pt=new ProductThread(sname,proAddress,num,signId,1,request,response);
            List<String> proList=new ArrayList<>();
            for (int i=0;i<5;i++){
                FutureTask<List<String>> lt = new FutureTask<>(pt);
                new Thread(lt,"线程"+i).start();
                try {
                    proList.addAll(lt.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            String join = StringUtils.join(proList, ",");
            int i = join.indexOf("&o=id:");
            int ii = join.lastIndexOf("&o=id:");
            String substring = join.substring(i + 6, i + 19);
            String substring1 = join.substring(ii + 6, ii + 19);
            DownloadTxt dt=new DownloadTxt();
            dt.exportTxt(response,join,sname+substring+"~"+substring1);
            MajorLog ml = new MajorLog();
            Object admin = request.getSession().getAttribute("admin");
            ml.setMember(admin.toString());
            String remoteAddr = IPUtils.getRemoteAddr(request);
            ml.setRemoteAddr(remoteAddr);
            ml.setNodeAddress(proAddress);
            ml.setMethod(request.getRequestURL().toString());
            ml.setOperatime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            ml.setOperation("批量产生二维码信息："+sname+substring+"~"+substring1);
            majorLogService.insertMajorLog(ml);
        }
    }

    @RequestMapping("addCode")
    @ResponseBody
    public  JSONObject  addCode(String proAddress,Long  signId,
                                HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        String signMessage=null;
        String originMessage = "";
        String message=null;
        String sname="";
        MajorLog ml = new MajorLog();
        try {
            Object admin = request.getSession().getAttribute("admin");
            ml.setMember(admin.toString());
            String remoteAddr = IPUtils.getRemoteAddr(request);
            ml.setRemoteAddr(remoteAddr);
            ml.setNodeAddress(proAddress);
            ml.setMethod(request.getRequestURL().toString());
            ml.setOperatime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            NrcMain  nm = new NrcMain();
            nm.initRPC();
            originMessage = "id:"+signId;
            signMessage = nm.signMessage(proAddress,originMessage);
            LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            message = "https://reitschain.com/co?s="+signMessage+"&o="+originMessage+"&a="+proAddress;
            ml.setOperation("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            majorLogService.insertMajorLog(ml);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        String substring = sname+signId;
        res.put("message",message);
        res.put("codeId",substring);
        return res;
    }



    @RequestMapping("toAddProductBacth/{sname}")
    @ResponseBody
    public ModelAndView toAddProductBacth(@PathVariable("sname")String sname, HttpServletRequest request, HttpServletResponse response){
        ModelAndView res = new ModelAndView();
        res.addObject("code", 0);
        res.addObject("msg", "成功");
        try{
            Map<String,String> map =  new HashMap<>();
            map.put("sname",sname);
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            String url = "api/getBatchCount";
            String params="signCode="+sign;
            params += "&sname="+sname;
            String s = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s);
            int snameCount = Integer.valueOf(JSONObject.toJSONString(jsonObject.get("result")))+1;
            String  address = "1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
            map.put("primeAddr",address);
            String signPro = ApiUtil.getSign(map);
            System.out.println("sign："+signPro);
            String urlPro = "api/getProductBySname";
            String paramsPro="primeAddr="+address;
            paramsPro += "&signCode="+signPro;
            paramsPro += "&sname="+sname;
            String s1 = ApiUtil.sendPost(urlPro, paramsPro);
            JSONObject jsonObject1 = JSON.parseObject(s1);
            Object result = jsonObject1.get("result");
            res.addObject("data",result);
            sname=sname+"--"+snameCount;
            res.addObject("sname",sname);
        }catch (Exception e){
            e.printStackTrace();
            res.addObject("code", 1);
            res.addObject("msg", "失败");
        }
        res.setViewName("page/product_batch_add");
        return  res;
    }


    @RequestMapping("getProductBatchList")
    @ResponseBody  //'signCode','timeStamp','appId','primeAddr','pageIndex','limit'
    public JSONObject getProductBatchList(Product product,HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        int pageIndex = product.getPageIndex();
        int limit = product.getLimit();
        Map<String,String> map =  new HashMap<>();
        map.put("sname",product.getSname());
        try{
            //查询总数
            String signCount = ApiUtil.getSign(map);
            System.out.println("sign："+signCount);
            String urlCount = "api/getBatchCount";
            String paramsCount="signCode="+signCount;
            paramsCount += "&sname="+product.getSname();
            String s = ApiUtil.sendPost(urlCount, paramsCount);
            JSONObject jsonObjectCount = JSON.parseObject(s);
            Object result1 = jsonObjectCount.get("result");
            res.put("count", result1);
            //查询所有
            map.put("pageIndex",String.valueOf(pageIndex));
            map.put("limit",String.valueOf(limit));
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            String url = "api/getProductListBySname";
            String params="sname="+product.getSname();
            params += "&signCode="+sign;
            params += "&pageIndex="+pageIndex;
            params += "&limit="+limit;
            String s1 = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s1);
            Object result = jsonObject.get("result");
            res.put("data",result);
        }catch (Exception e){
            e.getMessage();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }



    @RequestMapping("productList1")
    @ResponseBody  //'signCode','timeStamp','appId','primeAddr','pageIndex','limit'
    public JSONObject productList1(Product product,HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        int pageIndex = product.getPageIndex();
        int limit = product.getLimit();
        Map<String,String> map =  new HashMap<>();
        String  address = "1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
        map.put("primeAddr",address);
        try{
            //查询总数
            String signCount = ApiUtil.getSign(map);
            System.out.println("signCount："+signCount);
            String urlCount = "api/getProductCount";
            String paramCounts="primeAddr="+address;
                   paramCounts += "&signCode="+signCount;
            String count = ApiUtil.sendPost(urlCount, paramCounts);
            JSONObject jsonObjectCount = JSON.parseObject(count);
            JSONObject resultCount = (JSONObject) jsonObjectCount.get("result");
            Object productCount = resultCount.get("productCount");
            res.put("count", productCount);
            //查询所有
            map.put("pageIndex",String.valueOf(pageIndex));
            map.put("limit",String.valueOf(limit));
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            String url = "api/getProductList";
            String params="primeAddr="+address;
                   params += "&signCode="+sign;
                   params += "&pageIndex="+pageIndex;
                   params += "&limit="+limit;
            String s1 = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s1);
            Object result = jsonObject.get("result");
            res.put("data",result);
        }catch (Exception e){
            e.getMessage();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        return res;
    }


    @RequestMapping("toProBacthList/{sname}")
    @ResponseBody
    public ModelAndView toProBacthList(@PathVariable("sname")String sname, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView res = new ModelAndView();
        res.addObject("code", 0);
        res.addObject("msg", "成功");
        Map<String,String> map =  new HashMap<>();
        String  address = "1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
        map.put("primeAddr",address);
        map.put("sname",sname);
        try{
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            String url = "api/getProductBySname";
            String params="primeAddr="+address;
            params += "&signCode="+sign;
            params += "&sname="+sname;
            String s1 = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s1);
            Object result = jsonObject.get("result");
            if (result!=null){
                res.addObject("parentList", result);
                res.setViewName("page/product_batch_list");
            }
        }catch (Exception e){
            e.printStackTrace();
            res.addObject("code", 1);
            res.addObject("msg", "失败");
        }
        return res;
    }

    @RequestMapping("getProductByAddress/{proAddress}")
    @ResponseBody
    public ModelAndView getProductByAddress(@PathVariable("proAddress")String proAddress, HttpServletRequest request, HttpServletResponse response){
        ModelAndView res = new ModelAndView();
        res.addObject("code", 0);
        res.addObject("msg", "成功");
        try{
            if (proAddress!=null){
                Map<String,String> map =  new HashMap<>();
                map.put("proAddress",proAddress);
                String sign = ApiUtil.getSign(map);
                System.out.println("sign："+sign);
                String url = "api/getProductByAddress";
                String params="signCode="+sign;
                params += "&proAddress="+proAddress;
                String s1 = ApiUtil.sendPost(url, params);
                JSONObject jsonObject = JSON.parseObject(s1);
                Object result = jsonObject.get("result");
//                request.setAttribute("roles", result);
                res.addObject("data",result);
            }
        }catch (Exception e){
            e.printStackTrace();
            res.addObject("code", 1);
            res.addObject("msg", "失败");
        }
        res.setViewName("page/product_edit");
        return  res;
    }

    @RequestMapping("getProductBySname")
    @ResponseBody
    public JSONObject getProductBySname(String sname, HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "成功");
        Map<String,String> map =  new HashMap<>();
        String  address = "1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
        map.put("primeAddr",address);
        map.put("sname",sname);
        try{
            String sign = ApiUtil.getSign(map);
            System.out.println("sign："+sign);
            String url = "api/getProductBySname";
            String params="primeAddr="+address;
            params += "&signCode="+sign;
            params += "&sname="+sname;
            String s1 = ApiUtil.sendPost(url, params);
            JSONObject jsonObject = JSON.parseObject(s1);
            Object result = jsonObject.get("result");
            if (result!=null){
                res.put("code", 1);
                res.put("msg", "英文名称不允许重复");
                return  res;
            }
//            res.put("data",result);
        }catch (Exception e){
            e.printStackTrace();
            res.put("code", 1);
            res.put("msg", "失败");
        }
        return  res;
    }



    //修改产品
    @RequestMapping("updPro")
    @ResponseBody
    public  JSONObject updPro(Product product,HttpServletRequest request, HttpServletResponse response){
        JSONObject mv = new JSONObject();
        String fullRoot = WwSystem.getFullRoot(request);
        try {
            Map<String,String> map =  new HashMap<>();
            String  params = "";
            String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//            String primeAddr = "1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
            NrcMain nm = new NrcMain();
            nm.initRPC();
            String primeaddr = nm.getPrimeaddr();
            map.put("primeAddr",primeaddr);
            params += "&primeAddr="+primeaddr;
            String proThumbnail = product.getProThumbnail();
            if (proThumbnail!=null){
                proThumbnail =fullRoot+"public/file_list/images/"+proThumbnail;
                product.setProThumbnail(proThumbnail);
                map.put("proThumbnail",proThumbnail);
                params += "&proThumbnail="+proThumbnail;
            }
            String proName = product.getProName();
            if (proName!=null){
                map.put("proName",proName);
                params += "&proName="+proName;
            }
            String sname = product.getSname();
            if (sname!=null){
                map.put("sname",sname);
                params += "&sname="+sname;
            }
            map.put("assetId",product.getAssetId()+"");
            params += "&assetId="+product.getAssetId();
            String proDisplay = product.getProDisplay();
            if (proDisplay!=null){
                String s1 = proDisplay.replaceAll("src=\"public","src=\""+fullRoot+"public");
                product.setProDisplay(s1);
                map.put("proDisplay",s1);
                params += "&proDisplay="+s1;
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
            int reward = product.getReward();
            String s2 = "";
            if (reward<0){
                s2="0";
            }else {
                s2=reward+"";
            }
            map.put("reward",s2);
            params += "&reward="+s2;

          /*  String createTime1 = product.getCreateTime();
            if (createTime1!=null){
                map.put("createTime1",createTime1);
                params += "&createTime1="+createTime1;
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
            Integer status1 = product.getStatus();
            if (status1!=null){
                status1=1;
            }
            map.put("status",status1+"");
            params += "&status="+status1;
            int proNum = product.getProNum();
            if (proNum<0){
                proNum=0;
                product.setProNum(proNum);
            }
            map.put("proNum",proNum+"");
            params += "&proNum="+proNum;

            String producer = product.getProducer();
            Object admin = request.getSession().getAttribute("admin");
            if (producer!=null){
                map.put("producer",producer);
                params += "&producer="+producer;
            }else {
                map.put("producer",admin.toString());
                params += "&producer="+admin;
            }
            String proAdvertImg = product.getProAdvertImg();
            if (proAdvertImg!=null){
                proAdvertImg =fullRoot+"public/file_list/images/"+proAdvertImg;
                product.setProAdvertImg(proAdvertImg);
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
                classify =fullRoot+"public/file_list/images/"+classify;
                product.setClassify(classify);
                map.put("classify",classify);
                params += "&classify="+classify;
            }
            map.put("updateTime",updateTime);
            params += "&updateTime="+updateTime;
            product.setUpdateTime(updateTime);
            String   url = "api/updateProduct";
            String so = "";

            String sign = ApiUtil.getSign(map);
            params += "&signCode="+sign;
            if (params.startsWith("&")){
                so = params.replaceFirst("&","");
            }
            String s1 = ApiUtil.sendPost(url, so);
            LOGGER.info("s1"+s1);
            JSONObject jsonObject = JSON.parseObject(s1);
            String status = JSONObject.toJSONString(jsonObject.get("status"));
            if (status.equals("false")){
                mv.put("code", 1);
                mv.put("msg", "修改失败");
                return mv;
            }else {
                authProductMapper.upProduct(product);
            }
        } catch (Exception e) {
            e.getMessage();
            mv.put("code", 1);
            mv.put("msg", "失败，错误信息：" + e.getMessage());
            return mv;
        }
        mv.put("code",0);
        mv.put("msg", "修改成功");
        return mv;
    }

    //添加产品
    @RequestMapping("addPro")
    @ResponseBody
    public  JSONObject addPro(Product model,HttpServletRequest request, HttpServletResponse response){
        JSONObject mv = new JSONObject();
        String fullRoot = WwSystem.getFullRoot(request);
        try {
            SysManage sysManage = sysManageMapper.selSysManageByManageAddr("158Yh5k9C6K8sibvn9zxYhtaKvYMjLRtcE");
//            SysManage sysManage = sysManageMapper.selSysManageByManageAddr("1ojviBeqSqasEKRWqifwLwwP5MzJN181y");
            if (StringUtils.isEmpty(sysManage.getAddress())){
                mv.put("code", 1);
                mv.put("msg", "当前管理地址没有管理员" );
                return mv;
            }
            if (sysManage.getStatus()!=1){
                mv.put("code", 1);
                mv.put("msg", "当前管理员已失效" );
                return mv;
            }
            mv.put("sysManage",sysManage);
            String  classify=model.getClassify();
            if (classify!=null&&!classify.equals("")){
                classify =fullRoot+"public/file_list/images/"+classify;
                model.setClassify(classify);
            }
            String  proThumbnail=model.getProThumbnail();
            if (proThumbnail!=null&&!proThumbnail.equals("")){
                proThumbnail =fullRoot+"public/file_list/images/"+proThumbnail;
                model.setProThumbnail(proThumbnail);
            }
            String  proAdvertImg=model.getProAdvertImg();
            if (proAdvertImg!=null&&!proAdvertImg.equals("")){
                proAdvertImg =fullRoot+"public/file_list/images/"+proAdvertImg;
                model.setProAdvertImg(proAdvertImg);
            }
            String proDisplay = model.getProDisplay();
            if (proDisplay!=null&&!proDisplay.equals("")){
                String s = proDisplay.replaceAll("<p><span id=\"aaa\"></span></p>","").replaceAll("<p><br/></p>","");

                String s1 = s.replaceAll("src=\"public","src=\""+fullRoot+"public");
                model.setProDisplay(s1);
            }
            String updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            model.setCreateTime(updateTime);
            model.setStatus(2);
            authProductService.saveProduct(model);
            mv.put("authProduct",model);
        } catch (Exception e) {
            e.getMessage();
            mv.put("code", 1);
            mv.put("msg", "保存失败，错误信息：" + e.getMessage());
            return mv;
        }
        mv.put("code",0);
        mv.put("msg", "保存成功");
        return mv;
    }


}
