package com.chainfuture.code.service.impl;


import com.chainfuture.code.bean.WeChatUser;
import com.chainfuture.code.bean.WeChatUserExample;
import com.chainfuture.code.mapper.WeChatUserMapper;
import com.chainfuture.code.service.WeChatUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("weChatUserServiceImpl")
public class WeChatUserServiceImpl implements WeChatUserService{


    public static final Logger LOGGER = Logger.getLogger(NewsServiceImpl.class);

    @Autowired
    private WeChatUserMapper weChatUserMapper;

    @Override
    public List<WeChatUser> listWeChatUser(WeChatUser model) {
        return weChatUserMapper.listWeChatUser(model);
    }

    @Override
    public List<Map<String, Object>> selectSql(String sql) {
        try{
            return weChatUserMapper.selectSql(sql);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer queryCount() {
        return   weChatUserMapper.queryCount();
    }

    @Override
    public WeChatUserExample selUserByAddr(String s) {
        return weChatUserMapper.selUserByAddr(s);
    }

    @Override
    public void insertUser(WeChatUserExample result1) {
        weChatUserMapper.insertUser(result1);
    }

    @Override
    public WeChatUserExample selAdminByAddr(String address) {
        return weChatUserMapper.selAdminByAddr(address);
    }

    @Override
    public void updateUser(WeChatUserExample weChatUserExample) {
        weChatUserMapper.updateUser(weChatUserExample);
    }

    @Override
    public List<WeChatUserExample> getWeChatAll() {
        return weChatUserMapper.getWeChatAll();
    }

    @Override
    public int selectCoin() {
        return  weChatUserMapper.selectCoin();
    }

}
