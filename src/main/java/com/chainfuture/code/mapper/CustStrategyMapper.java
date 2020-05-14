package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.CustStrategy;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by admin on 2019/3/21.
 */
public interface CustStrategyMapper {
    void insert(CustStrategy custTable);


    List<CustStrategy> selectStrateAll(Integer id);

    List<CustStrategy> selFiled(int workId);
}
