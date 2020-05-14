package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.TaskFram;

import java.util.List;

/**
 * Created by admin on 2019/9/10.
 */
public interface TaskFramMapper {
    List<TaskFram> getTaskFramByModuleName(String moduleName);

    List<TaskFram> getTaskFramByParentId(Integer parentId);
}
