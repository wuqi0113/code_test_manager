package com.chainfuture.code.service;

import com.chainfuture.code.bean.Medal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MedalService {
    

    void saveMedal(Medal medal);

    List<Medal> getMedalList();

    Medal getById(Integer id);

    void updateMedal(Medal medal);

    List<Medal> querySql(String user);

    List<Medal> listMedal(Medal medal);

    Integer queryCount(Medal medal);
}
