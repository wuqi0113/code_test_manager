package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.SysModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2019/7/31.
 */
public interface SysModelMapper {
    List<HashMap<String,Object>> getModelList();
}
