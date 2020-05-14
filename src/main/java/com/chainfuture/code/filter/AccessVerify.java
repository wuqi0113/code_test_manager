package com.chainfuture.code.filter;

import com.chainfuture.code.common.MD5;
import com.chainfuture.code.utils.StringSort;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;


public class AccessVerify {
	
	private String appId="DEMO";
	private String secure="vsK3TKb61kbRcYzGIo4HUNaa29xLxyDw";
	
	/*public String verify(HttpServletRequest request){
		return"ok";
	}*/
	public String verify(HttpServletRequest request){
		
		String sign_code=request.getParameter("sign_code");
		if(sign_code==null||sign_code.isEmpty()){
			return "failed";
		}
		String appId2=request.getParameter("appId");
		if(appId2==null||appId2.isEmpty()){
			return "failed";
		}
		if(!appId2.equals(appId)){
			return "failed";
		}
		
		Enumeration<String> keys =request.getParameterNames();
		ArrayList<String> keys_sort=new ArrayList<String>();
        while (keys.hasMoreElements()) {
        	String key=keys.nextElement();
        	//String value=request.getParameter(key);
        	if(!key.equals("sign_code")){
        		keys_sort.add(key);
        	}
        }
        keys_sort.add("secure");
        System.out.println(keys_sort+":keys_sort");
        try {
			String[] keys_array= StringSort.sort(keys_sort.toArray(new String[keys_sort.size()]));
			System.out.println(keys_array+":keys_array");
			String src="";
			for(int i=0;i<keys_array.length;i++){
				String key=keys_array[i];
				if(key.equals("appId")){
					src+=appId;
				}else if(key.equals("secure")){
					src+=secure;
				}else{
					src+=request.getParameter(key);
				}				
			}
			System.out.println(src);
			String sign_code2= MD5.md5(src);
			
			if(sign_code2.equals(sign_code)){
				return "ok";
			}else{
				return "failed";
			}		
			
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
