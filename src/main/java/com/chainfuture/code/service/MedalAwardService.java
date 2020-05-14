package com.chainfuture.code.service;

import com.chainfuture.code.bean.MedalAward;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface MedalAwardService {

    List<MedalAward> getMedalAwardList(MedalAward model);

    Integer queryCount(MedalAward model);

    void saveMedalAward(MedalAward model);

    void delete(Integer id);
}
