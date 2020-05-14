package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.SignDetail;

import java.util.List;

/**
 * Created by admin on 2020/1/10.
 */
public interface SignDetailMapper {
    List<SignDetail> selProductCode(Integer status);

    void insertProductCode(SignDetail signDetail);

    void updateProductCode(SignDetail signDetail);

    void delProductCode(Long id);
}
