package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.FxAwardInfeed;
import com.chainfuture.code.mapper.FxAwardInfeedMapper;
import com.chainfuture.code.service.FxAwardInfeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FxAwardInfeedServiceImpl implements FxAwardInfeedService {
    @Autowired
    private FxAwardInfeedMapper fxAwardInfeedMapper;

    @Override
    public List<FxAwardInfeed> selFxAwardInfeed() {
        return fxAwardInfeedMapper.selFxAwardInfeed();
    }
}
