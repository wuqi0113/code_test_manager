package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.Medal;
import com.chainfuture.code.bean.WeChatUser;
import com.chainfuture.code.bean.WeChatUserExample;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/10/21.
 */
public interface WeChatUserMapper {
    List<WeChatUser> listWeChatUser(WeChatUser user);

    List<Map<String,Object>> selectSql(String sql);

    Integer queryCount(WeChatUser model);

    Integer queryCount();

    int selectCoin();

    WeChatUserExample selUserByAddr(String s);

    void insertUser(WeChatUserExample result1);

    WeChatUserExample selAdminByAddr(String address);

    void updateUser(WeChatUserExample weChatUserExample);

    List<WeChatUserExample> getWeChatAll();
}
