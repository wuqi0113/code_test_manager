package com.chainfuture.code.service;

import com.chainfuture.code.bean.Medal;
import com.chainfuture.code.bean.WeChatUser;
import com.chainfuture.code.bean.WeChatUserExample;

import java.util.List;
import java.util.Map;

public interface WeChatUserService {

     List<Map<String,Object>> selectSql(String sql);

     int selectCoin();

    List<WeChatUser> listWeChatUser(WeChatUser model);

    Integer queryCount();

    WeChatUserExample selUserByAddr(String s);

    void insertUser(WeChatUserExample result1);

    WeChatUserExample selAdminByAddr(String s);

    void updateUser(WeChatUserExample weChatUserExample);

    List<WeChatUserExample> getWeChatAll();

    ;
}
