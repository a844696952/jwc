package com.yice.edu.cn.jw.service.jySchoolResoucesByDay;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.jy.resources.*;
import com.yice.edu.cn.jw.feign.JySchoolResoucesFeign.JySchoolResoucesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class JySchoolResoucesService {
    @Autowired
    private JySchoolResoucesFeign jySchoolResoucesFeign;

    public JySchoolResouces findJySchoolResoucesById(String id) {
        return jySchoolResoucesFeign.findJySchoolResoucesById(id);
    }

    public JySchoolResouces saveJySchoolResouces(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.saveJySchoolResouces(jySchoolResouces);
    }

    public List<JySchoolResouces> findJySchoolResoucesListByCondition(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.findJySchoolResoucesListByCondition(jySchoolResouces);
    }

    public JySchoolResouces findOneJySchoolResoucesByCondition(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.findOneJySchoolResoucesByCondition(jySchoolResouces);
    }

    public long findJySchoolResoucesCountByCondition(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.findJySchoolResoucesCountByCondition(jySchoolResouces);
    }

    public void updateJySchoolResouces(JySchoolResouces jySchoolResouces) {
        jySchoolResoucesFeign.updateJySchoolResouces(jySchoolResouces);
    }
    /**
     * 批量插入数据
     * @param jySchoolResoucesList
     * @return
     */
    public void batchSaveJySchoolResouces(List<JySchoolResouces> jySchoolResoucesList) {
        jySchoolResoucesFeign.batchSaveJySchoolResouces(jySchoolResoucesList);
    }
    public void deleteJySchoolResouces(String id) {
        jySchoolResoucesFeign.deleteJySchoolResouces(id);
    }

    public void deleteJySchoolResoucesByCondition(JySchoolResouces jySchoolResouces) {
        jySchoolResoucesFeign.deleteJySchoolResoucesByCondition(jySchoolResouces);
    }
    /**
     * 返回资源列表和文件夹列表
     * @param jySchoolResouces
     * @return
     */
    public List<JySchoolResouces> findJySchoolResoucesList(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.findJySchoolResoucesList(jySchoolResouces);
    }
    /**
     * 返回资源和文件夹记录数
     * @param jySchoolResouces
     * @return
     */
    public long findJySchoolResoucesCount(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.findJySchoolResoucesCount(jySchoolResouces);
    }

    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    public void updateManySchoolResouces(JyResouces jyResouces){
        jySchoolResoucesFeign.updateManySchoolResouces(jyResouces);
    }

    /**
     * 批量收藏
     *
     */
    public List<JyCollectionResource> insertManySchoolResouces(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesFeign.insertManySchoolResouces(jySchoolResouces);
    }
    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    public List<JyCollectionResource> getFileList(JyCollectionResource jyCollectionResource){
        return jySchoolResoucesFeign.getFileList(jyCollectionResource);
    }
    /**
     * 批量获取文件
     * @param jySchoolResouces
     * @return
     */
    public int repeatSchoolResouces(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesFeign.repeatSchoolResouces(jySchoolResouces);
    }

    //批量删除指定文件夹id
    public void deleteByResoucesType(JyResouces jyResouces){
        jySchoolResoucesFeign.deleteByResoucesType(jyResouces);
    }
    //批量删除指定的文件id
    public void deleteByResoucesId(JyResouces jyResouces){
        jySchoolResoucesFeign.deleteByResoucesId(jyResouces);
    }
    //批量删除指定的文件id
    public void deleteJyResoucesType(String id){
        jySchoolResoucesFeign.deleteJyResoucesType(id);
    }

    /**
     * 统计校本资源各种类型的数量，传入schoolId,teacherId,startTime(开始时间),endTime（结束时间）,type（类型），schollId必传
     * @param jySchoolResouces
     * @return
     */
    public List<JySchoolResourceCensus> censusSchoolResouces(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesFeign.censusSchoolResouces(jySchoolResouces);
    }
    /**
     * 统计校本资源的总数量，传入schoolId,teacherId,startTime(开始时间),endTime（结束时间）,type（类型），schollId必传
     * @param jySchoolResouces
     * @return
     */
    public long censusSumSchoolResouces(JySchoolResouces jySchoolResouces){
        return jySchoolResoucesFeign.censusSumSchoolResouces(jySchoolResouces);
    }
    /**
     * 统计校本资源各每天的数量，传入schoolId,teacherId,startTime(开始时间),endTime（结束时间）,type（类型），schollId必传
     * @param jySchoolResouces
     * @return
     */
    public List<JySchoolResourcesByDay> censusSumResoucesByDay(JySchoolResouces jySchoolResouces){
        List<JySchoolResourcesByDay> jySchoolResourcesByDayList = jySchoolResoucesFeign.censusSumResoucesByDay(jySchoolResouces);
        long cnt = DateUtil.between(DateUtil.parse(jySchoolResouces.getStartTime()),DateUtil.parse(jySchoolResouces.getEndTime()), DateUnit.DAY);
        List<JySchoolResourcesByDay> jySchoolResourcesByDayList1 = new ArrayList<>();
        for (int i=0;i<cnt;i++){
            String time = DateUtil.format(DateUtil.offsetDay(DateUtil.parse(jySchoolResouces.getStartTime()),i),"yyyy-MM-dd");
            JySchoolResourcesByDay jySchoolResourcesByDay = new JySchoolResourcesByDay();
            jySchoolResourcesByDay.setCnt(0);
            jySchoolResourcesByDay.setTime(time);
            jySchoolResourcesByDayList1.add(jySchoolResourcesByDay);
        }
        List<JySchoolResourcesByDay> listAll = jySchoolResourcesByDayList.parallelStream().collect(toList());
        List<JySchoolResourcesByDay> listAll2 = jySchoolResourcesByDayList1.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        List<JySchoolResourcesByDay> listAllDistinct = listAll.stream().distinct().collect(toList());
        listAllDistinct.sort(Comparator.comparing(JySchoolResourcesByDay::getTime));
        return listAllDistinct;
    }
}
