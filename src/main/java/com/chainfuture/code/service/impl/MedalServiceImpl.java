package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.Medal;
import com.chainfuture.code.bitcoin.NrcMain;
import com.chainfuture.code.mapper.MedalMapper;
import com.chainfuture.code.service.MedalService;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("medalServiceImpl")
public class MedalServiceImpl implements MedalService {

    //引入apache的log4j日志包
    public static final Logger LOGGER = Logger.getLogger(MedalServiceImpl.class);

    @Autowired
    private MedalMapper medalMapper;

    @Override
    public List<Medal> listMedal(Medal medal) {
        return  medalMapper.listMedal(medal);
    }

    @Override
    public Integer queryCount(Medal medal) {
        return medalMapper.queryCount(medal);
    }

    @Override
    public void saveMedal(Medal medal) {
        medalMapper.insertMedal(medal);
    }

    @Override
    public List<Medal> getMedalList() {
        return medalMapper.listMedal();
    }

    @Override
    public Medal getById(Integer id) {
        return medalMapper.getById(id);
    }

    @Override
    public void updateMedal(Medal medal) {
        medalMapper.updateMedal(medal);
    }

    @Override
    public List<Medal> querySql(String user) {
        return medalMapper.querySql(user);
    }

}
