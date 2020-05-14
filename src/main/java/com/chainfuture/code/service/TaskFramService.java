package com.chainfuture.code.service;

import com.chainfuture.code.bean.TaskFram;

import java.util.List;

/**
 * Created by admin on 2019/9/10.
 */
public interface TaskFramService {
    List<TaskFram> getTaskFramByModuleName(String sname);

    List<TaskFram> getTaskFramByParentId(Integer id);
}
