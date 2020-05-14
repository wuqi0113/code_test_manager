package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.BounsBill;
import com.chainfuture.code.mapper.BounsBillMapper;
import com.chainfuture.code.service.BounsBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BounsBillServiceImpl implements BounsBillService {

    @Autowired
    private BounsBillMapper bounsBillMapper;

    @Override
    public List<BounsBill> selBounsBillList(int status) {
        return bounsBillMapper.selBounsBillList(status);
    }

    @Override
    public void insertBounsBill(BounsBill bounsBill) {
        bounsBillMapper.insertBounsBill(bounsBill);
    }

    @Override
    public void updateBounsBill(BounsBill bounsBill) {
        bounsBillMapper.updateBounsBill(bounsBill);
    }
}
