package com.chainfuture.code.service;

import com.chainfuture.code.bean.News;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface NewsService {

    void saveAddToken(News news);

    void banNews(int i,int status);

    Integer saveNews(News news);

    News getById(Integer id);

    List<News> listNews(News news);

    Integer queryCount(News news);

    Integer updateNews(News news);

    Integer delNews(Integer id);
}
