package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.MajorLog;

import java.util.List;

public interface MajorLogMapper {
    List<MajorLog> listMajorLog(MajorLog model);

    int queryCount(MajorLog model);

    void insertMajorLog(MajorLog model);

    List<MajorLog> getById(Integer id);
}
