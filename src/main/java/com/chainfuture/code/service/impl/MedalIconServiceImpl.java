package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.MedalIcon;
import com.chainfuture.code.mapper.MedalIconMapper;
import com.chainfuture.code.service.MedalIconService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("medalIconServiceImpl")
public class MedalIconServiceImpl implements MedalIconService{

    //引入apache的log4j日志包
    public static final Logger LOGGER = Logger.getLogger(MedalIconServiceImpl.class);

    @Autowired
    private MedalIconMapper medalIconMapper;


    @Override
    public List<MedalIcon> listMedalIcon() {
        return medalIconMapper.listMedalIcon();
    }
}
