package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.Medal;

import java.util.List;

public interface MedalMapper {

    List<Medal> listMedal(Medal medal);

    void insertMedal(Medal medal);

    void updateMedal(Medal medal);

    Integer queryCount(Medal medal);

    List<Medal> listMedal();

    Medal getById(Integer id);

    List<Medal> querySql(String user);
}
