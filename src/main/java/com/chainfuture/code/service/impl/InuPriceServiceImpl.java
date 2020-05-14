package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.InuPriceExample;
import com.chainfuture.code.mapper.InuPriceMapper;
import com.chainfuture.code.service.InuPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InuPriceServiceImpl implements InuPriceService {

    @Autowired
    private InuPriceMapper inuPriceMapper;

    @Override
    public InuPriceExample selPrice() {
        return inuPriceMapper.selPrice();
    }
}
