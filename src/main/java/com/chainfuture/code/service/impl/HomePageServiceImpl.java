package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.HomePage;
import com.chainfuture.code.mapper.HomePageMapper;
import com.chainfuture.code.service.HomePageService;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("homePageServiceImpl")
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    private HomePageMapper homePageMapper;
    //引入apache的log4j日志包
    public static final Logger LOGGER = Logger.getLogger(HomePageServiceImpl.class);

    @Override
    public HomePage getHomePage() {
        return homePageMapper.getHomePage();
    }

    @Override
    public int insertHomePage(HomePage homePage) {
        return homePageMapper.insertHomePage(homePage);
    }

    @Override
    public int updateHomePage(HomePage homePage) {
        return homePageMapper.updateHomePage(homePage);
    }

    @Override
    public HomePage listHomePage(String assetName) {
        return homePageMapper.listHomePage(assetName);
    }

}
