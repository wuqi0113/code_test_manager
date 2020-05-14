package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.CustFileds;

/**
 * Created by admin on 2019/3/21.
 */
public interface FiledsMapper {
    void truncateTable();

    int insert(CustFileds cf);
}
