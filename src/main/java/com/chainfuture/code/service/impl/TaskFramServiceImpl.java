package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.TaskFram;
import com.chainfuture.code.mapper.TaskFramMapper;
import com.chainfuture.code.service.TaskFramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskFramServiceImpl implements TaskFramService {

    @Autowired
    private TaskFramMapper taskFramMapper;

    @Override
    public List<TaskFram> getTaskFramByModuleName(String sname) {
        return taskFramMapper.getTaskFramByModuleName(sname);
    }

    @Override
    public List<TaskFram> getTaskFramByParentId(Integer id) {
        return taskFramMapper.getTaskFramByParentId(id);
    }
}
