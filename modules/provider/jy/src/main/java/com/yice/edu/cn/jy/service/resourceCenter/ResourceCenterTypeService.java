package com.yice.edu.cn.jy.service.resourceCenter;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterType;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import com.yice.edu.cn.jy.dao.resourceCenter.IResourceCenterConditionDao;
import com.yice.edu.cn.jy.dao.resourceCenter.IResourceCenterTypeConditionDao;
import com.yice.edu.cn.jy.dao.resourceCenter.IResourceCenterTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResourceCenterTypeService {
    @Autowired
    private IResourceCenterTypeConditionDao resourceCenterTypeConditionDao;
    @Autowired
    private IResourceCenterConditionDao resourceCenterConditionDao;
    @Autowired
    private IResourceCenterTypeDao resourceCenterTypeDao;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public ResourceCenterType findResourceCenterTypeById(String id) {
        return resourceCenterTypeDao.findResourceCenterTypeById(id);
    }
    @Transactional
    public void saveResourceCenterType(ResourceCenterType resourceCenterType) {
        resourceCenterType.setId(sequenceId.nextId());
        resourceCenterTypeDao.saveResourceCenterType(resourceCenterType);
    }
    @Transactional(readOnly = true)
    public List<ResourceCenterType> findResourceCenterTypeListByCondition(ResourceCenterType resourceCenterType) {
        return resourceCenterTypeDao.findResourceCenterTypeListByCondition(resourceCenterType);
    }
    @Transactional(readOnly = true)
    public ResourceCenterType findOneResourceCenterTypeByCondition(ResourceCenterType resourceCenterType) {
        return resourceCenterTypeDao.findOneResourceCenterTypeByCondition(resourceCenterType);
    }
    @Transactional(readOnly = true)
    public long findResourceCenterTypeCountByCondition(ResourceCenterType resourceCenterType) {
        return resourceCenterTypeDao.findResourceCenterTypeCountByCondition(resourceCenterType);
    }
    @Transactional
    public void updateResourceCenterType(ResourceCenterType resourceCenterType) {
        resourceCenterTypeDao.updateResourceCenterType(resourceCenterType);
    }
    @Transactional
    public void deleteResourceCenterType(String id) {
        resourceCenterTypeDao.deleteResourceCenterType(id);
    }
    @Transactional
    public void deleteResourceCenterTypeByCondition(ResourceCenterType resourceCenterType) {
        resourceCenterTypeDao.deleteResourceCenterTypeByCondition(resourceCenterType);
    }
    @Transactional
    public void batchSaveResourceCenterType(List<ResourceCenterType> resourceCenterTypes){
        resourceCenterTypes.forEach(resourceCenterType -> resourceCenterType.setId(sequenceId.nextId()));
        resourceCenterTypeDao.batchSaveResourceCenterType(resourceCenterTypes);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public void saveResourceCenterType4Like(ResourceCenterType resourceCenterType) {
        resourceCenterType.setId(sequenceId.nextId());
        if(resourceCenterType.getSort() == null || "".equals(resourceCenterType.getSort())){
            resourceCenterType.setSort(100);
        }
        resourceCenterTypeDao.saveResourceCenterType(resourceCenterType);
    }
    @Transactional(readOnly = true)
    public List<ResourceCenterType> findResourceCenterTypeListByCondition4Like(ResourceCenterType resourceCenterType) {
        List<ResourceCenterType> list = resourceCenterTypeDao.findResourceCenterTypeListByCondition(resourceCenterType);
        if(!list.isEmpty()){
            list.forEach(s->{
                ResourceCenterCondition resourceCenterCondition = new ResourceCenterCondition();
                resourceCenterCondition.setTypeId(s.getId());
                long num = resourceCenterConditionDao.findResourceCenterConditionCountByCondition(resourceCenterCondition);
                s.setNum(num);
            });
        }
        return list;
    }

    @Transactional
    public void updateResourceCenterType4Like(ResourceCenterType resourceCenterType) {
        ResourceCenterType s1 = new ResourceCenterType();
        s1.setId(resourceCenterType.getId());
        s1.setName(resourceCenterType.getName());
        if(resourceCenterType.getSort()==null){
            resourceCenterType.setSort(200);
        }
        s1.setSort(resourceCenterType.getSort());
        resourceCenterTypeDao.updateResourceCenterType(s1);//修改分类表
        ResourceCenterTypeCondition s2 = new ResourceCenterTypeCondition();
        s2.setTypeId(s1.getId());
        long num = resourceCenterTypeConditionDao.findResourceCenterTypeConditionCountByCondition(s2);
        if(num==0){
            return;
        }else{
            s2.setTypeName(s1.getName());
            resourceCenterTypeConditionDao.updateResourceCenterTypeCondition(s2);
            return;
        }
    }
    @Transactional
    public ResourceCenterType deleteResourceCenterType4Like(String id) {
        ResourceCenterTypeCondition resourceCenterTypeCondition = new ResourceCenterTypeCondition();//查询是否可以删除
        resourceCenterTypeCondition.setTypeId(id);
        long count = resourceCenterTypeConditionDao.findResourceCenterTypeConditionCountByCondition(resourceCenterTypeCondition);
        ResourceCenterType resourceCenterType = new ResourceCenterType();
        if(count>=1) {
            resourceCenterType.setCode("204");
            resourceCenterType.setMsg("当前分类下有视频文件，请先取消视频关联");
            return resourceCenterType;
        }
        resourceCenterTypeDao.deleteResourceCenterType(id);//删除分类表
        ResourceCenterCondition resourceCenterCondition = new ResourceCenterCondition();//删除条件表
        resourceCenterCondition.setTypeId(id);
        resourceCenterConditionDao.deleteResourceCenterConditionByCondition(resourceCenterCondition);
        resourceCenterType.setCode("200");
        return resourceCenterType;
    }

    @Transactional
    public void deleteResourceCenterCondition4Like(ResourceCenterType resourceCenterType) {
        String[] ids = resourceCenterType.getIds();
        for(String id: ids){
            ResourceCenterCondition resourceCenterCondition = new ResourceCenterCondition();
            resourceCenterCondition.setTypeId(id);
            resourceCenterConditionDao.deleteResourceCenterConditionByCondition(resourceCenterCondition);
        }
    }
}
