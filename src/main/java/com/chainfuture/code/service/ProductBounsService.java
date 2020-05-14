package com.chainfuture.code.service;

import com.chainfuture.code.bean.ProductBouns;

/**
 * Created by admin on 2019/12/19.
 */
public interface ProductBounsService {
    ProductBouns selProductBounsById(int pid);

    void insertProductBouns(ProductBouns productBouns);

    void updateProductBouns(ProductBouns productBouns);
}
