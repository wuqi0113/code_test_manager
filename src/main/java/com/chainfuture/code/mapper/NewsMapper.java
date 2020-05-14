package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.News;

import java.util.List;
import java.util.Map;

public interface NewsMapper {

    List<News> listNews(News medal);

    Integer queryCount(News news);

    void saveAddToken(News news);

    void banNews(Map<String,Integer> map);

    Integer insertNews(News news);

    Integer updateNews(News news);

    News getById(Integer id);

    Integer delNews(Integer id);
}
