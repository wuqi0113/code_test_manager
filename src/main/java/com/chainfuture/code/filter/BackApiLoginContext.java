package com.chainfuture.code.filter;

import com.chainfuture.code.bean.WeChatUserExample;
import com.chainfuture.code.cache.WwSessionCache;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Date;


public class BackApiLoginContext {
	public static class PhoneValidCodeInfo implements Serializable{

		private static final long serialVersionUID = 6943364001224236309L;
		public String ValidCode="";
		public Date CreateDate;
		public int Times=0; //次数
		
		public PhoneValidCodeInfo(){
			
		}
	}
	public static class ImageValidCodeInfo implements Serializable{

		private static final long serialVersionUID = -1394555339920568488L;
		public String ValidCode="";
		public Date CreateDate;
		
		public ImageValidCodeInfo(){
			
		}
	}
	public static final Logger LOGGER = Logger.getLogger(BackApiLoginContext.class);
	public static boolean isRedis=false;
	
	/**
	 * 
	 * @param user
	 * @param token
	 */
	public static void setToken(WeChatUserExample user, String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			String adminLoginMap_key="adminLoginMap_"+user.getAddress();
			redis.set(adminLoginMap_key, token);
			
			String loginUser1Map_key="loginUser1Map_"+token;
			redis.set(loginUser1Map_key, user);
		}else{
			String adminLoginMap_key="adminLoginMap_"+user.getAddress();
			WwSessionCache.getInstance().setString(adminLoginMap_key, token);
			
			String loginUser1Map_key="loginUser1Map_"+token;
			WwSessionCache.getInstance().setObject(loginUser1Map_key, user);
		}
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public static String getToken(String username){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			String adminLoginMap_key="adminLoginMap_"+username;
			return redis.getString(adminLoginMap_key);			
		}else{		
			String adminLoginMap_key="adminLoginMap_"+username;
			return WwSessionCache.getInstance().getString(adminLoginMap_key);
		}
		
	}
	
	
	public static void setUser(String token,WeChatUserExample user){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String loginUser1Map_key="loginUser1Map_"+token;
			redis.set(loginUser1Map_key, user);
			String adminLoginMap_key="adminLoginMap_"+user.getAddress();
			redis.set(adminLoginMap_key, token);
			
		}else{				
			String loginUser1Map_key="loginUser1Map_"+token;
			WwSessionCache.getInstance().setObject(loginUser1Map_key, user);
			String adminLoginMap_key="adminLoginMap_"+user.getAddress();
			WwSessionCache.getInstance().setObject(adminLoginMap_key, token);
		}
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	public static WeChatUserExample getUser(String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String loginUser1Map_key="loginUser1Map_"+token;
			WeChatUserExample lu=(WeChatUserExample)redis.getObject(loginUser1Map_key);
			LOGGER.info("根据token获取用户："+lu);
			return lu;
		}else{
		
			String loginUser1Map_key="loginUser1Map_"+token;
			WeChatUserExample lu=(WeChatUserExample)WwSessionCache.getInstance().getObject(loginUser1Map_key);
			LOGGER.info("根据token获取用户："+lu);
			return lu;
		}
	}
	
	
	
	/**
	 * 
	 * @param userName
	 */
	public static void removeUser(String userName){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String adminLoginMap_key="adminLoginMap_"+userName;
			String token_old=redis.getString(adminLoginMap_key);
			if(!token_old.equals("")&&!token_old.isEmpty()){
				String loginUser1Map_key="loginUser1Map_"+token_old;
				redis.delete(loginUser1Map_key);
			}
			redis.delete(adminLoginMap_key);
		}else{		
			String adminLoginMap_key="adminLoginMap_"+userName;
			String token_old=WwSessionCache.getInstance().getString(adminLoginMap_key);
			if(!token_old.equals("")&&!token_old.isEmpty()){
				String loginUser1Map_key="loginUser1Map_"+token_old;
				WwSessionCache.getInstance().remove(loginUser1Map_key);
			}
			WwSessionCache.getInstance().remove(adminLoginMap_key);
		}
	}
	
	/**
	 * 
	 * @param token
	 */
	public static void removeToken(String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String loginUser1Map_key="loginUser1Map_"+token;
			WeChatUserExample lu=(WeChatUserExample)redis.getObject(loginUser1Map_key);
			if(lu!=null){
				String adminLoginMap_key="adminLoginMap_"+lu.getAddress();
				redis.delete(adminLoginMap_key);
			}
			WwSessionCache.getInstance().remove(loginUser1Map_key);	
		}else{
			String loginUser1Map_key="loginUser1Map_"+token;
			WeChatUserExample lu=(WeChatUserExample)WwSessionCache.getInstance().getObject(loginUser1Map_key);
			if(lu!=null){
				String adminLoginMap_key="adminLoginMap_"+lu.getAddress();
				WwSessionCache.getInstance().remove(adminLoginMap_key);
			}
			WwSessionCache.getInstance().remove(loginUser1Map_key);		
		}
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param validCode
	 */
	public static void setPhoneValidCode(String phoneNum,String validCode){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			redis.delete(PhoneValidCodeMap_key);
			PhoneValidCodeInfo pvci=new PhoneValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			redis.set(PhoneValidCodeMap_key, pvci);		
		}else{
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			WwSessionCache.getInstance().remove(PhoneValidCodeMap_key);
			PhoneValidCodeInfo pvci=new PhoneValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			WwSessionCache.getInstance().setObject(PhoneValidCodeMap_key, pvci);
		}
	}
	
	/**
	 * 
	 * @param phoneNum
	 * @param validCode
	 * @param times 调用次数
	 */
	public static void setPhoneValidCode(String phoneNum,String validCode,int times){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
		
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			redis.delete(PhoneValidCodeMap_key);
			PhoneValidCodeInfo pvci=new PhoneValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			pvci.Times=times;
			redis.set(PhoneValidCodeMap_key, pvci);
		}else{
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			WwSessionCache.getInstance().remove(PhoneValidCodeMap_key);
			PhoneValidCodeInfo pvci=new PhoneValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			pvci.Times=times;
			WwSessionCache.getInstance().setObject(PhoneValidCodeMap_key, pvci);
		}
	}
	
	/**
	 * 
	 * @param phoneNum
	 */
	public static PhoneValidCodeInfo getPhoneValidCode(String phoneNum){		
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			PhoneValidCodeInfo pvci=(PhoneValidCodeInfo)redis.getObject(PhoneValidCodeMap_key);
			return pvci;
		}else{
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			PhoneValidCodeInfo pvci=(PhoneValidCodeInfo)WwSessionCache.getInstance().getObject(PhoneValidCodeMap_key);
			return pvci;
		}
	}
	
	/**
	 * 
	 * @param phoneNum
	 */
	public static void removePhoneValidCode(String phoneNum){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			redis.delete(PhoneValidCodeMap_key);
		}else{
			String PhoneValidCodeMap_key="PhoneValidCodeMap_"+phoneNum;
			WwSessionCache.getInstance().remove(PhoneValidCodeMap_key);
		}
	}
	
		
	/**
	 * 
	 * @param token
	 * @param validCode
	 */
	public static void setImageValidCode(String token,String validCode){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			redis.delete(ImageValidCodeMap_key);
			ImageValidCodeInfo pvci=new ImageValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			redis.set(ImageValidCodeMap_key, pvci);
		}else{
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			WwSessionCache.getInstance().remove(ImageValidCodeMap_key);
			ImageValidCodeInfo pvci=new ImageValidCodeInfo();
			pvci.ValidCode=validCode;
			pvci.CreateDate=new Date();
			WwSessionCache.getInstance().setObject(ImageValidCodeMap_key, pvci);
		}
	}
	/**
	 * 
	 * @param token
	 * @return
	 */
	public static ImageValidCodeInfo getImageValidCode(String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			ImageValidCodeInfo pvci=(ImageValidCodeInfo)redis.getObject(ImageValidCodeMap_key);
			return pvci;
		}else{
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			ImageValidCodeInfo pvci=(ImageValidCodeInfo)WwSessionCache.getInstance().getObject(ImageValidCodeMap_key);
			return pvci;
		}
	}
	
	/**
	 * 
	 * @param token
	 */
	public static void removeImageValidCode(String token){
		if(isRedis){
			WwRedisDAO2 redis=WwRedisDAO2.inst();
			
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			redis.delete(ImageValidCodeMap_key);
		}else{
			String ImageValidCodeMap_key="ImageValidCodeMap_"+token;
			WwSessionCache.getInstance().remove(ImageValidCodeMap_key);
		}
	}	
	

}
