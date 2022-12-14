package com.yice.edu.cn.yed.service.jy.knowledgePoint;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.yice.edu.cn.common.pojo.jy.knowledgePoint.KnowledgePoint;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.yed.feignClient.jy.knowledgePoint.KnowledgePointFeign;
import com.yice.edu.cn.yed.feignClient.jy.source21.Source21Feign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KnowledgePointService {
    @Autowired
    private KnowledgePointFeign knowledgePointFeign;
    @Autowired
    private Source21Feign source21Feign;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public KnowledgePoint findKnowledgePointById(String id) {
        return knowledgePointFeign.findKnowledgePointById(id);
    }

    public KnowledgePoint saveKnowledgePoint(KnowledgePoint knowledgePoint) {
        return knowledgePointFeign.saveKnowledgePoint(knowledgePoint);
    }

    public List<KnowledgePoint> findKnowledgePointListByCondition(KnowledgePoint knowledgePoint) {
        return knowledgePointFeign.findKnowledgePointListByCondition(knowledgePoint);
    }

    public KnowledgePoint findOneKnowledgePointByCondition(KnowledgePoint knowledgePoint) {
        return knowledgePointFeign.findOneKnowledgePointByCondition(knowledgePoint);
    }

    public long findKnowledgePointCountByCondition(KnowledgePoint knowledgePoint) {
        return knowledgePointFeign.findKnowledgePointCountByCondition(knowledgePoint);
    }

    public void updateKnowledgePoint(KnowledgePoint knowledgePoint) {
        knowledgePointFeign.updateKnowledgePoint(knowledgePoint);
    }

    public void updateKnowledgePointForAll(KnowledgePoint knowledgePoint) {
        knowledgePointFeign.updateKnowledgePointForAll(knowledgePoint);
    }

    public void deleteKnowledgePoint(String id) {
        knowledgePointFeign.deleteKnowledgePoint(id);
    }

    public void deleteKnowledgePointByCondition(KnowledgePoint knowledgePoint) {
        knowledgePointFeign.deleteKnowledgePointByCondition(knowledgePoint);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public Map<String,Object> uploadKnowledgePoint(MultipartFile file) {
        Map<String,Object> result = new HashMap<>();
        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        try (InputStream is = file.getInputStream()){
            List<KnowledgePoint> list = ExcelImportUtil.importExcel(is,
                    KnowledgePoint.class, params);
            list = list.stream().filter(t->!isAllFieldNull(t)).collect(Collectors.toList());
            if(list == null||list.size()<=0){
                result.put("size",0);
                result.put("code",201);
                result.put("error","?????????????????????");
            }
            result = knowledgePointFeign.uploadKnowledgePoint(list);
            result.put("size",list.size());
        }catch (Exception e){

        }
        return result;
    }
    //?????????????????????: ??????ture?????????????????????null  ??????false??????????????????????????????null
    private boolean isAllFieldNull(Object obj){
        Class stuCla = obj.getClass();// ???????????????
        Field[] fs = stuCla.getDeclaredFields();//??????????????????
        boolean flag = true;
        try{
            for (Field f : fs) {//????????????
                f.setAccessible(true); // ??????????????????????????????(??????????????????)
                Object val = f.get(obj);// ?????????????????????
                if(val!=null&&!val.equals("null")) {//?????????1??????????????????,??????????????????????????????????????????
                    flag = false;
                    break;
                }
            }
        }catch (Exception e){
        }
        return flag;
    }
    public void downKnowledge421(String stage) {
        if(StringUtils.isNotEmpty(stage))
            source21Feign.downKnowledgeByStage(stage);
    }
    public List<KnowledgePoint> findKnowledgePointTreeByCondition(KnowledgePoint knowledgePoint) {
        return ObjectKit.buildTree(knowledgePointFeign.findKnowledgePointListByCondition(knowledgePoint),"0");
    }
}
