package com.yice.edu.cn.tap.service.prepLessonResource;

import com.yice.edu.cn.common.pojo.jy.prepLessonResource.LessonResFileMeterialItem;
import com.yice.edu.cn.tap.feignClient.prepLessonResource.LessonResFileMeterialItemFeign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonResFileMeterialItemService {
    @Autowired
    private LessonResFileMeterialItemFeign lessonResFileMeterialItemFeign;

    public LessonResFileMeterialItem findLessonResFileMeterialItemById(String id) {
        return lessonResFileMeterialItemFeign.findLessonResFileMeterialItemById(id);
    }

    public LessonResFileMeterialItem saveLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemFeign.saveLessonResFileMeterialItem(lessonResFileMeterialItem);
    }

    public List<LessonResFileMeterialItem> findLessonResFileMeterialItemListByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemFeign.findLessonResFileMeterialItemListByCondition(lessonResFileMeterialItem);
    }

    public LessonResFileMeterialItem findOneLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemFeign.findOneLessonResFileMeterialItemByCondition(lessonResFileMeterialItem);
    }

    public long findLessonResFileMeterialItemCountByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        return lessonResFileMeterialItemFeign.findLessonResFileMeterialItemCountByCondition(lessonResFileMeterialItem);
    }

    public void updateLessonResFileMeterialItem(LessonResFileMeterialItem lessonResFileMeterialItem) {
        lessonResFileMeterialItemFeign.updateLessonResFileMeterialItem(lessonResFileMeterialItem);
    }

    public void deleteLessonResFileMeterialItem(String id) {
        lessonResFileMeterialItemFeign.deleteLessonResFileMeterialItem(id);
    }

    public void deleteLessonResFileMeterialItemByCondition(LessonResFileMeterialItem lessonResFileMeterialItem) {
        lessonResFileMeterialItemFeign.deleteLessonResFileMeterialItemByCondition(lessonResFileMeterialItem);
    }
    public void deleteLessonResFileMeterialItemByCondition2(LessonResFileMeterialItem lessonResFileMeterialItem) {
        LessonResFileMeterialItem lr =  lessonResFileMeterialItemFeign.findLessonResFileMeterialItemById(lessonResFileMeterialItem.getId());
       String[] itemIdArr = lr.getMeterialItemId().split(",");
        List<String> list = new ArrayList<>();
        if (itemIdArr.length>1){
            for ( String s : itemIdArr){
                if (!s.equals(lessonResFileMeterialItem.getMeterialItemId())){
                    list.add(s);
                }
            }
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < list.size(); i++){
                if (i==list.size()-1){ sb. append(list.get(i).trim()); break; }
                sb. append(list.get(i).trim()+",");
            }
            String newStr = sb.toString();
            lessonResFileMeterialItem.setMeterialItemId(newStr);
            lessonResFileMeterialItemFeign.updateLessonResFileMeterialItem(lessonResFileMeterialItem);
        }else {
            lessonResFileMeterialItemFeign.deleteLessonResFileMeterialItem(lessonResFileMeterialItem.getId());
        }


    }
}
