package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.InuBase;

public interface InuBaseMapper {
    InuBase selInuBaseById(int id);

    InuBase selInuBaseByType(int type);
}
