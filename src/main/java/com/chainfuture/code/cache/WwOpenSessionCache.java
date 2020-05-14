package com.chainfuture.code.cache;

public class WwOpenSessionCache extends WwCache {
	
	private static WwOpenSessionCache _instance=null;	

	public WwOpenSessionCache() {
		super("open_session_cache");
	}
	
	public static WwOpenSessionCache getInstance(){
		if(_instance==null)
			_instance=new WwOpenSessionCache();
		
		return _instance;
	}

}
