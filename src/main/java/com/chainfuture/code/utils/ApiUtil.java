package com.chainfuture.code.utils;

import com.alibaba.fastjson.JSONObject;
import com.chainfuture.code.bitcoin.WwHttpRequest;
import com.chainfuture.code.common.MD5;
import org.apache.log4j.Logger;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.*;

public class ApiUtil {

    private static Logger LOGGER = Logger.getLogger(ApiUtil.class);
    private static String  appId = "test";
    private static String  secret = "test";
//    private static String  uri = "https://123.206.85.98/";
    private static String  uri = "https://reitschain.com/";
    private static String  timeStamp = Long.toString(new Date().getTime());


//    public static void main(String[] args) {
//        Map<String,String>  map =  new HashMap<>();
//        map.put("appId",appId);
//        map.put("secret",secret);
//        map.put("timeStamp",Long.toString(new Date().getTime()));
//        String sign = getSign(map);
//        System.err.println(sign);
//    }

    public static String sendPost(String url, String params){
        WwHttpRequest req=new WwHttpRequest();
        String param="appId="+appId+"&";
               param+="timeStamp="+timeStamp+"&";
//        JSONObject res= JSONObject.parseObject(req.post(url, param+params));
//        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(req.post(url, param + params));
        LOGGER.info(uri+url+param + params);
        String res = req.post(uri+url, param + params);
        return res;
    }

    public static   String  getSign(Map<String, String> map){
        map.put("appId",appId);
        map.put("secret",secret);
        map.put("timeStamp",timeStamp);
        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
//                        sb.append(key + ":" + val + ":");
                        LOGGER.info(key + ":" + val + ";");
                        sb.append(val);
                    }
                }

            }
//			sb.append(PropertyManager.getProperty("SIGNKEY"));
            result = sb.toString();
            System.out.println(result);
            //进行MD5加密
//            result = DigestUtils.md5Hex(result).toUpperCase();
            LOGGER.info( "result:"+result);
            result=MD5.md5(result);
        } catch (Exception e) {
            return null;
        }
        return result;

    }

    /**
     * 对字符串数组进行排序
     * @param keys
     * @return
     * @throws Exception
     * */
    public static String[] sort(String[] keys) throws Exception{

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
    private static boolean isMoreThan(String pre, String next) throws Exception{
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

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String md5(String text)
    {
        MessageDigest msgDigest = null;
        try
        {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "System doesn't support MD5 algorithm.");
        }
        try
        {
            msgDigest.update(text.getBytes("utf-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalStateException(
                    "System doesn't support your  EncodingException.");
        }

        byte[] bytes = msgDigest.digest();

        String md5Str = new String(encodeHex(bytes));

        return md5Str;
    }

    public static char[] encodeHex(byte[] data)
    {
        int l = data.length;

        char[] out = new char[l << 1];

        int i = 0; for (int j = 0; i < l; i++) {
        out[(j++)] = DIGITS[((0xF0 & data[i]) >>> 4)];
        out[(j++)] = DIGITS[(0xF & data[i])];
    }

        return out;
    }
}
