package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.HomePage;
import java.util.List;

public interface HomePageMapper {

    HomePage listHomePage(String assetName);

    HomePage getHomePage();

    int insertHomePage(HomePage homePage);

    int updateHomePage(HomePage homePage);
}
