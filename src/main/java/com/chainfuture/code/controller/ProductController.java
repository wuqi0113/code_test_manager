package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.bitcoin.WwHttpRequest;
import com.chainfuture.code.common.QRCodeUtil;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.mapper.AuthManageMapper;
import com.chainfuture.code.mapper.SignMsgMapper;
import com.chainfuture.code.mapper.SysManageMapper;
import com.chainfuture.code.service.*;
import com.chainfuture.code.utils.DownloadTxt;
import com.chainfuture.code.utils.HttpRequestUtil;
import com.chainfuture.code.utils.IPUtils;
import com.google.common.collect.Maps;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ProductThread implements Callable<List<String>> {

    private String productAddress;
    private String proName;
    private long codeId;
    public int count;
    public int num;
    private int type;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private Lock lock = new ReentrantLock();
    @Autowired
    private OrginMessageService orginMessageService;
    //引入apache的log4j日志包
    public  final Logger LOGGER = Logger.getLogger(ProductThread.class);

    public ProductThread(){

    }
    public ProductThread(String proName,String productAddress,int num,long codeId, int type,HttpServletRequest request, HttpServletResponse response){
        this.proName = proName;
        this.productAddress = productAddress;
        this.num = num;
        this.codeId = codeId;
        this.request = request;
        this.response = response;
        this.type = type;
    }

    NrcMain nm = new NrcMain();

    @Override
    public  List<String> call() throws Exception {
        List<String> list = new ArrayList<>();
        nm.initRPC();
        synchronized(this){
            while(num>0){
                String originMessage=null;
                String originMessage1=null;
                String signMessage=null;
                String message=null;
                String signMessage1=null;
                String message1=null;
                LOGGER.info("type："+type);
                /*if (type==1 || type==2){
                    originMessage = "id:"+codeId;
                }else if(type==3){
                    originMessage = "id:999"+codeId;
                }*/
                if (type==1 || type==2){
                    originMessage = "id:"+codeId;
                }else if(type==3){
                    originMessage = "id:"+codeId+",type:999";
                }
                signMessage = nm.signMessage(productAddress,originMessage);
                if(signMessage==null){
                    LOGGER.warn("签名失败:"+codeId+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
                    return null;
                }
                message = "https://reitschain.com/co?s="+signMessage+"&o="+originMessage+"&a="+productAddress;
                list.add(message);
                if (type==2){
                    originMessage1 = "id:"+codeId+",type:999";
                    signMessage1 = nm.signMessage(productAddress,originMessage1);
                    if(signMessage1==null){
                        LOGGER.warn("签名失败:999"+codeId+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
                        return null;
                    }
                    message1 = "https://reitschain.com/co?s="+signMessage1+"&o="+originMessage1+"&a="+productAddress;
                    list.add(message1);
                }
                num--;
                codeId++;
            }
        }
        return list;
    }
}

class NewProductThread implements Callable<List<String>> {

    private List<SignDetail> tList;
    private long codeId;
    public int count;
    public int num;
    private int type;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private Lock lock = new ReentrantLock();
    @Autowired
    private OrginMessageService orginMessageService;
    //引入apache的log4j日志包
    public  final Logger LOGGER = Logger.getLogger(ProductThread.class);

    public NewProductThread(){

    }
    public NewProductThread(List<SignDetail> tList,HttpServletRequest request, HttpServletResponse response){
        this.tList = tList;
    }

    NrcMain nm = new NrcMain();

    @Override
    public  List<String> call() throws Exception {
        List<String> list = new ArrayList<>();
        HashMap<String,Object> map = new HashMap<>();
        nm.initRPC();
        num = tList.size();
        synchronized(this){
            for(int j=0;j<num;j++){
                String originMessage=null;
                String signMessage=null;
                String message=null;
                String originMessage1=null;
                String message1=null;
                String signMessage1=null;
                codeId = tList.get(j).getSignId();
                String productAddress = tList.get(j).getProAddress();
                if (type==1 || type==2){
                    originMessage = "id:"+codeId;
                }else if(type==3){
                    originMessage = "id:"+codeId+",type:999";
                }
                signMessage = nm.signMessage(productAddress,originMessage);
                if(signMessage==null){
                    LOGGER.warn("签名失败:"+codeId+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
                    return null;
                }
                message = "https://reitschain.com/co?s="+signMessage+"&o="+originMessage+"&a="+productAddress;
                map.put("message",message);
                map.put("proAddress",productAddress);
                map.put("signId",codeId);
                list.add(map.toString());
                if (type==2){
                    originMessage1 = "id:"+codeId+",type:999";
                    signMessage1 = nm.signMessage(productAddress,originMessage1);
                    if(signMessage1==null){
                        LOGGER.warn("签名失败:999"+codeId+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
                        return null;
                    }
                    message1 = "https://reitschain.com/co?s="+signMessage1+"&o="+originMessage1+"&a="+productAddress;
                    map.put("message",message1);
                    map.put("proAddress",productAddress);
                    map.put("signId",codeId);
                    list.add(map.toString());
                }
                num--;
                codeId++;
            }
        }
        return list;
    }
}

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private TxService txService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrginMessageService orginMessageService;
    @Autowired
    private MedalService  medalService;
    @Autowired
    private MajorLogService majorLogService;
    @Autowired
    private AuthManageMapper authManageMapper;
    @Autowired
    private SysManageMapper sysManageMapper;
    @Autowired
    private SignMsgMapper signMsgMapper;
    //引入apache的log4j日志包
    public static final Logger LOGGER = Logger.getLogger(ProductController.class);

    public static final  SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public List<String>  newBatchSignMsg(List<SignDetail> list,HttpServletRequest request, HttpServletResponse response){
        NewProductThread npt = new NewProductThread(list,request,response);
        List<String> proList=new ArrayList<>();
        for (int i=0;i<5;i++){
            FutureTask<List<String>> lt = new FutureTask<>(npt);
            new Thread(lt,"线程"+i).start();
            try {
                proList.addAll(lt.get());
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
        }
        if (proList.size()<=0){
            return null;
        }
        return proList;
    }

    @RequestMapping("batchSignMsg")
    @ResponseBody
    public  void  batchSignMsg(Integer id,Integer num,String proAddress,HttpServletRequest request, HttpServletResponse response) {
        if (id!=null||num!=null||proAddress!=null){
            Product byId = productService.getById(id);
            Integer proNum = byId.getProNum();
            String  proName = byId.getSname();
            long codeId = new Date().getTime();
            ProductThread pt=new ProductThread(proName,proAddress,num,codeId,1,request,response);
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
            dt.exportTxt(response,join,proName+substring+"~"+substring1);

            Product pro=new Product();
            pro.setId(id);
            pro.setProNum(num+proNum);
            productService.saveProduct(pro);
            MajorLog ml = new MajorLog();
            Object admin = request.getSession().getAttribute("admin");
            ml.setMember(admin.toString());
            String remoteAddr = IPUtils.getRemoteAddr(request);
            ml.setRemoteAddr(remoteAddr);
            ml.setNodeAddress(proAddress);
            ml.setMethod(request.getRequestURL().toString());
            ml.setOperatime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            ml.setOperation("批量产生二维码信息："+proName+substring+"~"+substring1);
            majorLogService.insertMajorLog(ml);
        }
    }

    @RequestMapping("list")
    public ModelAndView list(Medal medal, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.addObject("aaa",1);
        mv.setViewName("view/address-manage");
        return mv;
    }

    @RequestMapping("getChildProList")
    @ResponseBody
    public JSONObject getChildProList(Product model,HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        List<Product> list = null;
        Integer total = 0;
        if (model.getSname()==null||model.getSname()==""){
            res.put("code",1);
            res.put("msg","无法获取数据！");
            return res;
        }
        try{
            list = productService.getChildProList(model);
            total = productService.getBySnameLike(model);
            res.put("count",total-1); // 总条数
            res.put("data", list); // 列表数据
        }catch (Exception e){
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败");
        }
        return res;
    }
    @ResponseBody
    @RequestMapping("createCode")
    public ModelAndView  createCode(Integer id,String proAddress,String sname, HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        Product  model=new Product();
        mv.addObject("code",0);
        mv.addObject("msg","成功");
        Tx tx=new Tx();
        Integer nameNum=0;
//        List<Product> proList = null;

        tx.setProductAddress(proAddress);
        Integer num = 0;
        try{
            model.setSname(sname);
            nameNum= productService.getBySnameLike(model);
            model = productService.getById(id);
            num= txService.getCount(tx);
            mv.addObject("verfiyNum",num);
        }catch (Exception e){
            e.printStackTrace();
            mv.addObject("code",1);
            mv.addObject("msg","失败"+e.getMessage());
        }
        mv.addObject("id",model.getId());
        mv.addObject("sname",model.getSname());
        mv.addObject("proNum",model.getProNum());
        mv.addObject("proName",model.getProName());
        mv.addObject("proAddress",model.getProAddress());
        mv.addObject("success",0);
        if (nameNum>1){
//                proList = productService.getChildProList(sname);
//                mv.addObject("verfiyNum",nameNum-1);
            mv.setViewName("view/product_child");
        }else{
            mv.setViewName("view/product_detail");
        }
        return  mv;
    }

    @RequestMapping("addCode")
    @ResponseBody
    public  JSONObject  addCode(String proAddress,Integer id,
                                HttpServletRequest request, HttpServletResponse response){
        JSONObject res = new JSONObject();
        res.put("code",0);
        res.put("msg","成功");
        String signMessage=null;
        String originMessage = "";
        String message=null;
        String sname="";
        long codeId = 0;
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
            Product byId = productService.getById(id);
            Integer proNum1 = byId.getProNum();
            sname = byId.getSname();
            if (proNum1==null){
                proNum1=0;
            }
            codeId = new Date().getTime();
            originMessage = "id:"+codeId;
            signMessage = nm.signMessage(proAddress,originMessage);
            LOGGER.info("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            if(signMessage==null){
                LOGGER.error("签名失败:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            orginMessageService.saveCode(proAddress,originMessage,codeId,signMessage);
            message = "https://reitschain.com/co?s="+signMessage+"&o="+originMessage+"&a="+proAddress;
            Product pro=new Product();
            pro.setId(id);
            pro.setProNum(proNum1+1);
            productService.saveProduct(pro);
            ml.setOperation("签名:"+signMessage+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            majorLogService.insertMajorLog(ml);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("code",1);
            res.put("msg","失败"+e.getMessage());
        }
        String substring = sname+codeId;
        res.put("message",message);
        res.put("codeId",substring);
        return res;
    }



    @RequestMapping("getById")
    @ResponseBody
    public ModelAndView getById(Integer id,String sname, HttpServletRequest request, HttpServletResponse response){
        ModelAndView res = new ModelAndView();
        res.addObject("code", 0);
        res.addObject("msg", "成功");
        Product  model = new Product();
        List<Product> productList = null;
        List<Medal> medalList = null;
        Integer nameNum = 0;
        try{
            if (sname==null||sname==""){
                model =productService.getById(id);
                productList = productService. getAssetList();
                medalList = medalService.getMedalList();
            }else {
                model.setSname(sname);
                nameNum = productService.getBySnameLike(model);
                sname=sname+"--"+nameNum;
                model.setSname(sname);
            }
        }catch (Exception e){
            e.printStackTrace();
            res.addObject("code", 1);
            res.addObject("msg", "失败");
        }
        res.addObject("medalList",medalList);
        res.addObject("productList",productList);
        res.addObject("data",model);
        res.addObject("success",0);
        res.setViewName("view/product_edit");
        return  res;
    }

    @RequestMapping("listProduct")
    @ResponseBody
    public JSONObject listProduct(Product model,HttpServletRequest request, HttpServletResponse response){
       JSONObject res = new JSONObject();
       res.put("code",0);
       res.put("msg","成功");
        List<Product> list = null;
        Integer total = 0;
       try{
           list = productService.listProduct(model);
           total = productService.queryCount(model);
       }catch (Exception e){
           e.getMessage();
           res.put("code",1);
           res.put("msg","失败");
       }
        res.put("count",total); // 总条数
        res.put("data", list); // 列表数据
        return res;
    }

    @RequestMapping("updateStatus")
    @ResponseBody
    public Map<String,Object> updateStatus(Integer id,Integer status,HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = Maps.newHashMap();
        map.put("code", 0);
        map.put("msg", "保存成功");
        try {
            productService.updateStatus(id,status);
        } catch (Exception e) {
            e.getMessage();
            map.put("code", 1);
            map.put("msg", "保存失败，错误信息：");
        }
        return map;
    }


    //添加产品
    @RequestMapping("addPro")
    @ResponseBody
    public  ModelAndView addPro(Product model,HttpServletRequest request, HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.addObject("code", 0);
        mv.addObject("msg", "保存成功");

        String fullRoot = WwSystem.getFullRoot(request);
        NrcMain  nm = new NrcMain();
        nm.initRPC();
        String accountAddress=null;
        MajorLog  ml= new MajorLog();
        try {
            if(model.getId()==null){
                List<Product> pt= productService.queryBySname(model);
                if (pt.size()!=0&&pt!=null){
                    mv.addObject("code", 1);
                    mv.addObject("msg", "英文名称不能重复！");
                    mv.setViewName("redirect:/product/list");
                    return  mv;
                }
                Object admin = request.getSession().getAttribute("admin");
                ml.setMember(admin.toString());
                String localAddr = request.getLocalAddr();
                ml.setRemoteAddr(localAddr);
                ml.setNodeAddress(model.getPrimeAddr());
                ml.setMethod(request.getRequestURL().toString());
                ml.setOperatime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                accountAddress= nm.getAccountAddress(model.getSname());
                ml.setOperation("产生产品："+admin.toString()+"，产品地址："+accountAddress);
                LOGGER.info("账户:"+model.getSname()+"产生地址:"+accountAddress+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                if (accountAddress==null){
//                    String s = nm.sendToAddress("14wsJoKvHo3se3RAURZVQno6heF12PZJ6K", "999999:1", null, null);
                    String param="toAddress=1Nx69PCDKL9z7rU8Gq1QrW4gnLMcy7MHvm&assetId=999999&amount=1";
                    String s = HttpRequestUtil.sendPost("http://127.0.0.1:9590/sendToAddress", param, true);
                    ml.setOperation("账号超出，向1Nx69PCDKL9z7rU8Gq1QrW4gnLMcy7MHvm打一个币，节点地址："+model.getPrimeAddr());
                    LOGGER.error("发送交易:"+s+ "时间:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
                majorLogService.insertMajorLog(ml);
            }
            String  classify=model.getClassify();
            if (classify!=null){
                classify =fullRoot+"public/file_list/images/"+classify;
                model.setClassify(classify);
            }
            String  proThumbnail=model.getProThumbnail();
            if (proThumbnail!=null){
                proThumbnail =fullRoot+"public/file_list/images/"+proThumbnail;
                model.setProThumbnail(proThumbnail);
            }
            String  proAdvertImg=model.getProAdvertImg();
            if (proAdvertImg!=null){
                proAdvertImg =fullRoot+"public/file_list/images/"+proAdvertImg;
                model.setProAdvertImg(proAdvertImg);
            }
            String proDisplay = model.getProDisplay();
            if (proDisplay!=null){
                String s = proDisplay.replaceAll("<span id=\"aaa\"></span>","");

                String s1 = s.replaceAll("src=\"public","src=\""+fullRoot+"public");
                model.setProDisplay(s1);
            }
            model.setProAddress(accountAddress);
            productService.saveProduct(model);
        } catch (Exception e) {
            e.getMessage();
            mv.addObject("code", 1);
            mv.addObject("msg", "保存失败，错误信息：" + e.getMessage());
        }
        mv.setViewName("redirect:/product/list");
        return mv;
    }

    //修改产品
    @RequestMapping("updatePro")
    @ResponseBody
    public  ModelAndView updatePro(Product model,HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("code", 0);
        mv.addObject("msg", "保存成功");
        try {
            String fullRoot = WwSystem.getFullRoot(request);
            String proDisplay = model.getProDisplay();
            if (proDisplay != null) {
                String s2 = proDisplay.replaceAll("<span id=\"aaa\">", "").replace("</span>", "").replaceAll("<p>", "").replaceAll("</p>", "");
                String s = WwSystem.replaceBlank(s2);
                String s1 = s.replaceAll("src=\"public", "src=\"" + fullRoot + "public");

                model.setProDisplay(s1);
            }
            String  proAdvertImg=model.getProAdvertImg();
            if (proAdvertImg!=null){
                proAdvertImg =fullRoot+"public/file_list/images/"+proAdvertImg;
                model.setProAdvertImg(proAdvertImg);
            }
            productService.saveProduct(model);
        } catch (Exception e) {
            mv.addObject("code", 1);
            mv.addObject("msg", "保存失败，错误信息：" + e.getMessage());
        }
        mv.setViewName("redirect:/product/list");
        return mv;
    }



    public   List<String>  returnManySignMsg(String smId,int smType,String user,String codeId,Integer num,String proAddress,String proName,HttpServletRequest request, HttpServletResponse response) {
        int count = 0;
        if (num<=0){
            return null;
        }else if (num<=1000){
            count = 1;
        }else if (num<100000&&num>1000){
            count = 5;
        }else {
            count = 10;
        }
        NrcMain nm = new NrcMain();
        nm.initRPC();
        List<String> proList=new ArrayList<>();
        String originMessage=null;
        String signMessage=null;
        String message=null;
        String hangUpMsg = null;

        hangUpMsg = "http://mov.gksharedmall.cn?id="+smId;
        proList.add(hangUpMsg);
        proList.add(hangUpMsg);
        ProductThread pt=new ProductThread(proName,proAddress,num,Long.valueOf(codeId),smType,request,response);

        for (int i=0;i<count;i++){
            FutureTask<List<String>> lt = new FutureTask<>(pt);
            new Thread(lt,"线程"+i).start();
            try {
                proList.addAll(lt.get());
            } catch (InterruptedException e) {
//                e.printStackTrace();
                return null;
            } catch (ExecutionException e) {
//                e.printStackTrace();
                return null;
            }
        }
        if (proList.size()<=0){
            return null;
        }
        /*MajorLog ml = new MajorLog();
        ml.setMember(user);
        String remoteAddr = IPUtils.getRemoteAddr(request);
        ml.setRemoteAddr(remoteAddr);
        ml.setNodeAddress(proAddress);
        ml.setMethod(request.getRequestURL().toString());
        ml.setOperatime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        ml.setOperation("批量产生二维码信息："+proName+codeId+"~"+codeId+num);
        majorLogService.insertMajorLog(ml);
        LOGGER.info("程序");*/
        return proList;
    }

    public   boolean  downloadCode(String user,long codeId,Integer num,String proAddress,String proName,HttpServletRequest request, HttpServletResponse response) {
        int count = 0;
        if (num<=0){
            return false;
        }else if (num<=1000){
            count = 1;
        }else if (num<100000&&num>1000){
            count = 5;
        }else {
            count = 10;
        }
        NrcMain nm = new NrcMain();
        nm.initRPC();
        List<String> proList=new ArrayList<>();
        String originMessage=null;
        String signMessage=null;
        String message=null;
        String hangUpMsg = null;
        Map<String,Object>  map = new HashMap<>();
        map.put("proAddress",proAddress);
        map.put("signId",codeId);
        SignMsg  sm = signMsgMapper.getSignMsgByAddrAndSignId(map);
        if (sm==null){
            return false;
        }
        hangUpMsg = "http://mov.gksharedmall.cn?id="+sm.getId();
        proList.add(hangUpMsg);
        proList.add(hangUpMsg);
        ProductThread pt=new ProductThread(proName,proAddress,num,Long.valueOf(codeId),sm.getType(),request,response);
        for (int i=0;i<count;i++){
            FutureTask<List<String>> lt = new FutureTask<>(pt);
            new Thread(lt,"线程"+i).start();
            try {
                proList.addAll(lt.get());
            } catch (InterruptedException e) {
//                e.printStackTrace();
                return false;
            } catch (ExecutionException e) {
//                e.printStackTrace();
                return false;
            }
        }
        if (proList.size()<=0){
            return false;
        }
        String join = StringUtils.join(proList, ",");
        int i = join.indexOf("&o=id:");
        int ii = join.lastIndexOf("&o=id:");
        String substring = join.substring(i + 6, i + 19);
        String substring1 = join.substring(ii + 6, ii + 19);
        boolean b = DownloadTxt.resExportTxt(response, join, proName + substring + "~" + substring1);
        if (!b){
            return false;
        }
       /* MajorLog ml = new MajorLog();
        ml.setMember(user);
        String remoteAddr = IPUtils.getRemoteAddr(request);
        ml.setRemoteAddr(remoteAddr);
        ml.setNodeAddress(proAddress);
        ml.setMethod(request.getRequestURL().toString());
        ml.setOperatime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        ml.setOperation("批量产生二维码信息："+proName+substring+"~"+substring1);
        majorLogService.insertMajorLog(ml);*/
        return true;
    }
}
