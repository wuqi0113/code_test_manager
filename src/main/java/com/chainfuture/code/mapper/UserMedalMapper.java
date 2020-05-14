package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.UserMedal;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/10/22.
 */
public interface UserMedalMapper {

    List<UserMedal> selectSql();

    int getMedalCount();
}
