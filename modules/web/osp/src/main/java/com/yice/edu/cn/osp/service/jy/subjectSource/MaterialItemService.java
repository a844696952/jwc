package com.yice.edu.cn.osp.service.jy.subjectSource;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jy.source21.model.common.Chapter;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.feignClient.dd.DdFeign;
import com.yice.edu.cn.osp.service.source21.Source21Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItem;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.MaterialItemKnowledge;
import com.yice.edu.cn.osp.feignClient.jy.subjectSource.MaterialItemFeign;

import static java.util.stream.Collectors.toList;

@Service
public class MaterialItemService {
    @Autowired
    private MaterialItemFeign materialItemFeign;
    @Autowired
    private DdFeign ddFeign;
    @Autowired
    private Source21Service source21Service;

    @CreateCache(name = Constant.Redis.OSP_STUDY_MENU, expire = 2, timeUnit= TimeUnit.HOURS)
    private Cache<String, List<Dd>> ddItemCache;


    public MaterialItem findMaterialItemById(String id) {
        return materialItemFeign.findMaterialItemById(id);
    }

    public MaterialItem saveMaterialItem(MaterialItem materialItem) {
        return materialItemFeign.saveMaterialItem(materialItem);
    }

    public List<MaterialItem> findMaterialItemListByCondition(MaterialItem materialItem) {
        return materialItemFeign.findMaterialItemListByCondition(materialItem);
    }

    public MaterialItem findOneMaterialItemByCondition(MaterialItem materialItem) {
        return materialItemFeign.findOneMaterialItemByCondition(materialItem);
    }

    public long findMaterialItemCountByCondition(MaterialItem materialItem) {
        return materialItemFeign.findMaterialItemCountByCondition(materialItem);
    }

    public void updateMaterialItem(MaterialItem materialItem) {
        materialItemFeign.updateMaterialItem(materialItem);
    }

    public void deleteMaterialItem(String id) {
        materialItemFeign.deleteMaterialItem(id);
    }

    public void deleteMaterialItemByCondition(MaterialItem materialItem) {
        materialItemFeign.deleteMaterialItemByCondition(materialItem);
    }
    public List<KnowledgePoint> findKnowledgePointListByItem(MaterialItemKnowledge materialItemKnowledge){
    	return materialItemFeign.findKnowledgePointListByItem(materialItemKnowledge);
    }
    public long findKnowledgePointCountByItem(MaterialItemKnowledge materialItemKnowledge) {
    	return materialItemFeign.findKnowledgePointCountByItem(materialItemKnowledge);
    }

    public List<Dd> findMaterialItemTree(MaterialItem materialItem) {
        List<Dd> ddResult=ddItemCache.get(Constant.Redis.OSP_STUDY_MENU);
        if(ddResult==null){
            List<Dd> dds = new ArrayList<>();
            Dd dd = new Dd();
            dd.setTypeId(Constant.DD_TYPE.SCHOOL_TYPE);
            List<Dd> data=ddFeign.findDdListByCondition(dd);
            data.forEach(e->{
                materialItem.setId(e.getId());
                List<MaterialItem> li = getChilren(materialItem);
                e.setItem(li);
            });
            ddItemCache.put(Constant.Redis.OSP_STUDY_MENU, data);

            return  data;
        }
        return ddResult;
    }

    /**
     *
     * @Title: getChilren
     * @Description: 获取子节点
     * @param
     * @param
     * @return List<MaterialItem>    返回类型
     * @throws
     */
    private  List<MaterialItem> getChilren(MaterialItem materialItem){
        List<MaterialItem> materialItemsResult=materialItemFeign.findMaterialItemTree(materialItem);
        List<MaterialItem> materialItemsResult2=materialItemsResult;
        materialItemsResult=materialItemsResult.stream()
                .filter(m->m.getLevel()==1)
                .collect(Collectors.mapping(m->{
                    m.setChildren(getChilren(m.getId(),materialItemsResult2));
                    return m;
                }, toList()));
        return  materialItemsResult;
    }

    /**
     *
     * @Title: getChilren
     * @Description: 获取子节点
     * @param parentId  父Id
     * @param materialItems 列表
     * @return List<MaterialItem>    返回类型
     * @throws
     */
    private static List<MaterialItem> getChilren(String parentId,final List<MaterialItem> materialItems){
        return materialItems.stream()
                .filter(m-> Objects.equals(m.getParentId(), parentId))
                .collect(Collectors.mapping(m->{
                    final List<MaterialItem> list = getChilren(m.getId(),materialItems);
                    m.setChildren(list.isEmpty()?null:list);
                    return m;
                },toList()));
    }

    /**
     * 查询教材对应的章节树
     * @param resourceVo
     * @return
     */
    public ResponseJson findChapterTree(ResourceVo resourceVo) {
        return materialItemFeign.findChapterTree(resourceVo);
    }
}
