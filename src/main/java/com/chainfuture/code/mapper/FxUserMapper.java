package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.FxUser;

/**
 * Created by admin on 2020/4/14.
 */
public interface FxUserMapper {
    FxUser selUserByAddr(String s);

    void insertUser(FxUser uu);

    FxUser selUserByInviteCode(String s1);

    FxUser selUserById(int pid);

    void updateUser(FxUser fx1);
}
