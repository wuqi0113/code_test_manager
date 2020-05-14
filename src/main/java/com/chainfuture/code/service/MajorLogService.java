package com.chainfuture.code.service;

import com.chainfuture.code.bean.MajorLog;

import java.util.List;

/**
 * Created by admin on 2019/2/13.
 */
public interface MajorLogService {
    List<MajorLog> listMajorLog(MajorLog model);

    int queryCount(MajorLog model);

    void insertMajorLog(MajorLog model);

    List<MajorLog> getById(Integer id);
}
