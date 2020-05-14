package com.chainfuture.code.service;

import com.chainfuture.code.bean.HomePage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface HomePageService {

    HomePage listHomePage(String assetName);

    HomePage getHomePage();

    int insertHomePage(HomePage homePage);

    int updateHomePage(HomePage homePage);
}
