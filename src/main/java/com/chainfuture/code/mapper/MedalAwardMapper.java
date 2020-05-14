package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.MedalAward;

import java.util.List;

public interface MedalAwardMapper {
    List<MedalAward> getMedalAwardList(MedalAward model);

    Integer getCount(MedalAward model);

    void insertMedalAward(MedalAward model);

    void updateMedalAward(MedalAward model);

    void delete(Integer id);
}
