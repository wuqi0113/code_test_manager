package com.chainfuture.code.mapper;

import com.chainfuture.code.bean.ProductBouns;

/**
 * Created by admin on 2019/12/19.
 */
public interface ProductBounsMapper {
    ProductBouns selProductBounsById(int pid);

    void insertProductBouns(ProductBouns productBouns);

    void updateProductBouns(ProductBouns productBouns);
}
