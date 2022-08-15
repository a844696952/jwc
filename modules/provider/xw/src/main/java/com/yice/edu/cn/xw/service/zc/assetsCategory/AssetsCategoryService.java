package com.yice.edu.cn.xw.service.zc.assetsCategory;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.xw.zc.assetsBasicCategory.AssetsBasicCategory;
import com.yice.edu.cn.common.pojo.xw.zc.assetsCatetory.AssetsCategory;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.xw.dao.zc.assetsCategory.IAssetsCategoryDao;
import com.yice.edu.cn.xw.service.zc.assetsBasicCategory.AssetsBasicCategoryService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class AssetsCategoryService {
    @Autowired
    private IAssetsCategoryDao assetsCategoryDao;
    @Autowired
    private AssetsBasicCategoryService assetsBasicCategoryService;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public AssetsCategory findAssetsCategoryById(String id) {
        return assetsCategoryDao.findAssetsCategoryById(id);
    }
    @Transactional
    public void saveAssetsCategory(AssetsCategory assetsCategory) {
        assetsCategory.setId(sequenceId.nextId());
        assetsCategoryDao.saveAssetsCategory(assetsCategory);
    }
    @Transactional(readOnly = true)
    public List<AssetsCategory> findAssetsCategoryListByCondition(AssetsCategory assetsCategory) {
        return assetsCategoryDao.findAssetsCategoryListByCondition(assetsCategory);
    }
    @Transactional(readOnly = true)
    public AssetsCategory findOneAssetsCategoryByCondition(AssetsCategory assetsCategory) {
        return assetsCategoryDao.findOneAssetsCategoryByCondition(assetsCategory);
    }
    @Transactional(readOnly = true)
    public long findAssetsCategoryCountByCondition(AssetsCategory assetsCategory) {
        return assetsCategoryDao.findAssetsCategoryCountByCondition(assetsCategory);
    }
    @Transactional
    public void updateAssetsCategory(AssetsCategory assetsCategory) {
        assetsCategoryDao.updateAssetsCategory(assetsCategory);
    }
    @Transactional
    public void deleteAssetsCategory(String id) {
        assetsCategoryDao.deleteAssetsCategory(id);
    }
    @Transactional
    public void deleteAssetsCategoryByCondition(AssetsCategory assetsCategory) {
        assetsCategoryDao.deleteAssetsCategoryByCondition(assetsCategory);
    }
    @Transactional
    public void batchSaveAssetsCategory(List<AssetsCategory> assetsCategorys){
        assetsCategorys.forEach(assetsCategory -> assetsCategory.setId(sequenceId.nextId()));
        assetsCategoryDao.batchSaveAssetsCategory(assetsCategorys);
    }
    @Transactional
    public List<AssetsCategory> findAllAssetsCategory(AssetsCategory assetsCategory) {


        //查询出基本数据
//        List<AssetsBasicCategory> assetsBasicCategoryList = assetsBasicCategoryService.findAssetsBasicCategoryListByCondition(new AssetsBasicCategory());
        //子节点数据
//        List<AssetsCategory> assetsCategoryList = assetsCategoryDao.findAssetsCategoryListByCondition(new AssetsCategory());

        Pager pager = new Pager().setPaging(false).addExcludes("path", "urlPath");
        assetsCategory.setPager(pager);
        List<AssetsCategory> menuList = assetsCategoryDao.findAllCategeoryMenu(assetsCategory);
        return ObjectKit.buildTree(menuList,"-1");

    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
