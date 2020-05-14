package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.MedalAward;
import com.chainfuture.code.mapper.MedalAwardMapper;
import com.chainfuture.code.service.MedalAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("medalAwardServiceImpl")
public class MedalAwardServiceImpl implements MedalAwardService {

    @Autowired
    private MedalAwardMapper medalAwardMapper;

    @Override
    public List<MedalAward> getMedalAwardList(MedalAward model) {
        return medalAwardMapper.getMedalAwardList(model);
    }

    @Override
    public Integer queryCount(MedalAward model) {
        return medalAwardMapper.getCount(model);
    }

    @Override
    public void saveMedalAward(MedalAward model) {
        if (model.getId()==null&&model.getId()<0){
            model.setStatus(1);
            medalAwardMapper.insertMedalAward(model);
        }else {
            medalAwardMapper.updateMedalAward(model);
        }
    }

    @Override
    public void delete(Integer id) {
        medalAwardMapper.delete(id);
    }
}
