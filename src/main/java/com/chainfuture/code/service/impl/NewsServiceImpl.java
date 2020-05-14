package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.News;
import com.chainfuture.code.mapper.NewsMapper;
import com.chainfuture.code.service.NewsService;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("newsServiceImpl")
public class NewsServiceImpl implements NewsService {

    //引入apache的log4j日志包
    public static final Logger LOGGER = Logger.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> listNews(News news) {
        return  newsMapper.listNews(news);
    }

    @Override
    public Integer queryCount(News news) {
        return newsMapper.queryCount(news);
    }

    @Override
    public Integer updateNews(News news) {
        return  newsMapper.updateNews(news);
    }

    @Override
    public Integer delNews(Integer id) {
        return newsMapper.delNews(id);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAddToken(News news) {
        Integer tokenTotal = news.getTokenTotal() + news.getNews_add();
        news.setTokenTotal(tokenTotal);
        Integer tokenSurplus = news.getTokenSurplus() + news.getNews_add();
        news.setTokenSurplus(tokenSurplus);
        newsMapper.saveAddToken(news);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void banNews(int id,int status) {
        Map<String,Integer> map=new HashMap<>();
        map.put("id",id);
        map.put("status",status);
        newsMapper.banNews(map);
    }

    @Override
    public Integer saveNews( News news) {
         return    newsMapper.insertNews(news);
    }

    @Override
    public News getById(Integer id) {
        return newsMapper.getById(id);
    }
}

