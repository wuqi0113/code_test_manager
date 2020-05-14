package com.chainfuture.code.controller;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.*;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.service.*;
import com.chainfuture.code.servlet.AccessVerify;
import com.chainfuture.code.utils.ApiUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/tast/")
public class TastController {

    @Autowired
    private FxUserService fxUserService;
    @Autowired
    private FxAwardService  fxAwardService;
    @Autowired
    private  FxRecordService  fxRecordService;
    @Autowired
    private FxOrderService fxOrderService;
    @Autowired
    private FxAwardInfeedService fxAwardInfeedService;
    //引入apache的log4j日志包
    public static final Logger LOGGER = Logger.getLogger(TastController.class);

    public TastController() {
    }

    @ResponseBody
    @RequestMapping(value="aaa")
    public JSONObject aaa(HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        res.put("cefff",1);
        return res;
    }
    @ResponseBody
    @RequestMapping(value="testLogin")
    public JSONObject testLogin(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        try{
            String invite = WwSystem.ToString(request.getParameter("invite"));
            AccessVerify av = new AccessVerify();
            JSONObject verify = av.verify(request);
            if (verify.get("success").toString().equals("false")){
                res.put("code",101);
                res.put("msg","登陆验证失敗");
                return res;
            }
            Object result = verify.get("data");
            FxUser user = fxUserService.selUserByAddr(result.toString());
            FxUser uu = new FxUser();
            int  zlevel = 0;
            String sss= "";
            if (user==null){
                Map<String, String> map1 =new HashMap<>(0);
                map1.put("openid",result.toString());
                String sign1 = ApiUtil.getSign(map1);
                String url = "api/getUserMessage";
                String params="signCode="+sign1;
                params += "&openid="+result.toString();
                String ss = ApiUtil.sendPost(url, params);
                JSONObject jsonObject = JSONObject.parseObject(ss);
                if (jsonObject.get("status").toString().equals("false")) {
                    res.put("code",2);
                    res.put("msg","连接失败");
                    return res;
                }
                String s = JSONObject.toJSONString(jsonObject.get("result"));
                JSONObject jsonObject1 = JSONObject.parseObject(s);
                uu.setUserName(jsonObject1.get("nickname").toString());
                uu.setAddress(jsonObject1.get("address").toString());
                uu.setHeadImage(jsonObject1.get("headimgurl").toString());
                int level = 0;
                String[] split = invite.split(":");
                String s2 = split[0];
                if (!StringUtils.isEmpty(s2)){
                    level=Integer.valueOf(s2);
                    String s1 = split[1];
                    uu.setInviteCode(s1);
                    uu.setPid(Integer.valueOf(s1));
                    sss = s1;
                }
                uu.setLevel(level);
                fxUserService.insertUser(uu);
                if (uu.getId()==null){
                    LOGGER.info("更新数据失败");
                }
                zlevel = level;
            }else {
                zlevel=user.getLevel();
                sss = user.getInviteCode();
            }
            if (zlevel>0){
                FxUser fxu = fxUserService.selUserByInviteCode(sss);
                if (fxu==null){
                    LOGGER.error("用户数据异常!");
                }
                FxRecord  fr = new FxRecord();
                fr.setFxUserId(fxu.getId());//父级I
                fr.setUserId(uu.getId());
                fr.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                fr.setStatus(1);
                int  sum = fxOrderService.getOrderSum(user.getId());

                fr.setMoney(BigDecimal.valueOf(1.10));
            }

            request.getSession().setAttribute("admin",uu.getId());
        }catch (Exception e){
            res.put("code",1);
            res.put("msg","失敗"+e.getMessage());
            return  res;
        }
        res.put("success",true);
        res.put("msg", "登录成功");
        res.put("code",0);
        return  res;
    }

    @RequestMapping(value = "saveOrder")
    @ResponseBody
    public JSONObject saveOrder(HttpServletRequest  request,HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
       /* Object admin = request.getSession().getAttribute("admin");
        if (admin==null){
            res.put("msg","登陆失败");
            return res;
        }*/
        String admin = WwSystem.ToString(request.getParameter("admin"));
        String string = WwSystem.ToString(request.getParameter("orderNum"));
        if (StringUtils.isEmpty(string)){
            res.put("msg","订单信息有误");
            return res;
        }
        int pid = WwSystem.ToInt(request.getParameter("pid"));
        if (pid<0){
            pid=0;
        }
        int amount = WwSystem.ToInt(request.getParameter("amount"));
        if (amount<0){
            amount=0;
        }
        FxOrder  fo = new FxOrder();
        fo.setOrderId(string);
//        fo.setUserId(Integer.valueOf(admin.toString()));
        fo.setUserId(Integer.valueOf(admin));
        fo.setPid(pid);
        fo.setGoodPrice(amount);
        fxOrderService.insertOrder(fo);
        if (fo.getId()<=0){
            res.put("msg","保存订单失败");
            return res;
        }
        double menoy = 0.00;
        FxUser  fx1 = fxUserService.selUserById(pid);
        fx1.setUmbrellaCount(fx1.getUmbrellaCount()+1);
        fx1.setOneCount(fx1.getOneCount()+1);
        fxUserService.updateUser(fx1);
        //竖向条件
        List<FxAward>  fa = fxAwardService.selFxAward();
        for (int i=0;i<fa.size();i++){
            if (fx1.getOneCount()>=fa.get(i).getLterm() && fx1.getOneCount()<fa.get(i).getHterm()){
                menoy=(double) amount*fa.get(i).getAward()/100;
            }
        }
        FxRecord fxRecord = new FxRecord();
        fxRecord.setMoney(BigDecimal.valueOf(menoy));
        fxRecord.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        fxRecord.setUserId(Integer.valueOf(admin));
//        fxRecord.setUserId(Integer.valueOf(admin.toString()));
        fxRecord.setFxUserId(pid);
        fxRecord.setOrderId(string);
        fxRecordService.insertFxRecord(fxRecord);
        if (fxRecord.getId()<=0){
            res.put("msg","保存记录失败");
            return res;
        }
        double menoy1 = 0.00;
        //横向条件
        List<FxAwardInfeed>  fai = fxAwardInfeedService.selFxAwardInfeed();
        for (int j=0;j<fai.size();j++){
            if (fai.get(j).getLcondit()<=fx1.getUmbrellaCount() && fai.get(j).getHcondit()>fx1.getUmbrellaCount()){
                menoy1 = (double)amount*fai.get(j).getReward()/100;
            }
        }
        FxRecord fxRecord1 = new FxRecord();
        fxRecord1.setMoney(BigDecimal.valueOf(menoy1));
        fxRecord1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        fxRecord1.setUserId(Integer.valueOf(admin));
//        fxRecord.setUserId(Integer.valueOf(admin.toString()));
        fxRecord1.setFxUserId(pid);
        fxRecord1.setOrderId(string);
        fxRecordService.insertFxRecord(fxRecord1);
        if (fxRecord.getId()<=0){
            res.put("msg","保存记录失败");
            return res;
        }
        if (fx1.getPid()>0){
            FxUser  pfx = fxUserService.selUserById(fx1.getPid());
            double menoy2 = (double)amount*fai.get(0).getPreward()/100;
            FxRecord fxRecord2 = new FxRecord();
            fxRecord2.setMoney(BigDecimal.valueOf(menoy2));
            fxRecord2.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            fxRecord2.setUserId(Integer.valueOf(admin));
//        fxRecord.setUserId(Integer.valueOf(admin.toString()));
            fxRecord2.setFxUserId(pfx.getId());
            fxRecord2.setOrderId(string);
            fxRecordService.insertFxRecord(fxRecord2);
            if (fxRecord.getId()<=0){
                res.put("msg","保存记录失败");
                return res;
            }
        }


        /*int parentid = pid;
        for (int i=0;;i++){
            FxUser  fx = fxUserService.selUserById(parentid);
            if (fx.getPid()>0){
                fx.setUmbrellaCount(fx.getUmbrellaCount()+1);
                fx.setOneCount(fx.getOneCount()+1);
                fxUserService.updateUser(fx);
                if (fx.getOneCount()>20){
                    int parentid2 = fx.getPid();
                    FxRecord fxRecord1 = new FxRecord();
                    fxRecord1.setMoney(BigDecimal.valueOf(amount*3/100));
                    fxRecord1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//                    fxRecord1.setUserId(Integer.valueOf(admin.toString()));
                    fxRecord1.setUserId(Integer.valueOf(admin));
                    fxRecord1.setFxUserId(parentid2);
                    fxRecord1.setOrderId(string);
                    fxRecordService.insertFxRecord(fxRecord1);
                    if (fxRecord.getId()<=0){
                        res.put("msg","保存记录失败");
                        return res;
                    }
                    for (int j=0;;j++){
                        FxUser  fxUser = fxUserService.selUserById(parentid2);
                        if (fxUser.getPid()>0){
                            fxUser.setUmbrellaCount(fxUser.getUmbrellaCount()+1);
                            fxUser.setTwoCount(fxUser.getTwoCount()+1);
                            fxUserService.updateUser(fxUser);
                            if (fxUser.getTwoCount()>100){
                                FxRecord fxRecord2 = new FxRecord();
                                fxRecord2.setMoney(BigDecimal.valueOf(amount*3/100));
                                fxRecord2.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//                                fxRecord2.setUserId(Integer.valueOf(admin.toString()));
                                fxRecord2.setUserId(Integer.valueOf(admin));
                                fxRecord2.setFxUserId(fxUser.getPid());
                                fxRecord2.setOrderId(string);
                                fxRecordService.insertFxRecord(fxRecord1);
                                if (fxRecord.getId()<=0){
                                    res.put("msg","保存记录失败");
                                    return res;
                                }
                                break;
                            }
                        }
                        parentid2=fxUser.getPid();
                    }
                }
            }else {
                break;
            }
            parentid=fx.getPid();
        }*/
        res.put("success",true);
        res.put("msg","成功");
        return res;
    }

    public static void  main(String[] args) {
        int amount=9885;
        double level = (double) amount*5/100;
        System.out.println(level);
    }
}
