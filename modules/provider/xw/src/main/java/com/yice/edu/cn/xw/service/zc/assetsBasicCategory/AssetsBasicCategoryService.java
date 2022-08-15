package com.yice.edu.cn.xw.service.zc.assetsBasicCategory;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.zc.assetsBasicCategory.AssetsBasicCategory;
import com.yice.edu.cn.xw.dao.zc.assetsBasicCategory.IAssetsBasicCategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetsBasicCategoryService {
    @Autowired
    private IAssetsBasicCategoryDao assetsBasicCategoryDao;

    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public AssetsBasicCategory findAssetsBasicCategoryById(String id) {
        return assetsBasicCategoryDao.findAssetsBasicCategoryById(id);
    }
    @Transactional
    public void saveAssetsBasicCategory(AssetsBasicCategory assetsBasicCategory) {
        assetsBasicCategory.setId(sequenceId.nextId());
        assetsBasicCategoryDao.saveAssetsBasicCategory(assetsBasicCategory);
    }
    @Transactional(readOnly = true)
    public List<AssetsBasicCategory> findAssetsBasicCategoryListByCondition(AssetsBasicCategory assetsBasicCategory) {
        return assetsBasicCategoryDao.findAssetsBasicCategoryListByCondition(assetsBasicCategory);
    }
    @Transactional(readOnly = true)
    public AssetsBasicCategory findOneAssetsBasicCategoryByCondition(AssetsBasicCategory assetsBasicCategory) {
        return assetsBasicCategoryDao.findOneAssetsBasicCategoryByCondition(assetsBasicCategory);
    }
    @Transactional(readOnly = true)
    public long findAssetsBasicCategoryCountByCondition(AssetsBasicCategory assetsBasicCategory) {
        return assetsBasicCategoryDao.findAssetsBasicCategoryCountByCondition(assetsBasicCategory);
    }
    @Transactional
    public void updateAssetsBasicCategory(AssetsBasicCategory assetsBasicCategory) {
        assetsBasicCategoryDao.updateAssetsBasicCategory(assetsBasicCategory);
    }
    @Transactional
    public void updateAssetsBasicCategoryForNotNull(AssetsBasicCategory assetsBasicCategory) {
        assetsBasicCategoryDao.updateAssetsBasicCategoryForNotNull(assetsBasicCategory);
    }
    @Transactional
    public void deleteAssetsBasicCategory(String id) {
        assetsBasicCategoryDao.deleteAssetsBasicCategory(id);
    }
    @Transactional
    public void deleteAssetsBasicCategoryByCondition(AssetsBasicCategory assetsBasicCategory) {
        assetsBasicCategoryDao.deleteAssetsBasicCategoryByCondition(assetsBasicCategory);
    }
    @Transactional
    public void batchSaveAssetsBasicCategory(List<AssetsBasicCategory> assetsBasicCategorys){
        assetsBasicCategorys.forEach(assetsBasicCategory -> assetsBasicCategory.setId(sequenceId.nextId()));
        assetsBasicCategoryDao.batchSaveAssetsBasicCategory(assetsBasicCategorys);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
