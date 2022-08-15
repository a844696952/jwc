package com.yice.edu.cn.xw.service.zc.assetsStockQuery;

import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockQuery.AssetsStockGatherDto;
import com.yice.edu.cn.xw.dao.zc.assetsStockQuery.IAssetsStockQueryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AssetsStockQueryService {
    @Autowired
    private IAssetsStockQueryDao assetsStockQueryDao;

    @Transactional
    public AssetsStockGatherDto findAssetsStockGather(String schoolId){
        AssetsStockGatherDto assetsStockGather = assetsStockQueryDao.findAssetsStockGather(schoolId);
        if (assetsStockGather.getUpkeepCostsTotal()!=null){
            assetsStockGather.setUpkeepCostsTotal(assetsStockGather.getUpkeepCostsTotal().setScale(2,BigDecimal.ROUND_HALF_UP));
        }
        return assetsStockGather;
    }

    @Transactional
    public List<AssetsFile> findAssetsStockListByCondition(AssetsFile assetsFile){
        return assetsStockQueryDao.findAssetsStockListByCondition(assetsFile);
    }

    @Transactional
    public long findAssetsStockListCountByCondition(AssetsFile assetsFile){
        return assetsStockQueryDao.findAssetsStockListCountByCondition(assetsFile);
    }
}
