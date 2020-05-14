package com.chainfuture.code.controller;

import com.chainfuture.code.bean.MedalIcon;
import com.chainfuture.code.bitcoin.WwHttpRequest;
import com.chainfuture.code.common.MD5;
import com.chainfuture.code.service.MedalIconService;
import com.chainfuture.code.utils.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/medalIcon")
public class MedalIconController {

    @Autowired
    private MedalIconService medalIconService;

//    private String  signCode;
    private static long timeStamp =new Date().getTime();
    private static String  appId="test";
    private static String  secret="test";
    private static String  url = "https://reitschain.com/api/test";
    @RequestMapping("/listMedalIcon")
    public String listMedal(Model model, HttpServletRequest request, HttpServletResponse response){
        List<MedalIcon> medalList = medalIconService.listMedalIcon();
        model.addAttribute("medalList", medalList);
        return "medal_list2";
    }


    public static void main(String[] args){
        /*ArrayList<String> keys_sort=new ArrayList<>();
        keys_sort.add("appId");
        keys_sort.add("timeStamp");
        keys_sort.add("secret");
        System.out.println(keys_sort+":keys_sort");
        try {
            String[] keys_array=sort(keys_sort.toArray(new String[keys_sort.size()]));
            System.out.println(keys_array+":keys_array");
            String src="test"+"test"+timeStamp;
           *//* for(int i=0;i<keys_array.length;i++){
                String key=keys_array[i];
                if(key.equals("appId")){
//                    src+="appId="+appId;
                    src+=appId;
                }else if(key.equals("secret")){
                    src+=secret;
//                    src+="&secret="+secret;
                }else{
                    src+=timeStamp;
//                    src+="&timeStamp="+timeStamp;
                }
            }*//*
            System.out.println(src);
            String sign_code2= MD5.md5(src);
            System.out.println(sign_code2);
            WwHttpRequest req=new WwHttpRequest();
            String params="appId="+appId;
            params += "&timeStamp="+timeStamp;
            params += "&signCode="+sign_code2;
            String s = HttpRequestUtil.sendGet(url, params);
//            String s = HttpRequestUtil.sendGet("http://www.baidu.com", null);
            System.out.println(s);
            String s1 = req.get(url, params);
            System.out.println("s1:"+s1);
            String res=req.post(url, params);
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
//        farm();
    }



    public  String[] sort(String[] keys) throws Exception{

        for (int i = 0; i < keys.length - 1; i++) {
            for (int j = 0; j < keys.length - i -1; j++) {
                String pre = keys[j];
                String next = keys[j + 1];
                if(isMoreThan(pre, next)){
                    String temp = pre;
                    keys[j] = next;
                    keys[j+1] = temp;
                }
            }
        }
        return keys;
    }

    /**
     * 比较两个字符串的大小，按字母的ASCII码比较
     * @param pre
     * @param next
     * @return
     * @throws Exception
     * */
    private  boolean isMoreThan(String pre, String next) throws Exception{
        if(null == pre || null == next || "".equals(pre) || "".equals(next)){
            //_log.error("字符串比较数据不能为空！");
            throw new Exception("字符串比较数据不能为空！");
            //return false;
        }

        char[] c_pre = pre.toCharArray();
        char[] c_next = next.toCharArray();

        int minSize = Math.min(c_pre.length, c_next.length);

        for (int i = 0; i < minSize; i++) {
            if((int)c_pre[i] > (int)c_next[i]){
                return true;
            }else if((int)c_pre[i] < (int)c_next[i]){
                return false;
            }
        }
        if(c_pre.length > c_next.length){
            return true;
        }

        return false;
    }

    public  void farm(){
        try {
            String src="test"+"test"+timeStamp;
            System.out.println(src);
            String sign_code2= MD5.md5(src);
            System.out.println(sign_code2);

            //调用方法
            farmPort(url,
                    "appId="+appId+
                            "&timeStamp="+timeStamp+
                            "&signCode="+sign_code2);
        } catch (IOException e) {
            log.error("农机接口创建用户异常",e.getMessage());
            e.printStackTrace();
        }
    }

    //接口操作
    public  void farmPort(String url, String query) throws IOException {
        URL restURL = new URL(url);
    /*
     * 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类 的子类HttpURLConnection
     */
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        //请求方式
        conn.setRequestMethod("POST");
        //设置是否从httpUrlConnection读入，默认情况下是true; httpUrlConnection.setDoInput(true);
        conn.setDoOutput(true);
        //allowUserInteraction 如果为 true，则在允许用户交互（例如弹出一个验证对话框）的上下文中对此 URL 进行检查。
        conn.setAllowUserInteraction(false);

        PrintStream ps = new PrintStream(conn.getOutputStream());
        ps.print(query);
        ps.close();
        BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line,resultStr="";

        while(null != (line=bReader.readLine()))
        {
            resultStr +=line;
        }
        bReader.close();
    }

}
