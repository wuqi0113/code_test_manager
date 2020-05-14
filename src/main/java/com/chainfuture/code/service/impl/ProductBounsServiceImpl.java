package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.ProductBouns;
import com.chainfuture.code.mapper.ProductBounsMapper;
import com.chainfuture.code.service.ProductBounsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBounsServiceImpl implements ProductBounsService {

    @Autowired
    private ProductBounsMapper productBounsMapper;

    @Override
    public ProductBouns selProductBounsById(int pid) {
        return productBounsMapper.selProductBounsById(pid);
    }

    @Override
    public void insertProductBouns(ProductBouns productBouns) {
        productBounsMapper.insertProductBouns(productBouns);
    }

    @Override
    public void updateProductBouns(ProductBouns productBouns) {
        productBounsMapper.updateProductBouns(productBouns);
    }
}
