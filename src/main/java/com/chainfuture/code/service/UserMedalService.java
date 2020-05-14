package com.chainfuture.code.service;

import com.chainfuture.code.bean.UserMedal;

import java.util.List;
import java.util.Map;

public interface UserMedalService {



    int getMedalCount();

    List<UserMedal> selectSql();
}
