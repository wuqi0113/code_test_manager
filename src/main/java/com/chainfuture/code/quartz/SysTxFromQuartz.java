package com.chainfuture.code.quartz;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bean.SysTxFrom;
import com.chainfuture.code.bean.SysTxTest;
import com.chainfuture.code.bean.SysTxTo;
import com.chainfuture.code.mapper.SysTxFromMapper;
import com.chainfuture.code.mapper.SysTxTestMapper;
import com.chainfuture.code.mapper.SysTxToMapper;
import com.chainfuture.code.utils.HttpRequestUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class  SysTxFromQuartz {

    @Autowired
    private SysTxTestMapper sysTxTestMapper;
    @Autowired
    private SysTxToMapper sysTxToMapper;
    @Autowired
    private SysTxFromMapper sysTxFromMapperMapper;
    private static Logger LOGGER = Logger.getLogger(SysTxFromQuartz.class);

//    @Scheduled(cron="0 */5 * * * ?")
    public void savefrom() {
        String  param="address=1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
        try{
            String txId = HttpRequestUtil.sendGet("http://127.0.0.1:9590/listBusiDataByFromAddress", param);
            JSONObject jsonObject = JSONObject.parseObject(txId);
            String s = JSONObject.toJSONString(jsonObject.get("data"));
//            int  count = sysTxTestMapper.getCount();
            int  count = sysTxFromMapperMapper.selSysTxCount();
            if (count<0){
                LOGGER.error("savefrom  getCount error"+"时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            if (count!=0){
                int ii=0;
                List<SysTxFrom> sysTxFroms = JSONObject.parseArray(s, SysTxFrom.class);
                int size = sysTxFroms.size();
                if (sysTxFroms.size()<0){
                    LOGGER.error("savefrom sysTxTests error"+"时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
                if (!String.valueOf(count).equals(String.valueOf(size))){
                    for (int i=count; i<sysTxFroms.size(); i++) {
                        SysTxFrom sysTxFrom = sysTxFroms.get(i);
//                        ii=sysTxTestMapper.insert(sysTxFrom);
                        ii=sysTxFromMapperMapper.addSysTxFrom(sysTxFrom);
                        if (ii<0){
                            LOGGER.error(sysTxFroms.get(i)+"savefrom insert  error"+"时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("savefrom: "+e.getMessage()+"时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
    }

//    @Scheduled(cron="0 */2 * * * ?")
    public void saveto() {
        String  param="address=1ojviBeqSqasEKRWqifwLwwP5MzJN181y";
        try{
            String txId = HttpRequestUtil.sendGet("http://127.0.0.1:9590/listBusiDataByToAddress", param);
            JSONObject jsonObject = JSONObject.parseObject(txId);
            String s = JSONObject.toJSONString(jsonObject.get("data"));
            int  count = sysTxToMapper.selSysTxCount();
            if (count<0){
                LOGGER.error("getCount error"+"时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
            if(count!=0){
                int ii=0;
                List<SysTxTo> sysTxTos = JSONObject.parseArray(s, SysTxTo.class);
                int size = sysTxTos.size();
                if (sysTxTos.size()<0){
                    LOGGER.error("sysTxTests error"+"时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
                if (!String.valueOf(count).equals(String.valueOf(size))){
                    for (int i=count; i<sysTxTos.size(); i++) {
                        SysTxTo sysTxTo = sysTxTos.get(i);
                        ii=sysTxToMapper.addSysTxTo(sysTxTo);
                        if (ii<0){
                            LOGGER.error(sysTxTos.get(i)+"insert  error"+"时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage()+"时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
    }
   /* @Scheduled(cron="0 0 0 /1 * ? ")//每天凌晨
    public void test1(){

    }
    @Scheduled(cron="0 0 1 /1 * ? ")
    public void test2(){

    }*/
}
