package com.chainfuture.code.service.impl;

import com.chainfuture.code.bean.Asset;
import com.chainfuture.code.mapper.AssetMapper;
import com.chainfuture.code.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("assetServiceImpl")
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetMapper assetMapper;

    @Override
    public List<Asset> getAssetList() {
        return assetMapper.getAssetList();
    }
}
