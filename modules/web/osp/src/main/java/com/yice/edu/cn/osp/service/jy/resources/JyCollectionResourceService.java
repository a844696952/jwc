package com.yice.edu.cn.osp.service.jy.resources;

import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.osp.feignClient.jy.resources.JyCollectionResourceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JyCollectionResourceService {
    @Autowired
    private JyCollectionResourceFeign jyCollectionResourceFeign;

    public JyCollectionResource findJyCollectionResourceById(String id) {
        return jyCollectionResourceFeign.findJyCollectionResourceById(id);
    }

    public JyCollectionResource saveJyCollectionResource(JyCollectionResource jyCollectionResource) {
        return jyCollectionResourceFeign.saveJyCollectionResource(jyCollectionResource);
    }
    public void batchSaveJyCollectionResource(List<JyCollectionResource> jyCollectionResources) {
        jyCollectionResourceFeign.batchSaveJyCollectionResource(jyCollectionResources);
    }
    public List<JyCollectionResource> findJyCollectionResourceListByCondition(JyCollectionResource jyCollectionResource) {
        return jyCollectionResourceFeign.findJyCollectionResourceListByCondition(jyCollectionResource);
    }

    public JyCollectionResource findOneJyCollectionResourceByCondition(JyCollectionResource jyCollectionResource) {
        return jyCollectionResourceFeign.findOneJyCollectionResourceByCondition(jyCollectionResource);
    }

    public long findJyCollectionResourceCountByCondition(JyCollectionResource jyCollectionResource) {
        return jyCollectionResourceFeign.findJyCollectionResourceCountByCondition(jyCollectionResource);
    }

    public void updateJyCollectionResource(JyCollectionResource jyCollectionResource) {
        jyCollectionResourceFeign.updateJyCollectionResource(jyCollectionResource);
    }

    public void deleteJyCollectionResource(String id) {
        jyCollectionResourceFeign.deleteJyCollectionResource(id);
    }

    public void deleteJyCollectionResourceByCondition(JyCollectionResource jyCollectionResource) {
        jyCollectionResourceFeign.deleteJyCollectionResourceByCondition(jyCollectionResource);
    }
    /**
     * ????????????????????????????????????
     * @param jyCollectionResource
     * @return
     */
    public List<JyCollectionResource> findJyCollectionResourceList(JyCollectionResource jyCollectionResource) {
        return jyCollectionResourceFeign.findJyCollectionResourceList(jyCollectionResource);
    }
    /**
     * ?????????????????????????????????
     * @param jyCollectionResource
     * @return
     */
    public long findJyCollectionResourceCount(JyCollectionResource jyCollectionResource) {
        return jyCollectionResourceFeign.findJyCollectionResourceCount(jyCollectionResource);
    }

    /**
     * ???????????????????????????????????????
     * @param jyResouces
     */
    public void updateManyCollectionResouces(JyResouces jyResouces){
        jyCollectionResourceFeign.updateManyCollectionResouces(jyResouces);
    }
    /**
     * ??????????????????
     * @param jyCollectionResource
     */
    public void deleteManyCollectionResouces(JyCollectionResource jyCollectionResource){
        jyCollectionResourceFeign.deleteManyCollectionResouces(jyCollectionResource);
    }

    /**
     * ??????????????????
     * @param jyCollectionResource
     * @return
     */
    public List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource){
        return jyCollectionResourceFeign.getFileList(jyCollectionResource);
    }

    /**
     * ??????????????????
     * @param jyCollectionResource
     * @return
     */
    public int repeatCollectionResouces(JyCollectionResource jyCollectionResource){
        return jyCollectionResourceFeign.repeatCollectionResouces(jyCollectionResource);
    }

    public void deleteJyResoucesType(String id){
        jyCollectionResourceFeign.deleteJyResoucesType(id);
    }
}
