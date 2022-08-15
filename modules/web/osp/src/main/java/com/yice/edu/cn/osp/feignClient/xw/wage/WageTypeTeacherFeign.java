package com.yice.edu.cn.osp.feignClient.xw.wage;

import com.yice.edu.cn.common.pojo.xw.wage.WageTypeTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="xw",contextId = "wageTypeTeacherFeign",path = "/wageTypeTeacher")
public interface WageTypeTeacherFeign {
    @GetMapping("/findWageTypeTeacherById/{id}")
    WageTypeTeacher findWageTypeTeacherById(@PathVariable("id") String id);
    @PostMapping("/saveWageTypeTeacher")
    WageTypeTeacher saveWageTypeTeacher(WageTypeTeacher wageTypeTeacher);
    @PostMapping("/findWageTypeTeacherListByCondition")
    List<WageTypeTeacher> findWageTypeTeacherListByCondition(WageTypeTeacher wageTypeTeacher);
    @PostMapping("/findWageTypeTeacherListByWorkNum")
    List<WageTypeTeacher> findWageTypeTeacherListByWorkNum(WageTypeTeacher wageTypeTeacher);
    @PostMapping("/findOneWageTypeTeacherByCondition")
    WageTypeTeacher findOneWageTypeTeacherByCondition(WageTypeTeacher wageTypeTeacher);
    @PostMapping("/findWageTypeTeacherCountByCondition")
    long findWageTypeTeacherCountByCondition(WageTypeTeacher wageTypeTeacher);
    @PostMapping("/updateWageTypeTeacher")
    void updateWageTypeTeacher(WageTypeTeacher wageTypeTeacher);
    @GetMapping("/deleteWageTypeTeacher/{id}")
    void deleteWageTypeTeacher(@PathVariable("id") String id);
    @PostMapping("/deleteWageTypeTeacherByCondition")
    void deleteWageTypeTeacherByCondition(WageTypeTeacher wageTypeTeacher);
    @PostMapping("/saveWageTypeValue")
    void saveWageTypeValue(WageTypeTeacher wageTypeTeacher);

    @PostMapping("/findWageValueByTypeId")
    List<Map<String, Object>> findWageValueByTypeId(Map<String,Object>wageTypeTeacherMap);

    @PostMapping("/findWageValueByRecordId")
    List<Map<String, String>> findWageValueByRecordId(Map<String,Object>wageTypeRecordMap);
    @PostMapping("/findWageValueByTeacherId")
    List<Map<String, String>> findWageValueByTeacherId(Map<String,Object>wageTypeTeacherMap);
    @PostMapping("/findWageAttributeListByRecordId")
    List<WageTypeTeacher> findWageAttributeListByRecordId(WageTypeTeacher wageTypeTeacher);
    @PostMapping("/findWageAttributeListByTeacherId")
    List<WageTypeTeacher> findWageAttributeListByTeacherId(WageTypeTeacher wageTypeTeacher);

    @PostMapping("/findWageAttributeNameByTeacherId")
    List<WageTypeTeacher> findWageAttributeNameByTeacherId(WageTypeTeacher wageTypeTeacher);

    @PostMapping("/updateWageTypeValue")
    void updateWageTypeValue(WageTypeTeacher wageTypeTeacher);

    @GetMapping("/deleteWageValueByRecordId/{id}")
    void deleteWageValueByRecordId(@PathVariable("id") String id);

    @PostMapping("/updateWageTypeTeacherReleaseState")
    void updateWageTypeTeacherReleaseState(WageTypeTeacher wageTypeTeacher);

    @PostMapping("/findWageValueByTypeIdCount")
    long findWageValueByTypeIdCount(WageTypeTeacher wageTypeTeacher);

    @PostMapping("/batchSaveWageTypeTeacher")
    Map<String,Object> batchSaveWageTypeTeacher(List<WageTypeTeacher> wageTypeTeacherList);

}
