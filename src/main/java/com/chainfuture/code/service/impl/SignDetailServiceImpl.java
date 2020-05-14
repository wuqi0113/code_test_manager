package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.SignDetail;
import com.chainfuture.code.mapper.SignDetailMapper;
import com.chainfuture.code.service.SignDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignDetailServiceImpl implements SignDetailService {

    @Autowired
    private SignDetailMapper signDetailMapper;

    @Override
    public List<SignDetail> selProductCode(Integer status) {
        return signDetailMapper.selProductCode(status);
    }

    @Override
    public void insertProductCode(SignDetail signDetail) {
        signDetailMapper.insertProductCode(signDetail);
    }

    @Override
    public void updateProductCode(SignDetail signDetail) {
        signDetailMapper.updateProductCode(signDetail);
    }

    @Override
    public void delProductCode(Long id) {
        signDetailMapper.delProductCode(id);
    }
}
