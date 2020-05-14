package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.FxAward;
import com.chainfuture.code.mapper.FxAwardMapper;
import com.chainfuture.code.service.FxAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FxAwardServiceImpl implements FxAwardService {
    @Autowired
    private FxAwardMapper fxAwardMapper;

    @Override
    public List<FxAward> selFxAward() {
        return fxAwardMapper.selFxAward();
    }
}
