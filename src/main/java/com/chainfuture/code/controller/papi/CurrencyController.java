package com.chainfuture.code.controller.papi;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.AuthManage;
import com.chainfuture.code.bean.InuBase;
import com.chainfuture.code.bean.SysModule;
import com.chainfuture.code.bean.WorkapiRecord;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.common.MD5;
import com.chainfuture.code.common.WwSystem;
import com.chainfuture.code.mapper.InuBaseMapper;
import com.chainfuture.code.mapper.WorkapiRecordMapper;
import com.chainfuture.code.service.AuthManageService;
import com.chainfuture.code.service.SysModuleService;
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
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/papi/currency/")
public class CurrencyController {

    @Autowired
    private WorkapiRecordMapper workapiRecordMapper;
    @Autowired
    private SysModuleService sysModuleService;
    @Autowired
    private InuBaseMapper inuBaseMapper;
    @Autowired
    private AuthManageService authManageService;
    public static final Logger LOGGER = Logger.getLogger(CurrencyController.class);//returnManySignMsg

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.POST)
    public JSONObject test(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",true);
        return res;
    }



    //扫码支付
    @ResponseBody
    @RequestMapping(value = "pubSweepPay", method = RequestMethod.POST)
    public JSONObject pubSweepPay(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);
        int assetId = WwSystem.ToInt(request.getParameter("assetId"));
        int amount = WwSystem.ToInt(request.getParameter("amount"));
        if (amount<=0){
            res.put("msg", "支付金额有误");
            return res;
        }
        String redirectUrl = WwSystem.ToString(request.getParameter("redirectUrl"));
        String address = WwSystem.ToString(request.getParameter("userAddress"));
        if (StringUtils.isEmpty(address)){
            res.put("msg","参数有误");
            return res;
        }
        String targetAddress = WwSystem.ToString(request.getParameter("targetAddress"));
        if (StringUtils.isEmpty(targetAddress)){
            res.put("msg","参数有误");
            return res;
        }
        String orderNum = WwSystem.ToString(request.getParameter("orderNum"));
        if (StringUtils.isEmpty(orderNum)){
            res.put("msg","参数有误");
            return res;
        }
        String way = WwSystem.ToString(request.getParameter("way"));//1--链云店支付
        if (StringUtils.isEmpty(way)){
            res.put("msg","参数有误");
            return res;
        }
        int type = WwSystem.ToInt(request.getParameter("type"));
        String fromAddress = "";
        long time = new Date().getTime();
        String msg = "";
        try{
            NrcMain nm = new NrcMain();
            nm.initRPC();
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String primeaddr = nm.getPrimeaddr();
            WorkapiRecord wr = new WorkapiRecord();
            wr.setPurchaser(address);
            wr.setAmount(amount);
            wr.setOrderNum(orderNum);
            wr.setType(type);
            wr.setWay(way);
            wr.setStatus(0);
            wr.setRecordTime(format);
            res.put("time",format);
            workapiRecordMapper.addWorkapiRecord(wr);
            LOGGER.info("work:"+wr.getId());
            if (wr.getId()<0){
                res.put("msg", "添加失败");
                res.put("success", false);
                return res;
            }
            String message = "assetId*"+assetId+",amount*"+amount+",toAddress*"+targetAddress+",fromAddress*"+fromAddress+",time*"+time+",work*"+wr.getId()+",redirectUrl*"+redirectUrl;
            String signMessage = nm.signMessage(primeaddr, message);
            if(signMessage==null){
                res.put("message", "签名失败");
                res.put("success", false);
                return res;
            }else {
                LOGGER.info("签名:"+signMessage+ "时间:"+ format);
            }
            msg = "https://www.reitschain.com/code/pay?s="+signMessage+"&o="+message+"&a="+primeaddr;
            wr.setSignMsg(signMessage);
            workapiRecordMapper.updateById(wr);

            AuthManage authManage =new AuthManage();
//            authManage.setManageAddr(sysModule.get(0).getAddress());
            authManage.setAddress(targetAddress);
//            authManage.setDutyAddress(user.getAddress());
            authManage.setDutyAddress(fromAddress);
            authManage.setStatus(0);
            authManage.setIsPayment(0);
            authManage.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            authManage.setOperation("充值");
            authManageService.addAuthManage(authManage);
            res.put("data",msg);
        }catch (Exception e){
            res.put("msg","失败");
            return res;
        }
        res.put("success",true);
        return res;
    }

    @ResponseBody
    @RequestMapping(value = "sweepPayCallBack",method = RequestMethod.POST)
    public JSONObject  sweepPayCallBack(HttpServletRequest request, HttpServletResponse response){
        JSONObject  res = new JSONObject();
        res.put("success",false);

        String orderNum = WwSystem.ToString(request.getParameter("orderNum"));
        if (StringUtils.isEmpty(orderNum)){
            res.put("msg", "参数有误");
            return res;
        }
        try{
            WorkapiRecord  workapiRecord = workapiRecordMapper.selWorkapiRecordByOrderNum(orderNum);
            LOGGER.info("workapiRecord:"+workapiRecord);
            if (workapiRecord==null){
                res.put("message", "支付失败");
                return res;
            }
           if (workapiRecord.getStatus()!=1){
                res.put("message", "支付失败");
                return res;
            }
        }catch (Exception e){
            res.put("msg","失败");
            LOGGER.info("支付回调失败"+e.getMessage());
            res.put("code",3);
            return res;
        }
        res.put("msg","已完成支付");
        res.put("success",true);
        return res;
    }

    /*===================================*/
    public static  void main(String[] args){
        // a b c d e f g h r j k l m n o p q r s t
        //vsK3TKb61kbRcYzGIo4HUNaa29xLxyDw
        //u t  o w t s
        //amount  appId  assetId  orderNum  redirectUrl  secure   targetAddress  type  userAddress   way
        String m = MD5.md5("DEMOvsK3TKb61kbRcYzGIo4HUNaa29xLxyDw");
//        String m = MD5.md5("NRC983401769999991BVQiXro12b6fyJCpiCJoZ4Ag5aW2obx3msikx68m24gf985t7u653dfyu64dfy73u0imgsq13dxc");
//        String m = MD5.md5("test1CkiFQHP1ESAc3DgdnbPrSKFrDuSWP6pRqtest1586850892");
//        String m = MD5.md5("10DEMO999999123123789http%3a%2f%2f172.16.211.136%3a9093%2fmobile%2findex.php%3fm%3dgoods%26id%3d708%26u%3d60vsK3TKb61kbRcYzGIo4HUNaa29xLxyDw1PKspzX5uxGiiMFLVDXPfacWm3WzgtYwMg118XQ7VGTCeTH65oBhrrEBEwDPh5NT2n8xM1");
//        String m = MD5.md5("200DEMO9999992020022115630363793aHR0cCUzQS8vMTcyLjE2LjIxMS4xMzYlM0E5MDkzL21vYmlsZS9pbmRleC5waHAlM0ZtJTNEb25saW5lcGF5JTI2YSUzRHBheXJldHVybiUyNm9yZGVyX3NuJTNEMjAyMDAyMjExNTYzMDM2Mzc5Mw==vsK3TKb61kbRcYzGIo4HUNaa29xLxyDw19KLLe9ezsLS1r1yGnxjWn1kGEcx3BpKmh11PKspzX5uxGiiMFLVDXPfacWm3WzgtYwMg1");
//        String m = MD5.md5("200DEMO100002020022115630363793aHR0cCUzQS8vMTcyLjE2LjIxMS4xMzYlM0E5MDkzL21vYmlsZS9pbmRleC5waHAlM0ZtJTNEb25saW5lcGF5JTI2YSUzRHBheXJldHVybiUyNm9yZGVyX3NuJTNEMjAyMDAyMjExNTYzMDM2Mzc5Mw==vsK3TKb61kbRcYzGIo4HUNaa29xLxyDw19KLLe9ezsLS1r1yGnxjWn1kGEcx3BpKmh11PKspzX5uxGiiMFLVDXPfacWm3WzgtYwMg1");
        System.out.println(m);
    }
}
